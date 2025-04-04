import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.GradientPaint;
import java.awt.AlphaComposite;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Composite;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;



/*
 *
 * 
 * @author AbuBakar
 * 
 * 
 */

public class Game_Panel extends JPanel implements Runnable{
    // Screen Settings
    final int Finalchar_size=16;
    final int scale=4;
    final int actualsize=Finalchar_size*scale;
    final int maxscreencolumn=16;
    final int maxscreenrow=12;
    final int screenwidth=maxscreencolumn*actualsize;
    final int screenheight=maxscreenrow*actualsize;
    
    // World settings
    final int maxWorldCol = 50; // Larger world than screen
    final int maxWorldRow = 50;
    
    // Camera position
    public int cameraX = 0;
    public int cameraY = 0;
    
    // Game components
    Controls c=new Controls(this);
    TileManager tileManager = new TileManager(this);
    PLayer player =new PLayer(this,this.c);
    
    // Reference to the main game
    private game mainGame;
    
    // Map transition variables
    private String pendingMapChange = null;
    private boolean changingMap = false;
    private Map<String, Point> exitPositions = new HashMap<>();
    private String currentMap = "maps/eldenbrook.txt";
    
    int playerx=100;
    int playery=100;
    int playerspeed=4;
    
    Thread gameThread;
    
    int fps=60;
    private boolean gameRunning = true;
    
    
    public Game_Panel() {
        this(null);
    }
    
    public Game_Panel(game mainGame){
        this.mainGame = mainGame;
        this.setPreferredSize(new Dimension(screenwidth,screenheight));
        this.setDoubleBuffered(true);
        this.setBackground(Color.black); // Change to black as a fallback
        
        // Set up exit positions for each map (where player appears when exiting a structure)
        setupExitPositions();
        
        // Ensure focus is properly set
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(c);
        
        // Print if we have focus
        System.out.println("Game panel initialized. Has focus: " + this.hasFocus());
    }
    
    /**
     * Set up exit positions for each map
     */
    private void setupExitPositions() {
        // Exit positions for each map
        exitPositions = new HashMap<>();
        
        // Kael's House exit position (in main map coordinates)
        exitPositions.put("kaels_house", new Point(23 * actualsize, 18 * actualsize));
        
        // Temple Ruins exit position (in main map coordinates)
        exitPositions.put("temple_ruins", new Point(37 * actualsize, 14 * actualsize));
        
        // Forest Cave exit position (in main map coordinates)
        exitPositions.put("forest_cave", new Point(12 * actualsize, 33 * actualsize));
    }
    
    /**
     * Request a map change. This will be processed in the update method.
     * @param mapFile The map file to load
     */
    public void requestMapChange(String mapFile) {
        if (!changingMap) {
            pendingMapChange = mapFile;
            changingMap = true;
            System.out.println("Map change requested: " + mapFile);
        }
    }
    
    /**
     * Process a pending map change
     */
    private void processMapChange() {
        if (pendingMapChange != null) {
            if (pendingMapChange.equals("exit")) {
                // Return to main map
                returnToMainMap();
            } else {
                // Load new map
                String previousMap = currentMap;
                currentMap = pendingMapChange;
                tileManager.loadMap(pendingMapChange);
                
                // Position player at appropriate entry point
                if (pendingMapChange.equals("maps/kaels_house.txt")) {
                    player.x = 5 * actualsize;
                    player.y = 8 * actualsize;
                } else if (pendingMapChange.equals("maps/temple_ruins.txt")) {
                    player.x = 25 * actualsize;
                    player.y = 20 * actualsize;
                } else if (pendingMapChange.equals("maps/cave.txt")) {
                    player.x = 5 * actualsize;
                    player.y = 5 * actualsize;
                } else if (pendingMapChange.equals("maps/village_house.txt")) {
                    player.x = 7 * actualsize;
                    player.y = 8 * actualsize;
                }
            }
            
            pendingMapChange = null;
            changingMap = false;
        }
    }
    
    /**
     * Return to the main Eldenbrook map
     */
    private void returnToMainMap() {
        // Store exit position before switching maps
        Point exitPos = exitPositions.get(currentMap);
        
        // Load main map
        currentMap = "maps/eldenbrook.txt";
        tileManager.loadMap(currentMap);
        
        // Position player at the exit point
        if (exitPos != null) {
            player.x = exitPos.x;
            player.y = exitPos.y;
        } else {
            // Default position if no exit position defined
            player.x = 15 * actualsize;
            player.y = 15 * actualsize;
        }
    }
    
    /**
     * Set reference to the main game
     */
    public void setMainGame(game mainGame) {
        this.mainGame = mainGame;
    }
    
    /**
     * Return to the main menu
     */
    public void returnToMainMenu() {
        if (mainGame != null) {
            stopGameThread();
            mainGame.returnToMenu();
        }
    }
    
