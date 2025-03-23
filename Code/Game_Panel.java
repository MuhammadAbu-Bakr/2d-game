import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;



/*
 *
 * 
 * @author AbuBakar
 * 
 * 
 */

public class Game_Panel extends JPanel implements Runnable{
    // Screen Settings
    final int Finalchar_size=16;
    final int scale=3;
    final int actualsize=Finalchar_size*scale;
    final int maxscreencolumn=16;
    final int maxscreenrow=12;
    final int screenwidth=maxscreencolumn*actualsize;
    final int screenheight=maxscreenrow*actualsize;
    
    Controls c=new Controls();
   PLayer player =new PLayer(this,this.c);
    
    int playerx=100;
    int playery=100;
    int playerspeed=4;
    
    Thread gameThread;
    
    int fps=60;
    
    
    public Game_Panel(){
        this.setPreferredSize(new Dimension(screenwidth,screenheight));
        this.setDoubleBuffered(true);
        this.setBackground(Color.green);
        this.addKeyListener(c);
        this.setFocusable(true);
        
        
        
    }
    
   
    
    public void startGameThread(){
       gameThread =new Thread(this);
       gameThread.start();
    }
 
    public void update(){
      player.update();
   
        
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       player.draw(g2);
        g2.dispose();
    }

  //method 1
    @Override
    public void run(){
        
        double drawInterval =1000000000/fps;//0.0166 sec
        double delta = 0;
        long lastTime =System.nanoTime();
        long currentTime;
        long timer =0;
        int drawcount=0;
        
        
        
        
       
        while(gameThread != null){
           
            //System.out.println("The game loop is running");
            
            currentTime=System.nanoTime();
            
            delta =((currentTime-lastTime)/drawInterval) + delta;
            lastTime=currentTime; 
            timer +=(currentTime-lastTime);
            
            if(delta>=1){
                // update 
                update();
                //draw
                repaint();
                delta--;
            }
        }
    } 
        
        
        
        
    //method 2
    //   @Override
    /*
    public void run(){
        double drawInterval =1000000000/fps;//0.0166 sec
        double nextdrawtime=System.nanoTime()+drawInterval;
        while(gameThread != null){
            System.out.println("The game loop is running");
            long currentTime =System.nanoTime();
            System.out.println("Current Time "+currentTime);
            // update 
            update();
            //draw
            repaint();
            try{
                double remainingtime=nextdrawtime-System.nanoTime();
                remainingtime=remainingtime/1000000;
                
                if (remainingtime<0) {
                    remainingtime=0;
                    
                }
                
                Thread.sleep((long) remainingtime);
                nextdrawtime+=drawInterval;
            }
            catch(InterruptedException e){
                e.printStackTrace();
                
            }
        }
    
        */
    
}
    
    
    
    

    

