/**
 * Program Name: CS Course Information
 * Author:       Grace Dong
 * Date:         1 Nov, 2024
 * Course:       CPSC1150
 * Compiler:     JDK 22.0.2
 */

 /**
  * This program accesses to a text file with student records, carry out various operations at user's choices.
  */

import java.util.*;
import java.io.*;

public class CSCourseInformation{

    /**
     * The main class displays menu, uses switch case to invoke different methods according to user input. It handles invalid int input and also terminates the program.
     */
    public static void main(String[] args) throws IOException{
        
        Scanner input = new Scanner(System.in);
        //Initialise a empty string to newFileName
        String newFileName = "";

        //Create infinite loop to display menu and get user input
        while(true){
            displayMenu();
            int selection = input.nextInt();
            
            //Switch cases invoke methods accordingly
            switch (selection) {
                case 1:
                    displayRecord();
                    break;
                case 2:
                    finalAverage();
                    break;
                case 3:
                    highestScore();
                    break;
                case 4:
                    newFileName = copyFile(); //Assign the returned value to variable newFileName
                    break;
                case 5: 
                    System.out.println("Program terminates.");
                    System.out.println("The file " + newFileName + " has been created for you.");
                    selection = 0;
                    return;
                default:
                    System.out.println("Invalid input.");    
            }
        }
    }

    //A method to display the menu
    public static void displayMenu(){

        System.out.println("Please enter: ");
        System.out.println("1. To display a specific student's record.");
        System.out.println("2. To calculate the final exam average.");
        System.out.println("3. To find a student with the highest score on the final exam.");
        System.out.println("4. To copy the student's record to another file.");
        System.out.println("5. To terminate the program.");
    }

    //A method to display a specific student's record
    public static void displayRecord() throws IOException{
        
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the student's name: ");
        String inputName = input.nextLine();
        
        Scanner inFile = new Scanner(new File("classList.txt"));

        boolean found = false; //Initialise a boolean as false
        
        while(inFile.hasNextLine()){

            String line = inFile.nextLine();
            if(line.contains(inputName)){
                displayInfo(line); //Invoke method displayInfo and pass 'line' as parameter
                found = true;  //Toggles the boolean to true when item found
            }  
        }
        //if found remains false after iterating thru array: !found -> true -> printing no match 
        if(!found){
            System.out.println("No match.");
        }
        inFile.close();        
    }

    //This method receives a line of String of student record, splits and displays the info
    public static void displayInfo(String line){

        int index1 = line.indexOf(":");
        int index2 = line.lastIndexOf(":");

        String id = line.substring(0, index1);
        String name = line.substring(index1+1, index2);
        String score = line.substring(index2+1);

        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Final: " + score);
    }
    
    //This method reads through file, picks up the scores from each line then calculates the avearage score
    public static void finalAverage() throws IOException{

        Scanner inFile = new Scanner(new File("classList.txt"));

        int scoreSum = 0;
        int studentNumber = 0;

        while(inFile.hasNextLine()){

            String line = inFile.nextLine();
            int index = line.lastIndexOf(":");
            String strScore = line.substring(index+1);

            try{
                int score = Integer.parseInt(strScore);
                scoreSum += score;
                studentNumber++;
            }catch(NumberFormatException e){
                //Ignore the invalid score and continue with next line
            }
        }
        double average = (double)scoreSum / studentNumber;
        System.out.println("The final exam average is: " + average);

        inFile.close();
    }

    //This method reads through each line of the file, finds the highest score, stores the line info and displays it
    public static void highestScore() throws IOException{

        Scanner inFile = new Scanner(new File("classList.txt"));

        int highestScore = 0;
        String highestScoreInfo = "";

        while(inFile.hasNextLine()){

            String line = inFile.nextLine();
            int index = line.lastIndexOf(":");           
            String strScore = line.substring(index+1);

            try{
                int intScore= Integer.parseInt(strScore);
                if(intScore > highestScore){
                    highestScore = intScore;
                    highestScoreInfo = line; //Store the line into highestScoreInfo
                }
            }catch(NumberFormatException e){
                //Ignore the invalid score and continue with next line
            }
        }

        System.out.println("The student with highest score of final exam: ");
        displayInfo(highestScoreInfo); //Invoke displayInfo method
        
        inFile.close();
    }
 
    //This method let user enter the new file name, create the file and copy the texts into it. It returns the new file name back to main class.
    public static String copyFile() throws IOException{

        Scanner input = new Scanner(System.in);
        Scanner inFile = new Scanner(new File("classList.txt"));

        System.out.println("Please enter the file name: ");
        String newFile = input.nextLine();
        PrintWriter outFile = new PrintWriter(new File(newFile));

        while(inFile.hasNextLine()){
            String line = inFile.nextLine();
            outFile.println(line);
        }
        System.out.println("New file " + newFile + " has been created.");

        inFile.close();
        outFile.close();
        
        return newFile;
    }
}