    /**
     * Stop the game thread safely
     */
    public void stopGameThread() {
        gameRunning = false;
        try {
            if (gameThread != null) {
                gameThread.join(1000); // Wait up to 1 second for thread to stop
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameThread = null;
    }
    
    /**
     * Centers the camera on the player
     */
    public void updateCameraPosition() {
        // Calculate the center of the screen
        int screenCenterX = screenwidth / 2;
        int screenCenterY = screenheight / 2;
        
        // Calculate camera position to center player
        cameraX = player.x - screenCenterX;
        cameraY = player.y - screenCenterY;
        
        // Apply camera bounds to prevent showing outside the map
        int maxCameraX = tileManager.mapWidth - screenwidth;
        int maxCameraY = tileManager.mapHeight - screenheight;
        
        if (cameraX < 0) {
            cameraX = 0;
        }
        if (cameraY < 0) {
            cameraY = 0;
        }
        if (cameraX > maxCameraX) {
            cameraX = maxCameraX;
        }
        if (cameraY > maxCameraY) {
            cameraY = maxCameraY;
        }
    }
    
    public void startGameThread(){
       gameRunning = true;
       gameThread = new Thread(this);
       gameThread.start();
       
       // Request focus to ensure keyboard input works
       this.requestFocus();
    }
 
    public void update(){
      // Process pending map change if any
      if (changingMap) {
          processMapChange();
      }
      
      // Update player position
      player.update();
      
      // Update camera position to center on player
      updateCameraPosition();
      
      // Check for escape key to return to menu or exit structure
      if(c.escapePressed) {
          if (currentMap.equals("maps/eldenbrook.txt")) {
              // On main map, return to main menu
              returnToMainMenu();
          } else {
              // Inside a structure, exit to main map
              requestMapChange("exit");
          }
          c.escapePressed = false;
      }
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // Set appropriate rendering hints for pixel art games
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        
        // Use a buffer for more efficient rendering
        BufferedImage buffer = new BufferedImage(screenwidth, screenheight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferG2 = buffer.createGraphics();
        
        // Set same rendering hints for buffer
        bufferG2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        bufferG2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        
        // Draw sky/background
        drawBackground(bufferG2);
        
        // Draw tiles to buffer (background) with camera offset
        tileManager.draw(bufferG2, cameraX, cameraY);
        
        // Then draw player (foreground) relative to camera
        player.draw(bufferG2, cameraX, cameraY);
        
        // Add visual effects like lighting if needed
        addVisualEffects(bufferG2);
        
        // Draw the buffer to the screen
        g2.drawImage(buffer, 0, 0, null);
        
        // Clean up
        bufferG2.dispose();
        g2.dispose();
    }
    
    /**
     * Draws the background sky or global background elements
     */
    private void drawBackground(Graphics2D g2) {
        // Base sky gradient
        GradientPaint skyGradient = new GradientPaint(
            0, 0, new Color(135, 206, 235), // Sky blue at top
            0, screenheight, new Color(225, 225, 255) // Lighter blue/white at bottom
        );
        g2.setPaint(skyGradient);
        g2.fillRect(0, 0, screenwidth, screenheight);
        
        // This will be mostly covered by the game tiles but shows at edges
        // and creates a more professional look if the map doesn't fill the screen
    }
    
    /**
     * Adds global visual effects like lighting
     */
    private void addVisualEffects(Graphics2D g2) {
        // Add subtle vignette effect for more professional look
        int vignetteSize = 100; // How far from edge the darkening begins
        
        // Create a radial gradient for vignette
        RadialGradientPaint vignette = new RadialGradientPaint(
            new Point(screenwidth/2, screenheight/2),
            (float)Math.max(screenwidth, screenheight)/1.5f,
            new float[]{0.0f, 0.85f, 1.0f},
            new Color[]{
                new Color(0,0,0,0), // Transparent in center
                new Color(0,0,0,0), // Transparent near edges
                new Color(0,0,0,70) // Slightly dark at edges
            }
        );
        
        // Apply vignette
        Composite originalComposite = g2.getComposite();
        g2.setPaint(vignette);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRect(0, 0, screenwidth, screenheight);
        g2.setComposite(originalComposite);
    }

  //method 1
    @Override
    public void run(){
        // FPS and UPS (updates per second) settings
        double FPS = 60.0;
        double drawInterval = 1000000000 / FPS; // 0.0166 seconds in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        int frameSkip = 0;
        
        // For performance monitoring
        long lastFpsTime = System.currentTimeMillis();
        int fps = 0;
        
        // Game loop
        while(gameThread != null && gameRunning){
            currentTime = System.nanoTime();
            
            delta = ((currentTime - lastTime) / drawInterval) + delta;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            // Update game logic at steady rate
            if(delta >= 1){
                // Update game state
                update();
                delta--;
                frameSkip = 0; // Reset frame skip counter after successful update
                
                // Count FPS
                fps++;
            } else if (frameSkip > 5) {
                // Force render if too many frames were skipped
                repaint();
                frameSkip = 0;
            } else {
                frameSkip++; // Count skipped frames
            }
            
            // Render at appropriate times
            if (timer >= drawInterval) {
                repaint();
                drawCount++;
                timer = 0;
            }
            
            // Print FPS every second for debugging
            if (System.currentTimeMillis() - lastFpsTime >= 1000) {
                // System.out.println("FPS: " + fps);
                fps = 0;
                lastFpsTime = System.currentTimeMillis();
            }
            
            // Add a small delay to prevent CPU overuse
            try {
                long sleepTime = (long)((drawInterval - (System.nanoTime() - currentTime)) / 1000000);
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } 
        
        
        
        
    //method 2
    //   @Override
    /*
    public void run(){
        double drawInterval =1000000000/fps;//0.0166 sec
        double nextdrawtime=System.nanoTime()+drawInterval;
        while(gameThread != null){
            System.out.println("The game loop is running");
            long currentTime =System.nanoTime();
            System.out.println("Current Time "+currentTime);
            // update 
            update();
            //draw
            repaint();
            try{
                double remainingtime=nextdrawtime-System.nanoTime();
                remainingtime=remainingtime/1000000;
                
                if (remainingtime<0) {
                    remainingtime=0;
                    
                }
                
                Thread.sleep((long) remainingtime);
                nextdrawtime+=drawInterval;
            }
            catch(InterruptedException e){
                e.printStackTrace();
                
            }
        }
    
        */
    
}
    
    
    
    

    

