package sistemaDeMatricula.implementacao.code;

import java.util.List;

public class Aluno extends Usuario {

    private Matricula matricula;
    private List<Disciplina> disciplinas;
    private Curso curso;

    public Aluno(String nome, String email, String senha, Matricula matricula, List<Disciplina> disciplinas,
            Curso curso) {
        super(nome, email, senha);
        this.matricula = matricula;
        this.disciplinas = disciplinas;
        this.curso = curso;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void matricularDisciplina() {
        // Lógica para matricular o aluno em uma disciplina
    }

    public void cancelarDisciplina(Disciplina disciplina) {
        // Lógica para cancelar a matrícula em uma disciplina
    }

    public void visualizarDisciplina() {
        // Lógica para visualizar as disciplinas do aluno
    }

    public void atualizarDisciplina() {
        // Lógica para atualizar as informações do aluno
    }

}
