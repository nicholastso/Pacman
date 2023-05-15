import bagel.*;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 *
 * Please enter your name below
 * @author Nicholas Tso
 */
public class ShadowPac extends AbstractGame  {
	
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static String WORLD_FILE1 = "res/level0.csv";
    private final static String WORLD_FILE2 = "res/level1.csv";
    
    // title, instruction message and fonts defs
    private final static int TITLE_SIZE = 64;
    private final static int INSTRUCTION_SIZE = 24;
    private final static int SCORE_SIZE = 20;
    private final Font TITLE_FONT = new Font("res/FSO8BITR.TTF", TITLE_SIZE);
    private final Font INSTRUCTION_FONT = new Font("res/FSO8BITR.TTF", INSTRUCTION_SIZE);
    private final Font SCORE_FONT = new Font("res/FSO8BITR.TTF", SCORE_SIZE);
    private final static String INSTRUCTION_MESSAGE = "PRESS SPACE TO START\nUSE ARROW KEYS TO FIND GATE";
    private final static String LEVEL_COMPLETE = "LEVEL COMPLETE!";
    private final static String WIN_MESSAGE = "WELL DONE!";
    private final static String LOSE_MESSAGE = "Game OVER!";
    private final static int TITLE_X = 260;
    private final static int TITLE_Y = 250;
    private final static int INS_X = 60;
    private final static int INS_Y = 190;
    private final static int SCO_X = 25;
    private final static int SCO_Y = 25;
    private final static int MAX_FRAMES = 300;
    private final static int MAX_SCORE1 = 110;
    private final static int MAX_SCORE2 = 800;
    
    // array defs for game entities
    private final static int GHOSTS_SIZE = 4;
    private final static int WALLS_SIZE = 145;
    private final static int DOTS_SIZE = 121;
    private final static int WALLS_SIZE2 = 151;
    private final static int DOTS_SIZE2 = 106;
    private final static int CHERRY_SIZE = 3;
    private final static Wall[] walls1 = new Wall[WALLS_SIZE];
    private final static Wall[] walls2 = new Wall[WALLS_SIZE2];
    private final static Ghost[] ghosts = new Ghost[GHOSTS_SIZE];
    private final static Dot[] dots1 = new Dot[DOTS_SIZE];
    private final static Dot[] dots2 = new Dot[DOTS_SIZE2];
    private final static Cherry[] cherrys = new Cherry[CHERRY_SIZE];
    
    private int score;
    private int frameCount = 0;
    private int startX;
    private int startY;
    private int startX2;
    private int startY2;
    private Player player;
    private GhostRed ghostRed;
    private GhostBlue ghostBlue;
    private GhostGreen ghostGreen;
    private GhostPink ghostPink;
    private Pellet pellet;
    private boolean hasStarted1;
    private boolean hasStarted2;
    private boolean gameOver;
    private boolean playerWin1;
    private boolean playerWin2;
    private int hearts = 3;

