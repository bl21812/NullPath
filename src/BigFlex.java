import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JPanel;
import java.util.ArrayList;

public class BigFlex extends JPanel implements KeyListener{

    	Character testPlayer = null;
    	PhysicsEngine newEng = null;
    	StationaryPlatform plat = null;
    	FanWind wind = null;
    	ConveyorBelt track = null;
    	ArrayList <Projectile> ProjectileList = new ArrayList<Projectile>();
    	ProjectileLauncher launcher = null;
    	double initStart;
    	int lastNum;
    	boolean onID;
    	boolean onIA;
    	String[][] contactMap;
    	Resources resource;
    	 private int lowX,lowY,highX,highY;
    	 private double ratio;
    	
    	public BigFlex() throws FileNotFoundException, IOException {
    		this.setSize(new Dimension(1720, 760));
    		testPlayer = new Character(80, 240, 60, 40, 0);
    		plat = new StationaryPlatform(80, 250, 150, 300);
    		plat.setIce(true);
    		wind = new FanWind(450, 100, 600, 50, 1, 1);
    		track = new ConveyorBelt(500, 250, 40, 200, 1, 0);
    		launcher = new ProjectileLauncher(600, 600,50, 50, false, "RAND");
    		initStart = System.nanoTime()/(Math.pow(10, 9));
    		lastNum = 0;
    		onIA = false;
    		onID = false;
    		System.out.println(testPlayer.getPosition()[0] + " AND "+testPlayer.getPosition()[1] + ", "+(testPlayer.getPosition()[0]+testPlayer.getWidth())+" AND " + (testPlayer.getPosition()[1]+testPlayer.getHeight()));
    		System.out.println(plat.getX() + " AND "+plat.getY() + ", "+(plat.getX()+plat.getWidth())+" AND " + (plat.getY()+plat.getHeight()));
    		newEng = new PhysicsEngine("Bad");
    		contactMap = newEng.retrieveCMap();
    	    newEng.printMap(contactMap);
    	    
    		setFocusable(true);
    		requestFocusInWindow();
            setVisible (true);
    		addKeyListener(this);
    	}
    	
