import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

public class SudokuFrame extends JFrame implements ActionListener,MouseListener 
{
	String lab;
	Container cont;
	JButton close;
	menu menu;
	solver solver;
	int height,width;
	
	public SudokuFrame() {
		height=700;
		width=900;
		cont = getContentPane();
		cont.setLayout(null);
		setLayout(null);
		setBounds(250,100,width,height);
		setUndecorated(true);
		setVisible(true);
		
		Font label=new Font("Arial",Font.BOLD,15);
		
		close=new JButton("x");
		close.setBounds(10,10,15,15);
		close.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.white));
		close.setOpaque(true);
		close.setBackground(Color.RED);
		close.setFont(label);
		close.setFocusable(false);
		close.setForeground(Color.white);
	
		menu=new menu();
		menu.setBounds(0,0,width,height);
		
		cont.add(close);
		cont.add(menu);
		
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		close.setActionCommand("x");
		close.addActionListener(this);
		close.addMouseListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu.start.setActionCommand("Start");
		menu.start.addActionListener(this);
		menu.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae){
		lab=ae.getActionCommand();
		// if close button pressed, quit program
		if(lab.equals("x")){
			this.dispose();
			System.exit(0);
		} else if(lab.equals("Start")){
			// if start button pressed, display the solver
			try {
			solver=new solver();
			solver.setBounds(0,0,width,height);
			cont.add(solver);
			menu.setVisible(false);
			solver.setVisible(true);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {  }
	
    public void mouseEntered(MouseEvent e) {  
    	AbstractButton EventSource = (AbstractButton)e.getSource();
    } 
    public void mouseExited(MouseEvent e) {  
    	AbstractButton EventSource = (AbstractButton)e.getSource();
    }  
    
    public void mousePressed(MouseEvent e) {}  
    public void mouseReleased(MouseEvent e) {} 

}
