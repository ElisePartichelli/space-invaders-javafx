import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

/**
 * Represents the Java Gun 
 * 
 * @author André Menezes Bins 21200695-1
 *         Marcos do Nascimento Ferreira 21200886-6
 *         Yanny Elise Partichelli 21200661-3 
 */
public class CanhaoJava extends Canhao {

    public CanhaoJava(int px,int py){
        super(px,py);
        setSpeed(7);
        
        try{
            setImage(new Image( "javaa.png",0,40,true,true ));
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void OnInput(KeyCode keyCode, boolean isPressed) {
        if (keyCode == KeyCode.LEFT){
            int dh = isPressed ? -1 : 0;
            setDirH(dh);
        }
        if (keyCode == KeyCode.RIGHT){
            int dh = isPressed ? 1 : 0;
            setDirH(dh);
        }
        if (keyCode == KeyCode.SPACE){
            if (shot_timer <= 0) {
                Game.getInstance().addChar(new Shot(getX()+28,getY()-32));
                Game.getInstance().addChar(new Shot(getX()+4,getY()-32));
                shot_timer = RELOAD_TIME;
            }

        }
    }
}


