import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Moveable {
    public int BombLimit;
    public int BombRadii;
    public int Pts;
    public boolean ControlBombs;
    public boolean GhostMode;
    public int Health;
    public int Index;

    Player(Panel parent, int x, int y, double ps1, double ps2, int health, int pts, int bombLimit, int bombRadii, boolean controlBombs, boolean ghostMode) {
        super(parent, x, y, 5, ps1, ps2);
        this.ImageRatio = 48.0 / 87.0;
        this.Health = health;
        this.BombLimit = bombLimit;
        this.BombRadii = bombRadii;
        this.Pts = pts;
        this.ControlBombs = controlBombs;
        this.GhostMode = ghostMode;
        this.Index = 0;

    }
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
            if ((2 * (Parent.MyVariables.player.Coordiantes.x+Parent.map_offset.x) > Parent.Width) & (Parent.map_offset.x == 0)) {
                Parent.MyVariables.player.Coordiantes.x = (Parent.Width / 2) -Parent.map_offset.x;
                Parent.MapIsMovingHorizontally = true;
            }
            if ((2 * (Parent.MyVariables.player.Coordiantes.x+Parent.map_offset.x) < Parent.Width) & (Parent.map_offset.x == -(Parent.MyVariables.COLUMNS * Parent.unit - Parent.Width))) {
                Parent.MyVariables.player.Coordiantes.x = (Parent.Width / 2) -Parent.map_offset.x;
                Parent.MapIsMovingHorizontally = true;
            }
        }
        if (!Parent.MapIsMovingVertically) {
            if ((2 * (Parent.MyVariables.player.Coordiantes.y+Parent.map_offset.y) > Parent.Height) & (Parent.map_offset.y == 0)) {
                Parent.MyVariables.player.Coordiantes.y = (Parent.Height / 2)-Parent.map_offset.y;
                Parent.MapIsMovingVertically = true;
            }
            if ((2 * (Parent.MyVariables.player.Coordiantes.y+Parent.map_offset.y) < Parent.Height) & (Parent.map_offset.y == -(Parent.MyVariables.ROWS * Parent.unit - Parent.Height))) {
                Parent.MyVariables.player.Coordiantes.y = (Parent.Height / 2)-Parent.map_offset.y;
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
            if ((Parent.type(this.Coordiantes.x - this.Speed, this.Coordiantes.y  )!=(-1))
                    & (Parent.type(this.Coordiantes.x - this.Speed, this.Coordiantes.y + Parent.player_height )!=(-1))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if ((Parent.type(this.Coordiantes.x - this.Speed, this.Coordiantes.y  )>1)
                    & (Parent.type(this.Coordiantes.x - this.Speed, this.Coordiantes.y + Parent.player_height )>1)) {
                return true;
            }
            else if (Parent.type(this.Coordiantes.x , this.Coordiantes.y ) == (-2)
                    & Parent.type(this.Coordiantes.x , this.Coordiantes.y + Parent.player_height ) == (-2)) {
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
            if ((Parent.type(this.Coordiantes.x + Parent.player_width + this.Speed, this.Coordiantes.y )!=(-1))
                    & (Parent.type(this.Coordiantes.x + Parent.player_width + this.Speed, this.Coordiantes.y + Parent.player_height )!=(-1))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if ((Parent.type(this.Coordiantes.x + Parent.player_width + this.Speed, this.Coordiantes.y ) > 1)
                    & (Parent.type(this.Coordiantes.x + Parent.player_width + this.Speed, this.Coordiantes.y + Parent.player_height ) > 1)) {
                return true;
            } else if (Parent.type(this.Coordiantes.x, this.Coordiantes.y ) == (-2)
                    & Parent.type(this.Coordiantes.x, this.Coordiantes.y + Parent.player_height ) == (-2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean RequestUp() {
        if(GhostMode){
            if ((Parent.type(this.Coordiantes.x , this.Coordiantes.y - this.Speed )!=(-1))
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y - this.Speed )!=(-1))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if ((Parent.type(this.Coordiantes.x , this.Coordiantes.y - this.Speed ) > 1)
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y - this.Speed ) > 1)) {
                return true;
            } else if (Parent.type(this.Coordiantes.x, this.Coordiantes.y  ) == (-2)
                    & Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y ) == (-2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean RequestDown() {
        if(GhostMode){
            if ((Parent.type(this.Coordiantes.x, this.Coordiantes.y + Parent.player_height + this.Speed )!=(-1))
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y + Parent.player_height + this.Speed )!=(-1))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if ((Parent.type(this.Coordiantes.x, this.Coordiantes.y + Parent.player_height + this.Speed ) > 1)
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y + Parent.player_height + this.Speed ) > 1)) {
                return true;
            }
            if ((Parent.type(this.Coordiantes.x, this.Coordiantes.y + Parent.player_height ) == (-2))
                    & (Parent.type(this.Coordiantes.x + Parent.player_width, this.Coordiantes.y + Parent.player_height ) == (-2))) {
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
            Parent.Lose();
        }
        else {
            this.Pts -= pts;
        }
    }
}
