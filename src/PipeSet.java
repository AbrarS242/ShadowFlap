import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

public abstract class PipeSet {

    protected final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    protected static final int PIPE_GAP = 168;
    protected final int INITIAL_PIPE_SPEED = 3;
    protected final double SPEED_CHANGE_MULTIPLIER = 1.5;
    protected Image pipeImage;
    protected double currentPipeSpeed = INITIAL_PIPE_SPEED;
    protected double topPipeY;
    protected double bottomPipeY;
    protected double pipeX = Window.getWidth();
    protected boolean checked = false;
    protected boolean continueRendering = true;

    /**
     * Update the state of the pipe set.
     *
     * @param timescale The current timescale of the game.
     */
    public abstract void update(int timescale);

    /**
     * Move the pipe.
     *
     * @param timescale The current timescale of the game.
     */
    public void move(int timescale)
    {
        // Move the pipe according to the timescale
        currentPipeSpeed = INITIAL_PIPE_SPEED * (Math.pow(SPEED_CHANGE_MULTIPLIER, (timescale - 1)));
        pipeX -= currentPipeSpeed;

        // Stop rendering the pipe once it leaves the screen
        if (getTopBox().right() < 0){
            continueRendering =  false;
        }
    }

    /**
     * Display the top and bottom pipes.
     */
    public void renderPipeSet() {
        // Display the top and bottom pipes
        pipeImage.drawFromTopLeft(pipeX, topPipeY);
        pipeImage.drawFromTopLeft(pipeX, bottomPipeY, ROTATOR);
    }

    /**
     * Get whether the pipe set has been checked.
     *
     * @return whether the pipe set has been checked.
     */
    public boolean getChecked(){
        return checked;
    }

    /**
     * Set whether the pipe set has been checked or not.
     *
     * @param checked The flag indicating whether the pipe set has been checked or not.
     */
    public void setChecked(boolean checked){
        this.checked = checked;
    }

    /**
     * Set whether the pipe set should continue to be rendered or not.
     *
     * @param continueRendering The flag indicating whether the pipe set should continue to be rendered or not.
     */
    public void setContinueRendering(boolean continueRendering){
        this.continueRendering = continueRendering;
    }

    /**
     * Get whether the pipe set should continue to be rendered or not.
     *
     * @return whether the pipe set should continue to be rendered or not.
     */
    public boolean getContinueRendering(){
        return continueRendering;
    }

    /**
     * Get the rectangle around the image of the top pipe.
     *
     * @return the rectangle around the image of the top pipe.
     */
    public Rectangle getTopBox() {
        return new Rectangle(pipeX, topPipeY, pipeImage.getWidth(), pipeImage.getHeight());
    }

    /**
     * Get the rectangle around the image of the bottom pipe.
     *
     * @return the rectangle around the image of the bottom pipe.
     */
    public Rectangle getBottomBox() {
        return new Rectangle(pipeX, bottomPipeY, pipeImage.getWidth(), pipeImage.getHeight());
    }

    /**
     * Get the x-coordinate of the right side of the pipe set.
     *
     * @return the x-coordinate of the right side of the pipe set.
     */
    public double getRightX(){
        return pipeImage.getBoundingBoxAt(new Point(pipeX, bottomPipeY)).right();
    }

}
