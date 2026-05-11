import java.io.*;
import java.util.ArrayList;

class Produto implements Serializable {

    String nome;
    double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + preco;
    }
}

class Pedido implements Serializable {

    int codigo;
    ArrayList<Produto> produtos;

    public Pedido(int codigo) {
        this.codigo = codigo;
        produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto p) {
        produtos.add(p);
    }

    public double calcularTotal() {

        double total = 0;

        for (Produto p : produtos) {
            total += p.preco;
        }

        return total;
    }
}

public class Ex4 {

    public static void main(String[] args) throws Exception {

        Produto p1 = new Produto("Mouse", 80.0);
        Produto p2 = new Produto("Teclado", 150.0);

        Pedido pedido = new Pedido(1);

        pedido.adicionarProduto(p1);
        pedido.adicionarProduto(p2);

        ObjectOutputStream out =
                new ObjectOutputStream(
                        new FileOutputStream("pedido.ser"));

        out.writeObject(pedido);

        out.close();

        System.out.println("Pedido salvo!");

        ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream("pedido.ser"));

        Pedido pedidoLido = (Pedido) in.readObject();

        in.close();

        System.out.println("\nCódigo: " + pedidoLido.codigo);

        System.out.println("Produtos:");

        for (Produto p : pedidoLido.produtos) {
            System.out.println(p);
        }

        System.out.println("Valor total: R$ "
                + pedidoLido.calcularTotal());
    }
}