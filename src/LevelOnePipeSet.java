public abstract class LevelOnePipeSet extends PipeSet implements Damageable {

    /**
     * Instantiates a new level 1 pipe set.
     */
    public LevelOnePipeSet() {
    }

    /**
     * Update the state of the level 1 pipe set.
     *
     * @param timescale The current timescale of the game.
     */
    public void update(int timescale) {
    }

    /**
     * Destroy the pipe.
     */
    public void takeDamage() {
        // Destroy the pipe
        continueRendering = false;
    }

}
