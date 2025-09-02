package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatriculaRepositorio {
    private final Path caminhoArquivo;

    public MatriculaRepositorio(String nomeArquivo) {
        this.caminhoArquivo = Path.of(nomeArquivo);
    }

    public List<Matricula> carregar() throws IOException {
        if (!Files.exists(caminhoArquivo)) {
            return new ArrayList<>();
        }

        List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
        Map<String, Matricula> matriculasMap = new HashMap<>(); // Usar Map para agrupar por email

        for (String linha : linhas) {
            if (linha.isBlank())
                continue;

            String[] partes = linha.split(";");
            if (partes.length < 2)
                continue;

            String emailAluno = partes[0];

            // Obter ou criar a matrícula para este aluno
            Matricula matricula = matriculasMap.getOrDefault(emailAluno, new Matricula(emailAluno));

            DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");

            // Processar disciplinas
            if (partes.length > 1) {
                String[] disciplinas = partes[1].split(",");
                for (String disc : disciplinas) {
                    if (disc.startsWith("O:")) {
                        String nomeDisc = disc.substring(2);
                        Disciplina disciplina = discRepo.procurarMateria(nomeDisc);
                        if (disciplina != null && !matricula.getDisciplinasObrigatorias().contains(disciplina)) {
                            matricula.getDisciplinasObrigatorias().add(disciplina);
                        }
                    } else if (disc.startsWith("P:")) {
                        String nomeDisc = disc.substring(2);
                        Disciplina disciplina = discRepo.procurarMateria(nomeDisc);
                        if (disciplina != null && !matricula.getDisciplinasOptativas().contains(disciplina)) {
                            matricula.getDisciplinasOptativas().add(disciplina);
                        }
                    }
                }
            }

            matriculasMap.put(emailAluno, matricula);
        }

        return new ArrayList<>(matriculasMap.values());
    }

    public void salvarMatricula(Aluno aluno) throws IOException {
        List<String> linhas = new ArrayList<>();
        if (Files.exists(caminhoArquivo)) {
            linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
        }

        // Remover matrícula existente do aluno (se houver)
        String emailAluno = aluno.getEmail();
        linhas.removeIf(linha -> linha.startsWith(emailAluno + ";"));

        // Criar nova linha para este aluno
        StringBuilder sb = new StringBuilder();
        sb.append(emailAluno).append(";");

        // Disciplinas obrigatórias
        for (Disciplina disc : aluno.getMatricula().getDisciplinasObrigatorias()) {
            sb.append("O:").append(disc.getNome()).append(",");
        }

        // Disciplinas optativas
        for (Disciplina disc : aluno.getMatricula().getDisciplinasOptativas()) {
            sb.append("P:").append(disc.getNome()).append(",");
        }

        // Remover última vírgula se existir
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setLength(sb.length() - 1);
        }

        linhas.add(sb.toString());
        Files.write(caminhoArquivo, linhas, StandardCharsets.UTF_8);
    }

    public Matricula carregarMatriculaPorEmail(String emailAluno) throws IOException {
        List<Matricula> todasMatriculas = carregar();

        return todasMatriculas.stream()
                .filter(m -> m.getEmailAluno() != null &&
                        m.getEmailAluno().equalsIgnoreCase(emailAluno))
                .findFirst()
                .orElse(new Matricula(emailAluno)); // Retorna nova se não encontrada
    }

    public Matricula procurarMatricula(String emailAluno) throws IOException {
        List<Matricula> todasMatriculas = carregar();

        for (Matricula matricula : todasMatriculas) {
            if (matricula.getEmailAluno() != null &&
                    matricula.getEmailAluno().equalsIgnoreCase(emailAluno)) {
                return matricula;
            }
        }

        // Se não encontrou, retorna uma nova matrícula vazia para este aluno
        return new Matricula(emailAluno);
    }
}