import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamesListDialogBox extends JDialog {
    JPanel DialogBoxPanel;
    GridLayout DialogLayout;
    JLabel PanelsLabel;
    JComboBox<String> PanelComboBox;
    JButton CancelButton;
    JButton OkButton;

    GamesListDialogBox (JFrame MyFrame, String title, ArrayList<String> panelsArrayList){
        super(MyFrame,title,true);
        this.setSize(GameFrame.MyFrame.getBounds().width/5, GameFrame.MyFrame.getBounds().height/6);
        this.setLocation(4*GameFrame.MyFrame.getBounds().width/10, 4*GameFrame.MyFrame.getBounds().height/10);
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

        OkButton = new JButton("Go");
        OkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(PanelComboBox.getSelectedItem() != null){
                    GameFrame.MyCardLayout.show(GameFrame.MotherPanel, String.valueOf(PanelComboBox.getSelectedIndex()+1));
                    GameFrame.CurrentPanelIndex = PanelComboBox.getSelectedIndex()+1;
                    GamesListDialogBox.this.setVisible(false);
                }
            }
        });
        OkButton.setFont(GameFrame.f);
        DialogBoxPanel.add(OkButton);
        this.add(DialogBoxPanel);
    }

}
