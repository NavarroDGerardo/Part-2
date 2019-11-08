import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;

public class Main {
    //private static Stack<Character> pushdown = new Stack<>();
    private static HashMap<Character, ArrayList<String>> transitions = new HashMap<>();

    public static void main(String[] args) throws IOException{
        File file;
        Scanner scan;
        try{
            file = new File("C:/Users/Navarro D. Gerardo/Documents/Quinto Semestre/mate computacional/Part2/src/test1.txt");
            scan = new Scanner(file); //the scanner will read the file;
        }catch(FileNotFoundException e1){
            throw e1;
        }

        String s = "";

        if(scan.hasNext()){
            System.out.println("set of not terminal symbols: " + scan.next());
            System.out.println("set fo terminal symbols: " + scan.next());
            s = scan.next();
        }

        Character start = s.charAt(0);
        System.out.println("Start point: " + start);

        while(scan.hasNext()){
            String t = scan.next();
            Character key = t.charAt(0);
            String content = t.substring(3);

            if(!transitions.containsKey(key)){
                transitions.put(key, new ArrayList<String>());
            }
            transitions.get(key).add(content);
        }

        System.out.println("Production of the grammar: " + transitions);
        Stack<Character> PDA = new Stack<>();
        PDA.add(start);
        String testString = "babbb";
        System.out.println(checkString(PDA, testString));
    }

    private static boolean checkString(Stack<Character> PDA, String testString){
        if(PDA.empty() && testString.equals("")) return true;
        if(testString.equals("") && !PDA.empty()) return false;
        int index = 0;

        while(!PDA.empty() || index < testString.length()){
            System.out.println(PDA + " testString: " + testString);
            char c = PDA.pop();
            if(transitions.containsKey(c)){
                for(int i = 0; i < transitions.get(c).size(); i++){
                    String s = transitions.get(c).get(i);
                    System.out.println(c + " -> " + s);
                    System.out.println("PDA: " + PDA);
                    Stack<Character> auxStack = PDA;
                    for(int j = s.length()-1; j >= 0; j--){
                        auxStack.add(s.charAt(j));
                    }
                    checkString(auxStack, testString);
                }
            }else{
                if(c == testString.charAt(index)){
                    testString = testString.substring(index+1);
                }else{
                   return false;
                }
            }
            index++;
        }

        return false;
    }
}
