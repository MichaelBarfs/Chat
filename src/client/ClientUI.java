package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The ui for the Client.
 * @author Marc Kirchner & Michael Barfs
 * @version 2015.12.03
 */
public class ClientUI {
    public JFrame _frame;
    public final JButton _sendBtn;
    public final JTextField _inputField;
    private DefaultListModel<String> _outputListModel;
    private DefaultListModel<String> _userListModel;


    /**
     * Create a new ClientUI object with a title
     * @param title The title of the Window.
     */
    public ClientUI(String title){
        _frame = new JFrame(title);
        _frame.setSize(600, 600);
        _frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });


        /* Top */
        _outputListModel = new DefaultListModel<String>();
        JList<String> outputList = new JList<String>(_outputListModel);
        JScrollPane outputListScroller = new JScrollPane(outputList);
        _frame.add(outputListScroller, BorderLayout.CENTER);

        _userListModel = new DefaultListModel<String>();
        JList<String> userList = new JList<String>(_userListModel);
        JScrollPane userListScroller = new JScrollPane(userList);
        _frame.add(userListScroller, BorderLayout.EAST);


        /* Bottom */
        GridLayout outputLayout = new GridLayout(1,2);
        outputLayout.setHgap(10);
        outputLayout.setVgap(10);

        JPanel inputPanel = new JPanel(outputLayout);
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        _frame.add(inputPanel, BorderLayout.SOUTH);

        _inputField = new JTextField();
        inputPanel.add(_inputField);

        _sendBtn = new JButton("Send");
        inputPanel.add(_sendBtn);
    }

    /**
     * Show the ui.
     */
    public void show(){
        _frame.setVisible(true);
    }

    /**
     * Close the ui.
     */
    public void close(){
        _frame.setVisible(false);
        _frame.dispose();
    }

    /**
     * Returns deletes the text from textfield and returns it.
     * @return Text from textfield
     */
    public String getInput()
    {
        String input = _inputField.getText();
        _inputField.setText("");
        _inputField.requestFocus();
        return input;
    }

    /**
     * Show a message in ui.
     * @param message The message to be shown.
     */
    public void showMessage(String message){
        _outputListModel.addElement(message);
    }

    /**
     * Remove an user from ui.
     * @param user The user you want to remove.
     */
    public void removeUser(String user){
        _userListModel.removeElement(user);
    }

    /**
     * Add a user to ui.
     * @param user The user you want to add.
     */
    public void addUser(String user){
        _userListModel.addElement(user);
    }

    /**
     * Clears the userlist
     */
    public void clearUserList()
    {
        _userListModel.clear();
    }
}
