import java.util.*;
import java.io.*;

public class InputFileReader{
    protected ArrayList<String> excludedWord;
    protected TreeSet<Website> siteList;
    private Scanner scanner;

    public static void main(String[] args){
        ExcludedListReader excludeRead = new ExcludedListReader("excludedList.txt");
        excludeRead.readExcluded();
        InputFileReader input = new InputFileReader("textFileNames.txt",excludeRead);
        input.readFile();
        input.print();
    }
    
    public TreeSet<Website> siteList(){
        return this.siteList;
    }
    
    public InputFileReader(String dir, ExcludedListReader list){
        try{
            FileReader reader = new FileReader(dir);
            scanner = new Scanner(reader);
            this.excludedWord = list.excludedList();
            this.siteList = new TreeSet<Website>();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }

    public void readFile(){
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] words = line.split(" ");
            String url = words[0];
            String priority = words[1];
            String fileName = words[2];
            Website newSite = new Website(fileName, url, priority);

            try{
                Scanner siteScanner = new Scanner(new FileReader(fileName));
                while(siteScanner.hasNextLine()){
                    String line2 = siteScanner.nextLine().replaceAll("[^a-zA-Z ]", "");
                    String[] words2 = line2.split(" ");
                    
                    for(String word: words2){
                        if(!this.excludedWord.contains(word.toLowerCase())){
                            newSite.addWord(word);
                        }
                    }
                }
                siteScanner.close();
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
        scanner.close();
    }

    public void print(){
        Iterator itr = this.siteList.iterator();
        while(itr.hasNext()){
            Website site = (Website) itr.next();
            site.print();
            System.out.println();
        }
    }

    /*public void addSiteData(){
    for(String siteFile: fileNames){
    try{
    FileReader reader = new FileReader(siteFile);
    Scanner siteScanner = new Scanner(reader);

    String nameLine = siteScanner.nextLine();
    String name = nameLine.substring(5);

    String urlLine = siteScanner.nextLine();
    String url = urlLine.substring(4);

    String priorityLine = siteScanner.nextLine();
    String priority = priorityLine.substring(7);

    Website newSite = new Website(name,url,priority);

    while(siteScanner.hasNextLine()){
    String line = siteScanner.nextLine();
    String[] words = line.split(" ");
    /*for(String word: words){
    word = word.toLowerCase();
    word = word.replaceAll("[\\W+]", "");

    for(String word: words){
    if(!this.excludedWord.contains(word.toLowerCase())){
    newSite.addWord(word);
    }
    }
    }

    siteList.add(newSite);
    }
    catch(FileNotFoundException e){
    System.out.println("File Not Found ");
    e.printStackTrace();
    }
    catch(Exception e){
    e.printStackTrace();
    }
    finally{
    scanner.close();
    }
    }
    }*/        
}