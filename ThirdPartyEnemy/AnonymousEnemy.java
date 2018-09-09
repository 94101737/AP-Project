import java.util.Random;

public class AnonymousEnemy extends AbstractEnemy {
    Random random;
    public AnonymousEnemy(Panel parent,int x, int y, double ps1, double ps2, int direction) {
        super(parent, x, y, 40, ps1, ps2, 5, direction);
        random = new Random();
    }

    @Override
    public void Decide() {
        switch (this.Direction){
            case 1:
                if (RequestLeft()){
                    this.Direction = 1;
                }
                else {
                    this.Direction = 3;
                }
                break;
            case 3:
                if (RequestUp()){
                    this.Direction = 3;
                }
                else {
                    this.Direction = 2;
                }
                break;
            case 2:
                if (RequestRight()){
                    this.Direction = 2;
                }
                else {
                    this.Direction = 4;
                }
                break;
            case 4:
                if (RequestDown()){
                    this.Direction = 4;
                }
                else {
                    this.Direction = 1;
                }
                break;
        }
        RandomizeSpeed();
    }

    private void RandomizeSpeed() {
        double auxiliary = 40.0*random.nextDouble();
        if(auxiliary<10){
            auxiliary = 10;
        }
        this.Speed = (int) auxiliary;
    }

    @Override
    public boolean RequestLeft() {
        if((Math.abs(Parent.MyBlock[((this.Coordiantes.y)/Parent.unit)]                    [((this.Coordiantes.x-((Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE)==1)
          |(Math.abs(Parent.MyBlock[((this.Coordiantes.y+((2*Parent.unit)/3))/Parent.unit)][((this.Coordiantes.x-((Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE)==1)) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean RequestRight() {
        if((Math.abs(Parent.MyBlock[((this.Coordiantes.y)/Parent.unit)]                    [((this.Coordiantes.x+(5*(Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE)==1)
          |(Math.abs(Parent.MyBlock[((this.Coordiantes.y+((2*Parent.unit)/3))/Parent.unit)][((this.Coordiantes.x+(5*(Parent.unit/6)+2))/Parent.unit)].BLOCK_TYPE)==1)) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean RequestUp() {
        if((Math.abs(Parent.MyBlock[((this.Coordiantes.y-((Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x)/Parent.unit)].BLOCK_TYPE)==1)
          |(Math.abs(Parent.MyBlock[((this.Coordiantes.y-((Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x+((2*Parent.unit)/3))/Parent.unit)].BLOCK_TYPE)==1)) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean RequestDown() {
        if((Math.abs(Parent.MyBlock[((this.Coordiantes.y+(5*(Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x)/Parent.unit)].BLOCK_TYPE)==1)
          |(Math.abs(Parent.MyBlock[((this.Coordiantes.y+(5*(Parent.unit/6)+2))/Parent.unit)][((this.Coordiantes.x+((2*Parent.unit)/3))/Parent.unit)].BLOCK_TYPE)==1)) {
            return false;
        }
        else {
            return true;
        }
    }
}
