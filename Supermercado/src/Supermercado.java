import java.util.Scanner;

class Humano {
    private String identificacion;
    private int años;

    public Humano(String identificacion, int años) {
        this.identificacion = identificacion;
        this.años = años;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public int getAnios() {
        return años;
    }
}

class Trabajador extends Humano {
    private double salario;

    public Trabajador(String identificacion, int años, double salario) {
        super(identificacion, años);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }
}

class Comprador extends Humano {
    private boolean esVIP;

    public Comprador(String identificacion, int años, boolean esVIP) {
        super(identificacion, años);
        this.esVIP = esVIP;
    }

    public boolean esVIP() {
        return esVIP;
    }
}

class Articulo {
    private String etiqueta;
    private double costo;
    private int stock;

    public Articulo(String etiqueta, double costo, int stock) {
        this.etiqueta = etiqueta;
        this.costo = costo;
        this.stock = stock;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public double getCosto() {
        return costo;
    }

    public int getStock() {
        return stock;
    }

    public void actualizarStock(int cantidadVendida) {
        this.stock -= cantidadVendida;
    }
}

class PuntoDeVenta {
    private Trabajador empleado;

    public PuntoDeVenta(Trabajador empleado) {
        this.empleado = empleado;
    }

    public Trabajador getEmpleado() {
        return empleado;
    }

    public double calcularTotal(Comprador comprador, Articulo[] articulos, int[] cantidades) {
        double suma = 0;

        for (int i = 0; i < articulos.length; i++) {
            Articulo articulo = articulos[i];
            int cantidadVendida = cantidades[i];
            suma += articulo.getCosto() * cantidadVendida;
            articulo.actualizarStock(cantidadVendida);
        }

        if (comprador.esVIP()) {
            suma *= 0.6;
        }

        return suma;
    }
}

class Operacion {
    private Comprador comprador;
    private Articulo[] articulos;
    private int[] cantidades;
    private double monto;
    private PuntoDeVenta puntoDeVenta;

    public Operacion(Comprador comprador, Articulo[] articulos, int[] cantidades, double monto, PuntoDeVenta puntoDeVenta) {
        this.comprador = comprador;
        this.articulos = articulos;
        this.cantidades = cantidades;
        this.monto = monto;
        this.puntoDeVenta = puntoDeVenta;
    }

    public void imprimirResumen() {
        System.out.println("Comprador: " + comprador.getIdentificacion());
        System.out.println("Atendido por: " + puntoDeVenta.getEmpleado().getIdentificacion());
        System.out.println("Artículos adquiridos:");
        for (int i = 0; i < articulos.length; i++) {
            Articulo articulo = articulos[i];
            int cantidadVendida = cantidades[i];
            System.out.println("- " + articulo.getEtiqueta() + " x" + cantidadVendida + " - $" + articulo.getCosto() * cantidadVendida);
        }
        System.out.println("Monto total: $" + monto);
    }
}

public class Supermercado {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Trabajador vendedor = new Trabajador("María", 28, 75000);

        System.out.println();
        System.out.println("¿Es usted un cliente VIP? Seleccione 1: Sí o 2: No");
        String opcion = entrada.nextLine();

        boolean esVIP = false;
        if (opcion.equals("1") || opcion.equalsIgnoreCase("Si")) {
            esVIP = true;
            System.out.println("Excelente, lo registraremos como cliente VIP");
        }

        System.out.println("¿Cuál es su nombre para registrarlo?");
        String nombreComprador = entrada.next();

        Comprador comprador = new Comprador(nombreComprador, 40, esVIP);

        Articulo[] inventario = {
                new Articulo("Yogurt", 800, 1000),
                new Articulo("Galletas", 1100, 543),
                new Articulo("Cerveza", 1500.50, 504380)
        };

        int[] unidades = new int[inventario.length];
        for (int i = 0; i < inventario.length; i++) {
            System.out.println("¿Cuántas unidades de " + inventario[i].getEtiqueta() + " desea adquirir?");
            unidades[i] = entrada.nextInt();
        }

        PuntoDeVenta caja = new PuntoDeVenta(vendedor);
        double total = caja.calcularTotal(comprador, inventario, unidades);

        Operacion venta = new Operacion(comprador, inventario, unidades, total, caja);
        venta.imprimirResumen();

        entrada.close();
    }
}