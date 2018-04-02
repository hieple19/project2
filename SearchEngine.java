import java.util.*;
import java.io.*;

/**
 * Class handles user input and keywords
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SearchEngine
{
    private Database ds;

    public SearchEngine(Database ds){
        this.ds = ds;
    }

    public void input(){
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine(); 
        String[] inputs = line.split("or");

        if(inputs.length == 2){
            String[] firstClause = inputs[0].split("and");
            String[] secondClause = inputs[1].split("and");
            trim(firstClause);
            trim(secondClause);                      
        }
    }
    
    

    /*public searchWithOr(String[] words, int indexOfOr){
    ArrayList<String> firstClause = new ArrayList<String>();
    for(int i = 0; i<indexOfOr; i++){
    if(i%2

    }
    }
     */
    public String[] trim(String[] strings){
        for(String s: strings){
            s.trim();
        }
        return strings;
    }

}
