import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

/**
 * Represents the game Gun
 * @author Bernardo Copstein, Rafael Copstein
 */
public class Canhao extends BasicElement implements KeyboardCtrl{
    protected int RELOAD_TIME = 250000000; // Time is in nanoseconds
    protected int shot_timer = 0;
    protected Image image;
    

    public Canhao(int px,int py){
        super(px,py);
        setSpeed(7);
        try{
            image =  new Image( "nave1.png",0,40,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start() {
        setLimH(-16,Params.WINDOW_WIDTH-21);
        setLimV(Params.WINDOW_HEIGHT-100,Params.WINDOW_HEIGHT);
    }

    @Override
    public void Update(long deltaTime) {
        if (jaColidiu()) {
            Game.getInstance().loseLife();
            setColidiu(false);
        }

        int newPos = getX() + getDirH() * getSpeed();
        if (newPos > getLMaxH() ) {
            setPosX(getLMaxH());
        } else if(newPos < getLMinH()) {
            setPosX(getLMinH());
        } else {
            setPosX(newPos);
        }

        if (shot_timer > 0) shot_timer -= deltaTime;
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
                Game.getInstance().addChar(new Shot(getX()+16,getY()-32));
                
                shot_timer = RELOAD_TIME;
            }
        }
    }

    @Override
    public int getAltura(){
        return 80;
    }

    @Override
    public int getLargura(){
        return 32;
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, getX(),getY());
    }

    public void setImage(Image img){
        image=img;
    }
}
