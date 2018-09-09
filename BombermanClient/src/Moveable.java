import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Moveable {
    public Panel Parent;
    public Point Coordiantes;
    public int Speed;
    public double ImageStyle[];
    public double ImageRatio;

    public Moveable(Panel panel, int x, int y, int speed, double ps1, double ps2){
        this.Coordiantes = new Point(x,y);
        this.Speed = speed;
        this.ImageStyle = new double[2];
        this.ImageStyle [0] = ps1;
        this.ImageStyle [1] = ps2;
        this.Parent = panel;
    }
    public void draw(BufferedImage img, Graphics g, int h, int w) {
        g.drawImage(img, Coordiantes.x+Parent.map_offset.x, Coordiantes.y+Parent.map_offset.y, h, w, null);
    }

    public abstract boolean RequestLeft();

    public abstract boolean RequestRight();

    public abstract boolean RequestUp();

    public abstract boolean RequestDown();
}
