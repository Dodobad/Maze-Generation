import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;

public class Maze extends Application {

  private Random random = new Random();
  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage primaryStage) throws Exception {
    mazeLogic(primaryStage);
  }

  public void mazeLogic(Stage stage){
    VBox box = new VBox();
    int width = 1000, height = 1000;
    int[][] grid = new int[1000][1000];

    recursiveDivision(grid, 0, 0, width, height, chooseDirection(width, height));


    Scene scene = new Scene(box,1000,1000,Color.WHITESMOKE);
    stage.setTitle("Sequenced Maze Generation");
    stage.setScene(scene);
    stage.show();
  }

  public void recursiveDivision(int[][] grid, int xPos, int yPos, int width, int height, boolean direct){
    if(width <= 2 || height <= 2) return;

    boolean horizontal = direct;

    int wx = xPos + (horizontal ? 0 : random.nextInt(width - 2));
    int wy = yPos + (horizontal ? random.nextInt(height - 2) : 0);
    int px = wx + (horizontal ? random.nextInt(width) : 0);
    int py = wy + (horizontal ? 0: random.nextInt(height));
    int dx = horizontal ? 1 : 0;
    int dy = horizontal ? 0 : 1;
    int length = horizontal ? width: height;
    int dir = horizontal ? 1 : 2;

    for (int i = 0; i < length; i++) {
      if(wx != px || wy != py) grid[wy][wx] |= dir;
      wx += dx;
      wy += dy;
    }

    int nx = xPos;
    int ny = yPos;
    int w = horizontal ? width: wx-xPos + 1;
    int h = horizontal ? wy - yPos + 1 : height;

    recursiveDivision(grid, nx, ny, w, h, chooseDirection(w, h));

    nx = horizontal ? xPos : wx + 1; 
    ny = horizontal ? wy + 1 : yPos;
    w = horizontal ? width : xPos + width - wx - 1;
    h = horizontal ? yPos + height - wy - 1 : height;

    recursiveDivision(grid, nx, ny, w, h, chooseDirection(w, h));
  }

  public boolean chooseDirection(double width, double height){
    if(width < height) return true;
    return false;
  }
}