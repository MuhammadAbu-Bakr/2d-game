import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Creates custom textures for the Eldenbrook starting zone.
 * @author AbuBakar
 */
public class CreateEldenbrookTextures {
    
    public static void main(String[] args) {
        // Create directory if it doesn't exist
        File tilesDir = new File("D:\\Projects\\2d game\\Tiles");
        if (!tilesDir.exists()) {
            tilesDir.mkdirs();
        }
        
        // Create Eldenbrook-specific textures
        createGrassWithFlowersTexture();
        createPathTexture();
        createHouseTexture();
        createTreeTexture();
        createCliffTexture();
        createWaterTexture();
        createWardStoneTexture();
        
        System.out.println("✅ All Eldenbrook textures created successfully!");
    }
    
    private static void createGrassWithFlowersTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Base color - lush green
            g2.setColor(new Color(58, 157, 35)); // Vibrant grass green
            g2.fillRect(0, 0, 16, 16);
            
            // Add some random variations to make it look more natural
            for (int i = 0; i < 10; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                int size = 1 + (int)(Math.random() * 2);
                
                g2.setColor(new Color(85, 185, 50)); // Lighter green
                g2.fillRect(x, y, size, size);
            }
            
            // Add flowers
            // Pink flower
            g2.setColor(new Color(255, 182, 193)); // Light pink
            g2.fillRect(3, 5, 2, 2);
            
            // Yellow flower
            g2.setColor(new Color(255, 255, 0)); // Yellow
            g2.fillRect(12, 9, 2, 2);
            
            // White flower
            g2.setColor(new Color(255, 255, 255)); // White
            g2.fillRect(7, 12, 2, 2);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\eldenbrook_grass.png"));
            System.out.println("✅ Created Eldenbrook grass texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createPathTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Base color - dirt path
            g2.setColor(new Color(160, 126, 84)); // Dirt brown
            g2.fillRect(0, 0, 16, 16);
            
            // Add path details
            for (int i = 0; i < 25; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                
                if (Math.random() > 0.5) {
                    g2.setColor(new Color(180, 140, 100)); // Lighter brown
                } else {
                    g2.setColor(new Color(140, 100, 60)); // Darker brown
                }
                
                g2.fillRect(x, y, 1, 1);
            }
            
            // Add small stones
            g2.setColor(new Color(170, 170, 170)); // Stone gray
            g2.fillRect(4, 3, 2, 1);
            g2.fillRect(10, 10, 2, 1);
            g2.fillRect(7, 14, 1, 1);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\eldenbrook_path.png"));
            System.out.println("✅ Created Eldenbrook path texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createHouseTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // House wall - wooden
            g2.setColor(new Color(139, 69, 19)); // Brown
            g2.fillRect(0, 0, 16, 16);
            
            // Wall details
            g2.setColor(new Color(101, 67, 33)); // Darker brown
            g2.fillRect(0, 4, 16, 1);
            g2.fillRect(0, 12, 16, 1);
            g2.fillRect(4, 0, 1, 16);
            g2.fillRect(12, 0, 1, 16);
            
            // Window
            g2.setColor(new Color(173, 216, 230)); // Light blue
            g2.fillRect(6, 6, 4, 4);
            
            // Window frame
            g2.setColor(new Color(101, 67, 33)); // Dark brown
            g2.drawRect(6, 6, 4, 4);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\eldenbrook_house.png"));
            System.out.println("✅ Created Eldenbrook house texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createTreeTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Create transparent background (but use green for fallback)
            g2.setColor(new Color(58, 157, 35)); // Match the grass
            g2.fillRect(0, 0, 16, 16);
            
            // Tree trunk
            g2.setColor(new Color(101, 67, 33)); // Dark brown
            g2.fillRect(6, 8, 4, 8);
            
            // Tree leaves - with time distortion hint (slightly purple-tinted)
            g2.setColor(new Color(34, 139, 34)); // Forest green base
            g2.fillOval(2, 1, 12, 9);
            
            // Add purple tint to some leaves
            g2.setColor(new Color(75, 139, 120)); // Teal-tinted green
            g2.fillOval(3, 2, 5, 4);
            
            g2.setColor(new Color(100, 149, 237)); // Cornflower blue tint
            g2.fillOval(9, 3, 4, 3);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\eldenbrook_tree.png"));
            System.out.println("✅ Created Eldenbrook tree texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createCliffTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Base cliff color
            g2.setColor(new Color(105, 105, 105)); // Dim gray
            g2.fillRect(0, 0, 16, 16);
            
            // Cliff details
            g2.setColor(new Color(169, 169, 169)); // Dark gray
            g2.drawLine(0, 4, 16, 4);
            g2.drawLine(0, 8, 16, 8);
            g2.drawLine(0, 12, 16, 12);
            
            // Rock details
            g2.setColor(new Color(190, 190, 190)); // Light gray
            g2.fillRect(3, 2, 2, 1);
            g2.fillRect(10, 6, 3, 1);
            g2.fillRect(5, 10, 4, 1);
            g2.fillRect(12, 14, 2, 1);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\eldenbrook_cliff.png"));
            System.out.println("✅ Created Eldenbrook cliff texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createWaterTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Base water color - deep blue
            g2.setColor(new Color(0, 105, 148)); // Deep blue
            g2.fillRect(0, 0, 16, 16);
            
            // Add wave pattern
            g2.setColor(new Color(65, 155, 205)); // Lighter blue
            for (int i = 0; i < 16; i += 4) {
                g2.drawLine(0, i, 16, i);
            }
            
            // Add some random lighter spots to simulate light reflection
            g2.setColor(new Color(173, 216, 230)); // Light sky blue
            for (int i = 0; i < 6; i++) {
                int x = (int)(Math.random() * 14);
                int y = (int)(Math.random() * 14);
                g2.fillOval(x, y, 2, 1);
            }
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\eldenbrook_water.png"));
            System.out.println("✅ Created Eldenbrook water texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createWardStoneTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Create transparent/grass background
            g2.setColor(new Color(58, 157, 35)); // Match the grass
            g2.fillRect(0, 0, 16, 16);
            
            // Ward stone base
            g2.setColor(new Color(90, 90, 90)); // Dark gray stone
            g2.fillRect(5, 6, 6, 10);
            
            // Glowing runes
            g2.setColor(new Color(173, 255, 47)); // Bright green-yellow
            g2.fillRect(7, 8, 2, 1);
            g2.fillRect(7, 10, 2, 1);
            g2.fillRect(7, 12, 2, 1);
            
            // Glowing aura
            g2.setColor(new Color(173, 255, 47, 100)); // Semi-transparent glow
            g2.fillOval(3, 4, 10, 12);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\eldenbrook_wardstone.png"));
            System.out.println("✅ Created Eldenbrook ward stone texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 