/*  [MainMenuPanel.java]
    Base panel with buttons for options, quitting, and starting game.
    Author: Brian Zhang
    ICS4UE
    Date: 01/22/19
 */

//Base imports
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel implements KeyListener {

    //Base imports
    private int selection = -1;
    private boolean next = false;

    //Mouse Position
    private int xPos = 0;
    private int yPos = 0;

    //Override KeyListener Methods (proceed to next frame upon keypressed)
    public void keyTyped(KeyEvent e) {
        System.out.println("big boi");
    }
    public void keyReleased(KeyEvent e){
    }
    public void keyPressed(KeyEvent e) {
    }

    //Override paintComponent
    public void paintComponent(Graphics g) {

        // Call the super class
        super.paintComponent(g);
        setDoubleBuffered(true);

        //Override mouseListener methods
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                xPos = e.getX();
                yPos = e.getY();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent evt){
                setLocation(evt.getXOnScreen()-xPos, evt.getYOnScreen()-yPos);
            }
        });

        this.setLayout(null);

        //Setup all buttons for options
        JButton buttonOne = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Start.png"))));
        JButton buttonTwo = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Options.png"))));
        JButton buttonThree = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Quit.png"))));

        JLabel flow = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("WaterFlow.gif"))));

        // Draw start button (rectangles right now)
        this.add(buttonOne);
        buttonOne.setLocation(-100,150);
        buttonOne.setSize(1720,64);
        buttonOne.setContentAreaFilled(false);
        buttonOne.setBorder(null);

       //Override component listener
        buttonOne.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("StartHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Start.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("StartPress.png"))));
                selection = 0;
                next = true;
                System.out.println("Startpressed");
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        // Draw options button (rectangles right now)
        this.add(buttonTwo);
        buttonTwo.setLocation(-100,300);
        buttonTwo.setSize(1720,64);
        buttonTwo.setContentAreaFilled(false);
        buttonTwo.setBorder(null);

               //Override component listener
        buttonTwo.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("OptionsHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Options.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("OptionsPress.png"))));
                selection = 1;
                next = true;
                System.out.println("Options pressed");
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        // Draw quit button (rectangles right now)
        this.add(buttonThree);
        buttonThree.setLocation(-100,450);
        buttonThree.setSize(1720,64);
        buttonThree.setContentAreaFilled(false);
        buttonThree.setBorder(null);
        
       //Override component listener
        buttonThree.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("QuitHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Quit.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("QuitPress.png"))));
                selection = 2;
                next = true;
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        this.add(flow);
        flow.setLocation(0,-100);
        flow.setSize(2000,1020);

        // Repaint
        repaint();
        setVisible(true);
    }

    //Method for next retrieval
    public boolean getNext(){
        return next;}
    //Method for selection retrieval
    public int getSelection() {
        return selection;
    }

}
