
package pedro.ieslaencanta.com.dawairtemplate.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 *
 * @author Pedro
 */
public class Player {
    private int lifes=3;
    private long score;
    private Font font;//, Font.ITALIC, 50);
    public Player(){
     
    }

   

    /**
     * @return the lifes
     */
    public int getLifes() {
        return lifes;
    }
    public void incScore(){
        this.score++;
    }
    /**
     * @return the score
     */
    public long getScore() {
        return score;
    }

    /**
     * @param lifes the lifes to set
     */
    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    /**
     * @param score the score to set
     */
    public void setScore(long score) {
        this.score = score;
    }
    
}
