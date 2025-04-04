import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author AbuBakar
 */
public class PLayer extends Entity{
    Controls c;
    
    // Collision
    public Rectangle solidArea;
    public boolean collisionOn = false;
    
    // Movement
    private final int NORMAL_SPEED = 4;
    private final int SPRINT_SPEED = 8;
    private final int BUILDING_DETECTION_DISTANCE = 16; // Range to detect buildings from
    
    // Interaction
    private boolean interactionKeyPressed = false;
    private int interactCooldown = 0;
    private final int INTERACT_COOLDOWN_TIME = 20; // Frames to wait before allowing another interaction
    private long lastInteractionTime = 0;
    
    // Mouse movement
    private boolean hasTarget = false;
    private Point targetPosition = new Point(0, 0);
    private int pathfindingTolerance = 8; // How close to get to target before stopping

    public Controls getC() {
        return c;
    }

    public void setC(Controls c) {
        this.c = c;
    }
    Game_Panel gb;
    
    public PLayer(Game_Panel gb,Controls c){
        this.gb=gb;
        this.c=c;
        setdefaultValues();
        getplayerimage();
        loadImages();
        
        // Set up collision area (smaller than the tile size)
        solidArea = new Rectangle(8, 16, 32, 32); // Adjust based on your character size
    }
    
    public PLayer() {
        solidArea = new Rectangle(8, 16, 32, 32);
    }

    public void setdefaultValues(){
        // Start player outside the larger Kael's House on the path
        // Position adjusted for the new 2x2 house layout
        x = (10 + 5) * gb.actualsize; // villageStartX + 5
        y = (10 + 7) * gb.actualsize; // villageStartY + 7 (two tiles below the 2x2 Kael's House)
        speed = NORMAL_SPEED; 
        direction = "down";
    }
    
    
    public void loadImages() {
        up1 = loadImage("D:\\Projects\\2d game\\Character\\p1.png");
        up2 = loadImage("D:\\Projects\\2d game\\Character\\p2.png");
        walkImage = loadImage("D:\\Projects\\2d game\\Character\\p1.png");
        jumpImage = loadImage("D:\\Projects\\2d game\\Character\\p2.png");

        // Default image
        pp = up1;
    }

    /**
     * Loads and applies pixel art filters to ensure high quality sprites.
     */
    private BufferedImage loadImage(String path) {
        try {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                System.out.println("✅ Loaded: " + path);
                BufferedImage original = ImageIO.read(imgFile);
                return applyPlayerPixelArtFilter(original);
            } else {
                System.out.println("❌ Image not found: " + path);
                return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // Empty fallback
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // Prevent crash
        }
    }
    
