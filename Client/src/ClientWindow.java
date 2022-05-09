import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectoinListner {

    private final String IP_ADRR = "0.0.0.0";
    private final int PORT = 1234;
    private final int WEDTH = 400;
    private final int WEIGHT = 300;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }
    private final JTextArea text = new JTextArea();
    private final JTextField fieldNickname = new JTextField("Enter name");
    private final JTextField fieldin = new JTextField();

    TCPconnection connection;

    private ClientWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WEDTH,WEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);

        try {
            connection = new TCPconnection(this, IP_ADRR,PORT);
        } catch (IOException e) {
            printmessage("Connection exception " + e);
        }

        text.setEditable(false);
        text.setLineWrap(true);

        fieldin.addActionListener(this);
        add(text, BorderLayout.CENTER);
        add(fieldNickname, BorderLayout.NORTH);
        add(fieldin, BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String message = fieldin.getText();
        if (message.equals("")){
            return;
        }
        fieldin.setText(null);
        connection.sendMessage(fieldNickname.getText() + " said: " + message);
    }

    @Override
    public void onConnectoinReady(TCPconnection tcPconnection) {
        printmessage("Conection ready");
    }

    @Override
    public void onReiceveString(TCPconnection tcPconnection, String message) {
        printmessage(message);
    }

    @Override
    public void onDisconnect(TCPconnection tcPconnection) {
        printmessage("Connection close");
    }

    @Override
    public void onException(TCPconnection tcPconnection, Exception e) {
        printmessage("Connection exception " + e);
    }

    private synchronized void printmessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                text.append(message + "\n");
                text.setCaretPosition(text.getDocument().getLength());
            }
        });
    }
}
