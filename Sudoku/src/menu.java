

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Panel1 extends JPanel
{
	
	Panel1()
	{	}
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        g2d.setPaint(Color.black);
        g2d.fillRect(0, 0, w, h);
    }
}

public class menu extends Panel1 implements ActionListener,MouseListener{
	String lab;
	JLabel title,author;
	JButton start;
	
	public menu() {
		setLayout(null);
		setVisible(false);
		
		// FONTS
		Font titleF=new Font("Arial",Font.BOLD,70);
		Font label=new Font("Arial",Font.BOLD,15);
		Font button=new Font("Arial",Font.BOLD,35);
		
		//COLORS
		Color electricblue = new Color(0, 123, 255);

		title=new JLabel("Sudoku Solver");
		title.setForeground(electricblue);
		title.setBorder(null);
		title.setLayout(null);
		title.setBounds(200,60,500,100);
		title.setFont(titleF);
		
		author=new JLabel("Created by: Angie Anaya");
		author.setForeground(Color.white);
		author.setBorder(null);
		author.setLayout(null);
		author.setBounds(700,630,400,100);
		author.setFont(label);
	
		start=new JButton("START");
		start.setBounds(350,350,200,100);
		start.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.white));
		start.setOpaque(true);
		start.setBackground(electricblue);
		start.setFont(button);
        start.setFocusable(false);
        start.setForeground(Color.white);
        start.addActionListener(this);
        start.addMouseListener(this);
		
		add(title);
		add(author);
		add(start);
	}
	
	public void actionPerformed(ActionEvent ae){
		lab=ae.getActionCommand();
	}
	
	public void mouseClicked(MouseEvent e) { }  
	
	//change color of button on hover
    public void mouseEntered(MouseEvent e) {  
    	AbstractButton EventSource = (AbstractButton)e.getSource();
    	if(EventSource.equals(start)){
    		EventSource.setBackground(Color.MAGENTA);
    	}
    } 
    
    public void mouseExited(MouseEvent e) {  
    	AbstractButton EventSource = (AbstractButton)e.getSource();
    	if(EventSource.equals(start)){
    		Color electricblue = new Color(0, 123, 255);
    		EventSource.setBackground(electricblue);
    	}
    } 
    
    public void mousePressed(MouseEvent e) {}  
    
    public void mouseReleased(MouseEvent e) {} 
}
