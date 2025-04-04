import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The main menu screen for the Eldenbrook RPG game.
 */
public class MainMenu extends JPanel {
    // Menu dimensions
    private int width;
    private int height;
    
    // Menu options
    private String[] options = {"Play", "Options", "Exit"};
    private Rectangle[] optionBounds;
    private int selectedOption = -1; // -1 means no option is selected
    
    // Background image
    private BufferedImage backgroundImage;
    private BufferedImage titleImage;
    
    // Font settings
    private Font titleFont;
    private Font menuFont;
    private Font versionFont;
    
    // Menu colors
    private Color titleColor = new Color(255, 235, 180);
    private Color menuColor = new Color(255, 255, 240);
    private Color highlightColor = new Color(255, 220, 100);
    private Color shadowColor = new Color(100, 70, 20, 180);
    
    // Game version
    private String version = "v0.1";
    
    // Reference to the main game for transitions
    private game mainGame;
    
    /**
     * Creates a new main menu with the specified dimensions
     */
    public MainMenu(int width, int height, game mainGame) {
        this.width = width;
        this.height = height;
        this.mainGame = mainGame;
        
        // Set panel properties
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        // Initialize menu items
        optionBounds = new Rectangle[options.length];
        for (int i = 0; i < options.length; i++) {
            optionBounds[i] = new Rectangle();
        }
        
        // Load fonts
        titleFont = new Font("Times New Roman", Font.BOLD, 72);
        menuFont = new Font("Arial", Font.BOLD, 32);
        versionFont = new Font("Arial", Font.PLAIN, 12);
        
        // Load background image
        loadImages();
        
        // Add mouse listener for menu interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < optionBounds.length; i++) {
                    if (optionBounds[i].contains(e.getPoint())) {
                        handleMenuSelection(i);
                        break;
                    }
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                updateSelectedOption(e.getPoint());
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                selectedOption = -1;
                repaint();
            }
        });
        
        // Add mouse motion listener for hover effects
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateSelectedOption(e.getPoint());
                repaint();
            }
        });
    }
    
    /**
     * Load menu background and title images
     */
    private void loadImages() {
        try {
            // Attempt to load custom background
            File bgFile = new File("D:\\Projects\\2d game\\images\\menu_background.png");
            if (bgFile.exists()) {
                backgroundImage = ImageIO.read(bgFile);
            } else {
                // Create a default gradient background if file doesn't exist
                backgroundImage = createDefaultBackground();
            }
            
            // Attempt to load title image
            File titleFile = new File("D:\\Projects\\2d game\\images\\title.png");
            if (titleFile.exists()) {
                titleImage = ImageIO.read(titleFile);
            }
        } catch (Exception e) {
            System.out.println("Error loading menu images: " + e.getMessage());
            backgroundImage = createDefaultBackground();
        }
    }
    
    /**
     * Creates a default background image with a gradient
     */
    private BufferedImage createDefaultBackground() {
        BufferedImage bg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bg.createGraphics();
        
        // Create a dark green to black gradient
        Color topColor = new Color(20, 40, 20);
        Color bottomColor = new Color(5, 10, 5);
        
        for (int y = 0; y < height; y++) {
            float ratio = (float)y / height;
            int r = (int)(topColor.getRed() * (1-ratio) + bottomColor.getRed() * ratio);
            int g = (int)(topColor.getGreen() * (1-ratio) + bottomColor.getGreen() * ratio);
            int b = (int)(topColor.getBlue() * (1-ratio) + bottomColor.getBlue() * ratio);
            
            g2.setColor(new Color(r, g, b));
            g2.drawLine(0, y, width, y);
        }
        g2.dispose();
        return bg;
    }
    
    /**
     * Updates which menu option is currently selected based on mouse position
     */
    private void updateSelectedOption(java.awt.Point mousePos) {
        selectedOption = -1;
        for (int i = 0; i < optionBounds.length; i++) {
            if (optionBounds[i].contains(mousePos)) {
                selectedOption = i;
                break;
            }
        }
    }
    
    /**
     * Handles menu selection based on which option was clicked
     */
    private void handleMenuSelection(int option) {
        switch (option) {
            case 0: // Play
                System.out.println("Starting game...");
                mainGame.startGame();
                break;
                
            case 1: // Options
                System.out.println("Options menu selected");
                // TODO: Show options screen
                break;
                
            case 2: // Exit
                System.out.println("Exiting game...");
                System.exit(0);
                break;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // Enable anti-aliasing for smoother text
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw background
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, width, height, null);
        }
        
        // Draw title (either image or text)
        int titleY = height / 6;
        if (titleImage != null) {
            int titleWidth = width / 2;
            int titleHeight = titleWidth / 4; // Assuming aspect ratio of 4:1
            g2.drawImage(titleImage, width/2 - titleWidth/2, titleY - titleHeight/2, 
                         titleWidth, titleHeight, null);
        } else {
            // Draw title text if no image
            g2.setFont(titleFont);
            String title = "Eldenbrook";
            int titleWidth = g2.getFontMetrics().stringWidth(title);
            
            // Draw text shadow
            g2.setColor(shadowColor);
            g2.drawString(title, width/2 - titleWidth/2 + 4, titleY + 4);
            
            // Draw text
            g2.setColor(titleColor);
            g2.drawString(title, width/2 - titleWidth/2, titleY);
        }
        
        // Draw subtitle
        g2.setFont(new Font("Arial", Font.ITALIC, 24));
        String subtitle = "The Forgotten Edge";
        int subtitleWidth = g2.getFontMetrics().stringWidth(subtitle);
        g2.setColor(shadowColor);
        g2.drawString(subtitle, width/2 - subtitleWidth/2 + 2, titleY + 60 + 2);
        g2.setColor(new Color(220, 220, 180));
        g2.drawString(subtitle, width/2 - subtitleWidth/2, titleY + 60);
        
        // Draw menu options
        g2.setFont(menuFont);
        int menuY = height / 2 + 50;
        int menuGap = 50;
        
        for (int i = 0; i < options.length; i++) {
            String option = options[i];
            int optionWidth = g2.getFontMetrics().stringWidth(option);
            int optionX = width / 2 - optionWidth / 2;
            int optionY = menuY + i * menuGap;
            
            // Update bounds for hit detection
            optionBounds[i].setBounds(optionX - 20, optionY - 30, optionWidth + 40, 40);
            
            // Draw selection highlight
            if (i == selectedOption) {
                // Draw highlight box
                g2.setColor(new Color(255, 220, 100, 70));
                g2.fillRoundRect(optionBounds[i].x, optionBounds[i].y, 
                                optionBounds[i].width, optionBounds[i].height, 10, 10);
                
                // Draw border
                g2.setColor(highlightColor);
                g2.drawRoundRect(optionBounds[i].x, optionBounds[i].y, 
                                optionBounds[i].width, optionBounds[i].height, 10, 10);
            }
            
            // Draw text shadow
            g2.setColor(shadowColor);
            g2.drawString(option, optionX + 2, optionY + 2);
            
            // Draw menu text
            g2.setColor(i == selectedOption ? highlightColor : menuColor);
            g2.drawString(option, optionX, optionY);
        }
        
        // Draw version at bottom right
        g2.setFont(versionFont);
        g2.setColor(new Color(200, 200, 200, 180));
        g2.drawString(version, width - 50, height - 20);
    }
} 