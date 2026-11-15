import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.image.Image;

/**
 * @author 
 *         André Menezes Bins 21200695-1
 *         Marcos do Nascimento Ferreira 21200886-6
 *         Yanny Elise Partichelli 21200661-3 
 */         
public class Enemy3 extends Enemies {
    protected double RELOAD_TIME ; 
    protected double shot_timer = 0;

    public Enemy3(int px,int py){
        super(px,py);
        setSize(65, 50);
        setLives(1);
        setImage("enemy3.png");
        pointsAwarded = 70;
        updateReloadTime();
    }

    @Override
    public void Update(long deltaTime){
        super.Update(deltaTime);

        if (shot_timer > 0){
            shot_timer -= deltaTime;
        } else {
            Game.getInstance().addChar(new ShotEnemy(getX()+16,getY()+32));
            
            shot_timer = RELOAD_TIME;
        }
    }

    public void updateReloadTime() {
        RELOAD_TIME = 1500000000  - (Game.getInstance().getLevel() * 0.13) * 1000000000;

        if (RELOAD_TIME < 300000000) {
            RELOAD_TIME = 300000000;
            System.out.println("Minimum shot reload time reached. level: " + Game.getInstance().getLevel());
        }
    }
}