import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CalculatorSession {
    private final Socket socket;

    public CalculatorSession(Socket socket) {
        this.socket = socket;
    }

      public void start() throws Exception {

        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        boolean stop = false;
        Console cons = System.console();
        while (!stop) {
            String line = cons.readLine("> ");
            line = line.trim() + "\n";

            bw.write(line);
            bw.flush();

            String result = br.readLine();
            result = result.trim();
            
            System.out.printf(">result: %s\n", result);

            
            
        }
    }
    
}
    

