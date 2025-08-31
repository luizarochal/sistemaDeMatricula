package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CursoRepositorio {

    private final Path caminhoArquivo;

    public CursoRepositorio(String nomeArquivo) {
        this.caminhoArquivo = Path.of(nomeArquivo);
    }

    public List<Curso> carregar() throws IOException {
        if (!Files.exists(caminhoArquivo)) {
            return new ArrayList<>();
        }

        List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
        List<Curso> cursos = new ArrayList<>();

        for (String linha : linhas) {
            if (!linha.isBlank()) {
                cursos.add(criarDoTexto(linha));
            }
        }
        return cursos;
    }

    public void salvar(List<Curso> cursos) throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Curso c : cursos) {
            linhas.add(gerarDadosTexto(c));
        }
        Files.write(caminhoArquivo, linhas, StandardCharsets.UTF_8);
    }

    private String gerarDadosTexto(Curso curso) {
        return curso.getNome() + ";" + curso.getNumCreditos();
    }

    private Curso criarDoTexto(String linha) throws IOException {
        String[] dados = linha.split(";");
        if (dados.length != 2) {
            throw new IllegalArgumentException("Linha inválida: " + linha);
        }

        String nomeCurso = dados[0];
        int numCreditos = Integer.parseInt(dados[1]);

        // Evita dependência circular: não carrega disciplinas aqui
        List<Disciplina> disciplinasDoCurso = new ArrayList<>();
        return new Curso(nomeCurso, numCreditos, disciplinasDoCurso);
    }

    public Curso procurarCurso(String nomeCurso) throws IOException {
        for (Curso c : carregar()) {
            if (c.getNome().equalsIgnoreCase(nomeCurso)) {
                return c;
            }
        }
        return null;
    }
}
