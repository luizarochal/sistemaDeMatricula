package sistemaDeMatricula.implementacao.code;

public class Secretaria extends Usuario {

    private Curriculo curriculo;

    public Secretaria(String nome, String email, String senha, Curriculo curriculo) {
        super(nome, email, senha);
        this.curriculo = curriculo;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
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

}
