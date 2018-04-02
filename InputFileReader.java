import java.util.*;
import java.io.*;

public class InputFileReader{
    private ArrayList<String> fileNames;
    protected ArrayList<String> excludedWord;
    protected TreeSet<Website> siteList;
    private Scanner scanner;


    public static void main(String[] args){
        ExcludedListReader excludeRead = new ExcludedListReader("excludedList.txt");
        excludeRead.readExcluded();
        InputFileReader input = new InputFileReader("textFileNames.txt",excludeRead);
        input.readFileNames();
        input.addSiteData();
        input.print();
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

    public void readFileNames(){
        this.fileNames = new ArrayList<String>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            line.trim();
            this.fileNames.add(line);
        }
        System.out.println(fileNames);
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

    public void addSiteData(){
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
                     */
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
    }          
}