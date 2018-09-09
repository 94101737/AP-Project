import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameFrame {
    static ArrayList<String> GameNames;
    static ArrayList<String> IPArrayList;
    static ArrayList<Integer> NumberOfGamesInIPs;
    static String SelectedIP;
    static int NumberOfGamesInSelectedIP;
    static int SelectedPort;
    static int CurrentPanelIndex;
    static int TargetPanelIndex;
    static JFrame MyFrame ;
    static ArrayList<JPanel> MyPanels;
    static RawPanel MyJPanel;
    static JPanel MotherPanel;
    static CardLayout MyCardLayout;
    static JMenuBar MyMenuBar;
    static JMenu SinglePlayermenu;
    static JMenuItem newitem ;
    static JMenuItem openitem ;
    static JMenuItem saveitem ;
    static JMenuItem closeitem;
    static JMenu Mutiplayermenu;
    static JMenuItem MutiplayerNewitem ;
    static JMenuItem NewMultiplayerGame;
    static JMenuItem Messenger;
    static JMenu Helpmenu;
    static JMenuItem Guideitem ;
    static JMenuItem Aboutitem ;
    static Font f;

    public static void main(String[] args) {
        GameNames = new ArrayList<>();
        CurrentPanelIndex = 0;
        IPArrayList = new ArrayList<>();
        NumberOfGamesInIPs = new ArrayList<>();
        MyFrame = new JFrame("Client");
        MyFrame.setResizable(false);
        MyFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(MyFrame.getGraphicsConfiguration()).bottom);
        MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        MyFrame.setLayout(new BorderLayout());
        MyMenuBar = new JMenuBar();
        SinglePlayermenu = new JMenu("Single Player");
        newitem = new JMenuItem("New",KeyEvent.VK_N);
        openitem = new JMenuItem("Open",KeyEvent.VK_O);
        saveitem = new JMenuItem("Save",KeyEvent.VK_S);
        saveitem.setEnabled(false);
        closeitem = new JMenuItem("Close",KeyEvent.VK_C);
        Mutiplayermenu = new JMenu("Multiplayer");
        MutiplayerNewitem = new JMenuItem("Connection");
        NewMultiplayerGame = new JMenuItem("New");
        NewMultiplayerGame.setEnabled(false);
        Messenger = new JMenuItem("Messenger");
        Messenger.setEnabled(false);
        Helpmenu = new JMenu("Help");
        Guideitem = new JMenuItem("User Guide",KeyEvent.VK_U);
        Aboutitem = new JMenuItem("About Me",KeyEvent.VK_A);
        f = new Font("Serif", Font.PLAIN, 12);
        MyFrame.add(MyMenuBar, BorderLayout.NORTH);
        MotherPanel = new JPanel();
        MyFrame.add(MotherPanel,BorderLayout.CENTER);
        MyCardLayout = new CardLayout();
        MotherPanel.setLayout(MyCardLayout);
        MyJPanel = new RawPanel("Press  Ctrl+N  for a new game");
        MotherPanel.add(MyJPanel , "MyJPanel");
        MyPanels = new ArrayList<>();
        MyPanels.add(new Panel());
        MotherPanel.add(MyPanels.get(0) , "0");
        SinglePlayermenu.setFont(f);
        newitem.setFont(f);
        newitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
        openitem.setFont(f);
        openitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));
        saveitem.setFont(f);
        saveitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
        closeitem.setFont(f);
        closeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
        SinglePlayermenu.add(newitem);
        SinglePlayermenu.add(openitem);
        SinglePlayermenu.add(saveitem);
        SinglePlayermenu.addSeparator();
        SinglePlayermenu.add(closeitem);
        Mutiplayermenu.setFont(f);
        MutiplayerNewitem.setFont(f);
        Mutiplayermenu.add(MutiplayerNewitem);
        NewMultiplayerGame.setFont(f);
        Mutiplayermenu.add(NewMultiplayerGame);
        Messenger.setFont(f);
        Mutiplayermenu.add(Messenger);
        Helpmenu.setFont(f);
        Helpmenu.setMnemonic(KeyEvent.VK_H);
        Guideitem.setFont(f);
        Guideitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_DOWN_MASK));
        Aboutitem.setFont(f);
        Aboutitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK));
        Helpmenu.add(Guideitem);
        Helpmenu.add(Aboutitem);
        MyMenuBar.add(SinglePlayermenu);
        MyMenuBar.add(Mutiplayermenu);
        MyMenuBar.add(Helpmenu);
        MyFrame.add(MotherPanel);
        try {
            MyFrame.setIconImages(Collections.singletonList(ImageIO.read(new File("resources/bomberman.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyFrame.setVisible(true);
        MyFrame.repaint();

        newitem.addActionListener(new ActionListener(
        ) {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                NewDialogBox message = new NewDialogBox(GameFrame.MyFrame, "New Game");
                message.setVisible(true);

            }
        });
        openitem.addActionListener(new ActionListener(
        ) {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub

                LoadDialog loadDialog = new LoadDialog(GameFrame.MyFrame, "Load");
                loadDialog.setVisible(true);

            }
        });
        saveitem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                SaveDialog saveDialog = new SaveDialog(GameFrame.MyFrame, "Save");
                saveDialog.setVisible(true);
            }
        });
        closeitem.addActionListener(new ActionListener(
        ) {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                MotherPanel.remove(GameFrame.MyPanels.size()-1);
                MyCardLayout.first(GameFrame.MotherPanel);
                GameFrame.saveitem.setEnabled(false);
                MotherPanel.repaint();
            }
        });
        MutiplayerNewitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MultiplayerDialog connection = new MultiplayerDialog(GameFrame.MyFrame, "Connection");
                connection.setVisible(true);
            }
        });
        NewMultiplayerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MultiplayerNewDialog multiplayerNewDialog = new MultiplayerNewDialog(GameFrame.MyFrame, "New Game");
                multiplayerNewDialog.setVisible(true);
            }
        });
        Messenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientPanel auxiliaryPanel = (ClientPanel) GameFrame.MyPanels.get(CurrentPanelIndex);
                auxiliaryPanel.messageFrame.setVisible(true);
            }
        });
        Guideitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string[][] = {
                        {"The game has four stages and you can go to next stage after killing all enemies in stage which you are in.","Following is picture of stone blocks in the game; you can not pass such blocks except you eat ghost mode power up.","Following is picture of wall blocks; you can not pass such blocks except you explode them with bomb.","You can Go to higher stages by achieving door after killing all enemies"},
                        {"You can bomb a bomb with pressing B key on your keyboard; each bomb has a radius an can be controlled by eating suitable power up "},
                        {"By eating GHOST power-up, you can pass stone blocks (except border blocks).","By eating BOMB CONTROL power-up, you can explode your oldest bomb by pressing E on keyboard.","By eating SPEED INCREASE power-up, your speed increases 1 unit.","By eating SPEED DECREASE power-up, your speed decreases 1 unit but it does not become less than its primary value.","By eating INCREASE BOMB RADIUS power-up, your bombs radius increases 1 unit.","By eating DECREASE BOMB RADIUS power-up, your bombs radius decreases 1 unit but it does not become less than its primary value.","By eating INCREASE POINTS power-up, your points increases 100 units","By eating DECREASE POINTS power-up, your points decreases 100 units"},
                        {"1st type of enemies has half of player's initial speed and can't pass walls or stones; it decides randomly to which direction moves after each movement. this type appears in all stages.","2nd type of enemies has speed equal with player's initial speed and can't pass walls or stones; it decides based on minimizing its manhattans distance from player after each movement. this type appears after stage 1.","3rd type of enemies has twice of player's initial speed and can't pass walls or stones; it decides based on minimizing its manhattans distance from player after each movement. this type appears after stage 2.","3rd type of enemies has twice of player's initial speed and can pass walls or stones (except border blocks); it decides based on minimizing its manhattans distance from player after each movement. this type appears after stage 3."}
                };
                String icons[][] = {
                        {"","resources/Tiles/SolidBlock.png","resources/Tiles/ExplodableBlock.png","resources/PowerUps/Door.png"},
                        {"resources/Bomb/Bomb_f01.png"},
                        {"resources/PowerUps/Sprite.png","resources/PowerUps/ControlBomb.png","resources/PowerUps/IncreaseSpeed.png","resources/PowerUps/DecreaseSpeed.png","resources/PowerUps/IncreaseRaduis.png","resources/PowerUps/DecreaseRaduis.png","resources/PowerUps/IncreaseBombs.png","resources/PowerUps/DecreaseBombs.png","resources/PowerUps/PointIncrease.png","resources/PowerUps/PointDecrease.png"},
                        {"resources/Enemy1/Left/Creep_L_f00.png","resources/Enemy2/Left/Creep_L_f00.png","resources/Enemy3/Left/Creep_L_f00.png","resources/Enemy4/Left/Creep_L_f00.png"}
                };
                String groups[]= {"Game Story","Bombs","Power-ups","Enemies"};
                RawFrame rawFrame = new RawFrame(500,500,f,"User Guide",string,groups, icons);
            }
        });
        Aboutitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string[][] = {
                        {"This game is made by Aref Sadeghian, an undergraduate student at department of mathematical sciences of Sharif university of technology, as ADVANCED PROGRAMMING course project."}
                };
                String icons[][] = {
                        {"resources/programming.png"}
                };
                String groups[]= {"About Me"};
                RawFrame rawFrame = new RawFrame(500,500,f,"About Me",string,groups, icons);
            }
        });
    }

    public static void UpdateGameNames(ArrayList<String> gameNames, String usernameOfDatabase, String passwordOfDatabase) {
        gameNames.clear();
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/BOMBERMAN?useSSL=false",  usernameOfDatabase, passwordOfDatabase);
            stmt = conn.createStatement();

            String sql = "SELECT GAME_NAME FROM NAME_OF_GAMES";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                gameNames.add( rs.getString("GAME_NAME"));
            }
            rs.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se) {
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public static void SaveGame(String name,String usernameOfDatabase, String passwordOfDatabase) {
        Connection conn1 = null;
        Statement stmt1 = null;
        try{
            conn1 = DriverManager.getConnection("jdbc:mysql://localhost/BOMBERMAN?useSSL=false",  usernameOfDatabase, passwordOfDatabase);
            stmt1 = conn1.createStatement();
        }catch(SQLException se) {
            try {
                conn1 = DriverManager.getConnection("jdbc:mysql://localhost/?useSSL=false",  usernameOfDatabase, passwordOfDatabase);
                stmt1 = conn1.createStatement();
                stmt1.executeUpdate("CREATE DATABASE BOMBERMAN");
                stmt1.executeUpdate("USE BOMBERMAN");
                stmt1.executeUpdate("CREATE TABLE NAME_OF_GAMES " +
                        "(GAME_NAME VARCHAR(255)," +
                        "PRIMARY KEY (GAME_NAME))");
                stmt1.executeUpdate("CREATE TABLE GAME_PROPERTIES " +
                        "(NAME VARCHAR(255), " +
                        "ROWS INTEGER, " +
                        "COLUMNS INTEGER, " +
                        "GAME_TIME INTEGER, " +
                        "STAGE INTEGER, " +
                        "GAME_IS_FINISHED BIT)");
                stmt1.executeUpdate("CREATE TABLE PLAYER_TABLE " +
                        "(NAME VARCHAR (255), " +
                        "COORDINATE_X INTEGER, " +
                        "COORDINATE_Y INTEGER, " +
                        "IMAGE_STYLE_0 DOUBLE, " +
                        "IMAGE_STYLE_1 DOUBLE, " +
                        "HEALTH INTEGER, " +
                        "POINTS INTEGER, " +
                        "BOMB_LIMIT INTEGER, " +
                        "BOMB_RADII INTEGER, " +
                        "CONTROL_BOMB BIT, " +
                        "GHOST_MODE BIT, " +
                        "SPEED INTEGER)");
                stmt1.executeUpdate("CREATE TABLE BOMBS_TABLE " +
                        "(NAME VARCHAR (255)," +
                        "COORDINATE_X INTEGER, " +
                        "COORDINATE_Y INTEGER, " +
                        "CONTROLABLE BIT , " +
                        "EXPLOSION_RADIUS INTEGER)");
                stmt1.executeUpdate("CREATE TABLE ENEMY_TABLE " +
                        "(NAME VARCHAR (255)," +
                        " ENEMY_TYPE INTEGER, " +
                        " COORDINATE_X INTEGER, " +
                        " COORDINATE_Y INTEGER, " +
                        " IMAGE_STYLE_0 DOUBLE, " +
                        " IMAGE_STYLE_1 DOUBLE, " +
                        " DIRECTION INTEGER, " +
                        " MOVE_PROGRESS INTEGER)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }finally{
            try{
                if(stmt1!=null)
                    stmt1.close();
            }catch(SQLException se2){
            }
            try{
                if(conn1!=null)
                    conn1.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        Connection conn2 = null;
        Statement stmt2 = null;
        try{
            conn2 = DriverManager.getConnection("jdbc:mysql://localhost/BOMBERMAN?useSSL=false",  usernameOfDatabase, passwordOfDatabase);
            stmt2 = conn2.createStatement();
            stmt2.executeUpdate("INSERT INTO NAME_OF_GAMES " +
                                    "VALUES ('"+name+"')");
            Panel auxiliaryPanel = (Panel) MyPanels.get(CurrentPanelIndex);
            auxiliaryPanel.MyVariables.save(name,usernameOfDatabase,passwordOfDatabase);
            GameFrame.UpdateGameNames(GameNames,usernameOfDatabase,passwordOfDatabase);
        }catch(SQLException se){

        }finally{
            try{
                if(stmt2!=null)
                    stmt2.close();
            }catch(SQLException se){
            }
            try{
                if(conn2!=null)
                    conn2.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }

    public static void LoadGame(String selectedItem,String usernameOfDatabase, String passwordOfDatabase) {
        Connection conn1 = null;
        Statement stmt1 = null;
        try{
            conn1 = DriverManager.getConnection("jdbc:mysql://localhost/BOMBERMAN?useSSL=false",  usernameOfDatabase, passwordOfDatabase);
            stmt1 = conn1.createStatement();
        }catch(SQLException se) {
            try {
                conn1 = DriverManager.getConnection("jdbc:mysql://localhost/?useSSL=false",  usernameOfDatabase, passwordOfDatabase);
                stmt1 = conn1.createStatement();
                stmt1.executeUpdate("CREATE DATABASE BOMBERMAN");
                stmt1.executeUpdate("USE BOMBERMAN");
                stmt1.executeUpdate("CREATE TABLE NAME_OF_GAMES " +
                        "(GAME_NAME VARCHAR(255)," +
                        "PRIMARY KEY (GAME_NAME))");
                stmt1.executeUpdate("CREATE TABLE GAME_PROPERTIES " +
                        "(NAME VARCHAR(255), " +
                        "ROWS INTEGER, " +
                        "COLUMNS INTEGER, " +
                        "GAME_TIME INTEGER, " +
                        "STAGE INTEGER, " +
                        "GAME_IS_FINISHED BIT) ");
                stmt1.executeUpdate("CREATE TABLE PLAYER_TABLE " +
                        "(NAME VARCHAR (255), " +
                        "COORDINATE_X INTEGER, " +
                        "COORDINATE_Y INTEGER, " +
                        "IMAGE_STYLE_0 DOUBLE, " +
                        "IMAGE_STYLE_1 DOUBLE, " +
                        "HEALTH INTEGER, " +
                        "POINTS INTEGER, " +
                        "BOMB_LIMIT INTEGER, " +
                        "BOMB_RADII INTEGER, " +
                        "CONTROL_BOMB BIT, " +
                        "GHOST_MODE BIT, " +
                        "SPEED INTEGER) ");
                stmt1.executeUpdate("CREATE TABLE BOMBS_TABLE " +
                        "(NAME VARCHAR (255)," +
                        "COORDINATE_X INTEGER, " +
                        "COORDINATE_Y INTEGER, " +
                        "CONTROLABLE BIT , " +
                        "EXPLOSION_RADIUS INTEGER)");
                stmt1.executeUpdate("CREATE TABLE ENEMY_TABLE " +
                        "(NAME VARCHAR (255)," +
                        " ENEMY_TYPE INTEGER, " +
                        " COORDINATE_X INTEGER, " +
                        " COORDINATE_Y INTEGER, " +
                        " IMAGE_STYLE_0 DOUBLE, " +
                        " IMAGE_STYLE_1 DOUBLE, " +
                        " DIRECTION INTEGER, " +
                        " MOVE_PROGRESS INTEGER)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }finally{
            try{
                if(stmt1!=null)
                    stmt1.close();
            }catch(SQLException se2){
            }
            try{
                if(conn1!=null)
                    conn1.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        GameFrame.MyPanels.add(new Panel(selectedItem,usernameOfDatabase,passwordOfDatabase));
        GameFrame.MotherPanel.add(GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1) , String.valueOf(GameFrame.MyPanels.size()-1));
        GameFrame.MyCardLayout.last(GameFrame.MotherPanel);
        GameFrame.MyPanels.get(GameFrame.MyPanels.size()-1).requestFocusInWindow();
        GameFrame.MotherPanel.repaint();
        GameFrame.saveitem.setEnabled(true);
        GameFrame.CurrentPanelIndex = GameFrame.MyPanels.size()-1;
        GameFrame.MyFrame.repaint();
    }
}