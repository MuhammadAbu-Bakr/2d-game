import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.*;

/*
 *
 *
 *  @author AbuBakar
 * 
 * 
 */

public class game {
    // Constants for screen dimensions
    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;
    
    // UI Components
    private JFrame frame;
    private MainMenu mainMenu;
    private Game_Panel gamePanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    // Screen identifiers
    private final String MENU_SCREEN = "menu";
    private final String GAME_SCREEN = "game";
    
    /**
     * Constructor for the game
     */
    public game() {
        // Create the main window
        frame = new JFrame("Eldenbrook - The Forgotten Edge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        // Create card layout for switching between screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Create main menu
        mainMenu = new MainMenu(DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
        
        // Add components to card layout
        cardPanel.add(mainMenu, MENU_SCREEN);
        
        // Add card panel to frame
        frame.add(cardPanel);
        frame.pack();
    }
    
    /**
     * Start the game and transition from menu to gameplay
     */
    public void startGame() {
        // Create game panel if it doesn't exist
        if (gamePanel == null) {
            gamePanel = new Game_Panel(this);
            cardPanel.add(gamePanel, GAME_SCREEN);
        }
        
        // Switch to game screen
        cardLayout.show(cardPanel, GAME_SCREEN);
        
        // Start the game thread after showing the panel
        gamePanel.startGameThread();
        
        // Request focus for the game panel
        gamePanel.requestFocusInWindow();
        
        System.out.println("Game started and focus requested.");
    }
    
    /**
     * Return to the main menu from gameplay
     */
    public void returnToMenu() {
        // Stop game thread if running
        if (gamePanel != null) {
            gamePanel.stopGameThread();
        }
        
        // Switch back to menu screen
        cardLayout.show(cardPanel, MENU_SCREEN);
        
        // Request focus for the menu
        mainMenu.requestFocusInWindow();
        
        System.out.println("Returned to main menu.");
    }

    public static void main(String[] args) {
        // Create and display the game window using SwingUtilities
        SwingUtilities.invokeLater(() -> {
            game mainGame = new game();
            mainGame.frame.setVisible(true);
            
            // Ensure the menu gets focus
            mainGame.mainMenu.requestFocusInWindow();
        });
    }
}
