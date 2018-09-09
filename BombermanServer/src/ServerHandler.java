import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerHandler implements Runnable{
    public Scanner scanner;
    public ObjectOutputStream objectOutputStream;
    public PrintWriter printWriter;
    public Panel panel;
    public Thread InputThread;
    public Thread OutputThread;
    public int ClientIndex;
    public boolean isAsPlayer;
    public Socket socket;
    public boolean isMessenger;

    ServerHandler(Socket socket1){
        isMessenger = false;
        this.socket = socket1;
        try {
            this.scanner = new Scanner(socket.getInputStream(),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.InputThread=new Thread(new Runnable() {
            @Override
            public void run() {
                scanner.reset();
                a: while (true){
                    if (scanner.hasNext()){
                        switch (Integer.parseInt(scanner.nextLine())){
                            case 0:
                                PrintWriter printWriter = null;
                                try {
                                    printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                printWriter.println(GameFrame.MyPanels.size()-1);
                                break;
                            case 1:
                                if((panel !=null)&&(!panel.MyVariables.PlayerList.get(ClientIndex).GameIsFinished)){
                                    panel.UpAction(ClientIndex);
                                    break;
                                }
                            case 2:
                                if((panel !=null)&&(!panel.MyVariables.PlayerList.get(ClientIndex).GameIsFinished)){
                                    panel.LeftAction(ClientIndex);
                                    break;
                                }
                            case 3:
                                if((panel !=null)&&(!panel.MyVariables.PlayerList.get(ClientIndex).GameIsFinished)){
                                    panel.RightAction(ClientIndex);
                                    break;
                                }
                            case 4:
                                if((panel !=null)&&(!panel.MyVariables.PlayerList.get(ClientIndex).GameIsFinished)){
                                    panel.DownAction(ClientIndex);
                                    break;
                                }
                            case 5:
                                if((panel !=null)&&(!panel.MyVariables.PlayerList.get(ClientIndex).GameIsFinished)){
                                    panel.BombAction(ClientIndex);
                                    break;
                                }
                            case 6:
                                if((panel !=null)&&(!panel.MyVariables.PlayerList.get(ClientIndex).GameIsFinished)){
                                    panel.ExplosionAction(ClientIndex);
                                    break;
                                }
                            case 7:
                                setPanel(Integer.parseInt(scanner.nextLine()));
                                isAsPlayer = false;
                                ClientIndex = 0;
                                StartAsViewer();
                                break;
                            case 8:
                                setPanel(Integer.parseInt(scanner.nextLine()));
                                if (panel.PLAYER_CAPACITY>panel.MyVariables.PlayerList.size()){
                                    isAsPlayer = true;
                                    ClientIndex = panel.MyVariables.PlayerList.size();
                                    StartAsPlayer(scanner.nextLine());
                                }
                                break;
                            case 9:
                                setPanel(Integer.parseInt(scanner.nextLine()));
                                StartAsMessenger(Integer.parseInt(scanner.nextLine()));
                                isMessenger = true;
                                break a;
                        }
                    }
                }
                while (true){
                    if (scanner.hasNext()){
                        panel.UpdateClientMessages(ClientIndex,scanner.nextLine());
                    }
                }
            }
        });
        this.OutputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        panel.UpdateClientVariables(panel.clientVariablesArrayList.get(ClientIndex));
                        objectOutputStream.reset();
                        objectOutputStream.writeObject(panel.clientVariablesArrayList.get(ClientIndex));
                        Thread.sleep(10);
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    @Override
    public void run() {
        InputThread.start();
    }
    public void StartAsViewer(){
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(panel.clientVariablesArrayList.get(ClientIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OutputThread.start();
    }
    public void StartAsPlayer(String name){
        panel.MyVariables.PlayerList.add(new Player(panel, panel.unit, panel.unit, 3,0,3,0,1,1,false,false,ClientIndex,name));
        panel.clientVariablesArrayList.add(panel.MakeClientVariables(ClientIndex));
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(panel.clientVariablesArrayList.get(ClientIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OutputThread.start();
    }
    public void StartAsMessenger (int index){
        this.ClientIndex = index;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void WriteMessages(int indexOfWriter, String message){
        try {
            printWriter.println(indexOfWriter);
            printWriter.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setPanel (int index){
        this.panel = GameFrame.MyPanels.get(index);
    }
}
