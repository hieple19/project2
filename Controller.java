
/**
 * Controller class takes in directories of input files and 
 * runs the program
 *
 * @author Hiep Le
 * @date  04/15/18
 */
public class Controller
{
    public static void main(String[] args){
        InputFileReader input = new InputFileReader(args[0],args[1]);
        input.readExcludedFile();
        input.readSiteFile();

        Database ds = new Database();
        ds.readData(input);

        SearchEngine s1 = new SearchEngine(ds);
        s1.run();
    }
}
