package sistemaDeMatricula.implementacao.code;

public class Professor extends Usuario {

    private Disciplina disciplina;

    public Professor(String nome, String email, String senha, Disciplina disciplina) {
        super(nome, email, senha);
        this.disciplina = disciplina;
    }

    @Override
    public int getTipo() {
        return 2;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void visualizarDisciplinas() {
        // Lógica para visualizar as disciplinas atribuídas ao professor
    }

}
