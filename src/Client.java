import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import ru.csc.java2014.my_yar.MyFileArchiver;

public class Client {
    public static final int PORT = 5000;
    public static final String HOST = "localhost";
    OutputStream socketOutStream;
    private DirPathPanel dirPathPanel;
    private ArchPathPanel archPathPanel;
    private SendPathPanel sendPathPanel;
    private JTextField dirField;
    private JTextField archField;
    private JTextField sendField;

    public static void main(String[] args) {
        new Client().go();
    }

    private void go() {
        JFrame gui = new JFrame();
        InputPathsPanel inPathFields = new InputPathsPanel();
        ControlButtons buttons = new ControlButtons();
        gui.getContentPane().add(BorderLayout.CENTER, inPathFields);
        gui.getContentPane().add(BorderLayout.SOUTH, buttons);
        gui.setSize(500, 200);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }

    private void setUpNetworking() {
        try {
            Socket socket;

            socket = new Socket(sendField.getText(), PORT);
            if (sendField.getText() == "")
                socket = new Socket(HOST, PORT);

            socketOutStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class InputPathsPanel extends JPanel {

        public InputPathsPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            dirPathPanel = new DirPathPanel();
            //dirPathPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            archPathPanel = new ArchPathPanel();
            //archPathPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            sendPathPanel = new SendPathPanel();
            //sendPathPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            add(dirPathPanel);
            add(archPathPanel);
            add(sendPathPanel);
        }

    }

    private class DirPathPanel extends JPanel {
        DirPathPanel() {
            JLabel label = new JLabel("Directory path: ");
            dirField = new JTextField(20);
            add(label);
            add(dirField);
        }
    }

    private class ArchPathPanel extends JPanel {
        ArchPathPanel() {
            JLabel label = new JLabel("Archive path: ");
            archField = new JTextField(20);
            add(label);
            add(archField);
        }
    }

    private class SendPathPanel extends JPanel {
        SendPathPanel() {
            JLabel label = new JLabel("Send path: ");
            sendField = new JTextField(20);
            add(label);
            add(sendField);
        }
    }

    private class ControlButtons extends JPanel {
        public ControlButtons() {
            JButton packButton = new JButton("pack");
            JButton unpackButton = new JButton("unpack");
            JButton sendButton = new JButton("send");

            packButton.addActionListener(new PackListener());
            unpackButton.addActionListener(new UnpackListener());
            sendButton.addActionListener(new SendListener());

            add(packButton);
            add(unpackButton);
            add(sendButton);
        }

        private class PackListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                File dir = new File(dirField.getText());
                File archive = new File(archField.getText());

                MyFileArchiver archiver = new MyFileArchiver(archive);
                try {
                    archiver.pack(dir);
                } catch (IOException ex) {
                }
            }
        }

        private class UnpackListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                File dir = new File(dirField.getText());
                File archive = new File(archField.getText());

                MyFileArchiver archiver = new MyFileArchiver(archive);
                try {
                    archiver.unpack(dir);
                } catch (IOException ex) {
                }
            }
        }

        private class SendListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpNetworking();
                MyFileArchiver archiver = new MyFileArchiver();
                archiver.setArchOutStream(socketOutStream);

                File dir = new File(dirField.getText());
                try {
                    archiver.pack(dir);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}