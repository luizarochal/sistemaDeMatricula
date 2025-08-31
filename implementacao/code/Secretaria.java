package sistemaDeMatricula.implementacao.code;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Secretaria extends Usuario {

    public Secretaria(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public int getTipo() {
        return 1;
    }

    public void cadastrarInfoAluno(Aluno aluno) throws IOException {
        
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio("usuarios.txt");
        usuarioRepo.adicionar(aluno);

        MatriculaRepositorio matriculaRepo = new MatriculaRepositorio("matriculas.txt");
        matriculaRepo.salvarMatricula(aluno);

        if (aluno.getCurso() != null) {
            CursoRepositorio cursoRepo = new CursoRepositorio("curso.txt");
            List<Curso> cursos = cursoRepo.carregar();
            if (!cursos.contains(aluno.getCurso())) {
                cursos.add(aluno.getCurso());
                cursoRepo.salvar(cursos);
            }
        }
    }

    public void cadastrarInfoProf(Professor professor) throws IOException {
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio("usuarios.txt");
        usuarioRepo.adicionar(professor);

        if (professor.getDisciplina() != null) {
            DisciplinaRepositorio discRepo = new DisciplinaRepositorio("disciplina.txt");
            discRepo.vincularProfessorADisciplina(professor.getEmail(), professor.getDisciplina().getNome());
        }
    }

    public void cadastrarInfoDisciplina(Disciplina disciplina) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("disciplinas.txt", true))) {

            int qtdAlunos = disciplina.getAlunos() != null ? disciplina.getAlunos().size() : 0;

            String nomeProfessor = (disciplina.getProfessor() != null)
                    ? disciplina.getProfessor().getNome()
                    : "Sem professor";

            String nomeCurso = (disciplina.getCurso() != null)
                    ? disciplina.getCurso().getNome()
                    : "Sem curso";

            bw.write(
                    nomeCurso + ";" +
                            nomeProfessor + ";" +
                            qtdAlunos + "/" + 60 + ";" +
                            (disciplina.isAtiva() ? "Ativa" : "Inativa") + ";" +
                            (disciplina.iseObrigatorio() ? "Obrigatória" : "Optativa"));
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplina: " + e.getMessage());
        }
    }

    public void gerenciarCurriculo(Curriculo curriculo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("curriculos.txt", true))) {

            String nome = curriculo.getNome();
            String ano = (curriculo.getAno() != null) ? curriculo.getAno().toString() : "Sem ano";
            int semestre = curriculo.getSemestre();

            StringBuilder cursosStr = new StringBuilder();
            if (curriculo.getCursos() != null && !curriculo.getCursos().isEmpty()) {
                for (Curso c : curriculo.getCursos()) {
                    cursosStr.append(c.getNome()).append(", ");
                }
                cursosStr.setLength(cursosStr.length() - 2);
            } else {
                cursosStr.append("Nenhum curso");
            }

            bw.write(nome + ";" + ano + ";" + semestre + ";" + cursosStr);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Erro ao salvar currículo: " + e.getMessage());
        }
    }

    public List<String> carregarArquivo(String nomeArquivo) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
        return linhas;
    }

}
