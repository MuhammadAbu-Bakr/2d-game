import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.RenderingHints;

/**
 * Manages the tiles and map for the game.
 * @author AbuBakar
 */
public class TileManager {
    
    Game_Panel gp;
    public Tile[] tiles;
    public int[][] mapTileNum;
    
    // Map bounds
    public int mapWidth;
    public int mapHeight;
    
    // Add current map field if it doesn't exist
    private String currentMap = "maps/map.txt";
    
    /**
     * Creates a new TileManager for the specified game panel.
     * @param gp The game panel to manage tiles for
     */
    public TileManager(Game_Panel gp) {
        this.gp = gp;
        tiles = new Tile[30]; // Support for up to 30 different tile types
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // Using world size instead of screen size
        
        // Calculate the map dimensions in pixels
        mapWidth = gp.maxWorldCol * gp.actualsize;
        mapHeight = gp.maxWorldRow * gp.actualsize;
        
        loadTiles();
        loadMap("maps/eldenbrook.txt"); // Load Eldenbrook map
    }
    
    /**
     * Loads all tile images into memory.
     */
    public void loadTiles() {
        try {
            // Basic tiles
            // 0 = grass (walkable)
            tiles[0] = new Tile(loadImage("Tiles/grass.png"), false);
            
            // 1 = wall (collision)
            tiles[1] = new Tile(loadImage("Tiles/wall.png"), true);
            
            // 2 = water (collision)
            tiles[2] = new Tile(loadImage("Tiles/water.png"), true);
            
            // 3 = sand (walkable)
            tiles[3] = new Tile(loadImage("Tiles/sand.png"), false);
            
            // 4 = tree (collision)
            tiles[4] = new Tile(loadImage("Tiles/tree.png"), true);
            
            // 5 = eldenbrook grass with flowers (walkable)
            tiles[5] = new Tile(loadImage("Tiles/eldenbrook_grass.png"), false);
            
            // 6 = eldenbrook path (walkable)
            tiles[6] = new Tile(loadImage("Tiles/eldenbrook_path.png"), false);
            
            // 7 = eldenbrook house (collision)
            tiles[7] = new Tile(loadHighResImage("Tiles/eldenbrook_house.png"), true);
            
            // 8 = eldenbrook tree (collision)
            tiles[8] = new Tile(loadHighResImage("Tiles/eldenbrook_tree.png"), true);
            
            // 9 = eldenbrook cliff (collision)
            tiles[9] = new Tile(loadImage("Tiles/eldenbrook_cliff.png"), true);
            
            // 10 = eldenbrook water (collision)
            tiles[10] = new Tile(loadImage("Tiles/eldenbrook_water.png"), true);
            
            // 11 = eldenbrook ward stone (collision)
            tiles[11] = new Tile(loadHighResImage("Tiles/eldenbrook_wardstone.png"), true);
            
            // New important location tiles - these will be kept but not used in the map yet
            // 12 = Kael's House (temporarily non-collision for testing)
            tiles[12] = new Tile(loadHighResImage("Tiles/kaels_house.png"), false);
            
            // 13 = Village Square tiles (walkable)
            tiles[13] = new Tile(loadHighResImage("Tiles/village_square.png"), false);
            
            // 14 = Ward Stone Circle in Village Square (walkable, but triggers event)
            tiles[14] = new Tile(loadHighResImage("Tiles/ward_stone_circle.png"), false);
            
            // 15 = Temple Ruin (collision for walls, walkable for floor)
            tiles[15] = new Tile(loadHighResImage("Tiles/temple_ruin.png"), true);
            
            // 16 = Forest Edge (walkable)
            tiles[16] = new Tile(loadImage("Tiles/forest_edge.png"), false);
            
            // 17 = Cave Entrance (collision but can be entered through interaction)
            tiles[17] = new Tile(loadHighResImage("Tiles/cave.png"), true);
            
            // 18 = Noticeboard (collision)
            tiles[18] = new Tile(loadHighResImage("Tiles/noticeboard.png"), true);
            
            // 19 = Chest (collision, but can be opened through interaction)
            tiles[19] = new Tile(loadHighResImage("Tiles/chest.png"), true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Helper method to load a standard resolution image file.
     */
    private BufferedImage loadImage(String path) {
        try {
            String fullPath = "D:\\Projects\\2d game\\" + path;
            File imgFile = new File(fullPath);
            if (imgFile.exists()) {
                System.out.println("✅ Loaded tile: " + path);
                return applyPixelArtFilters(ImageIO.read(imgFile));
            } else {
                System.out.println("❌ Tile image not found: " + fullPath);
                // Create a default colored tile as fallback
                BufferedImage defaultTile = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = defaultTile.createGraphics();
                g2.fillRect(0, 0, 16, 16);
                g2.dispose();
                return defaultTile;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Create a default colored tile as fallback
            BufferedImage defaultTile = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = defaultTile.createGraphics();
            g2.fillRect(0, 0, 16, 16);
            g2.dispose();
            return defaultTile;
        }
    }
    
    /**
     * Helper method to load and enhance a high resolution image file.
     * This scales up the image quality for important structures.
     */
    private BufferedImage loadHighResImage(String path) {
        try {
            String fullPath = "D:\\Projects\\2d game\\" + path;
            File imgFile = new File(fullPath);
            if (imgFile.exists()) {
                System.out.println("✅ Loaded high-res tile: " + path);
                // Load the original image
                BufferedImage originalImg = ImageIO.read(imgFile);
                
                // Create a larger image with better quality
                BufferedImage enhancedImg = new BufferedImage(
                    originalImg.getWidth(), 
                    originalImg.getHeight(), 
                    BufferedImage.TYPE_INT_ARGB
                );
                
                // Draw the original image with better rendering hints
                Graphics2D g2 = enhancedImg.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                g2.drawImage(originalImg, 0, 0, originalImg.getWidth(), originalImg.getHeight(), null);
                g2.dispose();
                
                return applyPixelArtFilters(enhancedImg);
            } else {
                System.out.println("❌ High-res tile image not found: " + fullPath);
                // Create a default colored tile as fallback
                BufferedImage defaultTile = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = defaultTile.createGraphics();
                g2.fillRect(0, 0, 16, 16);
                g2.dispose();
                return defaultTile;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Create a default colored tile as fallback
            BufferedImage defaultTile = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = defaultTile.createGraphics();
            g2.fillRect(0, 0, 16, 16);
            g2.dispose();
            return defaultTile;
        }
    }
    
    /**
     * Applies pixel art-specific filters and enhancements to maintain the crisp pixel art style
     */
    private BufferedImage applyPixelArtFilters(BufferedImage input) {
        // For pixel art, we want to preserve the crispness of the pixels
        // while still allowing for some color enhancement
        
        int width = input.getWidth();
        int height = input.getHeight();
        
        // Create output image with same dimensions
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = input.getRGB(x, y);
                
                // Extract color components
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                
                // Slightly enhance contrast and saturation for modern pixel art look
                // but maintain the original pixel edges
                if (alpha > 0) {  // Only process visible pixels
                    // Enhance colors slightly to match modern pixel art styles
                    red = Math.min(255, (int)(red * 1.05));
                    green = Math.min(255, (int)(green * 1.05));
                    blue = Math.min(255, (int)(blue * 1.05));
                    
                    // Ensure full opacity for non-transparent pixels to maintain crisp edges
                    if (alpha > 200) alpha = 255;
                }
                
                // Recombine color components
                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                output.setRGB(x, y, newRgb);
            }
        }
        
        return output;
    }
    
    /**
     * Loads a map from a text file.
     * @param filePath The path to the map file
     */
    public void loadMap(String filePath) {
        currentMap = filePath;
        try {
            String fullPath = "D:\\Projects\\2d game\\" + filePath;
            File file = new File(fullPath);
            
            if (!file.exists()) {
                System.out.println("❌ Map file not found: " + fullPath);
                createEldenbrookMap(); // Create Eldenbrook map
                return;
            }
            
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            int col = 0;
            int row = 0;
            
            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                
                if (line == null) {
                    break; // End of file
                }
                
                String[] numbers = line.split(" ");
                
                while (col < gp.maxWorldCol && col < numbers.length) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            
            br.close();
            System.out.println("✅ Map loaded successfully: " + filePath);
            
        } catch (Exception e) {
            System.out.println("❌ Error loading map: " + e.getMessage());
            e.printStackTrace();
            createEldenbrookMap(); // Create Eldenbrook map
        }
    }
    
    /**
     * Creates the Eldenbrook map.
     */
    private void createEldenbrookMap() {
        System.out.println("⚠️ Creating Eldenbrook - The Forgotten Edge map...");
        
        // First, fill everything with grass
        for (int i = 0; i < gp.maxWorldCol; i++) {
            for (int j = 0; j < gp.maxWorldRow; j++) {
                mapTileNum[i][j] = 5; // Eldenbrook grass by default
            }
        }
        
        // Add map border with walls/cliffs
        for (int i = 0; i < gp.maxWorldCol; i++) {
            mapTileNum[i][0] = 1; // Top border
            mapTileNum[i][gp.maxWorldRow-1] = 1; // Bottom border
        }
        
        for (int j = 0; j < gp.maxWorldRow; j++) {
            mapTileNum[0][j] = 1; // Left border
            mapTileNum[gp.maxWorldCol-1][j] = 1; // Right border
        }
        
        // East cliff edge overlooking the sea
        int cliffStartX = 35;
        for (int j = 5; j < gp.maxWorldRow - 5; j++) {
            for (int i = cliffStartX; i < cliffStartX + 3; i++) {
                mapTileNum[i][j] = 9; // Cliff
            }
            // Add water beyond the cliff
            for (int i = cliffStartX + 3; i < cliffStartX + 8; i++) {
                if (i < gp.maxWorldCol) {
                    mapTileNum[i][j] = 10; // Water
                }
            }
        }
        
        // Add a river flowing through the map
        createRiver();
        
        // Village center (create a wider area for the village)
        int villageStartX = 10;
        int villageStartY = 10;
        int villageWidth = 20;
        int villageHeight = 20;
        
        // Create flower patterns around the village
        createFlowerPatterns(villageStartX, villageStartY, villageWidth, villageHeight);
        
        // Create the Eldenbrook village in the center of the map
        // Main paths through the village
        createVillagePaths(villageStartX, villageStartY, villageWidth, villageHeight);
        
        // 1. Kael's House (Starting Location)
        int kaelsHouseX = villageStartX + 5;
        int kaelsHouseY = villageStartY + 5;
        placeKaelsHouse(kaelsHouseX, kaelsHouseY);
        
        // 2. Village Square with ward stone circle
        int squareX = villageStartX + 12;
        int squareY = villageStartY + 12;
        placeVillageSquare(squareX, squareY);
        
        // 3. Old Temple Ruin (just outside the village)
        int templeX = villageStartX + 25;
        int templeY = villageStartY + 8;
        placeTempleRuin(templeX, templeY);
        
        // 4. Forest Edge with cave
        int forestX = villageStartX + 3;
        int forestY = villageStartY + 22;
        placeForestEdge(forestX, forestY);
        
        // Add houses and ward stones throughout the village
        placeVillageHouses(villageStartX, villageStartY);
        
        // Add trees throughout the map
        addTreesThroughoutMap();
        
        // Create map file for future use
        saveEldenbrookMap();
    }
    
    /**
     * Creates a river flowing through the map
     */
    private void createRiver() {
        // Create a winding river from the north to the south
        int riverX = 30;
        
        for (int j = 1; j < gp.maxWorldRow - 1; j++) {
            // Make the river wind
            if (j % 10 < 5) {
                riverX += (j % 3 == 0) ? 1 : 0;
            } else {
                riverX -= (j % 3 == 0) ? 1 : 0;
            }
            
            // Keep river within bounds
            if (riverX < 5) riverX = 5;
            if (riverX > gp.maxWorldCol - 5) riverX = gp.maxWorldCol - 5;
            
            // Create river width (3 tiles wide)
            for (int i = riverX - 1; i <= riverX + 1; i++) {
                if (i > 0 && i < gp.maxWorldCol - 1 && i < 35) { // Don't overlap with the eastern cliff
                    mapTileNum[i][j] = 10; // Water
                }
            }
            
            // Add some sand at the river edges
            if (j % 5 == 0) {
                if (riverX - 2 > 0 && riverX - 2 < gp.maxWorldCol - 1) {
                    mapTileNum[riverX - 2][j] = 3; // Sand
                }
                if (riverX + 2 > 0 && riverX + 2 < gp.maxWorldCol - 1) {
                    mapTileNum[riverX + 2][j] = 3; // Sand
                }
            }
        }
        
        // Add a small bridge over the river
        int bridgeY = 20;
        if (riverX > 5 && riverX < gp.maxWorldCol - 5) {
            mapTileNum[riverX - 1][bridgeY] = 6; // Path (bridge)
            mapTileNum[riverX][bridgeY] = 6; // Path (bridge)
            mapTileNum[riverX + 1][bridgeY] = 6; // Path (bridge)
            
            // Connect bridge to nearest path
            for (int i = riverX + 2; i < riverX + 5; i++) {
                mapTileNum[i][bridgeY] = 6; // Path
            }
        }
    }
    
    /**
     * Creates flower patterns around the village
     */
    private void createFlowerPatterns(int startX, int startY, int width, int height) {
        // Create circular flower patterns in the corners of the map
        createFlowerCircle(5, 5, 3);
        createFlowerCircle(gp.maxWorldCol - 10, 5, 4);
        createFlowerCircle(5, gp.maxWorldRow - 10, 4);
        createFlowerCircle(gp.maxWorldCol - 15, gp.maxWorldRow - 15, 5);
        
        // Create a flower path leading to temple
        for (int i = startX + width + 6; i < startX + width + 12; i += 2) {
            for (int j = startY + 6; j < startY + 8; j++) {
                if ((i + j) % 3 == 0) {
                    mapTileNum[i][j] = 5; // Flower grass
                }
            }
        }
    }
    
    /**
     * Creates a circular flower pattern
     */
    private void createFlowerCircle(int centerX, int centerY, int radius) {
        for (int i = centerX - radius; i <= centerX + radius; i++) {
            for (int j = centerY - radius; j <= centerY + radius; j++) {
                // Check if within bounds
                if (i > 1 && i < gp.maxWorldCol - 2 && j > 1 && j < gp.maxWorldRow - 2) {
                    // Calculate distance from center
                    double distance = Math.sqrt(Math.pow(i - centerX, 2) + Math.pow(j - centerY, 2));
                    
                    // Create a circular pattern
                    if (distance <= radius) {
                        // Use either decorated grass or normal grass in a pattern
                        if ((i + j) % 3 == 0) {
                            mapTileNum[i][j] = 5; // Decorated grass
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Creates the network of paths through Eldenbrook village
     */
    private void createVillagePaths(int startX, int startY, int width, int height) {
        // Main east-west path
        for (int i = startX + 1; i < startX + width - 1; i++) {
            mapTileNum[i][startY + height/2] = 6; // Horizontal path
        }
        
        // Main north-south path
        for (int j = startY + 1; j < startY + height - 1; j++) {
            mapTileNum[startX + width/2][j] = 6; // Vertical path
        }
        
        // Path to Kael's house - curved path
        int kaelPathX = startX + 5;
        for (int j = startY + 5; j < startY + height/2; j++) {
            // Create winding path
            if (j < startY + 7) {
                mapTileNum[kaelPathX][j] = 6; // Straight part
            } else if (j < startY + 9) {
                mapTileNum[kaelPathX + 1][j] = 6; // Curve right
            } else {
                mapTileNum[kaelPathX + 2][j] = 6; // Further right
            }
        }
        
        // Connect the curved path to the main path
        for (int i = kaelPathX + 2; i < startX + width/2; i++) {
            mapTileNum[i][startY + height/2 - 1] = 6;
        }
        
        // Path to temple ruin - add some curves
        int templeY = startY + 8;
        for (int i = startX + width/2; i < startX + width + 5; i++) {
            // Add slight curves to the path
            if (i < startX + width/2 + 3) {
                mapTileNum[i][templeY] = 6; // Straight part
            } else if (i < startX + width/2 + 6) {
                mapTileNum[i][templeY - 1] = 6; // Curve up
            } else {
                mapTileNum[i][templeY - 2] = 6; // Further up
            }
        }
        
        // Path to forest edge - winding path
        int forestX = startX + 3;
        for (int j = startY + height/2; j < startY + height + 3; j++) {
            // Create winding path
            if (j < startY + height/2 + 3) {
                mapTileNum[forestX][j] = 6; // Straight part
            } else if (j < startY + height/2 + 5) {
                mapTileNum[forestX + 1][j] = 6; // Curve right
            } else {
                mapTileNum[forestX + 2][j] = 6; // Further right
            }
        }
        
        // Create a circular path around the village square
        int squareX = startX + 12;
        int squareY = startY + 12;
        
        // Top path of the square
        for (int i = squareX - 2; i <= squareX + 2; i++) {
            mapTileNum[i][squareY - 2] = 6;
        }
        
        // Bottom path of the square
        for (int i = squareX - 2; i <= squareX + 2; i++) {
            mapTileNum[i][squareY + 2] = 6;
        }
        
        // Left path of the square
        for (int j = squareY - 2; j <= squareY + 2; j++) {
            mapTileNum[squareX - 2][j] = 6;
        }
        
        // Right path of the square
        for (int j = squareY - 2; j <= squareY + 2; j++) {
            mapTileNum[squareX + 2][j] = 6;
        }
    }
    
    /**
     * Places Kael's House and surrounding elements
     */
    private void placeKaelsHouse(int x, int y) {
        // Kael's House (using 2x2 tiles for a larger structure)
        mapTileNum[x][y] = 12; // Kael's House - top left
        mapTileNum[x+1][y] = 12; // Kael's House - top right
        mapTileNum[x][y+1] = 12; // Kael's House - bottom left
        mapTileNum[x+1][y+1] = 12; // Kael's House - bottom right
        
        // Create a garden/yard around Kael's house
        for (int i = x-1; i <= x+2; i++) {
            for (int j = y-1; j <= y+2; j++) {
                // Skip the house itself
                if ((i == x || i == x+1) && (j == y || j == y+1)) {
                    continue;
                }
                
                // Create garden/yard around house
                if (i >= 0 && j >= 0 && i < gp.maxWorldCol && j < gp.maxWorldRow) {
                    mapTileNum[i][j] = 5; // Decorated grass
                }
            }
        }
        
        // Chest with first weapon near Kael's House
        mapTileNum[x+2][y] = 19; // Chest
        
        // Ward stone near Kael's house
        mapTileNum[x][y-1] = 11; // Ward stone
        
        // Add a path from Kael's house
        for (int j = y+2; j < y+4; j++) {
            mapTileNum[x][j] = 6; // Path leading from the house
        }
    }
    
    /**
     * Places the Village Square and surrounding elements
     */
    private void placeVillageSquare(int x, int y) {
        // Village square tiles (4x4 area)
        for (int i = x-1; i <= x+2; i++) {
            for (int j = y-1; j <= y+2; j++) {
                mapTileNum[i][j] = 13; // Village square tile
            }
        }
        
        // Ward stone circle in the center (2x2)
        mapTileNum[x][y] = 14; // Ward stone circle - top left
        mapTileNum[x+1][y] = 14; // Ward stone circle - top right
        mapTileNum[x][y+1] = 14; // Ward stone circle - bottom left
        mapTileNum[x+1][y+1] = 14; // Ward stone circle - bottom right
        
        // Noticeboard
        mapTileNum[x+3][y] = 18; // Noticeboard
        
        // Elder Rowan's house (larger 2x2 building)
        mapTileNum[x+5][y-2] = 7; // Elder's house - top left
        mapTileNum[x+6][y-2] = 7; // Elder's house - top right
        mapTileNum[x+5][y-1] = 7; // Elder's house - bottom left
        mapTileNum[x+6][y-1] = 7; // Elder's house - bottom right
        
        // Ward stone for Elder's house
        mapTileNum[x+5][y-3] = 11; // Ward stone
        
        // Mira's herb garden (special decorated grass area)
        for (int i = x-4; i <= x-2; i++) {
            for (int j = y+2; j <= y+4; j++) {
                mapTileNum[i][j] = 5; // Herb garden
            }
        }
    }
    
    /**
     * Places the Temple Ruin
     */
    private void placeTempleRuin(int x, int y) {
        // Temple Ruin (8x8 area) - larger, more impressive ruins
        for (int i = x; i < x + 8; i++) {
            for (int j = y; j < y + 8; j++) {
                // Create a more interesting pattern with some walkable areas
                if (i == x || i == x + 7 || j == y || j == y + 7) {
                    mapTileNum[i][j] = 15; // Temple ruin walls
                } else {
                    mapTileNum[i][j] = 6; // Walkable floor inside
                }
            }
        }
        
        // Add interior pillars/walls to create a more complex ruin
        // Central structure (2x2)
        mapTileNum[x + 3][y + 3] = 15; // Interior pillar
        mapTileNum[x + 4][y + 3] = 15; // Interior pillar
        mapTileNum[x + 3][y + 4] = 15; // Interior pillar
        mapTileNum[x + 4][y + 4] = 15; // Interior pillar
        
        // Additional pillars
        mapTileNum[x + 2][y + 2] = 15;
        mapTileNum[x + 5][y + 2] = 15;
        mapTileNum[x + 2][y + 5] = 15;
        mapTileNum[x + 5][y + 5] = 15;
        
        // Ancient ward stones near the temple (placed in a pattern)
        mapTileNum[x - 1][y - 1] = 11; // Ward stone
        mapTileNum[x + 8][y - 1] = 11; // Ward stone
        mapTileNum[x - 1][y + 8] = 11; // Ward stone
        mapTileNum[x + 8][y + 8] = 11; // Ward stone
        
        // Create an entrance to the temple
        mapTileNum[x + 3][y] = 6; // Entrance
        mapTileNum[x + 4][y] = 6; // Entrance
    }
    
    /**
     * Places the Forest Edge and Cave
     */
    private void placeForestEdge(int x, int y) {
        // Forest edge area (8x8) - larger, more dense forest
        for (int i = x; i < x + 8; i++) {
            for (int j = y; j < y + 8; j++) {
                // Create a gradient effect from grass to dense forest
                if (i <= x + 1 || j <= y + 1) {
                    mapTileNum[i][j] = 5; // Grass at the edge
                } else {
                    mapTileNum[i][j] = 16; // Forest edge
                }
            }
        }
        
        // Add dense trees in the forest in a more organic pattern
        // Outer ring of trees
        for (int i = x + 2; i < x + 8; i += 2) {
            mapTileNum[i][y + 2] = 8; // Trees at the edge
        }
        
        // More trees deeper in the forest
        mapTileNum[x + 3][y + 3] = 8; // Tree
        mapTileNum[x + 6][y + 3] = 8; // Tree
        mapTileNum[x + 2][y + 5] = 8; // Tree
        mapTileNum[x + 5][y + 5] = 8; // Tree
        mapTileNum[x + 3][y + 6] = 8; // Tree
        mapTileNum[x + 7][y + 6] = 8; // Tree
        
        // Cave entrance - create a small clearing around it
        mapTileNum[x + 4][y + 4] = 5; // Grass clearing 
        mapTileNum[x + 4][y + 5] = 5; // Grass clearing
        mapTileNum[x + 5][y + 4] = 5; // Grass clearing
        
        // Place cave in the center of the clearing
        mapTileNum[x + 4][y + 4] = 17; // Cave entrance
        
        // Add water elements - a small stream near the cave
        mapTileNum[x + 6][y + 5] = 10; // Water
        mapTileNum[x + 7][y + 5] = 10; // Water
        mapTileNum[x + 7][y + 4] = 10; // Water
    }
    
    /**
     * Places houses and ward stones throughout the village
     */
    private void placeVillageHouses(int startX, int startY) {
        // Villager houses scattered around
        // House 1
        mapTileNum[startX + 8][startY + 15] = 7; // House
        mapTileNum[startX + 8][startY + 14] = 11; // Ward stone
        
        // House 2
        mapTileNum[startX + 15][startY + 6] = 7; // House
        mapTileNum[startX + 15][startY + 5] = 11; // Ward stone
        
        // House 3
        mapTileNum[startX + 18][startY + 16] = 7; // House
        mapTileNum[startX + 18][startY + 15] = 11; // Ward stone
    }
    
    /**
     * Adds trees throughout the map
     */
    private void addTreesThroughoutMap() {
        // Add trees - using a pseudo-random pattern
        for (int i = 2; i < gp.maxWorldCol - 2; i += 3) {
            for (int j = 2; j < gp.maxWorldRow - 2; j += 4) {
                // Skip if there's already something there
                if (mapTileNum[i][j] == 5) { // Only place on grass
                    // Vary trees with some randomness based on coordinates
                    if ((i + j) % 5 == 0) {
                        mapTileNum[i][j] = 8; // Eldenbrook tree
                    } else if ((i + j) % 7 == 0) {
                        mapTileNum[i][j] = 4; // Regular tree
                    }
                }
            }
        }
    }
    
    /**
     * Saves the Eldenbrook map to a file.
     */
    private void saveEldenbrookMap() {
        try {
            File mapsDir = new File("D:\\Projects\\2d game\\maps");
            if (!mapsDir.exists()) {
                mapsDir.mkdirs();
            }
            
            File mapFile = new File("D:\\Projects\\2d game\\maps\\eldenbrook.txt");
            java.io.PrintWriter writer = new java.io.PrintWriter(mapFile);
            
            for (int row = 0; row < gp.maxWorldRow; row++) {
                for (int col = 0; col < gp.maxWorldCol; col++) {
                    writer.print(mapTileNum[col][row]);
                    if (col < gp.maxWorldCol - 1) {
                        writer.print(" ");
                    }
                }
                writer.println();
            }
            
            writer.close();
            System.out.println("✅ Enhanced Eldenbrook map saved to file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Draws the map tiles on the screen, taking into account the camera position.
     * @param g2 The Graphics2D object to draw with
     * @param cameraX The X position of the camera
     * @param cameraY The Y position of the camera
     */
    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        // Optimize by only drawing tiles that are visible on screen
        // Calculate the tiles visible on screen
        int startCol = Math.max(0, cameraX / gp.actualsize);
        int endCol = Math.min(gp.maxWorldCol - 1, (cameraX + gp.screenwidth) / gp.actualsize + 1);
        int startRow = Math.max(0, cameraY / gp.actualsize);
        int endRow = Math.min(gp.maxWorldRow - 1, (cameraY + gp.screenheight) / gp.actualsize + 1);
        
        // Calculate the offset to ensure smooth scrolling
        int drawOffsetX = -(cameraX % gp.actualsize);
        int drawOffsetY = -(cameraY % gp.actualsize);
        
        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                // Calculate screen position of this tile
                int x = (col - startCol) * gp.actualsize + drawOffsetX;
                int y = (row - startRow) * gp.actualsize + drawOffsetY;
                
                int tileNum = mapTileNum[col][row];
                
                // Make sure we don't try to draw a non-existent tile
                if (tileNum >= 0 && tileNum < tiles.length && tiles[tileNum] != null) {
                    // Determine if this is a special structure that should be drawn larger
                    if (isLargeStructure(tileNum)) {
                        // Draw the structure at 1.5x size for important buildings
                        int largeSize = (int)(gp.actualsize * 1.5);
                        int offsetX = (largeSize - gp.actualsize) / 2;
                        int offsetY = (largeSize - gp.actualsize) / 2;
                        
                        // Apply pixel-perfect positioning for large structures
                        int screenX = col * gp.actualsize - cameraX - offsetX;
                        int screenY = row * gp.actualsize - cameraY - offsetY;
                        
                        g2.drawImage(tiles[tileNum].image, screenX, screenY, largeSize, largeSize, null);
                    } else {
                        // For normal tiles, use the pre-calculated position
                        int screenX = col * gp.actualsize - cameraX;
                        int screenY = row * gp.actualsize - cameraY;
                        
                        g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.actualsize, gp.actualsize, null);
                    }
                } else {
                    // Draw a default tile if the tile number is invalid
                    g2.setColor(java.awt.Color.black);
                    g2.fillRect(col * gp.actualsize - cameraX, row * gp.actualsize - cameraY, 
                               gp.actualsize, gp.actualsize);
                }
            }
        }
    }
    
    /**
     * Determines if a tile is a structure that should be drawn larger.
     * @param tileNum The tile number to check
     * @return True if the tile is a large structure
     */
    private boolean isLargeStructure(int tileNum) {
        // These are the important structures that should be drawn larger
        return tileNum == 7 ||   // eldenbrook_house
               tileNum == 8 ||   // eldenbrook_tree
               tileNum == 11 ||  // eldenbrook_wardstone
               tileNum == 12 ||  // kaels_house
               tileNum == 13 ||  // village_square
               tileNum == 14 ||  // ward_stone_circle
               tileNum == 15 ||  // temple_ruin
               tileNum == 17;    // cave
    }
    
    /**
     * Checks if a tile is an interactable structure that should trigger a map change.
     * @param tileNum The tile number to check
     * @return The map file to load if interactable, or null if not
     */
    public String getInteractableMap(int playerX, int playerY) {
        int tileX = playerX / gp.actualsize;
        int tileY = playerY / gp.actualsize;
        
        // Check if player is near an interactable structure entrance
        if (currentMap.equals("maps/map.txt")) {
            // Kael's House entrance
            if (tileX >= 22 && tileX <= 24 && tileY >= 17 && tileY <= 19) {
                return "maps/kaels_house.txt";
            }
            
            // Temple Ruins entrance
            if (tileX >= 36 && tileX <= 38 && tileY >= 13 && tileY <= 15) {
                return "maps/temple_ruins.txt";
            }
            
            // Forest Cave entrance
            if (tileX >= 11 && tileX <= 13 && tileY >= 32 && tileY <= 34) {
                return "maps/forest_cave.txt";
            }
        } else {
            // If inside a structure, return null for now (we'll exit via the other method)
            return null;
        }
        
        // Not near any interactable structure
        return null;
    }
    
    /**
     * Checks if the player is standing on an entrance tile.
     * @param playerX The player's world X position
     * @param playerY The player's world Y position
     * @return The map file to load if standing on an entrance, or null if not
     */
    public String checkMapTransition(int playerX, int playerY) {
        // Convert player position to tile position
        int col = playerX / gp.actualsize;
        int row = playerY / gp.actualsize;
        
        // Ensure we're within map bounds
        if (col < 0 || col >= gp.maxWorldCol || row < 0 || row >= gp.maxWorldRow) {
            return null;
        }
        
        // Get the tile at the player's position
        int tileNum = mapTileNum[col][row];
        
        // Check if it's an entrance tile
        return getInteractableMap(playerX, playerY);
    }
} 