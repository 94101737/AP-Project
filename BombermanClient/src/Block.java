import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Block{
    public Panel Parent;
    public Point BLOCK_COORDINATES;
    public int FireImageStyle[];
    public int ImageStyle[];
    //public BufferedImage IMAGE;
    //public BufferedImage FIRE_IMAGE;
    public int BLOCK_TYPE;
    public boolean IsFiring;
    public Thread BlockFire;

    Block(Panel panel,int x, int y, int type, int is1, int is2){
        Parent = panel;
        this.BLOCK_TYPE=type;
        this.BLOCK_COORDINATES=new Point(x,y);
        ImageStyle = new int[2];
        ImageStyle[0]=is1;
        ImageStyle[1]=is2;
        FireImageStyle = new int[2];
        FireImageStyle[0]=8;
        FireImageStyle[1]=0;
        this.IsFiring = false;
    }
    public void BlockAchieved (Panel MyPanel){
        this.ImageStyle[0] = 2;
        this.ImageStyle[1] = 0;
        this.BLOCK_TYPE = 2;
        MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit] = 2;
        MyPanel.MyVariables.shadowTable[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit] = 2;

    }
    public void draw (Graphics g, Point map_offset, int unit){
        g.drawImage(Parent.Images[ImageStyle[0]][ImageStyle[1]],BLOCK_COORDINATES.x+map_offset.x,BLOCK_COORDINATES.y+map_offset.y, unit, unit, null);
    }
    public void fire(Panel MyPanel , int FiredBlockType){
        Graphics g = MyPanel.getGraphics();
        this.IsFiring = true;
        BlockFire = new Thread(new Runnable() {
            @Override
            public void run() {
                //following for loop explodes bomb and checks for damages
                for (int i = 0; i<100;i++){
                    if(MyPanel.MyVariables.GameIsFinished){
                        break;
                    }
                    Block.this.FireImageStyle[1] = ((i)%5);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (MyPanel.MyVariables.EnemiesList){
                        for (int j=0; j<MyPanel.MyVariables.EnemiesList.size();j++){
                            if(IsInFire(MyPanel.MyVariables.EnemiesList.get(j).Coordiantes,(2*MyPanel.unit)/3,(2*MyPanel.unit)/3,MyPanel)){
                                MyPanel.MyVariables.EnemiesList.get(j).Die();
                                MyPanel.MyVariables.EnemiesList.remove(j);
                            }
                        }
                    }
                    if(IsInFire(new Point(MyPanel.MyVariables.player.Coordiantes.x,MyPanel.MyVariables.player.Coordiantes.y),MyPanel.player_width,MyPanel.player_height,MyPanel)){
                        MyPanel.Lose();
                    }
                    MyPanel.repaint();
                }
                //following if checks weather power up is visible; if so, bomb destroys it.
                if((Block.this.BLOCK_TYPE>2&&Block.this.BLOCK_TYPE<13)&&MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]==Block.this.BLOCK_TYPE){
                    BlockAchieved(MyPanel);
                }
                else {
                    Block.this.BLOCK_TYPE = FiredBlockType;
                }
                //following if and its content, reform BLOCK_TYPE of walls with suitable TYPE which is underneath it
                if(MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]==1){
                    switch (Block.this.BLOCK_TYPE){
                        case 2:
                            Block.this.ImageStyle[0] = 2;
                            Block.this.ImageStyle[1] = 0;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 3:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 0;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 4:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 1;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 5:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 2;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 6:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 3;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 7:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 4;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 8:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 5;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 9:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 6;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 10:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 7;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 11:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 8;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 12:
                            Block.this.ImageStyle[0] = 8;
                            Block.this.ImageStyle[1] = 5;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                        case 13:
                            Block.this.ImageStyle[0] = 9;
                            Block.this.ImageStyle[1] = 9;
                            MyPanel.MyVariables.table[Block.this.BLOCK_COORDINATES.y/MyPanel.unit][Block.this.BLOCK_COORDINATES.x/MyPanel.unit]=Block.this.BLOCK_TYPE;
                            MyPanel.MyVariables.player.IncreasePoints(10);
                            break;
                    }
                }
                Block.this.IsFiring = false;


            }
        });
    }
    public boolean IsInFire(Point coordinates, int w, int h, Panel MyPanel){
        if(((this.BLOCK_COORDINATES.x/MyPanel.unit) == (coordinates.x/MyPanel.unit)) &&((this.BLOCK_COORDINATES.y/MyPanel.unit) == (coordinates.y/MyPanel.unit))){
            return true;
        }
        if(((this.BLOCK_COORDINATES.x/MyPanel.unit) == ((coordinates.x+w)/MyPanel.unit)) &&((this.BLOCK_COORDINATES.y/MyPanel.unit) == (coordinates.y/MyPanel.unit))){
            return true;
        }
        if(((this.BLOCK_COORDINATES.x/MyPanel.unit) == (coordinates.x/MyPanel.unit)) &&((this.BLOCK_COORDINATES.y/MyPanel.unit) == ((coordinates.y+h)/MyPanel.unit))){
            return true;
        }
        if(((this.BLOCK_COORDINATES.x/MyPanel.unit) == ((coordinates.x+w)/MyPanel.unit)) &&((this.BLOCK_COORDINATES.y/MyPanel.unit) == ((coordinates.y+h)/MyPanel.unit))){
            return true;
        }
        return false;
    }
}