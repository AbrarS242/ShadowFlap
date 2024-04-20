import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Weapon {

    protected final double SPEED_CHANGE_MULTIPLIER = 1.5;
    protected int INITIAL_MOVE_SPEED = 3;
    protected int SHOOT_SPEED = 5;
    protected Image weaponImage;
    protected double currentMoveSpeed;
    protected int shotFromFrame;
    protected double weaponX = Window.getWidth();
    protected double weaponY;
    protected boolean continueRendering = true;
    protected boolean pickedUp = false;
    protected boolean beingShot = false;

    /**
     * Get the shooting range of the weapon.
     *
     * @return the shooting range of the weapon.
     */
    public abstract int getShootingRange();

    /**
     * Display the weapon on screen.
     */
    public void renderWeapon(){
        // Display the weapon on screen
        weaponImage.draw(weaponX, weaponY);
    }

    /**
     * Move the weapon.
     *
     * @param timescale The current timescale of the game.
     * @param birdRightX The x-coordinate of the right side of the bird.
     * @param birdY The y-coordinate of the centre of the bird.
     */
    public void move(int timescale, double birdRightX, double birdY) {

        if (!pickedUp && !beingShot){

            // Weapon movement when initially spawned
            currentMoveSpeed = INITIAL_MOVE_SPEED * (Math.pow(SPEED_CHANGE_MULTIPLIER, (timescale - 1)));
            weaponX -= currentMoveSpeed;

            // Stop rendering if the weapon leaves the left border of the window
            if (getRectangle().right() < 0){
                continueRendering =  false;
            }

        } else if (pickedUp && !beingShot){
            // Weapon movement when picked up by the bird
            weaponX = birdRightX;
            weaponY = birdY;
        } else if (beingShot){
            // Weapon movement when shot
            weaponX += SHOOT_SPEED;
            shotFromFrame ++;
        }

    }

    /**
     * Get the rectangle around the image of the weapon.
     *
     * @return the rectangle around the image of the weapon.
     */
    public Rectangle getRectangle() {
        return weaponImage.getBoundingBoxAt(new Point(weaponX, weaponY));
    }

    /**
     * Set that the weapon has been picked up.
     */
    public void setPickedUp() {
        this.pickedUp = true;
    }

    /**
     * Change weapon state when shot.
     */
    public void shoot() {
        // Change weapon state when shot
        this.beingShot = true;
        this.pickedUp = false;
        this.shotFromFrame = 1;
    }

    /**
     * Update the state of the weapon.
     *
     * @param timescale The current timescale of the game.
     * @param birdRightX The x-coordinate of the right side of the bird.
     * @param birdY The y-coordinate of the centre of the bird.
     */
    public void update(int timescale, double birdRightX, double birdY){
        renderWeapon();
        move(timescale, birdRightX, birdY);

        // Keep rendering the weapon being shot until it is past its shooting range
        if (beingShot){
            if (shotFromFrame > getShootingRange()){
                continueRendering = false;
            }
        }

    }

    /**
     * Get whether the weapon is being shot ot not.
     *
     * @return whether the weapon is being shot ot not.
     */
    public boolean getBeingShot() {
        return beingShot;
    }

    /**
     * Set whether the weapon should continue to be rendered or not.
     *
     * @param continueRendering The flag indicating whether the weapon should continue to be rendered or not.
     */
    public void setContinueRendering(boolean continueRendering){
        this.continueRendering = continueRendering;
    }

    /**
     * Get whether the weapon should continue to be rendered or not.
     *
     * @return whether the weapon should continue to be rendered or not.
     */
    public boolean getContinueRendering(){
        return continueRendering;
    }

}
