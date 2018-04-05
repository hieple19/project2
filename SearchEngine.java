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
    private PriorityQueue<String> searchWords;
    private PriorityQueue<Website> results;

    public static void main(String[] args){
        ExcludedListReader excludeRead = new ExcludedListReader("excludedList.txt");
        excludeRead.readExcluded();
        InputFileReader input = new InputFileReader("textFileNames.txt",excludeRead);
        input.readFile();

        Database ds = new Database();
        ds.readData(input);
        ds.print();

        SearchEngine s1 = new SearchEngine(ds);
        s1.input();

    }

    public SearchEngine(Database ds){
        this.ds = ds;
    }

    public void input(){
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine(); 
        String[] inputs = line.split(" or ");
        this.searchWords = new PriorityQueue<String>();
        this.results = new PriorityQueue<Website>();

        if(inputs.length == 1){
            String[] keyWords = inputs[0].split(" and ");
            keyWords = trim(keyWords);

            for(String keyWord: keyWords){
                this.searchWords.add(keyWord);
            }

            this.results = ds.searchWords(this.searchWords);

        }
        else if(inputs.length == 2){
            String[] firstClause = inputs[0].split(" and ");
            firstClause = trim(firstClause);

            String[] secondClause = inputs[1].split(" and ");
            secondClause = trim(secondClause);

            for(String keyWord: firstClause){
                this.searchWords.add(keyWord);
            }
            System.out.print("search words 1 " + searchWords);
            PriorityQueue<Website> resultFirst = ds.searchWords(this.searchWords);
            System.out.println(resultFirst);
            this.searchWords.clear();
            for(String keyWord: secondClause){
                this.searchWords.add(keyWord);
            }
            PriorityQueue<Website> resultSecond = ds.searchWords(this.searchWords);
            
            this.results = this.mergeResults(resultFirst,resultSecond);

        }
        this.printResult();
    }

    public PriorityQueue<Website> mergeResults(PriorityQueue<Website> p1, PriorityQueue<Website> p2){
        for(Website toAdd: p2){
            p1.add(toAdd);
        }
        return p1;
    }
    
    public void printResult(){
        for(Website w: this.results){
            w.printResult();
        }
    }

    public String[] trim(String[] strings){
        String[] result = new String[strings.length];
        for(int i = 0; i<result.length; i++){
            String toAdd= strings[i].trim();
            result[i] = toAdd;
        }
        return result;
    }

}
