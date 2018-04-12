
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
        assertArrayEquals("Testing",input.websiteArray(), ds.getSiteList().toArray());
    }

    @Test
    public void checkExcludedTest(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);

        TreeSet<String> search = new TreeSet<String>();
        String[] expected = {};
        search.add("anything");

        search = ds.checkForExcluded(search);
        assertArrayEquals("Test excluded", expected, search.toArray());

        search.add("nicole");
        search = ds.checkForExcluded(search);
        String[]expected2 = {"nicole"};
        assertArrayEquals("Test Nicole", expected2, search.toArray());

        search.add("computer");
        search.add("science");
        search.add("potato");
        search = ds.checkForExcluded(search);

        String[] expected3 = {"computer", "nicole", "potato","science"};
        assertArrayEquals("Test 3", expected3, search.toArray());

        search.add("everything");
        search = ds.checkForExcluded(search);
        assertArrayEquals("Test 4", expected3, search.toArray());

        search.add("computer");
        search.add("function");
        search.add("talent");
        search = ds.checkForExcluded(search);
        String[] expected4 = {"computer","function","nicole","potato","science","talent"};
        assertArrayEquals("Test 5", expected4, search.toArray());
    }

    @Test
    public void searchTest(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);

        Iterator itr = ds.getSiteList().iterator();
        Website site2 = (Website)itr.next();
        Website site3 = (Website)itr.next();
        Website site1 = (Website)itr.next();
        TreeSet<String> words = new TreeSet<String>();
        PriorityQueue<Website> results = new PriorityQueue<Website>();
        
        assertArrayEquals("No searchwords", results.toArray(), ds.searchWords(words).toArray());
    }
    /*public String[] queueToArray(PriorityQueue<String> queue){
    String[] array = new String[queue.size()];
    Iterator itr = queue.iterator();
    for(int i =0; i<array.length; i++){
    String s = (String)itr.next();
    array[i] = s;
    }
    return array;
    }*/
}
