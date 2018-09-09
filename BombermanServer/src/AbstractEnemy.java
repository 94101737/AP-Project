import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class AbstractEnemy extends Moveable implements Runnable{
    public ArrayList<Integer> Directions;
    public Thread EnemyThread;
    public int EnemyType;
    public boolean isAlive;
    public int Direction;
    public int MoveProgress;
    public AbstractEnemy(Panel parent,int x, int y, int speed, double ps1, double ps2, int enemyType, int direction){
        super(parent,x,y,speed,ps1,ps2);
        super.ImageRatio = 1;
        this.EnemyThread = new Thread(this);
        this.EnemyType = enemyType;
        this.isAlive = true;
        MoveProgress = this.Parent.unit;
        this.Direction = direction;
        Directions = new ArrayList<>();
        Directions.add(1); // this number corresponds to LEFT
        Directions.add(2); // this number corresponds to RIGHT
        Directions.add(3); // this number corresponds to UP
        Directions.add(4); // this number corresponds to DOWN

    }
    public abstract void Decide();
    //Each time the following method is called, the enemy object moves 1 unit (continuously) in specified direction
    public void Move() {
        switch (this.Direction){
            case 1:
                for (int i=0;i<this.Parent.MyVariables.PlayerList.size();i++){
                    if(Collision(i)){
                        Parent.Lose(i);
                    }
                }
                this.Coordiantes.x-=2;
                MoveProgress+=2;
                this.ImageStyle[0] = 12+(this.EnemyType-1)*4;
                this.ImageStyle[1] = (this.ImageStyle[1] +0.25)%7;
                try {
                    Parent.repaint();
                    Thread.sleep((int)(this.Speed));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                for (int i=0;i<this.Parent.MyVariables.PlayerList.size();i++){
                    if(Collision(i)){
                        Parent.Lose(i);
                    }
                }
                this.Coordiantes.x+=2;
                MoveProgress+=2;
                this.ImageStyle[0] = 13+(this.EnemyType-1)*4;
                this.ImageStyle[1] = (this.ImageStyle[1] +0.25)%7;
                try {
                    Parent.repaint();
                    Thread.sleep((int)(this.Speed));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                for (int i=0;i<this.Parent.MyVariables.PlayerList.size();i++){
                    if(Collision(i)){
                        Parent.Lose(i);
                    }
                }
                this.Coordiantes.y-=2;
                MoveProgress+=2;
                this.ImageStyle[0] = 10+(this.EnemyType-1)*4;
                this.ImageStyle[1] = (this.ImageStyle[1] +0.25)%6;
                try {
                    Parent.repaint();
                    Thread.sleep((int)(this.Speed));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                for (int i=0;i<this.Parent.MyVariables.PlayerList.size();i++){
                    if(Collision(i)){
                        Parent.Lose(i);
                    }
                }
                this.Coordiantes.y+=2;
                MoveProgress+=2;
                this.ImageStyle[0] = 11+(this.EnemyType-1)*4;
                this.ImageStyle[1] = (this.ImageStyle[1] +0.25)%6;
                try {
                    Parent.repaint();
                    Thread.sleep((int)(this.Speed));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    @Override
    public void run() {
        while (isAlive){
            while (MoveProgress<this.Parent.unit){
                Move();
            }
            Decide();
            MoveProgress = 0;
        }
    }

    @Override
    public abstract boolean RequestLeft();
    @Override
    public abstract boolean RequestRight();
    @Override
    public abstract boolean RequestUp();
    @Override
    public abstract boolean RequestDown();
    public void Die(){
        this.isAlive = false;
    }
    public boolean Collision (int PlayerIndex){
        if(twoRectanglesCoincidence(this.Coordiantes.x,this.Coordiantes.y,2*(Parent.unit/3),2*(Parent.unit/3),(Parent.MyVariables.PlayerList.get(PlayerIndex).Coordiantes.x),(Parent.MyVariables.PlayerList.get(PlayerIndex).Coordiantes.y),Parent.player_width,Parent.player_height)){
            return true;
        }
        return false;
    }
    public boolean twoRectanglesCoincidence(int Rect1x, int Rect1y, int Rect1Width, int Rect1Height,int Rect2x, int Rect2y, int Rect2Width, int Rect2Height){
        return ((Rect1x<=Rect2x+Rect2Width)&&(Rect1x+Rect1Width>Rect2x)&&(Rect1y<=Rect2y+Rect2Height)&&(Rect1y+Rect1Height>=Rect2y));
    }
}
