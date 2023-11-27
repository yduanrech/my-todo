package com.example.application.views;


import com.example.application.backend.Financeiro;
import com.example.application.backend.Ganho;
import com.example.application.backend.GanhoRepository;
import com.fasterxml.jackson.databind.ser.std.MapProperty;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.w3c.dom.html.HTMLInputElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route("ganhos")
public class GanhosView extends VerticalLayout {

    protected ComboBox<String> tipoGanhosComboBox;
    protected DatePicker dataGanhoPicker;
    protected NumberField valorGanhoField;
    protected Button addGanhoButton;

    Financeiro financeiro = new Financeiro();
    GanhoRepository ganhoRepository = new GanhoRepository();
    Grid<Ganho> grid = new Grid<>(Ganho.class);
    private final Dialog editDialog = new Dialog();
    private AvatarGroup tipoGanhoSelect;

    public GanhosView() {
        ViewUtils ViewUtils = new ViewUtils();
        add(new H1("Gestão Financeira - Ganhos"));
        add(ViewUtils.criarBotaoTopo());
        setupAddGanhoSection();

        // Inicializar itens do ComboBox aqui
        List<String> tiposDeGanho = Arrays.asList("Salário", "Investimento", "Outros");
        tipoGanhosComboBox.setItems(tiposDeGanho);

        grid.addComponentColumn(ganhos -> {
            Button deleteButton = new Button("Deletar", clickEvent -> {
                ganhoRepository.delete(ganhos);
                updateGrid();
            });

            Button editButton = new Button("Editar", clickEvent -> openEditDialog(ganhos));
            return new HorizontalLayout(editButton, deleteButton);
        });

        add(grid);
        updateGrid();
    }

    protected void setupAddGanhoSection() {

        tipoGanhosComboBox = new ComboBox<>();
        dataGanhoPicker = new DatePicker("Data do Ganho");
        valorGanhoField = new NumberField("Valor do Ganho");
        addGanhoButton = new Button("Adicionar Ganho");

        NumberField idGanhoField = new NumberField("ID do Ganho");
        List<String> tiposDeGanho = new ArrayList<>();
        tiposDeGanho.addAll(Arrays.asList("Salário", "Investimento", "Outros"));

        ComboBox<String> tipoGanhosComboBox = new ComboBox<>();
        tipoGanhosComboBox.setItems(tiposDeGanho);
        tipoGanhosComboBox.setLabel("Tipo de Ganho");
        tipoGanhosComboBox.setAllowCustomValue(true);

        tipoGanhosComboBox.addCustomValueSetListener(event -> {
            String novoTipo = event.getDetail();
            if (!tiposDeGanho.contains(novoTipo)) {
                tiposDeGanho.add(novoTipo);
                tipoGanhosComboBox.setItems(tiposDeGanho);
            }
            tipoGanhosComboBox.setValue(novoTipo);
        });

        DatePicker dataGanhoPicker = new DatePicker("Data do Ganho");
        NumberField valorGanhoField = new NumberField("Valor do Ganho");

        Button addGanhoButton = new Button("Adicionar Ganho");
        addGanhoButton.addClickListener(e -> {
            Ganho ganhos = new Ganho(
                    0,
                    tipoGanhosComboBox.getValue(),
                    dataGanhoPicker.getValue(),
                    valorGanhoField.getValue());
            financeiro.adicionarGanho(ganhos);
            ganhoRepository.add(ganhos);
            updateGrid();
        });

        add(tipoGanhosComboBox, dataGanhoPicker, valorGanhoField, addGanhoButton);
    }


    public class ViewUtils {
        public HorizontalLayout criarBotaoTopo() {
            Button relatorioButton = new Button("Relatório", e -> {
            getUI().ifPresent(ui -> ui.navigate("SecondView"));
            });
            Button ganhoButton = new Button("Ganho", e -> {
                getUI().ifPresent(ui -> ui.navigate("SecondView"));
            });
            Button gastoButton = new Button("Gasto", e -> {
                getUI().ifPresent(ui -> ui.navigate(""));
            });
            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.add(relatorioButton, ganhoButton, gastoButton);

            return buttonLayout;
        }
    }



    private void openEditDialog(Ganho ganhos) {
        editDialog.removeAll();

        Select<String> editTipoGanhosSelect = new Select<>();
        editTipoGanhosSelect.setItems("Salário", "Investimento", "Outros");
        editTipoGanhosSelect.setLabel("Tipo de Ganho");
        editTipoGanhosSelect.setValue(ganhos.getTipo());

        DatePicker editDataGanhoPicker = new DatePicker("Data do Ganhos");
        editDataGanhoPicker.setValue(ganhos.getData()); // Usando o método getLocalDate

        NumberField editValorGanhoField = new NumberField("Valor do Ganho");
        editValorGanhoField.setValue(ganhos.getValor());


        Button saveButton = new Button("Salvar", e -> {
            ganhos.setTipo(editTipoGanhosSelect.getValue());
            ganhos.setData(editDataGanhoPicker.getValue());
            ganhos.setValor(editValorGanhoField.getValue());

            ganhoRepository.update(ganhos);
            editDialog.close();
            updateGrid();

        });

        editDialog.add(editTipoGanhosSelect, editDataGanhoPicker, editValorGanhoField, saveButton);
        editDialog.open();
    }



    private void updateGrid() {
        grid.setItems(GanhoRepository.getAll());
    }


}