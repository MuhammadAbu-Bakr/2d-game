import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Creates higher quality textures for the game.
 * @author AbuBakar
 */
public class ImprovedTextures {
    
    public static void main(String[] args) {
        // Create directory if it doesn't exist
        File tilesDir = new File("D:\\Projects\\2d game\\Tiles");
        if (!tilesDir.exists()) {
            tilesDir.mkdirs();
        }
        
        // Create improved Eldenbrook textures
        createImprovedGrass();
        createImprovedPath();
        
        // Create important location textures
        createKaelsHouse();
        createVillageSquare();
        createWardStoneCircle();
        createTempleRuin();
        createForestEdge();
        createCave();
        createNoticeboard();
        createChest();
        
        System.out.println("✅ All improved textures created successfully!");
    }
    
    private static void createImprovedGrass() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Base color with gradient
            GradientPaint grassGradient = new GradientPaint(
                0, 0, new Color(58, 157, 35), 
                16, 16, new Color(75, 170, 45)
            );
            g2.setPaint(grassGradient);
            g2.fillRect(0, 0, 16, 16);
            
            // Add texture details
            for (int i = 0; i < 20; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                int size = 1 + (int)(Math.random() * 2);
                
                if (Math.random() > 0.7) {
                    g2.setColor(new Color(85, 185, 50)); // Lighter green
                } else {
                    g2.setColor(new Color(45, 135, 30)); // Darker green
                }
                
                g2.fillRect(x, y, size, size);
            }
            
            // Add some grass blades
            g2.setColor(new Color(65, 175, 40));
            g2.drawLine(3, 5, 3, 2);
            g2.drawLine(10, 8, 10, 5);
            g2.drawLine(7, 12, 7, 9);
            g2.drawLine(13, 4, 13, 1);
            
            // Add flowers with better detail
            // Pink flower
            g2.setColor(new Color(255, 120, 180));
            g2.fillOval(2, 4, 2, 2);
            g2.setColor(new Color(255, 210, 220));
            g2.fillOval(2, 5, 1, 1);
            
            // Yellow flower
            g2.setColor(new Color(255, 230, 0));
            g2.fillOval(12, 9, 2, 2);
            g2.setColor(new Color(255, 255, 150));
            g2.fillOval(12, 10, 1, 1);
            
