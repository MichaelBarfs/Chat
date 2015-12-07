package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Created by abs848 on 03.12.2015.
 */
public class Client {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Socket _socket;
    private BufferedReader _inFromServer;
    private DataOutputStream _outToServer;
    private boolean _serviceRequested;

    //Threads
    private ClientWorkerThread _clientThread;

    //UI
    private ClientUI _clientUI;
    private ConnectDialog _conDialog;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Client(){
        _serviceRequested = true;
        //ClientUI ui = new ClientUI("Test");
        //ui.show();
    }

    public static void main(String args[]){
        Client client = new Client();
        System.out.println("Client Objekt erzeugt.");

        client.showConnectionDialog();
    }

    public void showConnectionDialog()
    {
        _conDialog = new ConnectDialog("localhost", 12345, "Anonymous"); //Parameter = Default Werte
        _conDialog._connectBtn.addActionListener(actionEvent -> {

            boolean valid = true; //Is set to false if one of the fields is empty

            if(_conDialog.getUsername().equals(""))
            {
                valid = false;
                //TODO Fehlermeldung username == ""
            }

            if(_conDialog.getServer().equals(""))
            {
                valid = false;
                //TODO Fehlermeldung hostadresse == ""
            }

            if(_conDialog.getPort().equals(""))
            {
                valid = false;
                //TODO Fehlermeldung wenn kein Port oder leer
            }

            if (valid)
            {
                try {
                    startTCPConnection(_conDialog.getServer(), Integer.parseInt(_conDialog.getPort()), _conDialog.getUsername());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        _conDialog.show(); //Shows the connection dialog
    }

    public void startTCPConnection(String host, int port, String username) throws IOException {
        _socket = new Socket(host, port); //Creates socket for client/server communication
        _inFromServer = new BufferedReader(new InputStreamReader(_socket.getInputStream(), "UTF-8"));
        _outToServer = new DataOutputStream(_socket.getOutputStream());
        System.out.println("Socket und Streams erzeugt!");




        _clientUI = new ClientUI(username + "  Host: " + host + "   Port: " + port); //Initialisiert Chatfenster

        _clientThread = new ClientWorkerThread(_inFromServer,_clientUI, this);
        _clientThread.start(); //TODO fenster erst zeigen wenn connected wurde

        _clientUI._frame.addWindowListener(new WindowAdapter() { //Fenster-Schliessen-Button definieren
            @Override
            public void windowClosing(WindowEvent e) {
                //_clientUI.close();
                try {
                    disconnect();
                    _serviceRequested = false;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        _clientUI._sendBtn.addActionListener(actionEvent -> { //Send-Button definieren
            try {
                sendMessage(_clientUI.getInput());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        startClient(host, port, username);
        _conDialog.close();
    }
    /**
    Connects to server
     */
    private void connect(String username) throws IOException {
        writeToServer("100 " + username); //Protocol for connecting to server
    }

    /**
    Disconnects from server
     */
    private void disconnect() throws IOException
    {
        writeToServer("199"); //Protocol for disconnecting from server
    }

    private void sendMessage(String message) throws IOException {
        writeToServer("101 " + message);
    }

    private synchronized void writeToServer(String message) throws IOException {
        message = message + '\r' + '\n';
        _outToServer.write(message.getBytes("UTF-8")); //Sends the given message to the server (encoded in UTF8)
        System.out.println("Write to Server: " + message);
    }

    private void startClient(String host, int port, String username) throws IOException {

        _clientUI.show(); //Zeigt Chatfenster

        System.out.println("Connect to server...");
        connect(username); //Verbindet mit dem Server (Handshake)
    }

    public void closeConnection() throws IOException {
        _socket.close();
    }
}
