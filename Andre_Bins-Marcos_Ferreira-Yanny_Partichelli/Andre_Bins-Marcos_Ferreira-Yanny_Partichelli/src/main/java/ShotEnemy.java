import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.image.Image;

/**
 * @author André Menezes Bins 21200695-1
 *         Marcos do Nascimento Ferreira 21200886-6
 *         Yanny Elise Partichelli 21200661-3 
 */
public class ShotEnemy extends Shot{
    public ShotEnemy(int px,int py){
        super(px,py);
    }

    @Override
    public void start(){
        setDirV(1);
        setSpeed(4);
    }

    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Paint.valueOf("#FF0000"));
        graphicsContext.fillOval(getX(), getY(), 8, 16);
    }

    @Override
    public boolean testaColisao(Character outro){
        // Não verifica colisão de um tiro com outro tiro
        if (outro instanceof Shot || outro instanceof Enemies){
            return false;
        }else{
            return super.testaColisao(outro);
        }
    }
    
}