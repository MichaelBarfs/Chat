package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is the main class for the chat server. In the main loop we accept new connections and create a worker for each
 * client.
 * @author Marc Kirchner & Michael Barfs
 * @version 2015.12.03
 */
public class Server extends Thread{
    public ServerSocket _socket;
    private Map<ServerWorker, String> _user;
    private Lock _userlock;

    /**
     * Create a new server which is listening on a port.
     * @param port The port.
     * @throws IOException
     */
    public Server(int port) throws IOException {
        _socket = new ServerSocket(port);
        _user = new HashMap<ServerWorker, String>();
        _userlock = new ReentrantLock();
    }

    /**
     * Accept new connections while not interrupted.
     */
    public void run(){
        ServerWorker worker;
        while(!isInterrupted()){
            try {
                worker = new ServerWorker(_socket.accept(), this);
                worker.init();
                worker.start();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add a user to the usermap.
     * @param worker The worker, you want to add.
     * @param username The username off the worker.
     * @return
     */
    public boolean addUser(ServerWorker worker, String username){
        _userlock.lock();
        if(_user.containsValue(username)){
            return false;
        }
        _user.put(worker, username);
        _userlock.unlock();
        return true;
    }

    /**
     * Remove a worker from the usermap.
     * @param worker The worker you want to remove
     */
    public void removeUser(ServerWorker worker){
        _userlock.lock();
        _user.remove(worker);
        _userlock.unlock();
    }

    /**
     * Send a message to all clients.
     * @param message The message you want to send.
     */
    public void sendToAllClients(String message){
        System.out.println("SendToAll: " + message);
        _userlock.lock();
        System.out.println("Lock überwunden");
        for(ServerWorker worker : _user.keySet()){
            try {
                System.out.println("Sende an: " + worker.getName());
                worker.sendToClient(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        _userlock.unlock();
    }

    /**
     * Get a comma separated list of all connected user.
     * @return Userlist
     */
    public String getUserList(){
        String user = "";
        _userlock.lock();
        for(String username : _user.values()){
            user = user + "," + username;
        }
        _userlock.unlock();
        return user.substring(1); // remove leading komma
    }

    public static void main(String args[]){
        try {
            Server server = new Server(12345);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