    /**
     * Applies modern pixel art styling to player sprites
     */
    private BufferedImage applyPlayerPixelArtFilter(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        
        // Create output image with same dimensions
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Process each pixel for modern pixel art style
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = input.getRGB(x, y);
                
                // Extract color components
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                
                // Apply modern pixel art adjustments
                if (alpha > 0) {
                    // Boost colors for a more vibrant character
                    red = Math.min(255, (int)(red * 1.1));
                    green = Math.min(255, (int)(green * 1.05));
                    blue = Math.min(255, (int)(blue * 1.1));
                    
                    // Ensure crisp outlines
                    if (alpha > 200) alpha = 255;
                }
                
                // Recombine colors
                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                output.setRGB(x, y, newRgb);
            }
        }
        
        return output;
    }
    
    public void getplayerimage(){
        try {
            File imgFile = new File("D:\\Projects\\2d game\\Character\\p1.png");
            if (imgFile.exists()) {
                pp = ImageIO.read(imgFile);
                System.out.println("Player image loaded successfully!");
            } else {
                System.out.println("Image not found at: " + imgFile.getAbsolutePath());
                pp = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // Create empty image
            }
        } catch (IOException e) {
            e.printStackTrace();
            pp = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // Create empty image
        }    
    }
    
    // Check tile collision
    public boolean checkTileCollision(int nextX, int nextY) {
        int col1 = (nextX + solidArea.x) / gb.actualsize;
        int col2 = (nextX + solidArea.x + solidArea.width) / gb.actualsize;
        int row1 = (nextY + solidArea.y) / gb.actualsize;
        int row2 = (nextY + solidArea.y + solidArea.height) / gb.actualsize;
        
        // Check if any of the four collision points hit a solid tile
        boolean collision = false;
        
        // Ensure we don't check outside map boundaries
        if (col1 < 0 || col2 >= gb.maxWorldCol || row1 < 0 || row2 >= gb.maxWorldRow) {
            return true; // Collide with map edges
        }
        
        // Check all four corner points
        if (isSolidTile(col1, row1) || isSolidTile(col1, row2) || 
            isSolidTile(col2, row1) || isSolidTile(col2, row2)) {
            collision = true;
        }
        
        return collision;
    }
    
    // Check if a tile is solid/collision
    private boolean isSolidTile(int col, int row) {
        int tileNum = gb.tileManager.mapTileNum[col][row];
        return gb.tileManager.tiles[tileNum] != null && gb.tileManager.tiles[tileNum].collision;
    }
    
    public void update(){
        // Update speed based on run mode
        speed = c.runModeActive ? SPRINT_SPEED : NORMAL_SPEED;
        
        // Handle interaction cooldown
        if (interactCooldown > 0) {
            interactCooldown--;
        }
        
        // Check for interaction key (E key)
        if (c.interactPressed && interactCooldown <= 0) {
            interactionKeyPressed = true;
            interactCooldown = INTERACT_COOLDOWN_TIME;
            checkInteraction();
        } else {
            interactionKeyPressed = false;
        }
        
        // Handle mouse click movement
        if (c.mouseClicked) {
            hasTarget = true;
            targetPosition.setLocation(c.targetPosition);
            c.mouseClicked = false;
        }
        
        // Priority 1: Handle mouse-click movement if there's a target
        if (hasTarget) {
            moveTowardTarget();
        }
        // Priority 2: Handle keyboard movement input
        else if (c.uppreassed || c.downpreassed || c.leftpreassed || c.rightpreassed) {
            handleKeyboardMovement();
        }
        
        // Check for automatic map transitions
        checkMapTransition();
        
        // Continuously check for nearby entrances
        checkNearbyEntrances();
    }
    
    /**
     * Handle mouse-click based movement toward a target
     */
    private void moveTowardTarget() {
        // Calculate distance to target
        int dx = targetPosition.x - x;
        int dy = targetPosition.y - y;
        double distance = Math.sqrt(dx*dx + dy*dy);
        
        // If close enough to target, stop moving
        if (distance <= pathfindingTolerance) {
            hasTarget = false;
            return;
        }
        
        // Determine direction and next position
        int nextX = x;
        int nextY = y;
        
        // Normalize the direction vector
        double length = Math.sqrt(dx*dx + dy*dy);
        double normalizedDx = dx / length;
        double normalizedDy = dy / length;
        
        // Calculate next position
        nextX = x + (int)(normalizedDx * speed);
        nextY = y + (int)(normalizedDy * speed);
        
        // Set direction based on movement vector
        if (Math.abs(normalizedDx) > Math.abs(normalizedDy)) {
            direction = normalizedDx > 0 ? "right" : "left";
        } else {
            direction = normalizedDy > 0 ? "down" : "up";
        }
        
        // Check collision before moving
        boolean collision = checkTileCollision(nextX, nextY);
        
        if (!collision) {
            x = nextX;
            y = nextY;
        } else {
            // If collision, try moving just horizontally or vertically
            // Try horizontal movement
            if (!checkTileCollision(nextX, y)) {
                x = nextX;
            } 
            // Try vertical movement
            else if (!checkTileCollision(x, nextY)) {
                y = nextY;
            } else {
                // If still stuck, cancel pathfinding
                hasTarget = false;
            }
        }
        
        // Update animation counter
        sprintcounter++;
        if (sprintcounter > 8) { // Faster animation when moving by mouse
            sprintnum = (sprintnum == 1) ? 2 : 1;
            sprintcounter = 0;
        }
    }
    
    /**
     * Handle keyboard-based movement
     */
    private void handleKeyboardMovement() {
        System.out.println("Movement key detected! Direction: " + 
                          (c.uppreassed ? "UP " : "") +
                          (c.downpreassed ? "DOWN " : "") +
                          (c.leftpreassed ? "LEFT " : "") +
                          (c.rightpreassed ? "RIGHT" : ""));
        
        // Store original position
        int oldX = x;
        int oldY = y;
        
        // Determine next position based on key press
        int nextX = x;
        int nextY = y;
        
        if (c.uppreassed) {
            direction = "up";
            nextY = y - speed;
        }
        else if (c.downpreassed) {
            direction = "down";
            nextY = y + speed;
        }
        else if (c.leftpreassed) {
            direction = "left";
            nextX = x - speed;
        }
        else if (c.rightpreassed) {
            direction = "right";
            nextX = x + speed;
        }
        
        // Check collision before moving
        boolean collision = checkTileCollision(nextX, nextY);
        System.out.println("Position: " + x + "," + y + " -> " + nextX + "," + nextY + " Collision: " + collision);
        
        if (!collision) {
            x = nextX;
            y = nextY;
        }
        
        // Update animation counter
        sprintcounter++;
        if (sprintcounter > 12) {
            sprintnum = (sprintnum == 1) ? 2 : 1;
            sprintcounter = 0;
        }
    }
    
    /**
     * Checks if the player is in front of an interactable object
     */
    private void checkInteraction() {
        // Determine the tile in front of the player based on direction
        int checkX = x;
        int checkY = y;
        
        switch (direction) {
            case "up":
                checkY = y - gb.actualsize;
                break;
            case "down":
                checkY = y + gb.actualsize;
                break;
            case "left":
                checkX = x - gb.actualsize;
                break;
            case "right":
                checkX = x + gb.actualsize;
                break;
        }
        
        // Check if the tile in front is an entrance
        if (checkMapInteraction(checkX, checkY)) {
            System.out.println("Player interacted with structure at: " + checkX + "," + checkY);
        }
    }
    
    /**
     * Checks and handles map transitions by player position
     */
    private void checkMapTransition() {
        // Check if player is standing on a transition tile
        String mapFile = gb.tileManager.checkMapTransition(x, y);
        if (mapFile != null) {
            // Trigger map transition
            gb.requestMapChange(mapFile);
        }
    }
    
    /**
     * Continuously check for nearby buildings that can be entered
     */
    private void checkNearbyEntrances() {
        // Check in all directions for nearby buildings
        int[][] directions = {
            {0, -1},  // Up
            {1, 0},   // Right
            {0, 1},   // Down
            {-1, 0}   // Left
        };
        
        for (int[] dir : directions) {
            // Check for entrances at different distances
            for (int distance = 1; distance <= BUILDING_DETECTION_DISTANCE / (gb.actualsize/4); distance++) {
                int checkX = x + dir[0] * distance * (gb.actualsize/4);
                int checkY = y + dir[1] * distance * (gb.actualsize/4);
                
                // Calculate tile coordinates
                int tileCol = checkX / gb.actualsize;
                int tileRow = checkY / gb.actualsize;
                
                // Skip if out of bounds
                if (tileCol < 0 || tileCol >= gb.maxWorldCol || tileRow < 0 || tileRow >= gb.maxWorldRow) {
                    continue;
                }
                
                // Check if it's an entrance
                String mapFile = gb.tileManager.getInteractableMap(checkX, checkY);
                if (mapFile != null) {
                    // Draw a hint on the screen to show this is an entrance (handled in the draw method)
                    break;
                }
            }
        }
    }
    
    /**
     * Checks for interaction with structures that lead to map changes
     * @return true if interaction triggered a map change
     */
    private boolean checkMapInteraction(int checkX, int checkY) {
        // Check if it's an interactable structure
        String mapFile = gb.tileManager.getInteractableMap(checkX, checkY);
        if (mapFile != null) {
            // Trigger map transition
            gb.requestMapChange(mapFile);
            return true;
        }
        
        return false;
    }
    
    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        // Draw player at screen position (adjusted for camera)
        int screenX = x - cameraX;
        int screenY = y - cameraY;
        
        BufferedImage image = pp;

        if (direction != null) {
            switch(direction) {
                case "up":
                    image = (sprintnum == 1) ? up1 : up2;
                    break;  
                case "down":
                    image = (sprintnum == 1) ? up1 : up2;
                    break;
                case "left":
                    image = (sprintnum == 1) ? up1 : up2;
                    break;
                case "right":
                    image = (sprintnum == 1) ? up1 : up2;
                    break;
                default:
                    image = up1;
                    break;
            }
        }
        
        // Save original rendering hints
        Object originalInterpolation = g2.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        
        // Apply pixel art specific rendering for player
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Draw player at larger size for better visibility
        int playerSize = (int)(gb.actualsize * 1.2); // 20% larger
        int offsetX = (playerSize - gb.actualsize) / 2;
        int offsetY = (playerSize - gb.actualsize) / 2;
        
        // Apply pixel-perfect positioning for player
        int drawX = screenX - offsetX;
        int drawY = screenY - offsetY;
        
        // Round to nearest pixel for crisp rendering
        drawX = drawX - (drawX % 2);
        drawY = drawY - (drawY % 2);
        
        g2.drawImage(image, drawX, drawY, playerSize, playerSize, null);
        
        // Restore original rendering hints
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, originalInterpolation);
        
        // Draw path to target position if mouse movement is active
        if (hasTarget) {
            g2.setColor(new Color(255, 255, 255, 100)); // Semi-transparent white
            int targetScreenX = targetPosition.x - cameraX;
            int targetScreenY = targetPosition.y - cameraY;
            g2.drawLine(screenX, screenY, targetScreenX, targetScreenY);
            g2.fillOval(targetScreenX - 5, targetScreenY - 5, 10, 10);
        }
        
        // Show visual indicator for nearby entrances
        drawEntranceIndicators(g2, cameraX, cameraY);
        
        // Debug: draw collision box
        // g2.setColor(java.awt.Color.RED);
        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }

    /**
     * Draw indicators for nearby building entrances
     */
    private void drawEntranceIndicators(Graphics2D g2, int cameraX, int cameraY) {
        // Check in all directions for nearby buildings
        int[][] directions = {
            {0, -1},  // Up
            {1, 0},   // Right
            {0, 1},   // Down
            {-1, 0}   // Left
        };
        
        for (int[] dir : directions) {
            // Check for entrances at different distances
            for (int distance = 1; distance <= BUILDING_DETECTION_DISTANCE / (gb.actualsize/4); distance++) {
                int checkX = x + dir[0] * distance * (gb.actualsize/4);
                int checkY = y + dir[1] * distance * (gb.actualsize/4);
                
                // Check if it's an entrance
                String mapFile = gb.tileManager.getInteractableMap(checkX, checkY);
                if (mapFile != null) {
                    // Draw entrance indicator
                    int screenX = checkX - cameraX;
                    int screenY = checkY - cameraY;
                    
                    // Draw a pulsing indicator
                    long currentTime = System.currentTimeMillis();
                    float pulse = (float) Math.sin(currentTime * 0.01) * 0.5f + 0.5f;
                    
                    Color doorColor = new Color(0, 1.0f, 1.0f, pulse * 0.7f);
                    g2.setColor(doorColor);
                    g2.fillOval(screenX - 10, screenY - 10, 20, 20);
                    
                    // Show "Press E" text if very close
                    if (distance <= 8) {
                        g2.setColor(Color.WHITE);
                        g2.drawString("Press E", screenX - 20, screenY - 15);
                    }
                    
                    break;
                }
            }
        }
    }

    public void checkMapChange() {
        // Only check for map interactions if enough time has passed
        if (System.currentTimeMillis() - lastInteractionTime < INTERACT_COOLDOWN_TIME) {
            return;
        }
        
        if (c.interactPressed) {
            String mapToLoad = gb.tileManager.checkMapTransition(x + solidArea.x, y + solidArea.y);
            
            if (mapToLoad != null) {
                // Request the map change
                gb.requestMapChange(mapToLoad);
                
                // Set position in the new map
                if (mapToLoad.equals("maps/kaels_house.txt")) {
                    // Position for inside Kael's house
                    x = 8 * gb.actualsize;
                    y = 14 * gb.actualsize;
                } else if (mapToLoad.equals("maps/temple_ruins.txt")) {
                    // Position for inside Temple Ruins
                    x = 25 * gb.actualsize;
                    y = 32 * gb.actualsize;
                } else if (mapToLoad.equals("maps/forest_cave.txt")) {
                    // Position for inside Forest Cave
                    x = 10 * gb.actualsize;
                    y = 10 * gb.actualsize;
                } else if (mapToLoad.equals("maps/map.txt")) {
                    // Returning to the main map, position will be set by Game_Panel
                }
                
                // Reset interaction timer
                lastInteractionTime = System.currentTimeMillis();
            }
        }
    }
}
