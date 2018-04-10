
/**
 * Write a description of class Contoller here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Controller
{
    public static void main(String[] args){
        InputFileReader input = new InputFileReader("textFileNames.txt","excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();

        Database ds = new Database();
        ds.readData(input);
        ds.print();

        SearchEngine s1 = new SearchEngine(ds);
       // s1.input();
        
    }
}
