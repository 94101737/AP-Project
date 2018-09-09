import java.awt.*;
import java.io.Serializable;

public class ClientVariables implements Serializable {
    public int ROWS;
    public int COLUMNS;
    public int PlayerIndex;;
    public Point BlockImageStyles[][];
    public Point BlockFireImageStyles[][];
    public Point BlockCoordinates[][];
    public boolean BlockIsFiring[][];
    public int NumberOfBombs;
    public Point BombCoordinates[];
    public int NumberOfPlayers;
    public String PlayerNames[];
    public Point PlayerImageStyles[];
    public Point PlayerCoordinates[];
    public double PlayerImageRatio[];
    public int PlayerHealth[];
    public int PlayerPts[];
    public boolean PlayerControlBombs[];
    public boolean PlayerGhostMode[];
    public int PlayerSpeed[];
    public int PlayerBombRadii[];
    public int PlayerBombLimit[];
    public int PlayerNumberOfBombedBoms[];
    public boolean PlayerIsFinished[];
    public int NumberOfEnemies;
    public Point EnemyImageStyles[];
    public Point EnemyCoordinates[];
    public int GameTime;
    public int stage;
}