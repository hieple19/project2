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
    private ArrayList<String> matchList;

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
        this.matchList = new ArrayList<String>();
    }

    public Website(Website copy){
        this.name = copy.getName();
        this.url = copy.getUrl();
        this.priority = copy.getPriority();
        this.wordList = new ArrayList<String>();
        this.excludedList = new ArrayList<String>();
        this.matchList = new ArrayList<String>();

        for(String word: copy.getWordList()){
            this.wordList.add(word);
        }
        for(String word: copy.getExcludedList()){
            this.excludedList.add(word);
        }
        for(String word: copy.getMatchList()){
            this.matchList.add(word);
        }
    }

    public String getName(){return this.name;}

    public String getUrl() {return this.url;}

    public String getPriority() {return this.priority;}

    public ArrayList<String> getWordList() {return this.wordList;}

    public ArrayList<String> getExcludedList() {return this.excludedList;};

    public void setExcludedList(ArrayList<String> list) {this.excludedList = list;}

    public ArrayList<String> getMatchList() { return this.matchList;}

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

    public void clearMatchList(){
        this.matchList.clear();
    }

    public boolean searchWords(PriorityQueue<String> searchWords){
        if(searchWords.size() == 1){
            String top = searchWords.peek().toLowerCase();

            if(this.searchWord(top)){
                this.matchList.add(top);
                return true;
            }
        }
        else if(searchWords.size() == 2){
            Iterator itr = searchWords.iterator();
            while(itr.hasNext()){
                String top = (String) itr.next();
                top = top.toLowerCase();
                
                if(this.searchWord(top)){
                    this.matchList.add(top);
                }          
            }
            if(this.matchList.size() == 2){
                return true;
            }
        }
        else{
            Iterator itr = searchWords.iterator();
            while(itr.hasNext()){
                String top = (String) itr.next();
                top = top.toLowerCase();
                if(this.searchWord(top)){
                    this.matchList.add(top);  
                }      
            }
            if(this.matchList.size() >= 2){
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Website site){
        Integer noMatches1 = this.matchList.size();
        Integer noMatches2 = site.getMatchList().size();
        if(noMatches1 != noMatches2){
            return noMatches1.compareTo(noMatches2);
        }

        else {
            if(this.getPriority().toLowerCase().equals(site.getPriority().toLowerCase())){
                return (-1)*this.getName().compareTo(site.getName());
            }
            else if(this.priority.toLowerCase().equals("low")){
                return -1;
            }
            else if(this.priority.toLowerCase().equals("medium")){
                if(site.getPriority().toLowerCase().equals("high")){
                    return -1;
                }
                else if(site.getPriority().toLowerCase().equals("low")){
                    return 1;
                }
            }
            return 1;
        }
    }

    public String toString(){
        return this.name + " " + this.url + " " + this.priority;
    }

    public boolean combineMatchList(Website site){
        if(site.toString().equals(this.toString())){
            for(String match: site.getMatchList()){
                this.matchList.add(match);
            }
            return true;
        }
        return false;
    }

    public void printResult(){
        System.out.println("Name " + this.name);
        System.out.println("Url " + this.url);
        System.out.println("Priority " + this.priority);
        System.out.println("No words" + this.matchList.size());
        System.out.println("MatchList " + matchList);
    }

}
/*public int compareTo(Website site){
Integer word1 = this.matchList.size();
Integer word2 = site.getMatchList().size();
if(word1 != word2){
return word1.compareTo(word2);
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
}*/