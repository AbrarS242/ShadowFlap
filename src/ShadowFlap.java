import bagel.*;

/**
 * @author Abrar Hasan Yusuf
 */
public class ShadowFlap extends AbstractGame {

    public final int MAX_TIMESCALE = 5;
    public final int MIN_TIMESCALE = 1;
    public final int SCORE_COUNTER_X = 100;
    public final int SCORE_COUNTER_Y = 100;
    private final int FONT_SIZE = 48;
    private final int SCORE_MSG_OFFSET = 75;
    public final int LEVEL_UP_SCORE = 10;
    public final int LEVEL_UP_MSG_FRAMES = 20;
    public final int WIN_SCORE = 30;
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final String LEVEL_UP_MSG = "LEVEL UP!";
    private final String GAME_OVER_MSG = "GAME OVER";
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    public enum LevelNum {
        ZERO,
        ONE
    }
    private Image backgroundImage = new Image("res/level-0/background.png");
    public LevelNum activeLevel;
    public LevelZero levelZero = new LevelZero();
    public LevelOne levelOne = new LevelOne();
    private int score;
    private boolean gameOn;
    private boolean win;
    private int levelUpStartFrame;
    private int frames = 1;
    public int timescale = 1;
    private boolean gameOver = false;
    private boolean levelUpScreen = false;


    /**
     * Instantiates a new game of Shadow Flap.
     */
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        activeLevel = LevelNum.ZERO;
        score = 0;
        gameOn = false;
        win = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     *
     * @param input The user input to the game.
     */
    @Override
    public void update(Input input) {

        // Exit the game if the escape key is pressed.
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // Game has not started
        if (!gameOn) {
            if (activeLevel == LevelNum.ZERO) {
                levelZero.renderBackground();
                levelZero.renderInstructionScreen();
            } else if (activeLevel == LevelNum.ONE){
                levelOne.renderBackground();
                levelOne.renderInstructionScreen();
            }
            if (input.wasPressed(Keys.SPACE)) {
                gameOn = true;
            }
        }

        // Game is on level 0
        if (gameOn && !levelUpScreen && activeLevel == LevelNum.ZERO && !gameOver && !win){
            backgroundImage = new Image("res/level-0/background.png");
            levelZero.update(input, timescale);
            updateScore();
            adjustTimescale(input);
            if (score == LEVEL_UP_SCORE) {
                levelUpScreen = true;
                levelUpStartFrame = frames;
                timescale = 1;
            }
            gameOver = levelZero.isGameOver();
        }

        // Game is on level 1
        if (gameOn && !levelUpScreen && activeLevel == LevelNum.ONE && !gameOver && !win){
            backgroundImage = new Image("res/level-1/background.png");
            levelOne.update(input, timescale);
            updateScore();
            adjustTimescale(input);
            gameOver = levelOne.isGameOver();
        }

        // Levelling Up
        if (gameOn && levelUpScreen){
             if (frames - levelUpStartFrame <= LEVEL_UP_MSG_FRAMES) {
                 renderLevelUpScreen();
             } else {
                 levelUpScreen = false;
                 activeLevel = LevelNum.ONE;
                 gameOn = false;
             }
        }

        // Game over
        if (gameOver){
            if (activeLevel == LevelNum.ZERO) {
                levelZero.renderBackground();
            } else if (activeLevel == LevelNum.ONE){
                levelOne.renderBackground();
            }
            renderGameOverScreen();

        }

        // Game won
        if (win && activeLevel == LevelNum.ONE) {
            renderWinScreen();
        }

        frames ++;
    }

    /**
     * Display the winning message on screen.
     */
    public void renderWinScreen() {
        // Display the winning message on screen
        backgroundImage.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    /**
     * Display the level up message on screen.
     */
    public void renderLevelUpScreen() {
        // Display the level up message on screen
        backgroundImage.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        FONT.drawString(LEVEL_UP_MSG, (Window.getWidth()/2.0-(FONT.getWidth(LEVEL_UP_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    /**
     * Update the game score
     */
    public void updateScore() {

        // Update the game score
        if (activeLevel == LevelNum.ZERO && levelZero.getScoreUp() >= 0) {
            score += levelZero.getScoreUp();
            levelZero.setScoreUp(0);
        } else if (activeLevel == LevelNum.ONE && levelOne.getScoreUp() >= 0){
            score += levelOne.getScoreUp();
            levelOne.setScoreUp(0);
        }
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, SCORE_COUNTER_X, SCORE_COUNTER_Y);

        // Detect win
        if (score == WIN_SCORE) {
            win = true;
        }
    }

    /**
     * Adjust the timescale according to the player input
     *
     * @param input The user input to the game.
     */
    public void adjustTimescale(Input input){
        // Adjust the timescale according to the player input
        if (input.wasPressed(Keys.L) && timescale < MAX_TIMESCALE) {
            timescale += 1;
        } else if (input.wasPressed(Keys.K) && timescale > MIN_TIMESCALE) {
            timescale -= 1;
        }
    }

    /**
     * Display the game over message on screen.
     */
    public void renderGameOverScreen() {
        // Display the game over message on screen
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

}
