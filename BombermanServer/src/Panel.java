import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Panel extends JPanel implements KeyListener {

    Variables MyVariables;
    ArrayList<ClientVariables> clientVariablesArrayList;
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
    BufferedImage[][] Images =new BufferedImage[30][10];
    Timer GameTimer; // is used to measure time of the game
    int PLAYER_CAPACITY;
    MessageFrame messageFrame;
    ArrayList<String> NameOfClassToBeLoaded;

    public int type ( int y,int x, int PlayerIndex ) {
        if((MyBlock[(x/unit)][(y/unit)].BLOCK_TYPE>2)&&(MyBlock[(x/unit)][(y/unit)].BLOCK_TYPE<13)){
            switch (MyBlock[(x/unit)][(y/unit)].BLOCK_TYPE){
                case 3:
                    MyVariables.PlayerList.get(PlayerIndex).ControlBombs = true;
                    break;
                case 4:
                    MyVariables.PlayerList.get(PlayerIndex).IncreaseSpeed();
                    break;
                case 5:
                    MyVariables.PlayerList.get(PlayerIndex).DecreaseSpeed();
                    break;
                case 6:
                    MyVariables.PlayerList.get(PlayerIndex).IncreaseRadii();
                    break;
                case 7:
                    MyVariables.PlayerList.get(PlayerIndex).DecreaseRadii();
                    break;
                case 8:
                    MyVariables.PlayerList.get(PlayerIndex).IncreaseBombs();
                    break;
                case 9:
                    MyVariables.PlayerList.get(PlayerIndex).DecreaseBombs();
                    break;
                case 10:
                    MyVariables.PlayerList.get(PlayerIndex).IncreasePoints(100);
                    break;
                case 11:
                    MyVariables.PlayerList.get(PlayerIndex).DecreasePoints(100);
                    break;
                case 12:
                    MyVariables.PlayerList.get(PlayerIndex).GhostMode = true;
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

    public ClientVariables MakeClientVariables (int index){
        ClientVariables clientVariables = new ClientVariables();
        clientVariables.ROWS = MyVariables.ROWS;
        clientVariables.COLUMNS = MyVariables.COLUMNS;
        clientVariables.PlayerIndex = index;
        clientVariables.BlockImageStyles= new Point[MyVariables.ROWS][MyVariables.COLUMNS];
        for(int i=0;i<MyVariables.ROWS;i++){
            for(int j=0;j<MyVariables.COLUMNS;j++){
                clientVariables.BlockImageStyles[i][j] = new Point(MyBlock[i][j].ImageStyle[0],MyBlock[i][j].ImageStyle[1]);
            }
        }
        clientVariables.BlockFireImageStyles= new Point[MyVariables.ROWS][MyVariables.COLUMNS];
        for(int i=0;i<MyVariables.ROWS;i++){
            for(int j=0;j<MyVariables.COLUMNS;j++){
                clientVariables.BlockFireImageStyles[i][j] = new Point(MyBlock[i][j].FireImageStyle[0],MyBlock[i][j].FireImageStyle[1]);
            }
        }
        clientVariables.BlockCoordinates= new Point[MyVariables.ROWS][MyVariables.COLUMNS];
        for(int i=0;i<MyVariables.ROWS;i++){
            for(int j=0;j<MyVariables.COLUMNS;j++){
                clientVariables.BlockCoordinates[i][j] = new Point(MyBlock[i][j].BLOCK_COORDINATES.x,MyBlock[i][j].BLOCK_COORDINATES.y);
            }
        }
        clientVariables.BlockIsFiring= new boolean[MyVariables.ROWS][MyVariables.COLUMNS];
        for(int i=0;i<MyVariables.ROWS;i++){
            for(int j=0;j<MyVariables.COLUMNS;j++){
                clientVariables.BlockIsFiring[i][j] = MyBlock[i][j].IsFiring;
            }
        }
        clientVariables.NumberOfBombs = MyVariables.BombList.size();
        clientVariables.BombCoordinates = new Point[MyVariables.BombList.size()];
        for(int i=0;i<MyVariables.BombList.size();i++){
            clientVariables.BombCoordinates[i] = new Point(MyVariables.BombList.get(i).BOMB_COORDINATES.x,MyVariables.BombList.get(i).BOMB_COORDINATES.y);
        }
        clientVariables.NumberOfPlayers = MyVariables.PlayerList.size();
        clientVariables.PlayerNames = new String[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerNames[i] = MyVariables.PlayerList.get(i).Name;
        }
        clientVariables.PlayerImageStyles = new Point[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerImageStyles[i] = new Point((int) MyVariables.PlayerList.get(i).ImageStyle[0],(int)MyVariables.PlayerList.get(i).ImageStyle[1]);
        }
        clientVariables.PlayerCoordinates = new Point[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerCoordinates[i] = new Point(MyVariables.PlayerList.get(i).Coordiantes.x,MyVariables.PlayerList.get(i).Coordiantes.y);
        }
        clientVariables.PlayerImageRatio = new double[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerImageRatio[i]= MyVariables.PlayerList.get(i).ImageRatio;
        }

        clientVariables.PlayerHealth = new int[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerHealth[i]= MyVariables.PlayerList.get(i).Health;
        }
        clientVariables.PlayerPts = new int[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerPts[i]= MyVariables.PlayerList.get(i).Pts;
        }
        clientVariables.PlayerControlBombs = new boolean[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerControlBombs[i]= MyVariables.PlayerList.get(i).ControlBombs;
        }
        clientVariables.PlayerGhostMode = new boolean[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerGhostMode[i]= MyVariables.PlayerList.get(i).GhostMode;
        }
        clientVariables.PlayerSpeed = new int[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerSpeed[i]= MyVariables.PlayerList.get(i).Speed;
        }
        clientVariables.PlayerBombRadii = new int[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerBombRadii[i]= MyVariables.PlayerList.get(i).BombRadii;
        }
        clientVariables.PlayerBombLimit = new int[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerBombLimit[i]= MyVariables.PlayerList.get(i).BombLimit;
        }
        clientVariables.PlayerNumberOfBombedBoms = new int[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerNumberOfBombedBoms[i]= MyVariables.PlayerList.get(i).NumberOfBombedBombs;
        }
        clientVariables.PlayerIsFinished = new boolean[MyVariables.PlayerList.size()];
        for(int i=0;i<MyVariables.PlayerList.size();i++){
            clientVariables.PlayerIsFinished[i]= MyVariables.PlayerList.get(i).GameIsFinished;
        }

        clientVariables.NumberOfEnemies = MyVariables.EnemiesList.size();

        clientVariables.EnemyImageStyles = new Point[MyVariables.EnemiesList.size()];
        for(int i=0;i<MyVariables.EnemiesList.size();i++){
            clientVariables.EnemyImageStyles[i]= new Point((int)MyVariables.EnemiesList.get(i).ImageStyle[0],(int)MyVariables.EnemiesList.get(i).ImageStyle[1]);
        }
        clientVariables.EnemyCoordinates = new Point[MyVariables.EnemiesList.size()];
        for(int i=0;i<MyVariables.EnemiesList.size();i++){
            clientVariables.EnemyCoordinates[i]= new Point(MyVariables.EnemiesList.get(i).Coordiantes.x,MyVariables.EnemiesList.get(i).Coordiantes.y);
        }
        clientVariables.GameTime = MyVariables.GameTime;
        clientVariables.stage = MyVariables.stage;

        return clientVariables;

    }

    public void UpdateClientVariables (ClientVariables clientVariables){
        clientVariables.ROWS = MyVariables.ROWS;
        clientVariables.COLUMNS = MyVariables.COLUMNS;
        for(int i=0;i<MyVariables.ROWS;i++){
            for(int j=0;j<MyVariables.COLUMNS;j++){
                clientVariables.BlockImageStyles[i][j].x = MyBlock[i][j].ImageStyle[0];
                clientVariables.BlockImageStyles[i][j].y = MyBlock[i][j].ImageStyle[1];
            }
        }
        for(int i=0;i<MyVariables.ROWS;i++){
            for(int j=0;j<MyVariables.COLUMNS;j++){
                clientVariables.BlockFireImageStyles[i][j].x = MyBlock[i][j].FireImageStyle[0];
                clientVariables.BlockFireImageStyles[i][j].y = MyBlock[i][j].FireImageStyle[1];
            }
        }
        for(int i=0;i<MyVariables.ROWS;i++){
            for(int j=0;j<MyVariables.COLUMNS;j++){
                clientVariables.BlockCoordinates[i][j].x = MyBlock[i][j].BLOCK_COORDINATES.x;
                clientVariables.BlockCoordinates[i][j].y = MyBlock[i][j].BLOCK_COORDINATES.y;
            }
        }
        for(int i=0;i<MyVariables.ROWS;i++){
            for(int j=0;j<MyVariables.COLUMNS;j++){
                clientVariables.BlockIsFiring[i][j] = MyBlock[i][j].IsFiring;
            }
        }
        if(clientVariables.NumberOfBombs != MyVariables.BombList.size()){
            clientVariables.NumberOfBombs = MyVariables.BombList.size();
            clientVariables.BombCoordinates = new Point[MyVariables.BombList.size()];
            for(int i=0;i<MyVariables.BombList.size();i++){
                clientVariables.BombCoordinates[i] = new Point(MyVariables.BombList.get(i).BOMB_COORDINATES.x,MyVariables.BombList.get(i).BOMB_COORDINATES.y);
            }
        }
        else {
            for(int i=0;i<MyVariables.BombList.size();i++){
                clientVariables.BombCoordinates[i].x = MyVariables.BombList.get(i).BOMB_COORDINATES.x;
                clientVariables.BombCoordinates[i].y = MyVariables.BombList.get(i).BOMB_COORDINATES.y;
            }
        }
        if(clientVariables.NumberOfPlayers != MyVariables.PlayerList.size()){
            clientVariables.NumberOfPlayers = MyVariables.PlayerList.size();

            clientVariables.PlayerNames = new String[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerNames[i] = MyVariables.PlayerList.get(i).Name;
            }
            clientVariables.PlayerImageStyles = new Point[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerImageStyles[i] = new Point((int) MyVariables.PlayerList.get(i).ImageStyle[0],(int)MyVariables.PlayerList.get(i).ImageStyle[1]);
            }
            clientVariables.PlayerCoordinates = new Point[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerCoordinates[i] = new Point(MyVariables.PlayerList.get(i).Coordiantes.x,MyVariables.PlayerList.get(i).Coordiantes.y);
            }
            clientVariables.PlayerImageRatio = new double[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerImageRatio[i]= MyVariables.PlayerList.get(i).ImageRatio;
            }

            clientVariables.PlayerHealth = new int[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerHealth[i]= MyVariables.PlayerList.get(i).Health;
            }
            clientVariables.PlayerPts = new int[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerPts[i]= MyVariables.PlayerList.get(i).Pts;
            }
            clientVariables.PlayerControlBombs = new boolean[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerControlBombs[i]= MyVariables.PlayerList.get(i).ControlBombs;
            }
            clientVariables.PlayerGhostMode = new boolean[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerGhostMode[i]= MyVariables.PlayerList.get(i).GhostMode;
            }
            clientVariables.PlayerSpeed = new int[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerSpeed[i]= MyVariables.PlayerList.get(i).Speed;
            }
            clientVariables.PlayerBombRadii = new int[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerBombRadii[i]= MyVariables.PlayerList.get(i).BombRadii;
            }
            clientVariables.PlayerBombLimit = new int[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerBombLimit[i]= MyVariables.PlayerList.get(i).BombLimit;
            }
            clientVariables.PlayerNumberOfBombedBoms = new int[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerNumberOfBombedBoms[i]= MyVariables.PlayerList.get(i).NumberOfBombedBombs;
            }
            clientVariables.PlayerIsFinished = new boolean[MyVariables.PlayerList.size()];
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerIsFinished[i]= MyVariables.PlayerList.get(i).GameIsFinished;
            }
        }
        else {
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerNames[i] = MyVariables.PlayerList.get(i).Name;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerImageStyles[i].x = (int) MyVariables.PlayerList.get(i).ImageStyle[0];
                clientVariables.PlayerImageStyles[i].y = (int) MyVariables.PlayerList.get(i).ImageStyle[1];
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerCoordinates[i].x = MyVariables.PlayerList.get(i).Coordiantes.x;
                clientVariables.PlayerCoordinates[i].y = MyVariables.PlayerList.get(i).Coordiantes.y;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerImageRatio[i]= MyVariables.PlayerList.get(i).ImageRatio;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerHealth[i]= MyVariables.PlayerList.get(i).Health;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerPts[i]= MyVariables.PlayerList.get(i).Pts;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerControlBombs[i]= MyVariables.PlayerList.get(i).ControlBombs;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerGhostMode[i]= MyVariables.PlayerList.get(i).GhostMode;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerSpeed[i]= MyVariables.PlayerList.get(i).Speed;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerBombRadii[i]= MyVariables.PlayerList.get(i).BombRadii;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerBombLimit[i]= MyVariables.PlayerList.get(i).BombLimit;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerNumberOfBombedBoms[i]= MyVariables.PlayerList.get(i).NumberOfBombedBombs;
            }
            for(int i=0;i<MyVariables.PlayerList.size();i++){
                clientVariables.PlayerIsFinished[i]= MyVariables.PlayerList.get(i).GameIsFinished;
            }
        }
        if(clientVariables.NumberOfEnemies != MyVariables.EnemiesList.size()){
            clientVariables.EnemyImageStyles = new Point[MyVariables.EnemiesList.size()];
            for(int i=0;i<MyVariables.EnemiesList.size();i++){
                clientVariables.EnemyImageStyles[i]= new Point((int)MyVariables.EnemiesList.get(i).ImageStyle[0],(int)MyVariables.EnemiesList.get(i).ImageStyle[1]);
            }
            clientVariables.EnemyCoordinates = new Point[MyVariables.EnemiesList.size()];
            for(int i=0;i<MyVariables.EnemiesList.size();i++){
                clientVariables.EnemyCoordinates[i]= new Point(MyVariables.EnemiesList.get(i).Coordiantes.x,MyVariables.EnemiesList.get(i).Coordiantes.y);
            }
        }
        else {
            for(int i=0;i<MyVariables.EnemiesList.size();i++){
                clientVariables.EnemyImageStyles[i].x = (int)MyVariables.EnemiesList.get(i).ImageStyle[0];
                clientVariables.EnemyImageStyles[i].y = (int)MyVariables.EnemiesList.get(i).ImageStyle[1];
            }
            for(int i=0;i<MyVariables.EnemiesList.size();i++){
                clientVariables.EnemyCoordinates[i].x = MyVariables.EnemiesList.get(i).Coordiantes.x;
                clientVariables.EnemyCoordinates[i].y = MyVariables.EnemiesList.get(i).Coordiantes.y;
            }
        }


        clientVariables.GameTime = MyVariables.GameTime;
        clientVariables.stage = MyVariables.stage;
    }

    Panel(){
        this.Height=0;
        this.Width=0;
    }

    Panel(String name,int rows,int columns , int stg, int enemies, int playerCapacity){
        NameOfClassToBeLoaded = new ArrayList<>();
        messageFrame = new MessageFrame(this,500,500,"Messenger");
        GameFrame.MessengerItem.setEnabled(true);
        PLAYER_CAPACITY = playerCapacity;
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
                        MyVariables.PlayerList.get(0).DecreasePoints(1);
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
            Images[26][0] = ImageIO.read(new File("resources/EnemyAnonymous/Back/Creep_B_f00.png"));
            Images[26][1] = ImageIO.read(new File("resources/EnemyAnonymous/Back/Creep_B_f01.png"));
            Images[26][2] = ImageIO.read(new File("resources/EnemyAnonymous/Back/Creep_B_f02.png"));
            Images[26][3] = ImageIO.read(new File("resources/EnemyAnonymous/Back/Creep_B_f03.png"));
            Images[26][4] = ImageIO.read(new File("resources/EnemyAnonymous/Back/Creep_B_f04.png"));
            Images[26][5] = ImageIO.read(new File("resources/EnemyAnonymous/Back/Creep_B_f05.png"));
            Images[27][0] = ImageIO.read(new File("resources/EnemyAnonymous/Front/Creep_F_f00.png"));
            Images[27][1] = ImageIO.read(new File("resources/EnemyAnonymous/Front/Creep_F_f01.png"));
            Images[27][2] = ImageIO.read(new File("resources/EnemyAnonymous/Front/Creep_F_f02.png"));
            Images[27][3] = ImageIO.read(new File("resources/EnemyAnonymous/Front/Creep_F_f03.png"));
            Images[27][4] = ImageIO.read(new File("resources/EnemyAnonymous/Front/Creep_F_f04.png"));
            Images[27][5] = ImageIO.read(new File("resources/EnemyAnonymous/Front/Creep_F_f05.png"));
            Images[28][0] = ImageIO.read(new File("resources/EnemyAnonymous/Left/Creep_L_f00.png"));
            Images[28][1] = ImageIO.read(new File("resources/EnemyAnonymous/Left/Creep_L_f01.png"));
            Images[28][2] = ImageIO.read(new File("resources/EnemyAnonymous/Left/Creep_L_f02.png"));
            Images[28][3] = ImageIO.read(new File("resources/EnemyAnonymous/Left/Creep_L_f03.png"));
            Images[28][4] = ImageIO.read(new File("resources/EnemyAnonymous/Left/Creep_L_f04.png"));
            Images[28][5] = ImageIO.read(new File("resources/EnemyAnonymous/Left/Creep_L_f05.png"));
            Images[28][6] = ImageIO.read(new File("resources/EnemyAnonymous/Left/Creep_L_f06.png"));
            Images[29][0] = ImageIO.read(new File("resources/EnemyAnonymous/Right/Creep_R_f00.png"));
            Images[29][1] = ImageIO.read(new File("resources/EnemyAnonymous/Right/Creep_R_f01.png"));
            Images[29][2] = ImageIO.read(new File("resources/EnemyAnonymous/Right/Creep_R_f02.png"));
            Images[29][3] = ImageIO.read(new File("resources/EnemyAnonymous/Right/Creep_R_f03.png"));
            Images[29][4] = ImageIO.read(new File("resources/EnemyAnonymous/Right/Creep_R_f04.png"));
            Images[29][5] = ImageIO.read(new File("resources/EnemyAnonymous/Right/Creep_R_f05.png"));
            Images[29][6] = ImageIO.read(new File("resources/EnemyAnonymous/Right/Creep_R_f06.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MyVariables = new Variables(this, rows,columns, stg, enemies);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        MyVariables.PlayerList.get(0).Name = name;
        MyVariables.PlayerList.get(0).Coordiantes.x = unit;
        MyVariables.PlayerList.get(0).Coordiantes.y = unit;
        player_height = (int) ((0.7)*unit);
        player_width = (int) (MyVariables.PlayerList.get(0).ImageRatio*player_height);
        int MAP_OFFSET_X = 0;
        int MAP_OFFSET_Y = 0;
        if(MyVariables.PlayerList.get(0).Coordiantes.x>(this.Width/2)){
            if(MyVariables.PlayerList.get(0).Coordiantes.x<(MyVariables.COLUMNS * this.unit - this.Width/2)){
                MAP_OFFSET_X = (this.Width/2)-MyVariables.PlayerList.get(0).Coordiantes.x;
            }
            else {
                MAP_OFFSET_X = (-MyVariables.COLUMNS * this.unit + this.Width);
            }

        }
        if(MyVariables.PlayerList.get(0).Coordiantes.y>(this.Height/2)){
            if(MyVariables.PlayerList.get(0).Coordiantes.y<(MyVariables.ROWS * this.unit - this.Height/2)){
                MAP_OFFSET_Y = (this.Height/2)-MyVariables.PlayerList.get(0).Coordiantes.y;
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
        clientVariablesArrayList = new ArrayList<>();
        clientVariablesArrayList.add(MakeClientVariables(0));
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
                    MyVariables.PlayerList.get(0).AdjustMapMoving();
                    MyVariables.PlayerList.get(0).MoveUp();
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
                    MyVariables.PlayerList.get(0).AdjustMapMoving();
                    MyVariables.PlayerList.get(0).MoveLeft();
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
                    MyVariables.PlayerList.get(0).AdjustMapMoving();
                    MyVariables.PlayerList.get(0).MoveRight();
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
                    MyVariables.PlayerList.get(0).AdjustMapMoving();
                    MyVariables.PlayerList.get(0).MoveDown();
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
                if (MyVariables.PlayerList.get(0).BombLimit > MyVariables.PlayerList.get(0).NumberOfBombedBombs) {
                    Bomb NewBomb = new Bomb(0, this.unit * ((MyVariables.PlayerList.get(0).Coordiantes.x) / this.unit), this.unit * ((MyVariables.PlayerList.get(0).Coordiantes.y) / this.unit), MyVariables.PlayerList.get(0).ControlBombs, MyVariables.PlayerList.get(0).BombRadii);
                    MyVariables.BombList.add(NewBomb);
                    this.repaint();
                    MyVariables.PlayerList.get(0).NumberOfBombedBombs++;
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
                    if ((MyVariables.BombList.get(i).CONTROLABLE) && (MyVariables.BombList.get(i).OwnerIndex == 0)) {
                        synchronized (MyVariables.BombList.get(i)) {
                            MyVariables.BombList.get(i).notify();
                            break;
                        }
                    }

                }
            }
        }
    }

    public void Lose(int indexOfLostPlayer){
        if(MyVariables.PlayerList.get(indexOfLostPlayer).Health>0){
            MyVariables.PlayerList.get(indexOfLostPlayer).BombRadii = 1;
            MyVariables.PlayerList.get(indexOfLostPlayer).BombLimit = 1;
            MyVariables.PlayerList.get(indexOfLostPlayer).Speed = 5;
            MyVariables.PlayerList.get(indexOfLostPlayer).ControlBombs = false;
            MyVariables.PlayerList.get(indexOfLostPlayer).GhostMode = false;
            MyVariables.PlayerList.get(indexOfLostPlayer).Coordiantes.x = unit;
            MyVariables.PlayerList.get(indexOfLostPlayer).Coordiantes.y = unit;
            MyVariables.PlayerList.get(indexOfLostPlayer).Health--;
            if(indexOfLostPlayer == 0){
                map_offset.x = 0;
                map_offset.y = 0;
                MapIsMovingVertically = false;
                MapIsMovingHorizontally = false;
            }
        }
        if(MyVariables.PlayerList.get(indexOfLostPlayer).Health <= 0){
            MyVariables.PlayerList.get(indexOfLostPlayer).GameIsFinished = true;
            if(indexOfLostPlayer==0){
                MyVariables.GameIsFinished = true;
            }
        }
        for (int i = 0; i<MyVariables.PlayerList.size();i++){
            if(MyVariables.PlayerList.get(i).GameIsFinished==false){
                break;
            }
            for (int j=0; j< MyVariables.EnemiesList.size();j++){
                MyVariables.EnemiesList.get(j).Die();
            }
            GameTimer.stop();
        }
    }

    public void NextStage() { 
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
        int currentNumberOfPlayers = MyVariables.PlayerList.size();
        int currentPlayersHealth[] = new int[MyVariables.PlayerList.size()];
        int currentPlayersBombRadii[] = new int[MyVariables.PlayerList.size()];
        int currentPlayersBombLimit[] = new int[MyVariables.PlayerList.size()];
        int currentPlayersPts[] = new int[MyVariables.PlayerList.size()];
        int currentPlayersSpeed[] = new int[MyVariables.PlayerList.size()];
        boolean currentPlayersControlBombs[] = new boolean[MyVariables.PlayerList.size()];
        boolean currentPlayersIsFinished[] = new boolean[MyVariables.PlayerList.size()];
        boolean curentPlayersGhostMode[] = new boolean[MyVariables.PlayerList.size()];
        String currentPlayersNames[] = new String[MyVariables.PlayerList.size()];

        for(int i=0;i<MyVariables.PlayerList.size();i++){
            currentPlayersHealth[i] = MyVariables.PlayerList.get(i).Health;
            currentPlayersBombRadii[i] = MyVariables.PlayerList.get(i).BombRadii;
            currentPlayersBombLimit[i] = MyVariables.PlayerList.get(i).BombLimit;
            currentPlayersPts[i] = MyVariables.PlayerList.get(i).Pts;
            currentPlayersSpeed[i] = MyVariables.PlayerList.get(i).Speed;
            currentPlayersControlBombs[i] = MyVariables.PlayerList.get(i).ControlBombs;
            currentPlayersIsFinished[i] = MyVariables.PlayerList.get(i).GameIsFinished;
            curentPlayersGhostMode[i] = MyVariables.PlayerList.get(i).GhostMode;
            currentPlayersNames[i]= MyVariables.PlayerList.get(i).Name;
        }
        MyVariables.GameTime = 300000;
        try {
            MyVariables = new Variables(this, currentROWS, currentCOLUMNS,currentstage+1, initialNumberOfEnemies);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        for(int i=0;i<currentNumberOfPlayers;i++){
            MyVariables.PlayerList.add(new Player(Panel.this, Panel.this.unit, Panel.this.unit, 3,0,3,0,1,1,false,false,i,currentPlayersNames[i]));
            MyVariables.PlayerList.get(i).Health = currentPlayersHealth[i];
            MyVariables.PlayerList.get(i).BombRadii = currentPlayersBombRadii[i];
            MyVariables.PlayerList.get(i).BombLimit = currentPlayersBombLimit[i];
            MyVariables.PlayerList.get(i).Pts = currentPlayersPts[i];
            MyVariables.PlayerList.get(i).Speed = currentPlayersSpeed[i];
            MyVariables.PlayerList.get(i).ControlBombs = currentPlayersControlBombs[i];
            MyVariables.PlayerList.get(i).GameIsFinished = currentPlayersIsFinished[i];
            MyVariables.PlayerList.get(i).GhostMode = curentPlayersGhostMode[i];
            MyVariables.PlayerList.get(i).Name = currentPlayersNames[i];
            MyVariables.PlayerList.get(i).Coordiantes.x = unit;
            MyVariables.PlayerList.get(i).Coordiantes.y = unit;
        }
        this.PlayerIsMoving = false;
        int MAP_OFFSET_X = 0;
        int MAP_OFFSET_Y = 0;
        if(MyVariables.PlayerList.get(0).Coordiantes.x>(this.Width/2)){
            if(MyVariables.PlayerList.get(0).Coordiantes.x<(MyVariables.COLUMNS * this.unit - this.Width/2)){
                MAP_OFFSET_X = (this.Width/2)-MyVariables.PlayerList.get(0).Coordiantes.x;
            }
            else {
                MAP_OFFSET_X = (-MyVariables.COLUMNS * this.unit + this.Width);
            }

        }
        if(MyVariables.PlayerList.get(0).Coordiantes.y>(this.Height/2)){
            if(MyVariables.PlayerList.get(0).Coordiantes.y<(MyVariables.ROWS * this.unit - this.Height/2)){
                MAP_OFFSET_Y = (this.Height/2)-MyVariables.PlayerList.get(0).Coordiantes.y;
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
        g.setFont(f);
        fm = g.getFontMetrics();
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
        //following for loop is for drawing PlayerList.get(0)(s)
        for(int i=0;i<MyVariables.PlayerList.size();i++) {
            if(!MyVariables.PlayerList.get(i).GameIsFinished){
                MyVariables.PlayerList.get(i).draw(Images[(int) MyVariables.PlayerList.get(i).ImageStyle[0]][(int) MyVariables.PlayerList.get(i).ImageStyle[1]], g , player_width, player_height);
            }
        }
        //following for loop is for drawing enemies
        for(int i=0;i<MyVariables.EnemiesList.size();i++) {
            MyVariables.EnemiesList.get(i).draw(Images[(int) MyVariables.EnemiesList.get(i).ImageStyle[0]][(int) MyVariables.EnemiesList.get(i).ImageStyle[1]],g,((2*unit)/3),((2*unit)/3));
        }
        //following lines is for drawing game specifications on top of the game panel

        g.setColor(new Color(255, 255, 255));
        gap = (this.Width - (fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.PlayerList.get(0).ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf(MyVariables.PlayerList.get(0).GhostMode))+fm.stringWidth("Speed : " + String.valueOf(MyVariables.PlayerList.get(0).Speed/5))+fm.stringWidth("Explosion radius : " + String.valueOf(MyVariables.PlayerList.get(0).BombRadii))+fm.stringWidth("Bombs limit : " + String.valueOf(MyVariables.PlayerList.get(0).BombLimit))+fm.stringWidth("Time : " + String.valueOf(MyVariables.stage))))/10;
        g.drawString("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health), gap/2,(this.unit)/2);
        g.drawString("Stage : " + String.valueOf(MyVariables.stage), gap/2 + gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health)) ,(this.unit)/2);
        g.drawString("Points : " + String.valueOf(MyVariables.PlayerList.get(0).Pts), gap/2 + 2*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage)),(this.unit)/2);
        g.drawString("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()), gap/2 + 3*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage)) ,(this.unit)/2);
        g.drawString("Bomb control : " + String.valueOf(MyVariables.PlayerList.get(0).ControlBombs), gap/2 + 4*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size())) ,(this.unit)/2);
        g.drawString("Ghost Mode : " + String.valueOf(MyVariables.PlayerList.get(0).GhostMode), gap/2 + 5*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.PlayerList.get(0).ControlBombs)) ,(this.unit)/2);
        g.drawString("Speed : " + String.valueOf(MyVariables.PlayerList.get(0).Speed/5), gap/2 + 6*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+ fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.PlayerList.get(0).ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf(MyVariables.PlayerList.get(0).GhostMode)),(this.unit)/2);
        g.drawString("Explosion radius : " + String.valueOf(MyVariables.PlayerList.get(0).BombRadii), gap/2 + 7*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+ fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.PlayerList.get(0).ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf(MyVariables.PlayerList.get(0).GhostMode))+fm.stringWidth("Speed : " + String.valueOf(MyVariables.PlayerList.get(0).Speed/5)),(this.unit)/2);
        g.drawString("Bombs limit : " + String.valueOf(MyVariables.PlayerList.get(0).BombLimit), gap/2 + 8*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+ fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.PlayerList.get(0).ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf(MyVariables.PlayerList.get(0).GhostMode))+fm.stringWidth("Speed : " + String.valueOf(MyVariables.PlayerList.get(0).Speed/5))+fm.stringWidth("Explosion radius : " + String.valueOf(MyVariables.PlayerList.get(0).BombRadii)),(this.unit)/2);
        g.drawString("Time : " + String.valueOf(MyVariables.GameTime/1000), gap/2 + 9*gap +fm.stringWidth("Health : " + String.valueOf(MyVariables.PlayerList.get(0).Health))+fm.stringWidth("Stage : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Points : " + String.valueOf(MyVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(MyVariables.EnemiesList.size()))+fm.stringWidth("Bomb control : " + String.valueOf(MyVariables.PlayerList.get(0).ControlBombs))+fm.stringWidth("Ghost Mode : " + String.valueOf(MyVariables.PlayerList.get(0).GhostMode))+fm.stringWidth("Speed : " + String.valueOf(MyVariables.PlayerList.get(0).Speed/5))+fm.stringWidth("Explosion radius : " + String.valueOf(MyVariables.PlayerList.get(0).BombRadii))+fm.stringWidth("Bombs limit : " + String.valueOf(MyVariables.PlayerList.get(0).BombLimit)),(this.unit)/2);
    }

    public void UpAction(int clientIndex) {
        MyVariables.PlayerList.get(clientIndex).MoveUp(clientIndex);
    }

    public void LeftAction(int clientIndex) {
        MyVariables.PlayerList.get(clientIndex).MoveLeft(clientIndex);
    }

    public void RightAction(int clientIndex) {
        MyVariables.PlayerList.get(clientIndex).MoveRight(clientIndex);
    }

    public void DownAction(int clientIndex) {
        MyVariables.PlayerList.get(clientIndex).MoveDown(clientIndex);
    }

    public void BombAction(int clientIndex) {
        if (MyVariables.PlayerList.get(clientIndex).BombLimit > MyVariables.PlayerList.get(clientIndex).NumberOfBombedBombs) {
            Bomb NewBomb = new Bomb(clientIndex, this.unit * ((MyVariables.PlayerList.get(clientIndex).Coordiantes.x) / this.unit), this.unit * ((MyVariables.PlayerList.get(clientIndex).Coordiantes.y) / this.unit), MyVariables.PlayerList.get(clientIndex).ControlBombs, MyVariables.PlayerList.get(clientIndex).BombRadii);
            MyVariables.BombList.add(NewBomb);
            MyVariables.PlayerList.get(clientIndex).NumberOfBombedBombs++;
            this.repaint();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NewBomb.Trigger(Panel.this);
                }
            }).start();
        }
    }

    public void ExplosionAction(int clientIndex) {
        for (int i = 0; i < MyVariables.BombList.size(); i++) {
            if ((MyVariables.BombList.get(i).CONTROLABLE) && (MyVariables.BombList.get(i).OwnerIndex == clientIndex)) {
                synchronized (MyVariables.BombList.get(i)) {
                    MyVariables.BombList.get(i).notify();
                    break;
                }
            }

        }
    }

    public void UpdateClientMessages(int indexOfWriter, String message) {
        messageFrame.addMessage(clientVariablesArrayList.get(indexOfWriter).PlayerNames[indexOfWriter],message);
        for(int i=0;i<GameFrame.serverHandlers.size();i++){
            if((GameFrame.serverHandlers.get(i).isMessenger&&GameFrame.serverHandlers.get(i).panel==this)&&(GameFrame.serverHandlers.get(i).ClientIndex!=indexOfWriter)){
                GameFrame.serverHandlers.get(i).WriteMessages(indexOfWriter,message);
            }
        }
    }
}