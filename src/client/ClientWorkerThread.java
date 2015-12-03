package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by abt273 on 03.12.2015.
 */
public class ClientWorkerThread extends Thread {
    private BufferedReader _inFromServer;
    private Client _client;
    private boolean _serviceRequested;

    //UI
    private ClientUI _clientUI;

    public ClientWorkerThread(BufferedReader inFromServer,ClientUI clientUI, Client client) {
        _inFromServer = inFromServer;
        _client = client;
        _clientUI = clientUI;
    }

    @Override
    public void run()
    {
        while(_serviceRequested)
        {
            try {
                readFromServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readFromServer() throws IOException {
        String message = _inFromServer.readLine();
        if(message != null)
        {
            int code = getCode(message);


            switch(code)
            {
                case 200:
                    updateUserlist(getMessage(message));
                    break;
                case 201:
                    printMessage(getMessage(message));
                    break;
                case 299:
                    disconnect();
                    break;
                default:
                    System.err.println("Fehler im Switch");
            }
        }

    }
    private void updateUserlist(String message)
    {
        String[] userlist = message.split(",");
        for(String user : userlist)
        {
            System.out.println(user);
        }
    }

    private void printMessage(String message)
    {
        _clientUI.showMessage(message);
        System.out.println("Client: " + message);
    }

    private void disconnect() throws IOException {
        _serviceRequested = false;
        _client.closeConnection();
    }

    private int getCode(String message)
    {
        return Integer.parseInt(message.substring(0, 3)); //gets the 3 digit code from the message
    }

    private String getMessage(String message)
    {
        return message.substring(4);
    }
}
