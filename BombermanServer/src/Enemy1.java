import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;


public class Enemy1 extends AbstractEnemy{

    public Enemy1(Panel parent,int x, int y, double ps1, double ps2, int direction){
        super( parent, x,  y,  40,  ps1,  ps2,  1,  direction);
    }
    /* fallowing method is the decision maker of enemy before each move.
     it determines direction in which object should move in next step
     randomization is a result of shuffling Directions ArrayList*/
    @Override
    public void Decide(){
        Collections.shuffle(Directions);
        forLoop : for (int i =0; i< Directions.size();i++){
            switch (Directions.get(i)){
                case 1:
                    if(this.RequestLeft()){
                        Direction = 1;
                        break forLoop;
                    }
                    break;
                case 2:
                    if(this.RequestRight()){
                        Direction = 2;
                        break forLoop;
                    }
                    break;
                case 3:
                    if(this.RequestUp()){
                        Direction = 3;
                        break forLoop;
                    }
                    break;
                case 4:
                    if(this.RequestDown()){
                        Direction = 4;
                        break forLoop;
                    }
                    break;
            }
        }

    }
    //fallowing 4 Request methods determine weather it is possible to move in specified direction or not
    @Override
    public boolean RequestLeft() {
        if((Parent.MyBlock[((this.Coordiantes.y)/Parent.unit)]                    [((this.Coordiantes.x-((Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE>1)
                &(Parent.MyBlock[((this.Coordiantes.y+((2*Parent.unit)/3))/Parent.unit)][((this.Coordiantes.x-((Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE>1)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean RequestRight() {
        if((Parent.MyBlock[((this.Coordiantes.y)/Parent.unit)]                    [((this.Coordiantes.x+(5*(Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE>1)
                &(Parent.MyBlock[((this.Coordiantes.y+((2*Parent.unit)/3))/Parent.unit)][((this.Coordiantes.x+(5*(Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE>1)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean RequestUp() {
        if((Parent.MyBlock[((this.Coordiantes.y-((Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x)/Parent.unit)].BLOCK_TYPE>1)
                &(Parent.MyBlock[((this.Coordiantes.y-((Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x+((2*Parent.unit)/3))/Parent.unit)].BLOCK_TYPE>1)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean RequestDown() {
        if((Parent.MyBlock[((this.Coordiantes.y+(5*(Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x)/Parent.unit)].BLOCK_TYPE>1)
                &(Parent.MyBlock[((this.Coordiantes.y+(5*(Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x+((2*Parent.unit)/3))/Parent.unit)].BLOCK_TYPE>1)) {
            return true;
        }
        else {
            return false;
        }
    }
}
