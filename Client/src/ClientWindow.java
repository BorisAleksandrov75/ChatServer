import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow extends JFrame implements ActionListener {

    private final String IP_ADRR = "127.0.0.0";
    private final int PORT = 1234;
    private final int WEDTH = 400;
    private final int WEIGHT = 600;

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

    private ClientWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WEDTH,WEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);

        text.setEditable(false);
        text.setLineWrap(true);

        fieldin.addActionListener(this);
        add(text, BorderLayout.CENTER);
        add(fieldNickname, BorderLayout.NORTH);
        add(fieldin, BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
