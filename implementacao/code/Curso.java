package sistemaDeMatricula.implementacao.code;

import java.util.List;

public class Curso {
    private String nome;
    private List<Disciplina> disciplinas;
    private int numCreditos;

    public Curso(String nome, int numCreditos, List<Disciplina> disciplinas) {
        this.nome = nome;
        this.disciplinas = disciplinas;
        this.numCreditos = numCreditos;
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public int calcularNumCreditos() {
        return numCreditos;
    }

    public String getNome() {
        return nome;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public int getNumCreditos() {
        return numCreditos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}