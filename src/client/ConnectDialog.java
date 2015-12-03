package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by abs848 on 03.12.2015.
 */
public class ConnectDialog {
    private JDialog _dialog;
    public final JButton _connectBtn;

    public ConnectDialog(String host, int port, String username){
        _dialog = new JDialog();
        _dialog.setSize(300, 300);
        _dialog.setTitle("Serverauswahl");

        GridLayout gridLayout = new GridLayout(4,2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);

        JPanel panel = new JPanel(gridLayout);

        _dialog.add(panel);

        JLabel srvLabel = new JLabel("Server:");
        panel.add(srvLabel);
        final JTextField srvTxt = new JTextField(host);
        panel.add(srvTxt);

        JLabel prtLabel = new JLabel("Port:");
        panel.add(prtLabel);
        final JTextField prtTxt = new JTextField(String.valueOf(port));
        panel.add(prtTxt);

        JLabel usrLabel = new JLabel("User:");
        panel.add(usrLabel);
        final JTextField usrTxt = new JTextField(username);
        panel.add(usrTxt);

        _connectBtn = new JButton("Verbinden");

        panel.add(_connectBtn);

        _dialog.setVisible(true);


        _dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

    }

    public void close(){
        _dialog.setVisible(false);
        _dialog.dispose();
    }
}
