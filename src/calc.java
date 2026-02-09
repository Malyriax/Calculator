import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
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
public calc(){
    setTitle("calc (short for calculator");
    setSize(400, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    field = new JTextField();
    field.setEditable(true);
    add(field, BorderLayout.NORTH);
    JPanel buttons = new JPanel();
    buttons.setLayout(new GridLayout(5, 4, 8,8));
    String[] nums = {
        "1", "2", "3", "/", "4", "5", "6", "*", "7", "8", "9", "-", "C", "0", "=", "+", ".", "^", "(", ")"
    };
    for(int i = 0; i < nums.length; i++){
        JButton btn = new JButton(nums[i]); 
    buttons.add(btn);
    }
    add(buttons, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent buttonPressed){
        String whatBroPressed = buttonPressed.getActionCommand();
        if("1234567890.".contains(whatBroPressed)){
            field.setText(field.getText() + whatBroPressed);
            
        } 
        else if("C".contains(whatBroPressed)){
            firstNum = 0;
            secondNum = 0;
            smoothOperator = null;
            field.setText("");
            // дописать все прочие переменные
        }
        else if("*+-/^()".contains(whatBroPressed)){
            field.setText(field.getText() + whatBroPressed);

        }
        else if("=".contains(whatBroPressed)){
        field.setText((resultString));
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
    public static String[] createUsableForm(String finalInput){
        String[] arrayOfOperatorsAndOperands = new String[finalInput.length() * 2];
        java.util.Arrays.fill(arrayOfOperatorsAndOperands, "");
        int amountOfNumbers = 0; 

        for(int i = 0; i < finalInput.length(); i++){
            String currentSimbol = String.valueOf(finalInput.charAt(i));
            if("0123456789.".contains(currentSimbol)){
                arrayOfOperatorsAndOperands[amountOfNumbers] = arrayOfOperatorsAndOperands[amountOfNumbers] + currentSimbol;
            }
            else if("*+/-^".contains(currentSimbol)){
                amountOfNumbers = amountOfNumbers + 1;
                arrayOfOperatorsAndOperands[amountOfNumbers] = currentSimbol;
                amountOfNumbers = amountOfNumbers + 1;
            }
        }
        return arrayOfOperatorsAndOperands;
        }


    public static String[] removeBraces(String[] input){
        int amountOfOddValues = 1;
        String[] brace = input;
        for(int i = 0; i < brace.length; i++){
            if(brace[i].equals("(") || brace[i].equals(")")){
                for(int j = i; j < brace.length - 1; j++){
                    brace[j] = brace[j + 1];
                }
            brace[brace.length - amountOfOddValues] = "";
            amountOfOddValues++;
            i--;
            }
        }
        // step 2
        int extraSpaceCounter = 0;
        for(int i = 0; i < brace.length; i++){
            if(brace[i].equals("")){
                extraSpaceCounter++;
            }
        }
        String braceNoExtraSpace[] = new String[brace.length - extraSpaceCounter];
        int insertIndex = 0;
        for(int i = 0; i < braceNoExtraSpace.length; i++){
            if(!brace[i].equals("")){
            braceNoExtraSpace[insertIndex] = brace[i];
            insertIndex++;
            }
        }
    return braceNoExtraSpace;
    }



    public static void main(String[] args) throws Exception {
       // calc newcalc = new calc();
      //  newcalc.setVisible(true);
     String test = "((((5+2)-(2-5)+4";
        validInput(test);
        if(reasonableInput == false){
            resultString = "Invalid input, check amount of braces";
            System.out.println(resultString);
        } else {
    String act1 = addBracesIfNeeded(test);
    System.out.println("Fixed String: " + act1); // Проверяем, добавились ли скобки
    
    String[] act2 = parceByBraces(act1);
    System.out.println("Braces found: " + Arrays.toString(act2)); // Смотрим ВСЕ найденные скобки
    
    String[] act3 = removeBraces(createUsableForm(act2[2])); 
    System.out.println(": " + Arrays.toString(act3)); // Смотрим на числа без скобок
        
        
    }
}
}

