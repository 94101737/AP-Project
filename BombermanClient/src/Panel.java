import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Panel extends JPanel implements KeyListener {

    Variables MyVariables;
    Block[][] MyBlock;
    final int Height;
    final int Width;
    int unit;
    int player_height;
    int player_width;
    Point map_offset;
    boolean MapIsMovingVertically;
    boolean MapIsMovingHorizontally;
    boolean PlayerIsMoving;
    Font f = new Font(Font.SERIF, Font.PLAIN , 14);
    FontMetrics fm;
    int gap; // this variable is used to measure the gap between texts which display game properties on the top of panel
    BufferedImage[][] Images =new BufferedImage[26][10];
    Timer GameTimer; // is used to measure time of the game


    public int type ( int y,int x ) {
        if((MyBlock[(x/unit)][(y/unit)].BLOCK_TYPE>2)&&(MyBlock[(x/unit)][(y/unit)].BLOCK_TYPE<13)){
            switch (MyBlock[(x/unit)][(y/unit)].BLOCK_TYPE){
                case 3:
                    MyVariables.player.ControlBombs = true;
                    break;
                case 4:
                    MyVariables.player.IncreaseSpeed();
                    break;
                case 5:
                    MyVariables.player.DecreaseSpeed();
                    break;
                case 6:
                    MyVariables.player.IncreaseRadii();
                    break;
                case 7:
                    MyVariables.player.DecreaseRadii();
                    break;
                case 8:
                    MyVariables.player.IncreaseBombs();
                    break;
                case 9:
                    MyVariables.player.DecreaseBombs();
                    break;
                case 10:
                    MyVariables.player.IncreasePoints(100);
                    break;
                case 11:
                    MyVariables.player.DecreasePoints(100);
                    break;
                case 12:
                    MyVariables.player.GhostMode = true;
                    break;
            }
            MyBlock[(x/unit)][(y/unit)].BlockAchieved(this);

        }
        if(MyBlock[(x/unit)][(y/unit)].BLOCK_TYPE==13){
            if(MyVariables.EnemiesList.size()==0){
                NextStage();
            }
        }
        return MyBlock[(x/unit)][(y/unit)].BLOCK_TYPE;
    }
    Panel(){
        this.Height=0;
        this.Width=0;
    }
    Panel(int rows,int columns , int stg, int enemies){
        this.setFocusable(true);
        addKeyListener(this);
        this.Height=GameFrame.MyJPanel.getBounds().height;
        this.Width=GameFrame.MyJPanel.getBounds().width;
        this.PlayerIsMoving = false;
        unit=72;
        while ((unit*rows<Height)|(unit*columns<Width)) {
            unit+=6;
        }
        GameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyVariables.GameTime-=10;
                if((MyVariables.GameTime%100) == 0){
                    if((MyVariables.GameTime < 0)&&((MyVariables.GameTime%1000) == 0)){
                        MyVariables.player.DecreasePoints(1);
                    }
                    Panel.this.repaint();
                }

            }
        });
        try {
            Images[0][0] = ImageIO.read(new File("resources/Tiles/SolidBlock.png"));
            Images[1][0] = ImageIO.read(new File("resources/Tiles/ExplodableBlock.png"));
            Images[2][0] = ImageIO.read(new File("resources/Tiles/BackgroundTile.png"));
            Images[3][0] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f00.png"));
            Images[3][1] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f01.png"));
            Images[3][2] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f02.png"));
            Images[3][3] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f03.png"));
            Images[3][4] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f04.png"));
            Images[3][5] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f05.png"));
            Images[3][6] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f06.png"));
            Images[3][7] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f07.png"));
            Images[4][0] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f00.png"));
            Images[4][1] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f01.png"));
            Images[4][2] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f02.png"));
            Images[4][3] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f03.png"));
            Images[4][4] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f04.png"));
            Images[4][5] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f05.png"));
            Images[4][6] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f06.png"));
            Images[4][7] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f07.png"));
            Images[5][0] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f00.png"));
            Images[5][1] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f01.png"));
            Images[5][2] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f02.png"));
            Images[5][3] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f03.png"));
            Images[5][4] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f04.png"));
            Images[5][5] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f05.png"));
            Images[5][6] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f06.png"));
            Images[5][7] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f07.png"));
            Images[6][0] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f00.png"));
            Images[6][1] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f01.png"));
            Images[6][2] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f02.png"));
            Images[6][3] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f03.png"));
            Images[6][4] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f04.png"));
            Images[6][5] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f05.png"));
            Images[6][6] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f06.png"));
            Images[6][7] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f07.png"));
            Images[7][0] = ImageIO.read(new File("resources/Bomb/Bomb_f01.png"));
            Images[8][0] = ImageIO.read(new File("resources/Flame/Flame_f00.png"));
            Images[8][1] = ImageIO.read(new File("resources/Flame/Flame_f01.png"));
            Images[8][2] = ImageIO.read(new File("resources/Flame/Flame_f02.png"));
            Images[8][3] = ImageIO.read(new File("resources/Flame/Flame_f03.png"));
            Images[8][4] = ImageIO.read(new File("resources/Flame/Flame_f04.png"));
            Images[8][5] = ImageIO.read(new File("resources/PowerUps/Sprite.png"));
            Images[9][0] = ImageIO.read(new File("resources/PowerUps/ControlBomb.png"));
            Images[9][1] = ImageIO.read(new File("resources/PowerUps/IncreaseSpeed.png"));
            Images[9][2] = ImageIO.read(new File("resources/PowerUps/DecreaseSpeed.png"));
            Images[9][3] = ImageIO.read(new File("resources/PowerUps/IncreaseRaduis.png"));
            Images[9][4] = ImageIO.read(new File("resources/PowerUps/DecreaseRaduis.png"));
            Images[9][5] = ImageIO.read(new File("resources/PowerUps/IncreaseBombs.png"));
            Images[9][6] = ImageIO.read(new File("resources/PowerUps/DecreaseBombs.png"));
            Images[9][7] = ImageIO.read(new File("resources/PowerUps/PointIncrease.png"));
            Images[9][8] = ImageIO.read(new File("resources/PowerUps/PointDecrease.png"));
            Images[9][9] = ImageIO.read(new File("resources/PowerUps/Door.png"));
            Images[10][0] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f00.png"));
            Images[10][1] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f01.png"));
            Images[10][2] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f02.png"));
            Images[10][3] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f03.png"));
            Images[10][4] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f04.png"));
            Images[10][5] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f05.png"));
            Images[11][0] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f00.png"));
            Images[11][1] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f01.png"));
            Images[11][2] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f02.png"));
            Images[11][3] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f03.png"));
            Images[11][4] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f04.png"));
            Images[11][5] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f05.png"));
            Images[12][0] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f00.png"));
            Images[12][1] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f01.png"));
            Images[12][2] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f02.png"));
            Images[12][3] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f03.png"));
            Images[12][4] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f04.png"));
            Images[12][5] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f05.png"));
            Images[12][6] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f06.png"));
            Images[13][0] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f00.png"));
            Images[13][1] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f01.png"));
            Images[13][2] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f02.png"));
            Images[13][3] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f03.png"));
            Images[13][4] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f04.png"));
            Images[13][5] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f05.png"));
            Images[13][6] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f06.png"));
            Images[14][0] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f00.png"));
            Images[14][1] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f01.png"));
            Images[14][2] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f02.png"));
            Images[14][3] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f03.png"));
            Images[14][4] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f04.png"));
            Images[14][5] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f05.png"));
            Images[15][0] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f00.png"));
            Images[15][1] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f01.png"));
            Images[15][2] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f02.png"));
            Images[15][3] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f03.png"));
            Images[15][4] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f04.png"));
            Images[15][5] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f05.png"));
            Images[16][0] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f00.png"));
            Images[16][1] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f01.png"));
            Images[16][2] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f02.png"));
            Images[16][3] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f03.png"));
            Images[16][4] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f04.png"));
            Images[16][5] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f05.png"));
            Images[16][6] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f06.png"));
            Images[17][0] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f00.png"));
            Images[17][1] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f01.png"));
            Images[17][2] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f02.png"));
            Images[17][3] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f03.png"));
            Images[17][4] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f04.png"));
            Images[17][5] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f05.png"));
            Images[17][6] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f06.png"));
            Images[18][0] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f00.png"));
            Images[18][1] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f01.png"));
            Images[18][2] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f02.png"));
            Images[18][3] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f03.png"));
            Images[18][4] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f04.png"));
            Images[18][5] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f05.png"));
            Images[19][0] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f00.png"));
            Images[19][1] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f01.png"));
            Images[19][2] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f02.png"));
            Images[19][3] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f03.png"));
            Images[19][4] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f04.png"));
            Images[19][5] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f05.png"));
            Images[20][0] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f00.png"));
            Images[20][1] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f01.png"));
            Images[20][2] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f02.png"));
            Images[20][3] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f03.png"));
            Images[20][4] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f04.png"));
            Images[20][5] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f05.png"));
            Images[20][6] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f06.png"));
            Images[21][0] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f00.png"));
            Images[21][1] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f01.png"));
            Images[21][2] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f02.png"));
            Images[21][3] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f03.png"));
            Images[21][4] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f04.png"));
            Images[21][5] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f05.png"));
            Images[21][6] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f06.png"));
            Images[22][0] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f00.png"));
            Images[22][1] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f01.png"));
            Images[22][2] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f02.png"));
            Images[22][3] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f03.png"));
            Images[22][4] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f04.png"));
            Images[22][5] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f05.png"));
            Images[23][0] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f00.png"));
            Images[23][1] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f01.png"));
            Images[23][2] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f02.png"));
            Images[23][3] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f03.png"));
            Images[23][4] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f04.png"));
            Images[23][5] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f05.png"));
            Images[24][0] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f00.png"));
            Images[24][1] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f01.png"));
            Images[24][2] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f02.png"));
            Images[24][3] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f03.png"));
            Images[24][4] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f04.png"));
            Images[24][5] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f05.png"));
            Images[24][6] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f06.png"));
            Images[25][0] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f00.png"));
            Images[25][1] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f01.png"));
            Images[25][2] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f02.png"));
            Images[25][3] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f03.png"));
            Images[25][4] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f04.png"));
            Images[25][5] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f05.png"));
            Images[25][6] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f06.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        MyVariables = new Variables(this, rows,columns, stg, enemies);
        MyVariables.player.Coordiantes.x = unit;
        MyVariables.player.Coordiantes.y = unit;
        player_height = (int) ((0.7)*unit);
        player_width = (int) (MyVariables.player.ImageRatio*player_height);
        int MAP_OFFSET_X = 0;
        int MAP_OFFSET_Y = 0;
        if(MyVariables.player.Coordiantes.x>(this.Width/2)){
            if(MyVariables.player.Coordiantes.x<(MyVariables.COLUMNS * this.unit - this.Width/2)){
                MAP_OFFSET_X = (this.Width/2)-MyVariables.player.Coordiantes.x;
            }
            else {
                MAP_OFFSET_X = (-MyVariables.COLUMNS * this.unit + this.Width);
            }

        }
        if(MyVariables.player.Coordiantes.y>(this.Height/2)){
            if(MyVariables.player.Coordiantes.y<(MyVariables.ROWS * this.unit - this.Height/2)){
                MAP_OFFSET_Y = (this.Height/2)-MyVariables.player.Coordiantes.y;
            }
            else {
                MAP_OFFSET_Y = (-MyVariables.ROWS * this.unit + this.Height);
            }
        }
        map_offset = new Point(MAP_OFFSET_X,MAP_OFFSET_Y);
        MapIsMovingVertically = false;
        MapIsMovingHorizontally = false;
        if((map_offset.x<0)&&(map_offset.x>(-MyVariables.COLUMNS * this.unit + this.Width))){
            MapIsMovingHorizontally = true;
        }
        if((map_offset.y<0)&&(map_offset.y>(-MyVariables.ROWS * this.unit + this.Height))){
            MapIsMovingVertically = true;
        }


        MyBlock = new Block[MyVariables.ROWS][MyVariables.COLUMNS];
        for(int i=0;i<this.MyVariables.ROWS;i++) {
            for(int j=0;j<this.MyVariables.COLUMNS;j++) {

                switch (this.MyVariables.table[i][j]) {
                    case -1:
                    case 0:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.table[i][j], 0,0);
                        break;
                    case 1:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit, MyVariables.table[i][j], 1,0);
                        break;
                    case 2:
                        MyBlock[i][j] = new Block( this,j*this.unit,i*this.unit, MyVariables.table[i][j], 2,0);
                        break;
                    case 3:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,0);
                        break;
                    case 4:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,1);
                        break;
                    case 5:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,2);
                        break;
                    case 6:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,3);
                        break;
                    case 7:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,4);
                        break;
                    case 8:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,5);
                        break;
                    case 9:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,6);
                        break;
                    case 10:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,7);
                        break;
                    case 11:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,8);
                        break;
                    case 12:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 8,5);
                        break;
                    case 13:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,9);
                        break;
                }
            }
        }
        GameTimer.start();
        for (int i=0; i< MyVariables.EnemiesList.size();i++){
            MyVariables.EnemiesList.get(i).EnemyThread.start();
        }
        repaint();
    }
    Panel(String  DBName, String user, String pass){
        this.setFocusable(true);
        addKeyListener(this);
        this.Height=GameFrame.MyJPanel.getBounds().height;
        this.Width=GameFrame.MyJPanel.getBounds().width;
        this.PlayerIsMoving = false;
        unit=72;
        GameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyVariables.GameTime-=10;
                if((Math.abs(MyVariables.GameTime)%100) == 0){
                    if((MyVariables.GameTime < 0)&&((MyVariables.GameTime%1000) == 0)){
                        MyVariables.player.DecreasePoints(1);
                    }
                    Panel.this.repaint();
                }

            }
        });
        try {
            Images[0][0] = ImageIO.read(new File("resources/Tiles/SolidBlock.png"));
            Images[1][0] = ImageIO.read(new File("resources/Tiles/ExplodableBlock.png"));
            Images[2][0] = ImageIO.read(new File("resources/Tiles/BackgroundTile.png"));
            Images[3][0] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f00.png"));
            Images[3][1] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f01.png"));
            Images[3][2] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f02.png"));
            Images[3][3] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f03.png"));
            Images[3][4] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f04.png"));
            Images[3][5] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f05.png"));
            Images[3][6] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f06.png"));
            Images[3][7] = ImageIO.read(new File("resources/Bomberman/Right/Bman_F_f07.png"));
            Images[4][0] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f00.png"));
            Images[4][1] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f01.png"));
            Images[4][2] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f02.png"));
            Images[4][3] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f03.png"));
            Images[4][4] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f04.png"));
            Images[4][5] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f05.png"));
            Images[4][6] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f06.png"));
            Images[4][7] = ImageIO.read(new File("resources/Bomberman/Left/Bman_F_f07.png"));
            Images[5][0] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f00.png"));
            Images[5][1] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f01.png"));
            Images[5][2] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f02.png"));
            Images[5][3] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f03.png"));
            Images[5][4] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f04.png"));
            Images[5][5] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f05.png"));
            Images[5][6] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f06.png"));
            Images[5][7] = ImageIO.read(new File("resources/Bomberman/Up/Bman_B_f07.png"));
            Images[6][0] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f00.png"));
            Images[6][1] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f01.png"));
            Images[6][2] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f02.png"));
            Images[6][3] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f03.png"));
            Images[6][4] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f04.png"));
            Images[6][5] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f05.png"));
            Images[6][6] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f06.png"));
            Images[6][7] = ImageIO.read(new File("resources/Bomberman/Down/Bman_F_f07.png"));
            Images[7][0] = ImageIO.read(new File("resources/Bomb/Bomb_f01.png"));
            Images[8][0] = ImageIO.read(new File("resources/Flame/Flame_f00.png"));
            Images[8][1] = ImageIO.read(new File("resources/Flame/Flame_f01.png"));
            Images[8][2] = ImageIO.read(new File("resources/Flame/Flame_f02.png"));
            Images[8][3] = ImageIO.read(new File("resources/Flame/Flame_f03.png"));
            Images[8][4] = ImageIO.read(new File("resources/Flame/Flame_f04.png"));
            Images[8][5] = ImageIO.read(new File("resources/PowerUps/Sprite.png"));
            Images[9][0] = ImageIO.read(new File("resources/PowerUps/ControlBomb.png"));
            Images[9][1] = ImageIO.read(new File("resources/PowerUps/IncreaseSpeed.png"));
            Images[9][2] = ImageIO.read(new File("resources/PowerUps/DecreaseSpeed.png"));
            Images[9][3] = ImageIO.read(new File("resources/PowerUps/IncreaseRaduis.png"));
            Images[9][4] = ImageIO.read(new File("resources/PowerUps/DecreaseRaduis.png"));
            Images[9][5] = ImageIO.read(new File("resources/PowerUps/IncreaseBombs.png"));
            Images[9][6] = ImageIO.read(new File("resources/PowerUps/DecreaseBombs.png"));
            Images[9][7] = ImageIO.read(new File("resources/PowerUps/PointIncrease.png"));
            Images[9][8] = ImageIO.read(new File("resources/PowerUps/PointDecrease.png"));
            Images[9][9] = ImageIO.read(new File("resources/PowerUps/Door.png"));
            Images[10][0] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f00.png"));
            Images[10][1] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f01.png"));
            Images[10][2] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f02.png"));
            Images[10][3] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f03.png"));
            Images[10][4] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f04.png"));
            Images[10][5] = ImageIO.read(new File("resources/Enemy1/Back/Creep_B_f05.png"));
            Images[11][0] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f00.png"));
            Images[11][1] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f01.png"));
            Images[11][2] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f02.png"));
            Images[11][3] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f03.png"));
            Images[11][4] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f04.png"));
            Images[11][5] = ImageIO.read(new File("resources/Enemy1/Front/Creep_F_f05.png"));
            Images[12][0] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f00.png"));
            Images[12][1] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f01.png"));
            Images[12][2] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f02.png"));
            Images[12][3] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f03.png"));
            Images[12][4] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f04.png"));
            Images[12][5] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f05.png"));
            Images[12][6] = ImageIO.read(new File("resources/Enemy1/Left/Creep_L_f06.png"));
            Images[13][0] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f00.png"));
            Images[13][1] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f01.png"));
            Images[13][2] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f02.png"));
            Images[13][3] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f03.png"));
            Images[13][4] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f04.png"));
            Images[13][5] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f05.png"));
            Images[13][6] = ImageIO.read(new File("resources/Enemy1/Right/Creep_R_f06.png"));
            Images[14][0] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f00.png"));
            Images[14][1] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f01.png"));
            Images[14][2] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f02.png"));
            Images[14][3] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f03.png"));
            Images[14][4] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f04.png"));
            Images[14][5] = ImageIO.read(new File("resources/Enemy2/Back/Creep_B_f05.png"));
            Images[15][0] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f00.png"));
            Images[15][1] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f01.png"));
            Images[15][2] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f02.png"));
            Images[15][3] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f03.png"));
            Images[15][4] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f04.png"));
            Images[15][5] = ImageIO.read(new File("resources/Enemy2/Front/Creep_F_f05.png"));
            Images[16][0] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f00.png"));
            Images[16][1] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f01.png"));
            Images[16][2] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f02.png"));
            Images[16][3] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f03.png"));
            Images[16][4] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f04.png"));
            Images[16][5] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f05.png"));
            Images[16][6] = ImageIO.read(new File("resources/Enemy2/Left/Creep_L_f06.png"));
            Images[17][0] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f00.png"));
            Images[17][1] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f01.png"));
            Images[17][2] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f02.png"));
            Images[17][3] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f03.png"));
            Images[17][4] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f04.png"));
            Images[17][5] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f05.png"));
            Images[17][6] = ImageIO.read(new File("resources/Enemy2/Right/Creep_R_f06.png"));
            Images[18][0] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f00.png"));
            Images[18][1] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f01.png"));
            Images[18][2] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f02.png"));
            Images[18][3] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f03.png"));
            Images[18][4] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f04.png"));
            Images[18][5] = ImageIO.read(new File("resources/Enemy3/Back/Creep_B_f05.png"));
            Images[19][0] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f00.png"));
            Images[19][1] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f01.png"));
            Images[19][2] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f02.png"));
            Images[19][3] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f03.png"));
            Images[19][4] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f04.png"));
            Images[19][5] = ImageIO.read(new File("resources/Enemy3/Front/Creep_F_f05.png"));
            Images[20][0] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f00.png"));
            Images[20][1] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f01.png"));
            Images[20][2] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f02.png"));
            Images[20][3] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f03.png"));
            Images[20][4] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f04.png"));
            Images[20][5] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f05.png"));
            Images[20][6] = ImageIO.read(new File("resources/Enemy3/Left/Creep_L_f06.png"));
            Images[21][0] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f00.png"));
            Images[21][1] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f01.png"));
            Images[21][2] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f02.png"));
            Images[21][3] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f03.png"));
            Images[21][4] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f04.png"));
            Images[21][5] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f05.png"));
            Images[21][6] = ImageIO.read(new File("resources/Enemy3/Right/Creep_R_f06.png"));
            Images[22][0] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f00.png"));
            Images[22][1] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f01.png"));
            Images[22][2] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f02.png"));
            Images[22][3] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f03.png"));
            Images[22][4] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f04.png"));
            Images[22][5] = ImageIO.read(new File("resources/Enemy4/Back/Creep_B_f05.png"));
            Images[23][0] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f00.png"));
            Images[23][1] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f01.png"));
            Images[23][2] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f02.png"));
            Images[23][3] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f03.png"));
            Images[23][4] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f04.png"));
            Images[23][5] = ImageIO.read(new File("resources/Enemy4/Front/Creep_F_f05.png"));
            Images[24][0] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f00.png"));
            Images[24][1] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f01.png"));
            Images[24][2] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f02.png"));
            Images[24][3] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f03.png"));
            Images[24][4] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f04.png"));
            Images[24][5] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f05.png"));
            Images[24][6] = ImageIO.read(new File("resources/Enemy4/Left/Creep_L_f06.png"));
            Images[25][0] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f00.png"));
            Images[25][1] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f01.png"));
            Images[25][2] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f02.png"));
            Images[25][3] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f03.png"));
            Images[25][4] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f04.png"));
            Images[25][5] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f05.png"));
            Images[25][6] = ImageIO.read(new File("resources/Enemy4/Right/Creep_R_f06.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        MyVariables = new Variables(this, DBName,user,pass);
        while ((this.unit*MyVariables.ROWS<this.Height)|(this.unit*MyVariables.COLUMNS<this.Width)) {
            this.unit+=6;
        }
        player_height = (int) ((0.7)*unit);
        player_width = (int) (MyVariables.player.ImageRatio*player_height);
        int MAP_OFFSET_X = 0;
        int MAP_OFFSET_Y = 0;
        if(MyVariables.player.Coordiantes.x>(this.Width/2)){
            if(MyVariables.player.Coordiantes.x<(MyVariables.COLUMNS * this.unit - this.Width/2)){
                MAP_OFFSET_X = (this.Width/2)-MyVariables.player.Coordiantes.x;
            }
            else {
                MAP_OFFSET_X = (-MyVariables.COLUMNS * this.unit + this.Width);
            }

        }
        if(MyVariables.player.Coordiantes.y>(this.Height/2)){
            if(MyVariables.player.Coordiantes.y<(MyVariables.ROWS * this.unit - this.Height/2)){
                MAP_OFFSET_Y = (this.Height/2)-MyVariables.player.Coordiantes.y;
            }
            else {
                MAP_OFFSET_Y = (-MyVariables.ROWS * this.unit + this.Height);
            }
        }
        map_offset = new Point(MAP_OFFSET_X,MAP_OFFSET_Y);
        MapIsMovingVertically = false;
        MapIsMovingHorizontally = false;
        if((map_offset.x<0)&&(map_offset.x>(-MyVariables.COLUMNS * this.unit + this.Width))){
            MapIsMovingHorizontally = true;
        }
        if((map_offset.y<0)&&(map_offset.y>(-MyVariables.ROWS * this.unit + this.Height))){
            MapIsMovingVertically = true;
        }
        MyBlock = new Block[MyVariables.ROWS][MyVariables.COLUMNS];
        for(int i=0;i<this.MyVariables.ROWS;i++) {
            for(int j=0;j<this.MyVariables.COLUMNS;j++) {

                switch (this.MyVariables.table[i][j]) {
                    case -1:
                    case 0:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.table[i][j], 0,0);
                        break;
                    case 1:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit, MyVariables.table[i][j], 1,0);
                        break;
                    case 2:
                        MyBlock[i][j] = new Block( this,j*this.unit,i*this.unit, MyVariables.table[i][j], 2,0);
                        break;
                    case 3:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,0);
                        break;
                    case 4:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,1);
                        break;
                    case 5:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,2);
                        break;
                    case 6:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,3);
                        break;
                    case 7:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,4);
                        break;
                    case 8:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,5);
                        break;
                    case 9:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,6);
                        break;
                    case 10:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,7);
                        break;
                    case 11:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,8);
                        break;
                    case 12:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 8,5);
                        break;
                    case 13:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,9);
                        break;
                }
            }
        }
        GameTimer.start();
        for(int i=0; i< MyVariables.BombList.size();i++){
            MyVariables.BombList.get(i).Trigger(this);
        }
        for (int i=0; i< MyVariables.EnemiesList.size();i++){
            MyVariables.EnemiesList.get(i).EnemyThread.start();
        }
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent arg0) {
    }
    @Override
    public void keyReleased(KeyEvent arg0) {

    }
    @Override
    public void keyPressed(KeyEvent arg0) {
        if(!MyVariables.GameIsFinished) {
            if (!this.PlayerIsMoving) {
                if ((arg0.getKeyCode() == KeyEvent.VK_UP)) {
                    this.PlayerIsMoving = true;
                    MyVariables.player.AdjustMapMoving();
                    MyVariables.player.MoveUp();
                    this.repaint();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Panel.this.PlayerIsMoving = false;
                        }
                    }).start();

                }

                if ((arg0.getKeyCode() == KeyEvent.VK_LEFT)) {
                    this.PlayerIsMoving = true;
                    MyVariables.player.AdjustMapMoving();
                    MyVariables.player.MoveLeft();
                    this.repaint();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Panel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }

                if ((arg0.getKeyCode() == KeyEvent.VK_RIGHT)) {
                    this.PlayerIsMoving = true;
                    MyVariables.player.AdjustMapMoving();
                    MyVariables.player.MoveRight();
                    this.repaint();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Panel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }

                if ((arg0.getKeyCode() == KeyEvent.VK_DOWN)) {
                    this.PlayerIsMoving = true;
                    MyVariables.player.AdjustMapMoving();
                    MyVariables.player.MoveDown();
                    this.repaint();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Panel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
            }

            if ((arg0.getKeyCode() == KeyEvent.VK_B)) {
                if (MyVariables.player.BombLimit > MyVariables.BombList.size()) {
                    Bomb NewBomb = new Bomb( this.unit * ((MyVariables.player.Coordiantes.x) / this.unit), this.unit * ((MyVariables.player.Coordiantes.y) / this.unit), MyVariables.player.ControlBombs, MyVariables.player.BombRadii);
                    MyVariables.BombList.add(NewBomb);
                    this.repaint();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            NewBomb.Trigger(Panel.this);
                        }
                    }).start();
                }
            }
            if ((arg0.getKeyCode() == KeyEvent.VK_E)) {
                for (int i = 0; i < MyVariables.BombList.size(); i++) {
                    if (MyVariables.BombList.get(i).CONTROLABLE) {
                        synchronized (MyVariables.BombList.get(i)) {
                            MyVariables.BombList.get(i).notify();
                            break;
                        }
                    }

                }
            }
        }
    }
    public void Lose(){
        if(MyVariables.player.Health>0){
            MyVariables.player.BombRadii = 1;
            MyVariables.player.BombLimit = 1;
            MyVariables.player.Speed = 5;
            MyVariables.player.ControlBombs = false;
            MyVariables.player.GhostMode = false;
            MyVariables.player.Coordiantes.x = unit;
            MyVariables.player.Coordiantes.y = unit;
            MyVariables.player.Health--;
            map_offset.x = 0;
            map_offset.y = 0;
            MapIsMovingVertically = false;
            MapIsMovingHorizontally = false;
            if(MyVariables.player.Health==0){
                MyVariables.GameIsFinished = true;
                for (int i=0; i< MyVariables.EnemiesList.size();i++){
                    MyVariables.EnemiesList.get(i).Die();
                }
                GameTimer.stop();
            }
            this.repaint();
        }
    }
    public void NextStage(){
        if(MyVariables.stage==4){
            return;
        }
        GameTimer.stop();
        for (int i=0; i< MyVariables.EnemiesList.size();i++){
            MyVariables.EnemiesList.get(i).Die();
        }
        int currentROWS = MyVariables.ROWS;
        int currentCOLUMNS = MyVariables.COLUMNS;
        int currentstage = MyVariables.stage;
        int initialNumberOfEnemies = MyVariables.NUMBER_OF_ENEMIES;
        int currentPlayerHealth = MyVariables.player.Health;
        int currentPlayerBombRadii = MyVariables.player.BombRadii;
        int currentPlayerBombLimit = MyVariables.player.BombLimit;
        int currentPlayerPts = MyVariables.player.Pts;
        int currentPlayerSpeed = MyVariables.player.Speed;
        boolean currentPlayerControlBombs = MyVariables.player.ControlBombs;
        MyVariables.GameTime = 300000;
        MyVariables = new Variables(this, currentROWS, currentCOLUMNS,currentstage+1, initialNumberOfEnemies);
        MyVariables.player.Health = currentPlayerHealth;
        MyVariables.player.BombRadii = currentPlayerBombRadii;
        MyVariables.player.BombLimit = currentPlayerBombLimit;
        MyVariables.player.Pts = currentPlayerPts;
        MyVariables.player.Speed = currentPlayerSpeed;
        MyVariables.player.ControlBombs = currentPlayerControlBombs;
        this.PlayerIsMoving = false;
        MyVariables.player.Coordiantes.x = unit;
        MyVariables.player.Coordiantes.y = unit;
        int MAP_OFFSET_X = 0;
        int MAP_OFFSET_Y = 0;
        if(MyVariables.player.Coordiantes.x>(this.Width/2)){
            if(MyVariables.player.Coordiantes.x<(MyVariables.COLUMNS * this.unit - this.Width/2)){
                MAP_OFFSET_X = (this.Width/2)-MyVariables.player.Coordiantes.x;
            }
            else {
                MAP_OFFSET_X = (-MyVariables.COLUMNS * this.unit + this.Width);
            }

        }
        if(MyVariables.player.Coordiantes.y>(this.Height/2)){
            if(MyVariables.player.Coordiantes.y<(MyVariables.ROWS * this.unit - this.Height/2)){
                MAP_OFFSET_Y = (this.Height/2)-MyVariables.player.Coordiantes.y;
            }
            else {
                MAP_OFFSET_Y = (-MyVariables.ROWS * this.unit + this.Height);
            }
        }
        map_offset = new Point(MAP_OFFSET_X,MAP_OFFSET_Y);
        MapIsMovingVertically = false;
        MapIsMovingHorizontally = false;
        if((map_offset.x<0)&&(map_offset.x>(-MyVariables.COLUMNS * this.unit + this.Width))){
            MapIsMovingHorizontally = true;
        }
        if((map_offset.y<0)&&(map_offset.y>(-MyVariables.ROWS * this.unit + this.Height))){
            MapIsMovingVertically = true;
        }
        MyBlock = new Block[MyVariables.ROWS][MyVariables.COLUMNS];
        for(int i=0;i<this.MyVariables.ROWS;i++) {
            for(int j=0;j<this.MyVariables.COLUMNS;j++) {

                switch (this.MyVariables.table[i][j]) {
                    case -1:
                    case 0:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.table[i][j], 0,0);
                        break;
                    case 1:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit, MyVariables.table[i][j], 1,0);
                        break;
                    case 2:
                        MyBlock[i][j] = new Block( this,j*this.unit,i*this.unit, MyVariables.table[i][j], 2,0);
                        break;
                    case 3:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,0);
                        break;
                    case 4:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,1);
                        break;
                    case 5:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,2);
                        break;
                    case 6:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,3);
                        break;
                    case 7:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,4);
                        break;
                    case 8:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,5);
                        break;
                    case 9:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,6);
                        break;
                    case 10:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,7);
                        break;
                    case 11:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,8);
                        break;
                    case 12:
                        MyBlock[i][j] = new Block(this,j*this.unit,i*this.unit,  MyVariables.shadowTable[i][j], 9,9);
                        break;
                }
            }
        }
        GameTimer.restart();
        for (int i=0; i< MyVariables.EnemiesList.size();i++){
            MyVariables.EnemiesList.get(i).EnemyThread.start(); 
        }
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //following 2 for loops are for drawing blocks
        for(int i=0;i<this.MyVariables.ROWS;i++) {
            for(int j=0;j<this.MyVariables.COLUMNS;j++) {
                MyBlock[i][j].draw(g,map_offset, this.unit);
                if(MyBlock[i][j].IsFiring){
                    g.drawImage(Images[MyBlock[i][j].FireImageStyle[0]][MyBlock[i][j].FireImageStyle[1]],MyBlock[i][j].BLOCK_COORDINATES.x+this.map_offset.x,MyBlock[i][j].BLOCK_COORDINATES.y+this.map_offset.y, this.unit, this.unit, null);
                }
            }
        }
        //following for loop is for drawing bombs
        for(int i=0;i<MyVariables.BombList.size();i++) {
            MyVariables.BombList.get(i).draw(g,this);
        }
        //following for loop is for drawing player(s)

        MyVariables.player.draw(Images[(int) MyVariables.player.ImageStyle[0]][(int) MyVariables.player.ImageStyle[1]], g , player_width, player_height);
        //following for loop is for drawing enemies
        for(int i=0;i<MyVariables.EnemiesList.size();i++) {
            MyVariables.EnemiesList.get(i).draw(Images[(int) MyVariables.EnemiesList.get(i).ImageStyle[0]][(int) MyVariables.EnemiesList.get(i).ImageStyle[1]],g,((2*unit)/3),((2*unit)/3));
        }
        //following lines is for drawing game specifications on top of the game panel
        g.setFont(f);
        g.setColor(new Color(255, 255, 255));
        fm = g.getFontMetrics();
        gap = (this.Width - (fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.player.ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf( MyVariables.player.GhostMode))+fm.stringWidth("Speed : " + String.valueOf(MyVariables.player.Speed/5))+fm.stringWidth("Explosion radius : " + String.valueOf(MyVariables.player.BombRadii))+fm.stringWidth("Bombs limit : " + String.valueOf(MyVariables.player.BombLimit))+fm.stringWidth("Time : " + String.valueOf(MyVariables.stage))))/10;
        g.drawString("Health : " + String.valueOf(MyVariables.player.Health), gap/2,(this.unit)/2);
        g.drawString("Stage : " + String.valueOf(MyVariables.stage), gap/2 + gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health)) ,(this.unit)/2);
        g.drawString("Points : " + String.valueOf(MyVariables.player.Pts), gap/2 + 2*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage)),(this.unit)/2);
        g.drawString("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()), gap/2 + 3*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage)) ,(this.unit)/2);
        g.drawString("Bomb control : " + String.valueOf(MyVariables.player.ControlBombs), gap/2 + 4*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size())) ,(this.unit)/2);
        g.drawString("Ghost Mode : " + String.valueOf( MyVariables.player.GhostMode), gap/2 + 5*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.player.ControlBombs)) ,(this.unit)/2);
        g.drawString("Speed : " + String.valueOf(MyVariables.player.Speed/5), gap/2 + 6*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+ fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.player.ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf( MyVariables.player.GhostMode)),(this.unit)/2);
        g.drawString("Explosion radius : " + String.valueOf(MyVariables.player.BombRadii), gap/2 + 7*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+ fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.player.ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf( MyVariables.player.GhostMode))+fm.stringWidth("Speed : " + String.valueOf(MyVariables.player.Speed/5)),(this.unit)/2);
        g.drawString("Bombs limit : " + String.valueOf(MyVariables.player.BombLimit), gap/2 + 8*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+ fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.player.ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf( MyVariables.player.GhostMode))+fm.stringWidth("Speed : " + String.valueOf(MyVariables.player.Speed/5))+fm.stringWidth("Explosion radius : " + String.valueOf(MyVariables.player.BombRadii)),(this.unit)/2);
        g.drawString("Time : " + String.valueOf(MyVariables.GameTime/1000), gap/2 + 9*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.player.Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.player.ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf( MyVariables.player.GhostMode))+fm.stringWidth("Speed : " + String.valueOf(MyVariables.player.Speed/5))+fm.stringWidth("Explosion radius : " + String.valueOf(MyVariables.player.BombRadii))+fm.stringWidth("Bombs limit : " + String.valueOf(MyVariables.player.BombLimit)),(this.unit)/2);
    }
}