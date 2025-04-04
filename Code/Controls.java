import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;
/**
 *
 * @author AbuBakar
 */
public class Controls implements KeyListener, MouseListener {
    
    public boolean uppreassed, downpreassed, leftpreassed, rightpreassed, escapePressed;
    public boolean interactPressed; // E key for interactions
    public boolean runModeActive; // Shift key for run mode
    private Game_Panel gamePanel;
    
    // Mouse movement
    public boolean mouseClicked;
    public Point targetPosition;
    
    /**
     * Constructor with Game_Panel reference
     */
    public Controls(Game_Panel gamePanel) {
        this.gamePanel = gamePanel;
        this.targetPosition = new Point(0, 0);
        // Add this object as a mouse listener to the game panel
        if (gamePanel != null) {
            gamePanel.addMouseListener(this);
        }
    }
    
    /**
     * Default constructor for backward compatibility
     */
    public Controls() {
        this.gamePanel = null;
        this.targetPosition = new Point(0, 0);
    }
    
    @Override
    public void keyTyped(KeyEvent e){
      
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        System.out.println("Key pressed: " + KeyEvent.getKeyText(code) + " (code: " + code + ")");
        
        // WASD controls
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            uppreassed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downpreassed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftpreassed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightpreassed = true;  
        }
        // Menu key (ESC)
        if (code == KeyEvent.VK_ESCAPE) {
            escapePressed = true;
        }
        // Interaction key (E)
        if (code == KeyEvent.VK_E) {
            interactPressed = true;
            System.out.println("Interaction key pressed");
        }
        // Run mode (SHIFT)
        if (code == KeyEvent.VK_SHIFT) {
            runModeActive = true;
            System.out.println("Run mode activated");
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        
        // WASD controls
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            uppreassed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downpreassed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftpreassed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightpreassed = false;  
        } 
        // Menu key (ESC)
        if (code == KeyEvent.VK_ESCAPE) {
            escapePressed = false;
        }
        // Interaction key (E)
        if (code == KeyEvent.VK_E) {
            interactPressed = false;
        }
        // Run mode (SHIFT)
        if (code == KeyEvent.VK_SHIFT) {
            runModeActive = false;
            System.out.println("Run mode deactivated");
        }
    }
    
    // Mouse Listener implementation for mouse-based movement
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gamePanel != null) {
            // Convert screen coordinates to world coordinates
            int worldX = e.getX() + gamePanel.cameraX;
            int worldY = e.getY() + gamePanel.cameraY;
            
            // Set the target position for the player to move to
            targetPosition.setLocation(worldX, worldY);
            mouseClicked = true;
            
            System.out.println("Mouse clicked at: " + worldX + "," + worldY);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        // Not needed for this implementation
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        // Not needed for this implementation
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        // Not needed for this implementation
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        // Not needed for this implementation
    }
}
