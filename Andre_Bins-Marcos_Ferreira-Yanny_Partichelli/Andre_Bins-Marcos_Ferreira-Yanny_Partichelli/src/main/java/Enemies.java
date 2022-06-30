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
    private int vidas;
    protected int ptsConcedidos;

    public Enemies(int px,int py){
        super(px,py);
        setLargAlt(50, 40);

        setSpeed(7 + Game.getInstance().getFase() / 7);
    }

    protected void createImage() {
        try{
            image =  new Image(imageSrc,getLargura(),getAltura(),true,true);
            
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
        if (jaColidiu()){
            if (vidas > 1) {
                vidas--;
                setColidiu(false);
            } else {
                deactivate();
                return;
            }
        }
        setPosX(getX() + getDirH() * getSpeed());
        // Se chegou na borda da tela...
        if (getX() >= getLMaxH() - getLargura() || getX() < getLMinH()){
            fimDaTela();
            
            // Posiciona inimigo na borda da tela para impedir que saia da tela
            if (getX() < getLMinH()) {
                setPosX(getLMinH());
            } else if (getX() >= getLMaxH() - getLargura()){
                setPosX(getLMaxH() - getLargura());
            }   
        }


        // if (getY() >= getLMaxV() - getAltura()) {
        //     Game.getInstance().loseLife();
        //     deactivate();
        // }
    }

    public void mudaVelocidade() {
        if(Game.getInstance().getFase()>=5){
            setSpeed(Params.getInstance().nextInt(4)+2);
        }
    }

    public void fimDaTela() {
        // Inverte a direção
        setDirH(getDirH()*-1);

        mudaVelocidade();

        setPosY(getY() + 45);
      
    }

    
    public int getPointsConcedidos() {
        return ptsConcedidos;
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.drawImage(image, getX(),getY());
    }

    @Override
    public boolean testaColisao(Character outro){
        if (outro instanceof Enemies || outro instanceof ShotEnemy){
            return false;
        }else{
            boolean colidiu = super.testaColisao(outro);
            if (colidiu && outro instanceof Canhao) {
                Game.getInstance().setFalseGameOver();
                // diminuiVidas();
            } else if (colidiu && outro instanceof Shot) {
                BasicElement el = (Shot) outro;
                el.deactivate();
            }
            return colidiu;
        }
    }

    @Override
    public void deactivate(){
        Game.getInstance().incPontos(this.getPointsConcedidos());
        super.deactivate();
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void diminuiVidas() {
        this.vidas = vidas-1;
    }

    public int getVidas() {
        return vidas;
    }

    public void setImage(String img) {
        imageSrc = img;
    }
}
