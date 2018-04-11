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
    private boolean search;
    private boolean exit;

    public static void main(String[] args){
        InputFileReader input = new InputFileReader("textFileNames.txt","excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();

        Database ds = new Database();
        ds.readData(input);
        //ds.print();

        SearchEngine s1 = new SearchEngine(ds);
        s1.run();
    }

    public SearchEngine(Database ds){
        this.ds = ds;
        this.search = false;
    }

    public void checkContinue(){
        System.out.println("Press Y to start search");
        System.out.println("Press any other to exit");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if(!line.toLowerCase().equals("y")){
            System.exit(0);
        }
    }

    public void searchOneClause(String[] keyWords){
        for(String keyWord: keyWords){
            this.searchWords.add(keyWord);
        }
        this.results = ds.searchWords(this.searchWords);
    }

    public void searchWithOr(String[] firstClause, String[] secondClause){
        for(String keyWord: firstClause){
            this.searchWords.add(keyWord);
        }
        PriorityQueue<Website> resultFirst = this.copyQueue(ds.searchWords(this.searchWords));
        this.searchWords.clear();
        System.out.println("First result" + resultFirst);

        for(String keyWord: secondClause){
            this.searchWords.add(keyWord);
        }            
        PriorityQueue<Website> resultSecond = this.copyQueue(ds.searchWords(this.searchWords));
        System.out.println("Second result" + resultSecond);
        if(resultFirst.size() >= resultSecond.size()){
            this.results = this.mergeResults(resultFirst,resultSecond);
        }
        else{
            this.results = this.mergeResults(resultSecond,resultFirst);
        }

    }

    public void run(){
        while(!search){       
            //this.checkContinue();
            System.out.println("Type in keywords");
            System.out.println("Please do not end query with connector and/or");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] inputs = line.split(" or ");
            
            this.searchWords = new PriorityQueue<String>();
            this.results = new PriorityQueue<Website>();
            
            if(inputs[0].equals("n")){
                System.exit(0);
            }
            if(inputs.length == 1){
                String[] keyWords = inputs[0].split(" and ");
                keyWords = trim(keyWords);
                this.searchOneClause(keyWords);
                System.out.println();
                this.printResult();
            }
            else if(inputs.length == 2){
                String[] firstClause = inputs[0].split(" and ");
                firstClause = trim(firstClause);

                String[] secondClause = inputs[1].split(" and ");
                secondClause = trim(secondClause);

                this.searchWithOr(firstClause, secondClause);
                System.out.println();
                this.printResult();
            }
        }

    }

    public PriorityQueue<Website> mergeResults(PriorityQueue<Website> p1, PriorityQueue<Website> p2){
        //ResultComparator c = new ResultComparator();

        PriorityQueue<Website> result = new PriorityQueue<Website>(Collections.reverseOrder());
        PriorityQueue<Website> toDelete = new PriorityQueue<Website>();
        for(Website queue1: p1){
            for(Website queue2: p2){
                if(queue1.combineMatchList(queue2)){
                    toDelete.add(queue2);}
            }
            result.add(queue1);
        }
        for(Website site: toDelete){
            p2.remove(site);
        }
        for(Website site: p2){
            result.add(site);
        }

        return result;
    }

    public PriorityQueue<Website> copyQueue(PriorityQueue<Website> copy){
        PriorityQueue<Website> result = new PriorityQueue<Website>(Collections.reverseOrder());
        for(Website toCopy: copy){
            Website toAdd = new Website(toCopy);
            result.add(toAdd);
        }
        return result;
    }

    public void printResult(){
        System.out.println("Top results ");
        PriorityQueue<Website> temp = this.copyQueue(this.results);
        if(temp.size() == 0){
            System.out.println("No results found");
            System.out.println();
            System.out.println("---------------");
            System.out.println();
        }
        else{
            int i = 0;
            while(temp.size() != 0 && i<5){
                Website tempSite = temp.poll();
                tempSite.printResult();
                System.out.println();
                i++;
            }
            System.out.println("---------------");
            System.out.println();
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