    public ShadowPac() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV1();
        hasStarted1 = false;
        gameOver = false;
        playerWin1 = false;
        playerWin2 = false;
        hasStarted2 = false;
    }

    // read world file for level 1
    private void readCSV1() {
    	try(BufferedReader reader = new BufferedReader(new FileReader(WORLD_FILE1))) {
            String line;
            int wallCount = 0;
            int dotCount = 0;
            int ghostCount = 0;

            while((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                switch(sections[0]) {
                    case "Player":
                        player = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        startX = Integer.parseInt(sections[1]);
                        startY = Integer.parseInt(sections[2]);
                        break;
                    case "Ghost":
                        ghosts[ghostCount] = new Ghost(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        ghostCount++;
                        break;
                    case "Wall":
                        walls1[wallCount] = new Wall(Integer.parseInt(sections[1]),Integer.parseInt(sections[2]));
                        wallCount++;
                        break;
                    case "Dot":
                        dots1[dotCount] = new Dot(Integer.parseInt(sections[1]),Integer.parseInt(sections[2]));
                        dotCount++;
                        break;
                }
            }
        }
    	
    	catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // read world file for level 2
    private void readCSV2() {
    	try(BufferedReader reader = new BufferedReader(new FileReader(WORLD_FILE2))) {
            String line;
            int wallCount2 = 0;
            int dotCount2 = 0;
            int cherryCount = 0;

            while((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                switch(sections[0]) {
                    case "Player":
                        player = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        startX2 = Integer.parseInt(sections[1]);
                        startY2 = Integer.parseInt(sections[2]);
                        break;
                    case "Wall":
                        walls2[wallCount2] = new Wall(Integer.parseInt(sections[1]),Integer.parseInt(sections[2]));
                        wallCount2++;
                        break;
                    case "Dot":
                        dots2[dotCount2] = new Dot(Integer.parseInt(sections[1]),Integer.parseInt(sections[2]));
                        dotCount2++;
                        break;
                    case "Cherry":
                        cherrys[cherryCount] = new Cherry(Integer.parseInt(sections[1]),Integer.parseInt(sections[2]));
                        cherryCount++;
                        break;
                    case "Pellet":
                        pellet = new Pellet(Integer.parseInt(sections[1]),Integer.parseInt(sections[2]));
                        break;
                    case "GhostRed":
                        ghostRed = new GhostRed(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "GhostBlue":
                        ghostBlue = new GhostBlue(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "GhostGreen":
                        ghostGreen = new GhostGreen(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "GhostPink":
                        ghostPink = new GhostPink(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                }
            }
        }
    	
    	catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    // game updates
    @Override
    protected void update(Input input) {
        if(input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if(!hasStarted1 && !hasStarted2) {
            drawStartScreen();
            if(input.wasPressed(Keys.SPACE)){
                hasStarted1 = true;
            }
        }
        
        if(gameOver) {
            drawMessage(LOSE_MESSAGE);
        }
        
        // draw level complete and load level 2 when conditions are met
        if(playerWin1 && !hasStarted2) {
        	BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        	drawMessage(LEVEL_COMPLETE);
        	score = 0;
        	hasStarted1 = false;
        	
        	// timer to count to 300 frames
        	frameCount++;
        	if(frameCount == MAX_FRAMES) {
        		hasStarted2 = true;
        	}
        	
        	readCSV2();
        	
        }
        
        if(playerWin2) {
        	drawMessage(WIN_MESSAGE);
        } 
        
        // load level 1 when start conditions are matched
        if(hasStarted1 && !gameOver && !playerWin1) {
        	
        	SCORE_FONT.drawString("SCORE " + score, SCO_X, SCO_Y);
        	player.drawHeart(hearts);
        	
        	for(Wall current: walls1) {
        		current.update();
        	}
        	
        	for(Ghost current: ghosts) {
        		current.update();
        	}
        	
        	for(Dot current: dots1) {
        		current.update();
        	}
        	
        	player.update(input, this);
        	
        	if(hearts == 0) {
                gameOver = true;
            }
        	
        	if(score == MAX_SCORE1) {
        		playerWin1 = true;
        	}
        	
        	collisionsDetection(player);
        }
        
        // level 2 starts when conditions are met
        if(hasStarted2 && !gameOver && playerWin1 && !playerWin2) {
        	
        	SCORE_FONT.drawString("SCORE " + score, SCO_X, SCO_Y);
        	player.drawHeart(hearts);
        	
        	for(Wall current: walls2) {
        		current.update();
        	}
        	
        	for(Dot current: dots2) {
        		current.update();
        	}
        	
        	for(Cherry current: cherrys) {
        		current.update();
        	}
        	
        	pellet.update();
        	ghostRed.update();
        	ghostBlue.update();
        	ghostGreen.update();
        	ghostPink.update();
        	player.update(input, this);
        	
        	if(hearts == 0){
                gameOver = true;
            }
        	
        	if(score == MAX_SCORE2) {
        		playerWin2 = true;
        	}
        	
        	collisionsDetection2(player);
        }
        
    }
    
    // apply hit-box for all level 1 entities, and actions when Pac man collides
    public void collisionsDetection(Player player) {
        Rectangle pacBox = player.getBoundingBox();
        
        for(Wall current : walls1) {
            Rectangle wallBox = current.getBoundingBox();
            if(pacBox.intersects(wallBox)) {
               player.moveBack();		// moves player back to last position when hits a wall
            }
        }

        for(Ghost ghost : ghosts) {
            Rectangle ghostBox = ghost.getBoundingBox();
            if(pacBox.intersects(ghostBox)) {
            	hearts--;
                player.moveToStart(startX, startY);		// move player back to starting point
            }
        }
        
        for(Dot dot : dots1) {
        	Rectangle dotBox = dot.getBoundingBox();
        	if(dot.isActive() && pacBox.intersects(dotBox)) {
        		score += 10;
        		dot.setActive(false);		// disable dots that are picked up
        	}
        }
        
    }
    
    // apply hit-box for all level 2 entities, and actions when Pac man collides
    public void collisionsDetection2(Player player) {
        Rectangle pacBox = player.getBoundingBox();
        Rectangle pelletBox = pellet.getBoundingBox();
        
        if(pacBox.intersects(pelletBox)) {
        	ghostRed.ghostRedFrenzy();		// frenzy mode when player picks up pellet
        	ghostBlue.ghostBlueFrenzy();
        	ghostGreen.ghostGreenFrenzy();
        	ghostPink.ghostPinkFrenzy();
        	pellet.setActive(false);
        }
        
        Rectangle ghostRedBox = ghostRed.getBoundingBox();
        if(pacBox.intersects(ghostRedBox)) {
        	hearts--;
            player.moveToStart(startX2, startY2);		// move player back to starting point, can add ghost reset position
        }
        
        Rectangle ghostBlueBox = ghostBlue.getBoundingBox();
        if(pacBox.intersects(ghostBlueBox)) {
        	hearts--;
            player.moveToStart(startX2, startY2);
        }
        
        Rectangle ghostGreenBox = ghostGreen.getBoundingBox();
        if(pacBox.intersects(ghostGreenBox)) {
        	hearts--;
            player.moveToStart(startX2, startY2);
        }
        
        Rectangle ghostPinkBox = ghostPink.getBoundingBox();
        if(pacBox.intersects(ghostPinkBox)) {
        	hearts--;
            player.moveToStart(startX2, startY2);
        }
        
        for(Wall current : walls2) {
            Rectangle wallBox = current.getBoundingBox();
            if(pacBox.intersects(wallBox)) {
               player.moveBack();		// moves player back to last position when hits a wall
            }
            if(ghostRedBox.intersects(wallBox)) {
            	ghostRed.changeDirection();		// changes the direction of the ghosts when they hit a wall
            	ghostRed.moveBack();
            }
            if(ghostBlueBox.intersects(wallBox)) {
            	ghostBlue.changeDirection();
            	ghostBlue.moveBack();
            }
            if(ghostGreenBox.intersects(wallBox)) {
            	ghostGreen.changeDirection();
            	ghostGreen.moveBack();
            }
            if(ghostPinkBox.intersects(wallBox)) {
            	ghostPink.changeDirection();
            	ghostPink.moveBack();
            }
        }

        for(Dot dot : dots2) {
        	Rectangle dotBox = dot.getBoundingBox();
        	if(dot.isActive() && pacBox.intersects(dotBox)) {
        		score += 10;
        		dot.setActive(false);		// disable dots that are picked up
        	}
        }

        for(Cherry cherry : cherrys) {
        	Rectangle cherryBox = cherry.getBoundingBox();
        	if(cherry.isActive() && pacBox.intersects(cherryBox)) {
        		score += 20;
        		cherry.setActive(false);		// disable cherries that are picked up
        	}
        }
        
    }
    
    private void drawStartScreen() {
        TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE,TITLE_X + INS_X, TITLE_Y + INS_Y);
    }
    
    private void drawMessage(String message) {
        TITLE_FONT.drawString(message, (Window.getWidth()/2.0 - (TITLE_FONT.getWidth(message)/2.0)),
                (Window.getHeight()/2.0 + (TITLE_SIZE/2.0)));
    }
    
}
