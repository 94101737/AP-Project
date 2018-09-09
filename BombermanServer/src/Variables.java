import java.awt.Point;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Variables {
    Random random = new Random();
    public final int STONE = 0;
    public final int WALL = 1;
    public final int GROUND = 2;
    public final int POWER_UP_CONTROL_BOMB = 3;
    public final int POWER_UP_INCREASE_SPEED = 4;
    public final int POWER_UP_DECREASE_SPEED = 5;
    public final int POWER_UP_INCREASE_RADII = 6;
    public final int POWER_UP_DECREASE_RADII = 7;
    public final int POWER_UP_INCREASE_BOMB = 8;
    public final int POWER_UP_DECREASE_BOMB = 9;
    public final int POWER_UP_INCREASE_POINT = 10;
    public final int POWER_UP_DECREASE_POINT = 11;
    public final int POWER_UP_GHOST_MODE = 12;
    public final int DOOR = 13;
    public final double WALL_DENSITY=0.2;

    public int ROWS;
    public int COLUMNS;
    public int NUMBER_OF_ENEMIES;
    public int GameTime;
    public int stage;
    public boolean GameIsFinished;
    public int table[][];
    public int shadowTable[][];
    //public int NumberOfBombs;
    public ArrayList<Bomb> BombList;
    //public int NumberOfPlayers;
    public ArrayList<Player> PlayerList;
    //public int NumberOfEnemies;
    public ArrayList<AbstractEnemy> EnemiesList;
    public ArrayList<Point> EnemyAddress;


    public Variables (Panel panel,int r, int c, int stg, int numberOfEnemies) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ROWS=r;
        COLUMNS=c;
        NUMBER_OF_ENEMIES= numberOfEnemies;
        GameTime = 300000; // 5 minutes
        stage = stg;
        GameIsFinished = false;
        // in the following lines, table (numeric version of game map) is constructed
        // shadowtable is the table wich obtained by letting main table get fired
        int numberOfPowerUps = 0;
        int numberOfWalls = 0;
        table = new int[ROWS][COLUMNS];
        shadowTable = new int[ROWS][COLUMNS];
        for(int i=0;i<ROWS;i++) {
            for(int j=0;j<COLUMNS;j++) {
                table[i][j] = GROUND;
            }
        }
        for (int i=0;i<ROWS;i++) {
            table[i][0] = -1;
            table[i][COLUMNS-1] = -1;
        }
        for (int j=0;j<COLUMNS;j++) {
            table[0][j] = -1;
            table[ROWS-1][j] = -1;
        }
        for(int i=1;i<((ROWS)/2);i++) {
            for(int j=1;j<((COLUMNS)/2);j++) {
                table[2*i][2*j] = STONE;
            }
        }
        for(int i=0;i<ROWS;i++) {
            for (int j = 0; j < COLUMNS; j++) {
                shadowTable[i][j]=table[i][j];
            }
        }
        ArrayList<Point> WallAddress = new ArrayList<>();
        do{
            for(int i=0;i<ROWS;i++) {
                for(int j=0;j<COLUMNS;j++) {
                    if(table[i][j] == GROUND) {
                        if(!( ((i==1)&(j==1)) | ((i==1)&(j==2)) | ((i==2)&(j==1)) )) {
                            if(random.nextDouble()<WALL_DENSITY) {
                                table[i][j]= WALL;
                                shadowTable[i][j] = GROUND;
                                numberOfWalls++;
                                WallAddress.add(new Point(i , j));
                            }
                        }
                    }
                }
            }
        }while (numberOfWalls == 0);
        int minimumDimension = r;
        if (c<r){
            minimumDimension = c;
        }
        numberOfPowerUps = numberOfWalls/3;
        int powerUpDecrement =numberOfWalls/3 - minimumDimension;
        for (int i =0; i<powerUpDecrement;i++){
            if(random.nextBoolean()){
                numberOfPowerUps--;
            }
        }
        Collections.shuffle(WallAddress);
        shadowTable[WallAddress.get(0).x][WallAddress.get(0).y] = DOOR;
        for (int i=1;i<numberOfPowerUps;i++){
            shadowTable[WallAddress.get(i).x][WallAddress.get(i).y] = 3+(int)(10*random.nextDouble());
        }
        // this was end of construction of 2 tables
        BombList = new ArrayList<>();
        PlayerList = new ArrayList<>();
        PlayerList.add(new Player(panel,0,0,3,0,3,0,1,1,false,false,0));
        EnemiesList = new ArrayList<>();
        EnemyAddress = new ArrayList<>();
        for(int i=0;i<ROWS;i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if(table[i][j]==GROUND){
                    EnemyAddress.add(new Point(i,j));
                }
            }
        }
        Collections.shuffle(EnemyAddress);
        // following switch statement determines the arrangement of enemies
        int NumberOfEnemyTypes = this.stage+panel.NameOfClassToBeLoaded.size();
        switch (this.stage){
            case 1:
                for (int i=0; i< numberOfEnemies;i++){
                    double auxiliary = NumberOfEnemyTypes*random.nextDouble();
                    if(auxiliary<=1){
                        EnemiesList.add(new Enemy1(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else {
                        for(int j=1;j<NumberOfEnemyTypes;j++){
                            if((j<auxiliary)&&(auxiliary<=(j+1))){
                                EnemiesList.add((AbstractEnemy) Class.forName(panel.NameOfClassToBeLoaded.get(j-1)).getConstructor(Panel.class,Integer.TYPE,Integer.TYPE, Double.TYPE, Double.TYPE, Integer.TYPE).newInstance(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                            }
                        } 
                    }
                }
                break;
            case 2:
                for (int i=0; i< numberOfEnemies;i++){
                    double auxiliary = NumberOfEnemyTypes*random.nextDouble();
                    if(auxiliary<=1){
                        EnemiesList.add(new Enemy1(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if ((1<auxiliary)&&(auxiliary<=2)){
                        EnemiesList.add(new Enemy2(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else {
                        for(int j=2;j<NumberOfEnemyTypes;j++){
                            if((j<auxiliary)&&(auxiliary<=(j+1))){
                                EnemiesList.add((AbstractEnemy) Class.forName(panel.NameOfClassToBeLoaded.get(j-2)).getConstructor(Panel.class,Integer.TYPE,Integer.TYPE, Double.TYPE, Double.TYPE, Integer.TYPE).newInstance(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                            }
                        }
                    }
                }
                break;
            case 3:
                for (int i=0; i< numberOfEnemies;i++){
                    double auxiliary = NumberOfEnemyTypes*random.nextDouble();
                    if(auxiliary<=1){
                        EnemiesList.add(new Enemy1(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if((1<auxiliary)&&(auxiliary<=2)){
                        EnemiesList.add(new Enemy2(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if ((2<auxiliary)&&(auxiliary<=3)) {
                        EnemiesList.add(new Enemy3(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else {
                        for(int j=3;j<NumberOfEnemyTypes;j++){
                            if((j<auxiliary)&&(auxiliary<=(j+1))){
                                EnemiesList.add((AbstractEnemy) Class.forName(panel.NameOfClassToBeLoaded.get(j-3)).getConstructor(Panel.class,Integer.TYPE,Integer.TYPE, Double.TYPE, Double.TYPE, Integer.TYPE).newInstance(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                            }
                        }
                    }
                }
                break;
            case 4:
                for (int i=0; i< numberOfEnemies;i++){
                    double auxiliary = NumberOfEnemyTypes*random.nextDouble();
                    if(auxiliary<=1){
                        EnemiesList.add(new Enemy1(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if((1<auxiliary)&&(auxiliary<=2)){
                        EnemiesList.add(new Enemy2(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if ((2<auxiliary)&&(auxiliary<=3)){
                        EnemiesList.add(new Enemy3(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if ((3<auxiliary)&&(auxiliary<=4)){
                        EnemiesList.add(new Enemy4(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else {
                        for(int j=4;j<NumberOfEnemyTypes;j++){
                            if((j<auxiliary)&&(auxiliary<=(j+1))){
                                EnemiesList.add((AbstractEnemy) Class.forName(panel.NameOfClassToBeLoaded.get(j-4)).getConstructor(Panel.class,Integer.TYPE,Integer.TYPE, Double.TYPE, Double.TYPE, Integer.TYPE).newInstance(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                            }
                        }
                    }
                }
                break;
        }
    }
}