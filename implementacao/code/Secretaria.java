package sistema-de-matricula.implementacao.code;

import sistema-de-matricula.implementacao.code.Usuario;

public class Secretaria extends Usuario {

    private Curriculo curriculo;

    public Secretaria(Curriculo curriculo) {
        this.curriculo = curriculo;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }

    public void gerarCurriculo() {
        // Lógica para criar o currículo
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
