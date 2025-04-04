import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * GameLauncher - A standalone application to launch the Eldenbrook game.
 * This launcher provides a nice startup experience with splash screen 
 * and ensures the game window is properly centered on the screen.
 * 
 * @author Claude
 */
public class GameLauncher extends JFrame {
    
    // UI Components
    private JPanel mainPanel;
    private JButton playButton;
    private JButton optionsButton;
    private JButton exitButton;
    private JLabel titleLabel;
    private JLabel imageLabel;
    
    // Window dimensions
    private static final int LAUNCHER_WIDTH = 800;
    private static final int LAUNCHER_HEIGHT = 600;
    
    /**
     * Constructor for the game launcher
     */
    public GameLauncher() {
        // Set up the launcher window
        setTitle("Eldenbrook - The Forgotten Edge Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(LAUNCHER_WIDTH, LAUNCHER_HEIGHT);
        setResizable(false);
        
        // Set up main panel with a dark theme
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Create dark gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(25, 25, 35),
                    0, getHeight(), new Color(10, 10, 15)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        
        // Create title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titleLabel = new JLabel("ELDENBROOK: THE FORGOTTEN EDGE", JLabel.CENTER);
        titleLabel.setFont(new Font("Trajan Pro", Font.BOLD, 28));
        titleLabel.setForeground(new Color(218, 165, 32)); // Gold color
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Add padding to title
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));
        
        // Create image panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        
        // Try to load an image, or use a placeholder
        try {
            File imageFile = new File("D:\\Projects\\2d game\\Character\\p1.png");
            if (imageFile.exists()) {
                BufferedImage image = ImageIO.read(imageFile);
                // Scale the image to a larger size for the launcher
                Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(scaledImage));
            } else {
                // Placeholder if image can't be found
                imageLabel = new JLabel("ELDENBROOK", JLabel.CENTER);
                imageLabel.setFont(new Font("Arial", Font.BOLD, 42));
                imageLabel.setForeground(Color.WHITE);
            }
        } catch (IOException e) {
            // Placeholder if image loading fails
            imageLabel = new JLabel("ELDENBROOK", JLabel.CENTER);
            imageLabel.setFont(new Font("Arial", Font.BOLD, 42));
            imageLabel.setForeground(Color.WHITE);
        }
        
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 50, 150));
        
        // Create stylish buttons
        playButton = createStylishButton("PLAY GAME");
        optionsButton = createStylishButton("OPTIONS");
        exitButton = createStylishButton("EXIT");
        
        // Add action listeners to buttons
        playButton.addActionListener(e -> launchGame());
        optionsButton.addActionListener(e -> showOptions());
        exitButton.addActionListener(e -> System.exit(0));
        
        // Add buttons to panel
        buttonPanel.add(playButton);
        buttonPanel.add(optionsButton);
        buttonPanel.add(exitButton);
        
        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Center the window on the screen
        centerOnScreen();
    }
    
    /**
     * Creates a stylish button with hover effects
     */
    private JButton createStylishButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 70));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(80, 80, 120));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(50, 50, 70));
            }
        });
        
        return button;
    }
    
    /**
     * Centers the window on the screen
     */
    private void centerOnScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - LAUNCHER_WIDTH) / 2;
        int y = (screenSize.height - LAUNCHER_HEIGHT) / 2;
        setLocation(x, y);
    }
    
    /**
     * Launches the actual game
     */
    private void launchGame() {
        // Show a loading message while the game starts
        JDialog loadingDialog = createLoadingDialog();
        
        // Start loading in a separate thread
        new Thread(() -> {
            try {
                // Display the loading dialog for at least 1 second
                loadingDialog.setVisible(true);
                Thread.sleep(1000);
                
                // Launch the game on the EDT
                SwingUtilities.invokeLater(() -> {
                    // Hide the launcher
                    setVisible(false);
                    
                    // Create and display the game with proper centering
                    game mainGame = new game();
                    
                    // Extra code to ensure the game window is centered
                    // This works with the existing game implementation
                    JFrame gameFrame = getGameFrame(mainGame);
                    if (gameFrame != null) {
                        // Ensure game frame is centered on screen
                        gameFrame.setLocationRelativeTo(null);
                        // Make sure it's visible after centering
                        gameFrame.setVisible(true);
                        // Ensure proper focus
                        gameFrame.requestFocus();
                    }
                    
                    // Close the loading dialog
                    loadingDialog.dispose();
                    
                    // Dispose the launcher after game is launched
                    dispose();
                });
            } catch (Exception e) {
                e.printStackTrace();
                loadingDialog.dispose();
                
                // Show error dialog if game fails to launch
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, 
                        "Failed to launch the game: " + e.getMessage(), 
                        "Launch Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        }).start();
    }
    
    /**
     * Creates a loading dialog to show while the game is starting
     */
    private JDialog createLoadingDialog() {
        JDialog dialog = new JDialog(this, "Loading Game", false);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create loading label
        JLabel loadingLabel = new JLabel("Loading Eldenbrook...");
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Create progress indicator
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        panel.add(loadingLabel, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        return dialog;
    }
    
    /**
     * Shows the options dialog
     */
    private void showOptions() {
        JOptionPane.showMessageDialog(this,
            "Game options are not yet implemented.\nCheck back in a future update!",
            "Options", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Helper method to access the game's JFrame
     */
    private JFrame getGameFrame(game mainGame) {
        // Use reflection to access the frame field
        // This allows us to center the game window properly
        try {
            java.lang.reflect.Field frameField = game.class.getDeclaredField("frame");
            frameField.setAccessible(true);
            return (JFrame) frameField.get(mainGame);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Main method to start the launcher
     */
    public static void main(String[] args) {
        // Use system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and display the launcher window
        SwingUtilities.invokeLater(() -> {
            GameLauncher launcher = new GameLauncher();
            launcher.setVisible(true);
        });
    }
} 