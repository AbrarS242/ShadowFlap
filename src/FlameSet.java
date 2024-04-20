import bagel.*;
import bagel.util.Rectangle;

public class FlameSet {
    private final Image FLAME_IMAGE = new Image ("res/level-1/flame.png");
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private final int RENDER_PIXELS = 10;
    private double flameLeftX;
    private double topPipeBottomY;
    private double bottomPipeTopY;

    /**
     * Instantiates a new set of flames.
     *
     * @param flameLeftX     The x-coordinate of the left of the flame
     * @param topPipeBottomY The y-coordinate of the bottom side of the top pipe which the flame is generated from.
     * @param bottomPipeTopY The y-coordinate of the top side of the bottom pipe which the flame is generated from.
     */
    public FlameSet (double flameLeftX, double topPipeBottomY, double bottomPipeTopY) {
        this.flameLeftX = flameLeftX;
        this.topPipeBottomY = topPipeBottomY;
        this.bottomPipeTopY = bottomPipeTopY;
    }

    /**
     * Display the flames on screen
     */
    public void renderFlameSet() {
        // Render the flames on screen
        FLAME_IMAGE.draw(flameLeftX + FLAME_IMAGE.getWidth() / 2.0, topPipeBottomY + RENDER_PIXELS);
        FLAME_IMAGE.draw(flameLeftX + FLAME_IMAGE.getWidth() / 2.0 , bottomPipeTopY - RENDER_PIXELS, ROTATOR);
    }

    /**
     * Get the rectangle around the image of the top flame.
     *
     * @return the rectangle around the image of the top flame.
     */
    public Rectangle getTopBox() {
        return new Rectangle(flameLeftX + FLAME_IMAGE.getWidth() / 2.0,  topPipeBottomY + RENDER_PIXELS,
                FLAME_IMAGE.getWidth(), FLAME_IMAGE.getHeight());
    }

    /**
     * Get the rectangle around the image of the bottom flame.
     *
     * @return the rectangle around the image of the bottom flame.
     */
    public Rectangle getBottomBox() {
        return new Rectangle(flameLeftX + FLAME_IMAGE.getWidth() / 2.0, bottomPipeTopY - RENDER_PIXELS,
                FLAME_IMAGE.getWidth(), FLAME_IMAGE.getHeight());
    }

}
