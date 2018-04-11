import java.util.*;
import java.io.*;

public class InputFileReader{
    protected TreeSet<String> excludedList;
    protected TreeSet<Website> siteList;
    private Scanner siteScanner;
    private Scanner excludedScanner;

    public static void main(String[] args){
        InputFileReader input = new InputFileReader("textFileNames.txt","excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();

        InputFileReader input2 = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input2.readExcludedFile();
        input2.readSiteFile();
        //input2.printSites();
        Iterator itr = input2.siteList().iterator();
        itr.next();
        Website site3 = (Website)itr.next();
        
        site3.printWords();

        //input.printExcluded();
    }

    public TreeSet<Website> siteList(){
        return this.siteList;
    }

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

    public void readSiteFile(){
        while(siteScanner.hasNextLine()){
            String line = siteScanner.nextLine();
            String[] words = line.split(" ");
            String url = words[0];
            String priority = words[1];
            String fileName = words[2];
            Website newSite = new Website(fileName, url, priority);
            newSite.setExcludedList(this.excludedList);
            try{
                Scanner siteDataScanner = new Scanner(new FileReader(fileName));
                while(siteDataScanner.hasNextLine()){
                    String line2 = siteDataScanner.nextLine().replaceAll("[^a-zA-Z ]", "");
                    String[] words2 = line2.split(" ");

                    for(String word: words2){
                        if(!this.excludedList.contains(word.toLowerCase())){
                            newSite.addWord(word);
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

    public void readExcludedFile(){
        this.excludedList = new TreeSet<String>();
        while(excludedScanner.hasNextLine()){
            String line = excludedScanner.nextLine();
            line.trim();
            this.excludedList.add(line);
        }
    }

    public TreeSet<String> excludedList(){ return this.excludedList;}

    public String[] excludedToArray(){
        String[] result = new String[this.excludedList.size()];
        for(int i = 0; i<result.length; i++){
            result[i] = this.excludedList.pollFirst();
        }
        return result;         
    }

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


    public void printSites(){
        Iterator itr = this.siteList.iterator();
        while(itr.hasNext()){
            Website site = (Website) itr.next();
            site.printResult();
            System.out.println();
        }
    }

    public void printExcluded(){
        for(String s: this.excludedList){
            System.out.println(s);
        }
        System.out.println(excludedList.size());
    }
    
    
}