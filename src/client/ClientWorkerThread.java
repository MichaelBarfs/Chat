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
        _serviceRequested = true;
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
                    String username = getMessage(message);
                    if(username.equals(_client.getUsername()))
                    {
                        disconnect();
                        _serviceRequested = false;
                    }
                    else
                    {
                        _clientUI.removeUser(username);
                    }
                    break;
                case 300:
                    _clientUI.showMessage(getMessage(message));
                    break;
                case 301:
                    _clientUI.showMessage(getMessage(message));
                    break;
                default:
                    System.err.println("Fehler im Switch");
            }
        }

    }
    private void updateUserlist(String message)
    {
            String[] userlist = message.split(",");
            _clientUI.clearUserList();
            for(String user : userlist)
            {
                _clientUI.addUser(user);
            }
    }

    private void printMessage(String message)
    {
        _clientUI.showMessage(message);
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
