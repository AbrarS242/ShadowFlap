import bagel.*;
import bagel.util.Rectangle;

public abstract class Level {

    protected final int PIPE_SPAWN_INTERVAL = 100;
    protected final int FONT_SIZE = 48;
    protected final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    protected final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    protected int scoreUp = 0;
    protected boolean gameOver = false;

    /**
     * Render the background for the level
     */
    public abstract void renderBackground();

    /**
     * Display the level instructions on screen
     */
    public abstract void renderInstructionScreen();

    /**
     * Update the state of the level.
     *
     * @param input The user input.
     * @param timescale The current timescale of the game.
     *
     */
    public abstract void update(Input input, int timescale);

    /**
     * Check if the bird is out of bounds.
     *
     * @returns whether the bird is out of bounds.
     */
    public abstract boolean birdOutOfBound();

    /**
     * Check if the bird collides with any pipe.
     *
     * @returns whether the bird has collided with any pipe.
     */
    public abstract boolean detectBirdPipeCollision();

    /**
     * Generate pipes at a regular interval.
     */
    public abstract void generatePipes();

    /**
     * Make the bird take damage and respawn if out of bounds.
     */
    public abstract void checkBirdState();

    /**
     * Update the score for when the bird passes the pipe.
     */
    public abstract void updateScore();


    /**
     * Get the score increase for the level.
     *
     * @return the score increase for the level.
     */
    public int getScoreUp() {
        return scoreUp;
    }

    /**
     * Set the score increase for the level.
     *
     * @param scoreUp The score increase for the level.
     */
    public void setScoreUp(int scoreUp) {
        this.scoreUp = scoreUp;
    }

    /**
     * Check if the game is over
     *
     * @return whether the game is over.
     */
    public boolean isGameOver(){
        return gameOver;
    }

}
