package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.util.List;

public class ProfessorService {
    private ProfessorRepositorio professorRepo;
    private DisciplinaRepositorio disciplinaRepo;

    public ProfessorService() {
        this.professorRepo = new ProfessorRepositorio("usuarios.txt");
        this.disciplinaRepo = new DisciplinaRepositorio("disciplina.txt");
    }

    public void vincularProfessorDisciplina(String emailProfessor, String nomeDisciplina) throws IOException {
        Professor professor = professorRepo.procurarProfessor(emailProfessor);
        Disciplina disciplina = disciplinaRepo.procurarMateria(nomeDisciplina);
        
        if (professor == null) {
            throw new IllegalArgumentException("Professor não encontrado: " + emailProfessor);
        }
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não encontrada: " + nomeDisciplina);
        }
        
        professor.setDisciplina(disciplina);
        disciplina.setProfessor(professor);
        
        // Atualizar no repositório
        disciplinaRepo.vincularProfessorADisciplina(emailProfessor, nomeDisciplina);
    }

    public List<Disciplina> listarDisciplinasSemProfessor() throws IOException {
        List<Disciplina> disciplinas = disciplinaRepo.carregar();
        disciplinas.removeIf(d -> d.getProfessor() != null);
        return disciplinas;
    }
}