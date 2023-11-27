package com.example.application.backend;

import org.junit.*;
import static org.junit.Assert.*;
import java.sql.*;
import java.time.LocalDate;

public class GastoRepositoryTest {

    private GastoRepository gastoRepository;
    private Connection conexao;

    @Before
    public void setUp() throws SQLException {
        conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/BancoFinanceiro", "root", "root");
        gastoRepository = new GastoRepository(); // Usando o construtor padrão
    }

    @Test
    public void testAddAndUpdateGasto() throws SQLException {
        // Adicionar um novo gasto do tipo "Alimentação"
        Gasto novoGasto = new Gasto(0, "Alimentação", LocalDate.now(), 100, "Dinheiro");
        gastoRepository.add(novoGasto);

        // Recuperar e imprimir o gasto inserido
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM gastos WHERE tipo = 'Alimentação'");
        assertTrue("Gasto deve ser adicionado", rs.next());
        int gastoId = rs.getInt("id");
        imprimirGasto(rs);

        // Atualizar o tipo do gasto para "Lazer"
        Gasto gastoAtualizado = new Gasto(gastoId, "Lazer", LocalDate.now(), 100, "Dinheiro");
        gastoRepository.update(gastoAtualizado);

        // Verificar e imprimir a atualização do gasto
        ResultSet rsAtualizado = stmt.executeQuery("SELECT * FROM gastos WHERE id = " + gastoId);
        assertTrue("Gasto deve ser atualizado", rsAtualizado.next());
        imprimirGasto(rsAtualizado);

        // Limpeza após o teste
        stmt.executeUpdate("DELETE FROM gastos WHERE id = " + gastoId);
        rs.close();
        rsAtualizado.close();
        stmt.close();
    }

    private void imprimirGasto(ResultSet rs) throws SQLException {
        System.out.println("ID: " + rs.getInt("id"));
        System.out.println("Tipo: " + rs.getString("tipo"));
        System.out.println("Data: " + rs.getDate("data").toLocalDate());
        System.out.println("Valor: " + rs.getDouble("valor"));
        System.out.println("Forma de Pagamento: " + rs.getString("formaPagamento"));
        System.out.println("-----------------------------------");
    }

    @After
    public void tearDown() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }
}
