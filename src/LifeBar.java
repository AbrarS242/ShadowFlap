import bagel.*;

public class LifeBar {

    private int FIRST_SPAWN_X = 100;
    private int SPAWN_Y = 15;
    private int HEART_SPACE = 50;
    private int emptyLives = 0;
    private int initialLives;
    private int currentLives;
    private Image lifeHeart = new Image("res/level/fullLife.png");
    private Image noLifeHeart = new Image ("res/level/noLife.png");

    /**
     * Instantiates a new life bar.
     *
     * @param numLives The number of lives of the bird.
     */
    public LifeBar (int numLives){
        this.initialLives = numLives;
        this.currentLives = numLives;
    }

    /**
     * Update the state of the life bar.
     */
    public void update() {
        render();
    }

    /**
     * Display the life bar.
     */
    public void render() {

        int renderPoint = FIRST_SPAWN_X;

        // Render the full hearts
        for (int i = 0; i < currentLives; i++) {
            lifeHeart.drawFromTopLeft(renderPoint, SPAWN_Y);
            renderPoint += HEART_SPACE;
        }

        // Render the empty hearts
        for (int i = 0; i < emptyLives; i++) {
            noLifeHeart.drawFromTopLeft(renderPoint, SPAWN_Y);
            renderPoint += HEART_SPACE;
        }
    }

    /**
     * Adjust the life bar with one less life when the bird takes damage.
     */
    public void loseLife() {
        // Adjust the life bar with one less life when the bird takes damage
        if (currentLives > 0) {
            currentLives -= 1;
        }
        if (emptyLives < initialLives) {
            emptyLives += 1;
        }
    }

    /**
     * Get the current lives of the bird.
     *
     * @return the current lives of the bird.
     */
    public int getCurrentLives() {
        return currentLives;
    }

}
