package sistemaDeMatricula.implementacao.code;

import java.io.*;
import java.util.*;

public class ProfessorRepositorio {
    private String arquivo;

    public ProfessorRepositorio(String arquivo) {
        this.arquivo = arquivo;
    }

    public Professor procurarProfessor(String emailProfessor) {
    try (Scanner sc = new Scanner(new File(arquivo))) {
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            String[] dados = linha.split(";");
            if (dados.length == 4 && dados[0].equals("2")) {
                String nome = dados[1];
                String email = dados[2];
                String senha = dados[3];
                
                if (email.equalsIgnoreCase(emailProfessor)) {
                    // Retornar professor sem disciplina inicialmente
                    return new Professor(nome, email, senha, null);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

    // Método para atualizar professor com disciplina
    public void atualizarProfessor(Professor professor) throws IOException {
        List<String> linhas = new ArrayList<>();
        File file = new File(arquivo);
        
        if (file.exists()) {
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String linha = sc.nextLine();
                    String[] dados = linha.split(";");
                    
                    if (dados.length == 4 && dados[0].equals("2") && 
                        dados[2].equalsIgnoreCase(professor.getEmail())) {
                        // Substituir a linha do professor
                        linhas.add(professor.gerarDadosTexto());
                    } else {
                        linhas.add(linha);
                    }
                }
            }
        }
        
        // Reescrever o arquivo
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String linha : linhas) {
                pw.println(linha);
            }
        }
    }

    public List<Professor> carregarTodosProfessores() {
        List<Professor> professores = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(arquivo))) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                String[] dados = linha.split(";");
                if (dados.length == 4 && dados[0].equals("2")) {
                    String nome = dados[1];
                    String email = dados[2];
                    String senha = dados[3];
                    
                    DisciplinaRepositorio disciplinaRepo = new DisciplinaRepositorio("disciplina.txt");
                    Disciplina disciplina = disciplinaRepo.procurarDisciplinaPorProfessor(email);
                    
                    Professor professor = new Professor(nome, email, senha, disciplina);
                    professores.add(professor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professores;
    }
}