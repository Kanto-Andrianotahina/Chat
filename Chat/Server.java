

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.net.ServerSocket;
    import java.net.Socket;
    import java.util.Scanner;
    
    public class Server {
     
     public static void main(String[] test) {
     
        ServerSocket serverSocket ;
        Socket clientSocket ;
        BufferedReader in;
        PrintWriter out;
        Scanner scan=new Scanner(System.in);
     
        try {
            serverSocket = new ServerSocket(1234);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
            
            Thread envoi= new Thread(new Runnable() {
            String msg;
            @Override

                public void run() {
                    while(true){
                            msg = scan.nextLine();
                            out.println(msg);
                            out.flush();
                    }
                }
            });
            envoi.start();
     
            Thread recevoir= new Thread(new Runnable() {
                String msg ;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("Client : "+msg);
                            msg = in.readLine();
                        }
                        System.out.println("Client déconecté");
                        out.close();
                        clientSocket.close();
                        serverSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();
        
        }catch (IOException e) {
                e.printStackTrace();
        }
    }
}
    