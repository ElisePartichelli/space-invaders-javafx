import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Handles window initialization and primary game setup
 * @author Bernardo Copstein, Rafael Copstein
 * @changes André Menezes Bins 21200695-1
 *           Marcos do Nascimento Ferreira 21200886-6
 *           Yanny Elise Partichelli 21200661-3 
 */

public class Main extends Application {
    TextField name;
    Stage stageMenu;
    Stage stageRanking;
    Stage stageGame;

    @Override
    public void start(Stage stage) throws Exception {
      Menu();

    }

    public void StartGame(){
         stageGame=new Stage();
         // Initialize Window
         stageGame.setTitle(Params.WINDOW_TITLE);
         stageGame.setResizable(false);
 
 
         Group root = new Group();
         Scene scene = new Scene( root );
         stageGame.setScene( scene );
 
         Canvas canvas = new Canvas(Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT );
         root.getChildren().add( canvas );
         scene.setFill(Color.BLACK);
 
         // Setup Game object
         Game.getInstance().Start();
 
         // Register User Input Handler
         scene.setOnKeyPressed((KeyEvent event) -> {
             Game.getInstance().OnInput(event.getCode(), true);
         });
 
         scene.setOnKeyReleased((KeyEvent event) -> {
             Game.getInstance().OnInput(event.getCode(), false);
         });
 
         // Register Game Loop
         final GraphicsContext gc = canvas.getGraphicsContext2D();
         gc.setFont(new Font("Arial", 20));

 
         new AnimationTimer()
         {
             long lastNanoTime = System.nanoTime();
 
             @Override
             public void handle(long currentNanoTime)
             {
                 long deltaTime = currentNanoTime - lastNanoTime;
 
                 Game.getInstance().Update(currentNanoTime, deltaTime);
                 gc.clearRect(0, 0, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
                 Game.getInstance().Draw(gc);
 
                 lastNanoTime = currentNanoTime;

                 if(Game.getInstance().getGameOver()){
                   Game.writeRank(name.getText()+": "+Game.getInstance().getPoints());
                   StartRanking();
                   stageGame.close();
                   stop(); 
                }
             }
 
         }.start();

         // Show window
         stageGame.show();
        
    }

    public void Menu(){
        stageMenu = new Stage();
        
        GridPane grid = new GridPane();
         grid.setAlignment(Pos.CENTER);
         grid.setHgap(50);
         grid.setVgap(50);
         grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("SPACE INVADERS");
        scenetitle.setFill(Paint.valueOf("#FFD700"));
        scenetitle.setId("menu-text");
        scenetitle.setFont(Font.font("Impact", FontWeight.NORMAL, 50));
        grid.add(scenetitle, 0,1,2,1);

        Label userName = new Label("Name:");
        userName.setTextFill(Paint.valueOf("#FFD700"));
        userName.setFont(Font.font("Impact",FontWeight.NORMAL,20));
        grid.add(userName, 0, 2);
         name = new TextField();
        grid.add(name, 1, 2);

        Button btnPlay = new Button("Play");
        HBox hbBtnPlay = new HBox(10);
        
        hbBtnPlay.setAlignment(Pos.CENTER_LEFT);
        hbBtnPlay.getChildren().add(btnPlay);
        grid.add(hbBtnPlay, 1, 3);
        btnPlay.setOnAction(e->this.handlePlayButton(e));
        btnPlay.setFont(Font.font("Impact", FontWeight.NORMAL, 17));
        


         Button btnRanking = new Button("Ranking");
         HBox hbBtnRanking = new HBox(10);
         hbBtnRanking.setAlignment(Pos.CENTER_LEFT);
         hbBtnRanking.getChildren().add(btnRanking);
         grid.add(hbBtnRanking, 1,4);
         btnRanking.setOnAction(e->this.handleRankingButton(e));
         btnRanking.setFont(Font.font("Impact", FontWeight.NORMAL, 17));


        Button btnExit = new Button("Exit");
        HBox hbBtnExit = new HBox(10);
        hbBtnExit.setAlignment(Pos.CENTER_LEFT);
        hbBtnExit.getChildren().add(btnExit);
        grid.add(hbBtnExit, 1, 5);
        btnExit.setOnAction(e->this.handleExitButton(e));
         btnExit.setFont(Font.font("Impact", FontWeight.NORMAL, 17));

        Image img = new Image("background.jpg",800,600,false,true);
        BackgroundImage bImg = new BackgroundImage(img,
                                                   BackgroundRepeat.NO_REPEAT,
                                                   BackgroundRepeat.NO_REPEAT,
                                                   BackgroundPosition.DEFAULT,
                                                   BackgroundSize.DEFAULT);

        Background bGround = new Background(bImg);
        grid.setBackground(bGround);

        Scene sceneMenu = new Scene(grid,800,600);
        stageMenu.setScene(sceneMenu);
        stageMenu.show();
    }

    
    public void handlePlayButton(ActionEvent e) {
        stageMenu.close();
        StartGame();
    }

    public void handleRankingButton(ActionEvent e) {
        stageMenu.close();
        StartRanking();
    }


    public void handleExitButton(ActionEvent e){
        stageMenu.close();
    }

    public void handleMenuButton(ActionEvent e){
        stageRanking.close();
        Menu();
    }

    public void StartRanking(){
        stageRanking = new Stage();
        ArrayList<String> list=Game.readRank();


        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Ranking");
        scenetitle.setFill(Paint.valueOf("#FFD700"));
        scenetitle.setFont(Font.font("Impact", FontWeight.NORMAL, 40));
        grid.add(scenetitle, 0, 0, 2, 1);

        int count=2;
        for(String st:list){
            Text a = new Text(st);
            a.setFill(Paint.valueOf("#F8F8FF"));
            a.setFont(Font.font("Impact", FontWeight.NORMAL, 20));

            grid.add(a,0,count,2,1);
            count++;
        }

        Button btnMenu = new Button("Back to menu");
        HBox hbBtnMenu = new HBox(20);
        hbBtnMenu.setAlignment(Pos.BASELINE_LEFT);
        hbBtnMenu.getChildren().add(btnMenu);
        grid.add(hbBtnMenu, 0, 14);
        btnMenu.setOnAction(e->this.handleMenuButton(e));
        btnMenu.setFont(Font.font("Impact", FontWeight.NORMAL, 17));

        
        Image img = new Image("background.jpg",800,600,false,true);
        BackgroundImage bImg = new BackgroundImage(img,
                                                   BackgroundRepeat.NO_REPEAT,
                                                   BackgroundRepeat.NO_REPEAT,
                                                   BackgroundPosition.DEFAULT,
                                                   BackgroundSize.DEFAULT);

        Background bGround = new Background(bImg);
        grid.setBackground(bGround);

        Scene sceneRanking = new Scene(grid,800,600);
        stageRanking.setScene(sceneRanking);
        stageRanking.show();
    }


    public static void main(String args[]) {
        launch();
    }
}

