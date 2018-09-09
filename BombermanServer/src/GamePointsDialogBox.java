import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePointsDialogBox extends JDialog {
    JPanel DialogBoxPanel;
    GridLayout DialogLayout;
    JLabel PanelsLabel;
    JComboBox<String> PanelComboBox;
    JButton CancelButton;
    JButton OkButton;

    JPanel GamePointsPanel;
    JScrollPane scrollPane;
    GridLayout GamePointsLayout;


    GamePointsDialogBox (JFrame MyFrame, String title, ArrayList<String> panelsArrayList){
        super(MyFrame,title,true);
        this.setSize(GameFrame.MyFrame.getBounds().width/5, GameFrame.MyFrame.getBounds().height/6);
        this.setLocation(4*GameFrame.MyFrame.getBounds().width/10, 4*GameFrame.MyFrame.getBounds().height/10);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        DialogBoxPanel = new JPanel();
        DialogBoxPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        DialogLayout = new GridLayout(2,2,20,20);
        DialogBoxPanel.setLayout(DialogLayout);

        PanelsLabel = new JLabel("Choose Game : ", JLabel.LEFT);
        PanelsLabel.setFont(GameFrame.f);
        DialogBoxPanel.add(PanelsLabel);

        PanelComboBox = new JComboBox<>();
        for(int i=1; i<panelsArrayList.size();i++){
            PanelComboBox.addItem(" "+String.valueOf(i)+" ; with player capacity : "+panelsArrayList.get(i));
        }
        PanelComboBox.setFont(GameFrame.f);
        PanelComboBox.setSelectedItem(null);
        DialogBoxPanel.add(PanelComboBox);

        CancelButton = new JButton("Cancel");
        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        CancelButton.setFont(GameFrame.f);
        DialogBoxPanel.add(CancelButton);

        OkButton = new JButton("View Points");
        OkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(PanelComboBox.getSelectedItem() != null){
                    GamePointsLayout = new GridLayout(GameFrame.MyPanels.get(PanelComboBox.getSelectedIndex()+1).MyVariables.PlayerList.size()+1,2,20,20);
                    GamePointsPanel.setLayout(GamePointsLayout);
                    JLabel HeaderNameLabel = new JLabel("Index of player");
                    HeaderNameLabel.setFont(GameFrame.f);
                    HeaderNameLabel.setHorizontalAlignment(JLabel.CENTER);
                    JLabel HeaderPtsLabel = new JLabel("Points");
                    HeaderPtsLabel.setFont(GameFrame.f);
                    HeaderPtsLabel.setHorizontalAlignment(JLabel.CENTER);

                    GamePointsPanel.add(HeaderNameLabel);
                    GamePointsPanel.add(HeaderPtsLabel);
                    for(int i=0;i<GameFrame.MyPanels.get(PanelComboBox.getSelectedIndex()+1).MyVariables.PlayerList.size();i++){
                        JLabel NameLabel = new JLabel(String.valueOf(GameFrame.MyPanels.get(PanelComboBox.getSelectedIndex()+1).MyVariables.PlayerList.get(i).Index));
                        NameLabel.setFont(GameFrame.f);
                        JLabel PtsLabel = new JLabel(String.valueOf(GameFrame.MyPanels.get(PanelComboBox.getSelectedIndex()+1).MyVariables.PlayerList.get(i).Pts));
                        PtsLabel.setFont(GameFrame.f);

                        GamePointsPanel.add(NameLabel);
                        GamePointsPanel.add(PtsLabel);
                    }
                    scrollPane = new JScrollPane(GamePointsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    GamePointsDialogBox.this.remove(DialogBoxPanel);
                    GamePointsDialogBox.this.repaint();
                    GamePointsDialogBox.this.setSize(GameFrame.MyFrame.getBounds().width/4, (GameFrame.MyPanels.get(PanelComboBox.getSelectedIndex()+1).MyVariables.PlayerList.size()+1)*GameFrame.MyFrame.getBounds().height/10);
                    GamePointsDialogBox.this.add(scrollPane);
                    GamePointsDialogBox.this.repaint();
                }
            }
        });
        OkButton.setFont(GameFrame.f);
        DialogBoxPanel.add(OkButton);
        this.add(DialogBoxPanel);

        GamePointsPanel = new JPanel();
        GamePointsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
    }

}
