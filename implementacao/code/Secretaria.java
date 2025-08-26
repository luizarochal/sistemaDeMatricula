package sistemaDeMatricula.implementacao.code;

public class Secretaria extends Usuario {

    private Curriculo curriculo;

    public Secretaria(String nome, String email, String senha, Curriculo curriculo) {
        super(nome, email, senha);
    }

    public void cadastrarInfoAluno() {
        // Lógica para cadastrar as informações do aluno
    }

    public void cadastrarInfoProf() {
        // Lógica para cadastrar as informações do professor
    }

    public void cadastrarInfoDisciplina() {
        // Lógica para cadastrar as informações da disciplina
    }

    public void gerenciarCurriculo(Curriculo curriculo){

    }

}
