import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class LevelZeroPipeSet extends PipeSet{

    /**
     * Instantiates a new level 0 pipe set.
     *
     * @param gapPoint The y-coordinate of the start of the gap between the top and bottom pipes.
     */
    public LevelZeroPipeSet(int gapPoint) {
        this.pipeImage = new Image("res/level/plasticPipe.png");
        this.topPipeY = -Window.getHeight() + gapPoint;
        this.bottomPipeY = gapPoint + PIPE_GAP;
    }

    /**
     * Update the state of the level 0 pipe set.
     *
     * @param timescale The current timescale of the game.
     */
    public void update(int timescale) {
        renderPipeSet();
        move(timescale);
    }

}
