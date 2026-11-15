import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.image.Image;

/**
 * @author 
 *          André Menezes Bins 21200695-1
 *         Marcos do Nascimento Ferreira 21200886-6
 *         Yanny Elise Partichelli 21200661-3 
 */
public class Enemy2 extends Enemies {
    public Enemy2(int px,int py){
        super(px,py);
        setLives(3);
        setImage("Enemy2.png");
        pointsAwarded = 50;
    }
}