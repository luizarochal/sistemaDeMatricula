package sistemaDeMatricula.implementacao.code;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Curriculo {
    private String nome;
    private LocalDate ano;
    private List<Curso> cursos;
    private int semestre;

   
    public Curriculo(String nome, LocalDate ano, List<Curso> cursos, int semestre) {
        this.nome = nome;
        this.ano = ano;
        this.cursos = cursos;
        this.semestre = semestre;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getAno() {
        return ano;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAno(LocalDate ano) {
        this.ano = ano;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    /*Curriculo curriculo = new Curriculo("Engenharia de Software", LocalDate.of(2024, 1, 1), cursos, 8);*/
}
