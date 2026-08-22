import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class ManejadorCliente implements Runnable {

    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                System.out.println("Comando recibido: " + linea);

                if (linea.equals("LISTAR")) {
                    List<String> ordenes = OrdenDAO.listarPendientesTexto();
                    salida.println(ordenes.size());
                    for (String o : ordenes) salida.println(o);

                } else if (linea.startsWith("LISTA:")) {
                    int id = Integer.parseInt(linea.substring(6));
                    OrdenDAO.marcarComoLista(id);
                    salida.println("OK");

                } else if (linea.equals("SALIR")) {
                    break;

                } else {
                    salida.println("ERROR:Comando desconocido");
                }
            }
        } catch (IOException | SQLException e) {
            System.out.println("Error con cliente: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException e) { }
        }
    }
}