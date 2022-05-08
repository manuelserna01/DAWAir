/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.enemigo;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.enemigo.AEnemy;
import pedro.ieslaencanta.com.dawairtemplate.model.Bullet;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.FactoryBullet;
import pedro.ieslaencanta.com.dawairtemplate.model.FireBullet;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;

/**
 *
 * @author Manuel
 */
public class Enemy extends AEnemy implements ICollision {

    private Image img;
    //path para la imagen
    private static String pathurl = "enemigos/e1.png";
    //para la animación

    @Override
    public void init(Coordenada p, Rectangle board) {
        // Factorias de las balas
        FactoryBullet.addBullet("FireBullet", FireBullet::new);
        this.img = new Image(getClass().getResourceAsStream("/" + Enemy.pathurl));
        super.init(3, new Size(64, 30), p, true, true, board);
    }

    /**
     * dibujar, es algo más complejo al moverse las alas
     *
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, 0, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                this.getPosicion().getX(), this.getPosicion().getY(),
                this.getSize().getWidth(), this.getSize().getHeight());

        this.balas.forEach(b -> {
            b.draw(gc);
        });

    }

    @Override
    public void shoot() {
        /*
        TODO BALAS
         */

        Bullet tempo;
        int random = (int) (Math.random() * 200);

        if (random < 2) {
            tempo = FactoryBullet.create("LaserBullet");
            tempo.init(new Coordenada(this.getPosicion().getX(), this.getPosicion().getY() + 10), board);
            tempo.setInc(-6);
            this.balas.add(tempo);
        }

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

}
