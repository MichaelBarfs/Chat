package client;

/**
 * Created by abs848 on 03.12.2015.
 */
public class Client {
    //private Socket _socket;
    //private BufferR

    public Client(){
        ClientUI ui = new ClientUI("Test");
        ui.show();
    }

    public static void main(String args[]){
        Client client = new Client();
        ConnectDialog con = new ConnectDialog("", 0, "");
        System.out.println("Ich bin Michael :=)");
    }
}
