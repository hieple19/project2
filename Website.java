import java.util.*;

/**
 * Class contains information about a webpage.
 * This includes site's name, URL, list of words as well as priority.
 *
 * @ Hiep Le
 * @ 04/13/18
 */
public class Website implements Comparable<Website>
{
    /**
     * Class has string variables to keep track of the site's name,
     * url and priority
     * It also has tree sets to store list of matching words for every
     * search, list of words of a website and list of words to 
     * exclude from search and text parsing
     * 
     */
    private String name;
    private String url;
    private String priority;
    private TreeSet<String> matchList;

    private TreeSet<String> wordList;
    private TreeSet<String> excludedList;

    /**
     * Constructor for objects of class Webpage
     * @param String name
     * @param String url
     * @param String priority
     */
    public Website(String name, String url, String priority)
    {
        this.name = name;
        this.url = url;
        this.priority = priority;
        this.wordList = new TreeSet<String>();
        this.excludedList = new TreeSet<String>();
        this.matchList = new TreeSet<String>();
    }

    /**
     * Copy constructor
     * @param Website to copy
     */
    public Website(Website copy){
        this.name = copy.getName();
        this.url = copy.getUrl();
        this.priority = copy.getPriority();
        this.wordList = new TreeSet<String>();
        this.excludedList = new TreeSet<String>();
        this.matchList = new TreeSet<String>();

        for(String word: copy.getWordList()){
            this.wordList.add(word);
        }
        for(String word: copy.getExcludedList()){
            this.excludedList.add(word);
        }
        for(String word: copy.getMatchList()){
            this.matchList.add(word);
        }
    }

    public String getName(){return this.name;}

    public String getUrl() {return this.url;}

    public String getPriority() {return this.priority;}

    public TreeSet<String> getWordList() {return this.wordList;}

    public TreeSet<String> getExcludedList() {return this.excludedList;}

    public void setExcludedList(TreeSet<String> list) {this.excludedList = list;}

    public void setMatchList(TreeSet<String> list) {this.matchList = list;}

    public TreeSet<String> getMatchList() { return this.matchList;}

    /**
     * Method add a word to site's list of words if it does not
     * already contain the word
     * @param String word
     */
    public void addWord(String word){
        if(!this.wordList.contains(word.toLowerCase())){
            this.wordList.add(word.toLowerCase());
        }
    }

    /**
     * Method searches the list of words of the website for results
     * @param String word
     * @return true if word is present. If word is in the form "-word",
     * return true if word is not present
     * 
     */
    public boolean searchWord(String word){
        // Check if word is in the form "-word"
        if(word.startsWith("-")){
            String search = word.substring(1,word.length()); // Remove "-" at the front
            search = search.replaceAll("[^a-zA-Z ]", "");    // Remove punctuation and non-word character
            return !this.wordList.contains(search);          // Check that list does NOT contain word
        }

        // Format word and check that list contains word
        return this.wordList.contains(word.replaceAll("[^a-zA-Z ]", ""));   
    }
    
    /**
     * Method searches website for matches,
     * takes in set of search words of different sizes
     * and handle them accordingly
     * @param Set of search words
     * @return true if website has enough matches
     */
    public boolean searchWords(TreeSet<String> searchWords){
        if(searchWords.size() == 0){
            return false;       // Return false if set has no word
        }
        // For set of one word, return true if website contains word
        else if(searchWords.size() == 1){
            String top = searchWords.first().toLowerCase();

            if(this.searchWord(top)){
                this.matchList.add(top);    // Add matching words to list of match
                return true;
            }
        }
        
        // For set equal or greater than two words, return true if website contains at least two
        else{
            Iterator itr = searchWords.iterator(); // Iterate through set
            while(itr.hasNext()){
                String top = (String) itr.next();
                top = top.toLowerCase();
                if(this.searchWord(top)){
                    this.matchList.add(top);   // Add matching words to list
                }      
            }
            if(this.matchList.size() >= 2){
                return true;                    // If matching list contains at least two words, return true
            }
        }
        return false;                   
    }
    
    /**
     * Method provides comparison methods for websites
     * @param Website to be compared to
     * @return negative integer if website compared is greater, 0 if equal, and positive
     * if website compared is smaller
     */
    @Override
    public int compareTo(Website site){
        Integer noMatches1 = this.matchList.size();
        Integer noMatches2 = site.getMatchList().size();
         // Compare number of matching words
         // Website with more matching words is greater
        if(noMatches1 != noMatches2){
            return noMatches1.compareTo(noMatches2);   
            
        }
        // Two sites have same number of matching words
        else {
            // If two sites have same priority, compare their name. 
            // The (-1) is there because the order will be reversed in a queue of result websites
            // Higher priority will be placed first, but within the list there is stil alphebetical order
            if(this.getPriority().toLowerCase().equals(site.getPriority().toLowerCase())){
                return (-1)*this.getName().compareTo(site.getName());       
            }
            else if(this.priority.toLowerCase().equals("low")){
                return -1;                                            // If this website's priority is low and compared one is higher
            }
            else if(this.priority.toLowerCase().equals("medium")){
                if(site.getPriority().toLowerCase().equals("high")){
                    return -1;          // If this website's priority is medium and compared one is high
                }
                else if(site.getPriority().toLowerCase().equals("low")){
                    return 1;           // If this website's priority is medium and compared one is low
                }
            }
            return 1;       // If this website's priority is high
        }
    }
    
    /**
     * Method returns string representaion of the website
     */
    public String toString(){
        return this.name + " " + this.url + " " + this.priority;
    }
    
    /**
     * Method combines the list of matching words for two Website objects.
     * Usually used when two result queues have the same website but different matching lists
     * @return true if it is indeed a duplicate website
     */
    public boolean combineMatchList(Website site){
        if(site.toString().equals(this.toString())){
            for(String match: site.getMatchList()){
                this.matchList.add(match);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Method prints information about the website
     */
    public void printResult(){
        System.out.println("Name " + this.name + " " + this.url);
        System.out.println("Reliability " + this.priority);
        System.out.println("Matching words " + matchList);
    }

    
    /**
     * Method returns an array of words in the website
     * Used for testing purposes
     * @return Array of website's words
     */
    public String[] wordArray() {
        Iterator itr = this.wordList.iterator();
        String[] result = new String[this.wordList.size()];
        int i = 0;
        while(itr.hasNext()){
            String word = (String) itr.next();
            result[i] = word;
            i++;
        }
        return result;
    }
}
