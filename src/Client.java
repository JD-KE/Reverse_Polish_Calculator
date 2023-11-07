import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        int port = 3000;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } 
        // else {
        //     System.err.println("Please input port number");
        //     System.exit(1);
        // }

        Socket socket = new Socket("localhost", port);
        CalculatorSession session = new CalculatorSession(socket);

        session.start();

    }
    
}
