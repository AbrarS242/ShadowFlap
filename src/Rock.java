import bagel.*;

public class Rock extends Weapon{

    private int SHOOTING_RANGE = 25;

    /**
     * Instantiate a new rock.
     *
     * @param weaponY The y-coordinate of the spawn point of the rock.
     */
    public Rock(int weaponY) {
        this.weaponImage = new Image("res/level-1/rock.png");
        this.weaponY = weaponY;
    }

    /**
     * Get the shooting range of the rock.
     *
     * @return the shooting range of the rock.
     */
    public int getShootingRange() {
        return SHOOTING_RANGE;
    }

}
