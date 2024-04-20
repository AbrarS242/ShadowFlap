import bagel.*;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.lang.Math;

public class LevelZero extends Level {

    private final Image BACKGROUND_IMAGE = new Image("res/level-0/background.png");
    private final int PIPE_TYPES = 3;
    public final int LOW_GAP_NO = 1;
    public final int MID_GAP_NO = 2;
    public final int HIGH_GAP_NO = 3;
    public final int LOW_GAP = 500;
    public final int MID_GAP = 300;
    public final int HIGH_GAP = 100;
    private int LEVEL_0_LIVES = 3;
    private Image birdDown  = new Image("res/level-0/birdWingDown.png");
    private Image birdUp = new Image("res/level-0/birdWingUp.png");
    private Bird bird;
    private LevelZeroPipeSet newPipeSet;
    private ArrayList<LevelZeroPipeSet> pipeSets = new ArrayList<LevelZeroPipeSet>();
    private ArrayList<LevelZeroPipeSet> removePipeSets = new ArrayList<LevelZeroPipeSet>();
    private Rectangle topPipeBox;
    private Rectangle bottomPipeBox;
    private Rectangle birdBox;
    private int frames = 1;

    /**
     * Instantiates a new level 0.
     */
    public LevelZero() {
        this.bird = new Bird(LEVEL_0_LIVES, birdDown, birdUp);
    }

    /**
     * Update the state of level 0.
     *
     * @param input The user input.
     * @param timescale The current timescale of the game.
     *
     */
    public void update(Input input, int timescale) {

        renderBackground();
        generatePipes();

        // Update pipes
        if (!pipeSets.isEmpty()){
            for (LevelZeroPipeSet pipeSet : pipeSets) {
                pipeSet.update(timescale);
            }
        }

        // Update the bird
        bird.update(input);
        checkBirdState();

        // Check if the bird collides with any pipe
        if (!pipeSets.isEmpty() && detectBirdPipeCollision()){
            bird.takeDamage();
            if (bird.isDead()) {
                gameOver = true;
            }
        }

        // Remove pipes that no longer need to be rendered
        if (!pipeSets.isEmpty()){
            for (LevelZeroPipeSet pipeSet : pipeSets) {
                if (!pipeSet.getContinueRendering()) {
                    removePipeSets.add(pipeSet);
                }
            }
        }
        pipeSets.removeAll(removePipeSets);
        removePipeSets.clear();


        updateScore();

        // Update the frame counter
        frames++;
    }

    /**
     * Render the background for the level.
     */
    public void renderBackground() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

    /**
     * Update the score for when the bird passes the pipe.
     */
    public void updateScore() {
        // Update the score when the bird passes a pipe
        if (!pipeSets.isEmpty()) {
            for (LevelZeroPipeSet pipeSet : pipeSets)
                if (bird.getX() > pipeSet.getRightX() && !pipeSet.getChecked()) {
                    scoreUp ++;
                    pipeSet.setChecked(true);
                }
        }
    }

    /**
     * Check if the bird is out of bounds.
     *
     * @returns whether the bird is out of bounds.
     */
    public boolean birdOutOfBound() {
        // Check if the bird is out of bounds
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    /**
     * Check if the bird collides with any pipe.
     *
     * @returns whether the bird collides any pipe.
     */
    public boolean detectBirdPipeCollision() {
        // Check if the bird collides with any pipe
        boolean collision = false;
        birdBox = bird.getRectangle();
        for (LevelZeroPipeSet pipeSet : pipeSets){
            topPipeBox = pipeSet.getTopBox();
            bottomPipeBox = pipeSet.getBottomBox();
            if (birdBox.intersects(topPipeBox) || birdBox.intersects(bottomPipeBox)){
                collision = true;
                pipeSet.setContinueRendering(false);
                return collision;
            }
        }
        return collision;
    }

    /**
     * Display the level instructions on screen.
     */
    public void renderInstructionScreen() {
        // Display the level instructions on screen
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));

    }

    /**
     * Generate pipes at a regular interval.
     */
    public void generatePipes() {

        // Generate pipes at a regular interval
        if ((frames  % PIPE_SPAWN_INTERVAL == 0) || (frames == 1)){
            // Generate a random pipe type with a random gap point at the edge of the screen
            int gapType = (int)Math.floor(Math.random() * (PIPE_TYPES) + 1);
            if (gapType == LOW_GAP_NO) {
                newPipeSet = new LevelZeroPipeSet(LOW_GAP);
            } else if (gapType == MID_GAP_NO) {
                newPipeSet = new LevelZeroPipeSet(MID_GAP);
            } else if (gapType == HIGH_GAP_NO) {
                newPipeSet = new LevelZeroPipeSet(HIGH_GAP);
            }
            pipeSets.add(newPipeSet);
        }
    }

    /**
     * Make the bird take damage and respawn if out of bounds.
     */
    public void checkBirdState() {
        // Make the bird take damage and respawn if out of bounds
        if (birdOutOfBound()){
            bird.respawn();
            bird.takeDamage();
            if (bird.isDead()) {
                gameOver = true;
            }
        }
    }

}