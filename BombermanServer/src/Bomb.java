import java.awt.*;

public class Bomb {
    public int OwnerIndex;
    public Point BOMB_COORDINATES;
    public boolean CONTROLABLE;
    public int EXPLOSION_RADIUS;

    public Bomb(int ownerIndex,int x, int y, boolean c, int er){
        this.OwnerIndex = ownerIndex;
        this.BOMB_COORDINATES = new Point(x,y);
        this.CONTROLABLE = c;
        this.EXPLOSION_RADIUS = er;
    }
    public void Trigger( Panel Parent){
        int a = Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit].BLOCK_TYPE;
        Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit].BLOCK_TYPE = -2;
        if(!this.CONTROLABLE){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit].BLOCK_TYPE = a;
            Parent.MyVariables.BombList.remove(Bomb.this);
            Parent.MyVariables.PlayerList.get(OwnerIndex).NumberOfBombedBombs--;
            Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit].fire(Parent , Parent.MyVariables.shadowTable[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit], this.OwnerIndex);
            Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit].BlockFire.start();

            //following 4 for loops determine blocks which should be exploded in each direction
            for(int i=1;i<=this.EXPLOSION_RADIUS;i++){
                if(Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)+i][BOMB_COORDINATES.x/Parent.unit].BLOCK_TYPE <= 0){
                    break;
                }
                Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)+i][BOMB_COORDINATES.x/Parent.unit].fire(Parent , Parent.MyVariables.shadowTable[(BOMB_COORDINATES.y/Parent.unit)+i][BOMB_COORDINATES.x/Parent.unit], this.OwnerIndex);
                Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)+i][BOMB_COORDINATES.x/Parent.unit].BlockFire.start();
            }
            for(int i= 1;i<=this.EXPLOSION_RADIUS;i++){
                if(Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)-i][BOMB_COORDINATES.x/Parent.unit].BLOCK_TYPE <= 0){
                    break;
                }
                Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)-i][BOMB_COORDINATES.x/Parent.unit].fire(Parent , Parent.MyVariables.shadowTable[(BOMB_COORDINATES.y/Parent.unit)-i][BOMB_COORDINATES.x/Parent.unit], this.OwnerIndex);
                Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)-i][BOMB_COORDINATES.x/Parent.unit].BlockFire.start();
            }
            for(int i= 1;i<=this.EXPLOSION_RADIUS;i++){
                if(Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)+i].BLOCK_TYPE <= 0){
                    break;
                }
                Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)+i].fire(Parent , Parent.MyVariables.shadowTable[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)+i], this.OwnerIndex);
                Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)+i].BlockFire.start();
            }
            for(int i= 1;i<=this.EXPLOSION_RADIUS;i++){
                if(Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)-i].BLOCK_TYPE <= 0){
                    break;
                }
                Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)-i].fire(Parent , Parent.MyVariables.shadowTable[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)-i], this.OwnerIndex);
                Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)-i].BlockFire.start();
            }
        }
        else{
            synchronized (this){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit].BLOCK_TYPE = a;
            Parent.MyVariables.BombList.remove(Bomb.this);
            Parent.MyVariables.PlayerList.get(OwnerIndex).NumberOfBombedBombs--;
            Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit].fire(Parent , Parent.MyVariables.shadowTable[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit], this.OwnerIndex);
            Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][BOMB_COORDINATES.x/Parent.unit].BlockFire.start();
            for(int i=1;i<=this.EXPLOSION_RADIUS;i++){
                if(Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)+i][BOMB_COORDINATES.x/Parent.unit].BLOCK_TYPE <= 0){
                    break;
                }
                Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)+i][BOMB_COORDINATES.x/Parent.unit].fire(Parent , Parent.MyVariables.shadowTable[(BOMB_COORDINATES.y/Parent.unit)+i][BOMB_COORDINATES.x/Parent.unit], this.OwnerIndex);
                Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)+i][BOMB_COORDINATES.x/Parent.unit].BlockFire.start();
            }
            for(int i= 1;i<=this.EXPLOSION_RADIUS;i++){
                if(Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)-i][BOMB_COORDINATES.x/Parent.unit].BLOCK_TYPE <= 0){
                    break;
                }
                Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)-i][BOMB_COORDINATES.x/Parent.unit].fire(Parent , Parent.MyVariables.shadowTable[(BOMB_COORDINATES.y/Parent.unit)-i][BOMB_COORDINATES.x/Parent.unit], this.OwnerIndex);
                Parent.MyBlock[(BOMB_COORDINATES.y/Parent.unit)-i][BOMB_COORDINATES.x/Parent.unit].BlockFire.start();
            }
            for(int i= 1;i<=this.EXPLOSION_RADIUS;i++){
                if(Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)+i].BLOCK_TYPE <= 0){
                    break;
                }
                Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)+i].fire(Parent , Parent.MyVariables.shadowTable[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)+i], this.OwnerIndex);
                Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)+i].BlockFire.start();
            }
            for(int i= 1;i<=this.EXPLOSION_RADIUS;i++){
                if(Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)-i].BLOCK_TYPE <= 0){
                    break;
                }
                Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)-i].fire(Parent , Parent.MyVariables.shadowTable[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)-i], this.OwnerIndex);
                Parent.MyBlock[BOMB_COORDINATES.y/Parent.unit][(BOMB_COORDINATES.x/Parent.unit)-i].BlockFire.start();
            }
        }

    }
    public void draw(Graphics g , Panel Parent){
        g.drawImage(Parent.Images[7][0], BOMB_COORDINATES.x +Parent.map_offset.x, BOMB_COORDINATES.y+Parent.map_offset.y,Parent.unit , Parent.unit, null);
    }
}
