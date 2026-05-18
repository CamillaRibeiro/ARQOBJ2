import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Aluno {
    String nome;
    double nota;

    public Aluno(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }
}

public class CadastroAlunos {

    static ArrayList<Aluno> alunos = new ArrayList<>();
    static final String ARQUIVO = "alunos.xml";

    public static void main(String[] args) {

        criarArquivoSeNaoExistir();

        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Exibir alunos");
            System.out.println("3 - Média da turma");
            System.out.println("4 - Aluno com maior nota");
            System.out.println("5 - Quantidade de alunos com nota >= 7");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    cadastrarAluno(sc);
                    salvarArquivo();
                    break;

                case 2:
                    exibirAlunos();
                    break;

                case 3:
                    calcularMedia();
                    break;

                case 4:
                    maiorNota();
                    break;

                case 5:
                    alunosAprovados();
                    break;

                case 0:
                    System.out.println("Encerrando programa...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }

    public static void criarArquivoSeNaoExistir() {
        try {
            File arquivo = new File(ARQUIVO);

            if (!arquivo.exists()) {
                PrintWriter pw = new PrintWriter(arquivo);
                pw.println("<alunos>");
                pw.println("</alunos>");
                pw.close();

                System.out.println("Arquivo criado com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("Erro ao criar arquivo.");
        }
    }

    public static void cadastrarAluno(Scanner sc) {

        System.out.print("Nome do aluno: ");
        String nome = sc.nextLine();

        System.out.print("Nota do aluno: ");
        double nota = sc.nextDouble();

        alunos.add(new Aluno(nome, nota));

        System.out.println("Aluno cadastrado com sucesso!");
    }

    public static void exibirAlunos() {

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("\n===== LISTA DE ALUNOS =====");

        for (Aluno a : alunos) {
            System.out.println("Nome: " + a.nome);
            System.out.println("Nota: " + a.nota);
            System.out.println("-------------------");
        }
    }

    public static void calcularMedia() {

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        double soma = 0;

        for (Aluno a : alunos) {
            soma += a.nota;
        }

        double media = soma / alunos.size();

        System.out.printf("Média da turma: %.2f\n", media);
    }

    public static void maiorNota() {

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        Aluno maior = alunos.get(0);

        for (Aluno a : alunos) {
            if (a.nota > maior.nota) {
                maior = a;
            }
        }

        System.out.println("Aluno com maior nota:");
        System.out.println("Nome: " + maior.nome);
        System.out.println("Nota: " + maior.nota);
    }

    public static void alunosAprovados() {

        int contador = 0;

        for (Aluno a : alunos) {
            if (a.nota >= 7) {
                contador++;
            }
        }

        System.out.println("Quantidade de alunos com nota >= 7: " + contador);
    }

    public static void salvarArquivo() {

        try {

            PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO));

            pw.println("<alunos>");

            for (Aluno a : alunos) {

                pw.println("    <aluno>");
                pw.println("        <nome>" + a.nome + "</nome>");
                pw.println("        <nota>" + a.nota + "</nota>");
                pw.println("    </aluno>");
            }

            pw.println("</alunos>");

            pw.close();

        } catch (Exception e) {
            System.out.println("Erro ao salvar arquivo.");
        }
    }
}