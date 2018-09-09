import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Moveable {
    public boolean GameIsFinished;
    public int BombLimit;
    public int BombRadii;
    public int Pts;
    public boolean ControlBombs;
    public boolean GhostMode;
    public int Health;
    public int Index;
    public int NumberOfBombedBombs;
    public String Name;

    Player(Panel parent, int x, int y, double ps1, double ps2, int health, int pts, int bombLimit, int bombRadii, boolean controlBombs, boolean ghostMode, int playerIndex) {
        super(parent, x, y, 5, ps1, ps2);
        this.ImageRatio = 48.0 / 87.0;
        this.Health = health;
        this.BombLimit = bombLimit;
        this.BombRadii = bombRadii;
        this.Pts = pts;
        this.ControlBombs = controlBombs;
        this.GhostMode = ghostMode;
        this.Index = playerIndex;
        this.NumberOfBombedBombs =0;
        GameIsFinished = false;
        Name = "Unknown";
    }
    Player(Panel parent, int x, int y, double ps1, double ps2, int health, int pts, int bombLimit, int bombRadii, boolean controlBombs, boolean ghostMode, int playerIndex,String name) {
        super(parent, x, y, 5, ps1, ps2);
        this.ImageRatio = 48.0 / 87.0;
        this.Health = health;
        this.BombLimit = bombLimit;
        this.BombRadii = bombRadii;
        this.Pts = pts;
        this.ControlBombs = controlBombs;
        this.GhostMode = ghostMode;
        this.Index = playerIndex;
        this.NumberOfBombedBombs =0;
        GameIsFinished = false;
        Name = name;
    }

    @Override
    public void draw(BufferedImage img, Graphics g, int h, int w) {
        g.drawImage(img, Coordiantes.x+Parent.map_offset.x, Coordiantes.y+Parent.map_offset.y, h, w, null);
        g.setColor(new Color(255,255,255,50));
        g.fillRect(Coordiantes.x+Parent.map_offset.x-(g.getFontMetrics().stringWidth(Name)+10-Parent.player_width)/2,Coordiantes.y+Parent.map_offset.y-30,g.getFontMetrics().stringWidth(Name)+10,20);
        g.setColor(new Color(0,0,0,255));
        g.drawString(Name,Coordiantes.x+Parent.map_offset.x+(Parent.player_width-g.getFontMetrics().stringWidth(Name))/2, Coordiantes.y+Parent.map_offset.y-15);

    }

    public void MoveLeft() {
        if (this.RequestLeft()) {
            if (!Parent.MapIsMovingHorizontally) {
                this.Coordiantes.x -= this.Speed;
            }
            if (Parent.MapIsMovingHorizontally) {
                this.Coordiantes.x -= this.Speed;
                Parent.map_offset.x += this.Speed;
            }
        }
        this.ImageStyle[0] = 4;
        this.ImageStyle[1] = (this.ImageStyle[1] + 0.1 * this.Speed) % 8;
    }

    public void MoveRight() {
        if (this.RequestRight()) {
            if (!Parent.MapIsMovingHorizontally) {
                this.Coordiantes.x += this.Speed;
            }
            if (Parent.MapIsMovingHorizontally) {
                this.Coordiantes.x += this.Speed;
                Parent.map_offset.x -= this.Speed;
            }
        }
        this.ImageStyle[0] = 3;
        this.ImageStyle[1] = (this.ImageStyle[1] + 0.1 * this.Speed) % 8;
    }

    public void MoveUp() {
        if (this.RequestUp()) {
            if (!Parent.MapIsMovingVertically) {
                this.Coordiantes.y -= this.Speed;
            }
            if (Parent.MapIsMovingVertically) {
                this.Coordiantes.y -= this.Speed;
                Parent.map_offset.y += this.Speed;
            }
        }
        this.ImageStyle[0] = 5;
        this.ImageStyle[1] = (this.ImageStyle[1] + 0.1 * this.Speed) % 8;
    }

    public void MoveDown() {
        if (this.RequestDown()) {
            if (!Parent.MapIsMovingVertically) {
                this.Coordiantes.y += this.Speed;
            }
            if (Parent.MapIsMovingVertically) {
                this.Coordiantes.y += this.Speed;
                Parent.map_offset.y -= this.Speed;
            }
        }
        this.ImageStyle[0] = 6;
        this.ImageStyle[1] = (this.ImageStyle[1] + 0.1 * this.Speed) % 8;
    }

    public void AdjustMapMoving() {
        if (!Parent.MapIsMovingHorizontally) {
            if ((2 * (Parent.MyVariables.PlayerList.get(this.Index).Coordiantes.x+Parent.map_offset.x) > Parent.Width) & (Parent.map_offset.x == 0)) {
                Parent.MyVariables.PlayerList.get(this.Index).Coordiantes.x = (Parent.Width / 2) -Parent.map_offset.x;
                Parent.MapIsMovingHorizontally = true;
            }
            if ((2 * (Parent.MyVariables.PlayerList.get(this.Index).Coordiantes.x+Parent.map_offset.x) < Parent.Width) & (Parent.map_offset.x == -(Parent.MyVariables.COLUMNS * Parent.unit - Parent.Width))) {
                Parent.MyVariables.PlayerList.get(this.Index).Coordiantes.x = (Parent.Width / 2) -Parent.map_offset.x;
                Parent.MapIsMovingHorizontally = true;
            }
        }
        if (!Parent.MapIsMovingVertically) {
            if ((2 * (Parent.MyVariables.PlayerList.get(this.Index).Coordiantes.y+Parent.map_offset.y) > Parent.Height) & (Parent.map_offset.y == 0)) {
                Parent.MyVariables.PlayerList.get(this.Index).Coordiantes.y = (Parent.Height / 2)-Parent.map_offset.y;
                Parent.MapIsMovingVertically = true;
            }
            if ((2 * (Parent.MyVariables.PlayerList.get(this.Index).Coordiantes.y+Parent.map_offset.y) < Parent.Height) & (Parent.map_offset.y == -(Parent.MyVariables.ROWS * Parent.unit - Parent.Height))) {
                Parent.MyVariables.PlayerList.get(this.Index).Coordiantes.y = (Parent.Height / 2)-Parent.map_offset.y;
                Parent.MapIsMovingVertically = true;
            }
        }
        if (Parent.MapIsMovingHorizontally) {
            if (Parent.map_offset.x > 0) {
                Parent.map_offset.x = 0;
                Parent.MapIsMovingHorizontally = false;
            }
            if (Parent.map_offset.x < (-(Parent.MyVariables.COLUMNS * Parent.unit - Parent.Width))) {
                Parent.map_offset.x = -(Parent.MyVariables.COLUMNS * Parent.unit - Parent.Width);
                Parent.MapIsMovingHorizontally = false;
            }
        }
        if (Parent.MapIsMovingVertically) {
            if (Parent.map_offset.y > 0) {
                Parent.map_offset.y = 0;
                Parent.MapIsMovingVertically = false;
            }
            if (Parent.map_offset.y < (-(Parent.MyVariables.ROWS * Parent.unit - Parent.Height))) {
                Parent.map_offset.y = -(Parent.MyVariables.ROWS * Parent.unit - Parent.Height);
                Parent.MapIsMovingVertically = false;
            }
        }
    }

    @Override
    public boolean RequestLeft() {
        if(GhostMode){
            if ((Parent.type(this.Coordiantes.x - this.Speed, this.Coordiantes.y ,this.Index)!=(-1))
                    & (Parent.type(this.Coordiantes.x - this.Speed, this.Coordiantes.y + Parent.player_height,this.Index)!=(-1))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if ((Parent.type(this.Coordiantes.x - this.Speed, this.Coordiantes.y ,this.Index)>1)
                    & (Parent.type(this.Coordiantes.x - this.Speed, this.Coordiantes.y + Parent.player_height,this.Index)>1)) {
                return true;
            }
            else if (Parent.type(this.Coordiantes.x , this.Coordiantes.y,this.Index) == (-2)
                    & Parent.type(this.Coordiantes.x , this.Coordiantes.y + Parent.player_height,this.Index) == (-2)) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public boolean RequestRight() {
        if(GhostMode){
            if ((Parent.type(this.Coordiantes.x + Parent.player_width + this.Speed, this.Coordiantes.y,this.Index)!=(-1))
                    & (Parent.type(this.Coordiantes.x + Parent.player_width + this.Speed, this.Coordiantes.y + Parent.player_height,this.Index)!=(-1))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if ((Parent.type(this.Coordiantes.x + Parent.player_width + this.Speed, this.Coordiantes.y,this.Index) > 1)
                    & (Parent.type(this.Coordiantes.x + Parent.player_width + this.Speed, this.Coordiantes.y + Parent.player_height,this.Index) > 1)) {
                return true;
            } else if (Parent.type(this.Coordiantes.x, this.Coordiantes.y,this.Index) == (-2)
                    & Parent.type(this.Coordiantes.x, this.Coordiantes.y + Parent.player_height,this.Index) == (-2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean RequestUp() {
        if(GhostMode){
            if ((Parent.type(this.Coordiantes.x , this.Coordiantes.y - this.Speed,this.Index)!=(-1))
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y - this.Speed,this.Index)!=(-1))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if ((Parent.type(this.Coordiantes.x , this.Coordiantes.y - this.Speed,this.Index) > 1)
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y - this.Speed,this.Index) > 1)) {
                return true;
            } else if (Parent.type(this.Coordiantes.x, this.Coordiantes.y,this.Index ) == (-2)
                    & Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y,this.Index) == (-2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean RequestDown() {
        if(GhostMode){
            if ((Parent.type(this.Coordiantes.x, this.Coordiantes.y + Parent.player_height + this.Speed,this.Index)!=(-1))
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y + Parent.player_height + this.Speed,this.Index)!=(-1))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if ((Parent.type(this.Coordiantes.x, this.Coordiantes.y + Parent.player_height + this.Speed,this.Index) > 1)
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y + Parent.player_height + this.Speed,this.Index) > 1)) {
                return true;
            }
            if ((Parent.type(this.Coordiantes.x, this.Coordiantes.y + Parent.player_height,this.Index) == (-2))
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y + Parent.player_height,this.Index) == (-2))) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void IncreaseBombs() {
        this.BombLimit++;
    }

    public void DecreaseBombs() {
        if (this.BombLimit > 1) {
            this.BombLimit--;
        }
    }

    public void IncreaseRadii() {
        this.BombRadii++;
    }

    public void DecreaseRadii() {
        if (this.BombRadii > 1) {
            this.BombRadii--;
        }
    }

    public void IncreaseSpeed() {
        this.Speed += 2;
    }

    public void DecreaseSpeed() {
        if (this.Speed > 5) {
            this.Speed -= 2;
        }
    }

    public void IncreasePoints(int pts) {
        this.Pts += pts;
    }

    public void DecreasePoints(int pts) {
        if(this.Pts<=0){
            Parent.Lose(this.Index);
        }
        else {
            this.Pts -= pts;
        }
    }

    public void MoveUp(int clientIndex) {
        if (this.RequestUp()) {
            this.Coordiantes.y -= this.Speed;
            this.ImageStyle[0] = 5;
            this.ImageStyle[1] = (this.ImageStyle[1] + 0.1 * this.Speed) % 8;
        }
    }

    public void MoveLeft(int clientIndex) {
        if (this.RequestLeft()) {
            this.Coordiantes.x -= this.Speed;
            this.ImageStyle[0] = 4;
            this.ImageStyle[1] = (this.ImageStyle[1] + 0.1 * this.Speed) % 8;
        }
    }

    public void MoveDown(int clientIndex) {
        if (this.RequestDown()) {
            this.Coordiantes.y += this.Speed;
            this.ImageStyle[0] = 6;
            this.ImageStyle[1] = (this.ImageStyle[1] + 0.1 * this.Speed) % 8;
        }
    }

    public void MoveRight(int clientIndex) {
        if (this.RequestRight()) {
            this.Coordiantes.x += this.Speed;
            this.ImageStyle[0] = 3;
            this.ImageStyle[1] = (this.ImageStyle[1] + 0.1 * this.Speed) % 8;
        }

    }
}
