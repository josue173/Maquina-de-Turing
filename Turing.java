import java.io.*;
import java.util.*;

class Transicion {
    char simboloLeido;
    char simboloEscrito;
    String siguienteEstado;
    char movimiento; // 'L' para izquierda, 'R' para derecha

    public Transicion(char simboloLeido, char simboloEscrito, String siguienteEstado, char movimiento) {
        this.simboloLeido = simboloLeido;
        this.simboloEscrito = simboloEscrito;
        this.siguienteEstado = siguienteEstado;
        this.movimiento = movimiento;
    }

    @Override
    public String toString() {
        return simboloLeido + " -> " + simboloEscrito + ", " + siguienteEstado + ", " + movimiento;
    }
}

class TuringMachine {
    private String estadoInicial;
    private Set<String> estadosAceptacion;
    private Map<String, Transicion> transiciones;

    public TuringMachine(String estadoInicial, Set<String> estadosAceptacion, Map<String, Transicion> transiciones) {
        this.estadoInicial = estadoInicial;
        this.estadosAceptacion = estadosAceptacion;
        this.transiciones = transiciones;
    }

    public void imprimirConfiguracion() {
        System.out.println("Estado Inicial: " + estadoInicial);
        System.out.println("Estados de aceptación: " + String.join(", ", estadosAceptacion));
        System.out.println("Tabla de transicion de estados:");
        
        // Encabezado de la tabla
        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "Estado Inicial", "Símbolo Leído", "Nuevo Estado", "Símbolo Escrito", "Movimiento");
        System.out.println("---------------------------------------------------------------------");

        // Contenido de las transiciones en forma de tabla
        for (Map.Entry<String, Transicion> entrada : transiciones.entrySet()) {
            String estadoInicial = entrada.getKey().split(",")[0];
            char simboloLeido = entrada.getValue().simboloLeido;
            String nuevoEstado = entrada.getValue().siguienteEstado;
            char simboloEscrito = entrada.getValue().simboloEscrito;
            char movimiento = entrada.getValue().movimiento;
            
            System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", estadoInicial, simboloLeido, nuevoEstado, simboloEscrito, movimiento);
        }
    }

    public static TuringMachine cargarDesdeArchivo(String rutaArchivo) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
        String estadoInicial = lector.readLine();
        
        // Leer estados de aceptación
        Set<String> estadosAceptacion = new HashSet<>(Arrays.asList(lector.readLine().split(",")));
        
        // Leer transiciones
        Map<String, Transicion> transiciones = new HashMap<>();
        String linea;
        while ((linea = lector.readLine()) != null) {
            String[] partes = linea.split(",");
            String estadoActual = partes[0];
            char simboloLeido = partes[1].charAt(0);
            char simboloEscrito = partes[2].charAt(0);
            String siguienteEstado = partes[3];
            char movimiento = partes[4].charAt(0);
            transiciones.put(estadoActual + "," + simboloLeido, new Transicion(simboloLeido, simboloEscrito, siguienteEstado, movimiento));
        }
        lector.close();
        return new TuringMachine(estadoInicial, estadosAceptacion, transiciones);
    }
}

public class Turing {
    public static void main(String[] args) {
        try {
            TuringMachine maquina = TuringMachine.cargarDesdeArchivo("datos.txt");
            
            // Imprimir la configuración inicial de la máquina en formato de tabla
            maquina.imprimirConfiguracion();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
