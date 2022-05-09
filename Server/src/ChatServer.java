import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectoinListner {

    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPconnection> conectionLIst = new ArrayList<>();

    private ChatServer() {
        System.out.println("Waiting...");
        try(ServerSocket serverSocket = new ServerSocket(1234) ) {
            while (true) {
                try {
                    new TCPconnection(serverSocket.accept(), this);
                } catch (IOException e) {
                    System.out.println("TCPConnection " + e);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectoinReady(TCPconnection tcPconnection) {
        conectionLIst.add(tcPconnection);
        sendAll("Client conected " + tcPconnection);
    }

    @Override
    public synchronized void onReiceveString(TCPconnection tcPconnection, String message) {
        sendAll(message);
    }

    @Override
    public synchronized void onDisconnect(TCPconnection tcPconnection) {
        conectionLIst.remove(tcPconnection);
        sendAll("Clien disconncted " + tcPconnection);
    }

    @Override
    public synchronized void onException(TCPconnection tcPconnection, Exception e) {
        System.out.println("TCPconnection Exception " + e);
    }

    void sendAll (String message) {
        System.out.println(message);
        for (TCPconnection tcPconnection : conectionLIst) {
            tcPconnection.sendMessage(message);
        }
    }
}
