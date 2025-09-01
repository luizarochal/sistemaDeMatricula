package sistemaDeMatricula.implementacao.code;

import java.io.IOException;

public abstract class Usuario {
    private String nome;
    private String email;
    private String senha;

    public Usuario(String nome, String email, String senha) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido!");
        }
        if (senha.length() < 5) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 5 caracteres!");
        }
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }

    public void setNome(String nome) { this.nome = nome; }

    public void setEmail(String email) {
        if (!email.contains("@"))
            throw new IllegalArgumentException("Email inválido!");
        this.email = email;
    }

    public void setSenha(String senha) {
        if (senha.length() < 5)
            throw new IllegalArgumentException("Senha deve ter pelo menos 5 caracteres!");
        this.senha = senha;
    }

    public abstract int getTipo();

    public String gerarDadosTexto() {
        return String.format("%d;%s;%s;%s", getTipo(), nome, email, senha);
    }

    public static Usuario criarDoTexto(String linha) throws IOException {
    String[] dados = linha.split(";");
    if (dados.length != 4) {
        throw new IllegalArgumentException("Linha inválida: " + linha);
    }

    int tipo = Integer.parseInt(dados[0]);
    String nome = dados[1];
    String email = dados[2];
    String senha = dados[3];

    switch (tipo) {
        case 1: return new Secretaria(nome, email, senha);
        case 2: return new Professor(nome, email, senha, null); // Disciplina será carregada depois
        case 3: 
            // Aluno sem dependências iniciais
            Matricula matricula = new Matricula();
            matricula = MatriculaRepositorio.procurarMatricula(email);
            Curso curso = null; // Será carregado posteriormente se necessário
            return new Aluno(nome, email, senha, matricula, curso);
        default:
            throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
    }
}
}