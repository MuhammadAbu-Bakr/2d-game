
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *
 * @author AbuBakar
 */
public class Controls implements KeyListener {
    
    public boolean uppreassed,downpreassed,leftpreassed,rightpreassed;
    @Override
    public void keyTyped(KeyEvent e){
      
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        int code =e.getKeyCode();
        if (code==KeyEvent.VK_W) {
            uppreassed=true;
        }
        if (code==KeyEvent.VK_S) {
            downpreassed=true;
        }
        if (code==KeyEvent.VK_A) {
            leftpreassed=true;
        }
        if (code==KeyEvent.VK_D) {
           rightpreassed=true;  
        }

    }
    @Override
    public void keyReleased(KeyEvent e){
        int code =e.getKeyCode();
        
        if (code==KeyEvent.VK_W) {
            uppreassed=false;
        }
        if (code==KeyEvent.VK_S) {
            downpreassed=false;
        }
        if (code==KeyEvent.VK_A) {
            leftpreassed=false;
        }
        if (code==KeyEvent.VK_D) {
           rightpreassed=false;  
        } 
    }
    
    
}
