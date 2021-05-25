import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;


import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

class Panel2 extends JPanel{
	
	Panel2()
	{	}
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = Color.black;
        g2d.setPaint(color1);
        g2d.fillRect(0, 0, w, h);
    }

}

public class solver extends Panel2 implements ActionListener,MouseListener {
	String lab;
	// holds the original matrix (the one entered by the user)
	static int[][] original;
	// holds the solution matrix (the one returned by prolog)
	static int[][] sol;
	JButton solves,reset;
	static JLabel msg;
	// this textfield matrix allows the user to enter the givens
	static JTextField[][] num;
	
	public solver() {
		setLayout(null);
		setVisible(false);
		
		Font f1=new Font("Arial",Font.BOLD,25);
		Font label=new Font("Arial",Font.BOLD,20);
		Font button=new Font("Arial",Font.BOLD,25);
		
		Color electricblue = new Color(0, 123, 255);
		
		// initializing matrices
		original=new int[9][9];
		num= new JTextField[9][9];
		
		// UI for sudoku (grid)
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				num[i][j]=new JTextField();
				num[i][j].setBounds(50+(j*70),50+(i*70),50,50);
				num[i][j].setLayout(null);
				num[i][j].setFont(f1);
				num[i][j].setText("");
				num[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.white));
				num[i][j].setHorizontalAlignment(JTextField.CENTER);
				num[i][j].setForeground(electricblue);
				num[i][j].setBackground(Color.DARK_GRAY);
				num[i][j].addKeyListener(new KeyAdapter() {
		            public void keyTyped(KeyEvent e) {
		                char caracter = e.getKeyChar();
		                if (((caracter < '1') || (caracter > '9')) && (caracter != '\b')) {
		                    e.consume();
		                }
		            }
		        });
				add(num[i][j]);
			}
		}
		
		solves=new JButton("Solve");
		solves.setBounds(700,530,150,60);
		solves.setForeground(Color.white);
		solves.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.white));
		solves.setOpaque(true);
		solves.setBackground(Color.MAGENTA);
		solves.setFocusable(false);
		solves.setFocusPainted(false);
		solves.setLayout(null);
		solves.setFont(button);
		solves.addActionListener(this);
		solves.addMouseListener(this);
		
		reset=new JButton("Reset");
		reset.setBounds(700,610,150,60);
		reset.setForeground(Color.white);
		reset.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.white));
		reset.setOpaque(true);
		reset.setBackground(Color.MAGENTA);
		reset.setFocusable(false);
		reset.setFocusPainted(false);
		reset.setLayout(null);
		reset.setFont(button);
		reset.addActionListener(this);
		reset.addMouseListener(this);
		
		// to indicate if sudoku was calculated or not
		msg=new JLabel();
		msg.setBounds(700,200,200,100);
		msg.setForeground(Color.white);
		msg.setOpaque(false);
		msg.setBorder(null);
		msg.setLayout(null);
		msg.setFont(label);
		
		add(solves);
		add(msg);
		add(reset);
	}
	
	// clears the board 
	public static void resets(){
		Color electricblue = new Color(0, 123, 255);
		msg.setText("");
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				original[i][j]=0;
				sol[i][j]=0;
				num[i][j].setEditable(true);
				num[i][j].setForeground(electricblue);
				num[i][j].setText("");
			}
		}
	}
	
	// counter to assert fact in prolog file
	int puzzleNum = 7;
	public void actionPerformed(ActionEvent ae){
		lab = ae.getActionCommand();
		
		// compiles prolog file
        Query q1 = new Query(
        		"consult",
        		new Term[] {new Atom("src/sudoku.pl")}
        		);

        // verifies prolog file was compiled successfully
        q1.hasSolution();
		
		// if solve button is pressed
		if(lab.equals("Solve")){
			// initialize array of strings that holds the entered givens
			String[] arr = {"","","","","","","","","",""};
			
			for(int i=0; i<9; i++){
				for(int j=0; j<9; j++){
					// if the textfield is empty fill it with an underscore
					if(num[i][j].getText().equals("")) {
						arr[i] += "_";
					} else { // if the textfield is not empty get the integer 
						 arr[i] += num[i][j].getText();
					}
					//place a comma in between every textfield except in the last element of every row
					if(j != 8) {
						arr[i] += ",";
					}
				}
			}
			
			System.out.println("Array of strings that holds the entered givens");
			for(int i=0; i < arr.length; i++) {
				System.out.println(arr[i]);
			}
			
			 // 2d array with the givens entered by the user to be able to pass them to prolog
	        String rows[][] = {{arr[0]}, {arr[1]}, {arr[2]}, {arr[3]}, {arr[4]}, {arr[5]}, {arr[6]}, {arr[7]}, {arr[8]}};
	        System.out.println("2D array that is used in prolog query\n"+ Arrays.deepToString(rows));
	        
	        // fact that should be placed dynamically in prolog
	        String puzzle = "puzzle(" + puzzleNum + ", " + Arrays.deepToString(rows) + ")"; // utilizes the puzzleNum variable to be able to use the program several time
	    
	        // place fact in prolog file
	        Query q2 = new Query("assert(" + puzzle + ")");

	        // verify if fact has been placed
	        q2.hasSolution();
	       
	        String result = "";
	        try { 
	        	// query to get the solution of the sudoku puzzle
	         	Query  q3 = new Query("puzzle(" + puzzleNum + ",Rows), sudoku(Rows)."); 
	         	result = q3.oneSolution().get("Rows").toString();
	            
	         	// prints the solution returned by prolog as a string
	         	System.out.println("The result returned by prolog as a String");
	         	System.out.println(result);
	         	msg.setText("Sudoku solved!");
	        }catch(Exception e)  {
	        	 System.out.println("An error ocurred");
	        	 msg.setText("Unable to solve.");
	        }
	       
	        int aux = 0, j = 0, a;
	        
	        //Array of int that holds the solution that will be placed in the interface
	        sol = new int[9][9];
	       
	        System.out.println("Get characters from the solution(String) and parse them to integer");
	        for(int i=0; i<result.length(); i++) {

	        	if(aux == 9) {
	        		aux = 0;
	        		j++;
	        		System.out.println(); 
	        	}
	        	
	        	if(j == 9) {
	        		j = 0;
	        	}
	        	
	        	// get numerical value of the character saved in the result string
	        	a = Character.getNumericValue(result.charAt(i));
	        	
	        	// print only the integers between 1 and 9
	        	if(a >= 1 && a <= 9) {
	        		sol[j][aux] = a;
	        		System.out.print(" " + sol[j][aux]);
	        		aux++;
	        	}
	        }
	    	
	       for(int i=0; i < sol.length; i++){
				for(int k=0; k < sol[i].length; k++){
					num[i][k].setForeground(Color.white);
					//reading the matrix solution and parsing to String
					num[i][k].setText(String.valueOf(sol[i][k]));
					//to stop editing the textfields
					num[i][k].setEditable(false);
				}
			}
		} else if(lab.equals("Reset")){
			// if reset button is pressed
			puzzleNum++; //used in the dynamic fact 
			resets();
		}
	}
	
	public void mouseClicked(MouseEvent e) {  }  
	// changes the color of the button on hover
    public void mouseEntered(MouseEvent e) {  
    	Color electricblue = new Color(0, 123, 255);
    	AbstractButton EventSource = (AbstractButton)e.getSource();
    	if(EventSource.equals(solves)){
    		EventSource.setBackground(electricblue);
    	} else if(EventSource.equals(reset)){
    		EventSource.setBackground(electricblue);
    	}
    } 
    // changes the color of the button to the original
    public void mouseExited(MouseEvent e) {  
    	AbstractButton EventSource = (AbstractButton)e.getSource();
    	if(EventSource.equals(solves)){
    		EventSource.setBackground(Color.MAGENTA);
    	} else if(EventSource.equals(reset)){
    		EventSource.setBackground(Color.MAGENTA);
    	}
    }  
    
    public void mousePressed(MouseEvent e) {	}
    
    public void mouseReleased(MouseEvent e) {	}
    
}
