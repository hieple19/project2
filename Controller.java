
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
        InputFileReader input = new InputFileReader(args[0],args[1]);   // Read input files
        input.readExcludedFile();
        input.readSiteFile();

        Database database = new Database();                       
        database.readData(input);

        SearchEngine engine = new SearchEngine(database);
        engine.run();
    }
}
