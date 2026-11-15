import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * Represents a shot that crosses the screen from bottom to up and then dismiss
 * @author Bernardo Copstein and Rafael Copstein
 */
public class Shot extends BasicElement{
    public Shot(int px,int py){
        super(px,py);
    }

    @Override
    public void start(){
        setDirV(-1);
        setSpeed(9);
    }

    
    @Override
    public boolean testCollision(Character other){
        // Do not check collision between two shots
        if (other instanceof Shot){
            return false;
        }else{
            return super.testCollision(other);
        }
    }

    @Override
    public void Update(long deltaTime){
        if (hasCollided()){
            deactivate();
        }else{
            setPosY(getY() + getDirV() * getSpeed());
            // If it reached the top of the screen ...
            if (getY() <= getLMinV() || getY() > getLMaxV()){
                // Disappear
                deactivate();
            }
        }
    }

    @Override
    public int getHeight(){
        return 16;
    }

    @Override
    public int getWidth(){
        return 8;
    }

    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Paint.valueOf("#00FF00"));
        graphicsContext.fillOval(getX(), getY(), 8, 16);
    }
}
 