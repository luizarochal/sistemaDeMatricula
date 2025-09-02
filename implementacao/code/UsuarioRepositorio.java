package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class UsuarioRepositorio {

    private final Path caminhoArquivo;

    public UsuarioRepositorio(String nomeArquivo) {
        this.caminhoArquivo = Paths.get(nomeArquivo);
    }

    public List<Usuario> carregar() throws IOException {
        if (!Files.exists(caminhoArquivo)) {
            return new ArrayList<>();
        }

        List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
        List<Usuario> usuarios = new ArrayList<>();

        for (String linha : linhas) {
            if (!linha.isBlank()) {
                usuarios.add(Usuario.criarDoTexto(linha));
            }
        }
        return usuarios;
    }

    private void salvar(List<Usuario> usuarios) throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Usuario u : usuarios) {
            linhas.add(u.gerarDadosTexto());
        }
        Files.write(caminhoArquivo, linhas, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void adicionar(Usuario usuario) throws IOException {
        List<Usuario> usuarios = carregar();

        try {
            Aluno aluno = (Aluno) usuario;
            if (aluno.getCurso() != null) {
                CursoRepositorio cursoRepo = new CursoRepositorio("curso.txt");
                List<Curso> cursos = cursoRepo.carregar();
                if (!cursos.contains(aluno.getCurso())) {
                    cursos.add(aluno.getCurso());
                    cursoRepo.salvar(cursos);
                }
            }
        } catch (ClassCastException e) {

        }

        usuarios.add(usuario);
        salvar(usuarios);
    }

    public boolean removerPorEmail(String email) throws IOException {
        List<Usuario> usuarios = carregar();
        boolean removido = usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        if (removido) {
            salvar(usuarios);
        }
        return removido;
    }

    public boolean atualizar(String emailAntigo, Usuario usuarioAtualizado) throws IOException {
        List<Usuario> usuarios = carregar();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equalsIgnoreCase(emailAntigo)) {
                usuarios.set(i, usuarioAtualizado);
                salvar(usuarios);
                return true;
            }
        }
        return false;
    }

    public boolean autenticar(String email, String senha) throws IOException {
        List<Usuario> usuarios = carregar();
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    public static Aluno procurarAlunoPorEmail(String emailAluno) {
        try {
            UsuarioRepositorio usuarioRepo = new UsuarioRepositorio("usuarios.txt");
            List<Usuario> usuarios = usuarioRepo.carregar();

            for (Usuario u : usuarios) {
                if (u instanceof Aluno && u.getEmail().equalsIgnoreCase(emailAluno)) {
                    return (Aluno) u;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}