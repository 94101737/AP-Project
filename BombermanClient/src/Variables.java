import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.sql.*;

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
    public ArrayList<Bomb> BombList;
    public Player player;
    public ArrayList<AbstractEnemy> EnemiesList;

    Variables (Panel panel,int r, int c, int stg, int numberOfEnemies) {
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
        player = new Player(panel,0,0,3,0,3,0,1,1,false,false);
        EnemiesList = new ArrayList<>();
        ArrayList<Point> EnemyAddress = new ArrayList<>();
        for(int i=0;i<ROWS;i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if(table[i][j]==GROUND){
                    EnemyAddress.add(new Point(i,j));
                }
            }
        }
        Collections.shuffle(EnemyAddress);
        // following switch statement determines the arrangement of enemies
        switch (this.stage){
            case 1:
                for (int i=0; i< numberOfEnemies;i++){
                    EnemiesList.add(new Enemy1(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                }
                break;
            case 2:
                for (int i=0; i< numberOfEnemies;i++){
                    double auxiliary = random.nextDouble();
                    if(auxiliary<0.5){
                        EnemiesList.add(new Enemy1(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else {
                        EnemiesList.add(new Enemy2(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                }
                break;
            case 3:
                for (int i=0; i< numberOfEnemies;i++){
                    double auxiliary = random.nextDouble();
                    if(auxiliary<0.33){
                        EnemiesList.add(new Enemy1(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if(auxiliary<0.66){
                        EnemiesList.add(new Enemy2(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else {
                        EnemiesList.add(new Enemy3(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                }
                break;
            case 4:
                for (int i=0; i< numberOfEnemies;i++){
                    double auxiliary = random.nextDouble();
                    if(auxiliary<0.25){
                        EnemiesList.add(new Enemy1(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if(auxiliary<0.5){
                        EnemiesList.add(new Enemy2(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else if (auxiliary<0.75){
                        EnemiesList.add(new Enemy3(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                    else {
                        EnemiesList.add(new Enemy4(panel,(EnemyAddress.get(i).y*panel.unit)+(panel.unit/6),(EnemyAddress.get(i).x*panel.unit)+(panel.unit/6),16,0,1));
                    }
                }
                break;
        }

    }

    Variables(Panel panel, String name, String usernameOfDatabase, String passwordOfDatabase) {
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/BOMBERMAN?useSSL=false", usernameOfDatabase, passwordOfDatabase);
            stmt = conn.createStatement();

            //Fetching 1st table of DB
            ResultSet rs = stmt.executeQuery("SELECT  ROWS, COLUMNS, GAME_TIME,STAGE,GAME_IS_FINISHED FROM GAME_PROPERTIES WHERE NAME = '"+name+"'");
            while(rs.next()){
                ROWS = rs.getInt("ROWS");
                COLUMNS = rs.getInt("COLUMNS");
                GameTime = rs.getInt("GAME_TIME");
                stage = rs.getInt("STAGE");
                GameIsFinished = rs.getBoolean("GAME_IS_FINISHED");
            }
            rs.close();

            //Fetching 2nd table of DB
            table = new int[ROWS][COLUMNS];
            String sql = "SELECT ";
            for (int j = 0; j < this.COLUMNS; j++) {
                sql = sql.concat("_"+String.valueOf(j));
                if(j!=COLUMNS-1) {
                    sql = sql.concat(",");
                }
            }
            sql = sql.concat(" FROM TABLE_OF_GAME_"+name);
            rs = stmt.executeQuery(sql);
            for(int i=0; rs.next();i++) {
                for(int j=0;j<this.COLUMNS;j++) {
                    table[i][j] = rs.getInt("_"+String.valueOf(j));
                }
            }
            rs.close();

            //Fetching 3rd table of DB
            shadowTable = new int[ROWS][COLUMNS];
            sql = "SELECT ";
            for (int j = 0; j < this.COLUMNS; j++) {
                sql = sql.concat("_"+String.valueOf(j));
                if(j!=COLUMNS-1) {
                    sql = sql.concat(",");
                }
            }
            sql = sql.concat(" FROM SHADOW_TABLE_OF_GAME_"+name);
            rs = stmt.executeQuery(sql);
            for(int i=0; rs.next();i++) {
                for(int j=0;j<this.COLUMNS;j++) {
                    shadowTable[i][j] = rs.getInt("_"+String.valueOf(j));
                }
            }
            rs.close();

            //Fetching 4th table of DB
            rs = stmt.executeQuery("SELECT  COORDINATE_X, COORDINATE_Y, IMAGE_STYLE_0,IMAGE_STYLE_1,HEALTH,POINTS,BOMB_LIMIT,BOMB_RADII,CONTROL_BOMB,GHOST_MODE,SPEED FROM PLAYER_TABLE WHERE NAME = '"+name+"'");
            rs.next();
            player = new Player(panel, rs.getInt("COORDINATE_X"), rs.getInt("COORDINATE_Y"), rs.getDouble("IMAGE_STYLE_0"), rs.getDouble("IMAGE_STYLE_1"), rs.getInt("HEALTH"), rs.getInt("POINTS"), rs.getInt("BOMB_LIMIT"), rs.getInt("BOMB_RADII"), rs.getBoolean("CONTROL_BOMB"), rs.getBoolean("GHOST_MODE"));
            player.Speed = rs.getInt("SPEED");

            //Fetching 5th table of DB
            BombList = new ArrayList<>();
            rs = stmt.executeQuery("SELECT  COORDINATE_X, COORDINATE_Y, CONTROLABLE,EXPLOSION_RADIUS FROM BOMBS_TABLE WHERE NAME = '"+name+"'");
            while (rs.next()){
                BombList.add(new Bomb(rs.getInt("COORDINATE_X"), rs.getInt("COORDINATE_Y"), rs.getBoolean("CONTROLABLE"), rs.getInt("EXPLOSION_RADIUS")));
            }
            rs.close();

            //Fetching 6th table of DB
            EnemiesList = new ArrayList<>();
            rs = stmt.executeQuery("SELECT  ENEMY_TYPE,COORDINATE_X, COORDINATE_Y,IMAGE_STYLE_0,IMAGE_STYLE_1, DIRECTION,MOVE_PROGRESS FROM ENEMY_TABLE WHERE NAME = '"+name+"'");
            int i = 0;
            while (rs.next()){
                switch (rs.getInt("ENEMY_TYPE")) {
                    case 1:
                        EnemiesList.add(new Enemy1(panel, rs.getInt("COORDINATE_X"), rs.getInt("COORDINATE_Y"), rs.getDouble("IMAGE_STYLE_0"), rs.getDouble("IMAGE_STYLE_1"), rs.getInt("DIRECTION")));
                        EnemiesList.get(i).MoveProgress = rs.getInt("MOVE_PROGRESS");
                        break;
                    case 2:
                        EnemiesList.add(new Enemy2(panel, rs.getInt("COORDINATE_X"), rs.getInt("COORDINATE_Y"), rs.getDouble("IMAGE_STYLE_0"), rs.getDouble("IMAGE_STYLE_1"), rs.getInt("DIRECTION")));
                        EnemiesList.get(i).MoveProgress = rs.getInt("MOVE_PROGRESS");
                        break;
                    case 3:
                        EnemiesList.add(new Enemy3(panel, rs.getInt("COORDINATE_X"), rs.getInt("COORDINATE_Y"), rs.getDouble("IMAGE_STYLE_0"), rs.getDouble("IMAGE_STYLE_1"), rs.getInt("DIRECTION")));
                        EnemiesList.get(i).MoveProgress = rs.getInt("MOVE_PROGRESS");
                        break;
                    case 4:
                        EnemiesList.add(new Enemy4(panel, rs.getInt("COORDINATE_X"), rs.getInt("COORDINATE_Y"), rs.getDouble("IMAGE_STYLE_0"), rs.getDouble("IMAGE_STYLE_1"), rs.getInt("DIRECTION")));
                        EnemiesList.get(i).MoveProgress = rs.getInt("MOVE_PROGRESS");
                        break;
                }
                i++;
            }
            rs.close();

        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public void save(String name,String usernameOfDatabase, String passwordOfDatabase) {
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/BOMBERMAN?useSSL=false", usernameOfDatabase, passwordOfDatabase);
            stmt = conn.createStatement();
            stmt.executeUpdate("USE BOMBERMAN");

            //first table of DB
            stmt.executeUpdate("INSERT INTO GAME_PROPERTIES " +
                    "VALUES ('"+name+"', "+this.ROWS+", "+this.COLUMNS+","+this.GameTime+","+this.stage+","+this.GameIsFinished+")");

            //second table of DB
            String sql = "CREATE TABLE TABLE_OF_GAME_"+name+" (ID INTEGER, ";
            for (int j = 0; j < this.COLUMNS; j++) {
                sql = sql.concat("_"+String.valueOf(j)+" INTEGER,");
            }
            sql = sql.concat(" PRIMARY KEY ( ID ))");
            stmt.executeUpdate(sql);
            for(int i=0;i<this.ROWS;i++) {
                sql = "INSERT INTO TABLE_OF_GAME_"+name+" VALUES ("+String.valueOf(i)+",";
                for (int j = 0; j < this.COLUMNS; j++) {
                    sql=sql.concat(String.valueOf(table[i][j]));
                    if(j!=COLUMNS-1){
                        sql=sql.concat(",");
                    }
                }
                sql=sql.concat(")");
                stmt.executeUpdate(sql);
            }

            //third table of DB
            sql = "CREATE TABLE SHADOW_TABLE_OF_GAME_"+name+" (ID INTEGER, ";
            for (int j = 0; j < this.COLUMNS; j++) {
                sql=sql.concat("_"+String.valueOf(j)+" INTEGER,");
            }
            sql=sql.concat(" PRIMARY KEY ( ID ))");
            stmt.executeUpdate(sql);
            for(int i=0;i<this.ROWS;i++) {
                sql = "INSERT INTO SHADOW_TABLE_OF_GAME_"+name+" VALUES ("+String.valueOf(i)+",";
                for (int j = 0; j < this.COLUMNS; j++) {
                    sql=sql.concat(String.valueOf(shadowTable[i][j]));
                    if(j!=COLUMNS-1){
                        sql=sql.concat(",");
                    }
                }
                sql=sql.concat(")");
                stmt.executeUpdate(sql);
            }

            //4th table of DB
            stmt.executeUpdate("INSERT INTO PLAYER_TABLE " +
                    "VALUES ('"+name+"',"+this.player.Coordiantes.x+", "+this.player.Coordiantes.y+","+this.player.ImageStyle[0]+","+this.player.ImageStyle[1]+","+this.player.Health+","+this.player.Pts+","+this.player.BombLimit+","+this.player.BombRadii+","+this.player.ControlBombs+","+this.player.GhostMode+","+this.player.Speed+")");

            //5th table of DB
            for(int i=0;i<BombList.size();i++) {
                stmt.executeUpdate("INSERT INTO BOMBS_TABLE " +
                        "VALUES ('"+name+"',"+this.BombList.get(i).BOMB_COORDINATES.x+", "+this.BombList.get(i).BOMB_COORDINATES.y+","+this.BombList.get(i).CONTROLABLE+","+this.BombList.get(i).EXPLOSION_RADIUS+")");

            }

            //6th table of DB
            for(int i=0;i<EnemiesList.size();i++) {
                stmt.executeUpdate("INSERT INTO ENEMY_TABLE " +
                        "VALUES ('"+name+"',"+this.EnemiesList.get(i).EnemyType+", "+this.EnemiesList.get(i).Coordiantes.x+","+this.EnemiesList.get(i).Coordiantes.y+","+this.EnemiesList.get(i).ImageStyle[0]+","+this.EnemiesList.get(i).ImageStyle[1]+","+this.EnemiesList.get(i).Direction+","+this.EnemiesList.get(i).MoveProgress+")");

            }
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}