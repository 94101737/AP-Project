import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NewDialogBox extends JDialog implements ActionListener {
    public String rows;
    public String columns;
    public String enemies;
    GridLayout DialogLayout;
    JTextField RowsTextField;
    JTextField ColumnsTextField;
    JTextField EnemyTextField;
    JLabel RowsLabel;
    JLabel ColumnsLabel;
    JLabel EnemyLabel;
    JButton Start;
    JButton Cancel;
    JPanel DialogBoxPanel;

    NewDialogBox(JFrame MyFrame, String title){
        super(MyFrame,title,true);
        this.setSize(GameFrame.MyFrame.getBounds().width/5, GameFrame.MyFrame.getBounds().height/3);
        this.setLocation(4*GameFrame.MyFrame.getBounds().width/10, 4*GameFrame.MyFrame.getBounds().height/10);
        DialogLayout = new GridLayout(4,2,20,20);

        DialogBoxPanel = new JPanel();
        DialogBoxPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        DialogBoxPanel.setLayout(DialogLayout);

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
            int NumberOfEnemies;
            this.setVisible(false);
            try {
                try{
                    NumberOfEnemies = Integer.parseInt(enemies);
                }catch (NumberFormatException e) {
                    NumberOfEnemies = Math.min(Integer.parseInt(rows), Integer.parseInt(columns));
                }
                GameFrame.MyPanels.add(new Panel(Integer.parseInt(rows), Integer.parseInt(columns),1, NumberOfEnemies));
                GameFrame.MotherPanel.add(GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1) , String.valueOf(GameFrame.MyPanels.size()-1));
                GameFrame.MyCardLayout.last(GameFrame.MotherPanel);
                GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1).requestFocusInWindow();
                GameFrame.MotherPanel.repaint();
                GameFrame.saveitem.setEnabled(true);
                GameFrame.CurrentPanelIndex = GameFrame.MyPanels.size()-1;
                GameFrame.MyFrame.repaint();
            } catch (NumberFormatException e) {
                GameFrame.MyFrame.repaint();
            }
        }
        if(arg0.getSource()==Cancel) {
            this.setVisible(false);
        }
    }

}
