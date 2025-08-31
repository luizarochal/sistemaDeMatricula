package sistemaDeMatricula.implementacao.code;

import java.util.List;

public class Disciplina {

    private List<Aluno> alunos;
    private Professor professor;
    private static final int CAPACIDADE = 60;
    private boolean ativa;
    private Curso curso;
    private boolean eObrigatorio;

    public Disciplina(List<Aluno> alunos, Professor professor, boolean ativa, Curso curso, boolean eObrigatorio) {
        this.alunos = alunos;
        this.professor = professor;
        this.ativa = ativa;
        this.curso = curso;
        this.eObrigatorio = eObrigatorio;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public boolean iseObrigatorio() {
        return eObrigatorio;
    }

    public void seteObrigatorio(boolean eObrigatorio) {
        this.eObrigatorio = eObrigatorio;
    }

    public String visualizarAlunos() {
        StringBuilder sb = new StringBuilder();
        for (Aluno aluno : alunos) {
            sb.append(aluno.getNome()).append(" - ").append(aluno.getEmail()).append("\n");
        }
        return sb.toString();
    }

    public boolean verificarCapacidade() {
        return alunos.size() < CAPACIDADE;
    }

    public boolean estaAtiva() {
        return alunos.size() >= 3;
    }

}