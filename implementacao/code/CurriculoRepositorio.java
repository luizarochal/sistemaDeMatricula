package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurriculoRepositorio {
    // nome;ano;semestre,curso1,curso2,...;
    public static Curriculo criarDoTexto(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 4) {
            throw new IllegalArgumentException("Linha inválida: " + linha);
        }

        String nome = partes[0];
        LocalDate ano = LocalDate.parse(partes[1]);
        int semestre = Integer.parseInt(partes[2]);

        List<Curso> cursos = new ArrayList<>();
        if (partes.length > 3 && !partes[3].isEmpty()) {
            String[] nomesCursos = partes[3].split(",");
            CursoRepositorio cursoRepo = new CursoRepositorio("curso.txt");

            try {
                List<Curso> todosCursos = cursoRepo.carregar();
                for (String nomeCurso : nomesCursos) {
                    Curso cursoEncontrado = todosCursos.stream()
                            .filter(c -> c.getNome().equalsIgnoreCase(nomeCurso.trim()))
                            .findFirst()
                            .orElse(null);

                    if (cursoEncontrado != null) {
                        cursos.add(cursoEncontrado);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Curriculo(nome, ano, cursos, semestre);
    }

    public static List<Curriculo> carregar(String nomeArquivo) throws IOException {
        Path caminho = Path.of(nomeArquivo);
        List<Curriculo> curriculos = new ArrayList<>();
        if (!Files.exists(caminho)) {
            return curriculos;
        }
        List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);
        for (String linha : linhas) {
            if (!linha.isBlank()) {
                curriculos.add(criarDoTexto(linha));
            }
        }
        return curriculos;
    }

    public static String gerarDadosTexto(Curriculo curriculo) {
        StringBuilder sb = new StringBuilder();
        sb.append(curriculo.getNome()).append(";")
                .append(curriculo.getAno()).append(";")
                .append(curriculo.getSemestre()).append(";");
        for (int i = 0; i < curriculo.getCursos().size(); i++) {
            sb.append(curriculo.getCursos().get(i).getNome());
            if (i < curriculo.getCursos().size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static void salvar(String nomeArquivo, List<Curriculo> curriculos) throws IOException {
        Path caminho = Path.of(nomeArquivo);
        List<String> linhas = new ArrayList<>();
        for (Curriculo curriculo : curriculos) {
            linhas.add(CurriculoRepositorio.gerarDadosTexto(curriculo));
        }
        Files.write(caminho, linhas, StandardCharsets.UTF_8);
    }

}