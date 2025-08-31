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
        DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");
        disciplina = discRepo.procurarDisciplinaPorProfessor(this.getEmail());
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        if (disciplina != null && disciplina.getProfessor() != this) {
            disciplina.setProfessor(this);
        }
    }

    public void visualizarDisciplina() {
        if (disciplina != null) {
            System.out.println("Professor " + getNome() + " ministra: " + disciplina.getNome());
            System.out.println("Curso: " + disciplina.getCurso().getNome());
            System.out.println("Alunos matriculados: " + disciplina.getAlunos().size());
        } else {
            System.out.println("Professor " + getNome() + " não está vinculado a nenhuma disciplina");
        }
    }

    public String visualizarAlunosDaDisciplina() {
        if (disciplina != null) {
            return disciplina.visualizarAlunos();
        }
        return "Nenhuma disciplina vinculada";
    }

    @Override
    public String gerarDadosTexto() {
        return getTipo() + ";" + getNome() + ";" + getEmail() + ";" + getSenha();
    }
    
    public static Professor criarDoTexto(String linha) {
        String[] dados = linha.split(";");
        if (dados.length != 4) {
            throw new IllegalArgumentException("Linha inválida: " + linha);
        }
        return new Professor(dados[1], dados[2], dados[3], null);
    }

    @Override
public String toString() {
    return "Professor{" +
            "nome='" + getNome() + '\'' +
            ", email='" + getEmail() + '\'' +
            ", disciplina=" + (disciplina != null ? disciplina.getNome() : "null") +
            '}';
}

public void visualizarDisciplinaCompleta() {
    if (disciplina != null) {
        System.out.println("=== DISCIPLINA DO PROFESSOR ===");
        System.out.println("Nome: " + disciplina.getNome());
        System.out.println("Curso: " + (disciplina.getCurso() != null ? disciplina.getCurso().getNome() : "N/A"));
        System.out.println("Obrigatória: " + (disciplina.iseObrigatorio() ? "Sim" : "Não"));
        System.out.println("Ativa: " + (disciplina.isAtiva() ? "Sim" : "Não"));
        System.out.println("Alunos matriculados: " + disciplina.getAlunos().size());
        System.out.println("Capacidade: " + disciplina.getAlunos().size() + "/60");
    } else {
        System.out.println("Nenhuma disciplina vinculada ao professor.");
    }
}
}