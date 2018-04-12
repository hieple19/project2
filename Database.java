import java.util.*;

/**
 * Class contains information about all sites 
 * as well as list of words to exclude
 *
 * @author Hiep Le
 */
public class Database
{
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

    public void addSite(Website site){
        this.siteList.add(site);
    }

    public void readData(InputFileReader i1){
        for(Website w: i1.siteList()){
            this.siteList.add(w);
        }
        this.excludedList = i1.excludedList();
    }

    public TreeSet<String> checkForExcluded(TreeSet<String> inputWords){
        TreeSet<String> searchWords = new TreeSet<String>();
        for(String word: inputWords){
            if(this.excludedList.contains(word)){
                System.out.println();
                System.out.println("'" + word +"' "+ "is in list of excluded words");
            }
            else{
                searchWords.add(word);
            }
        }
        return searchWords;
    }

    public PriorityQueue<Website> searchWords(TreeSet<String> inputWords){
        PriorityQueue<Website> sites = new PriorityQueue<Website> (Collections.reverseOrder());
        TreeSet<String> searchWords = this.checkForExcluded(inputWords);
        this.clearList();

        for(Website site: siteList){
            if(site.searchWords(searchWords)){
                sites.add(site);              
            }
        }
        return sites;
    }

    public void clearList(){
        for(Website site: this.siteList){
            site.clearMatchList();
        }
    }

    public void print(){
        System.out.println(siteList);
        for(Website w: siteList){
            w.printResult();
        }
    }

}
