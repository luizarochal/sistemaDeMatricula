package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MatriculaRepositorio {
    private final Path caminhoArquivo;

    public MatriculaRepositorio(String nomeArquivo) {
        this.caminhoArquivo = Path.of(nomeArquivo);
    }

    public void salvarMatricula(Aluno aluno) throws IOException {
        List<String> linhas = new ArrayList<>();
        if (Files.exists(caminhoArquivo)) {
            linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(aluno.getEmail()).append(";");
        
        // Disciplinas obrigatórias
        for (Disciplina disc : aluno.getMatricula().getDisciplinasObrigatorias()) {
            sb.append("O:").append(disc.getNome()).append(",");
        }
        
        // Disciplinas optativas
        for (Disciplina disc : aluno.getMatricula().getDisciplinasOptativas()) {
            sb.append("P:").append(disc.getNome()).append(",");
        }
        
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setLength(sb.length() - 1);
        }

        linhas.add(sb.toString());
        Files.write(caminhoArquivo, linhas, StandardCharsets.UTF_8);
    }

    public static Matricula procurarMatricula(String emailAluno) throws IOException {
        MatriculaRepositorio repo = new MatriculaRepositorio("matriculas.txt");
        Path caminho = Path.of("matriculas.txt");
        
        if (!Files.exists(caminho)) {
            return new Matricula();
        }

        List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);
        for (String linha : linhas) {
            String[] partes = linha.split(";");
            if (partes[0].equals(emailAluno)) {
                Matricula matricula = new Matricula();
                DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");
                
                if (partes.length > 1) {
                    String[] disciplinas = partes[1].split(",");
                    for (String disc : disciplinas) {
                        if (disc.startsWith("O:")) {
                            String nomeDisc = disc.substring(2);
                            Disciplina disciplina = discRepo.procurarMateria(nomeDisc);
                            if (disciplina != null) {
                                matricula.getDisciplinasObrigatorias().add(disciplina);
                            }
                        } else if (disc.startsWith("P:")) {
                            String nomeDisc = disc.substring(2);
                            Disciplina disciplina = discRepo.procurarMateria(nomeDisc);
                            if (disciplina != null) {
                                matricula.getDisciplinasOptativas().add(disciplina);
                            }
                        }
                    }
                }
                
                return matricula;
            }
        }
        
        return new Matricula();
    }

}