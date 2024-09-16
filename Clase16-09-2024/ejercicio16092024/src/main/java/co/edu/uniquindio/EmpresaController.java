package co.edu.uniquindio;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EmpresaController {

    @FXML
    private TableView<Empleado> tableView;
    @FXML
    private TableColumn<Empleado, String> colNombre;
    @FXML
    private TableColumn<Empleado, String> colIdentificacion;
    @FXML
    private TableColumn<Empleado, Double> colSalario;
    @FXML
    private TableColumn<Empleado, String> colTelefono;
    @FXML
    private TableColumn<Empleado, String> colCorreo;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfIdentificacion;
    @FXML
    private TextField tfSalario;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreo;

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;

    private Empresa empresa = new Empresa("Tech Solutions");
    private ObservableList<Empleado> empleadosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuración de las columnas de la tabla
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        colSalario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSalario()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));

        tableView.setItems(empleadosList);

        // Manejo de eventos de botones
        btnAgregar.setOnAction(e -> agregarEmpleado());
        btnActualizar.setOnAction(e -> actualizarEmpleado());
        btnEliminar.setOnAction(e -> eliminarEmpleado());
    }

    private void agregarEmpleado() {
        try {
            String nombre = tfNombre.getText();
            String identificacion = tfIdentificacion.getText();
            double salario = Double.parseDouble(tfSalario.getText());
            String telefono = tfTelefono.getText();
            String correo = tfCorreo.getText();

            Empleado nuevoEmpleado = new Empleado(nombre, identificacion, salario, telefono, correo);
            empresa.agregarEmpleado(nuevoEmpleado);
            empleadosList.add(nuevoEmpleado);
            clearForm();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Salario debe ser un número.");
        }
    }

    private void actualizarEmpleado() {
        try {
            String identificacion = tfIdentificacion.getText();
            Empleado empleadoExistente = empresa.obtenerEmpleado(identificacion);

            if (empleadoExistente != null) {
                String nombre = tfNombre.getText();
                double salario = Double.parseDouble(tfSalario.getText());
                String telefono = tfTelefono.getText();
                String correo = tfCorreo.getText();

                Empleado empleadoActualizado = new Empleado(nombre, identificacion, salario, telefono, correo);
                empresa.actualizarEmpleado(identificacion, empleadoActualizado);

                int index = empleadosList.indexOf(empleadoExistente);
                empleadosList.set(index, empleadoActualizado);
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Empleado no encontrado.");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Salario debe ser un número.");
        }
    }

    private void eliminarEmpleado() {
        String identificacion = tfIdentificacion.getText();
        boolean eliminado = empresa.eliminarEmpleado(identificacion);

        if (eliminado) {
            empleadosList.removeIf(e -> e.getIdentificacion().equals(identificacion));
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Empleado no encontrado.");
        }
    }

    private void clearForm() {
        tfNombre.clear();
        tfIdentificacion.clear();
        tfSalario.clear();
        tfTelefono.clear();
        tfCorreo.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
