import java.util.*;

/**
 * Class contains information about all sites 
 * as well as list of words to exclude
 *
 * @author Hiep Le
 */
public class Database
{
    protected ArrayList<String> excludedWord;
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
    }

    public void addSite(Website site){
        this.siteList.add(site);
    }

    public void readData(InputFileReader i1){
        for(Website w: i1.siteList()){
            this.siteList.add(w);
        }
    }

    public PriorityQueue<Website> searchWords(PriorityQueue<String> searchWords){
        PriorityQueue<Website>  sites = new PriorityQueue<Website> ();
        for(Website site: siteList){
            if(site.searchWords(searchWords)){
                sites.add(site);
            }
        }
        return sites;
    }

    public void print(){
        System.out.println(siteList);
        for(Website w: siteList){
            w.printResult();
        }
    }

}
