package sistemaDeMatricula.implementacao.code;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DisciplinaRepositorio {

    private final Path caminhoArquivo;

    public DisciplinaRepositorio(String nomeArquivo) {
        this.caminhoArquivo = Path.of(nomeArquivo);
    }

    public List<Disciplina> carregar() throws IOException {
        if (!Files.exists(caminhoArquivo)) {
            return new ArrayList<>();
        }

        List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
        List<Disciplina> disciplinas = new ArrayList<>();

        for (String linha : linhas) {
            if (!linha.isBlank()) {
                disciplinas.add(Disciplina.criarDoTexto(linha));
            }
        }
        return disciplinas;
    }

    public void salvar(List<Disciplina> disciplinas) throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Disciplina d : disciplinas) {
            linhas.add(d.gerarDadosTexto());
        }
        Files.write(caminhoArquivo, linhas, StandardCharsets.UTF_8);
    }

    public Disciplina procurarMateria(String nomeDisciplina) throws IOException {
        for (Disciplina d : carregar()) {
            if (d.getNome().equalsIgnoreCase(nomeDisciplina)) {
                return d;
            }
        }
        return null;
    }

    public Disciplina procurarMateriaAluno(String emailAluno) throws IOException {
        for (Disciplina d : carregar()) {
            for (Aluno a : d.getAlunos()) {
                if (a.getEmail().equalsIgnoreCase(emailAluno)) {
                    return d;
                }
            }
        }
        return null;
    }

    public List<Aluno> procurarAlunosDisciplina(String nomeDisciplina) throws IOException {
        List<Aluno> alunosEncontrados = new ArrayList<>();
        for (Disciplina d : carregar()) {
            if (d.getNome().equalsIgnoreCase(nomeDisciplina)) {
                alunosEncontrados.addAll(d.getAlunos());
            }
        }
        return alunosEncontrados;
    }

    public Disciplina procurarDisciplinaPorProfessor(String emailProfessor) {
        try {
            List<Disciplina> disciplinas = carregar();
            for (Disciplina disciplina : disciplinas) {
                if (disciplina.getProfessor() != null &&
                        disciplina.getProfessor().getEmail().equalsIgnoreCase(emailProfessor)) {
                    return disciplina;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void vincularProfessorADisciplina(String emailProfessor, String nomeDisciplina) throws IOException {
        List<Disciplina> disciplinas = carregar();
        ProfessorRepositorio profRepo = new ProfessorRepositorio("usuarios.txt");
        Professor professor = profRepo.procurarProfessor(emailProfessor);

        boolean encontrou = false;

        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getNome().equalsIgnoreCase(nomeDisciplina)) {
                disciplina.setProfessor(professor);
                if (professor != null) {
                    professor.setDisciplina(disciplina);
                    // Atualizar o professor no repositório
                    profRepo.atualizarProfessor(professor);
                }
                encontrou = true;
                break;
            }
        }

        if (encontrou) {
            salvar(disciplinas);
            System.out.println("Professor " + professor.getNome() + " vinculado à disciplina " + nomeDisciplina);
        } else {
            System.out.println("Disciplina não encontrada: " + nomeDisciplina);
        }
    }
}
