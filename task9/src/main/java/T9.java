import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Handler{

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private String calculate(String first, String sign, String second){
        try {
            Float num1 = Float.parseFloat(first);
            Float num2 = Float.parseFloat(second);

            switch (sign) {
                case ("add"):
                    return Float.toString(num1 + num2);
                case ("mult"):
                    return Float.toString(num1 * num2);
                case ("subs"):
                    return Float.toString(num1 - num2);
                case ("divide"):
                    return Float.toString(num1 / num2);
                default:
                    return "Unsupported operation";
            }
        } catch (Exception text){
            return "Invalid input";
        }
    }

    private String calculator(String input){
        String[] strIn = input.split(" ");
        if (strIn.length != 3){
            return "Invalid input";
        }
        return calculate(strIn[0], strIn[1], strIn[2]);
    }

//

    public Handler (Socket socket) throws IOException{
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        run();
    }
    public void run() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                String output = calculator(line);
                out.println("Result: " + output);
                }
            socket.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}

public class T9 {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9080);
        try {
                System.out.println("Server has started on 127.0.0.1:9080 and waiting for a connection...");
            while(true) {
                try {
                    System.out.println("Waiting for request");
                    Socket socket = server.accept();
                    try {
                        new Handler(socket);
                    } catch (IOException e){
                        socket.close();
                    }
                } catch (IOException e){
                    server.close();
                    System.out.println("Error on socket!");
                    break;
                }
            }
        } catch (Exception text){
            System.out.println("Error: " + text);
        }


    }
}