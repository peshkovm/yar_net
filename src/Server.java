import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ru.csc.java2014.my_yar.MyFileArchiver;

public class Server {
    public static final int PORT = 5000;
    private DirPathPanel dirPathPanel;
    private ArchCheckPanel archCheckPanel;
    private JTextField dirField;
    private JTextField archSizeField;
    private JCheckBox archCheckBox;
    InputStream socketInStream;

    public static void main(String[] args) {
        new Server().go();
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
        setUpNetworking();
    }

    private void setUpNetworking() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket = serverSocket.accept();
            socketInStream = clientSocket.getInputStream();
            archCheckBox.setSelected(true);
            archSizeField.setText(clientSocket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class InputPathsPanel extends JPanel {
        public InputPathsPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            dirPathPanel = new DirPathPanel();
            //dirPathPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            archCheckPanel = new ArchCheckPanel();
            //archCheckPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            add(dirPathPanel);
            add(archCheckPanel);
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

    private class ArchCheckPanel extends JPanel {
        ArchCheckPanel() {
            archCheckBox = new JCheckBox("Archive received", false);
            archCheckBox.setEnabled(false);
            archSizeField = new JTextField(20);
            add(archCheckBox);
            add(archSizeField);
        }
    }

    private class ControlButtons extends JPanel {
        public ControlButtons() {
            JButton unpackButton = new JButton("unpack");

            unpackButton.addActionListener(new UnpackListener());

            add(unpackButton);
        }

        private class UnpackListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFileArchiver archiver = new MyFileArchiver();
                archiver.setArchInStream(socketInStream);

                File dir = new File(dirField.getText());
                try {
                    archiver.unpack(dir);
                } catch (IOException ex) {
                }
            }
        }
    }
}