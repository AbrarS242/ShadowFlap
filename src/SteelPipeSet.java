import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;

public class SteelPipeSet extends LevelOnePipeSet {

    private final int FLAME_SET_SPAWN_INTERVAL = 20;
    private final int FLAME_SET_RENDER_DURATION = 3;
    private FlameSet flameSet;
    private int frames = 1;
    private int flameFrames = 0;
    private boolean hasFlame = false;

    /**
     * Instantiates a new steel pipe set.
     *
     * @param gapPoint The y-coordinate of the start of the gap between the top and bottom pipes.
     */
    public SteelPipeSet(int gapPoint) {
        this.pipeImage = new Image("res/level-1/steelPipe.png");
        this.topPipeY = -Window.getHeight() + gapPoint;
        this.bottomPipeY = gapPoint + PipeSet.PIPE_GAP;
    }

    /**
     * Update the state of the steel pipe set.
     *
     * @param timescale The current timescale of the game.
     */
    public void update(int timescale) {

        // Spawn flames at a regular interval
        if (frames % FLAME_SET_SPAWN_INTERVAL == 0){
            flameFrames = 1;
            hasFlame = true;
        }

        // Keep the flames on screen for a set amount of time
        if (flameFrames >= 1 && flameFrames <= FLAME_SET_RENDER_DURATION){
            flameSet = new FlameSet(pipeX, getTopBox().bottom(), getBottomBox().top());
            flameSet.renderFlameSet();
            flameFrames ++;
        } else {
            hasFlame = false;
        }

        renderPipeSet();
        move(timescale);

        // Update the frame counter
        frames ++;
    }


    /**
     * Get the rectangle around the image of the flame of the top steel pipe.
     *
     * @return the rectangle around the image of the flame of the top steel pipe.
     */
    public Rectangle getTopFlameBox() {
        return flameSet.getTopBox();
    }

    /**
     * Get the rectangle around the image of the flame of the bottom steel pipe.
     *
     * @return the rectangle around the image of the flame of the bottom steel pipe.
     */
    public Rectangle getBottomFlameBox() {
        return flameSet.getBottomBox();
    }

    /**
     * Get whether the pipe set has flames or not.
     *
     * @return whether the pipe set has flames or not.
     */
    public boolean getHasFlame() {
        return hasFlame;
    }

}
