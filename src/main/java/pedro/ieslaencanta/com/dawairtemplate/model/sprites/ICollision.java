package pedro.ieslaencanta.com.dawairtemplate.model.sprites;

/**
 *
 * @author pedro
 */
public interface ICollision {

    public enum State {
        COLLISION,
        DEAD,
        FREE
    };

    public int getX();

    public int getY();

    public int getHeight();

    public int getWidht();

    public default boolean isCollision(ICollision another) {
        boolean colision = false;

        //si el punto x mas a la izquerida esta en el margen
        if (another.getX() > this.getX() && another.getX() < this.getX() + this.getWidht()
                || //o si el punto mas a la derecha esta en el margen
                another.getX() + another.getWidht() > this.getX() && another.getX() + another.getWidht() < this.getX() + this.getWidht()) {
            if (another.getY() > this.getY() && another.getY() < this.getY() + this.getHeight()
                    || //o si el punto mas a la derecha esta en el margen
                    another.getY() + another.getHeight() > this.getY() && another.getY() + another.getHeight() < this.getY() + this.getHeight()) {

                colision = true;
            }
        }
        return colision;
    }

}
