import bagel.*;
import bagel.util.Rectangle;

import java.lang.Math;

import java.util.ArrayList;

public class LevelOne extends Level{

    private final Image BACKGROUND_IMAGE = new Image("res/level-1/background.png");
    private final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final int PIPE_TYPES = 2;
    private final int WEAPON_TYPES = 2;
    public final int SHOOT_MSG_OFFSET = 68;
    public final int PLASTIC_PIPE_NO = 1;
    public final int STEEL_PIPE_NO = 2;
    public final int LOWER_BOUND_GAP_Y = 100;
    public final int UPPER_BOUND_GAP_Y = 500;
    public final int LOWER_BOUND_WEAPON_SPAWN_Y = 100;
    public final int UPPER_BOUND_WEAPON_SPAWN_Y = 500;
    public final int WEAPON_SPAWN_INTERVAL = 180;
    public final int ROCK_NO = 1;
    public final int BOMB_NO = 2;
    private int LEVEL_1_LIVES = 6;
    private Image birdDown  = new Image("res/level-1/birdWingDown.png");
    private Image birdUp = new Image("res/level-1/birdWingUp.png");
    private Bird bird;
    private LevelOnePipeSet newPipeSet;
    private ArrayList<LevelOnePipeSet> pipeSets = new ArrayList<LevelOnePipeSet>();
    private ArrayList<LevelOnePipeSet> removePipeSets = new ArrayList<LevelOnePipeSet>();
    private Weapon newWeapon;
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    private ArrayList<Weapon> removeWeapons = new ArrayList<Weapon>();
    private Rectangle topPipeBox;
    private Rectangle bottomPipeBox;
    private Rectangle birdBox;
    private Rectangle weaponBox;
    private Rectangle topFlameBox;
    private Rectangle bottomFlameBox;
    private int frames = 1;

    /**
     * Instantiates a new level 1.
     */
    public LevelOne() {
        this.bird = new Bird(LEVEL_1_LIVES, birdDown, birdUp);
    }

    /**
     * Update the state of level 1.
     *
     * @param input The user input.
     * @param timescale The current timescale of the game.
     *
     */
    public void update(Input input, int timescale) {
        renderBackground();
        generatePipes();
        generateWeapons();


        // Update all pipes
        if (!pipeSets.isEmpty()){
            for (LevelOnePipeSet pipeSet : pipeSets) {
                pipeSet.update(timescale);
            }
        }

        // Update the bird
        bird.update(input);
        checkBirdState();


        // Update all weapons
        if (!weapons.isEmpty()){
            for (Weapon weapon : weapons) {
                weapon.update(timescale, bird.getRectangle().right(), bird.getY());
            }
        }

        // Check for a collision between the bird and any pipe
        if (!pipeSets.isEmpty() && detectBirdPipeCollision()){
            bird.takeDamage();
            if (bird.isDead()) {
                gameOver = true;
            }
        }

        // Check for collision between the bird and any weapon
        if (!weapons.isEmpty()){
            detectBirdWeaponCollision();
        }

        // Check for a collision between any weapon and any pipe
        if (!weapons.isEmpty() && !pipeSets.isEmpty()){
            detectWeaponPipeCollision();
        }

        // Remove pipes that no longer need to be rendered
        if (!pipeSets.isEmpty()){
            for (LevelOnePipeSet pipeSet : pipeSets) {
                if (!pipeSet.getContinueRendering()) {
                    removePipeSets.add(pipeSet);
                }
            }
        }
        pipeSets.removeAll(removePipeSets);
        removePipeSets.clear();

        // Remove weapons that no longer need to be rendered
        if (!weapons.isEmpty()){
            for (Weapon weapon : weapons) {
                if (!weapon.getContinueRendering()) {
                    removeWeapons.add(weapon);
                }
            }
        }
        weapons.removeAll(removeWeapons);
        removeWeapons.clear();

        updateScore();

        // Update the frame counter
        frames++;

    }

