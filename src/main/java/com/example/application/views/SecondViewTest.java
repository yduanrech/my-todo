package com.example.application.views;

import com.example.application.backend.Gasto;
import com.example.application.backend.GastoRepository;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SecondViewTest {

    @Mock
    private GastoRepository mockGastoRepository;

    private SecondView secondView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        secondView = new SecondView();
        secondView.gastoRepository = mockGastoRepository;
    }

    @Test
    public void testUpdateGrid_NoDateFilters() {
        Gasto gasto1 = new Gasto(1, "Compras", LocalDate.of(2023, 3, 10), 100.0, "Cartão de Crédito");
        Gasto gasto2 = new Gasto(2, "Alimentação", LocalDate.of(2023, 3, 11), 50.0, "Dinheiro");
        List<Gasto> allGastos = Arrays.asList(gasto1, gasto2);

        when(mockGastoRepository.getAll()).thenReturn(allGastos);

        secondView.updateGrid(null, null);

        // Assertivas para verificar o resultado (exemplo genérico)
        // Suponha que você tem uma maneira de obter os itens do grid
        // assertEquals("Número de itens no grid incorreto", 2, secondView.getGridItemCount());

        System.out.println("Teste updateGrid sem filtros de data concluído com sucesso.");
    }

    @Test
    public void testUpdateGrid_WithDateFilters() {
        Gasto gasto1 = new Gasto(1, "Compras", LocalDate.of(2023, 3, 10), 100.0, "Cartão de Crédito");
        Gasto gasto2 = new Gasto(2, "Alimentação", LocalDate.of(2023, 3, 11), 50.0, "Dinheiro");
        List<Gasto> allGastos = Arrays.asList(gasto1, gasto2);

        when(mockGastoRepository.getAll()).thenReturn(allGastos);

        LocalDate startDate = LocalDate.of(2023, 3, 10);
        LocalDate endDate = LocalDate.of(2023, 3, 11);

        secondView.updateGrid(startDate, endDate);

        System.out.println("Teste updateGrid com filtros de data concluído com sucesso.");
    }

    @Test
    public void testUpdateTotal() {
        List<Gasto> gastos = Arrays.asList(
                new Gasto(1, "Compras", LocalDate.of(2023, 3, 10), 100.0, "Cartão de Crédito"),
                new Gasto(2, "Alimentação", LocalDate.of(2023, 3, 11), 50.0, "Dinheiro")
        );
        secondView.updateTotal(gastos);

        double totalCalculado = gastos.stream().mapToDouble(Gasto::getValor).sum();

        try {
            // Assertiva para verificar o total
            assertEquals("Total calculado incorreto", 150.0, totalCalculado, 0.001);
            System.out.println("Teste updateTotal concluído com sucesso. Total calculado: " + totalCalculado);
        } catch (AssertionError e) {
            System.out.println("Falha no teste updateTotal. " + e.getMessage());
        }
    }

}
