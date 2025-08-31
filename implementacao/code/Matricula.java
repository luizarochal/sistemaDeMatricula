package sistemaDeMatricula.implementacao.code;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Matricula {

    private static final int MINOBRIGATORIO = 4;
    private static final int MAXOPTATIVAS = 2;
    private LocalDate periodoMatricula;

    private List<Disciplina> disciplinasObrigatorias;
    private List<Disciplina> disciplinasOptativas;

    public Matricula() {
        this.disciplinasObrigatorias = new ArrayList<>();
        this.disciplinasOptativas = new ArrayList<>();
    }

    public void cadastrarDisciplinaObrigatoria(Disciplina disciplinaObrigatoria, Aluno aluno) {
        if (!disciplinaObrigatoria.iseObrigatorio()) {
            throw new IllegalArgumentException("Essa disciplina não é obrigatória!");
        }
        if (!disciplinaObrigatoria.verificarCapacidade()) {
            throw new IllegalStateException("Disciplina cheia! Não é possível matricular.");
        }
        disciplinasObrigatorias.add(disciplinaObrigatoria);
        disciplinaObrigatoria.getAlunos().add(aluno);
    }

    public void cadastrarDisciplinaOptativa(Disciplina disciplinaOptativa, Aluno aluno) {
        if (disciplinaOptativa.iseObrigatorio()) {
            throw new IllegalArgumentException("Essa disciplina é obrigatória, não pode ser cadastrada como optativa!");
        }
        if (disciplinasOptativas.size() >= MAXOPTATIVAS) {
            throw new IllegalStateException("Número máximo de disciplinas optativas atingido (" + MAXOPTATIVAS + ").");
        }
        if (!disciplinaOptativa.verificarCapacidade()) {
            throw new IllegalStateException("Disciplina cheia! Não é possível matricular.");
        }
        disciplinasOptativas.add(disciplinaOptativa);
        disciplinaOptativa.getAlunos().add(aluno);
    }

    public boolean validarMatricula() {
        return disciplinasObrigatorias.size() >= MINOBRIGATORIO;
    }

    public void definirPeriodoMatricula(LocalDate periodoMatricula) {
        this.periodoMatricula = periodoMatricula;
    }

    public LocalDate getPeriodoMatricula() {
        return periodoMatricula;
    }

    public List<Disciplina> getDisciplinasObrigatorias() {
        return disciplinasObrigatorias;
    }

    public List<Disciplina> getDisciplinasOptativas() {
        return disciplinasOptativas;
    }
}
