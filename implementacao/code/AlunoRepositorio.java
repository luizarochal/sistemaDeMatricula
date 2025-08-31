package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepositorio {

    public static Aluno procurarAlunoPorEmail(String emailAluno) throws IOException {
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio("usuarios.txt");
        List<Usuario> usuarios = usuarioRepo.carregar();

        for (Usuario u : usuarios) {
            if (u.getEmail().equals(emailAluno)) {
                return (Aluno) u;
            }
        }
        return null;
    }

} 
