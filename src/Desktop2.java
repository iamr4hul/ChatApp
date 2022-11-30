import com.sun.source.tree.Scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Desktop2 {

    BufferedReader br; // read the data from desktop1
    PrintWriter out;   // write the data from desktop2

    public Desktop2() throws IOException {
        System.out.println("Sending request to Desktop1");
        Socket socket = new Socket("127.0.0.1",6666);
        System.out.println("Connection done!!!");


        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
        startReading();
        startWriting();
    }
    private void startWriting() {
        // thread-> data ko write karke deta rahega
        Runnable r1 =()->{
            System.out.println("writer started...");
            while (true){
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }

    private void startReading() {
        // thread-> data ko read karke deta rahega
        Runnable r2=()->{
            System.out.println("reader started...");
            while (true){
                String msg = null;
                try {
                    msg = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(msg.equals("exit")){
                    System.out.println("Desktop1 end the chat");
                    break;
                }
                System.out.println("Desktop1 : "+msg);
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Going to starting the Desktop2...");
        new Desktop2();
    }
    }
