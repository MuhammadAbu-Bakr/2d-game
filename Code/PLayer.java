/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author AbuBakar
 */
public class PLayer extends Entity{
    Controls c;

    public Controls getC() {
        return c;
    }

    public void setC(Controls c) {
        this.c = c;
    }
    Game_Panel gb;
    
    public PLayer(Game_Panel gb,Controls c){
        this.gb=gb;
        this.c=c;
        setdefaultValues();
        getplayerimage();
        loadImages();
    }
public PLayer(){
    
}

   // PLayer(Controls c) {
     //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    //}
    public void setdefaultValues(){
        x=100;
        y=100;
        speed=4; 
        direction="up";
    }
    
    
 public void loadImages() {
        up1 = loadImage("D:\\GitHub\\src\\main\\java\\com\\mycompany\\github\\resources\\p1.png");
        up2 = loadImage("D:\\GitHub\\src\\main\\java\\com\\mycompany\\github\\resources\\p2.png");
        walkImage = loadImage("D:\\GitHub\\src\\main\\java\\com\\mycompany\\github\\resources\\p1.png");
        jumpImage = loadImage("D:\\GitHub\\src\\main\\java\\com\\mycompany\\github\\resources\\p2.png");

        // Default image
        pp = up1;
    }

    private BufferedImage loadImage(String path) {
        try {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                System.out.println("✅ Loaded: " + path);
                return ImageIO.read(imgFile);
            } else {
                System.out.println("❌ Image not found: " + path);
                return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // Empty fallback
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // Prevent crash
        }
    }
    
    
    
    public void getplayerimage(){
        
            try {
            File imgFile = new File("C:\\Users\\AbuBakar\\Documents\\NetBeansProjects\\GitHub\\src\\main\\java\\com\\mycompany\\github\\resources\\p1.png");
            if (imgFile.exists()) {
            pp = ImageIO.read(imgFile);
            System.out.println("Player image loaded successfully!");
            } else {
            System.out.println("Image not found at: " + imgFile.getAbsolutePath());
            pp = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // Create empty image
            }
            } catch (IOException e) {
            e.printStackTrace();
            pp = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // Create empty image
            }    
}

    
    
    
    
    
    
    
    
    /*
    public void getplayerimage(){
        
        try {
        File imgFile = new File("C:/Users/AbuBakar/Documents/NetBeansProjects/GitHub/src/main/resources/main/pixi.png");
        if (imgFile.exists()) {
            pp = ImageIO.read(imgFile);
        } else {
            System.out.println("Image not found at: " + imgFile.getAbsolutePath());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    */
    
    public void update(){
        
        if (c.uppreassed==true|| c.downpreassed==true ||c.leftpreassed==true || c.rightpreassed==true) {
            {    
        }
          if (c.uppreassed==true) {
              direction="up";
            y -=speed;
            
        }
        else if (c.downpreassed==true) {
            direction="down";
            y +=speed;
            
        }
        else if (c.leftpreassed==true) {
            direction="left";
            x-=speed;
        }
        else if (c.rightpreassed==true) {
            direction="right";
            x+=speed;
        }
          sprintcounter++;
          if (sprintcounter>12){
              if(sprintnum==1 ){
                  sprintnum=2;
              }
              else if (sprintnum==2) {
                  sprintnum=1;
              }
              sprintcounter=0;
              
          }
        }
    }
    /*
    public void draw(Graphics2D g2){
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gb.actualsize, gb.actualsize);

        BufferedImage image =pp;
        switch(direction){
            case"up":
                image=pp;
                break;
            case"down":
                image=pp;
                break;
            case"left":
                image=pp;
                break;                    
            case"right":
                image=pp;
                break;           
            default:
                image=pp;
                break;
        }
        g2.drawImage(image, x, y,gb.actualsize, gb.actualsize,null);
    } 
*/
    public void draw(Graphics2D g2){
    BufferedImage image = pp;

    if (direction != null) {
        switch(direction){
            case "up":
                if (sprintnum==1) {
                    image = up1;
                }
                if (sprintnum==2) {
                    image = up2;
                }                
                break;  
            case "down":
                if (sprintnum==1) {
                    image = up1;
                }
                if (sprintnum==2) {
                    image = up2;
                }                
                break;
            case "left":
                if (sprintnum==1) {
                    image = up1;
                }
                if (sprintnum==2) {
                    image = up2;
                }
                break;
            case "right":
                if (sprintnum==1) {
                    image = up1;
                }
                if (sprintnum==2) {
                    image = up2;
                }                break;
            default:
                if (sprintnum==1) {
                    image = up1;
                }
                if (sprintnum==2) {
                    image = up2;
                }
                break;
        }
    }

    g2.drawImage(image, x, y, gb.actualsize, gb.actualsize, null);
}

}
