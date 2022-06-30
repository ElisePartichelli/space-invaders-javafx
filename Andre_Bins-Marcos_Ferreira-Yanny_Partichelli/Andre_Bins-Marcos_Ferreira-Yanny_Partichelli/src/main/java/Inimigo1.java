import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.image.Image;

/**
 * @author 
 *         André Menezes Bins 21200695-1
 *         Marcos do Nascimento Ferreira 21200886-6
 *         Yanny Elise Partichelli 21200661-3 
 */
public class Inimigo1 extends Enemies {
    public Inimigo1(int px,int py){
        super(px,py);
        setLargAlt(65, 50);
        setVidas(1);
        setImage("Inimigo1.png");
        ptsConcedidos = 10;
    }

}