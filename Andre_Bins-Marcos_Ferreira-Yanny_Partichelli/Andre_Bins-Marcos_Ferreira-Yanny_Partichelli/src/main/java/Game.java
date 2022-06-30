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
 * @Mudancas André Menezes Bins 21200695-1
 *           Marcos do Nascimento Ferreira 21200886-6
 *           Yanny Elise Partichelli 21200661-3 
 */
public class Game {
    private static Game game = null;
    private Canhao canhao;
    private List<Character> activeChars;
    private int pontos;
    public int aumentoVida;
    private int vidas;
    private int fase;
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
        faseAcabou();
    }

    public void Start() {
        activeChars = new LinkedList<>();
        pontos = 0;
        vidas = 3;
        fase = 0;
        aumentoVida=1;
        gameOver=false;

        proxFase();
    }

    public void aumentarVida(){
        if(pontos/(1000*aumentoVida)>=1){
            vidas++;
            aumentoVida++;
        }
    }

    public void Update(long currentTime, long deltaTime) {
        for(int i=0;i<activeChars.size();i++){
            Character este = activeChars.get(i);
            este.Update(deltaTime);
            for(int j =0; j<activeChars.size();j++){
                Character outro = activeChars.get(j);
                if ( este != outro){
                    este.testaColisao(outro);
                }
                aumentarVida();
            }
        }
    }

    public void OnInput(KeyCode keyCode, boolean isPressed) {
        canhao.OnInput(keyCode, isPressed);
    }

    public void Draw(GraphicsContext graphicsContext) {

            drawPoints(graphicsContext);
            drawLifes(graphicsContext);
            drawFase(graphicsContext);

        for(Character c:activeChars){
            c.Draw(graphicsContext);
        }
    }

    private void drawPoints(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Impact", 20));
        graphicsContext.setFill(Paint.valueOf("#FFD700"));
        graphicsContext.fillText("Pontos: " + getPontos(), 10, 20);
    }

    private void drawLifes(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Impact", 20));
        graphicsContext.setFill(Paint.valueOf("#FFD700"));
        graphicsContext.fillText("Vidas: " + getVidas(), 10, 40);
    }

    private void drawFase(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Impact", 40));
        graphicsContext.setFill(Paint.valueOf("#FFD700"));
        graphicsContext.fillText("Fase " + getFase(), Params.WINDOW_WIDTH/2 -80, perdeu()? 80: 40);
    }

    private void faseAcabou() {
        if (activeChars.stream().filter(c -> c instanceof Enemies).count() == 0) {
            proxFase();
        }
    }

    public void proxFase() {
        if (perdeu()) {
            return;
        }

        fase++;
        
        activeChars.clear();

        // Adiciona o canhao
        if(fase<4){
        canhao = new Canhao(400,550);
        activeChars.add(canhao);
        }else{
            canhao=new CanhaoJava(400, 550);
            activeChars.add(canhao);
        }

        criaInimigos();

        for(Character c:activeChars){
            c.start();
        }
    }

    public int getPontos() {
        return pontos;
    }

    public void incPontos(int pontos) {
        this.pontos += pontos;
    }

    public int getVidas() {
        return vidas;
    }

    public void loseLife() {
        vidas--;
        if (perdeu()) {
            activeChars.clear();            
        }
    }

    public boolean perdeu() {
        if(vidas <= 0){
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


    public Integer getFase() {
        return fase;
    }

    private void criaInimigos() {
        if (getFase() == 1) fase1();
        if (getFase() == 2) fase2();
        if (getFase() == 3) fase3();
        if (getFase() == 4) fase4();
        if (fase > 4){
            int n=Params.getInstance().nextInt(2);
            if(n==0){
                fase3();
            }else if(n==1){
                fase4();
            }
        }
    }

    private void fase1() {
        activeChars.add(new Inimigo1(0, 50));
        activeChars.add(new Inimigo1(200,50));
        activeChars.add(new Inimigo1(400,50));
        activeChars.add(new Inimigo1(600,50));
        activeChars.add(new Inimigo1(100,50));
    }

    private void fase2() {
        fase1();
        activeChars.add(new Inimigo2(0,150));
        activeChars.add(new Inimigo2(200,150));
        activeChars.add(new Inimigo2(400,150));
        activeChars.add(new Inimigo2(600,150));
        activeChars.add(new Inimigo2(100,150));
    }

    private void fase3() {
        activeChars.add(new Inimigo2(0, 200));
        activeChars.add(new Inimigo2(300, 200));
        activeChars.add(new Inimigo2(200,200));
        activeChars.add(new Inimigo1(400,200));
        activeChars.add(new Inimigo1(600,200));
        activeChars.add(new Inimigo1(100,200));
        activeChars.add(new Inimigo3(5,50));
        activeChars.add(new Inimigo3(200,50));
        activeChars.add(new Inimigo3(400,50));
        activeChars.add(new Inimigo3(100,50));
        activeChars.add(new Inimigo3(300,50));

       

    }


    private void fase4() {
        activeChars.add(new Inimigo4(200,200));
        activeChars.add(new Inimigo4(20,200));
        activeChars.add(new Inimigo4(600,50));
        activeChars.add(new Inimigo4(400,50));
        activeChars.add(new Inimigo4(100,50));
        activeChars.add(new Inimigo4(300,50));

    }

    public  static void escreveRank(String str){ 
        ArrayList<String> list=leRank();
        int pos=0;
        boolean achou=false;

        if(list.size()==0){
            list.add(str);
        }else{
        while(pos<list.size() && achou==false){
            int pts1= Integer.parseInt(str.substring(str.indexOf(": ")+2, str.length() ));
            int pts2= Integer.parseInt(list.get(pos).substring(list.get(pos).indexOf(": ")+2,list.get(pos).length() ));
            if(pts1>pts2){
                list.add(pos,str);
                achou=true;
            }
            pos++;
        }
        if(pos<10 && achou==false){
            list.add(str);
        }
    }
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"/"+"ranking.txt";
        Path path = Paths.get(nameComplete);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))){
          for(int i=0;i<list.size();i++){
                String linha = list.get(i);
                writer.println(linha);
            }
        }catch (IOException x){
          System.err.format("Erro de E/S: %s%n", x);
      }
    }

    public static ArrayList<String> leRank(){ 
     ArrayList<String> listaRank= new ArrayList<>(10);
    
     String currDir = Paths.get("").toAbsolutePath().toString();
     // Atenção: para windows use "\\"
     String nameComplete = currDir+"/"+"ranking.txt";
     Path path = Paths.get(nameComplete);
     int cont=0;
     try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))){
        while (sc.hasNext() && cont<10){
            String linha = sc.nextLine();
           // System.out.println(linha);
            listaRank.add(linha);
            cont++;
        }
     }catch (IOException x){
         System.err.format("Erro de E/S: %s%n", x);
     }
     //System.out.println(listaRank);
     return listaRank;
    }


}
