import bagel.Image;
import bagel.Window;

public class PlasticPipeSet extends LevelOnePipeSet {

    /**
     * Instantiates a new plastic pipe set.
     *
     * @param gapPoint The y-coordinate of the start of the gap between the top and bottom pipes.
     */
    public PlasticPipeSet(int gapPoint) {
        this.pipeImage = new Image("res/level/plasticPipe.png");
        this.topPipeY = -Window.getHeight() + gapPoint;
        this.bottomPipeY = gapPoint + PIPE_GAP;
    }

    /**
     * Update the state of the plastic pipe set.
     *
     * @param timescale The current timescale of the game.
     */
    public void update(int timescale) {
        renderPipeSet();
        move(timescale);
    }

}
