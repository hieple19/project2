import java.util.*;
import java.io.*;

/**
 * Class handles user input, passing of keywords to search for matches
 * in database
 * 
 * @author Hiep Le
 * @date 04/13
 */
public class SearchEngine
{   
    /**
     * Class has instance variables of database object, one variable to 
     * keep track of list of search words, and 
     * one variable to store the results,
     * 
     */
    private Database ds;
    private TreeSet<String> searchWords;
    private PriorityQueue<Website> results;

    public static void main(String[] args){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        //InputFileReader input = new InputFileReader("textFileNames.txt","excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();

        Database ds = new Database();
        ds.readData(input);
        //ds.print();

        SearchEngine s1 = new SearchEngine(ds);
        s1.run();
    }
    
    /**
     * Constructor creates search engine linked to a database.
     * @param database created from input files
     */
    public SearchEngine(Database ds){
        this.ds = ds;
        this.searchWords = new TreeSet<String>();
        this.results = new PriorityQueue<Website>();
    }

    public PriorityQueue<Website> getResults(){ return this.results;}
    
    /**
     * Method checks if user wants to continue
     * If user presses any other key than y, program exits
     */
    public void checkContinue(){
        System.out.println("Press Y to start search");
        System.out.println("Press any other to exit");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if(!line.toLowerCase().equals("y")){
            System.exit(0);
        }
    }
    
    /**
     * Method conducts search if user inputs no OR (one clause only)
     * @param keyWords taken from user input, which is read in subsequent
     * run() method
     */
    public void searchOneClause(String[] keyWords){
        this.searchWords.clear();
        for(String keyWord: keyWords){
            this.searchWords.add(keyWord);          // Add search words to list of search words
        }
        
        // Pass list of search words to database, which will return the matching sites
        this.results = ds.searchWords(this.searchWords);  
        System.out.println(results);
    }
    
    /**
     * Method conducts search if user input an OR (two clauses)
     * @param first clause taken from user input
     * @param second clause taken from user input
     */
    public void searchWithOr(String[] firstClause, String[] secondClause){
        this.searchWords.clear();
        for(String keyWord: firstClause){
            this.searchWords.add(keyWord);      // Add searchwords of first clause to list
        }
        
        // Pass list of search words to database
        // Store the results to one queue
        PriorityQueue<Website> resultFirst = this.copyQueue(ds.searchWords(this.searchWords));

        System.out.println("First result" + resultFirst);
        this.searchWords.clear();               // Clear list of search words
        for(String keyWord: secondClause){
            this.searchWords.add(keyWord);      // Add searchwords of second clause to list
        }      
        
        // Pass list of search words to database
        // Store the results to one queue
        PriorityQueue<Website> resultSecond = this.copyQueue(ds.searchWords(this.searchWords));
        System.out.println("Second result" + resultSecond);
        
        // Merge shorter list to longer list 
        if(resultFirst.size() >= resultSecond.size()){
            this.results = this.mergeResults(resultFirst,resultSecond);
        }
        else{
            this.results = this.mergeResults(resultSecond,resultFirst);
        }

    }
    /**
     * Method takes in user input, format inputs into string arrays 
     * and pass the words into the appropriate search function
     */
    public void run(){
        while(true){       
            //this.checkContinue();                     // Check if user wants to continue
            
            System.out.println("Type in keywords");     // Display instructions
            System.out.println("Please do not end query with connector and/or");
            System.out.println("Type n to exit program");
            
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();           // Read user input
            
            // Split input string into two clauses if there is an OR
            String[] inputs = line.split(" or ");           
            
            // Initialize new results priority queue
            this.results = new PriorityQueue<Website>();

            if(inputs[0].equals("n")){
                System.exit(0);             // User can do a quick exit from a search by typing n
            }
            // If input string array only has one string - no OR
            if(inputs.length == 1){
                String[] keyWords = inputs[0].split(" and ");   // Split the clause into various keywords separated by AND
                keyWords = trim(keyWords);                      // Format keywords and search
                this.searchOneClause(keyWords);
                System.out.println();
                this.printResult();                             // Print result of search
            }
            else if(inputs.length == 2){
                String[] firstClause = inputs[0].split(" and ");    // Split the clause into various keywords separated by AND
                firstClause = trim(firstClause);

                String[] secondClause = inputs[1].split(" and ");   // Split the clause into various keywords separated by AND
                secondClause = trim(secondClause);

                this.searchWithOr(firstClause, secondClause);   // Function searches with OR
                System.out.println();
                this.printResult();                             // Print result
            }
        }

    }
    
    /**
     * Method merge two result queue into one final queue to return
     * @param queue1 - longer queue
     * @param queue2 - shorter queue
     */
    public PriorityQueue<Website> mergeResults(PriorityQueue<Website> queue1, PriorityQueue<Website> queue2){
        // Initialize two queues, one final result queue 
        // and one queue which contains websites that appears in both parameter queues
        
        PriorityQueue<Website> result = new PriorityQueue<Website>(Collections.reverseOrder());
        PriorityQueue<Website> toDelete = new PriorityQueue<Website>();
        
        for(Website webInQ1: queue1){
            for(Website webInQ2: queue2){
                // Since two search on two clauses returns two queues of websites
                // A website can appear in both result queues
                // Check if this is the case, combine the match list and save the website for deletion
                if(webInQ1.combineMatchList(webInQ2)){
                    toDelete.add(webInQ2);}          // Add duplicate website to list to delete
            }
            result.add(webInQ1);          // Add all websites in queue 1
        }
        for(Website site: toDelete){
            queue2.remove(site);          // Remove all duplicates from queue 2
        }
        for(Website site: queue2){
            result.add(site);             // Add all websites in queue2
        }

        return result;
    }
    
    /**
     * Method copies a parameter queue and returns another queue with the exact content
     * @param queue to copy
     */
    public PriorityQueue<Website> copyQueue(PriorityQueue<Website> copy){
        PriorityQueue<Website> result = new PriorityQueue<Website>(Collections.reverseOrder());
        for(Website toCopy: copy){
            Website toAdd = new Website(toCopy);
            result.add(toAdd);
        }
        return result;
    }
    
    /**
     * Method prints result of every search
     */
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
    
    /**
     * Method trims to avoid leading and trailing whitespaces in 
     * all strings of an array
     * @paramt String array
     */
    public String[] trim(String[] strings){
        String[] result = new String[strings.length];
        for(int i = 0; i<result.length; i++){
            String toAdd= strings[i].trim();
            result[i] = toAdd;
        }
        return result;
    }
    
    /**
     * Method returns list of names of websites in queue of result websites
     * Used for testing purposes
     */
    public String[] nameSite(){
        String [] result = new String[this.results.size()];
        for(int i = 0; i<result.length; i++){
            Website web = this.results.poll();
            result[i] = web.getName();
        }
        return result;
    }
}
