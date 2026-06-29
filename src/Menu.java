import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final XmlManager xmlManager;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.xmlManager = new XmlManager(scanner);
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n=========== TORNEIO DE TÊNIS ===========\n");
            System.out.println("1 - Criar arquivo XML");
            System.out.println("2 - Adicionar jogo");
            System.out.println("3 - Listar jogos");
            System.out.println("4 - Alterar placar");
            System.out.println("5 - Alterar quadra");
            System.out.println("6 - Remover jogo");
            System.out.println("7 - Buscar jogo por ID");
            System.out.println("0 - Sair\n");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> xmlManager.criarXML();
                case 2 -> xmlManager.adicionarJogo();
                case 3 -> xmlManager.listarJogos();
                case 4 -> xmlManager.alterarPlacar();
                case 5 -> xmlManager.alterarQuadra();
                case 6 -> xmlManager.removerJogo();
                case 7 -> xmlManager.buscarJogoPorId();
                case 0 -> System.out.println("\nEncerrando o sistema. Até logo!");
                default -> System.out.println("\nOpção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número: ");
            scanner.nextLine();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
