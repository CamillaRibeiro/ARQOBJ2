import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlManager {
    private static final String ARQUIVO_XML = "jogos.xml";

    private final Scanner scanner;

    public XmlManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void criarXML() {
        try {
            File arquivo = new File(ARQUIVO_XML);
            if (arquivo.exists()) {
                System.out.println("\nO arquivo XML já existe.");
                return;
            }

            arquivo.getParentFile().mkdirs();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            Element torneio = documento.createElement("torneio");
            documento.appendChild(torneio);

            salvarDocumento(documento);
            System.out.println("\nArquivo XML criado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao criar o arquivo XML: " + e.getMessage());
        }
    }

    public void adicionarJogo() {
        if (!arquivoExiste()) {
            return;
        }

        try {
            Document documento = carregarDocumento();
            Element torneio = documento.getDocumentElement();

            System.out.print("\nJogador 1: ");
            String jogador1 = scanner.nextLine().trim();
            System.out.print("Jogador 2: ");
            String jogador2 = scanner.nextLine().trim();
            System.out.print("Placar: ");
            String placar = scanner.nextLine().trim();
            System.out.print("Quadra: ");
            String quadra = scanner.nextLine().trim();

            int id = obterProximoId(documento);

            Element jogo = documento.createElement("jogo");
            jogo.setAttribute("id", String.valueOf(id));

            adicionarElemento(documento, jogo, "jogador1", jogador1);
            adicionarElemento(documento, jogo, "jogador2", jogador2);
            adicionarElemento(documento, jogo, "placar", placar);
            adicionarElemento(documento, jogo, "quadra", quadra);

            torneio.appendChild(jogo);
            salvarDocumento(documento);

            System.out.println("\nJogo adicionado com sucesso! ID: " + id);
        } catch (Exception e) {
            System.out.println("\nErro ao adicionar jogo: " + e.getMessage());
        }
    }

    public void listarJogos() {
        if (!arquivoExiste()) {
            return;
        }

        try {
            Document documento = carregarDocumento();
            NodeList jogos = documento.getElementsByTagName("jogo");

            if (jogos.getLength() == 0) {
                System.out.println("\nNenhum jogo cadastrado.");
                return;
            }

            System.out.println("\n========== JOGOS CADASTRADOS ==========");
            for (int i = 0; i < jogos.getLength(); i++) {
                exibirJogo((Element) jogos.item(i));
                System.out.println("---------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("\nErro ao listar jogos: " + e.getMessage());
        }
    }

    public void alterarPlacar() {
        if (!arquivoExiste()) {
            return;
        }

        try {
            System.out.print("\nInforme o ID do jogo: ");
            String id = scanner.nextLine().trim();

            Document documento = carregarDocumento();
            Element jogo = buscarJogo(documento, id);

            if (jogo == null) {
                System.out.println("Jogo não encontrado.");
                return;
            }

            System.out.print("Novo placar: ");
            String novoPlacar = scanner.nextLine().trim();

            atualizarElemento(jogo, "placar", novoPlacar);
            salvarDocumento(documento);

            System.out.println("\nPlacar alterado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao alterar placar: " + e.getMessage());
        }
    }

    public void alterarQuadra() {
        if (!arquivoExiste()) {
            return;
        }

        try {
            System.out.print("\nInforme o ID do jogo: ");
            String id = scanner.nextLine().trim();

            Document documento = carregarDocumento();
            Element jogo = buscarJogo(documento, id);

            if (jogo == null) {
                System.out.println("Jogo não encontrado.");
                return;
            }

            System.out.print("Nova quadra: ");
            String novaQuadra = scanner.nextLine().trim();

            atualizarElemento(jogo, "quadra", novaQuadra);
            salvarDocumento(documento);

            System.out.println("\nQuadra alterada com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao alterar quadra: " + e.getMessage());
        }
    }

    public void removerJogo() {
        if (!arquivoExiste()) {
            return;
        }

        try {
            System.out.print("\nInforme o ID do jogo a remover: ");
            String id = scanner.nextLine().trim();

            Document documento = carregarDocumento();
            Element jogo = buscarJogo(documento, id);

            if (jogo == null) {
                System.out.println("Jogo não encontrado.");
                return;
            }

            documento.getDocumentElement().removeChild(jogo);
            salvarDocumento(documento);

            System.out.println("\nJogo removido com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao remover jogo: " + e.getMessage());
        }
    }

    public void buscarJogoPorId() {
        if (!arquivoExiste()) {
            return;
        }

        try {
            System.out.print("\nInforme o ID do jogo: ");
            String id = scanner.nextLine().trim();

            Document documento = carregarDocumento();
            Element jogo = buscarJogo(documento, id);

            if (jogo == null) {
                System.out.println("Jogo não encontrado.");
                return;
            }

            System.out.println("\n========== JOGO ENCONTRADO ==========");
            exibirJogo(jogo);
        } catch (Exception e) {
            System.out.println("\nErro ao buscar jogo: " + e.getMessage());
        }
    }

    private boolean arquivoExiste() {
        File arquivo = new File(ARQUIVO_XML);
        if (!arquivo.exists()) {
            System.out.println("\nArquivo XML não encontrado. Crie o arquivo primeiro (opção 1).");
            return false;
        }
        return true;
    }

    private Document carregarDocumento() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(ARQUIVO_XML));
    }

    private void salvarDocumento(Document documento) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(documento);
        StreamResult result = new StreamResult(new File(ARQUIVO_XML));
        transformer.transform(source, result);
    }

    private int obterProximoId(Document documento) {
        NodeList jogos = documento.getElementsByTagName("jogo");
        int maiorId = 0;

        for (int i = 0; i < jogos.getLength(); i++) {
            Element jogo = (Element) jogos.item(i);
            int id = Integer.parseInt(jogo.getAttribute("id"));
            if (id > maiorId) {
                maiorId = id;
            }
        }

        return maiorId + 1;
    }

    private Element buscarJogo(Document documento, String id) {
        NodeList jogos = documento.getElementsByTagName("jogo");

        for (int i = 0; i < jogos.getLength(); i++) {
            Element jogo = (Element) jogos.item(i);
            if (jogo.getAttribute("id").equals(id)) {
                return jogo;
            }
        }

        return null;
    }

    private void adicionarElemento(Document documento, Element pai, String nome, String valor) {
        Element elemento = documento.createElement(nome);
        elemento.setTextContent(valor);
        pai.appendChild(elemento);
    }

    private void atualizarElemento(Element jogo, String nome, String valor) {
        NodeList elementos = jogo.getElementsByTagName(nome);
        if (elementos.getLength() > 0) {
            elementos.item(0).setTextContent(valor);
        }
    }

    private void exibirJogo(Element jogo) {
        System.out.println("ID: " + jogo.getAttribute("id"));
        System.out.println("Jogador 1: " + obterTexto(jogo, "jogador1"));
        System.out.println("Jogador 2: " + obterTexto(jogo, "jogador2"));
        System.out.println("Placar: " + obterTexto(jogo, "placar"));
        System.out.println("Quadra: " + obterTexto(jogo, "quadra"));
    }

    private String obterTexto(Element jogo, String tag) {
        NodeList elementos = jogo.getElementsByTagName(tag);
        if (elementos.getLength() > 0) {
            Node node = elementos.item(0);
            return node.getTextContent();
        }
        return "";
    }
}
