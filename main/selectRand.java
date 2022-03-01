/**
 * Selects 1000 random elements from a list of entries from
 * the GISAID-database, ready to be selected in the 
 * GISAID-database afterwards to download specified (random)
 * DNA-strings. 
 */

package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class selectRand
{  
    public static void main(String[] args) throws IOException{        

        PrintWriter out = new PrintWriter("main/gisaidOutput.txt");
        String filePath = "main/dataInputs.txt"; 
        String input = usingBufferedReader(filePath);

        String splitStringOld[] = input.split(","); 
        String splitString[] = new String[splitStringOld.length];
    
        //Trim each string to remove blankspaces
        for(int i = 0; i<splitString.length; i++){
            splitString[i] = splitStringOld[i].trim();
        } 
        
        //Copy String[] to arrayList and create temp list
        List<String> stringList = Arrays.asList(splitString);  
        List<String> randomizedStringList = new ArrayList<String>();
        
        Random random = new Random();    

        //Create a list of 1000 elements from the list of all entries. If it contains
        //entries with an interval, randomize an element from that interval.
        //All elements must be unique
        //Prints the new entries to a .txt file with the correct formating
        while(randomizedStringList.size() < 1000){
            String foo = stringList.get(random.nextInt(stringList.size())); 
            if(foo.contains("-")){ 
                String randomIntervalFoo = randomInterval(foo);               
                if(!randomizedStringList.contains(randomIntervalFoo)){
                    randomizedStringList.add(randomIntervalFoo);
                    if(randomizedStringList.size() <= 999){
                        out.println(randomIntervalFoo + ", ");
                    }
                    else{
                        out.println(randomIntervalFoo);
                    }                
                }                
            }
            else{
                if(!randomizedStringList.contains(foo)){
                    randomizedStringList.add(foo);
                    if(randomizedStringList.size() <= 999){
                        out.println(foo + ", ");
                    }
                    else{
                        out.println(foo);
                    }     
                }                
            }
        }
        out.close();      
    }

    /**
     * Randomizes a header so that its no longer an interval, but a random item within
     * that interval. Eg EPI_ISL_1698273-1698827 returns EPI_ISL_XXXXXX[273-827] where the
     * brackets indicate a random number in that interval.
     * @param input a header with an interval
     * @return the header with a random item in that interval
     */
    private static String randomInterval(String input){

        Random rand = new Random();
        
        String temp[] = input.split("-");
        int noNumFirst = Integer.parseInt(temp[0].replaceAll("[^0-9]", ""));
        int noNumSecond = Integer.parseInt(temp[1].replaceAll("[^0-9]", ""));
        int dif = noNumSecond-noNumFirst;

        return "EPI_ISL_" + (noNumFirst+rand.nextInt(dif+1));        
    }

    /**
     * Converts a .txt file into a java String. BufferedReader reads one line at a time
     * @param filePath the file path of the String
     * @return the txt file expressed as a String
     */
    private static String usingBufferedReader(String filePath){
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){ 

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null){ 
                contentBuilder.append(sCurrentLine).append("\n"); 
            }
        } 
        catch (IOException e){
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
