
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class DatabaseTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DatabaseTest
{
    @Test
    public void siteTest(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);
        assertArrayEquals("Testing",input.websiteArray(), ds.websiteArray());
    }

    @Test
    public void searchTest(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);
        PriorityQueue<String> search = new PriorityQueue<String>();
        search.add("anything");
    }

    public String[] queueToArray(PriorityQueue<String> queue){
        String[] array = new String[queue.size()];
        Iterator itr = queue.iterator();
        for(int i =0; i<array.length; i++){
            String s = (String)itr.next();
            array[i] = s;
        }
        return array;
        

    }
}
