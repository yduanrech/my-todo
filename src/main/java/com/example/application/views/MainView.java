package com.example.application.views;
import com.example.application.backend.Financeiro;
import com.example.application.backend.GastoRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.example.application.backend.Gasto;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    Financeiro financeiro = new Financeiro();
    GastoRepository gastoRepository = new GastoRepository();
    Grid<Gasto> grid = new Grid<>(Gasto.class);
    private final Dialog editDialog = new Dialog();

    public MainView() {
        ViewUtils ViewUtils = new ViewUtils();
        add(new H1("Gestão Financeira - Gastos"));
        add(ViewUtils.criarBotaoTopo());
        setupAddGastoSection();



        grid.addComponentColumn(gasto -> {
            Button deleteButton = new Button("Deletar", clickEvent -> {
                gastoRepository.delete(gasto);
                updateGrid();
            });

            Button editButton = new Button("Editar", clickEvent -> openEditDialog(gasto));
            return new HorizontalLayout(editButton, deleteButton);
        });

        add(grid);
        updateGrid();
    }

    protected void setupAddGastoSection() {
        NumberField idGastoField = new NumberField("ID do Gasto");
        List<String> tiposDeGasto = new ArrayList<>();
        tiposDeGasto.addAll(Arrays.asList("Habitação", "Alimentação", "Transporte", "Lazer", "Outros"));

        ComboBox<String> tipoGastoComboBox = new ComboBox<>();
        tipoGastoComboBox.setItems(tiposDeGasto);
        tipoGastoComboBox.setLabel("Tipo de Gasto");
        tipoGastoComboBox.setAllowCustomValue(true);

        tipoGastoComboBox.addCustomValueSetListener(event -> {
            String novoTipo = event.getDetail();
            if (!tiposDeGasto.contains(novoTipo)) {
                tiposDeGasto.add(novoTipo);
                tipoGastoComboBox.setItems(tiposDeGasto);
            }
            tipoGastoComboBox.setValue(novoTipo);
        });

        DatePicker dataGastoPicker = new DatePicker("Data do Gasto");
        NumberField valorGastoField = new NumberField("Valor do Gasto");

        Select<String> formaPagamentoSelect = new Select<>();
        formaPagamentoSelect.setItems("Dinheiro", "Débito", "Crédito", "Pix");
        formaPagamentoSelect.setLabel("Forma de Pagamento");

        Button addGastoButton = new Button("Adicionar Gasto");
        addGastoButton.addClickListener(e -> {
            System.out.println("Antes do add gasto");
            Gasto gasto = new Gasto(
                    0,
                    tipoGastoComboBox.getValue(),
                    dataGastoPicker.getValue(),
                    valorGastoField.getValue(),
                    formaPagamentoSelect.getValue());
            financeiro.adicionarGasto(gasto);
            gastoRepository.add(gasto);
            updateGrid();
        });


        add(tipoGastoComboBox, dataGastoPicker, valorGastoField, formaPagamentoSelect, addGastoButton);
    }

    //Add a button called Relatorio and another button called Ganho to the main view side by side
    public class ViewUtils {
        public HorizontalLayout criarBotaoTopo() {
            Button relatorioButton = new Button("Relatório", e -> {
            getUI().ifPresent(ui -> ui.navigate("SecondView"));
            });
            Button ganhoButton = new Button("Ganho", e -> {
                getUI().ifPresent(ui -> ui.navigate("ganhos"));
            });
            Button gastoButton = new Button("Gasto", e -> {
                getUI().ifPresent(ui -> ui.navigate(""));
            });
            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.add(relatorioButton, ganhoButton, gastoButton);

            return buttonLayout;
        }
    }



    private void openEditDialog(Gasto gasto) {
        editDialog.removeAll();

        Select<String> editTipoGastoSelect = new Select<>();
        editTipoGastoSelect.setItems("Habitação", "Alimentação", "Transporte", "Lazer", "Outros");
        editTipoGastoSelect.setLabel("Tipo de Gasto");
        editTipoGastoSelect.setValue(gasto.getTipo());

        DatePicker editDataGastoPicker = new DatePicker("Data do Gasto");
        editDataGastoPicker.setValue(gasto.getData()); // Usando o método getLocalDate

        NumberField editValorGastoField = new NumberField("Valor do Gasto");
        editValorGastoField.setValue(gasto.getValor());

        Select<String> editFormaPagamentoSelect = new Select<>();
        editFormaPagamentoSelect.setItems("Dinheiro", "Débito", "Crédito", "Pix");
        editFormaPagamentoSelect.setLabel("Forma de Pagamento");
        editFormaPagamentoSelect.setValue(gasto.getFormaDePagamento());

        Button saveButton = new Button("Salvar", e -> {
            gasto.setTipo(editTipoGastoSelect.getValue());
            gasto.setData(editDataGastoPicker.getValue());
            gasto.setValor(editValorGastoField.getValue());
            gasto.setFormaDePagamento(editFormaPagamentoSelect.getValue());
            gastoRepository.update(gasto);
            editDialog.close();
            updateGrid();

        });

        editDialog.add(editTipoGastoSelect, editDataGastoPicker, editValorGastoField, editFormaPagamentoSelect, saveButton);
        editDialog.open();
    }



    private void updateGrid() {
        grid.setItems(gastoRepository.getAll());
    }


}