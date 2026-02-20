import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
public class calc extends JFrame implements ActionListener {

JTextField field;
String smoothOperator;
double firstNum = 0;
double secondNum = 0;
static double result;
static String resultString = String.valueOf(result); 
static int amountOfLeftBraces = 0;
static int amountOfRightBraces = 0;
static boolean reasonableInput;
static int bracesDifference = 0;
String equationEnoughBraces;
static String errorMessage = "Error!";
public calc(){
    setTitle("calc (short for calculator");
    setSize(400, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    field = new JTextField();
    field.setEditable(false);
    add(field, BorderLayout.NORTH);
    field.setFont(new Font("Arial", Font.BOLD, 22));
    JPanel buttons = new JPanel();
    buttons.setLayout(new GridLayout(5, 4, 8,8));
    String[] nums = {
        "1", "2", "3", "/", 
        "4", "5", "6", "*", 
        "7", "8", "9", "-", 
        "C", "0", "=", "+", 
        ".", "^", "(", ")"
    };
    for(int i = 0; i < nums.length; i++){
        JButton btn = new JButton(nums[i]); 
        btn.addActionListener(this);
    buttons.add(btn);
    }
    add(buttons, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent buttonPressed){
        String whatBroPressed = buttonPressed.getActionCommand();
        String currentText = field.getText();
        if(field.getText().contains("Error")){
            field.setText("");
        }
        if("1234567890".contains(whatBroPressed)){
            field.setText(field.getText() + whatBroPressed);
        } 
        if(".".equals(whatBroPressed)){
            if (currentText.isEmpty()){
                field.setText("0.");
                return;
            } 
             
            char lastSymbol = currentText.charAt(currentText.length() - 1);
            if (!"0123456789".contains(String.valueOf(lastSymbol))) {
                return; 
            }
            boolean thereIsNoDotAlready = false;
            for (int i = currentText.length() - 1; i >= 0; i--) {
                char currentChar = currentText.charAt(i);
                if(currentChar == '.'){
                    thereIsNoDotAlready = false;
                    break;
                }
                if("*+-/^()".contains(String.valueOf(currentChar))){
                    thereIsNoDotAlready = true;
                }
                if(thereIsNoDotAlready == true){
                    field.setText(currentText + whatBroPressed);
                }
            }
            
            }

        
        else if("C".contains(whatBroPressed)){
            firstNum = 0;
            secondNum = 0;
            smoothOperator = null;
            field.setText("");
            amountOfLeftBraces = 0;
            amountOfRightBraces = 0;
            reasonableInput = false;
            // дописать все прочие переменные
        }
        else if("*+-/^()".contains(whatBroPressed)){
            if (currentText.isEmpty()) {
                if ("+-.".contains(whatBroPressed)) {
                    field.setText(whatBroPressed);
                }
                return; 
        }
        char lastChar = currentText.charAt(currentText.length() - 1);
        boolean currentSimbolIsOperator = "*+-/^.".contains(whatBroPressed);
        boolean previousSimbolWasOperator = "*+-/^.".contains(String.valueOf(lastChar));
        if (currentSimbolIsOperator && previousSimbolWasOperator) {
                field.setText(currentText.substring(0, currentText.length() - 1) + whatBroPressed);
    } else field.setText(currentText + whatBroPressed);
}
        else if("=".contains(whatBroPressed)){
            validInput(currentText);
            if (!reasonableInput) {
                field.setText("Error: Input does not make sence");
            } else {
                try {
                    String act1 = fixHiddenMult(addBracesIfNeeded(currentText));
                    String[] act2 = parceByBraces(act1);
                    String[] act3 = solveAllBraces(act2);
                    String result = act3[act3.length - 1];
                    if(result.charAt(result.length() - 1) == '0' && result.charAt(result.length() - 2) == '.'){
                        result = result.substring(0, result.length() - 2);
                    }
                    field.setText(result);
                } catch (ArithmeticException mathError) { 
                    field.setText("Error: You cannot divide by zero");
                }
                catch (Exception exception)
                    {
                field.setText("Error: Incorrect Input");
            }   
        }
        }
    }
 



    // checks if there is more(if there is more it fixes it later) or equal opening braces than closing ones and sets true if input is valid, also counts braces and sets
    // variables that are being used later
    public static void validInput(String finalInput){
        amountOfLeftBraces = 0;
        amountOfRightBraces = 0;
        for(int i = 0; i < finalInput.length(); i++){
            if(finalInput.charAt(i) == '('){
                amountOfLeftBraces++;
            }
            if(finalInput.charAt(i) == ')'){
                amountOfRightBraces++;
            }
            }
        int lockedamountOfLeftBraces = amountOfLeftBraces;
        int lockedamountOfRightBraces = amountOfRightBraces;
        if(amountOfRightBraces <= amountOfLeftBraces){
            reasonableInput = true;
        }
        int extraCounterOpen = 0;
        int extraCounterClose = 0;
        for(int i = 0; i < finalInput.length(); i++){
            if(finalInput.charAt(i) == '('){
                extraCounterOpen++;
            }
            if(finalInput.charAt(i) == ')' ){
                extraCounterClose++;
            }
            if(extraCounterOpen < extraCounterClose){
                reasonableInput = false;
                break;
            }
        }
        if(finalInput.contains("()")){
            reasonableInput = false;
        }
        boolean firstSimbolIsNotOperator = false;
        for(int i = 0; i < finalInput.length(); i++){
            if("0123456789".contains(String.valueOf(finalInput.charAt(i)))){
                firstSimbolIsNotOperator = true;
                break;
            }
            if("*/^".contains(String.valueOf(finalInput.charAt(i) )) && firstSimbolIsNotOperator == false){
                reasonableInput = false;
                break;
            }
        
        }
        boolean lastSimbolIsNotOperator = false;
        for(int i = finalInput.length() - 1; i>0; i--){
            if("0123456789".contains(String.valueOf(finalInput.charAt(i)))){
                lastSimbolIsNotOperator = true;
                break;
            }
            if("*/^".contains(String.valueOf(finalInput.charAt(i) )) && lastSimbolIsNotOperator == false){
                reasonableInput = false;
                break;
            }
        }
        String equationNoBracesNoMinus = finalInput.replace("(", "").replace("(", "").replace("-", "");
        if(equationNoBracesNoMinus.equals("")){
            reasonableInput = false;
        }
        if(addBracesIfNeeded(finalInput).contains("()")){
           reasonableInput = false; 
        }
        amountOfLeftBraces = lockedamountOfLeftBraces;
        amountOfRightBraces = lockedamountOfRightBraces;
    }

    

        // add closing braces if necessary
    public static String addBracesIfNeeded(String finalInput){

        String finalInputFixed = finalInput;
        bracesDifference = amountOfLeftBraces - amountOfRightBraces;
        if(reasonableInput == true && bracesDifference != 0){
            for(int i = 0; i < bracesDifference; i++){
                finalInputFixed = finalInputFixed + ")";
            }
        }
        amountOfRightBraces = amountOfLeftBraces;
        amountOfRightBraces++;
        amountOfLeftBraces++;
        return "(" + finalInputFixed + ")";
    }
        
    public static String fixHiddenMult(String input){
        StringBuilder fixedInput = new StringBuilder(input);
        for(int i = fixedInput.length() - 2; i >= 0; i--){
            char currentPosition = fixedInput.charAt(i);
            char currentNextPosition = fixedInput.charAt(i+1);
            if("0123456789.".contains(String.valueOf((currentPosition))) && currentNextPosition == '('){
                fixedInput.insert(i+1, "*");
            }
        }
        for(int i = fixedInput.length() - 1; i > 0; i--){
            char currentPosition = fixedInput.charAt(i);
            char currentPreviousPosition = fixedInput.charAt(i-1);
            if("0123456789.".contains(String.valueOf((currentPosition))) && currentPreviousPosition == ')'){
                fixedInput.insert(i, "*");
            }
        }
        for(int i = fixedInput.length() - 2; i > 0; i--){
            char currentPosition = fixedInput.charAt(i);
            char currentNextPosition = fixedInput.charAt(i+1);
            if(currentPosition == ')' && currentNextPosition == '('){
                fixedInput.insert(i+1, "*");
            }
        }
        return fixedInput.toString();
    }


        // returns an array of braces in consecutive order
    public static String[] parceByBraces(String preparedInput){
        int[] allLeftBraces = new int[amountOfLeftBraces];
        int[] allRightBraces = new int[amountOfRightBraces];
        int currentPosAtLeft = 0;
        int currentPosAtRight = 0;
        for(int i = 0; i < preparedInput.length(); i++){
            if(preparedInput.charAt(i) == ')'){
                allRightBraces[currentPosAtRight] = i;
                currentPosAtRight++;
            }     
        }
        for(int i = preparedInput.length() - 1; i >= 0; i--){
            if(preparedInput.charAt(i) == '('){
                allLeftBraces[currentPosAtLeft] = i;
                currentPosAtLeft++;
            }
        }
        String[] consecutiveBraces = new String[amountOfLeftBraces];
        // Arrays.binarySearch(array, key) если есть точная позиция возвращает индекс, если нет возвращает предпологаемый + 1 *-1
            for(int i = 0; i < amountOfLeftBraces; i++){
               int res = Arrays.binarySearch(allRightBraces, allLeftBraces[i]);
                int currentClosingBrace = -(res + 1);
                if (currentClosingBrace < allRightBraces.length) {
                    String temporal = preparedInput.substring(allLeftBraces[i], allRightBraces[currentClosingBrace] + 1);
                    consecutiveBraces[i] = temporal;
                    allRightBraces[currentClosingBrace] = -1;
                    Arrays.sort(allRightBraces);
                }
              } 
            return consecutiveBraces;
        }




     // creates an array where each index is operator or operanda  
    public static ArrayList<String> createUsableForm(String finalInput) {
    ArrayList<String> equation = new ArrayList<>();
    String currentNumber = "";
    String previousSimbol = "x";
    for (int i = 0; i < finalInput.length(); i++) {
        String currentSymbol = String.valueOf(finalInput.charAt(i));
        if(i > 0){
            previousSimbol = String.valueOf(finalInput.charAt(i - 1));
        }
        if ("0123456789.".contains(currentSymbol)) {
            currentNumber += currentSymbol;
        } 
        else if ("*+/^".contains(currentSymbol)) {
            if (!currentNumber.isEmpty()) {
            equation.add(currentNumber);
            currentNumber = ""; 
            }
            equation.add(currentSymbol);
        }
        else if ("-".contains(currentSymbol)){
            if("+-/*^(".contains(previousSimbol ) || i == 0){
                if(currentNumber.equals("-")){
                    currentNumber = "";
                }
                else currentNumber += "-";
            }
            else if("0123456789.".contains(previousSimbol)){
                equation.add(currentNumber);
            currentNumber = ""; 
            equation.add(currentSymbol);
            }
        }
    }
        if (!currentNumber.isEmpty()) {
        equation.add(currentNumber);
         }
    return equation;
    }
   
    // I just found out about ArrayList omfg
    public static ArrayList<String> solvePower(ArrayList<String> equation){
        int initialSizeOfList = equation.size();
        
        for(int i = initialSizeOfList - 1; i >= 0; i--){
            if(equation.get(i).equals("^")){
                double currentResult = Math.pow(Double.parseDouble(equation.get(i-1)), Double.parseDouble(equation.get(i+1)));
                equation.set(i - 1,String.valueOf(currentResult));
                equation.remove(i);
                equation.remove(i);
            }
        }
        return equation;
    }
    public static ArrayList<String> solveMultDiv(ArrayList<String> equation){
        int initialSize = equation.size() - 1;
        for(int i = initialSize; i >=0; i--){
            if(equation.get(i).equals("*")){
                 double currentResult = Double.parseDouble(equation.get(i-1)) * Double.parseDouble(equation.get(i+1));
                 equation.set(i - 1, String.valueOf(currentResult));
                equation.remove(i);
                equation.remove(i);
            }
            else if(equation.get(i).equals("/")){
                double num1 = Double.parseDouble(equation.get(i-1));
                double num2 = Double.parseDouble(equation.get(i+1));
                if(num2 == 0.0){
                    throw new ArithmeticException("You cannot divide by zero");
                }
                 double currentResult = num1 / num2;
                 equation.set(i - 1, String.valueOf(currentResult));
                equation.remove(i);
                equation.remove(i);
            }
        }
        return equation;
    }
    public static String solvePlusMinus(ArrayList<String> equation){
       if(equation.get(0).equals("+")){
            equation.add(0, "0");
        } 
        double firstValue = 0;
        // no need since negatives are now at the same index
      //  if(equation.get(0).equals("-")){
      //      equation.add(0, "0");
      //  } 
        firstValue = Double.parseDouble(equation.get(0));
        int initialSize = equation.size() - 1;
        for(int i = 0; i < equation.size() - 1; i++){
            if(equation.get(i).equals("+")){
               firstValue = firstValue +  Double.parseDouble(equation.get(i+1));
            }
            if(equation.get(i).equals("-")){
               firstValue = firstValue -  Double.parseDouble(equation.get(i+1));
            }
        }
        return String.valueOf(firstValue);
    } 
    // here you can easily add any operands
    public static String solveTheBrace(ArrayList<String> preparedArray){
        String result = solvePlusMinus(solveMultDiv(solvePower(preparedArray)));
        return result;
    }
    public static String[] solveAllBraces(String[] preparedArray){

        for(int i = 0; i < preparedArray.length; i++){
        String currentString = preparedArray[i];
        ArrayList<String> currentStringParced = createUsableForm(currentString);
        String currentSolution = solveTheBrace(currentStringParced);
        // String currentSolutionBracesFailSafe = "(" + currentSolution + ")";
        for(int j = 0; j < preparedArray.length; j++){
            preparedArray[j] = preparedArray[j].replace(currentString, currentSolution);
        }
        }
        
        return preparedArray;
    }
    
    public static void main(String[] args) throws Exception {
       calc newcalc = new calc();
       newcalc.setVisible(true);
    // String test = "5/0";
     //String test = "-2.5^2+(-4*--0.5)-(-(-2^3)/--1)";
     //   validInput(test);
    //    if(reasonableInput == false){
     //       resultString = "Invalid input, check amount of braces";
       //     System.out.println(resultString);
      //  } else {
  //  String act1 = fixHiddenMult(addBracesIfNeeded(test));
 //  System.out.println("Fixed String: " + act1); 
    
  //  String[] act2 = parceByBraces(act1);
 // System.out.println("Braces found: " + Arrays.toString(act2));
   
  //  String[] act3 = solveAllBraces(act2);
  // System.out.println(Arrays.toString(act3));

   // System.out.println(act3[act3.length - 1]);
      // System.out.println(solveMultDiv(createUsableForm(("--3*2 + 5)")))) ;
      // System.out.println(Double.parseDouble("-5"));
    }
}


