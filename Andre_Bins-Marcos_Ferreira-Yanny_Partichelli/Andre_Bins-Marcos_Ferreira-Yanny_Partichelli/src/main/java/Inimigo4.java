import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.image.Image;


/**
 * @author 
 *         André Menezes Bins 21200695-1
 *         Marcos do Nascimento Ferreira 21200886-6
 *         Yanny Elise Partichelli 21200661-3 
 */

public class Inimigo4 extends Enemies {
    protected double RELOAD_TIME ; 
    protected double shot_timer = 0;
    
    public Inimigo4(int px,int py){
        super(px,py);
        setLargAlt(55, 55);
        setVidas(3);
        setImage("py.png");
        ptsConcedidos = 100;
        reloadAleatorio();
    }

    @Override
    public void Update(long deltaTime){
        super.Update(deltaTime);

                if (shot_timer > 0){
                    shot_timer -= deltaTime;
                } else {
                    Game.getInstance().addChar(new ShotEnemy(getX()+5,getY()+32));
                    Game.getInstance().addChar(new ShotEnemy(getX()+27,getY()+32));
                    shot_timer = RELOAD_TIME;
                }
    }

    public void reloadAleatorio() {
        RELOAD_TIME = 1500000000  - (Game.getInstance().getFase() * 0.13) * 1000000000;

        if (RELOAD_TIME < 300000000) {
            RELOAD_TIME = 300000000;
            System.out.println("Velocidade de tiro minimo alcando. fase: " + Game.getInstance().getFase());
        }
    }
}