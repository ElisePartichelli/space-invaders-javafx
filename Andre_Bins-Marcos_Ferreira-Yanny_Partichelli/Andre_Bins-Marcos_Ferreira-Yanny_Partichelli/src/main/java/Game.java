import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Path;



/**
 * Handles the game lifecycle and behavior
 * @author Bernardo Copstein and Rafael Copstein
 * 
 * @changes André Menezes Bins 21200695-1
 *           Marcos do Nascimento Ferreira 21200886-6
 *           Yanny Elise Partichelli 21200661-3 
 */
public class Game {
    private static Game game = null;
    private Cannon cannon;
    private List<Character> activeChars;
    private int points;
    public int extraLifeCount;
    private int lives;
    private int level;
    private boolean gameOver;

    
    private Game(){
    }

    public static Game getInstance(){
        if (game == null){
            game = new Game();
        }
        return(game);
    }

    public void addChar(Character c){
        activeChars.add(c);
        c.start();
    }

    public void eliminate(Character c){
        activeChars.remove(c);
        checkLevelComplete();
    }

    public void Start() {
        activeChars = new LinkedList<>();
        points = 0;
        lives = 3;
        level = 0;
        extraLifeCount=1;
        gameOver=false;

        nextLevel();
    }

    public void checkExtraLife(){
        if(points/(1000*extraLifeCount)>=1){
            lives++;
            extraLifeCount++;
        }
    }

    public void Update(long currentTime, long deltaTime) {
        for(int i=0;i<activeChars.size();i++){
            Character current = activeChars.get(i);
            current.Update(deltaTime);
            for(int j =0; j<activeChars.size();j++){
                Character other = activeChars.get(j);
                if ( current != other){
                    current.testCollision(other);
                }
                checkExtraLife();
            }
        }
    }

    public void OnInput(KeyCode keyCode, boolean isPressed) {
        cannon.OnInput(keyCode, isPressed);
    }

    public void Draw(GraphicsContext graphicsContext) {

            drawPoints(graphicsContext);
            drawLifes(graphicsContext);
            drawLevel(graphicsContext);

        for(Character c:activeChars){
            c.Draw(graphicsContext);
        }
    }

    private void drawPoints(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Impact", 20));
        graphicsContext.setFill(Paint.valueOf("#FFD700"));
        graphicsContext.fillText("Points: " + getPoints(), 10, 20);
    }

    private void drawLifes(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Impact", 20));
        graphicsContext.setFill(Paint.valueOf("#FFD700"));
        graphicsContext.fillText("Lives: " + getLives(), 10, 40);
    }

    private void drawLevel(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Impact", 40));
        graphicsContext.setFill(Paint.valueOf("#FFD700"));
        graphicsContext.fillText("Level " + getLevel(), Params.WINDOW_WIDTH/2 -80, hasLost()? 80: 40);
    }

    private void checkLevelComplete() {
        if (activeChars.stream().filter(c -> c instanceof Enemies).count() == 0) {
            nextLevel();
        }
    }

    public void nextLevel() {
        if (hasLost()) {
            return;
        }

        level++;
        
        activeChars.clear();

        // Add the cannon
        if(level<4){
        cannon = new Cannon(400,550);
        activeChars.add(cannon);
        }else{
            cannon=new JavaCannon(400, 550);
            activeChars.add(cannon);
        }

        createEnemies();

        for(Character c:activeChars){
            c.start();
        }
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        lives--;
        if (hasLost()) {
            activeChars.clear();            
        }
    }

    public boolean hasLost() {
        if(lives <= 0){
            setFalseGameOver();
            return true;
        }
            return false;
    }

    public void setFalseGameOver(){
        gameOver=true;
    }

    public boolean getGameOver(){
        return gameOver;
    }


    public Integer getLevel() {
        return level;
    }

    private void createEnemies() {
        if (getLevel() == 1) level1();
        if (getLevel() == 2) level2();
        if (getLevel() == 3) level3();
        if (getLevel() == 4) level4();
        if (level > 4){
            int n=Params.getInstance().nextInt(2);
            if(n==0){
                level3();
            }else if(n==1){
                level4();
            }
        }
    }

    private void level1() {
        activeChars.add(new Enemy1(0, 50));
        activeChars.add(new Enemy1(200,50));
        activeChars.add(new Enemy1(400,50));
        activeChars.add(new Enemy1(600,50));
        activeChars.add(new Enemy1(100,50));
    }

    private void level2() {
        level1();
        activeChars.add(new Enemy2(0,150));
        activeChars.add(new Enemy2(200,150));
        activeChars.add(new Enemy2(400,150));
        activeChars.add(new Enemy2(600,150));
        activeChars.add(new Enemy2(100,150));
    }

    private void level3() {
        activeChars.add(new Enemy2(0, 200));
        activeChars.add(new Enemy2(300, 200));
        activeChars.add(new Enemy2(200,200));
        activeChars.add(new Enemy1(400,200));
        activeChars.add(new Enemy1(600,200));
        activeChars.add(new Enemy1(100,200));
        activeChars.add(new Enemy3(5,50));
        activeChars.add(new Enemy3(200,50));
        activeChars.add(new Enemy3(400,50));
        activeChars.add(new Enemy3(100,50));
        activeChars.add(new Enemy3(300,50));

       

    }


    private void level4() {
        activeChars.add(new Enemy4(200,200));
        activeChars.add(new Enemy4(20,200));
        activeChars.add(new Enemy4(600,50));
        activeChars.add(new Enemy4(400,50));
        activeChars.add(new Enemy4(100,50));
        activeChars.add(new Enemy4(300,50));

    }

    public  static void writeRank(String str){ 
        ArrayList<String> list=readRank();
        int pos=0;
        boolean found=false;

        if(list.size()==0){
            list.add(str);
        }else{
        while(pos<list.size() && found==false){
            int pts1= Integer.parseInt(str.substring(str.indexOf(": ")+2, str.length() ));
            int pts2= Integer.parseInt(list.get(pos).substring(list.get(pos).indexOf(": ")+2,list.get(pos).length() ));
            if(pts1>pts2){
                list.add(pos,str);
                found=true;
            }
            pos++;
        }
        if(pos<10 && found==false){
            list.add(str);
        }
    }
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"/"+"ranking.txt";
        Path path = Paths.get(nameComplete);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))){
          for(int i=0;i<list.size();i++){
                String line = list.get(i);
                writer.println(line);
            }
        }catch (IOException x){
          System.err.format("I/O error: %s%n", x);
      }
    }

    public static ArrayList<String> readRank(){ 
     ArrayList<String> rankList= new ArrayList<>(10);
    
     String currDir = Paths.get("").toAbsolutePath().toString();
     // Note: on Windows use "\\"
     String nameComplete = currDir+"/"+"ranking.txt";
     Path path = Paths.get(nameComplete);
     int count=0;
     try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))){
        while (sc.hasNext() && count<10){
            String line = sc.nextLine();
           // System.out.println(line);
            rankList.add(line);
            count++;
        }
     }catch (IOException x){
         System.err.format("I/O error: %s%n", x);
     }
     //System.out.println(rankList);
     return rankList;
    }


}
