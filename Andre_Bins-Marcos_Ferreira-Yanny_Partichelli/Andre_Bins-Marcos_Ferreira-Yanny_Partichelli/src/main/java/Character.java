import javafx.scene.canvas.GraphicsContext;

/**
 * Represents the basic game character
 * @author Bernardo Copstein and Rafael Copstein
 */
public interface Character {
    int getX();
    int getY();
    int getHeight();
    int getWidth();

    boolean testCollision(Character c);
    boolean hasCollided();
    void setCollided();
    void setCollided(boolean collided);

    void start();
    boolean isActive();
    void Update(long deltaTime);
    void Draw(GraphicsContext graphicsContext);
}
