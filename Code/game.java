
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
/*
 *
 *
 *  @author AbuBakar
 * 
 * 
 */



public class game {



    public static void main(String[] args) {
        // Create a JFrame (window)
        JFrame frame = new JFrame("Simple Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 300);
        frame.setTitle("hello");
        frame.setBackground(Color.yellow);
        Game_Panel game_panel=new Game_Panel();
        frame.add(game_panel);
        frame.pack();
        game_panel.startGameThread();
        
        
        
        
        
        
        
        
        
        
        frame.setVisible(true);
    }
}
