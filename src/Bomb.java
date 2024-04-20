import bagel.*;
import bagel.util.Rectangle;

public class Bomb extends Weapon{
    private int SHOOTING_RANGE = 50;

    /**
     * Instantiate a new bomb.
     *
     * @param weaponY The y-coordinate of the spawn point of the bomb.
     */
    public Bomb(int weaponY) {
        this.weaponImage = new Image("res/level-1/bomb.png");
        this.weaponY = weaponY;
    }

    /**
     * Get the shooting range of the bomb.
     *
     * @return the shooting range of the bomb.
     */
    public int getShootingRange() {
        return SHOOTING_RANGE;
    }

}