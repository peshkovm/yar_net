import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import ru.csc.java2014.my_yar.MyFileArchiver;

class GUI extends JFrame {
    private DirPathPanel dirPathPanel;
    private ArchPathPanel archPathPanel;
    private SendPathPanel sendPathPanel;
    private JTextField dirField;
    private JTextField archField;
    private JTextField sendField;

    GUI() {
        InputPathsPanel inPathFields = new InputPathsPanel();
        ControlButtons buttons = new ControlButtons();
        getContentPane().add(BorderLayout.CENTER, inPathFields);
        getContentPane().add(BorderLayout.SOUTH, buttons);
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
        }

        private class PackListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                File dir = new File(dirField.getText());
                File archive = new File(archField.getText());

                try {
                    MyFileArchiver archiver = new MyFileArchiver(archive);
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

                try {
                    MyFileArchiver archiver = new MyFileArchiver(archive);
                    archiver.unpack(dir);
                } catch (IOException ex) {
                }
            }
        }

        private class SendListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }
    }
}