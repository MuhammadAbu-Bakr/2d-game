import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Utility class to generate simple tile textures for the game.
 * @author AbuBakar
 */
public class CreateTextures {
    
    public static void main(String[] args) {
        // Create directory if it doesn't exist
        File tilesDir = new File("D:\\Projects\\2d game\\Tiles");
        if (!tilesDir.exists()) {
            tilesDir.mkdirs();
        }
        
        // Create grass texture (green with small random variations)
        createGrassTexture();
        
        // Create wall texture (gray stones)
        createWallTexture();
        
        // Create water texture (blue)
        createWaterTexture();
        
        // Create sand texture (light brown/yellow)
        createSandTexture();
        
        // Create tree texture (brown trunk, green top)
        createTreeTexture();
        
        System.out.println("All textures created successfully!");
    }
    
    private static void createGrassTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Base color
            g2.setColor(new Color(34, 139, 34)); // Forest green
            g2.fillRect(0, 0, 16, 16);
            
            // Add some random variations to make it look more natural
            for (int i = 0; i < 20; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                int size = 1 + (int)(Math.random() * 2);
                
                // Randomly either lighter or darker spots
                if (Math.random() > 0.5) {
                    g2.setColor(new Color(50, 160, 50)); // Lighter green
                } else {
                    g2.setColor(new Color(25, 100, 25)); // Darker green
                }
                
                g2.fillRect(x, y, size, size);
            }
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\grass.png"));
            System.out.println("Created grass texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createWallTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Base color
            g2.setColor(new Color(105, 105, 105)); // Dim gray
            g2.fillRect(0, 0, 16, 16);
            
            // Add stone pattern
            g2.setColor(new Color(169, 169, 169)); // Dark gray
            
            // Draw brick pattern
            g2.drawLine(0, 4, 16, 4);
            g2.drawLine(0, 12, 16, 12);
            g2.drawLine(4, 0, 4, 4);
            g2.drawLine(12, 0, 12, 4);
            g2.drawLine(8, 4, 8, 12);
            g2.drawLine(0, 8, 8, 8);
            g2.drawLine(4, 12, 4, 16);
            g2.drawLine(12, 12, 12, 16);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\wall.png"));
            System.out.println("Created wall texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createWaterTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Base color
            g2.setColor(new Color(65, 105, 225)); // Royal blue
            g2.fillRect(0, 0, 16, 16);
            
            // Add wave pattern
            g2.setColor(new Color(30, 144, 255)); // Dodger blue
            
            // Simple wave pattern
            for (int i = 0; i < 16; i += 4) {
                g2.drawLine(0, i, 16, i);
            }
            
            // Add some random lighter spots to simulate light reflection
            g2.setColor(new Color(135, 206, 250)); // Light sky blue
            for (int i = 0; i < 5; i++) {
                int x = (int)(Math.random() * 14);
                int y = (int)(Math.random() * 14);
                g2.fillOval(x, y, 2, 2);
            }
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\water.png"));
            System.out.println("Created water texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createSandTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Base color
            g2.setColor(new Color(210, 180, 140)); // Tan
            g2.fillRect(0, 0, 16, 16);
            
            // Add some random variations
            for (int i = 0; i < 30; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                
                if (Math.random() > 0.5) {
                    g2.setColor(new Color(222, 184, 135)); // Lighter sand
                } else {
                    g2.setColor(new Color(205, 133, 63)); // Darker sand
                }
                
                g2.fillRect(x, y, 1, 1);
            }
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\sand.png"));
            System.out.println("Created sand texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createTreeTexture() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();
            
            // Background/empty space (transparent or matching grass)
            g2.setColor(new Color(34, 139, 34)); // Forest green (matching grass)
            g2.fillRect(0, 0, 16, 16);
            
            // Tree trunk
            g2.setColor(new Color(139, 69, 19)); // Saddle brown
            g2.fillRect(6, 8, 4, 8); // trunk
            
            // Tree top/leaves
            g2.setColor(new Color(0, 100, 0)); // Dark green
            g2.fillOval(2, 1, 12, 9); // main foliage
            
            // Highlights on leaves
            g2.setColor(new Color(50, 205, 50)); // Lime green
            g2.fillOval(4, 3, 3, 3);
            g2.fillOval(9, 2, 3, 3);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\tree.png"));
            System.out.println("Created tree texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 