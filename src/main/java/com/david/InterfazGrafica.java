package com.david;

import com.david.dao.PersonaDao;
import com.david.dao.TelefonoDao;
import com.david.modelo.Persona;
import com.david.modelo.Telefono;
import com.david.logica.PersonaLogica;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InterfazGrafica extends Application {

    private final PersonaDao personaDao = new PersonaDao();
    private final TelefonoDao telefonoDao = new TelefonoDao();
    private final PersonaLogica personaService = new PersonaLogica();

    private final ObservableList<Persona> personas = FXCollections.observableArrayList();
    private final ObservableList<Telefono> telefonos = FXCollections.observableArrayList();

    private TableView<Persona> tablaPersonas;
    private ListView<Telefono> listaTelefonos;

    @Override
    public void start(Stage stage) {
        tablaPersonas = crearTablaPersonas();
        listaTelefonos = new ListView<>(telefonos);

        Button btnRefrescar = new Button("Actualizar");
        btnRefrescar.setOnAction(e -> refrescarPersonas());

        Button btnNuevo = new Button("Nuevo");
        btnNuevo.setOnAction(e -> crearPersona());

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(e -> editarPersona());

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarPersona());

        HBox barra = new HBox(10, btnRefrescar, btnNuevo, btnEditar, btnEliminar);
        barra.setPadding(new Insets(10));

        VBox panelIzq = new VBox(10, new Label("Personas"), tablaPersonas);
        panelIzq.setPadding(new Insets(10));
        VBox.setVgrow(tablaPersonas, Priority.ALWAYS);

        VBox panelDer = new VBox(10, new Label("Teléfonos de la persona seleccionada"), listaTelefonos);
        panelDer.setPadding(new Insets(10));
        VBox.setVgrow(listaTelefonos, Priority.ALWAYS);

        SplitPane split = new SplitPane(panelIzq, panelDer);
        split.setDividerPositions(0.65);

        BorderPane root = new BorderPane();
        root.setTop(barra);
        root.setCenter(split);

        stage.setTitle("Agenda - Personas y Teléfonos");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();

        tablaPersonas.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) cargarTelefonosDe(newV.getId());
            else telefonos.clear();
        });

        refrescarPersonas();
    }

    private TableView<Persona> crearTablaPersonas() {
        TableView<Persona> tv = new TableView<>(personas);

        TableColumn<Persona, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()));
        colId.setPrefWidth(70);

        TableColumn<Persona, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colNombre.setPrefWidth(250);

        TableColumn<Persona, String> colDir = new TableColumn<>("Dirección");
        colDir.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDireccion()));
        colDir.setPrefWidth(350);

        tv.getColumns().addAll(colId, colNombre, colDir);
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tv;
    }

    private void refrescarPersonas() {
        try {
            personas.setAll(personaDao.obtenerTodas());
            if (!personas.isEmpty()) tablaPersonas.getSelectionModel().select(0);
        } catch (Exception ex) {
            error("Error al cargar personas", ex.getMessage());
        }
    }

    private void cargarTelefonosDe(int personaId) {
        try {
            telefonos.setAll(telefonoDao.obtenerPorPersona(personaId));
        } catch (Exception ex) {
            error("Error al cargar teléfonos", ex.getMessage());
        }
    }

    private void crearPersona() {
        Optional<FormResult> r = mostrarFormulario(null);
        if (r.isEmpty()) return;

        try {
            int id = personaService.crearPersonaConTelefonos(
                    r.get().nombre,
                    r.get().direccion,
                    r.get().telefonos
            );
            refrescarPersonas();
            seleccionarPersonaPorId(id);
        } catch (Exception ex) {
            error("No se pudo crear", ex.getMessage());
        }
    }

    private void editarPersona() {
        Persona sel = tablaPersonas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            info("Aviso", "Selecciona una persona para editar.");
            return;
        }

        List<String> telsActuales = new ArrayList<>();
        for (Telefono t : telefonos) telsActuales.add(t.getTelefono());

        Optional<FormResult> r = mostrarFormulario(new FormResult(sel.getNombre(), sel.getDireccion(), telsActuales));
        if (r.isEmpty()) return;

        try {
            personaService.actualizarPersonaConTelefonos(
                    sel.getId(),
                    r.get().nombre,
                    r.get().direccion,
                    r.get().telefonos
            );
            refrescarPersonas();
            seleccionarPersonaPorId(sel.getId());
        } catch (Exception ex) {
            error("No se pudo actualizar", ex.getMessage());
        }
    }

    private void eliminarPersona() {
        Persona sel = tablaPersonas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            info("Aviso", "Selecciona una persona para eliminar.");
            return;
        }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmar");
        a.setHeaderText("¿Eliminar a " + sel.getNombre() + "?");
        a.setContentText("Se eliminarán también sus teléfonos (CASCADE).");
        Optional<ButtonType> ok = a.showAndWait();
        if (ok.isEmpty() || ok.get() != ButtonType.OK) return;

        try {
            personaDao.eliminar(sel.getId());
            refrescarPersonas();
        } catch (Exception ex) {
            error("No se pudo eliminar", ex.getMessage());
        }
    }

    private void seleccionarPersonaPorId(int id) {
        for (Persona p : personas) {
            if (p.getId() == id) {
                tablaPersonas.getSelectionModel().select(p);
                tablaPersonas.scrollTo(p);
                return;
            }
        }
    }

    private Optional<FormResult> mostrarFormulario(FormResult base) {
        Dialog<FormResult> dialog = new Dialog<>();
        dialog.setTitle(base == null ? "Nueva persona" : "Editar persona");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        TextField txtNombre = new TextField(base == null ? "" : base.nombre);
        TextField txtDireccion = new TextField(base == null ? "" : base.direccion);

        ObservableList<String> tels = FXCollections.observableArrayList(
                base == null ? List.of() : base.telefonos
        );
        ListView<String> list = new ListView<>(tels);
        list.setPrefHeight(120);

        TextField txtTel = new TextField();
        txtTel.setPromptText("Ej: 686-123-4567");

        Button btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> {
            String t = txtTel.getText();
            if (t != null && !t.trim().isEmpty()) {
                tels.add(t.trim());
                txtTel.clear();
                txtTel.requestFocus();
            }
        });

        Button btnQuitar = new Button("Quitar seleccionado");
        btnQuitar.setOnAction(e -> {
            int idx = list.getSelectionModel().getSelectedIndex();
            if (idx >= 0) tels.remove(idx);
        });

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        gp.add(new Label("Nombre*"), 0, 0);
        gp.add(txtNombre, 1, 0);

        gp.add(new Label("Dirección"), 0, 1);
        gp.add(txtDireccion, 1, 1);

        gp.add(new Label("Teléfonos"), 0, 2);
        gp.add(list, 1, 2);

        HBox filaTel = new HBox(10, txtTel, btnAgregar, btnQuitar);
        gp.add(filaTel, 1, 3);

        dialog.getDialogPane().setContent(gp);

        Node guardarNode = dialog.getDialogPane().lookupButton(btnGuardar);
        guardarNode.addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
            if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
                ev.consume();
                error("Validación", "El nombre es obligatorio.");
            }
        });

        dialog.setResultConverter(btn -> {
            if (btn == btnGuardar) {
                return new FormResult(
                        txtNombre.getText().trim(),
                        txtDireccion.getText(),
                        new ArrayList<>(tels)
                );
            }
            return null;
        });

        return dialog.showAndWait();
    }

    // ---------- Alerts ----------
    private void info(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void error(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(titulo);
        a.setContentText(msg);
        a.showAndWait();
    }

    private static class FormResult {
        final String nombre;
        final String direccion;
        final List<String> telefonos;

        FormResult(String nombre, String direccion, List<String> telefonos) {
            this.nombre = nombre;
            this.direccion = direccion;
            this.telefonos = telefonos;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
