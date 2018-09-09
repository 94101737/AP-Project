import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientPanel extends JPanel implements KeyListener {
    final int Height;
    final int Width;
    int unit;
    int player_height;
    int player_width;
    Point map_offset;
    boolean PlayerIsMoving;
    Font f = new Font(Font.SERIF, Font.PLAIN , 14);
    FontMetrics fm;
    int gap; // this variable is used to measure the gap between texts which display game properties on the top of panel
    BufferedImage[][] Images =new BufferedImage[30][10];
    ClientVariables clientVariables;
    Thread InputThread;
    PrintWriter printWriter;
    ObjectInputStream objectInputStream;
    boolean PlayerMode;
    String IP;
    int Port;
    MessageFrame messageFrame;
    Thread MessageThread;
    Scanner MessageScanner;
    PrintWriter MessagePrintWriter;
    Thread MessageInputThread;
    
    ClientPanel(Socket socket, boolean isPlayer, String ip, int port){
        this.IP = ip;
        this.Port = port;
        this.PlayerMode = isPlayer;

        this.setFocusable(true);
        addKeyListener(this);
        this.Height=GameFrame.MyJPanel.getBounds().height;
        this.Width=GameFrame.MyJPanel.getBounds().width;
        this.PlayerIsMoving = false;
        unit=72;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            clientVariables = (ClientVariables) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        while ((this.unit*clientVariables.ROWS<this.Height)|(this.unit*clientVariables.COLUMNS<this.Width)) {
            this.unit+=6;
        }
        player_height = (int) ((0.7)*unit);
        player_width = (int) (clientVariables.PlayerImageRatio[clientVariables.PlayerIndex]*player_height);
        int MAP_OFFSET_X = 0;
        int MAP_OFFSET_Y = 0;
        if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x>(this.Width/2)){
            if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x<(clientVariables.COLUMNS * this.unit - this.Width/2)){
                MAP_OFFSET_X = (this.Width/2)-clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x;
            }
            else {
                MAP_OFFSET_X = (-clientVariables.COLUMNS * this.unit + this.Width);
            }

        }
        if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y>(this.Height/2)){
            if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y<(clientVariables.ROWS * this.unit - this.Height/2)){
                MAP_OFFSET_Y = (this.Height/2)-clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y;
            }
            else {
                MAP_OFFSET_Y = (-clientVariables.ROWS * this.unit + this.Height);
            }
        }
        map_offset = new Point(MAP_OFFSET_X,MAP_OFFSET_Y);
        repaint();
        InputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        clientVariables = (ClientVariables)objectInputStream.readObject();
                        AdjustMapOffset();
                        ClientPanel.this.repaint();
                    } catch (IOException e) {

                    } catch (ClassNotFoundException e) {

                    }
                }
            }
        });
        InputThread.start();
        MessageInputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(MessageScanner.hasNext()){
                        int a = Integer.valueOf(MessageScanner.nextLine());
                        if(MessageScanner.hasNext()){
                            messageFrame.addMessage(clientVariables.PlayerNames[a],MessageScanner.nextLine());
                        }
                    }
                }
            }
        });
        messageFrame = new MessageFrame(this,500,500,"Messenger");
        MessageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket MessageSocket = new Socket(IP,Port);
                    MessagePrintWriter = new PrintWriter(new OutputStreamWriter(MessageSocket.getOutputStream(),"UTF-8"),true);
                    MessageScanner = new Scanner(MessageSocket.getInputStream(),"UTF-8");
                    MessagePrintWriter.println(9);
                    MessagePrintWriter.println(GameFrame.TargetPanelIndex);
                    MessagePrintWriter.println(clientVariables.PlayerIndex);
                    MessageInputThread.start();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MessageThread.start();


    }

    public void AdjustMapOffset(){
        int MAP_OFFSET_X = 0;
        int MAP_OFFSET_Y = 0;
        if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x>(this.Width/2)){
            if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x<(clientVariables.COLUMNS * this.unit - this.Width/2)){
                MAP_OFFSET_X = (this.Width/2)-clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x;
            }
            else {
                MAP_OFFSET_X = (-clientVariables.COLUMNS * this.unit + this.Width);
            }

        }
        if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y>(this.Height/2)){
            if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y<(clientVariables.ROWS * this.unit - this.Height/2)){
                MAP_OFFSET_Y = (this.Height/2)-clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y;
            }
            else {
                MAP_OFFSET_Y = (-clientVariables.ROWS * this.unit + this.Height);
            }
        }
        map_offset = new Point(MAP_OFFSET_X,MAP_OFFSET_Y);

        if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x>(this.Width/2)){
            if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x<(clientVariables.COLUMNS * this.unit - this.Width/2)){
                map_offset.x = (this.Width/2)-clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].x;
            }
            else {
                map_offset.x = (-clientVariables.COLUMNS * this.unit + this.Width);
            }

        }
        if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y>(this.Height/2)){
            if(clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y<(clientVariables.ROWS * this.unit - this.Height/2)){
                map_offset.y = (this.Height/2)-clientVariables.PlayerCoordinates[clientVariables.PlayerIndex].y;
            }
            else {
                map_offset.y = (-clientVariables.ROWS * this.unit + this.Height);
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (!this.PlayerIsMoving) {
            if(this.PlayerMode){
                if ((arg0.getKeyCode() == KeyEvent.VK_UP)) {
                    this.PlayerIsMoving = true;
                    printWriter.println("1");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
                if ((arg0.getKeyCode() == KeyEvent.VK_LEFT)) {
                    this.PlayerIsMoving = true;
                    printWriter.println("2");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
                if ((arg0.getKeyCode() == KeyEvent.VK_RIGHT)) {
                    this.PlayerIsMoving = true;
                    printWriter.println("3");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
                if ((arg0.getKeyCode() == KeyEvent.VK_DOWN)) {
                    this.PlayerIsMoving = true;
                    printWriter.println("4");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
                if ((arg0.getKeyCode() == KeyEvent.VK_B)) {
                    this.PlayerIsMoving = true;
                    printWriter.println("5");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
                if ((arg0.getKeyCode() == KeyEvent.VK_E)) {
                    this.PlayerIsMoving = true;
                    printWriter.println("6");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
            }
            else {
                if ((arg0.getKeyCode() == KeyEvent.VK_UP)) {
                    this.PlayerIsMoving = true;
                    if(this.map_offset.y<0){
                        this.map_offset.y+=5;
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
                if ((arg0.getKeyCode() == KeyEvent.VK_LEFT)) {
                    this.PlayerIsMoving = true;
                    if(this.map_offset.x<0){
                        this.map_offset.x+=5;
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
                if ((arg0.getKeyCode() == KeyEvent.VK_RIGHT)) {
                    this.PlayerIsMoving = true;
                    if(this.map_offset.x>(-(clientVariables.COLUMNS * this.unit - this.Width))){
                        this.map_offset.x-=5;
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
                if ((arg0.getKeyCode() == KeyEvent.VK_DOWN)) {
                    this.PlayerIsMoving = true;
                    if(map_offset.y>(-(clientVariables.ROWS * this.unit - this.Height))){
                        this.map_offset.y-=5;
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep((int) (10));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ClientPanel.this.PlayerIsMoving = false;
                        }
                    }).start();
                }
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(f);
        fm = g.getFontMetrics();

        //following 2 for loops are for drawing blocks
        for(int i=0;i<this.clientVariables.ROWS;i++) {
            for(int j=0;j<this.clientVariables.COLUMNS;j++) {
                g.drawImage(Images[clientVariables.BlockImageStyles[i][j].x][clientVariables.BlockImageStyles[i][j].y],clientVariables.BlockCoordinates[i][j].x+map_offset.x,clientVariables.BlockCoordinates[i][j].y+map_offset.y, unit, unit, null);
                if(clientVariables.BlockIsFiring[i][j]){
                    g.drawImage(Images[clientVariables.BlockFireImageStyles[i][j].x][clientVariables.BlockFireImageStyles[i][j].y],clientVariables.BlockCoordinates[i][j].x+map_offset.x,clientVariables.BlockCoordinates[i][j].y+map_offset.y, this.unit, this.unit, null);
                }
            }
        }
        //following for loop is for drawing bombs
        for(int i=0;i<clientVariables.NumberOfBombs;i++) {
            g.drawImage(Images[7][0], clientVariables.BombCoordinates[i].x+map_offset.x,clientVariables.BombCoordinates[i].y+map_offset.y,unit , unit, null);
        }
        //following for loop is for drawing player(s)
        for(int i=0;i<clientVariables.NumberOfPlayers;i++) {
            if(!clientVariables.PlayerIsFinished[i]){
                g.drawImage(Images[clientVariables.PlayerImageStyles[i].x][clientVariables.PlayerImageStyles[i].y], clientVariables.PlayerCoordinates[i].x+map_offset.x, clientVariables.PlayerCoordinates[i].y+map_offset.y, player_width, player_height, null);
                g.setColor(new Color(255,255,255,50));
                g.fillRect(clientVariables.PlayerCoordinates[i].x+map_offset.x-(g.getFontMetrics().stringWidth(clientVariables.PlayerNames[i])+10-player_width)/2,clientVariables.PlayerCoordinates[i].y+map_offset.y-30,g.getFontMetrics().stringWidth(clientVariables.PlayerNames[i])+10,20);
                g.setColor(new Color(0,0,0,255));
                g.drawString(clientVariables.PlayerNames[i],clientVariables.PlayerCoordinates[i].x+map_offset.x+(player_width-g.getFontMetrics().stringWidth(clientVariables.PlayerNames[i]))/2, clientVariables.PlayerCoordinates[i].y+map_offset.y-15);
            }
        }
        //following for loop is for drawing enemies
        for(int i=0;i<clientVariables.NumberOfEnemies;i++) {
            g.drawImage(Images[clientVariables.EnemyImageStyles[i].x][clientVariables.EnemyImageStyles[i].y], clientVariables.EnemyCoordinates[i].x+map_offset.x,clientVariables.EnemyCoordinates[i].y+map_offset.y , ((2*unit)/3), ((2*unit)/3),null);
        }
        //following lines is for drawing game specifications on top of the game panel
        g.setColor(new Color(255, 255, 255));
        gap = (this.Width - (fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Points : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(clientVariables.NumberOfEnemies))+fm.stringWidth("Bomb control : " + String.valueOf(clientVariables.PlayerControlBombs[clientVariables.PlayerIndex]))+fm.stringWidth("Ghost Mode : " + String.valueOf(clientVariables.PlayerGhostMode[clientVariables.PlayerIndex]))+fm.stringWidth("Speed : " + String.valueOf(clientVariables.PlayerSpeed[clientVariables.PlayerIndex]/5))+fm.stringWidth("Explosion radius : " + String.valueOf(clientVariables.PlayerBombRadii[clientVariables.PlayerIndex]))+fm.stringWidth("Bombs limit : " + String.valueOf(clientVariables.PlayerBombLimit[clientVariables.PlayerIndex]))+fm.stringWidth("Time : " + String.valueOf(clientVariables.stage))))/10;
        g.drawString("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]), gap/2,(this.unit)/2);
        g.drawString("Stage : " + String.valueOf(clientVariables.stage), gap/2 + gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex])) ,(this.unit)/2);
        g.drawString("Points : " + String.valueOf(clientVariables.PlayerPts[clientVariables.PlayerIndex]), gap/2 + 2*gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage)),(this.unit)/2);
        g.drawString("Remaining enemies : " + String.valueOf(clientVariables.NumberOfEnemies), gap/2 + 3*gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Points : " + String.valueOf(clientVariables.stage)) ,(this.unit)/2);
        g.drawString("Bomb control : " + String.valueOf(clientVariables.PlayerControlBombs[clientVariables.PlayerIndex]), gap/2 + 4*gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Points : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(clientVariables.NumberOfEnemies)) ,(this.unit)/2);
        g.drawString("Ghost Mode : " + String.valueOf(clientVariables.PlayerGhostMode[clientVariables.PlayerIndex]), gap/2 + 5*gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Points : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(clientVariables.NumberOfEnemies))+fm.stringWidth("Bomb control : " + String.valueOf(clientVariables.PlayerControlBombs[clientVariables.PlayerIndex])) ,(this.unit)/2);
        g.drawString("Speed : " + String.valueOf(clientVariables.PlayerSpeed[clientVariables.PlayerIndex]/5), gap/2 + 6*gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+ fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Points : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(clientVariables.NumberOfEnemies))+fm.stringWidth("Bomb control : " + String.valueOf(clientVariables.PlayerControlBombs[clientVariables.PlayerIndex]))+fm.stringWidth("Ghost Mode : " + String.valueOf(clientVariables.PlayerGhostMode[clientVariables.PlayerIndex])),(this.unit)/2);
        g.drawString("Explosion radius : " + String.valueOf(clientVariables.PlayerBombRadii[clientVariables.PlayerIndex]), gap/2 + 7*gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+ fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Points : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(clientVariables.NumberOfEnemies))+fm.stringWidth("Bomb control : " + String.valueOf(clientVariables.PlayerControlBombs[clientVariables.PlayerIndex]))+fm.stringWidth("Ghost Mode : " + String.valueOf(clientVariables.PlayerGhostMode[clientVariables.PlayerIndex]))+fm.stringWidth("Speed : " + String.valueOf(clientVariables.PlayerSpeed[clientVariables.PlayerIndex]/5)),(this.unit)/2);
        g.drawString("Bombs limit : " + String.valueOf(clientVariables.PlayerBombLimit[clientVariables.PlayerIndex]), gap/2 + 8*gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+ fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Points : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(clientVariables.NumberOfEnemies))+fm.stringWidth("Bomb control : " + String.valueOf(clientVariables.PlayerControlBombs[clientVariables.PlayerIndex]))+fm.stringWidth("Ghost Mode : " + String.valueOf(clientVariables.PlayerGhostMode[clientVariables.PlayerIndex]))+fm.stringWidth("Speed : " + String.valueOf(clientVariables.PlayerSpeed[clientVariables.PlayerIndex]/5))+fm.stringWidth("Explosion radius : " + String.valueOf(clientVariables.PlayerBombRadii[clientVariables.PlayerIndex])),(this.unit)/2);
        g.drawString("Time : " + String.valueOf(clientVariables.GameTime/1000), gap/2 + 9*gap +fm.stringWidth("Health : " + String.valueOf(clientVariables.PlayerHealth[clientVariables.PlayerIndex]))+fm.stringWidth("Stage : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Points : " + String.valueOf(clientVariables.stage))+fm.stringWidth("Remaining enemies : " + String.valueOf(clientVariables.NumberOfEnemies))+fm.stringWidth("Bomb control : " + String.valueOf(clientVariables.PlayerControlBombs[clientVariables.PlayerIndex]))+fm.stringWidth("Ghost Mode : " + String.valueOf(clientVariables.PlayerGhostMode[clientVariables.PlayerIndex]))+fm.stringWidth("Speed : " + String.valueOf(clientVariables.PlayerSpeed[clientVariables.PlayerIndex]/5))+fm.stringWidth("Explosion radius : " + String.valueOf(clientVariables.PlayerBombRadii[clientVariables.PlayerIndex]))+fm.stringWidth("Bombs limit : " + String.valueOf(clientVariables.PlayerBombLimit[clientVariables.PlayerIndex])),(this.unit)/2);
    }
    
}
