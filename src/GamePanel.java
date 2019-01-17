import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class GamePanel extends JPanel {
 
 Resources resource;
 private String buttonPressed = "";
 
 private int lowX,lowY,highX,highY;
 private double ratio;
 
 GamePanel() {
  
 }

<<<<<<< HEAD
 public void paintComponent(Graphics g) {
  
  CameraAdjust(resource.getPlayers()); // Adjust camera values
  // Draw extra background in back here
  g.drawImage(Resources.getStages().get(0).getSprite(), lowX, lowY, highX-lowX, highY-lowY, this); // Draw image of stage
  
  for(int i = 0;i<4;i++){
   if (!resource.getCharacters().get(i).isAlive()){
    g.drawImage(resource.getCharacters().get(i).getSprites()[6], resource.getCharacters().get(i).getPosition()[0]-lowX, resource.getCharacters().get(i).getPosition()[0]-lowX, (int)(resource.getCharacters().get(i).getWidth()*ratio), (int)(resource.getCharacters().get(i).getHeight()*ratio), this);
   }
   //fix the living drawing boi for flipperino
   g.drawImage(resource.getCharacters().get(i).getActiveFrame(), resource.getCharacters().get(i).getPosition()[0]-lowX, resource.getCharacters().get(i).getPosition()[0]-lowX, (int)(resource.getCharacters().get(i).getWidth()*ratio), (int)(resource.getCharacters().get(i).getHeight()*ratio), this);
   
  }
  for (int i =0;i<resource.getCurrentStage().getItems().size();i++){
   //g.drawImage(resource.getCurrentStage().getItems().get(i).getImage(), resource.getCurrentStage().getItems().get(i).getX()-lowX, resource.getCurrentStage().getItems().get(i).getY()-lowY, (int)(resource.getCurrentStage().getItems().get(i).getWidth()*ratio), (int)(resource.getCurrentStage().getItems().get(i).getHeight()*ratio), this);
  }
 }
   
 void setResources(Resources resource){
  this.resource = resource;
 }
  
 void givePoints(Player p, int amount){
  p.setScore(p.getScore()+amount);
  
 }
 
 void death(){
  
 }
=======
	public void paintComponent(Graphics g) {
		
		CameraAdjust(resource.getPlayers()); // Adjust camera values
		// Draw extra background in back here
		g.drawImage(Resources.getStages().get(0).getSprite(), lowX, lowY, highX-lowX, highY-lowY, this); // Draw image of stage
		
		for(int i = 0;i<4;i++){
			if (!resource.getCharacters().get(i).isAlive()){
				g.drawImage(resource.getCharacters().get(i).getSprites()[6], resource.getCharacters().get(i).getPosition()[0]-lowX, resource.getCharacters().get(i).getPosition()[0]-lowX, (int)(resource.getCharacters().get(i).getWidth()*ratio), (int)(resource.getCharacters().get(i).getHeight()*ratio), this);
			}
			//fix the living drawing boi for flipperino
			if(resource.getCharacters().get(i).getDirectionFacing().equals("right")){
				g.drawImage(resource.getCharacters().get(i).getActiveFrame(), resource.getCharacters().get(i).getPosition()[0]-lowX, resource.getCharacters().get(i).getPosition()[0]-lowX, (int)(resource.getCharacters().get(i).getWidth()*ratio), (int)(resource.getCharacters().get(i).getHeight()*ratio), this);
			}else{
				g.drawImage(resource.getCharacters().get(i).getActiveFrame(), resource.getCharacters().get(i).getPosition()[0]-lowX +resource.getCharacters().get(i).getWidth(), resource.getCharacters().get(i).getPosition()[0]-lowX, -(int)(resource.getCharacters().get(i).getWidth()*ratio), (int)(resource.getCharacters().get(i).getHeight()*ratio), this);
			}
		}
		for (int i =0;i<resource.getCurrentStage().getItems().size();i++){
			g.drawImage(resource.getCurrentStage().getItems().get(i).getImage(), resource.getCurrentStage().getItems().get(i).getX()-lowX, resource.getCurrentStage().getItems().get(i).getY()-lowY, (int)(resource.getCurrentStage().getItems().get(i).getWidth()*ratio), (int)(resource.getCurrentStage().getItems().get(i).getHeight()*ratio), this);
		}
	}
  	
	void setResources(Resources resource){
		this.resource = resource;
	}
  
	void givePoints(Player p, int amount){
		p.setScore(p.getScore()+amount);
		
	}
	
	void death(){
		
	}
>>>>>>> b7c00742bcc1920bf94b4c3adcf81b05b9ea334c

 void end(){
  if(resource.getPlayers().get(0).getFinished()&&resource.getPlayers().get(1).getFinished()&&resource.getPlayers().get(2).getFinished()&&resource.getPlayers().get(3).getFinished()){
   //too easy no point  (writing code here gottem)
  }else if (resource.getPlayers().get(0).getFinished()||resource.getPlayers().get(1).getFinished()||resource.getPlayers().get(2).getFinished()||resource.getPlayers().get(3).getFinished()){ //if anyone finished
   for (int i=0;i<4;i++){
    if(!resource.getPlayers().get(i).getFinished()){
     if (resource.getPlayers().get(i).getKilledBy().getPlacer()!= null && (resource.getPlayers().get(i).getKilledBy().getPlacer()!= resource.getPlayers().get(i))){
      givePoints(resource.getPlayers().get(i).getKilledBy().getPlacer(),1);//trap
     }
    }else{
     givePoints(resource.getPlayers().get(i),3);//finish
    }
   }
  }
   
  
 }
 
 public void CameraAdjust(SimpleLinkedList<Player> players){
  int minX = players.get(0).getCharacter().getPosition()[0];
  int maxX = minX+players.get(0).getCharacter().getWidth();
  int minY = players.get(0).getCharacter().getPosition()[1];
  int maxY = minY+players.get(0).getCharacter().getHeight() ;
  
  for(int i =1;i<4;i++){
   if(players.get(i).getCharacter().isAlive()){
    if (players.get(i).getCharacter().getPosition()[0] <minX){ 
     minX = players.get(i).getCharacter().getPosition()[0];
    }
    if (players.get(i).getCharacter().getPosition()[0] > maxX){
     maxX = players.get(i).getCharacter().getPosition()[0]+players.get(i).getCharacter().getWidth();
    }
    if (players.get(i).getCharacter().getPosition()[1] <minY){
     minY = players.get(i).getCharacter().getPosition()[1];
    }
    if (players.get(i).getCharacter().getPosition()[0] >maxY){
     maxY = players.get(i).getCharacter().getPosition()[1]+players.get(i).getCharacter().getHeight();
    }
   
   }
  }
  
  minX-=20;
  maxX+=20;
  minY-=20;
  maxY+=20;
  
  //don't think we need this bc its different now because we can show offscreen
//  minX = Math.max(minX, 0);
//  minY = Math.max(minY, 0);
//  maxX = Math.min(maxX, 860);
//  maxY = Math.min(maxY, 380);
  
  double tempRatio = (maxX-minX)/(maxY-minY);
  int tempAmount;
  
  if (tempRatio > 36.0/29.0){ //too much x
   tempAmount = (int)Math.round((maxX-minX)*29.0/36.0);
   //fix y
   tempAmount /=2;
   if(minY> tempAmount){
    minY-=tempAmount;
    if(maxY<380-tempAmount){ //maxY can't be above 380
     maxY+= tempAmount;
    }else{
     int leftOver = 380-maxY;
     maxY =380;
     minY -= leftOver;
    }
   }
   
  }else if(tempRatio < 36.0/29.0){ //too much y
   tempAmount = (int)Math.round((maxY-minY)*36.0/29.0);
   //fix x
   tempAmount /=2;
   minX-=tempAmount;
   maxX+=tempAmount;
  }
  
  //gonna need a ratio ops
  //360 by 290
  
  
  this.highX= maxX;
  this.highY= maxY;
  this.lowX= minX;
  this.lowY= minY;
  
  //ratio time
  //ngl super unsure about this boi
  this.ratio = 360/(maxX-minX);
    
  
 }

 public void setButtonPressed(String buttonPressed) {
  this.buttonPressed = buttonPressed;
 }
 
 public int[] getScreen(){
  int[] dimensions = new int[4];
  dimensions[0]= lowX;
  dimensions[1]= highX;
  dimensions[2]= lowY;
  dimensions[3]= highY;
  return dimensions;
 }
 
 public double getRatio(){
  return ratio;
 }
}
