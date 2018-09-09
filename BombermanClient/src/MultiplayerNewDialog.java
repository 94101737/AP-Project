import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiplayerNewDialog extends JDialog {
    JPanel DialogPanel;
    GridLayout DialogLayout;
    JLabel GameLabel;
    JComboBox<String> GameComboBox;
    JLabel NameLabel;
    JTextField NameTextField;
    JButton AsViewer;
    JButton AsPlayer;

    MultiplayerNewDialog(JFrame MyFrame, String title){
        super(MyFrame,title,true);
        this.setSize(GameFrame.MyFrame.getBounds().width/5, GameFrame.MyFrame.getBounds().height/4);
        this.setLocation(4*GameFrame.MyFrame.getBounds().width/10, 4*GameFrame.MyFrame.getBounds().height/10);
        DialogPanel = new JPanel();
        DialogPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        DialogLayout = new GridLayout(3,2,20,20);
        DialogPanel.setLayout(DialogLayout);

        GameLabel = new JLabel("Choose game : ", JLabel.LEFT);
        GameLabel.setFont(GameFrame.f);
        DialogPanel.add(GameLabel);

        GameComboBox = new JComboBox<>();
        for(int i=1; i<=GameFrame.NumberOfGamesInSelectedIP;i++){
            GameComboBox.addItem(String.valueOf(i));
        }
        GameComboBox.setFont(GameFrame.f);
        DialogPanel.add(GameComboBox);
        GameFrame.TargetPanelIndex = 1;

        NameLabel = new JLabel("Choose a name : ");
        NameLabel.setFont(GameFrame.f);
        DialogPanel.add(NameLabel);

        NameTextField = new JTextField();
        NameTextField.setFont(GameFrame.f);
        NameTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogPanel.add(NameTextField);

        AsViewer = new JButton("Join as viewer");
        AsViewer.setFont(GameFrame.f);
        AsViewer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.TargetPanelIndex = GameComboBox.getSelectedIndex()+1;
                try {
                    Socket socket = new Socket(GameFrame.SelectedIP,GameFrame.SelectedPort);
                    try{
                        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
                        printWriter.println(7);
                        printWriter.println(GameFrame.TargetPanelIndex);
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                    GameFrame.MyPanels.add(new ClientPanel(socket,false,GameFrame.SelectedIP,GameFrame.SelectedPort));
                    GameFrame.MotherPanel.add(GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1) , String.valueOf(GameFrame.MyPanels.size()-1));
                    GameFrame.MyCardLayout.last(GameFrame.MotherPanel);
                    GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1).requestFocusInWindow();
                    GameFrame.MotherPanel.repaint();
                    GameFrame.MyFrame.repaint();
                }catch (Exception exc){
                    exc.printStackTrace();
                }

                MultiplayerNewDialog.this.setVisible(false);
            }
        });
        AsViewer.setFont(GameFrame.f);
        DialogPanel.add(AsViewer);

        AsPlayer = new JButton("Join as player");
        AsPlayer.setFont(GameFrame.f);
        AsPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.TargetPanelIndex = GameComboBox.getSelectedIndex()+1;
                try {
                    Socket socket = new Socket(GameFrame.SelectedIP,GameFrame.SelectedPort);
                    try{
                        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
                        printWriter.println(8);
                        printWriter.println(GameFrame.TargetPanelIndex);
                        printWriter.println(NameTextField.getText());
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                    GameFrame.MyPanels.add(new ClientPanel(socket,true,GameFrame.SelectedIP,GameFrame.SelectedPort));
                    GameFrame.MotherPanel.add(GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1) , String.valueOf(GameFrame.MyPanels.size()-1));
                    GameFrame.MyCardLayout.last(GameFrame.MotherPanel);
                    GameFrame.CurrentPanelIndex = GameFrame.MyPanels.size()-1;
                    GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1).requestFocusInWindow();
                    GameFrame.MotherPanel.repaint();
                    GameFrame.MyFrame.repaint();
                    GameFrame.Messenger.setEnabled(true);
                }catch (Exception exc){
                    exc.printStackTrace();
                }

                MultiplayerNewDialog.this.setVisible(false);
            }
        });
        AsPlayer.setFont(GameFrame.f);
        DialogPanel.add(AsPlayer);
        this.add(DialogPanel);
    }
}