    	public void paintComponent(Graphics g) {
    		  
    		int convSec = (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
    		// Call the super class
    	    super.paintComponent(g);
    	    setDoubleBuffered(true);
    	    
	    	for (int i = 0; i < contactMap.length; i++) {
	    		for (int a = 0; a < contactMap[0].length; a++) {
	    			if (contactMap[i][a].equals("1")) {
	    				g.setColor(Color.magenta);
	    				g.drawRect((a+1)*20, (i+1)*20, 20, 20);
	    			} else if (contactMap[i][a].equals("2")) {
	    				g.setColor(Color.PINK);
	    				g.fillRect((a+1)*20, (i+1)*20, 20, 20);
	    			}
	    		}
	    	}
	    	
	    	for (int i = 0; i < contactMap[0].length; i++) {
	    		g.setColor(Color.CYAN);
	    		g.drawRect(1700, 720, 20, 20);
	    	}
    	    
    	    g.setColor(Color.GREEN);
    	    g.fillRect(launcher.getX(), launcher.getY(), launcher.getWidth(), launcher.getHeight());
    	    g.setColor(Color.DARK_GRAY);
    	    g.fillRect(plat.getX(), plat.getY(), plat.getWidth(), plat.getHeight());
    	    g.setColor(Color.BLUE);
    	    g.fillRect(wind.getX(), wind.getY(), wind.getWidth(), wind.getHeight());
    	    g.setColor(Color.CYAN);
    	    g.fillRect(track.getX(), track.getY(), track.getWidth(), track.getHeight());
    	    g.setColor(Color.RED);
    	    g.fillRect(testPlayer.getPosition()[0], testPlayer.getPosition()[1], testPlayer.getWidth(), testPlayer.getHeight());
    	    
//    	    for (int i = 0; i < ProjectileList.size(); i++) {
//    	    	newEng.move(ProjectileList.get(i));
//    	    	g.setColor(Color.RED);
//    	    	g.fillRect(ProjectileList.get(i).getX(), ProjectileList.get(i).getY(), ProjectileList.get(i).getWidth(), ProjectileList.get(i).getHeight());
//    	    }
    	    
    	    cameraAdjust(resource.getPlayers()); // Adjust camera values
    		// Draw extra background in back here
    		g.drawImage(Resources.getStages().get(0).getSprite(), lowX, lowY, highX-lowX, highY-lowY, this); // Draw image of stage
    		
    		for(int i = 0;i<4;i++){
    			if (!resource.getCharacters().get(i).isAlive()){
    				if ((resource.getCharacters().get(i).getCurrentFrameIndex() == 0) || (resource.getCharacters().get(i).getCurrentFrameIndex() > 6)) {
    					resource.getCharacters().get(i).setCurrentFrameIndex(1);
    				}
    			} else if (resource.getCharacters().get(i).getFinished()) {
    				if ((resource.getCharacters().get(i).getCurrentFrameIndex() < 24)|| (resource.getCharacters().get(i).getCurrentFrameIndex() == 32)) {
    					resource.getCharacters().get(i).setCurrentFrameIndex(24);
    				}
    			}
    			// draw character sprite
    			if(resource.getCharacters().get(i).getDirectionFacing().equals("right")){
    				g.drawImage(resource.getCharacters().get(i).getActiveFrame(), resource.getCharacters().get(i).getPosition()[0]-lowX, resource.getCharacters().get(i).getPosition()[0]-lowX, (int)(resource.getCharacters().get(i).getWidth()*ratio), (int)(resource.getCharacters().get(i).getHeight()*ratio), this);
    			}else{
    				g.drawImage(resource.getCharacters().get(i).getActiveFrame(), resource.getCharacters().get(i).getPosition()[0]-lowX +resource.getCharacters().get(i).getWidth(), resource.getCharacters().get(i).getPosition()[0]-lowX, -(int)(resource.getCharacters().get(i).getWidth()*ratio), (int)(resource.getCharacters().get(i).getHeight()*ratio), this);
    			}
    			
    			// increase active frame index if character is dead
    			if ((!resource.getCharacters().get(i).isAlive()) || (resource.getCharacters().get(i).getFinished())) {
    				resource.getCharacters().get(i).setCurrentFrameIndex(resource.getCharacters().get(i).getCurrentFrameIndex()+1);
    			}
    			
    		}
    		for (int i =0;i<resource.getCurrentStage().getItems().size();i++){
    			g.drawImage(resource.getCurrentStage().getItems().get(i).getImage(), resource.getCurrentStage().getItems().get(i).getX()-lowX, resource.getCurrentStage().getItems().get(i).getY()-lowY, (int)(resource.getCurrentStage().getItems().get(i).getWidth()*ratio), (int)(resource.getCurrentStage().getItems().get(i).getHeight()*ratio), this);
    		}
    	    
    	    newEng.contactMapCollision(testPlayer);
    	    newEng.checkCollision(testPlayer, plat, false);
    	    newEng.checkCollision(testPlayer, track, false);
    	    newEng.checkCollision(testPlayer, wind, false);
    	    if (lastNum < convSec) {
    	    	lastNum = convSec;
    	    	launcher.launchProjectile(-3, 0, ProjectileList);
    	    }
    	    newEng.move(testPlayer);
    	    repaint();
    	}
    	
    	public void cameraAdjust(SimpleLinkedList<Player> players){
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
    	
    	public void keyPressed(KeyEvent e) {
    		char c = e.getKeyChar();
    		if (c == 'd') {
    			onID = true;
    			if (testPlayer.getMotion()[1] == false) {
    				if (testPlayer.getHoney()) {
    					testPlayer.setHMotion(true, 1);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+1, testPlayer.getVelocity()[1]});
    				} else if (testPlayer.getIce()){
    					if (testPlayer.getIMotion()) {
    						testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-testPlayer.getLastI() , testPlayer.getVelocity()[1]});
    					}
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+2, testPlayer.getVelocity()[1]});
    					testPlayer.setIMotion(false);
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+2, testPlayer.getVelocity()[1]});
    				}
    				testPlayer.setMotion(true, 1);
    			}
    		} else if (c == 'w' && testPlayer.getJump()) {
    			if (testPlayer.getMotion()[2] == false) {
    				/*
    				if(testPlayer.getIMotion()) {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-testPlayer.getLastI() , testPlayer.getVelocity()[1]});
    					testPlayer.setIMotion(false);
    				}
    				*/
    				if (testPlayer.getHoney()) {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0], testPlayer.getVelocity()[1]-2.5});
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0], testPlayer.getVelocity()[1]-4.5});
    				}
    				testPlayer.setMotion(true, 2);
    			}
    		} else if (c == 'a') {
    			onIA = true;
    			if (testPlayer.getMotion()[0] == false) {
    				if (testPlayer.getHoney()) {
    					testPlayer.setHMotion(true, 0);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-1, testPlayer.getVelocity()[1]});
    				} else if (testPlayer.getIce()){
    					if (testPlayer.getIMotion()) {
    						testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-testPlayer.getLastI() , testPlayer.getVelocity()[1]});
    					}
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-2 , testPlayer.getVelocity()[1]});
    					testPlayer.setIMotion(false);
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-2, testPlayer.getVelocity()[1]});
    				}
    				testPlayer.setMotion(true, 0);
    			}
    		} 
    	}
    	
    	public void keyReleased(KeyEvent e) {
    		char c = e.getKeyChar();
    		if (c == 'd') {
    			onID = false;
    			if (testPlayer.getMotion()[1]) {
    				if (testPlayer.getHMotion()[1]) {
    					testPlayer.setHMotion(false, 1);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-1, testPlayer.getVelocity()[1]});
    				} else if (testPlayer.getIce() && testPlayer.getIMotion() == false && onIA == false){
    					testPlayer.setIMotion(true);
    					testPlayer.setLastI(testPlayer.getVelocity()[0]*(.33));
    					System.out.println(testPlayer.getVelocity()[0]*(.33));    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]*(.33), testPlayer.getVelocity()[1]});
    				} else { 
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-2, testPlayer.getVelocity()[1]});
    				}
    				testPlayer.setMotion(false, 1);
    			}
    		} else if (c == 'w') {
    			if (testPlayer.getMotion()[2]) {
    				//testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0], testPlayer.getVelocity()[1]+6});
    				testPlayer.setMotion(false, 2);
    			}
    		} else if (c == 'a') {
    			onIA = false;
    			if (testPlayer.getMotion()[0]) {
    				if (testPlayer.getHMotion()[0]) {
    					testPlayer.setHMotion(false, 0);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+1, testPlayer.getVelocity()[1]});	
    				} else if (testPlayer.getIce() && testPlayer.getIMotion() == false && onID == false) {
    					testPlayer.setIMotion(true);
    					testPlayer.setLastI(testPlayer.getVelocity()[0]*(.33));
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]*(.33), testPlayer.getVelocity()[1]});
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+2, testPlayer.getVelocity()[1]});	
    				}
    				testPlayer.setMotion(false, 0);
    			}
    		} 
    	}
    	
    	public void keyTyped(KeyEvent e) {
    	}
    }