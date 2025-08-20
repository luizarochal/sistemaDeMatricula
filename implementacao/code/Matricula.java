package sistemaDeMatricula.implementacao.code;

import java.time.LocalDate;

public class Matricula {

    private static final int MINOBRIGATORIO = 4;
    private static final int MAXOPTATIVAS = 2;
    private LocalDate periodoMatricula;

    public Matricula() {
    }

    public void cadastrarDisciplinaObrigatoria(Disciplina disciplinaObrigatoria) {

    }

    public void cadastrarDisciplinaOptativa(Disciplina disciplinaOptativa) {

    }

    public void definirPeriodoMatricula(LocalDate periodoMatricula) {
        this.periodoMatricula = periodoMatricula;
    }

    public LocalDate getPeriodoMatricula() {
        return periodoMatricula;
    }

}