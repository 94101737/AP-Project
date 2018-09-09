import com.sun.deploy.panel.JSmartTextArea;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RawFrame extends JFrame {
    JPanel panel;
    GridBagLayout panelLayout;
    GridBagConstraints panelGridBagConstraints;
    JScrollPane scrollPane;
    BufferedImage Icon[][];
    JPanel SmallPanels[][];
    JPanel LargePanels[];
    GridBagLayout SmallPanelsLargePanelLayout;
    GridBagConstraints smallpanelsGridBagConstraints;
    public RawFrame(int width, int height, Font f,String title, String[][] strings, String[] groups,String[][] icons){
        this.setSize(width, height);
        this.setTitle(title);
        this.setLayout(new BorderLayout());
        panel = new JPanel();
        panelLayout = new GridBagLayout();

        panelGridBagConstraints = new GridBagConstraints();
        panel.setLayout(panelLayout);
        panel.setBackground(Color.WHITE);

        Icon = new  BufferedImage[icons.length][];
        for (int i=0;i<icons.length;i++){
            Icon[i]= new BufferedImage[icons[i].length];
            for (int j=0;j<icons[i].length;j++){
                try {
                    Icon[i][j] =ImageIO.read(new File(icons[i][j]));
                } catch (IOException e) {
                    Icon[i][j] = null;
                }
            }
        }

        SmallPanelsLargePanelLayout = new GridBagLayout();
        smallpanelsGridBagConstraints = new GridBagConstraints();

        LargePanels = new JPanel[strings.length];
        SmallPanels = new JPanel[strings.length][];
        int GridY=0;
        for(int i=0; i<strings.length;i++){
            LargePanels[i]= new JPanel();
            LargePanels[i].setLayout(new GridLayout(strings[i].length,1));
            LargePanels[i].setBackground(Color.WHITE);
            SmallPanels[i] = new JPanel[strings[i].length];
            Border border = BorderFactory.createLineBorder(Color.black);
            Border titled = BorderFactory.createTitledBorder(border,groups[i]);
            for (int j=0;j<strings[i].length;j++){
                SmallPanels[i][j] = new JPanel();
                SmallPanels[i][j].setLayout(SmallPanelsLargePanelLayout);
                SmallPanels[i][j].setBackground(Color.WHITE);

                JTextPane jTextPane = new JTextPane();
                jTextPane.setText(strings[i][j]);
                jTextPane.setPreferredSize(new Dimension(300,100));
                jTextPane.setEditable(false);
                smallpanelsGridBagConstraints.gridx=0;
                smallpanelsGridBagConstraints.gridy=0;
                smallpanelsGridBagConstraints.gridheight=1;
                smallpanelsGridBagConstraints.gridwidth=5;
                smallpanelsGridBagConstraints.weightx = 0;
                smallpanelsGridBagConstraints.weighty = 0;
                smallpanelsGridBagConstraints.insets.bottom = 10;
                smallpanelsGridBagConstraints.insets.left = 10;
                smallpanelsGridBagConstraints.insets.top = 10;
                smallpanelsGridBagConstraints.insets.right = 10;
                SmallPanels [i][j].add(jTextPane,smallpanelsGridBagConstraints);

                JLabel jLabel1 = new JLabel();
                smallpanelsGridBagConstraints.gridx=5;
                smallpanelsGridBagConstraints.gridy=0;
                smallpanelsGridBagConstraints.gridheight=1;
                smallpanelsGridBagConstraints.gridwidth=1;
                smallpanelsGridBagConstraints.weightx = 10;
                smallpanelsGridBagConstraints.weighty = 0;
                smallpanelsGridBagConstraints.insets.bottom = 10;
                smallpanelsGridBagConstraints.insets.left = 10;
                smallpanelsGridBagConstraints.insets.top = 10;
                smallpanelsGridBagConstraints.insets.right = 10;
                SmallPanels [i][j].add(jLabel1,smallpanelsGridBagConstraints);

                JLabel jLabel2 = new JLabel();
                jLabel2.setBorder(new EmptyBorder(0,0,0,0));
                if(Icon[i][j] != null){
                    jLabel2.setIcon(new ImageIcon(Icon[i][j].getScaledInstance(64,64,Image.SCALE_SMOOTH)));
                }
                jLabel2.setHorizontalAlignment(JLabel.RIGHT);
                smallpanelsGridBagConstraints.gridx=6;
                smallpanelsGridBagConstraints.gridy=0;
                smallpanelsGridBagConstraints.gridheight=1;
                smallpanelsGridBagConstraints.gridwidth=1;
                smallpanelsGridBagConstraints.weightx = 0;
                smallpanelsGridBagConstraints.weighty = 0;
                smallpanelsGridBagConstraints.insets.bottom = 10;
                smallpanelsGridBagConstraints.insets.left = 10;
                smallpanelsGridBagConstraints.insets.top = 10;
                smallpanelsGridBagConstraints.insets.right = 10;
                SmallPanels [i][j].add(jLabel2,smallpanelsGridBagConstraints);

                LargePanels[i].add(SmallPanels[i][j]);
            }
            LargePanels[i].setBorder(titled);
            panelGridBagConstraints.gridx = 0;
            panelGridBagConstraints.gridy = GridY;
            panelGridBagConstraints.gridwidth = 1;
            panelGridBagConstraints.gridheight = strings[i].length;
            GridY += strings[i].length;
            panelGridBagConstraints.weightx = 1;
            panelGridBagConstraints.weighty = 1;
            panelGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            panelGridBagConstraints.insets.bottom = 15;
            panelGridBagConstraints.insets.left = 15;
            panelGridBagConstraints.insets.top = 15;
            panelGridBagConstraints.insets.right = 15;
            panel.add(LargePanels[i],panelGridBagConstraints);
        }
        scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.setMinimumSize(new Dimension(500,0));
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
