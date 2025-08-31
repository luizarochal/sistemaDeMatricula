package sistemaDeMatricula.implementacao.code;

public class Secretaria extends Usuario {

    public Secretaria(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public int getTipo() {
        return 1;
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
