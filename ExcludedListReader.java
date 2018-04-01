
import java.util.*;
import java.io.*;

/**
 * Class handles the reading of input text files
 *
 */
public class ExcludedListReader
{   
    public static void main(String[] args){
        ExcludedListReader excludeRead = new ExcludedListReader("excludedList.txt");
        excludeRead.readExcluded();
        excludeRead.print();
        
    }
    protected ArrayList<String> excludedWord;
    private Scanner scanner;

    /**
     * Constructor for objects of class InputFile
     *
     */
    
    public ExcludedListReader(String dir){
        try{
            FileReader fileReader = new FileReader(dir);
            scanner = new Scanner(fileReader);
        }
        catch(Exception e){
            System.out.println("Cannot read file");
            e.printStackTrace(System.out);
        }
    }
    
    public void readExcluded(){
        this.excludedWord = new ArrayList<String>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            line.trim();
            excludedWord.add(line);
        }
    }
    
    public ArrayList<String> excludedList(){ return this.excludedWord;}
    
    public void print(){
        for(String s: this.excludedWord){
            System.out.println(s);
        }
        System.out.println(excludedWord.size());
        
    }
            
    
}
