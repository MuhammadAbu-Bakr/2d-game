import java.awt.image.BufferedImage;

/**
 * Represents a single tile in the game map.
 * @author AbuBakar
 */
public class Tile {
    public BufferedImage image;
    public boolean collision = false;
    
    /**
     * Creates a new tile with the specified image and collision property.
     * @param image The image to use for this tile
     * @param collision Whether this tile has collision (can't be walked through)
     */
    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }
} 