import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GreetServer implements AutoCloseable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        if ("hello server".equals(greeting)) {
            out.println("hello client");
        } else {
            out.println("unrecognised greeting");
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        while (true) {
            try (GreetServer server = new GreetServer()) {
                try {
                    server.start(6666);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
