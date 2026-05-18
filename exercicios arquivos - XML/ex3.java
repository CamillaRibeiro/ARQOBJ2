import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class CatalogoLivrosDOM {

    public static void main(String[] args) {

        try {
            criarXML();

            File arquivo = new File("livros.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(arquivo);

            doc.getDocumentElement().normalize();

            NodeList listaLivros = doc.getElementsByTagName("livro");

            double somaPrecos = 0;

            String livroMaisCaro = "";
            double maiorPreco = 0;

            ArrayList<String> titulos = new ArrayList<>();
            ArrayList<String> autores = new ArrayList<>();
            ArrayList<Double> precos = new ArrayList<>();

            for (int i = 0; i < listaLivros.getLength(); i++) {

                Node node = listaLivros.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element elemento = (Element) node;

                    String titulo = elemento
                            .getElementsByTagName("titulo")
                            .item(0)
                            .getTextContent();

                    String autor = elemento
                            .getElementsByTagName("autor")
                            .item(0)
                            .getTextContent();

                    double preco = Double.parseDouble(
                            elemento
                                    .getElementsByTagName("preco")
                                    .item(0)
                                    .getTextContent());

                    titulos.add(titulo);
                    autores.add(autor);
                    precos.add(preco);

                    somaPrecos += preco;

                    if (preco > maiorPreco) {
                        maiorPreco = preco;
                        livroMaisCaro = titulo;
                    }
                }
            }

            System.out.printf("Soma total dos preços: R$ %.2f\n", somaPrecos);

            LinkedHashSet<String> autoresUnicos = new LinkedHashSet<>(autores);

            ArrayList<String> listaAutores = new ArrayList<>(autoresUnicos);

            System.out.println("\n===== AUTORES =====");

            for (int i = 0; i < listaAutores.size(); i++) {
                System.out.println(i + " - " + listaAutores.get(i));
            }

            Scanner sc = new Scanner(System.in);

            System.out.print("\nEscolha o índice do autor: ");
            int indice = sc.nextInt();

            if (indice >= 0 && indice < listaAutores.size()) {

                String autorSelecionado = listaAutores.get(indice);

                System.out.println("\nLivros do autor: " + autorSelecionado);

                for (int i = 0; i < autores.size(); i++) {

                    if (autores.get(i).equals(autorSelecionado)) {

                        System.out.println("Título: " + titulos.get(i));
                        System.out.printf("Preço: R$ %.2f\n", precos.get(i));
                        System.out.println("-------------------");
                    }
                }

            } else {
                System.out.println("Índice inválido.");
            }

            System.out.println("\nLivro mais caro:");
            System.out.printf("%s - R$ %.2f\n", livroMaisCaro, maiorPreco);

            sc.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void criarXML() {

        try {

            File arquivo = new File("livros.xml");

            if (!arquivo.exists()) {

                PrintWriter pw = new PrintWriter(new FileWriter(arquivo));

                pw.println("<catalogo>");

                pw.println("    <livro>");
                pw.println("        <titulo>Java Básico</titulo>");
                pw.println("        <autor>Carlos Silva</autor>");
                pw.println("        <preco>59.90</preco>");
                pw.println("    </livro>");

                pw.println("    <livro>");
                pw.println("        <titulo>Estruturas de Dados</titulo>");
                pw.println("        <autor>Ana Souza</autor>");
                pw.println("        <preco>75.50</preco>");
                pw.println("    </livro>");

                pw.println("    <livro>");
                pw.println("        <titulo>POO com Java</titulo>");
                pw.println("        <autor>Carlos Silva</autor>");
                pw.println("        <preco>89.90</preco>");
                pw.println("    </livro>");

                pw.println("</catalogo>");

                pw.close();

                System.out.println("Arquivo XML criado com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("Erro ao criar XML.");
        }
    }
}