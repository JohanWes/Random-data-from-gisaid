/**
 * Selects 1000 random elements from a list of entries from
 * the GISAID-database, ready to be selected in the 
 * GISAID-database afterwards.
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
    
        for(int i = 0; i<splitString.length; i++){
            splitString[i] = splitStringOld[i].trim();
        } 

        List<String> stringList = Arrays.asList(splitString);  
        List<String> randomizedStringList = new ArrayList<String>();
        
        Random random = new Random();    
        while(randomizedStringList.size() < 1000){
            String foo = stringList.get(random.nextInt(stringList.size())); 
            if(!foo.contains("-")){
                if(!randomizedStringList.contains(foo)){
                    randomizedStringList.add(foo);
                }
            } 
        }

        for(int i = 0; i < 999; i++){            
            out.println(randomizedStringList.get(i) + ", ");
        }
        out.println(randomizedStringList.get(999));            

        out.close();       
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