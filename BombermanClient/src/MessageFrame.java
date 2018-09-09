import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MessageFrame extends JFrame{
    JPanel panel;
    GridBagLayout panelLayout;
    GridBagConstraints panelGridBagConstraints;
    JScrollPane scrollPane;
    ArrayList<JPanel> SmallPanels;
    GridBagLayout SmallPanelsLayout;
    GridBagConstraints SmallPanelsGridBagConstraints;
    int GridY;
    JPanel SendPanel;
    GridBagLayout SendPanelGridBagLayout;
    GridBagConstraints SendPanelGridBagConstraints;
    JTextField MessageTextField;
    JButton SendButton;

    MessageFrame(ClientPanel Parent,int width, int height, String title){
        this.setSize(width, height);
        this.setTitle(title);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        panel = new JPanel();
        panelLayout = new GridBagLayout();
        panelGridBagConstraints = new GridBagConstraints();
        panel.setLayout(panelLayout);
        panel.setBackground(Color.WHITE);

        SmallPanels = new ArrayList<>();
        SmallPanelsLayout = new GridBagLayout();
        SmallPanelsGridBagConstraints = new GridBagConstraints();

        GridY = 0;

        scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.setMinimumSize(new Dimension(500,0));
        this.add(scrollPane, BorderLayout.CENTER);

        SendPanel = new JPanel();
        SendPanelGridBagLayout = new GridBagLayout();
        SendPanel.setLayout(SendPanelGridBagLayout);
        SendPanelGridBagConstraints = new GridBagConstraints();
        MessageTextField = new JTextField();
        MessageTextField.setFont(GameFrame.f);
        MessageTextField.setBorder(new EmptyBorder(0,10,0,10));
        MessageTextField.setAutoscrolls(true);
        SendPanelGridBagConstraints.fill = GridBagConstraints.BOTH;
        SendPanelGridBagConstraints.gridx=0;
        SendPanelGridBagConstraints.gridy=0;
        SendPanelGridBagConstraints.gridheight=1;
        SendPanelGridBagConstraints.gridwidth=5;
        SendPanelGridBagConstraints.weightx = 10;
        SendPanelGridBagConstraints.weighty = 0;
        SendPanelGridBagConstraints.insets.bottom = 10;
        SendPanelGridBagConstraints.insets.left = 10;
        SendPanelGridBagConstraints.insets.top = 10;
        SendPanelGridBagConstraints.insets.right = 10;
        SendPanel.add(MessageTextField,SendPanelGridBagConstraints);
        SendButton = new JButton("Send");
        SendButton.setFont(GameFrame.f);
        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(MessageTextField.getText()!=""){
                    Parent.MessagePrintWriter.println(MessageTextField.getText());
                    addMessage(Parent.clientVariables.PlayerNames[Parent.clientVariables.PlayerIndex],MessageTextField.getText());
                    MessageTextField.setText("");
                }
            }
        });
        SendPanelGridBagConstraints.fill = GridBagConstraints.BOTH;
        SendPanelGridBagConstraints.gridx=5;
        SendPanelGridBagConstraints.gridy=0;
        SendPanelGridBagConstraints.gridheight=1;
        SendPanelGridBagConstraints.gridwidth=1;
        SendPanelGridBagConstraints.weightx = 0;
        SendPanelGridBagConstraints.weighty = 0;
        SendPanelGridBagConstraints.insets.bottom = 10;
        SendPanelGridBagConstraints.insets.left = 10;
        SendPanelGridBagConstraints.insets.top = 10;
        SendPanelGridBagConstraints.insets.right = 10;
        SendPanel.add(SendButton,SendPanelGridBagConstraints);

        SendPanel.setBackground(Color.LIGHT_GRAY);
        this.add(SendPanel,BorderLayout.SOUTH);
    }
    public void addMessage(String name, String message){
        JPanel result = new JPanel();
        result.setBackground(Color.white);
        Border border = BorderFactory.createLineBorder(Color.black);
        Border titled = BorderFactory.createTitledBorder(border,name + " says : ");
        result.setLayout(SmallPanelsLayout);
        JTextPane jTextPane = new JTextPane();
        jTextPane.setText(message);
        jTextPane.setPreferredSize(new Dimension(300,100));
        jTextPane.setEditable(false);
        SmallPanelsGridBagConstraints.gridx=0;
        SmallPanelsGridBagConstraints.gridy=0;
        SmallPanelsGridBagConstraints.gridheight=1;
        SmallPanelsGridBagConstraints.gridwidth=5;
        SmallPanelsGridBagConstraints.weightx = 0;
        SmallPanelsGridBagConstraints.weighty = 0;
        SmallPanelsGridBagConstraints.insets.bottom = 10;
        SmallPanelsGridBagConstraints.insets.left = 10;
        SmallPanelsGridBagConstraints.insets.top = 10;
        SmallPanelsGridBagConstraints.insets.right = 10;
        result.add(jTextPane,SmallPanelsGridBagConstraints);

        JLabel jLabel1 = new JLabel();
        SmallPanelsGridBagConstraints.gridx=5;
        SmallPanelsGridBagConstraints.gridy=0;
        SmallPanelsGridBagConstraints.gridheight=1;
        SmallPanelsGridBagConstraints.gridwidth=1;
        SmallPanelsGridBagConstraints.weightx = 10;
        SmallPanelsGridBagConstraints.weighty = 0;
        SmallPanelsGridBagConstraints.insets.bottom = 10;
        SmallPanelsGridBagConstraints.insets.left = 10;
        SmallPanelsGridBagConstraints.insets.top = 10;
        SmallPanelsGridBagConstraints.insets.right = 10;
        result.add(jLabel1,SmallPanelsGridBagConstraints);

        result.setBorder(titled);

        panelGridBagConstraints.gridx = 0;
        panelGridBagConstraints.gridy = GridY;
        panelGridBagConstraints.gridwidth = 1;
        panelGridBagConstraints.gridheight = 1;
        GridY += 1;
        panelGridBagConstraints.weightx = 1;
        panelGridBagConstraints.weighty = 1;
        panelGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelGridBagConstraints.insets.bottom = 15;
        panelGridBagConstraints.insets.left = 15;
        panelGridBagConstraints.insets.top = 15;
        panelGridBagConstraints.insets.right = 15;
        panel.add(result,panelGridBagConstraints);
        panel.scrollRectToVisible(new Rectangle(0, panel.getPreferredSize().height,10,10));

        panel.revalidate();
        panel.repaint();


    }
}
