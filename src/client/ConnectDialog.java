package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Michael Barfs & Marc Kirchner
 * @version 2015.12.03
 */
public class ConnectDialog {
    private JDialog _dialog;
    private JTextField _srvTxt;
    private JTextField _prtTxt;
    private JTextField _usrTxt;

    public final JButton _connectBtn;


    /**
     * Create a new ConnectDialog with default values
     * @param host The default host address.
     * @param port The default host port.
     * @param username The default username.
     */
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
        _srvTxt = new JTextField(host);
        panel.add(_srvTxt);

        JLabel prtLabel = new JLabel("Port:");
        panel.add(prtLabel);
        _prtTxt = new JTextField(String.valueOf(port));
        panel.add(_prtTxt);

        JLabel usrLabel = new JLabel("User:");
        panel.add(usrLabel);
        _usrTxt = new JTextField(username);
        panel.add(_usrTxt);

        _connectBtn = new JButton("Verbinden");

        panel.add(_connectBtn);

        _dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

    }

    /**
     * Get the username from TextField.
     * @return The username from textfield.
     */
    public String getServer() {
        return _srvTxt.getText();
    }

    /**
     * Get the port from textfield.
     * @return The Port from textfield.
     */
    public String getPort() {
        return _prtTxt.getText();
    }

    /**
     * Get the username from textfield.
     * @return The username from textfield.
     */
    public String getUsername() {
        return _usrTxt.getText();
    }

    /**
     * Make the dialog visible
     */
    public void show(){
        _dialog.setVisible(true);
    }

    /**
     * Close the window
     */
    public void close(){
        _dialog.setVisible(false);
        _dialog.dispose();
    }
}
