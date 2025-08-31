package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private List<Aluno> alunos;
    private String nome;
    private Professor professor;
    private static final int CAPACIDADE = 60;
    private boolean ativa;
    private Curso curso;
    private boolean eObrigatorio;

    public Disciplina(List<Aluno> alunos, String nome, Professor professor, boolean ativa, Curso curso,
            boolean eObrigatorio) {
        this.alunos = alunos != null ? alunos : new ArrayList<>();
        this.nome = nome;
        this.professor = professor;
        this.ativa = ativa;
        this.curso = curso;
        this.eObrigatorio = eObrigatorio;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public boolean iseObrigatorio() {
        return eObrigatorio;
    }

    public void seteObrigatorio(boolean eObrigatorio) {
        this.eObrigatorio = eObrigatorio;
    }

    public String visualizarAlunos() {
        StringBuilder sb = new StringBuilder();
        if (alunos.isEmpty()) {
            return "Nenhum aluno matriculado";
        }
        for (Aluno aluno : alunos) {
            sb.append(aluno.getNome()).append(" - ").append(aluno.getEmail()).append("\n");
        }
        return sb.toString();
    }

    public boolean verificarCapacidade() {
        return alunos.size() < CAPACIDADE;
    }

    public boolean estaAtiva() {
        return alunos.size() >= 3;
    }

    public String gerarDadosTexto() {
        String professorEmail = (professor != null) ? professor.getEmail() : "SEM_PROFESSOR";
        String cursoNome = (curso != null) ? curso.getNome() : "SEM_CURSO";
        
        return this.nome + ";" +
               cursoNome + ";" +
               this.ativa + ";" +
               professorEmail + ";" +
               this.eObrigatorio + ";" +
               this.alunos.size();
    }

    public static Disciplina criarDoTexto(String linha) throws IOException {
        String[] dados = linha.split(";");
        if (dados.length < 5) {
            throw new IllegalArgumentException("Linha inválida: " + linha);
        }

        String nomeDisciplina = dados[0];
        String nomeCurso = dados[1];
        boolean ativa = Boolean.parseBoolean(dados[2]);
        String emailProfessor = dados[3];
        boolean eObrigatoria = Boolean.parseBoolean(dados[4]);

        // Buscar curso
        CursoRepositorio cursoRepo = new CursoRepositorio("curso.txt");
        Curso curso = cursoRepo.procurarCurso(nomeCurso);

        // Buscar professor (criar apenas com email para evitar dependência circular)
        Professor professor = null;
        if (!"SEM_PROFESSOR".equals(emailProfessor)) {
            professor = new Professor("", emailProfessor, "12345", null);
        }

        List<Aluno> alunos = new ArrayList<>();

        return new Disciplina(alunos, nomeDisciplina, professor, ativa, curso, eObrigatoria);
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "nome='" + nome + '\'' +
                ", professor=" + (professor != null ? professor.getNome() : "null") +
                ", curso=" + (curso != null ? curso.getNome() : "null") +
                ", ativa=" + ativa +
                ", eObrigatorio=" + eObrigatorio +
                ", alunosMatriculados=" + alunos.size() +
                '}';
    }
}