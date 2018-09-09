import javax.swing.*;
import java.awt.*;

public class RawPanel extends JPanel {
    String s;
    Font f;
    FontMetrics fm;
    public RawPanel(String massage){
        this.s = massage;
        this.setBackground(Color.lightGray);
        this.f = new Font(Font.SERIF, Font.ITALIC , 18);;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(f);
        g.setColor(new Color(48, 60, 228));
        fm = g.getFontMetrics();
        g.drawString(s, (this.getSize().width-fm.stringWidth(s))/2,this.getSize().height/2);
    }

}
