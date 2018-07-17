/*
 */

package passwordchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author Ryan Nichols
 */
public class PasswordCheckerClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        char choice = 'a';
        //Menu
        while (choice != 'Q') {
            System.out.print("Check passwords via manual input(m), file input(f), or quit(q): ");
            choice = stdin.next().charAt(0);
            choice = Character.toUpperCase(choice);
            
            if (choice == 'F') 
                fileTest();            
            else if (choice == 'M')
                manualTest();            
        }        
    }
    
    public static void fileTest() {        
        Scanner stdin = new Scanner(System.in);
        int testCount = 0,
            passedTestCount = 0;
        String results ="";
        
        /*Get file name*/
        System.out.print("Enter the file name (with extension): ");
        String dir = stdin.next();
        File inputFile = new File(dir);
        /*Read file and run tests*/
        try {
            //Scanner fin = new Scanner(inputFile);
            Scanner fin = new Scanner(new BufferedReader(new FileReader(inputFile)));
            fin.useDelimiter("\n"); 
            while (fin.hasNext()) {                
                testCount++;
                PasswordChecker pwChecker = new PasswordChecker();
                /*Read test case*/
                pwChecker.setNewPassword(fin.nextLine());                
                pwChecker.setOldPassword1(fin.nextLine());
                pwChecker.setOldPassword2(fin.nextLine());
                String expectedResult = fin.nextLine(); //First char is A or R, followed by an optional comment
                     
                String actualResult = pwChecker.checkPasswordValdity();
                System.out.println("\nTest Case " + testCount + ": \n" +actualResult);
                
                /*DEBUG*/
                /*System.out.println(pwChecker.getNewPassword());
                System.out.println(pwChecker.getOldPassword1());
                System.out.println(pwChecker.getOldPassword2());
                System.out.println(expectedResult);
                System.out.println(actualResult); */       
                
                if (actualResult.charAt(13) == expectedResult.toUpperCase().charAt(0)) { 
                    passedTestCount++;
                }
                else {
                    results += "\nTest case " + testCount + " failed: Unexpected result."
                            + "\nExpected Result: " + expectedResult 
                            + "\nActual Result: " + actualResult;
                }                    
            }//End of file reading 
            if (passedTestCount == testCount) {
            results = "\nAll test cases (" + passedTestCount + " of " + testCount 
                    + ") passed successfully.";
            }
            else {
                results = "\n" + passedTestCount + " of " + testCount 
                        + " passed successfully." + results;
            }
            System.out.println(results);
        }//End of try
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }//End of catch     
    }//End of fileTest
    
    public static void manualTest() {
        char again;
        Scanner stdin = new Scanner(System.in);
        stdin.useDelimiter("\n");   //This allows the scanner to read lines with quotation marks treated as normal characters
        
        do {
            PasswordChecker pwChecker = new PasswordChecker();
            
            System.out.print("Enter new password: ");
            pwChecker.setNewPassword(stdin.next());
            
            System.out.print("Enter the first old password: ");
            pwChecker.setOldPassword1(stdin.next());
            
            System.out.print("Enter the second old password: ");
            pwChecker.setOldPassword2(stdin.next());
            
            String result = pwChecker.checkPasswordValdity();
            System.out.println(result);
            
            do {
                System.out.print("\nWould you like to run another manual test (y/n):");
                again = stdin.next().charAt(0);
                again = Character.toUpperCase(again);
            } while (again != 'Y' && again != 'N');           
        } while (again == 'Y');       
    }    
}
