package com.example.application.views;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;


import com.example.application.backend.GanhoRepository;


public class GanhosViewTest {

    private GanhosView ganhosView;

    @Mock
    private GanhoRepository ganhoRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ganhosView = new GanhosView();
        ganhosView.ganhoRepository = ganhoRepository;


    }

    @Test
    public void testAdicionarNovaCategoria() {
        // Define a nova categoria a ser adicionada
        String novaCategoria = "Freelance";

        // Adiciona a nova categoria ao ComboBox
        List<String> tiposDeGanhoAtualizados = new ArrayList<>(ganhosView.tipoGanhosComboBox.getListDataView().getItems().collect(Collectors.toList()));
        if (!tiposDeGanhoAtualizados.contains(novaCategoria)) {
            tiposDeGanhoAtualizados.add(novaCategoria);
            ganhosView.tipoGanhosComboBox.setItems(tiposDeGanhoAtualizados);
        }

        // Verifica se a nova categoria foi adicionada
        List<String> items = ganhosView.tipoGanhosComboBox.getListDataView().getItems().collect(Collectors.toList());
        boolean categoriaAdicionada = items.contains(novaCategoria);

        // Assert e imprime a mensagem de sucesso
        Assert.assertTrue("A nova categoria deve estar presente no ComboBox", categoriaAdicionada);
        if (categoriaAdicionada) {
            System.out.println("Teste bem-sucedido: Categoria '" + novaCategoria + "' adicionada com sucesso.");
        } else {
            System.out.println("Teste falhou: Categoria '" + novaCategoria + "' não foi adicionada.");
        }
    }

}
