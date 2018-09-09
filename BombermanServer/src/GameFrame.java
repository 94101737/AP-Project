import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameFrame {
    static Thread ServerThread;
    static Dialog PortDialogBox;
    static JPanel DialogBoxPanel;
    static GridLayout DialogLayout;
    static JTextField HostPortTextField;
    static JLabel HostPortLabel;
    static JButton OK;
    static JButton Cancel;
    static String Host;
    static int HostPort;
    static ArrayList<String> capacityArrayList;
    static int CurrentPanelIndex;
    static JFrame MyFrame ;
    static ArrayList<Panel> MyPanels;
    static ArrayList<Thread> threadArrayList;
    static ArrayList<ServerHandler> serverHandlers;
    static int NumberOfClients;
    static RawPanel MyJPanel;
    static JPanel MotherPanel;
    static CardLayout MyCardLayout;
    static JMenuBar MyMenuBar;
    static JMenu filemenu;
    static JMenuItem newitem ;
    static JMenuItem CurrentGamesitem ;
    static JMenuItem CurrentGamesPtsitem ;
    static JMenuItem MessengerItem;
    static JMenuItem closeitem;
    static JMenu Developer;
    static JMenuItem LoadClassItem;
    static JMenu Helpmenu;
    static JMenuItem Guideitem ;
    static JMenuItem Aboutitem ;
    static Font f;
    public static void main(String[] args) {
        NumberOfClients = 0;
        threadArrayList = new ArrayList<>();
        serverHandlers = new ArrayList<>();
        capacityArrayList = new ArrayList<>();
        MyFrame = new JFrame("Server");
        MyFrame.setResizable(false);
        MyFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
                 Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(MyFrame.getGraphicsConfiguration()).bottom);
        MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        MyFrame.setLayout(new BorderLayout());
        MyMenuBar = new JMenuBar();
        filemenu = new JMenu("File");
        newitem = new JMenuItem("New",KeyEvent.VK_N);
        CurrentGamesitem = new JMenuItem("Current Games");
        CurrentGamesPtsitem = new JMenuItem("Games Points");
        MessengerItem = new JMenuItem("Messenger");
        closeitem = new JMenuItem("Close",KeyEvent.VK_C);
        Developer = new JMenu("Edit");
        LoadClassItem = new JMenuItem("Load Class");
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
        capacityArrayList.add("0");
        filemenu.setFont(f);
        filemenu.setMnemonic(KeyEvent.VK_F);
        newitem.setFont(f);
        newitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
        closeitem.setFont(f);
        closeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
        CurrentGamesitem.setFont(f);
        CurrentGamesPtsitem.setFont(f);
        MessengerItem.setFont(f);
        filemenu.add(newitem);
        filemenu.add(CurrentGamesitem);
        CurrentGamesitem.setEnabled(false);
        filemenu.add(CurrentGamesPtsitem);
        CurrentGamesPtsitem.setEnabled(false);
        filemenu.add(MessengerItem);
        MessengerItem.setEnabled(false);
        filemenu.addSeparator();
        filemenu.add(closeitem);
        closeitem.setEnabled(false);
        Developer.setFont(f);
        Developer.setMnemonic(KeyEvent.VK_E);
        LoadClassItem.setFont(f);
        LoadClassItem.setEnabled(false);
        Developer.add(LoadClassItem);
        Helpmenu.setFont(f);
        Helpmenu.setMnemonic(KeyEvent.VK_H);
        Guideitem.setFont(f);
        Guideitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_DOWN_MASK));
        Aboutitem.setFont(f);
        Aboutitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK));
        Helpmenu.add(Guideitem);
        Helpmenu.add(Aboutitem);
        MyMenuBar.add(filemenu);
        MyMenuBar.add(Developer);
        MyMenuBar.add(Helpmenu);
        MyFrame.add(MotherPanel);
        try {
            MyFrame.setIconImages(Collections.singletonList(ImageIO.read(new File("resources/bomberman.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyFrame.setVisible(true);
        MyFrame.repaint();
        ServerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(GameFrame.HostPort)){
                    while (true){
                        Socket socket = serverSocket.accept();
                        NumberOfClients++;
                        serverHandlers.add(new ServerHandler(socket));
                        Thread thread = new Thread(serverHandlers.get(NumberOfClients-1));
                        thread.start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        newitem.addActionListener(new ActionListener(
        ) {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                threadArrayList.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NewDialogBox message = new NewDialogBox(GameFrame.MyFrame, "New Game");
                        message.setVisible(true);
                    }
                }));
                threadArrayList.get(threadArrayList.size()-1).start();

            }
        });
        CurrentGamesitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamesListDialogBox gamesListDialogBox = new GamesListDialogBox(GameFrame.MyFrame, "Games", capacityArrayList);
                gamesListDialogBox.setVisible(true);
            }
        });
        CurrentGamesPtsitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePointsDialogBox gamePointsDialogBox = new GamePointsDialogBox(GameFrame.MyFrame, "Games", capacityArrayList);
                gamePointsDialogBox.setVisible(true);
            }
        });
        MessengerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyPanels.get(CurrentPanelIndex).messageFrame.setVisible(true);
            }
        });
        closeitem.addActionListener(new ActionListener(
        ) {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                MyPanels.get(CurrentPanelIndex).MyVariables.GameIsFinished = true;
                MyCardLayout.first(GameFrame.MotherPanel);
                MotherPanel.repaint();
            }
        });
        LoadClassItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                FileDialog FileLoader = new FileDialog(GameFrame.MyFrame, "Choose Your Previous Save", FileDialog.LOAD);
                FileLoader.setVisible(true);
                if((FileLoader.getFile()==null)||(!FileLoader.getFile().endsWith(".class"))) {

                }
                else {
                    try {
                        GameFrame.MyPanels.get(CurrentPanelIndex).NameOfClassToBeLoaded.add(FileLoader.getFile().substring(0,FileLoader.getFile().lastIndexOf(".")));
                        Path from = Paths.get(FileLoader.getDirectory() + FileLoader.getFile());
                        Path to = Paths.get("out/production/BombermanServer/"+FileLoader.getFile());
                        Files.copy(from,to, StandardCopyOption.REPLACE_EXISTING);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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

        PortDialogBox = new Dialog(MyFrame,"Host Port",true);
        PortDialogBox.setSize(GameFrame.MyFrame.getBounds().width/5, GameFrame.MyFrame.getBounds().height/6);
        PortDialogBox.setLocation(4*GameFrame.MyFrame.getBounds().width/10, 4*GameFrame.MyFrame.getBounds().height/10);
        DialogLayout = new GridLayout(2,2,20,20);
        DialogBoxPanel = new JPanel();
        DialogBoxPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        DialogBoxPanel.setLayout(DialogLayout);

        HostPortLabel = new JLabel("Port number : ", JLabel.LEFT);
        HostPortLabel.setFont(f);
        DialogBoxPanel.add(HostPortLabel);

        HostPortTextField = new JTextField();
        HostPortTextField.setFont(f);
        HostPortTextField.setBorder(new EmptyBorder(0,10,0,10));
        DialogBoxPanel.add(HostPortTextField);

        Cancel = new JButton("Exit");
        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.MyFrame.setVisible(false);
                System.exit(0);
            }
        });
        Cancel.setFont(f);
        DialogBoxPanel.add(Cancel);

        OK = new JButton("Enter");
        OK.addActionListener(new ActionListener() {
            boolean Acceptable = true;
            @Override
            public void actionPerformed(ActionEvent e) {
                Host = HostPortTextField.getText();
                try {
                    HostPort = Integer.parseInt(Host);
                    Acceptable = true;
                    ServerThread.start();
                }catch (NumberFormatException nfe){
                    HostPortTextField.setText("");
                    Acceptable = false;
                }
                if (Acceptable){
                    PortDialogBox.setVisible(false);
                }

            }
        });
        OK.setFont(GameFrame.f);
        DialogBoxPanel.add(OK);

        PortDialogBox.add(DialogBoxPanel);
        PortDialogBox.setVisible(true);
    }

}

