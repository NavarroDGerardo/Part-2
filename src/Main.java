import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;

public class Main {
    private static Stack<Character> pushdown = new Stack<>();
    private static HashMap<Character, ArrayList<String>> transitions = new HashMap<>();
    private static String testString = "";
    private static int index = 0;

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
        pushdown.add(start);
        checkString();
    }

    private static boolean checkString(){
        if(pushdown.isEmpty() && index ==0)
        return false;
    }
}
