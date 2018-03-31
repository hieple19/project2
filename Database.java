import java.util.*;

/**
 * Class contains information about all sites 
 * as well as list of words to exclude
 *
 * @author Hiep Le
 */
public class Database
{
    private ArrayList<String> excludedWord;
    private PriorityQueue<Website> siteList;
    
    private TreeMap<String, ArrayList<String>> siteMap;

    /**
     * Constructor for objects of class Database
     */
    public Database()
    {
        this.siteMap = new TreeMap<String, ArrayList<String>>();
        this.siteList = new PriorityQueue<Website>();
    }

    public void addSite(Website site){
        siteList.add(site);
        siteMap.put(site.getUrl(), site.getWordList());
    }    
}
