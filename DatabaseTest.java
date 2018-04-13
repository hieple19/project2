
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

        words.add("lafayette");
        expected.add(site2);
        assertArrayEquals("One site", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("liberal");
        expected.add(site1);
        assertArrayEquals("One site", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-liberal");
        expected.clear();
        expected.add(site3);
        assertArrayEquals("One site", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-lafayette");
        expected.clear();
        expected.add(site1);
        expected.add(site3);
        assertArrayEquals("One site", expected.toArray(), ds.searchWords(words).toArray());
    }

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
        words.add("lafayette");
        expected.add(site2);
        assertArrayEquals("Test 1", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("dogs");
        words.add("liberal");
        expected.clear();
        assertArrayEquals("Test 2", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("liberal");
        words.add("calculator");
        expected.add(site2);
        expected.add(site1);
        assertArrayEquals("Test 3", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-hello");
        expected.add(site3);
        assertArrayEquals("Test 4", expected.toArray(), ds.searchWords(words).toArray());
    }

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
        words.add("dogs");
        words.add("computer");
        assertArrayEquals("Test 1", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("liberal");
        words.add("computer");
        words.add("lafayette");
        expected.add(site2);
        expected.add(site1);
        assertArrayEquals("Test 2", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-hello");
        words.add("dogs");
        words.add("-liberal");

        expected.add(site3);
        assertArrayEquals("Test 3", expected.toArray(), ds.searchWords(words).toArray());

        words.clear();
        words.add("-hello");
        words.add("liberal");
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
