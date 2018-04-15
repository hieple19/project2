import java.util.*;
import java.io.*;

/**
 * Class reads input files for website data and list of excluded words
 *
 * @ Hiep Le
 * @ 04/15/18
 */

public class InputFileReader{

    /**
     * Class has variables to store list of excluded words
     * and list of sites read. It also uses scanners to look
     * through the files.
     */
    protected TreeSet<String> excludedList;
    protected TreeSet<Website> siteList;
    private Scanner siteScanner;
    private Scanner excludedScanner;

    /**
     * Constructor for Input File Reader.
     * @param Input File Directories
     */
    public InputFileReader(String mainText, String excludedText){
        try{
            FileReader siteReader = new FileReader(mainText);
            FileReader excludedReader = new FileReader(excludedText);
            siteScanner = new Scanner(siteReader);
            excludedScanner = new Scanner(excludedReader);
            this.siteList = new TreeSet<Website>();
        }
        catch(Exception e){
            System.out.println("Error reading file");
            e.printStackTrace(System.out);
        }
    }

    public TreeSet<Website> siteList(){ return this.siteList; }

    public TreeSet<String> excludedList(){ return this.excludedList;}

    /**
     * Method reads the file for list of excluded words
     */
    public void readExcludedFile(){
        this.excludedList = new TreeSet<String>();
        while(excludedScanner.hasNextLine()){
            String line = excludedScanner.nextLine();
            line.trim();
            this.excludedList.add(line);         // Read, format and add word to set
        }
    }

    /**
     * Method reads the file for site information and words
     */
    public void readSiteFile(){
        while(siteScanner.hasNextLine()){
            String line = siteScanner.nextLine();
            String[] words = line.split(" ");       // Read the site's name, url and word fle directory
            String url = words[0];
            String priority = words[1];
            String fileName = words[2];
            Website newSite = new Website(fileName, url, priority);

            newSite.setExcludedList(this.excludedList); // Update new site's excluded list

            try{
                // Create a scanner to scan site data
                Scanner siteDataScanner = new Scanner(new FileReader(fileName));
                while(siteDataScanner.hasNextLine()){
                    String line2 = siteDataScanner.nextLine().replaceAll("[^a-zA-Z ]", ""); // Remove punctuation from the words
                    String[] words2 = line2.split(" ");     // Split every line into single words

                    for(String word: words2){
                        if(!this.excludedList.contains(word.toLowerCase())){
                            newSite.addWord(word);      // Add word if it's not in excluded list
                        }
                    }
                }
                siteDataScanner.close();
            }
            catch(FileNotFoundException e){
                System.out.println("File Not Found");
                e.printStackTrace();
            }
            catch(IOException e){
                System.out.println("Error");
                e.printStackTrace();
            }
            this.siteList.add(newSite);
        }
        siteScanner.close();
    }

    /**
     * Method returns string array of excluded words.
     * Used for testing purposes.
     * @return string array of excluded words
     */
    public String[] excludedToArray(){
        String[] result = new String[this.excludedList.size()];
        for(int i = 0; i<result.length; i++){
            result[i] = this.excludedList.pollFirst();
        }
        return result;         
    }

    /**
     * Method returns string array of names of all sites in site list
     * Used for testing purposes.
     * @return string array of names
     */
    public String[] siteNames(){
        String[] result = new String[this.siteList.size()];
        Iterator itr = this.siteList.iterator();
        int i =0;
        while(itr.hasNext()){
            Website site = (Website)itr.next();
            result[i] = site.getName();
            i++;
        }
        return result;    
    }

    /**
     * Method returns string array of priorities of all sites in site list
     * Used for testing purposes.
     * @return string array of priorities
     */
    public String[] sitePriority(){
        String[] result = new String[this.siteList.size()];
        Iterator itr = this.siteList.iterator();
        int i =0;
        while(itr.hasNext()){
            Website site = (Website)itr.next();
            result[i] = site.getPriority();
            i++;
        }
        return result;   
    }

    /**
     * Method returns string array of URLS of all sites in site list
     * Used for testing purposes.
     * @return string array of urls
     */
    public String[] siteUrl(){
        String[] result = new String[this.siteList.size()];
        Iterator itr = this.siteList.iterator();
        int i =0;
        while(itr.hasNext()){
            Website site = (Website)itr.next();
            result[i] = site.getUrl();
            i++;
        }
        return result;   
    }

    /**
     * Method returns array of websites from the list of sites
     * @return array of websites
     */
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
}