            // White flower
            g2.setColor(new Color(255, 255, 255));
            g2.fillOval(7, 12, 2, 2);
            g2.setColor(new Color(255, 255, 200));
            g2.fillOval(7, 13, 1, 1);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\improved_grass.png"));
            System.out.println("✅ Created improved grass texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createImprovedPath() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Base color with gradient
            GradientPaint pathGradient = new GradientPaint(
                0, 0, new Color(170, 140, 90), 
                16, 16, new Color(150, 120, 80)
            );
            g2.setPaint(pathGradient);
            g2.fillRect(0, 0, 16, 16);
            
            // Add texture details
            for (int i = 0; i < 30; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                
                if (Math.random() > 0.6) {
                    g2.setColor(new Color(190, 160, 110, 180)); // Lighter brown
                } else {
                    g2.setColor(new Color(130, 100, 60, 180)); // Darker brown
                }
                
                g2.fillRect(x, y, 1, 1);
            }
            
            // Add small stones with better detail
            g2.setColor(new Color(180, 180, 180));
            g2.fillOval(4, 3, 3, 2);
            g2.setColor(new Color(160, 160, 160));
            g2.fillOval(10, 10, 2, 1);
            g2.setColor(new Color(170, 170, 170));
            g2.fillOval(7, 14, 2, 1);
            
            // Add subtle path edge details
            g2.setColor(new Color(140, 110, 70, 100));
            g2.drawLine(0, 0, 15, 0);
            g2.drawLine(0, 15, 15, 15);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\improved_path.png"));
            System.out.println("✅ Created improved path texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createKaelsHouse() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // House wall - wooden with color gradient
            GradientPaint woodGradient = new GradientPaint(
                0, 0, new Color(160, 82, 45), 
                16, 16, new Color(139, 69, 19)
            );
            g2.setPaint(woodGradient);
            g2.fillRect(0, 0, 16, 16);
            
            // Wall details
            g2.setColor(new Color(101, 67, 33));
            g2.fillRect(0, 4, 16, 1);
            g2.fillRect(0, 12, 16, 1);
            g2.fillRect(4, 0, 1, 16);
            g2.fillRect(12, 0, 1, 16);
            
            // Roof (distinct for Kael's house)
            g2.setColor(new Color(80, 0, 0)); // Dark red roof
            g2.fillRect(0, 0, 16, 3);
            
            // Door
            g2.setColor(new Color(120, 60, 20));
            g2.fillRect(7, 10, 3, 6);
            g2.setColor(new Color(180, 160, 10)); // Gold doorknob
            g2.fillOval(9, 13, 1, 1);
            
            // Window
            g2.setColor(new Color(200, 230, 255)); // Light blue window
            g2.fillRect(6, 5, 5, 4);
            
            // Window frame
            g2.setColor(new Color(101, 67, 33));
            g2.drawRect(6, 5, 5, 4);
            g2.drawLine(8, 5, 8, 9); // Window divider
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\kaels_house.png"));
            System.out.println("✅ Created Kael's house texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createVillageSquare() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Stone tile base
            g2.setColor(new Color(180, 180, 180));
            g2.fillRect(0, 0, 16, 16);
            
            // Draw stone pattern
            g2.setColor(new Color(160, 160, 160));
            g2.drawLine(8, 0, 8, 16);
            g2.drawLine(0, 8, 16, 8);
            
            g2.setColor(new Color(140, 140, 140));
            g2.drawRect(2, 2, 4, 4);
            g2.drawRect(10, 2, 4, 4);
            g2.drawRect(2, 10, 4, 4);
            g2.drawRect(10, 10, 4, 4);
            
            // Add some texture
            for (int i = 0; i < 20; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                
                if (Math.random() > 0.5) {
                    g2.setColor(new Color(200, 200, 200, 100));
                } else {
                    g2.setColor(new Color(150, 150, 150, 100));
                }
                
                g2.fillRect(x, y, 1, 1);
            }
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\village_square.png"));
            System.out.println("✅ Created village square texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createWardStoneCircle() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Stone circle base (similar to village square but with special features)
            g2.setColor(new Color(170, 170, 170));
            g2.fillRect(0, 0, 16, 16);
            
            // Circle of stones
            g2.setColor(new Color(100, 100, 100));
            g2.fillOval(1, 1, 14, 14);
            
            // Inner area
            g2.setColor(new Color(190, 190, 190));
            g2.fillOval(3, 3, 10, 10);
            
            // Runes on stones (glowing)
            g2.setColor(new Color(0, 255, 100, 200)); // Bright green glow
            
            // Draw small rune symbols around the circle
            g2.fillRect(7, 1, 2, 1); // Top
            g2.fillRect(1, 7, 1, 2); // Left
            g2.fillRect(14, 7, 1, 2); // Right
            g2.fillRect(7, 14, 2, 1); // Bottom
            
            // Add central rune
            g2.fillOval(7, 7, 2, 2);
            
            // Add glow effect
            g2.setColor(new Color(100, 255, 150, 50));
            g2.fillOval(5, 5, 6, 6);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\ward_stone_circle.png"));
            System.out.println("✅ Created ward stone circle texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createTempleRuin() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Base - ancient stone
            g2.setColor(new Color(180, 175, 160));
            g2.fillRect(0, 0, 16, 16);
            
            // Cracked stone pattern
            g2.setColor(new Color(160, 155, 140));
            g2.drawLine(3, 0, 8, 16);
            g2.drawLine(10, 0, 5, 16);
            
            // Broken column elements
            g2.setColor(new Color(200, 195, 180));
            g2.fillRect(2, 3, 12, 2); // Top horizontal piece
            g2.fillRect(4, 3, 3, 10); // Left column
            g2.fillRect(10, 3, 3, 8); // Right column (shorter, broken)
            
            // Add cracks and damage
            g2.setColor(new Color(100, 95, 80));
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawLine(4, 8, 7, 8);
            g2.drawLine(11, 6, 11, 9);
            g2.drawLine(2, 3, 5, 5);
            
            // Add subtle blue glow for magical echo hint
            g2.setColor(new Color(100, 150, 255, 40));
            g2.fillOval(6, 6, 4, 4);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\temple_ruin.png"));
            System.out.println("✅ Created temple ruin texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createForestEdge() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Base - darker grass for forest floor
            g2.setColor(new Color(40, 120, 30));
            g2.fillRect(0, 0, 16, 16);
            
            // Add texture details
            for (int i = 0; i < 15; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                int size = 1 + (int)(Math.random() * 2);
                
                if (Math.random() > 0.6) {
                    g2.setColor(new Color(65, 145, 40, 180)); // Lighter green
                } else {
                    g2.setColor(new Color(30, 100, 20, 180)); // Darker green
                }
                
                g2.fillRect(x, y, size, size);
            }
            
            // Add fallen leaves
            g2.setColor(new Color(160, 120, 40));
            g2.fillOval(3, 5, 2, 1);
            g2.fillOval(8, 11, 2, 1);
            g2.setColor(new Color(130, 60, 20));
            g2.fillOval(11, 7, 2, 1);
            g2.fillOval(5, 13, 2, 1);
            
            // Add small shrubs
            g2.setColor(new Color(60, 130, 40));
            g2.fillOval(1, 2, 3, 2);
            g2.fillOval(12, 13, 3, 2);
            
            // Add subtle shadows for tree canopy above
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRect(0, 0, 16, 3);
            g2.fillRect(10, 0, 6, 9);
            g2.fillRect(0, 0, 4, 6);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\forest_edge.png"));
            System.out.println("✅ Created forest edge texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createCave() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Cave entrance - dark hole in stone
            g2.setColor(new Color(130, 120, 110)); // Rock face color
            g2.fillRect(0, 0, 16, 16);
            
            // Cave entrance hole
            g2.setColor(new Color(20, 20, 30)); // Very dark blue-black
            g2.fillOval(3, 4, 10, 10);
            
            // Rock highlights and details
            g2.setColor(new Color(150, 140, 130));
            g2.drawLine(2, 3, 5, 6);
            g2.drawLine(11, 5, 14, 3);
            g2.drawLine(7, 15, 12, 13);
            
            // Add subtle rock texture
            for (int i = 0; i < 10; i++) {
                int x = (int)(Math.random() * 16);
                int y = (int)(Math.random() * 16);
                
                if (x >= 3 && x <= 13 && y >= 4 && y <= 14) {
                    continue; // Skip the cave opening
                }
                
                if (Math.random() > 0.5) {
                    g2.setColor(new Color(145, 135, 125, 150));
                } else {
                    g2.setColor(new Color(115, 105, 95, 150));
                }
                
                g2.fillRect(x, y, 1, 1);
            }
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\cave.png"));
            System.out.println("✅ Created cave texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createNoticeboard() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Support posts
            g2.setColor(new Color(110, 70, 30)); // Brown wood
            g2.fillRect(3, 0, 2, 16);
            g2.fillRect(11, 0, 2, 16);
            
            // Board background
            g2.setColor(new Color(170, 130, 80)); // Lighter wood
            g2.fillRect(2, 3, 12, 9);
            
            // Board border
            g2.setColor(new Color(120, 80, 40));
            g2.drawRect(2, 3, 12, 9);
            
            // Papers on board
            g2.setColor(new Color(255, 250, 230)); // Off-white paper
            g2.fillRect(4, 5, 4, 3); // Left notice
            g2.fillRect(9, 4, 3, 4); // Right notice
            g2.fillRect(5, 9, 5, 2); // Bottom notice
            
            // Paper text lines (simplified)
            g2.setColor(new Color(0, 0, 0));
            g2.drawLine(5, 6, 7, 6);
            g2.drawLine(5, 7, 6, 7);
            
            g2.drawLine(10, 5, 11, 5);
            g2.drawLine(10, 6, 11, 6);
            g2.drawLine(10, 7, 11, 7);
            
            g2.drawLine(6, 10, 9, 10);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\noticeboard.png"));
            System.out.println("✅ Created noticeboard texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createChest() {
        try {
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Chest base
            g2.setColor(new Color(140, 90, 40)); // Brown wood
            g2.fillRect(3, 6, 10, 6);
            
            // Chest lid
            g2.setColor(new Color(120, 70, 30)); // Darker wood
            g2.fillRect(3, 4, 10, 2);
            
            // Metal reinforcements
            g2.setColor(new Color(180, 160, 120)); // Bronze metal
            g2.fillRect(3, 6, 10, 1); // Top band
            g2.fillRect(3, 11, 10, 1); // Bottom band
            g2.fillRect(3, 6, 1, 6); // Left band
            g2.fillRect(12, 6, 1, 6); // Right band
            
            // Lock
            g2.setColor(new Color(200, 180, 50)); // Gold
            g2.fillRect(7, 7, 2, 2);
            g2.setColor(new Color(160, 140, 40)); // Darker gold
            g2.fillRect(8, 7, 1, 1);
            
            // Highlight
            g2.setColor(new Color(255, 255, 255, 50));
            g2.fillRect(4, 7, 2, 1);
            g2.fillRect(4, 9, 1, 1);
            
            g2.dispose();
            ImageIO.write(img, "png", new File("D:\\Projects\\2d game\\Tiles\\chest.png"));
            System.out.println("✅ Created chest texture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 