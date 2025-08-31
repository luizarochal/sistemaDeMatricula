package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.util.List;

public class DebugService {

    public static void verificarEstadoSistema() {
        try {
            System.out.println("\n=== DEBUG DO SISTEMA ===");

            // Verificar usuários
            UsuarioRepositorio usuarioRepo = new UsuarioRepositorio("usuarios.txt");
            List<Usuario> usuarios = usuarioRepo.carregar();
            System.out.println("Total de usuários: " + usuarios.size());

            for (Usuario usuario : usuarios) {
                try {
                    Professor prof = (Professor) usuario;
                    System.out.println("Professor: " + prof.getNome() +
                            " - Disciplina: " +
                            (prof.getDisciplina() != null ? prof.getDisciplina().getNome() : "Nenhuma"));
                } catch (ClassCastException e) {
                    // Não é professor, ignora
                }
            }

            // Verificar disciplinas
            DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");
            List<Disciplina> disciplinas = discRepo.carregar();
            System.out.println("Total de disciplinas: " + disciplinas.size());

            for (Disciplina disc : disciplinas) {
                System.out.println("Disciplina: " + disc.getNome() +
                        " - Professor: " +
                        (disc.getProfessor() != null ? disc.getProfessor().getNome() : "Nenhum"));
            }

            System.out.println("=== FIM DEBUG ===");

        } catch (IOException e) {
            System.out.println("Erro no debug: " + e.getMessage());
        }
    }
}