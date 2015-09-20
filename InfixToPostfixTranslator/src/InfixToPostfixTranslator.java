import java.util.*;
import javax.swing.*;

public class InfixToPostfixTranslator {
    public static void main(String[] args) {
	String expression;
	String token;
	String operator;
	StringTokenizer tokens;
	int parenthesis=0;

	//create an output queue
	LinkedList<String> postfixQueue = new LinkedList<String>();

	//create empty stack of pending string operators
	Stack operatorStack = new Stack();

	expression = JOptionPane.showInputDialog( null, "Enter infix expression", "Input Dialog", JOptionPane.PLAIN_MESSAGE);
	tokens = new StringTokenizer(expression);

	while (tokens.hasMoreTokens()) {  //hasn't gone thru all the "tokens"
	    token = tokens.nextToken();    //get next "token" from input string
	    if (isNumeric(token)) {  //it's an operand (a number)
	        //extract int from String token, create Integer object, add token to queue:
	        postfixQueue.addLast(token);
	    }
	    else if (isOperator(token)) { //token is an operator
	    	if (token.equals("(")) { //token is "("
	    		parenthesis++; //for status of parentheses
	    		operatorStack.push(token); //push token to stack
	    	} else if (token.equals(")")) {
	    		parenthesis++; //for status of parentheses
	    		while (true) {
	    			operator = (String) operatorStack.pop(); //pop until "(" found
	    			if (operator.equals("(")) {
	    				break;
	    			} else {
		    			postfixQueue.addLast(operator);
	    			}
	    		}
	    	} else if (operatorStack.isEmpty()) {
	    		operatorStack.push(token); //push token to stack
	    	} else {  //if operatorStack is not empty
	    		String stackTop = (String) operatorStack.peek(); //get the stack top value
	    		//token has higher precedence than stack top
	    		if (operatorPrecedence(token) > operatorPrecedence(stackTop)) {
	    			operatorStack.push(token);
	    		//token has same or lower precedence than stack top
	    		} else if (operatorPrecedence(token) == operatorPrecedence(stackTop)
	    				|| operatorPrecedence(token) < operatorPrecedence(stackTop)) {
	    			if (parenthesis % 2 != 0) { //not closed parenthesis
	    				operatorStack.push(token);
	    			} else if (parenthesis == 0) { //there is no parenthesis
	    				if (operatorPrecedence(token) < operatorPrecedence(stackTop)) {
			    			operator = (String) operatorStack.pop();
			    			postfixQueue.addLast(operator);
		    				int count = postfixQueue.size();
		    				if (count % 2 != 0) {
		    					operatorStack.push(token);
		    				} else {
				    			postfixQueue.addLast(token);
		    				}
		    			} else if (operatorPrecedence(token) == operatorPrecedence(stackTop)) {
			    			operator = (String) operatorStack.pop();
			    			postfixQueue.addLast(operator);
		    				operatorStack.push(token);
		    			}
	    			} else { //closed parenthesis
		    			operator = (String) operatorStack.pop();
		    			postfixQueue.addLast(operator);
		    			operatorStack.push(token);
	    			}
	    		}
	    	}
	    } else { //token is neither a number nor an operator
	    	System.out.println(token + " is not verified character.");
	    }
	}
	//pop all the rest of operators off stack, and add them to queue
	while (!operatorStack.isEmpty()) {
		operator = (String) operatorStack.pop();
		postfixQueue.addLast(operator);
	}

	//display both infix expression and postfix expression
	System.out.println("Infix Expression : " + expression);
	System.out.print("Postfix Expression : ");
	for (int i=0; i < postfixQueue.size(); i++) {
		System.out.print(postfixQueue.get(i) + " ");
	}
	System.exit(0);
    }

    static boolean isNumeric(String n) {
    	try {
    		Double.parseDouble(n);
    		return true;
    	} catch (NumberFormatException e) {
    		return false;
    	}
    }

    static boolean isOperator(String t) {
    	if (t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/") ||
    	    t.equals("%") || t.equals("(") || t.equals(")"))
    	    return true;
    	else
    	    return false;
    }

    static int operatorPrecedence(String o) {
    	if (o.equals("*") || o.equals("/")) {
    		return 2;
    	} else {
    		return 1;
    	}
    }
}