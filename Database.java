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
        Database ds = new Database();
        Website w1 = new Website("CNN", "cnn.com", "High");
        Website w2 = new Website("CNN", "cnn.s2", "Low");
        Website w3 = new Website("CNN", "cnn.lol", "Medium");
        Website w4 = new Website("BC", "cnn.s2", "Low");
        ds.addSite(w2);
        //ds.addSite(w3);
        //ds.addSite(w1);
        ds.addSite(w2);
        ds.addSite(w4);

        ds.print();
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

    public PriorityQueue<String> checkForExcluded(PriorityQueue<String> inputWords){
        PriorityQueue<String> searchWords = new PriorityQueue<String>();
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

    public PriorityQueue<Website> searchWords(PriorityQueue<String> inputWords){
        PriorityQueue<Website> sites = new PriorityQueue<Website> (Collections.reverseOrder());
        PriorityQueue<String> searchWords = this.checkForExcluded(inputWords);
        this.clearList();

        for(Website site: siteList){
            if(site.searchWords(searchWords)){
                sites.add(site);              
            }
        }
        return sites;
    }

    public Website[] websiteArray(){
        Website[] list = new Website[this.siteList.size()];
        Iterator itr = this.siteList.iterator();
        int i =0;
        while(itr.hasNext()){
            Website site = (Website)itr.next();
            list[i] = site;
            i++;
        }
        return list;
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
