package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.util.List;

public class Aluno extends Usuario {
    private Matricula matricula;
    private Curso curso;

    public Aluno(String nome, String email, String senha, Matricula matricula, Curso curso) {
        super(nome, email, senha);
        this.matricula = matricula != null ? matricula : new Matricula(email);
        this.curso = curso;
    }

    @Override
    public int getTipo() {
        return 3;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void matricularDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não pode ser nula");
        }

        if (matricula.getDisciplinasObrigatorias().size() >= 4 && disciplina.iseObrigatorio()) {
            throw new IllegalStateException("Limite de 4 disciplinas obrigatórias atingido");
        }

        if (matricula.getDisciplinasOptativas().size() >= 2 && !disciplina.iseObrigatorio()) {
            throw new IllegalStateException("Limite de 2 disciplinas optativas atingido");
        }

        if (disciplina.iseObrigatorio()) {
            matricula.cadastrarDisciplinaObrigatoria(disciplina, this);
        } else {
            matricula.cadastrarDisciplinaOptativa(disciplina, this);
        }
    }

    public void cancelarDisciplina(Disciplina disciplina) {
        matricula.cancelarDisciplina(disciplina, this);
    }

    public void visualizarDisciplinas() throws IOException{
        MatriculaRepositorio repo = new MatriculaRepositorio("matriculas.txt");
        Matricula mat = repo.carregarMatriculaPorEmail(this.getEmail());
        System.out.println("Disciplinas matriculadas:");
        for (Disciplina disciplina : mat.getDisciplinasObrigatorias()) {
            System.out.println("- " + disciplina.getNome() + " (Obrigatória)");
        }
        for (Disciplina disciplina : mat.getDisciplinasOptativas()) {
            System.out.println("- " + disciplina.getNome() + " (Optativa)");
        }
    }

    // Na classe Aluno, adicione este método:
    public void cancelarDisciplinaPorNome(String nomeDisciplina) {
        Disciplina disciplinaCancelar = null;

        // Procurar nas obrigatórias
        for (Disciplina disc : matricula.getDisciplinasObrigatorias()) {
            if (disc.getNome().trim().equalsIgnoreCase(nomeDisciplina.trim())) {
                disciplinaCancelar = disc;
                break;
            }
        }

        // Procurar nas optativas
        if (disciplinaCancelar == null) {
            for (Disciplina disc : matricula.getDisciplinasOptativas()) {
                if (disc.getNome().trim().equalsIgnoreCase(nomeDisciplina.trim())) {
                    disciplinaCancelar = disc;
                    break;
                }
            }
        }

        if (disciplinaCancelar != null) {
            cancelarDisciplina(disciplinaCancelar);
        } else {
            throw new IllegalArgumentException("Disciplina não encontrada na matrícula: " + nomeDisciplina);
        }
    }
}