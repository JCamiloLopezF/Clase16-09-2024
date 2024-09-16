package co.edu.uniquindio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Empresa {
    private String nombre;
    private List<Empleado> empleados;

    // Constructor
    public Empresa(String nombre) {
        this.nombre = nombre;
        this.empleados = new ArrayList<>();
    }

    // Obtener nombre de la empresa
    public String getNombre() {
        return nombre;
    }

    // Establecer nombre de la empresa
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Agregar un nuevo empleado
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    // Leer un empleado por identificación
    public Empleado obtenerEmpleado(String identificacion) {
        Optional<Empleado> empleado = empleados.stream()
                .filter(e -> e.getIdentificacion().equals(identificacion))
                .findFirst();
        return empleado.orElse(null);
    }

    // Actualizar un empleado
    public boolean actualizarEmpleado(String identificacion, Empleado nuevoEmpleado) {
        for (int i = 0; i < empleados.size(); i++) {
            Empleado empleado = empleados.get(i);
            if (empleado.getIdentificacion().equals(identificacion)) {
                empleados.set(i, nuevoEmpleado);
                return true;
            }
        }
        return false;
    }

    // Eliminar un empleado
    public boolean eliminarEmpleado(String identificacion) {
        return empleados.removeIf(e -> e.getIdentificacion().equals(identificacion));
    }

    // Mostrar todos los empleados
    public void mostrarEmpleados() {
        for (Empleado empleado : empleados) {
            System.out.println(empleado);
        }
    }

    // Método toString para mostrar la información de la empresa
    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", empleados=" + empleados +
                '}';
    }
}
