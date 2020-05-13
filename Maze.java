import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Maze extends Application {
  private final int x = 15;
  private final int y = 15;

  private Random random = new Random();
  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage primaryStage) throws Exception {
    mazeLogic(primaryStage);
  }

  public void mazeLogic(Stage stage) throws InterruptedException {
    VBox box = new VBox();
    int width = x, height = y;
    int[][] grid = new int[width][height];
    int[] entrance = {random.nextInt(2), random.nextInt(15)};
    int[] exit = {random.nextInt(2), random.nextInt(15)};

    recursiveDivision(grid, 0, 0);
    display(grid,entrance,exit);
    System.out.println(entrance[0] + " "+ entrance[1]);
    System.out.println(exit[0] +" "+ exit[1]);

    Scene scene = new Scene(box,500,500,Color.WHITESMOKE);
    stage.setTitle("Sequenced Maze Generation");
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

  public void display(int[][] grid, int[] entrance, int[] exit){
    for ( int i =0; i< y ; i++) {
      for (int j = 0; j < x; j++){
        if((grid[j][i] & 1) == 0 && entrance[0] == 0 && j == entrance[1] && i == 0){ 
          System.out.print("+   ");
          continue;
        }
        System.out.print((grid[j][i] & 1) == 0 ? "+---" : "+   ");
      }
      System.out.println("+");

      for(int j = 0; j<x; j++){
        
        if((grid[j][i] & 8) == 0 && entrance[0] == 1  && i == entrance[1]){ 
          System.out.print("    ");
          continue;
        }
        System.out.print((grid[j][i] & 8) == 0 ? "|   " : "    ");
      }
      if(exit[0] == 1 && i == exit[1]){
        System.out.println(" ");
      }
      else{
        System.out.println("|");
      }
    }
    for (int j = 0; j < x; j++) {
      if(exit[0] == 0 && exit[1] == j){
        System.out.print("+   ");
      }
      else{
      System.out.print("+---");
      }
		}
		System.out.println("+");
  }

}