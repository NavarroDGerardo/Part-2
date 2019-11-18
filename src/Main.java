/**
 *
 *This is a Java program that read form a file the elements
 *that define a grammar and that indicates if a string
 *is accepted by the grammar.
 *
 *@author Diego Gerardo Navarro Gonzalez A01338941
 *@author Alan Omar Zavala Levaro A01338448
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.*;
import java.io.*;

public class Main {

    //Here we create a Hashmap that will store all the transitions of the grammar
    //the key of the Hashmap will be the non-terminal symbols and the content of it will be
    //a list of string with all the productions of the non terminal symbols
    private static HashMap<Character, ArrayList<String>> transitions = new HashMap<>();
    //Here we store the final result (true/false)
    private static boolean solution = false;

    public static void main(String[] args) throws IOException{
        System.out.println("papa");
        File file; //this is the variable of the file that we will read
        Scanner scan; //this is the variable of the scanner that we need to read the file.txt

        //We need to add a try and catch function to read the file in case we don't find it
        try{
            //We initialize the file variable with the path of the file
            file = new File("test3.txt");
            //we need to add to the scanner the file so we can read it
            scan = new Scanner(file);
        }catch(FileNotFoundException e1){
            //in case we don't find the file we will throw the IOException
            throw e1;
        }

        String s = ""; //this variable will contain the initial non-terminal symbol of the grammar

        //we check if the scan has a next line
        if(scan.hasNext()){
            System.out.println("set of non-terminal symbols: " + scan.next()); //we print the list of non-terminal symbols
            System.out.println("set fo terminal symbols: " + scan.next()); //we print the list of terminal symbols
            s = scan.next(); //we jump to the other line;
        }

        Character start = s.charAt(0); //we create a variable with the non-terminal symbol that we read form the .txt file
        System.out.println("Start point: " + start);// we print the char that will be the initial point of the grammar

        while(scan.hasNext()){ //while we have lines to read we will continue reading the file
            String t = scan.next(); //this String will have the productions of the grammar
            Character key = t.charAt(0); // This cahr will be the key of the Hashmap
            String content = t.substring(3); //this string will be contain the production of the non-terminal symbol
            System.out.println("content: "+ content);
            if(!transitions.containsKey(key)){ //if the key is not in the hashmap it means we need to add it to the hashmap
                transitions.put(key, new ArrayList<String>());//we initialize a new arraylist to the new key
            }
            if(content.equals("lmd")){
                transitions.get(key).add("{");
                Collections.sort(transitions.get(key));
            }
            else {
                transitions.get(key).add(content);//we add the production to the arraylist of the key
                Collections.sort(transitions.get(key));
            }
        }

        //after we have read all the .txt we will do block of code
        //first we will display the productions of the grammar
        System.out.println("Production of the grammar: " + transitions);
        //we will create a new Stack variable so we can add all the Productions of the grammar
        Stack<Character> PDA = new Stack<>();
        //we add to the stack the initial non-terminal symbol
        PDA.add(start);
        String testString = "b";//tenemos que cambiar esta linea por un scanner para que usuario lo ponga
        //we will print the final result of the method that will check if the string is produced by the grammar
        checkString(PDA, testString);
        System.out.println(solution);
    }

    //this function will check if the string is produced by the grammar
    private static boolean checkString(Stack<Character> PDA, String testString){
        System.out.println(PDA + " -> " + testString);

        if(solution) return true;
        
        //if the string is empty or the stack of production is empty we it means this si not the correct path
        if(testString.equals("") && !PDA.empty()) return false;
        if(!testString.equals("") && PDA.empty()) return false;
        //in case we have a empty production Stack and a empty string it means we have reach the string that we want
        //and we need to return true
        if(PDA.empty() && testString.equals("")) {
            solution = true;
            return true;
        }

        //first we need to pop a char from the production stack
        char c = PDA.pop();
        System.out.println("char to "+ c);
        if(c == '{'){
            System.out.println("Alan");
            c = PDA.pop();
            System.out.println("Encontramos esto: "+c);
            if(PDA.empty() && testString.equals("")){
                System.out.println("Paola");
                solution = true;
                return true;
            }
        }
        //if the char is in the Hashmap it means that the char is a non-terminal symbol so we have productions to follow
        if(transitions.containsKey(c)){
            //we initialize a StringBuilder because we need to append the productions of the non-terminal symbols
            StringBuilder sb = new StringBuilder();
            //we add to the string builder the rest of the production stack
            while (!PDA.empty()){
                sb.append(PDA.pop());
            }
            //we need to add a for so we can send all the different productions of the key
            for(int i = transitions.get(c).size() - 1; i >= 0; i--){
                //we create a new stack that will be the new production that we will send to the funcion
                Stack<Character> auxStack = new Stack<>();

                //we add all the chars form the string builder to the new stack
                for(int k=sb.length() - 1; k>= 0; k--){
                    auxStack.add(sb.charAt(k));
                }

                //this variable will contain the transition of the key in the position i
                String s = transitions.get(c).get(i);

                //we add all the chars from the production string in the position i to the new stack
                for(int j = s.length()-1; j >= 0; j--){
                    auxStack.add(s.charAt(j));
                }
                //we print the new stack
                System.out.println(auxStack);
                checkString(auxStack, testString); // we send to the checkString the new stack of production
            }
        }else{
            //in case the char that we pop isn't in the Hashmap it means that is a terminal symbol
            //if the char is the same as the char at the position 0 in the string that we want to validate
            //this means is a possible path to reach this string
            
            if(c == testString.charAt(0)){
                //we eliminate the char at the position 0
                testString = testString.substring(1);
                //we send the new string with the new production to the checkString method
                checkString(PDA, testString);
            }
        }


        return false;
    }
}
