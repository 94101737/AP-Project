import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class MultiplayerDialog extends JDialog {
    JPanel DialogPanel;
    GridLayout DialogLayout;
    String MinimumIP;
    String MaximumIP;
    String Port;
    JTextField MinimumIPTextField;
    JTextField MaximumIPTextField;
    JTextField PortTextField;
    JLabel MinimumIPLabel;
    JLabel MaximumIPLabel;
    JLabel PortLabel;
    JButton SearchButton;
    JComboBox<String> IPComboBox;
    JButton CancelButton;
    JButton ConnectButton;

    public long IPAddressToInteger (String IP){
        String[] auxiliaryArray = IP.split("\\.");
        long result = 0L;
        for(int i=0;i<auxiliaryArray.length;i++){
            result+=((Long.parseLong(auxiliaryArray[i]))*((int) Math.pow(256,3-i)));
        }
        return result;
    }
    public String IntegerToIPAddress (long Integer){
        String[] auxiliaryArray = new String[4];
        auxiliaryArray[3] = String.valueOf(Integer%256);
        Integer/=256;
        auxiliaryArray[2] = String.valueOf(Integer%256);
        Integer/=256;
        auxiliaryArray[1] = String.valueOf(Integer%256);
        Integer/=256;
        auxiliaryArray[0] = String.valueOf(Integer%256);
        String result = auxiliaryArray[0]+"."+auxiliaryArray[1]+"."+auxiliaryArray[2]+"."+auxiliaryArray[3];
        return result;
    }

    MultiplayerDialog(JFrame MyFrame, String title){
        super(MyFrame,title,true);
        this.setSize(GameFrame.MyFrame.getBounds().width/3, 5*GameFrame.MyFrame.getBounds().height/12);
        this.setLocation(4*GameFrame.MyFrame.getBounds().width/10, 4*GameFrame.MyFrame.getBounds().height/10);
        DialogPanel = new JPanel();
        DialogPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        DialogLayout = new GridLayout(5,2,20,20);
        DialogPanel.setLayout(DialogLayout);

        MinimumIPLabel = new JLabel("Minimum of IPv4 address : ", JLabel.LEFT);
        MinimumIPLabel.setFont(GameFrame.f);
        DialogPanel.add(MinimumIPLabel);

        MinimumIPTextField = new JTextField();
        MinimumIPTextField.setFont(GameFrame.f);
        MinimumIPTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogPanel.add(MinimumIPTextField);

        MaximumIPLabel = new JLabel("Maximum of IPv4 address : ", JLabel.LEFT);
        MaximumIPLabel.setFont(GameFrame.f);
        DialogPanel.add(MaximumIPLabel);

        MaximumIPTextField = new JTextField();
        MaximumIPTextField.setFont(GameFrame.f);
        MaximumIPTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogPanel.add(MaximumIPTextField);

        PortLabel = new JLabel("Port Number : ", JLabel.LEFT);
        PortLabel.setFont(GameFrame.f);
        DialogPanel.add(PortLabel);

        PortTextField = new JTextField();
        PortTextField.setFont(GameFrame.f);
        PortTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogPanel.add(PortTextField);

        SearchButton= new JButton("Search");
        SearchButton.setFont(GameFrame.f);
        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaximumIP = MaximumIPTextField.getText();
                MinimumIP = MinimumIPTextField.getText();
                Port = PortTextField.getText();
                long MaxIP = IPAddressToInteger(MaximumIP);
                long MinIP = IPAddressToInteger(MinimumIP);
                int port = Integer.valueOf(Port);
                GameFrame.SelectedPort = port;
                for(long i = MinIP; i<=MaxIP;i++){
                    try(Socket socket = new Socket()){
                        socket.connect(new InetSocketAddress(IntegerToIPAddress(i),port),10);
                        try(Scanner scanner = new Scanner(socket.getInputStream(),"UTF-8");
                            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true)){
                            printWriter.println(0);
                            GameFrame.NumberOfGamesInIPs.add(Integer.valueOf(scanner.nextLine()));
                        }
                        GameFrame.IPArrayList.add(IntegerToIPAddress(i));
                        IPComboBox.addItem(IntegerToIPAddress(i));
                    } catch (IOException e1) {

                    }
                }
            }
        });
        DialogPanel.add(SearchButton);

        IPComboBox = new JComboBox<>();
        for(int i=0; i<GameFrame.IPArrayList.size();i++){
            IPComboBox.addItem(GameFrame.IPArrayList.get(i));
        }
        IPComboBox.setFont(GameFrame.f);
        DialogPanel.add(IPComboBox);

        CancelButton = new JButton("Cancel");
        CancelButton.setFont(GameFrame.f);
        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MultiplayerDialog.this.setVisible(false);
            }
        });
        CancelButton.setFont(GameFrame.f);
        DialogPanel.add(CancelButton);

        ConnectButton = new JButton("Connect");
        ConnectButton.setFont(GameFrame.f);
        ConnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.SelectedIP = (String) IPComboBox.getSelectedItem();
                GameFrame.NumberOfGamesInSelectedIP = GameFrame.NumberOfGamesInIPs.get(IPComboBox.getSelectedIndex());
                GameFrame.NewMultiplayerGame.setEnabled(true);
                MultiplayerDialog.this.setVisible(false);
            }
        });
        ConnectButton.setFont(GameFrame.f);
        DialogPanel.add(ConnectButton);
        this.add(DialogPanel);
    }

}
