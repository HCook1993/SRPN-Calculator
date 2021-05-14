import java.util.*;

/*
import com.sun.org.apache.xpath.internal.operations.Bool;
if an "=" is received, print zero always
 if (s.charAt(s.length()-1) == '=') {
 System.out.println(0);
*/

public class SRPN {

  private Stack<Long> stk = new Stack<>();
  /*
   * Array of operators that can be used. Needs an exception to be implemented if
   * anything but recognised operator is used...
   */
  private String[] operator = { "+", "-", "/", "*", "%", "^" };

  /*
   * Set up array of numbers to be printed if r is input. These appear random but
   * are actually a set to the size of the stack (23) and are hard coded numbers
   * that only appear in this order.
   */

  int rIndex = 0;
  long[] rNumbers = new long[] { 1804289383, 846930886, 1681692777, 1714636915, 1957747793, 424238335, 719885386,
      1649760492, 596516649, 1189641421, 1025202362, 1350490027, 783368690, 1102520059, 2044897763, 1967513926,
      1365180540, 1540383426, 304089172, 1303455736, 35005211, 521595368, 180428938 };

  public void processCommand(String s) {

    /*
     * The string needs splitting to be read on one continuous command line and
     * should try to inlude option to handle whitespaces: String str = s; String[]
     * strArray = str.split("+");
     * Method to check is there is whitespace should be used
     */

    /*
     * Implement try/catch to produce error messages and not crash program.
     */
    try {
      if (checkOperation(s)) {

        if (stk.size() <= 1) {
          System.out.println("Stack underflow.");
          return;
        }
        // Can you apply the same logic for "Stack overflow."?
        if (stk.size() > 23) {
          System.out.println("Stack overflow.");
          return;
        }

        long input2 = stk.pop();
        long input1 = stk.pop();
        long result = executeOp(input1, input2, s);

        if (result > Integer.MAX_VALUE) {
          result = Integer.MAX_VALUE;
        }
        if (result < Integer.MIN_VALUE) {
          result = Integer.MIN_VALUE;
        }

        stk.push(result);
      }
      /*
       * Function 'd' is added to display the stack through converting to an array and
       * printing one stack per line.
       */
      else if (s.equals("d")) {
        Object[] arr = stk.toArray();
        for (int a = 0; a < arr.length; a++)
          System.out.println(arr[a]);
      }

      /*
       * Side note: A better method would be printing all elements of the stack
       * instead of converting to an array for efficiency.
       */

      else if (s.equals("=")) {
        System.out.println(stk.peek());
      }
      /*
       * Function to print a number from array 'r'. If 'r' is entered again, it prints
       * the next element of the array. Each element is then pushed to the stack.
       */
      else if (s.equals("r")) {
        System.out.println(rNumbers[rIndex]);
        stk.push(rNumbers[rIndex]);
        rIndex++;
      } else {
        long i = Long.parseLong(s);
        stk.push(i);
      }
    }

    catch (IllegalArgumentException e) {
      System.out.println("Unrecognised operator or operand \"" + s + "\".");
      return;
    } catch (ArithmeticException e) {
      System.out.println("Divide by 0.");
      return;
    }

  }
  /*
   * Side note: Could use string.format to include 's' in quotation marks as would be less work for the compiler.
   */

  /*
   * Method for executing the calculaitons depending on the operator input by the
   * user with exceptions in place to prompt the user for a legal operator and to stop the user from diving by 0.
   */

  private long executeOp(long input1, long input2, String operator)
      throws IllegalArgumentException, ArithmeticException {

    if (operator.equals("+")) {
      return input1 + input2;
    } else if (operator.equals("-")) {
      return input1 - input2;
    } else if (operator.equals("/")) {
      if (input2 == 0) { // method to stop program from dividing by 0
        throw new ArithmeticException();
      }
      return input1 / input2;
    } else if (operator.equals("*")) {
      return input1 * input2;
    } else if (operator.equals("%")) {
      return input1 % input2;
    // } else if (operator.equals("^")) {
    //  return Math.pow(input1, input2);}
    // ^ This is returning an error for "incompatible types: possible lossy conversion from double to long". Need more time to figure out how to complete this function.
    }  else {
      /*
       * For an innapropriate argument it makes sense to add
       * 'IllegalArgumentException' instead of just 'Exception'.
       */
      throw new IllegalArgumentException();
    }

  }

  /*
   * Function to check the operator is a boolean expression
   */
  private boolean checkOperation(String s) {
    for (int i = 0; i < operator.length; i++) {
      if (s.equals(operator[i])) {
        return true;
      }
    }
    return false;
  }
}

/*
 * Functionality to be added:
 * 
 * - white space:
 * 
 * Need to include functionality to use white space between numbers on the same
 * line and read them as separate numbers to be added to the stack... eg: 5 6 +
 * = 11
 * 
 * Can be achieved by splitting the string?
 * 
 * - #:
 * 
 * If typed into terminal after a calculation it returns this error message
 * 
 * - "Unrecognised operator or operand "�". -
 * "Unrecognised operator or operand "#".
 * 
 * Seems to work as a 'break' for the programs calculations to allow the user to
 * make notes e.g: 1 2 + = 3 # this is a comment #
 * 
 * Would therefore need to imlement a 'break' statement within a for loop when checking the inputs/operands.
 * 
 * - Size limit of stack:
 * 
 * Found that the max amount of numbers the stack can hold is 23 after testing.
 * This will need to be implemented and error messages added from
 * exceptions/throws/catches to print: "Stack overflow."
 * 
 * - 'r':
 * 
 * If 'r' is entered four times eg: 'rrrr' then the fourth element in the 'r'
 * array is * is printed. So far only the function to print out each element in
 * order has been achieved.
 */