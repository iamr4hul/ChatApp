import java.net.*;
import java.io.*;
public class Desktop1 {
    // Constructor
    BufferedReader br; // read the data from desktop2
    PrintWriter out;   // write the data from desktop1
    public Desktop1() throws IOException {
        ServerSocket server = new ServerSocket(6666);
        System.out.println("ready to accept connection");
        System.out.println("waiting...");
        Socket socket = server.accept();


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
                    System.out.println("Desktop2 end the chat");
                    break;

                }
                System.out.println("Desktop2 : "+msg);
            }
        };
        new Thread(r2).start();
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Desktop1 is going to started...");
        new Desktop1();
    }
}
