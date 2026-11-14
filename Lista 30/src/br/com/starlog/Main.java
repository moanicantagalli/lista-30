package br.com.starlog;

import br.com.starlog.dao.ProdutoDAO;
import br.com.starlog.dao.ProdutoDAOSqlite;
import br.com.starlog.model.Produto;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new File("starlog.db").delete();
        ProdutoDAO dao = new ProdutoDAOSqlite("jdbc:sqlite:starlog.db");

        System.out.println("[Create]");
        Produto teclado = new Produto("Teclado Mecânico RGB", 349.90, 10);
        Produto mousepad = new Produto("Mousepad Speed XL", 79.90, 25);
        dao.salvar(teclado);
        dao.salvar(mousepad);
        System.out.println("ID gerado: " + teclado.getId() + " - " + teclado.getNome());
        System.out.println("ID gerado: " + mousepad.getId() + " - " + mousepad.getNome());

        System.out.println("\n[Read]");
        imprimir(dao.listarTodos());

        System.out.println("\n[Update]");
        teclado.setEstoque(15);
        teclado.setPreco(289.90);
        boolean atualizado = dao.atualizar(teclado);
        System.out.println("Atualizado: " + atualizado);
        dao.buscarPorId(teclado.getId()).ifPresent(System.out::println);

        System.out.println("\n[Delete]");
        System.out.println("Excluir Mousepad (id " + mousepad.getId() + "): " + dao.excluir(mousepad.getId()));
        boolean inexistente = dao.excluir(888L);
        System.out.println("Excluir id 888: " + inexistente);
        if (!inexistente) {
            System.out.println("Produto 888 não encontrado, nenhuma linha afetada.");
        }

        System.out.println("\n[Final State]");
        List<Produto> finais = dao.listarTodos();
        imprimir(finais);
        System.out.println("Total de produtos: " + finais.size());
    }

    private static void imprimir(List<Produto> lista) {
        System.out.printf("%-4s | %-25s | %-10s | %-7s%n", "ID", "NOME", "PRECO", "ESTOQUE");
        System.out.println("-----+---------------------------+------------+--------");
        for (Produto p : lista) {
            System.out.printf("%-4d | %-25s | R$ %-7.2f | %-7d%n", p.getId(), p.getNome(), p.getPreco(), p.getEstoque());
        }
    }
}
