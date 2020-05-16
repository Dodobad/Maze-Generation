import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Maze extends Application {
  private final int x = 10;
  private final int y = 10;
  private Random random = new Random();
  private int[] entrance = { random.nextInt(2), random.nextInt(x) };
  private int[] exit = { entrance[0], random.nextInt(y/2) };

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) throws Exception {
    mazeLogic(primaryStage);
  }

  public void mazeLogic(Stage stage) throws InterruptedException {

    int width = x, height = y;
    int[][] grid = new int[width][height];

    recursiveDivision(grid, 0, 0);
    Group displayMaze = display(grid, entrance, exit);
    Scene scene = new Scene(displayMaze,x*20+40,y*20+40,Color.WHITESMOKE);
    stage.setTitle("Maze");
    stage.setScene(scene);
    stage.show();
  }

  public void recursiveDivision(int[][] grid, int xPos, int yPos)
      throws InterruptedException {
      
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        
        for(DIR dir : dirs) {
          int nx = xPos + dir.dx;
          int ny = yPos + dir.dy;
          if(between(nx, x) && between(ny, y) && (grid[nx][ny] == 0)) {
            grid[xPos][yPos] |= dir.bit;
            grid[nx][ny] |= dir.opposite.bit;
            // display(grid, entrance, exit);
            recursiveDivision(grid, nx, ny);
          }
        }
  }

  private static boolean between(int v, int upper){
    return (v >= 0 ) && (v < upper);
  }

  private enum DIR {
    N(1, 0, -1), S(2, 0 , 1), E(4, 1, 0), W(8, -1, 0);
    private final int bit, dx, dy;
    private DIR opposite;

    static {
      N.opposite = S;
      S.opposite = N;
      E.opposite = W;
      W.opposite = E;
    }

    private DIR(int bit, int dx, int dy) {
      this.bit = bit;
      this.dx = dx;
      this.dy = dy;
    }
  };

  private Group display(int[][] grid, int[] entrance, int[] exit){
    Group textDisplay = new Group();
    Line[][] lines = new Line [x][y];
    double startY=10,endY=30, startX=10, endX =30;
    double entranceLine1=0,entranceLine2=0,exitLine1=0,exitLine2=0;
    for ( int i =0; i< y ; i++) {
      startX=10; 
      endX=30;
      for (int j = 0; j < x; j++){
        if((grid[j][i] & 1) == 0 && entrance[0] == 0 && j == entrance[1] && i == 0){ 
          startX+=20;   
          endX+=20;
          continue;
        }
        if((grid[j][i] & 1) == 0){
          lines[j][i] = new Line(startX, startY, endX, startY);
          textDisplay.getChildren().addAll(lines[j][i]);
        }
        startX+=20;
        endX+=20;
        
      }
      
      startX = 10;
      for(int j = 0; j<x; j++){
        
        if((grid[j][i] & 8) == 0 && entrance[0] == 1  && i == entrance[1]){ 
          continue;
        }
        if((grid[j][i] & 8) == 0){
        lines[j][i] = new Line(startX, startY, startX, endY);
        textDisplay.getChildren().addAll(lines[j][i]);
        }
        startX+=20;
      }
      if(exit[0] == 1 && i == exit[1]){
        entranceLine2 = 10+(20*i);
        exitLine2 = entranceLine2 + 20;
      }
     
      startY+=20;
      endY+=20;
    }
    for (int j = 0; j < x; j++) {
      if(exit[0] == 0 && exit[1] == j){
        entranceLine1 = 10+(20*j);
        exitLine1 = entranceLine1 + 20;
      }

		}

    
    if(entranceLine2 !=0){
      Line lineEast1 = new Line(endX-20, 10, endX-20, entranceLine2);
      Line lineEast2 = new Line(endX-20, exitLine2, endX-20, endY-20);
      Circle circleStart = new Circle(endX-20, entranceLine2+10, 5);
      circleStart.setFill(Color.RED);
      textDisplay.getChildren().addAll(lineEast1, lineEast2, circleStart);
    }
    else {
      Line lineEast = new Line(endX-20, 10, endX-20, endY-20);
      textDisplay.getChildren().addAll(lineEast);
    }

    if(entranceLine1 !=0){
      Line lineSouth1 = new Line(10,endY-20, entranceLine1, endY-20);
      Line lineSouth2 = new Line(exitLine1, endY-20, endX-20, endY-20);
      Circle circleStart = new Circle(entranceLine1+10,endY-20, 5);
      circleStart.setFill(Color.RED);
      textDisplay.getChildren().addAll(lineSouth1, lineSouth2, circleStart);
    }
    else{
      Line lineSouth = new Line(10, endY-20, endX-20, endY-20);
      textDisplay.getChildren().addAll(lineSouth);
    }
    return textDisplay;
  }

  

}