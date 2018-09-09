import java.util.Collections;

public class Enemy4 extends AbstractEnemy {
    public int TenRandomStep;
    public Enemy4(Panel parent,int x, int y, double ps1, double ps2, int direction){
        super( parent, x,  y,  10,  ps1,  ps2,  4,  direction);
        this.TenRandomStep = 0;
    }
    //following method returns the distance between player and invoking enemy in Manhattans Distance in time which it is invoked
    public int ManhattansDistance(int dir, int indexOfPlayer){
        switch (dir){
            case 1:
                return (Math.abs(((Parent.MyVariables.PlayerList.get(indexOfPlayer).Coordiantes.x)-(this.Coordiantes.x-Parent.unit))/Parent.unit)
                        +Math.abs(((Parent.MyVariables.PlayerList.get(indexOfPlayer).Coordiantes.y)-(this.Coordiantes.y))/Parent.unit));
            case 2:
                return (Math.abs(((Parent.MyVariables.PlayerList.get(indexOfPlayer).Coordiantes.x)-(this.Coordiantes.x+Parent.unit))/Parent.unit)
                        +Math.abs(((Parent.MyVariables.PlayerList.get(indexOfPlayer).Coordiantes.y)-(this.Coordiantes.y))/Parent.unit));
            case 3:
                return (Math.abs(((Parent.MyVariables.PlayerList.get(indexOfPlayer).Coordiantes.x)-(this.Coordiantes.x))/Parent.unit)
                        +Math.abs(((Parent.MyVariables.PlayerList.get(indexOfPlayer).Coordiantes.y)-(this.Coordiantes.y-Parent.unit))/Parent.unit));
            case 4:
                return (Math.abs(((Parent.MyVariables.PlayerList.get(indexOfPlayer).Coordiantes.x)-(this.Coordiantes.x))/Parent.unit)
                        +Math.abs(((Parent.MyVariables.PlayerList.get(indexOfPlayer).Coordiantes.y)-(this.Coordiantes.y+Parent.unit))/Parent.unit));
        }
        return 0;
    }
    @Override
    public void Decide(){
        if(this.TenRandomStep == 0){
            int distance = (Parent.MyVariables.ROWS+Parent.MyVariables.COLUMNS); // maximum distance of two moveables in map
            Collections.shuffle(Directions);
            for (int i =0; i< Directions.size();i++){
                switch (Directions.get(i)){
                    case 1:
                        for(int j =0;j<Parent.MyVariables.PlayerList.size();j++){
                            if(ManhattansDistance(1,j)<=distance){
                                distance = ManhattansDistance(1,j);
                                if(RequestLeft()){
                                    Direction = 1;
                                    this.TenRandomStep = 0;
                                }
                                else {
                                    this.TenRandomStep = 10;
                                }
                            }
                        }
                        break;
                    case 2:
                        for(int j =0;j<Parent.MyVariables.PlayerList.size();j++){
                            if(ManhattansDistance(2,j)<=distance){
                                distance = ManhattansDistance(2,j);
                                if(RequestRight()){
                                    Direction = 2;
                                    this.TenRandomStep = 0;
                                }
                                else {
                                    this.TenRandomStep = 10;
                                }
                            }
                            break;
                        }
                    case 3:
                        for(int j =0;j<Parent.MyVariables.PlayerList.size();j++){
                            if(ManhattansDistance(3,j)<=distance){
                                distance = ManhattansDistance(3,j);
                                if(RequestUp()){
                                    Direction = 3;
                                    this.TenRandomStep = 0;
                                }
                                else {
                                    this.TenRandomStep = 10;
                                }
                            }
                            break;
                        }
                    case 4:
                        for(int j =0;j<Parent.MyVariables.PlayerList.size();j++){
                            if(ManhattansDistance(4,j)<=distance){
                                distance = ManhattansDistance(4,j);
                                if(RequestDown()){
                                    Direction = 4;
                                    this.TenRandomStep = 0;
                                }
                                else {
                                    this.TenRandomStep = 10;
                                }
                            }
                            break;
                        }
                }
            }
        }
        if(this.TenRandomStep != 0) {
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
            this.TenRandomStep--;
        }
    }
    @Override
    public boolean RequestLeft() {
        if((Parent.MyBlock[((this.Coordiantes.y)/Parent.unit)]                    [((this.Coordiantes.x-((Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE>(-1))
                &(Parent.MyBlock[((this.Coordiantes.y+((2*Parent.unit)/3))/Parent.unit)][((this.Coordiantes.x-((Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE>(-1))) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean RequestRight() {
        if((Parent.MyBlock[((this.Coordiantes.y)/Parent.unit)]                    [((this.Coordiantes.x+(5*(Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE>(-1))
                &(Parent.MyBlock[((this.Coordiantes.y+((2*Parent.unit)/3))/Parent.unit)][((this.Coordiantes.x+(5*(Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE>(-1))) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean RequestUp() {
        if((Parent.MyBlock[((this.Coordiantes.y-((Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x)/Parent.unit)].BLOCK_TYPE>(-1))
                &(Parent.MyBlock[((this.Coordiantes.y-((Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x+((2*Parent.unit)/3))/Parent.unit)].BLOCK_TYPE>(-1))) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean RequestDown() {
        if((Parent.MyBlock[((this.Coordiantes.y+(5*(Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x)/Parent.unit)].BLOCK_TYPE>(-1))
                &(Parent.MyBlock[((this.Coordiantes.y+(5*(Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x+((2*Parent.unit)/3))/Parent.unit)].BLOCK_TYPE>(-1))) {
            return true;
        }
        else {
            return false;
        }
    }
}
