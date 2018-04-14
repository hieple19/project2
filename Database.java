import java.util.*;

/**
 * Class contains information about all sites 
 * as well as list of words to exclude
 *
 * @author Hiep Le
 */
public class Database
{   
    /**
     * Class has instance variables that keeps tracks of excluded words
     * and list of sites in the database
     */
    protected TreeSet<String> excludedList;
    protected TreeSet<Website> siteList;

    public static void main(String[] args){

        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);

        Iterator itr = ds.getSiteList().iterator();
        Website site2 = (Website)itr.next();
        Website site3 = (Website)itr.next();
        Website site1 = (Website)itr.next();
        TreeSet<String> words = new TreeSet<String>();
        PriorityQueue<Website> results = new PriorityQueue<Website>();
        System.out.println(ds.searchWords(words));
    }

    /**
     * Constructor for objects of class Database
     */
    public Database()
    {
        this.siteList = new TreeSet<Website>();
        this.excludedList = new TreeSet<String>();
    }

    public TreeSet<Website> getSiteList(){
        return this.siteList;
    }

    /**
     * Method adds a website to database's set of sites
     */
    public void addSite(Website site){
        this.siteList.add(site);
    }

    /**
     * Method calls database to read input data from input reader
     * @param input reader 
     */
    public void readData(InputFileReader input){
        for(Website w: input.siteList()){
            this.siteList.add(w);           // Add all websites that the input reader finds 
        }
        this.excludedList = input.excludedList();   // Set excluded words to that read by input reader
    }

    /**
     * Method checks user input for any excluded word in there
     * @param input words
     * @return Set of words to search
     */
    public TreeSet<String> checkForExcluded(TreeSet<String> inputWords){
        TreeSet<String> searchWords = new TreeSet<String>(); // Set of words after checking
        for(String word: inputWords){
            // If a word that is excluded,
            // Display a message. 
            if(this.excludedList.contains(word)){
                System.out.println();
                System.out.println("'" + word +"' "+ "is in list of excluded words");
            }
            // If word is not in excluded list, add to final set
            else{
                searchWords.add(word);
            }
        }
        return searchWords;     // return set
    }

    /**
     * Method searches through all sites in the database for matches
     * @param input words from users
     * @return queue of matching sites
     */
    public PriorityQueue<Website> searchWords(TreeSet<String> inputWords){
        // Result queue, order is reversed to show high priority site first
        PriorityQueue<Website> sites = new PriorityQueue<Website> (Collections.reverseOrder());

        // Set of search words after checking for excluded words
        TreeSet<String> searchWords = this.checkForExcluded(inputWords);

        // Clear matchlist of all sites to prepare for new search
        this.clearList();       

        for(Website site: siteList){
            if(site.searchWords(searchWords)){
                sites.add(site);      // add site to result queue if there's match        
            }
        }
        return sites;
    }

    /**
     *  Method clears match list of all sites in database
     */
    public void clearList(){
        for(Website site: this.siteList){
            site.getMatchList().clear();
        }
    }

    public void print(){
        System.out.println(siteList);
        for(Website w: siteList){
            w.printResult();
        }
    }

}
