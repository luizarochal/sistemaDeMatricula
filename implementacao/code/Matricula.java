package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matricula {
    private static final int MIN_OBRIGATORIAS = 4;
    private static final int MAX_OPTATIVAS = 2;
    private LocalDate periodoMatricula;
    private List<Disciplina> disciplinasObrigatorias;
    private List<Disciplina> disciplinasOptativas;
    private String emailAluno;

    public Matricula(String emailAluno) {
        this.emailAluno = emailAluno;
        this.disciplinasObrigatorias = new ArrayList<>();
        this.disciplinasOptativas = new ArrayList<>();
    }

    public void cadastrarDisciplinaObrigatoria(Disciplina disciplina, Aluno aluno) {
        validarPeriodoMatricula();

        if (!disciplina.iseObrigatorio()) {
            throw new IllegalArgumentException("Disciplina não é obrigatória!");
        }

        if (disciplinasObrigatorias.size() >= MIN_OBRIGATORIAS) {
            throw new IllegalStateException("Limite de " + MIN_OBRIGATORIAS + " disciplinas obrigatórias atingido");
        }

        if (!disciplina.verificarCapacidade()) {
            throw new IllegalStateException("Disciplina cheia! Capacidade máxima: 60 alunos");
        }

        // Verificar se aluno já está matriculado nesta disciplina
        if (disciplinasObrigatorias.contains(disciplina) || disciplinasOptativas.contains(disciplina)) {
            throw new IllegalStateException("Aluno já está matriculado nesta disciplina");
        }

        disciplinasObrigatorias.add(disciplina);

        // Adicionar aluno à disciplina (se não estiver já)
        if (!disciplina.getAlunos().contains(aluno)) {
            disciplina.getAlunos().add(aluno);
        }

        // Atualizar status da disciplina
        if (disciplina.getAlunos().size() >= 3) {
            disciplina.setAtiva(true);
        }

        // Persistir mudanças
        try {
            DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");
            List<Disciplina> disciplinas = discRepo.carregar();

            // Atualizar a disciplina específica na lista
            for (int i = 0; i < disciplinas.size(); i++) {
                if (disciplinas.get(i).getNome().equals(disciplina.getNome())) {
                    disciplinas.set(i, disciplina);
                    break;
                }
            }

            discRepo.salvar(disciplinas);

            // Atualizar matrícula do aluno
            MatriculaRepositorio repo = new MatriculaRepositorio("matriculas.txt");
            repo.salvarMatricula(aluno);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar matrícula: " + e.getMessage());
        }
    }

    public void cadastrarDisciplinaOptativa(Disciplina disciplina, Aluno aluno) {
        validarPeriodoMatricula();

        if (disciplina.iseObrigatorio()) {
            throw new IllegalArgumentException("Disciplina é obrigatória!");
        }

        if (disciplinasOptativas.size() >= MAX_OPTATIVAS) {
            throw new IllegalStateException("Limite de " + MAX_OPTATIVAS + " disciplinas optativas atingido");
        }

        if (!disciplina.verificarCapacidade()) {
            throw new IllegalStateException("Disciplina cheia! Capacidade máxima: 60 alunos");
        }

        // Verificar se aluno já está matriculado nesta disciplina
        if (disciplinasObrigatorias.contains(disciplina) || disciplinasOptativas.contains(disciplina)) {
            throw new IllegalStateException("Aluno já está matriculado nesta disciplina");
        }

        disciplinasOptativas.add(disciplina);

        // Adicionar aluno à disciplina (se não estiver já)
        if (!disciplina.getAlunos().contains(aluno)) {
            disciplina.getAlunos().add(aluno);
        }

        // Verificar se disciplina atingiu mínimo de alunos
        if (disciplina.getAlunos().size() >= 3) {
            disciplina.setAtiva(true);
        }

        // Persistir mudanças
        try {
            DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");
            List<Disciplina> disciplinas = discRepo.carregar();

            // Atualizar a disciplina específica na lista
            for (int i = 0; i < disciplinas.size(); i++) {
                if (disciplinas.get(i).getNome().equals(disciplina.getNome())) {
                    disciplinas.set(i, disciplina);
                    break;
                }
            }

            discRepo.salvar(disciplinas);

            // Atualizar matrícula do aluno
            MatriculaRepositorio repo = new MatriculaRepositorio("matriculas.txt");
            repo.salvarMatricula(aluno);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar matrícula: " + e.getMessage());
        }
    }

    public void cancelarDisciplina(Disciplina disciplina, Aluno aluno) {
        validarPeriodoMatricula();

        boolean removida = false;

        if (disciplinasObrigatorias.contains(disciplina)) {
            if (disciplinasObrigatorias.size() <= MIN_OBRIGATORIAS) {
                throw new IllegalStateException(
                        "Mínimo de " + MIN_OBRIGATORIAS + " disciplinas obrigatórias necessário");
            }
            disciplinasObrigatorias.remove(disciplina);
            removida = true;
        } else if (disciplinasOptativas.contains(disciplina)) {
            disciplinasOptativas.remove(disciplina);
            removida = true;
        } else {
            throw new IllegalArgumentException("Disciplina não encontrada na matrícula");
        }

        if (removida) {
            // Remover aluno da disciplina
            disciplina.getAlunos().remove(aluno);

            // Verificar se disciplina ainda está ativa
            if (disciplina.getAlunos().size() < 3) {
                disciplina.setAtiva(false);
            }

            // Persistir mudanças
            try {
                DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");
                List<Disciplina> disciplinas = discRepo.carregar();

                // Atualizar a disciplina específica na lista
                for (int i = 0; i < disciplinas.size(); i++) {
                    if (disciplinas.get(i).getNome().equals(disciplina.getNome())) {
                        disciplinas.set(i, disciplina);
                        break;
                    }
                }

                discRepo.salvar(disciplinas);

                // Atualizar matrícula do aluno
                MatriculaRepositorio repo = new MatriculaRepositorio("matriculas.txt");
                repo.salvarMatricula(aluno);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao cancelar matrícula: " + e.getMessage());
            }
        }
    }

    private void validarPeriodoMatricula() {
        
    }

    public boolean validarMatricula() {
        return disciplinasObrigatorias.size() >= MIN_OBRIGATORIAS;
    }

    public void definirPeriodoMatricula(LocalDate periodoMatricula) {
        this.periodoMatricula = periodoMatricula;
    }

    public LocalDate getPeriodoMatricula() {
        return periodoMatricula;
    }

    public List<Disciplina> getDisciplinasObrigatorias() {
        return new ArrayList<>(disciplinasObrigatorias); // Retorna cópia para evitar modificações externas
    }

    public List<Disciplina> getDisciplinasOptativas() {
        return new ArrayList<>(disciplinasOptativas); // Retorna cópia para evitar modificações externas
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    // Método útil para verificar se aluno está matriculado em uma disciplina
    public boolean contemDisciplina(Disciplina disciplina) {
        return disciplinasObrigatorias.contains(disciplina) || disciplinasOptativas.contains(disciplina);
    }

    // Método para obter todas as disciplinas (obrigatórias + optativas)
    public List<Disciplina> getTodasDisciplinas() {
        List<Disciplina> todas = new ArrayList<>();
        todas.addAll(disciplinasObrigatorias);
        todas.addAll(disciplinasOptativas);
        return todas;
    }

    // Método para obter quantidade total de disciplinas
    public int getTotalDisciplinas() {
        return disciplinasObrigatorias.size() + disciplinasOptativas.size();
    }
}