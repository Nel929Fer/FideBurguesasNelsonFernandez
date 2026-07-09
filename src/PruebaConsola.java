import java.io.IOException;

/**
 * Clase de prueba en consola. No es parte del diseño de clases del proyecto,
 * solo sirve para verificar que Usuario, Producto, Combo, Orden, DetalleOrden
 * y Factura funcionan bien juntos antes de construir la interfaz gráfica.
 */
public class PruebaConsola {

    public static void main(String[] args) {

        System.out.println("=== 1. Probando login ===");
        Usuario cajero = new Usuario(1, "Nelson Fernandez", "nelson", "1234", "Sede Central");

        try {
            cajero.iniciarSesion("nelson", "clave_incorrecta");
        } catch (CredencialesInvalidasException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        try {
            cajero.iniciarSesion("nelson", "1234");
            System.out.println("Login correcto. Sesion activa: " + cajero.isSesionActiva());
        } catch (CredencialesInvalidasException e) {
            System.out.println("Esto no debería pasar: " + e.getMessage());
        }

        System.out.println("\n=== 2. Creando productos y combo ===");
        Producto hamburguesa = new Producto(1, "Hamburguesa", "Carne, queso, vegetales", 2500, "Principal");
        Producto papas = new Producto(2, "Papas", "Papas fritas medianas", 1000, "Acompañamiento");

        Combo comboClasico = new Combo(1, "Combo Clasico", "Hamburguesa + Papas", 5000);
        comboClasico.agregarProducto(hamburguesa);
        comboClasico.agregarProducto(papas);

        System.out.println(hamburguesa); // usa toString() de Producto
        System.out.println(comboClasico); // usa toString() de Combo (polimorfismo)

        System.out.println("\n=== 3. Creando orden con productos y combo mezclados ===");
        Orden orden = new Orden(1, cajero);

        // Fijate: agregamos un Producto y un Combo al mismo tipo de lista (ItemMenu)
        orden.agregarDetalle(new DetalleOrden(1, hamburguesa, 1));
        orden.agregarDetalle(new DetalleOrden(2, comboClasico, 1));

        for (DetalleOrden detalle : orden.getListaDetalles()) {
            System.out.println(detalle); // toString() distinto según sea Producto o Combo
        }

        try {
            System.out.println("Total de la orden: ₡" + orden.calcularTotal());
        } catch (OrdenVaciaException e) {
            System.out.println("Esto no debería pasar: " + e.getMessage());
        }

        System.out.println("\n=== 4. Generando factura ===");
        try {
            Factura factura = new Factura(1, orden, cajero);
            System.out.println("Subtotal: ₡" + factura.getTotal());
            System.out.println("Impuesto: ₡" + factura.getImpuesto());
            System.out.println("Total con impuesto: ₡" + factura.getTotalConImpuesto());

            factura.generarArchivo();
            System.out.println("Factura exportada a archivo .txt correctamente.");
        } catch (OrdenVaciaException | IOException e) {
            System.out.println("Error generando factura: " + e.getMessage());
        }

        System.out.println("\n=== 5. Probando orden vacía (excepción) ===");
        Orden ordenVacia = new Orden(2, cajero);
        try {
            ordenVacia.calcularTotal();
        } catch (OrdenVaciaException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }
}