import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Maze extends Application {
  
  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage primaryStage) throws Exception {
    mazeLogic(primaryStage);
  }

  public void mazeLogic(Stage stage){
    VBox box = new VBox();

    recursiveDivision();


    Scene scene = new Scene(box,1000,1000,Color.WHITESMOKE);
    stage.setTitle("Sequenced Maze Generation");
    stage.setScene(scene);
    stage.show();
  }

  public void recursiveDivision(){
    
  }
}