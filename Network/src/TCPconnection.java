import java.io.*;
import java.net.Socket;

public class TCPconnection {

    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectoinListner eventListner;
    private final BufferedReader in;
    private final BufferedWriter out;

    public TCPconnection(TCPConnectoinListner eventListner, String ipAdr, int port) throws  IOException {
        this(new Socket(ipAdr,port), eventListner);
    }

    public TCPconnection(Socket socket, TCPConnectoinListner eventListner) throws IOException {
        this.eventListner = eventListner;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListner.onConnectoinReady(TCPconnection.this);
                    while (!rxThread.isInterrupted()) {
                        String message = in.readLine();
                        eventListner.onReiceveString(TCPconnection.this, message);
                    }
                } catch (IOException e) {
                    eventListner.onException(TCPconnection.this, e);
                } finally {
                    eventListner.onDisconnect(TCPconnection.this);
                }
            }
        });
        rxThread.start();
    }

    public synchronized void sendMessage (String message) {
        try {
            out.write(message + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListner.onException(TCPconnection.this, e );
            disconnect();
        }
    }

    public synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListner.onException(TCPconnection.this, e);
        }
    }

    @Override
    public String toString() {
        return "TCPConect: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
