import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorCocina {

    public static final int PUERTO = 6000;

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor de cocina escuchando en el puerto " + PUERTO + "...");

            while (true) {
                Socket clienteSocket = servidor.accept();
                System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());
                new Thread(new ManejadorCliente(clienteSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}