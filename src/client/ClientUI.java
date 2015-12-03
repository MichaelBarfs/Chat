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
    private JFrame _frame;
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
     * Show a message in ui.
     * @param message The message to be shown.
     */
    public void showMessage(String message){
        _outputListModel.addElement(message);
    }
}
