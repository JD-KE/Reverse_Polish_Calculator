import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static final Integer DEFAULT_PORT = 3000;
    public static void main(String[] args) throws Exception {
        int port;
        ExecutorService executor = Executors.newFixedThreadPool(3);

        if (args.length <= 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        System.out.printf("Listening on port: %d%n", port);
        
        //try with resources to auto close server: to deal with server not closed error
        try(ServerSocket server = new ServerSocket(port)) {

            while (true) {
                Socket client = server.accept();
                System.out.println("New client connection");

                Runnable handler = new ClientHandler(client);

                executor.submit(handler);

            }
        }
        
    }
}