import java.util.*;
import java.io.*;

public class InputFileReader{
    protected ArrayList<String> excludedWord;
    protected TreeSet<Website> siteList;
    private Scanner siteScanner;
    private Scanner excludedScanner;

    public static void main(String[] args){
        InputFileReader input = new InputFileReader("textFileNames.txt","excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();

        //input.printSites();
        input.printExcluded();
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
            newSite.setExcludedList(this.excludedWord);
            try{
                Scanner siteDataScanner = new Scanner(new FileReader(fileName));
                while(siteDataScanner.hasNextLine()){
                    String line2 = siteDataScanner.nextLine().replaceAll("[^a-zA-Z ]", "");
                    String[] words2 = line2.split(" ");

                    for(String word: words2){
                        if(!this.excludedWord.contains(word.toLowerCase())){
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
        this.excludedWord = new ArrayList<String>();
        while(excludedScanner.hasNextLine()){
            String line = excludedScanner.nextLine();
            line.trim();
            this.excludedWord.add(line);
        }
    }

    public ArrayList<String> excludedList(){ return this.excludedWord;}

    public void printSites(){
        Iterator itr = this.siteList.iterator();
        while(itr.hasNext()){
            Website site = (Website) itr.next();
            site.printResult();
            System.out.println();
        }
    }

    public void printExcluded(){
        for(String s: this.excludedWord){
            System.out.println(s);
        }
        System.out.println(excludedWord.size());

    }
}