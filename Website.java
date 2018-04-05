import java.util.*;

/**
 * Class contains information about a webpage.
 * This includes site's name, URL, list of words as well as priority.
 *
 * @Hiep Le
 */
public class Website implements Comparable<Website>
{

    private String name;
    private String url;
    private String priority;

    private int wordsMatch;

    private ArrayList<String> wordList;
    private ArrayList<String> excludedList;

    /**
     * Constructor for objects of class Webpage
     */
    public Website(String name, String url, String priority)
    {
        this.name = name;
        this.url = url;
        this.priority = priority;
        this.wordList = new ArrayList<String>();
        this.excludedList = new ArrayList<String>();
        this.wordsMatch = 0;
    }

    public String getName(){return this.name;}

    public String getUrl() {return this.url;}

    public String getPriority() {return this.priority;}

    public int getWordsMatch() {return this.wordsMatch;}

    public ArrayList<String> getWordList() {return this.wordList;}

    /**
     * Method add words to site's list of words
     */
    public void addWords(ArrayList<String> toAdd){   
        for(String word: toAdd){  
            if(!this.wordList.contains(word.toLowerCase())){
                this.wordList.add(word.toLowerCase());
            }
        }
    }

    public void addWord(String word){
        if(!this.wordList.contains(word.toLowerCase())){
            this.wordList.add(word.toLowerCase());
        }
    }

    public boolean searchWord(String word){
        if(word.startsWith("-")){
            String search = word.substring(1,word.length());
            search = search.replaceAll("[^a-zA-Z ]", "");
            return !this.wordList.contains(search);
        }

        return this.wordList.contains(word.replaceAll("[^a-zA-Z ]", ""));
    }

    public boolean searchWords(PriorityQueue<String> searchWords){

        if(searchWords.size() == 1){
            String top = searchWords.peek().toLowerCase();

            if(this.searchWord(top)){
                this.wordsMatch = 1;
                return true;
            }
        }
        else if(searchWords.size() == 2){
            Iterator itr = searchWords.iterator();
            while(itr.hasNext()){
                String top = (String) itr.next();
                top = top.toLowerCase();
                if(this.searchWord(top)){
                    this.wordsMatch++;
                }          
            }
            if(this.wordsMatch == 2){
                return true;
            }
        }
        else{
            Iterator itr = searchWords.iterator();
            while(itr.hasNext()){
                String top = (String) itr.next();
                top = top.toLowerCase();
                if(this.searchWord(top)){
                    this.wordsMatch++;
                }      
            }
            if(this.wordsMatch >= 2){
                return true;
            }
        }
        return false;
    }

    public int compareTo(Website site){
        Integer word1 = this.wordsMatch;
        Integer word2 = site.getWordsMatch();
        if(word1 != word2){
            return word2.compareTo(word1);
        }

        if(this.getPriority().toLowerCase().equals(site.getPriority().toLowerCase())){
            return this.getName().compareTo(site.getName());
        }
        else if(this.priority.toLowerCase().equals("low")){
            return 1;
        }
        else if(this.priority.toLowerCase().equals("medium")){
            if(site.getPriority().toLowerCase().equals("high")){
                return 1;
            }
            else if(site.getPriority().toLowerCase().equals("low")){
                return -1;
            }
        }
        return -1;
    }

    public void print(){
        System.out.println("Name " + this.name);
        System.out.println("URL " + this.url);
        System.out.println("Priority " + this.url);
        System.out.println("No words " + this.wordsMatch);
        for(int i = 0; i<wordList.size(); i++){
            if(i%10 == 0){
                System.out.println();
            }

            System.out.print(wordList.get(i) + " ");
        }
    }

    public String toString(){
        return this.name + " " + this.url + " " + this.priority;
    }

    public void printResult(){
        System.out.println("Name " + this.name);
        System.out.println("URL " + this.url);
        System.out.println("No " + this.wordsMatch);
    }

}