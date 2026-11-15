import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.image.Image;


/**
 * @author André Menezes Bins 21200695-1
 *         Marcos do Nascimento Ferreira 21200886-6
 *         Yanny Elise Partichelli 21200661-3 
 */
    abstract class Enemies extends BasicElement {
    private Image image;
    private String imageSrc;
    private int lives;
    protected int pointsAwarded;

    public Enemies(int px,int py){
        super(px,py);
        setSize(50, 40);

        setSpeed(7 + Game.getInstance().getLevel() / 7);
    }

    protected void createImage() {
        try{
            image =  new Image(imageSrc,getWidth(),getHeight(),true,true);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(){
        createImage();
        setDirH(1);
    }


    @Override
    public void Update(long deltaTime){
        if (hasCollided()){
            if (lives > 1) {
                lives--;
                setCollided(false);
            } else {
                deactivate();
                return;
            }
        }
        setPosX(getX() + getDirH() * getSpeed());
        // If it reached the screen edge...
        if (getX() >= getLMaxH() - getWidth() || getX() < getLMinH()){
            reachedScreenEdge();
            
            // Place the enemy at the screen edge to keep it from leaving the screen
            if (getX() < getLMinH()) {
                setPosX(getLMinH());
            } else if (getX() >= getLMaxH() - getWidth()){
                setPosX(getLMaxH() - getWidth());
            }   
        }


        // if (getY() >= getLMaxV() - getHeight()) {
        //     Game.getInstance().loseLife();
        //     deactivate();
        // }
    }

    public void changeSpeed() {
        if(Game.getInstance().getLevel()>=5){
            setSpeed(Params.getInstance().nextInt(4)+2);
        }
    }

    public void reachedScreenEdge() {
        // Reverse direction
        setDirH(getDirH()*-1);

        changeSpeed();

        setPosY(getY() + 45);
      
    }

    
    public int getPointsAwarded() {
        return pointsAwarded;
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.drawImage(image, getX(),getY());
    }

    @Override
    public boolean testCollision(Character other){
        if (other instanceof Enemies || other instanceof ShotEnemy){
            return false;
        }else{
            boolean collided = super.testCollision(other);
            if (collided && other instanceof Cannon) {
                Game.getInstance().setFalseGameOver();
                // decreaseLives();
            } else if (collided && other instanceof Shot) {
                BasicElement el = (Shot) other;
                el.deactivate();
            }
            return collided;
        }
    }

    @Override
    public void deactivate(){
        Game.getInstance().addPoints(this.getPointsAwarded());
        super.deactivate();
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void decreaseLives() {
        this.lives = lives-1;
    }

    public int getLives() {
        return lives;
    }

    public void setImage(String img) {
        imageSrc = img;
    }
}
