import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.image.Image;

/**
 * @author 
 *         André Menezes Bins 21200695-1
 *         Marcos do Nascimento Ferreira 21200886-6
 *         Yanny Elise Partichelli 21200661-3 
 */
public class Enemy1 extends Enemies {
    public Enemy1(int px,int py){
        super(px,py);
        setSize(65, 50);
        setLives(1);
        setImage("Enemy1.png");
        pointsAwarded = 10;
    }

}