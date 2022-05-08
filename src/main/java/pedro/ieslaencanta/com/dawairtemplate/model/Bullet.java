/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.enemigo.Enemy;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IMove;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author Manuel
 */
public class Bullet extends SpriteMove implements ICollision {

    protected Image img;
    //path para la imagen
    private static String pathurl = "bullets/bullet_rigth.png";

    public Bullet() {
        super();
        this.inc = 3;
        this.img = new Image(getClass().getResourceAsStream("/" + Bullet.pathurl));
    }

    public void move() {
        this.move(IMove.Direction.RIGHT);
    }

    public void setImg(String url) {
        this.img = new Image(getClass().getResourceAsStream("/" + url));
    }

    public void setInc(int inc) {
        this.inc = inc;
    }

    public void init(Coordenada p, Rectangle board) {
        super.init(6, new Size(36, 8), p, true, true, board);
    }

    public void TicTac() {
        this.move();

    }

    @Override
    public void draw(GraphicsContext gc) {

        gc.drawImage(img, 0, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                this.getPosicion().getX(), this.getPosicion().getY(),
                this.getSize().getWidth(), this.getSize().getHeight());

    }

    @Override
    public int getX() {
        return this.getPosicion().getX();
    }

    @Override
    public int getY() {
        return this.getPosicion().getY();
    }

    @Override
    public int getHeight() {
        return this.getSize().getHeight();
    }

    @Override
    public int getWidht() {
        return this.getSize().getWidth();
    }

    @Override
    public boolean isCollision(ICollision another) {
        return ICollision.super.isCollision(another);
    }

}
