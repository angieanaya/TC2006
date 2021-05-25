import java.util.Arrays;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;


//Example of how to connect java with prolog using jpl
public class Sudoku {

	public static void main(String[] args) {

        // COMPILES PROLOG FILE
        Query q1 = new Query(
        		"consult",
        		new Term[] {new Atom("src\\sudoku.pl")}
        		);

        // VERIFIES PROLOG FILE HAS BEEN COMPILED CORRECTLY
        // System.out.println((q1.hasSolution() ? "SUCCESSFUL" : "FAILED"));
        q1.hasSolution();
        // 2D ARRAY WITH HINTS FOR SUDOKU
        String rows[][] = {{"8,_,_,_,_,_,_,_,_"}, {"_,_,3,6,_,_,_,_,_"}, {"_,7,_,_,9,_,2,_,_"}, {"_,5,_,_,_,7,_,_,_"}, {"_,_,_,_,4,5,7,_,_"}, {"_,_,_,1,_,_,_,3,_"}, {"_,_,1,_,_,_,_,6,8"}, {"_,_,8,5,_,_,_,1,_"}, {"_,9,_,_,_,_,4,_,_"}};

        // FACT THAT SHOULD BE PLACED DYNAMICALLY IN PROLOG FILE
        String puzzle="puzzle(7, "+Arrays.deepToString(rows) +")";

        // PLACE FACT IN PROLOG FILE
        Query q2 = new Query("assert("+ puzzle +")");

        // VERIFY IF FACT HAS BEEN PLACED
        q2.hasSolution();

        
         try { 
             // QUERY TO GET THE SOLUTION OF SUDOKU
         	Query  q3 = new Query("puzzle(7,Rows), sudoku(Rows)."); 
         	String result = q3.oneSolution().get("Rows").toString();
             // PRINTS THE SOLUTION
         	System.out.println(result);
        	 
        	 
        }catch(Exception e)  {
        	 System.out.println("An error ocurred");
        }
        
	}

}