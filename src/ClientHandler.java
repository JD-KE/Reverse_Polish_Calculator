import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Stack;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Stack<Integer> stack = new Stack<>();    

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    // run cannot directly throw exception
    @Override
    public void run() {

        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void start() throws Exception {
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter ows = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(ows);

        boolean stop = false;

        while(!stop) {
            String line = br.readLine();
            line = line.trim();

            if (line.length() <=0) {
                continue;
            }

            String[] inputs = line.split("\\s+");
            for (String input : inputs) {
                System.out.println(input);
            }


            if ("end".equalsIgnoreCase(inputs[0])) {
                System.out.println("End of session");
                stop = true;
                bw.write("Session Ended\n");
                bw.flush();
                break;
            }

            System.out.println("before putting into stack");

            int result = 0, a = 0, b = 0;

            for (String input : inputs) {
                switch (input) {
                    case "+":                        
                    case "-":                        
                    case "*":
                    case "/":
                        if (stack.size() < 2) continue;
                        a = stack.pop();
                        b = stack.pop();
                        if ("/".equals(input)){
                            result = (int) Math.floor((double) (a/b));
                        } else if ("+".equals(input)){
                            result = a + b;
                        } else if ("-".equals(input)){
                            result = a - b;                            
                        } else {
                            result = a * b;                            
                        }
                        stack.push(result);
                        break;
                    default:
                        stack.push(Integer.parseInt(input));
                        break;
                                        
                }
                
                // if (input.matches("0-9")) {
                //     int e = stack.push(Integer.parseInt(input));
                //     System.out.printf("Pushing %s input", e);
                    
                // } else {
                //     if (stack.size() <= 1) continue;
                //     int a = stack.pop();
                //     int b = stack.pop();
                //     int result = 0;

                //     switch (input) {
                //         case "+":
                //             result = a + b;
                //             stack.push(result);
                //             break;
                //         case "-":
                //             result = a - b;
                //             stack.push(result);
                //             break;
                //         case "*":
                //             result = a * b;
                //             stack.push(result);
                //             break;
                //         case "/":
                //             result = (int) Math.floor((double) (a/b));
                //             stack.push(result);
                //             break;
                //         default:
                //             break;
                                           
                //     }
                // }
            }
            System.out.println("After putting into stack");
            
            System.out.printf("Stack: %s%n", stack.toString());
            System.out.println(result);
            StringBuilder sb = new StringBuilder();
            for (Integer num : stack) {
                sb.append(String.format("%d ", num));
            }
            
            bw.write(sb + "\n");            
            bw.flush();

        }

        is.close();
        os.close();
        socket.close();        
    }
   
}
