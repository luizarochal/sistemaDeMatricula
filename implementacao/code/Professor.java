package sistema-de-matricula.implementação.code;

import sistema_de_matricula.implementacao.code.Usuario;

public class Professor extends Usuario {

    private Disciplina disciplina;

    public Professor(String nome, String email, String senha, Disciplina disciplina) {
        super(nome, email, senha);
        this.disciplina = disciplina;
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
