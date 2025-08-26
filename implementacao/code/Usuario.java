package sistemaDeMatricula.implementacao.code;

import java.io.File;
import java.nio.charset.Charset;
import java.util.IllegalFormatWidthException;
import java.util.Scanner;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private Scanner teclado;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void cadastrar(String nomeArquivo) {
        Scanner arquivo = null;
        int numUsuarios;
        File file = new File(nomeArquivo);
        String[] vetorUsuarios;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            arquivo = new Scanner(file, Charset.forName("UTF-8"));
            numUsuarios = Integer.parseInt(arquivo.nextLine());
            String usuario = cadastrarDadosUsuario();


            for(int i = 0; i < numUsuarios; i++){

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void fazerLogin() {
        // Lógica para fazer login
    }

    public void excluirCadastro() {
        // Lógica para excluir o cadastro do usuário
    }

    public void atualizarCadastro() {
        // Lógica para atualizar as informações do usuário
    }

    public String cadastrarDadosUsuario(){
        System.out.println("Digite seu nome completo:");
        String nome = teclado.nextLine();
        System.out.println("Digite seu email:");
        String email = teclado.nextLine();
        if(!email.contains("@")){
            throw new IllegalArgumentException("O email deve ser válido!");
        }
        System.out.println("Digite sua senha (minimo 5 caracteres):");
        String senha1 = teclado.nextLine();
        if(senha1.length() < 5){
            throw new IllegalFormatWidthException( "A senha tem que ter no minimo 5 caracteres!");
        }
        System.out.println("Confirme sua senha:");
        String senha2 = teclado.nextLine();
        if (!senha1.equals(senha2)) {
            throw new IllegalArgumentException("As senhas não coincidem!");
        }

        return String.format("%s;%s;%s",nome,email,senha1);
    }

}
