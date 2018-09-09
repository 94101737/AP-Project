import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NewDialogBox extends JDialog implements ActionListener {
    public String rows;
    public String columns;
    public String enemies;
    public String playerCapacity;
    GridLayout DialogLayout;
    JTextField NameTextField;
    JTextField RowsTextField;
    JTextField ColumnsTextField;
    JTextField EnemyTextField;
    JTextField PlayerCapacityTextField;
    JLabel NameLabel;
    JLabel RowsLabel;
    JLabel ColumnsLabel;
    JLabel EnemyLabel;
    JLabel PlayerCapacityLabel;
    JButton Start;
    JButton Cancel;
    JPanel DialogBoxPanel;

    NewDialogBox(JFrame MyFrame, String title){
        super(MyFrame,title,true);
        this.setSize(GameFrame.MyFrame.getBounds().width/5, GameFrame.MyFrame.getBounds().height/2);
        this.setLocation(4*GameFrame.MyFrame.getBounds().width/10, 4*GameFrame.MyFrame.getBounds().height/10);
        DialogLayout = new GridLayout(6,2,20,20);

        DialogBoxPanel = new JPanel();
        DialogBoxPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        DialogBoxPanel.setLayout(DialogLayout);

        NameLabel = new JLabel("Choose a name : ");
        NameLabel.setFont(GameFrame.f);
        DialogBoxPanel.add(NameLabel);

        NameTextField = new JTextField();
        NameTextField.setFont(GameFrame.f);
        NameTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogBoxPanel.add(NameTextField);

        RowsLabel = new JLabel("Number of Rows : ", JLabel.LEFT);
        RowsLabel.setFont(GameFrame.f);
        DialogBoxPanel.add(RowsLabel);

        RowsTextField = new JTextField();
        RowsTextField.setFont(GameFrame.f);
        RowsTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogBoxPanel.add(RowsTextField);

        ColumnsLabel = new JLabel("Number of Columns : ", JLabel.LEFT);
        ColumnsLabel.setFont(GameFrame.f);
        DialogBoxPanel.add(ColumnsLabel);

        ColumnsTextField = new JTextField();
        ColumnsTextField.setFont(GameFrame.f);
        ColumnsTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogBoxPanel.add(ColumnsTextField);

        EnemyLabel = new JLabel("Number of Enemies : ", JLabel.LEFT);
        EnemyLabel.setFont(GameFrame.f);
        DialogBoxPanel.add(EnemyLabel);

        EnemyTextField = new JTextField();
        EnemyTextField.setFont(GameFrame.f);
        EnemyTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogBoxPanel.add(EnemyTextField);

        PlayerCapacityLabel = new JLabel("Player Capacity : ", JLabel.LEFT);
        PlayerCapacityLabel.setFont(GameFrame.f);
        DialogBoxPanel.add(PlayerCapacityLabel);

        PlayerCapacityTextField = new JTextField();
        PlayerCapacityTextField.setFont(GameFrame.f);
        PlayerCapacityTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogBoxPanel.add(PlayerCapacityTextField);

        Cancel = new JButton("Cancel");
        Cancel.addActionListener(this);
        Cancel.setFont(GameFrame.f);
        DialogBoxPanel.add(Cancel);

        Start = new JButton("Start");
        Start.addActionListener(this);
        Start.setFont(GameFrame.f);
        DialogBoxPanel.add(Start);

        this.add(DialogBoxPanel);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource()==Start) {
            rows   = RowsTextField.getText();
            columns = ColumnsTextField.getText();
            enemies = EnemyTextField.getText();
            playerCapacity = PlayerCapacityTextField.getText();
            int NumberOfEnemies;
            this.setVisible(false);
            try {
                try{
                    NumberOfEnemies = Integer.parseInt(enemies);
                }catch (NumberFormatException e) {
                    NumberOfEnemies = Math.min(Integer.parseInt(rows), Integer.parseInt(columns));
                }
                GameFrame.MyPanels.add(new Panel(NameTextField.getText(),Integer.parseInt(rows), Integer.parseInt(columns),1, NumberOfEnemies,Integer.parseInt(playerCapacity)));
                GameFrame.MotherPanel.add(GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1) , String.valueOf(GameFrame.MyPanels.size()-1));
                GameFrame.MyCardLayout.last(GameFrame.MotherPanel);
                GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1).requestFocusInWindow();
                GameFrame.MotherPanel.repaint();
                GameFrame.MyFrame.repaint();
                GameFrame.capacityArrayList.add(playerCapacity);
                GameFrame.CurrentPanelIndex = GameFrame.MyPanels.size()-1;
                if(GameFrame.capacityArrayList.size()!=1){
                    GameFrame.CurrentGamesitem.setEnabled(true);
                    GameFrame.CurrentGamesPtsitem.setEnabled(true);
                    GameFrame.closeitem.setEnabled(true);
                    GameFrame.LoadClassItem.setEnabled(true);
                }
            } catch (NumberFormatException e) {
                GameFrame.MyFrame.repaint();
            }
        }
        if(arg0.getSource()==Cancel) {
            this.setVisible(false);
        }
    }

}
