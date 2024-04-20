import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Bird implements Damageable {

    private static final double GRAVITATIONAL_ACCELERATION = 0.4;
    private static final double FLYING_ACCELERATION = 6;
    private static final double MAX_GRAVITY = -10;
    private static final double BIRD_SPAWN_X = 200;
    private static final double BIRD_SPAWN_Y = 350;
    private static final double FLAP_TIMING = 10;
    private final Image birdDown;
    private final Image birdUp;
    private Rectangle rectangle;
    private Point point;
    private LifeBar lifeBar;
    private Weapon weapon;
    private double x;
    private double y;
    private double speed = 0;
    private int frames = 1;
    private boolean dead = false;
    private boolean hasWeapon = false;

    /**
     * Instantiate a new bird.
     *
     * @param currentLives The current lives of the bird.
     * @param birdDown     The image of the bird with its wings down.
     * @param birdUp       The image of the bird with its wings up.
     */
    public Bird(int currentLives, Image birdDown, Image birdUp) {
        this.x = BIRD_SPAWN_X;
        this.y = BIRD_SPAWN_Y;
        this.lifeBar = new LifeBar(currentLives);
        this.birdDown = birdDown;
        this.birdUp = birdUp;
    }

    /**
     * Update the state of the bird.
     *
     * @param input The user input to the game.
     */
    public void update(Input input) {

        render();
        move(input);
        lifeBar.render();

        // Shoot the equipped weapon
        if (hasWeapon && input.wasPressed(Keys.S)){
            weapon.shoot();
            weapon = null;
            hasWeapon = false;
        }

        // Update the frame counter
        frames++;
    }

    /**
     * Display the bird with a flapping motion.
     */
    private void render() {
        // Display the bird with a flapping motion
        if (frames % FLAP_TIMING != 0){
            birdDown.draw(x, y);
            point = new Point(x, y);
            rectangle = birdDown.getBoundingBoxAt(point);
        } else {
            birdUp.draw(x, y);
            point = new Point(x, y);
            rectangle = birdUp.getBoundingBoxAt(point);
        }
    }

    /**
     * Move the bird.
     *
     * @param input The user input to the game.
     */
    public void move(Input input) {

        // Flying logic
        if (input.wasPressed(Keys.SPACE)){
            speed = FLYING_ACCELERATION;
        } else {

            // Gravity logic
            speed -= GRAVITATIONAL_ACCELERATION;
            if (speed < MAX_GRAVITY) {
                speed = MAX_GRAVITY;
            }
        }

        // Reposition the bird
        y -= speed;
    }

    /**
     * Spawn the bird at the original coordinates.
     */
    public void respawn () {
        // Spawn the bird at the original coordinates
        x = BIRD_SPAWN_X;
        y = BIRD_SPAWN_Y;
        speed = 0;
    }

    /**
     * Remove one life.
     */
    public void takeDamage() {
        // Remove one life
        lifeBar.loseLife();
        if (lifeBar.getCurrentLives() == 0){
            dead = true;
        }
    }

    /**
     * Check if the bird has lost all of its lives.
     *
     * @return whether the bird has lost all of its lives.
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Get the y-coordinate of the centre of the bird.
     *
     * @return the y-coordinate of the centre of the bird.
     */
    public double getY() { return y; }

    /**
     * Get the x-coordinate of the centre of the bird.
     *
     * @return the x-coordinate of the centre of the bird.
     */
    public double getX() { return x; }

    /**
     * Get the rectangle around the image of the bird.
     *
     * @return the rectangle around the image of the bird.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Pick up a weapon
     */
    public void equipWeapon(Weapon weapon) {
        // Pick up a weapon
        this.weapon = weapon;
        this.hasWeapon = true;
    }

    /**
     * Check if the bird has a weapon.
     *
     * @return whether the bird has a weapon.
     */
    public boolean getHasWeapon() {
        return hasWeapon;
    }

}