    /**
     * Render the background for the level.
     */
    public void renderBackground() {
        // Render the background for the level
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

    /**
     * Update the score for when the bird passes the pipe.
     */
    public void updateScore() {
        // Update the score when the bird passes a pipe
        for (LevelOnePipeSet pipeSet : pipeSets)
            if (bird.getX() > pipeSet.getRightX() && !pipeSet.getChecked()) {
                scoreUp ++;
                pipeSet.setChecked(true);
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
     * @returns whether the bird collides with any pipe.
     */
    public boolean detectBirdPipeCollision() {

        boolean collision = false;
        birdBox = bird.getRectangle();

        // Check if the bird collides with any pipe
        for (LevelOnePipeSet pipeSet : pipeSets){
            topPipeBox = pipeSet.getTopBox();
            bottomPipeBox = pipeSet.getBottomBox();


            if (pipeSet instanceof SteelPipeSet && ((SteelPipeSet) pipeSet).getHasFlame()) {
                // Check if the bird collides with any steel pipe or its flames
                topFlameBox = ((SteelPipeSet) pipeSet).getTopFlameBox();
                bottomFlameBox = ((SteelPipeSet) pipeSet).getBottomFlameBox();
                if (birdBox.intersects(topPipeBox) || birdBox.intersects(bottomPipeBox)
                        || birdBox.intersects(bottomFlameBox) || birdBox.intersects(topFlameBox) ){
                    collision = true;
                    pipeSet.setContinueRendering(false);
                    return collision;
                }
            } else {
                // Check if the bird collides with any plastic pipe
                if (birdBox.intersects(topPipeBox) || birdBox.intersects(bottomPipeBox)){
                    collision = true;
                    pipeSet.setContinueRendering(false);
                    return collision;
                }
            }
        }
        return collision;

    }

    /**
     * Check if the bird (without a weapon) collides with any weapon.
     *
     * @returns whether the bird (without a weapon) collides with any weapon.
     */
    public boolean detectBirdWeaponCollision() {
        // Check if the bird (without a weapon) collides with any weapon
        boolean collision = false;
        birdBox = bird.getRectangle();
        if (!bird.getHasWeapon()){
            for (Weapon weapon : weapons) {
                if (!weapon.getBeingShot()){
                    weaponBox = weapon.getRectangle();
                    if (birdBox.intersects(weaponBox)) {
                        collision = true;
                        bird.equipWeapon(weapon);
                        weapon.setPickedUp();
                        return collision;
                    }
                }
            }
            return collision;
        }
        return collision;
    }

    /**
     * Check if the weapon being shot collides with any pipe.
     *
     * @returns whether the weapon being shot collides with any pipe.
     */
    public boolean detectWeaponPipeCollision() {

        // Check if a weapon being shot collides with any pipe
        boolean collision = false;
        for (Weapon weapon : weapons){
            if (weapon.getBeingShot()){
                weaponBox = weapon.getRectangle();
                for (LevelOnePipeSet pipeSet : pipeSets){
                    topPipeBox = pipeSet.getTopBox();
                    bottomPipeBox = pipeSet.getBottomBox();
                    if (weaponBox.intersects(topPipeBox) || weaponBox.intersects(bottomPipeBox)){
                        collision = true;
                        weapon.setContinueRendering(false);

                        // Check if the weapon can damage the pipe
                        if ((weapon instanceof Bomb) && (pipeSet instanceof SteelPipeSet)) {
                            scoreUp ++;
                            ((SteelPipeSet) pipeSet).takeDamage();
                        } else if (pipeSet instanceof PlasticPipeSet) {
                            scoreUp ++;
                            ((PlasticPipeSet) pipeSet).takeDamage();
                        }
                        return collision;
                    }
                }

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
        FONT.drawString(SHOOT_MSG, (Window.getWidth()/2.0-(FONT.getWidth(SHOOT_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)) + SHOOT_MSG_OFFSET);
    }

    /**
     * Generate pipes at a regular interval.
     */
    public void generatePipes() {

        // Generate pipes at a regular interval
        if ((frames  % PIPE_SPAWN_INTERVAL == 0) || (frames == 1)){
            // Generate a random pipe type with a random gap point at the edge of the screen
            int gapPoint = (int)Math.floor(Math.random() * (UPPER_BOUND_GAP_Y - LOWER_BOUND_GAP_Y) + LOWER_BOUND_GAP_Y);
            int pipeType = (int)Math.floor(Math.random() * (PIPE_TYPES) + 1);
            if (pipeType == PLASTIC_PIPE_NO) {
                newPipeSet = new PlasticPipeSet(gapPoint);
            } else if (pipeType == STEEL_PIPE_NO) {
                newPipeSet = new SteelPipeSet(gapPoint);
            }
            pipeSets.add(newPipeSet);
        }
    }

    /**
     * Generate weapons at a regular interval.
     */
    public void generateWeapons() {

        // Generate weapons at a regular interval
        if (frames  % WEAPON_SPAWN_INTERVAL == 0){
            while (true) {
                boolean reRender = false;

                // Generate a random weapon type at a random y-coordinate at the edge of the screen
                int weaponSpawnY = (int)Math.floor(Math.random() *
                        (UPPER_BOUND_WEAPON_SPAWN_Y - LOWER_BOUND_WEAPON_SPAWN_Y) + LOWER_BOUND_WEAPON_SPAWN_Y);
                int weaponType = (int)Math.floor(Math.random() * (WEAPON_TYPES) + 1);
                if (weaponType == ROCK_NO) {
                    newWeapon = new Rock(weaponSpawnY);
                } else if (weaponType == BOMB_NO) {
                    newWeapon = new Bomb(weaponSpawnY);
                }

                // Ensure that the generated weapon does not spawn on any pipe
                if (pipeSets.isEmpty()){
                    break;
                }
                weaponBox = newWeapon.getRectangle();
                for (LevelOnePipeSet pipeSet : pipeSets){
                    topPipeBox = pipeSet.getTopBox();
                    bottomPipeBox = pipeSet.getBottomBox();
                    if (weaponBox.intersects(topPipeBox) || weaponBox.intersects(bottomPipeBox)){
                        reRender = true;
                        break;
                    }
                }
                if (!reRender){
                    break;
                }
            }

            weapons.add(newWeapon);
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
