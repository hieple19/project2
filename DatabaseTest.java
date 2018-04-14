
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * JUnit Test Class for Database Class
 *
 * @author  Hiep Le
 * @version 04/14/18
 */
public class DatabaseTest
{   
    /**
     * Method tests if database reads the website input correctly
     * by comparing the input website array and database array
     * 
     */
    @Test
    public void siteTest(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);
        assertArrayEquals("Site Test",input.websiteArray(), ds.getSiteList().toArray());
    }

    /**
     * Method if checking of excluded words is done correctly
     */
    @Test
    public void checkExcludedTest(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);

        TreeSet<String> search = new TreeSet<String>();
        String[] expected = {};
        search.add("anything");     // Is in list of excluded, list expected to be empty

        search = ds.checkForExcluded(search);
        assertArrayEquals("Test excluded", expected, search.toArray());

        search.add("nicole");       // Is not in excluded list, list expected to have "nicole"
        search = ds.checkForExcluded(search);
        String[]expected2 = {"nicole"}; 
        assertArrayEquals("Test Nicole", expected2, search.toArray());

        search.add("computer");
        search.add("science");      // Are all not in excluded list
        search.add("potato");
        search = ds.checkForExcluded(search);

        String[] expected3 = {"computer", "nicole", "potato","science"};
        assertArrayEquals("Test 3", expected3, search.toArray());

        search.add("everything");   // Is in excluded list, does not change expected list
        search = ds.checkForExcluded(search);
        assertArrayEquals("Test 4", expected3, search.toArray());

        search.add("computer");     // Repetition so no addition to list
        search.add("function");     // Add to more to expected list
        search.add("talent");
        search = ds.checkForExcluded(search);
        String[] expected4 = {"computer","function","nicole","potato","science","talent"};
        assertArrayEquals("Test 5", expected4, search.toArray());
    }

    /**
     * Method if searching of set of one word is done correctly
     */
    @Test
    public void searchOne(){
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
        PriorityQueue<Website> expected = new PriorityQueue<Website>();

        assertArrayEquals("No searchwords", expected.toArray(), ds.searchWords(words).toArray());

        words.add("anything");
        assertArrayEquals("Keyword in exluded", expected.toArray(), ds.searchWords(words).toArray());

        words.add("lafayette");     // Only site 2 has "lafayette"
        expected.add(site2);
        assertArrayEquals("Test 1", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("liberal");       
        expected.add(site1);        // Site1 and 2 has "liberal"
        assertArrayEquals("Test 2", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-liberal");
        expected.clear();
        expected.add(site3);        // Only site 3 has no "liberal"
        assertArrayEquals("Test 3", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-lafayette");
        expected.clear();
        expected.add(site1);
        expected.add(site3);        // Site 1 and 3 has no "lafayette
        assertArrayEquals("Test 4", expected.toArray(), ds.searchWords(words).toArray());
    }

    /**
     * Method if searching of set of 2 words is done correctly
     */
    @Test
    public void searchTwo(){
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
        TreeSet<Website> expected = new TreeSet<Website>(Collections.reverseOrder());

        words.add("liberal");
        words.add("lafayette");     // Only site 2 has two words
        expected.add(site2);

        assertArrayEquals("Test 1", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("dogs");          // No site has both 
        words.add("liberal");
        expected.clear();
        assertArrayEquals("Test 2", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("liberal");       
        words.add("calculator");    // Site1 and 2 has both
        expected.add(site2);
        expected.add(site1);
        assertArrayEquals("Test 3", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-hello");        // All three have no hello
        expected.add(site3);
        assertArrayEquals("Test 4", expected.toArray(), ds.searchWords(words).toArray());
    }

    /**
     * Method if searching of set of 3 words is done correctly
     */
    @Test
    public void searchThree(){
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
        TreeSet<Website> expected = new TreeSet<Website>(Collections.reverseOrder());

        words.add("lafayette");
        words.add("dogs");          // Each site only has one, so result is empty
        words.add("computer");
        assertArrayEquals("Test 1", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("liberal");
        words.add("computer");      // Site1 has 2, site2 has 2
        words.add("lafayette");
        expected.add(site2);
        expected.add(site1);
        assertArrayEquals("Test 2", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-hello");
        words.add("dogs");
        words.add("-liberal");      // Only site 3 
        expected.clear();
        expected.add(site3);
        assertArrayEquals("Test 3", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-hello");
        words.add("liberal");       // All 3 has 2 out of 3
        words.add("bone");
        expected.clear();

        PriorityQueue<Website> temp = ds.searchWords(words);
        TreeSet<Website> results = new TreeSet<Website>(Collections.reverseOrder());
        for(Website site: temp){
            results.add(site);
        }
        expected.clear();
        expected.add(site1);
        expected.add(site2);
        expected.add(site3);

        assertArrayEquals("Test 4", expected.toArray(), results.toArray());
    }
}
