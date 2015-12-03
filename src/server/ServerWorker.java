package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 * @author Marc Kirchner & Michael Barfs
 * @version 2015.12.03
 */
public class ServerWorker extends Thread {
    private DataOutputStream _outToClient;
    private BufferedReader _inFromClient;
    private Socket _socket;
    private Server _server;
    private String _username;

    public ServerWorker(Socket socket, Server server) throws IOException {
        _server = server;
        _socket = socket;
        _outToClient = new DataOutputStream(socket.getOutputStream());
        _inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Init a new connection.
     * @return True, when everything went well otherwise false.
     * @throws IOException
     */
    public boolean init()throws IOException {
        String command = _inFromClient.readLine();
        String code = command.substring(0,3);
        if(code.equals("100")){ // Valid connect request?
            _username = command.substring(4);

            if(Pattern.matches("(\\w-_\\.)+", _username)){ // Is valid username?
                if(_server.addUser(this, _username)){ // username not in use?
                    _server.sendToAllClients("200 " + _server.getUserList());
                }else{ // username already in use
                    sendToClient("301 Username not available");
                    return init();
                }
            }else{ // invalid username
                sendToClient("300 Username invalid");
                return init();
            }

        }else{ // Wrong command
            _socket.close();
            return false;
        }
        return true;
    }

    /**
     * Mainloop check for new messages from client and execute commands.
     */
    public void run(){
        while(!isInterrupted()){
            try {
                String command = _inFromClient.readLine();
                String code = command.substring(0, 3);
                switch (code){
                    case "101":
                        _server.sendToAllClients("201 " + command.substring(4));
                    break;
                    case "102":
                        sendToClient("202 " + _server.getUserList());
                    break;
                    case "199":
                        disconnect();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void disconnect() throws IOException {
        _server.sendToAllClients("299 " + _username);
        _socket.close();
        this.interrupt();
    }

    public synchronized void sendToClient(String message) throws IOException {
        _outToClient.writeUTF(message);
    }

}
