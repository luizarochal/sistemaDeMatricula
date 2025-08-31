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

    public Matricula() {
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

        disciplinasObrigatorias.add(disciplina);
        disciplina.getAlunos().add(aluno);

        // Atualizar status da disciplina
        if (disciplina.getAlunos().size() >= 3) {
            disciplina.setAtiva(true);
        }

        // Persistir mudanças
        try {
            DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");
            discRepo.salvar(Collections.singletonList(disciplina));
        } catch (IOException e) {
            e.printStackTrace();
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

        disciplinasOptativas.add(disciplina);
        disciplina.getAlunos().add(aluno);

        // Verificar se disciplina atingiu mínimo de alunos
        if (disciplina.getAlunos().size() >= 3) {
            disciplina.setAtiva(true);
        }
    }

    public void cancelarDisciplina(Disciplina disciplina, Aluno aluno) {
        validarPeriodoMatricula();

        if (disciplinasObrigatorias.contains(disciplina)) {
            if (disciplinasObrigatorias.size() <= MIN_OBRIGATORIAS) {
                throw new IllegalStateException(
                        "Mínimo de " + MIN_OBRIGATORIAS + " disciplinas obrigatórias necessário");
            }
            disciplinasObrigatorias.remove(disciplina);
            disciplina.getAlunos().remove(aluno);
        } else if (disciplinasOptativas.contains(disciplina)) {
            disciplinasOptativas.remove(disciplina);
            disciplina.getAlunos().remove(aluno);
        } else {
            throw new IllegalArgumentException("Disciplina não encontrada na matrícula");
        }

        // Verificar se disciplina ainda está ativa
        if (disciplina.getAlunos().size() < 3) {
            disciplina.setAtiva(false);
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
        return disciplinasObrigatorias;
    }

    public List<Disciplina> getDisciplinasOptativas() {
        return disciplinasOptativas;
    }
}