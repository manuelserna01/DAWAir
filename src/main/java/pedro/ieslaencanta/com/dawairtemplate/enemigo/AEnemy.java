/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.enemigo;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.Bullet;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author Manuel
 */
public abstract class AEnemy extends SpriteMove implements IEnemy, IWarnClock, ICollision {

    protected ArrayList<Bullet> balas;

    public ArrayList<Bullet> getBullets() {
        return this.balas;
    }

    public AEnemy() {
        this.balas = new ArrayList<>();
    }

    public abstract void init(Coordenada p, Rectangle board);

    public Rectangle getBoard() {
        return board;
    }

    //movimiento del avión
    private void move() {
        this.move(Direction.LEFT);
    }
    public void draw(GraphicsContext gc){
         gc.fillText("X"+this.getPosicion().getX()+" Y:"+this.getPosicion().getY()+
                 " W:"+this.getSize().getWidth()+" H:"+this.getSize().getHeight(), 
                 this.getPosicion().getX(),
                 this.getPosicion().getY());
         
    }
    /**
     * cada vez que se recibe un tictac se mueve, faltan las balas del fighter
     */
    @Override
    public void TicTac() {
        this.move();
        //mover las balas 
        //this.shoot();
        /*this.balas.forEach(e -> {
            e.TicTac();
        });*/
    }

    @Override
    public boolean isCollision(ICollision another) {
        return ICollision.super.isCollision(another);
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.balas = bullets;
    }

}
