package com.example.application.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class FinanceiroTest {
    private Financeiro financeiro;

    @BeforeEach
    void setUp() {
        financeiro = new Financeiro();
        System.out.println("SetUp realizado: Instância de Financeiro criada.");
    }

    @Test
    void testAdicionarGasto() {
        Gasto gasto = new Gasto(1, "Alimentação", LocalDate.now(), 100.0, "Cartão de Crédito");
        financeiro.adicionarGasto(gasto);

        // Mensagem de retorno
        System.out.println("Teste adicionarGasto: Gasto adicionado.");

        // Verificar se o gasto foi adicionado corretamente
        assertEquals(1, financeiro.getGastos().size(), "Deve ter um gasto registrado");
        assertEquals(gasto, financeiro.getGastos().get(0), "O gasto registrado deve ser o mesmo que foi adicionado");

        // Mensagem de retorno
        System.out.println("Teste adicionarGasto: Verificação concluída.");
    }

    @Test
    void testAdicionarGanho() {
        Ganho ganho = new Ganho(1, "Salário", LocalDate.now(), 2000.0);
        financeiro.adicionarGanho(ganho);

        // Mensagem de retorno
        System.out.println("Teste adicionarGanho: Ganho adicionado.");

        // Verificar se o ganho foi adicionado corretamente
        assertEquals(1, financeiro.getGanhos().size(), "Deve ter um ganho registrado");
        assertEquals(ganho, financeiro.getGanhos().get(0), "O ganho registrado deve ser o mesmo que foi adicionado");

        // Mensagem de retorno
        System.out.println("Teste adicionarGanho: Verificação concluída.");
    }
}
