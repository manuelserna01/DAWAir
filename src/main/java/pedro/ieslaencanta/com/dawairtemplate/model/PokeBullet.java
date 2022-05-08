/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author Manuel
 */
public class PokeBullet extends Bullet {

    private static String pathurl = "bullets/pokeball.png";

    public PokeBullet() {
        super();
        this.img = new Image(getClass().getResourceAsStream("/" + PokeBullet.pathurl));
    }

    @Override
    public void init(Coordenada p, Rectangle board) {
        super.init(6, new Size(14, 14), p, true, true, board);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, 0, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                this.getPosicion().getX(), this.getPosicion().getY(),
                this.getSize().getWidth(), this.getSize().getHeight());
    }

}