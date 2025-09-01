package sistemaDeMatricula.implementacao.code;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio("usuarios.txt");
        DisciplinaRepositorio disciplinaRepo = new DisciplinaRepositorio("disciplina.txt");
        CursoRepositorio cursoRepo = new CursoRepositorio("curso.txt");

        System.out.println("=== Sistema de Matrículas ===");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        try {
            if (!usuarioRepo.autenticar(email, senha)) {
                System.out.println("Login inválido!");
                return;
            }

            Usuario usuarioLogado = null;
            for (Usuario u : usuarioRepo.carregar()) {
                if (u.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("Login bem-sucedido! Bem-vindo, " + u.getNome());
                    usuarioLogado = u;
                    break;
                }
            }

            switch (usuarioLogado.getTipo()) {
                case 1 -> menuSecretaria(sc, (Secretaria) usuarioLogado, cursoRepo, disciplinaRepo, usuarioRepo);
                case 2 -> menuProfessor(sc, (Professor) usuarioLogado);
                case 3 -> menuAluno(sc, (Aluno) usuarioLogado, disciplinaRepo);
                default -> System.out.println("Tipo de usuário inválido!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void menuSecretaria(Scanner sc, Secretaria secretaria, CursoRepositorio cursoRepo,
            DisciplinaRepositorio disciplinaRepo, UsuarioRepositorio usuarioRepo) throws IOException {
        int opcao;
        do {
            System.out.println("\n--- Menu Secretaria ---");
            System.out.println("1. Cadastrar Curso");
            System.out.println("2. Cadastrar Disciplina");
            System.out.println("3. Cadastrar Professor");
            System.out.println("4. Cadastrar Aluno");
            System.out.println("0. Sair");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome do curso: ");
                    String nomeCurso = sc.nextLine();
                    System.out.print("Número de créditos: ");
                    int creditos = Integer.parseInt(sc.nextLine());
                    Curso novoCurso = new Curso(nomeCurso, creditos, new java.util.ArrayList<>());
                    List<Curso> cursos = cursoRepo.carregar();
                    cursos.add(novoCurso);
                    cursoRepo.salvar(cursos);
                    System.out.println("Curso cadastrado!");
                }
                case 2 -> {
                    System.out.print("Nome da disciplina: ");
                    String nomeDisciplina = sc.nextLine();
                    System.out.print("Curso: ");
                    String nomeCurso = sc.nextLine();
                    Curso curso = cursoRepo.procurarCurso(nomeCurso);

                    System.out.print("É obrigatória? (s/n): ");
                    boolean obrigatoria = sc.nextLine().equalsIgnoreCase("s");

                    System.out.print("Email do professor: ");
                    String emailProf = sc.nextLine().trim();

                    // Verificar se professor existe
                    ProfessorRepositorio profRepo = new ProfessorRepositorio("usuarios.txt");
                    Professor professor = profRepo.procurarProfessor(emailProf);

                    if (professor == null && !emailProf.isEmpty()) {
                        System.out.println("Professor não encontrado! Criando disciplina sem professor.");
                    }

                    // Criar disciplina
                    Disciplina novaDisciplina = new Disciplina(
                            new ArrayList<>(),
                            nomeDisciplina,
                            professor,
                            false,
                            curso,
                            obrigatoria);

                    List<Disciplina> disciplinas = disciplinaRepo.carregar();
                    disciplinas.add(novaDisciplina);
                    disciplinaRepo.salvar(disciplinas);

                    // Vincular professor à disciplina se existir
                    if (professor != null) {
                        disciplinaRepo.vincularProfessorADisciplina(emailProf, nomeDisciplina);
                        professor.setDisciplina(novaDisciplina);
                        profRepo.atualizarProfessor(professor);
                    }

                    System.out.println("Disciplina cadastrada!");
                }
                case 3 -> {
                    System.out.print("Nome: ");
                    String nomeProf = sc.nextLine();
                    System.out.print("Email: ");
                    String emailProf = sc.nextLine();
                    System.out.print("Senha: ");
                    String senhaProf = sc.nextLine();
                    Professor professor = new Professor(nomeProf, emailProf, senhaProf, null);
                    usuarioRepo.adicionar(professor);
                    System.out.println("Professor cadastrado!");
                }
                case 4 -> {
                    System.out.print("Nome: ");
                    String nomeAluno = sc.nextLine();
                    System.out.print("Email: ");
                    String emailAluno = sc.nextLine();
                    System.out.print("Senha: ");
                    String senhaAluno = sc.nextLine();
                    System.out.print("Curso do aluno: ");
                    String nomeCursoAluno = sc.nextLine();

                    Curso cursoAluno = cursoRepo.procurarCurso(nomeCursoAluno);
                    if (cursoAluno == null) {
                        System.out.println("Curso não encontrado! Criando novo curso...");
                        cursoAluno = new Curso(nomeCursoAluno, 0, new ArrayList<>());
                        List<Curso> cursos = cursoRepo.carregar();
                        cursos.add(cursoAluno);
                        cursoRepo.salvar(cursos);
                    }

                    Matricula matricula = new Matricula();
                    matricula.definirPeriodoMatricula(LocalDate.now().plusDays(7));

                    Aluno aluno = new Aluno(nomeAluno, emailAluno, senhaAluno, matricula, cursoAluno);

                    secretaria.cadastrarInfoAluno(aluno);

                    System.out.println("Aluno cadastrado com sucesso!");
                }
            }
        } while (opcao != 0);
    }

    private static void menuAluno(Scanner sc, Aluno aluno, DisciplinaRepositorio disciplinaRepo) throws IOException {
        int opcao;
        do {
            System.out.println("\n--- Menu Aluno ---");
            System.out.println("1. Listar Disciplinas");
            System.out.println("2. Matricular-se em disciplina");
            System.out.println("3. Cancelar disciplina");
            System.out.println("4. Ver minhas disciplinas");
            System.out.println("0. Sair");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1 -> {
                    for (Disciplina d : disciplinaRepo.carregar()) {
                        System.out.println("- " + d.getNome() + " (" + d.getCurso().getNome() + ")");
                    }
                }
                case 2 -> {
                    System.out.print("Digite o nome da disciplina: ");
                    String nomeDisc = sc.nextLine();
                    Disciplina disciplina = disciplinaRepo.procurarMateria(nomeDisc);
                    if (disciplina != null) {
                        aluno.matricularDisciplina(disciplina);
                        MatriculaRepositorio matriculaRepo = new MatriculaRepositorio("matriculas.txt");
                        matriculaRepo.salvarMatricula(aluno);
                        System.out.println("Matriculado!");
                    }
                    
                }
                case 3 -> {
                    System.out.print("Digite o nome da disciplina a cancelar: ");
                    String nomeDisc = sc.nextLine();
                    Disciplina disciplina = disciplinaRepo.procurarMateria(nomeDisc);
                    if (disciplina != null) {
                        aluno.cancelarDisciplina(disciplina);
                        System.out.println("Cancelamento realizado!");
                        MatriculaRepositorio matriculaRepo = new MatriculaRepositorio("matriculas.txt");
                        matriculaRepo.removerMatricula(aluno);
                        matriculaRepo.salvarMatricula(aluno);
                    }
                }
                case 4 -> aluno.visualizarDisciplinas();
            }
        } while (opcao != 0);
    }

    private static void menuProfessor(Scanner sc, Professor professor) throws IOException {
    int opcao;
    do {
        System.out.println("\n--- Menu Professor ---");
        System.out.println("1. Visualizar minha disciplina");
        System.out.println("2. Ver alunos matriculados");
        System.out.println("3. Debug do sistema");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
        opcao = Integer.parseInt(sc.nextLine());

        switch (opcao) {
            case 1 -> {
                if (professor.getDisciplina() != null) {
                    professor.visualizarDisciplinaCompleta();
                } else {
                    System.out.println("Nenhuma disciplina vinculada.");
                    System.out.println("Professor: " + professor.toString());
                }
            }
            case 2 -> {
                if (professor.getDisciplina() != null) {
                    System.out.println("Alunos matriculados em " + professor.getDisciplina().getNome() + ":");
                    System.out.println(professor.visualizarAlunosDaDisciplina());
                } else {
                    System.out.println("Nenhuma disciplina vinculada.");
                }
            }
            case 3 -> {
                DebugService.verificarEstadoSistema();
            }
        }
    } while (opcao != 0);
}
}
