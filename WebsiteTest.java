
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class WebsiteTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class WebsiteTest
{   
    @Test
    public void compareTest(){
        Website s1 = new Website("F", "G", "low");
        Website s2 = new Website("A", "C", "medium");
        Website s3 = new Website("D", "E", "high");

        assertEquals("Medium > Low", -1, s1.compareTo(s2));
        assertEquals("Medium > Low", 1, s2.compareTo(s1));
        assertEquals("High > Low", -1, s1.compareTo(s3));
        assertEquals("High > Low", 1, s3.compareTo(s1));
        assertEquals("High > Medium", -1, s2.compareTo(s3));
        assertEquals("High > Medium", 1, s3.compareTo(s2));

        TreeSet<String> temp1 = new TreeSet<String>();
        temp1.add("Hiep");
        s1.setMatchList(temp1);

        assertEquals("Test 1", 1, s1.compareTo(s2));
        assertEquals("Test 2", 1, s1.compareTo(s3));

        Website s4 = new Website("F", "E", "high");
        assertEquals("Test name", -2, s4.compareTo(s3));
    }

    @Test
    public void combineMatchList(){
        Website s1 = new Website("F", "G", "low");
        Website s2 = new Website("F", "G", "low");

        String[] empty = {};
        assertEquals("Combine List", true, s1.combineMatchList(s2));
        assertArrayEquals("Combine list", empty, s1.getMatchList().toArray());
        
        TreeSet<String> temp1 = new TreeSet<String>();
        temp1.add("Hiep");
        s1.setMatchList(temp1);
        String[] expected = {"Hiep"};
        assertEquals("Combine List", true, s1.combineMatchList(s2));
        assertArrayEquals("Combine list", expected, s1.getMatchList().toArray());

        TreeSet<String> temp2 = new TreeSet<String>();
        temp1.add("Le");
        s2.setMatchList(temp2);
        String[] expected2 = {"Hiep", "Le"};
        assertEquals("Combine List", true, s1.combineMatchList(s2));
        assertArrayEquals("Combine List", expected2, s1.getMatchList().toArray());
    }

    @Test
    public void searchWord(){
        Website emptySite = new Website("a","b","c");
        assertEquals("Empty site", false, emptySite.searchWord("Hello"));
        assertEquals("Empty site", true, emptySite.searchWord("-hello"));

        InputFileReader input2 = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input2.readExcludedFile();
        input2.readSiteFile();
        Iterator itr = input2.siteList().iterator();
        Website site2 = (Website)itr.next();
        assertEquals("1 word", true, site2.searchWord("liberal"));
        assertEquals("1 word", true, site2.searchWord("c'ollege"));
        assertEquals("1 word", false, site2.searchWord("knight"));

        assertEquals("1 word", true, site2.searchWord("-can't"));
        assertEquals("1 word", false, site2.searchWord("-liberal"));
        assertEquals("1 word", false, site2.searchWord("-coll-ege"));
    }

    @Test
    public void searchOneWords(){
        InputFileReader input2 = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input2.readExcludedFile();
        input2.readSiteFile();
        Iterator itr = input2.siteList().iterator();
        Website site2 = (Website)itr.next();

        TreeSet<String> search = new TreeSet<String>();
        search.add("liberal");
        String[] expected = {"liberal"};
        assertEquals("Test 1", true, site2.searchWords(search));
        assertArrayEquals("Test 1", expected, site2.getMatchList().toArray());

        search.clear();
        search.add("-liberal");
        site2.clearMatchList();
        String[] expected2 = {};
        assertEquals("Test 1", false, site2.searchWords(search));
        assertArrayEquals("Test 1", expected2, site2.getMatchList().toArray());
    }

    @Test
    public void searchTwoWords(){
        InputFileReader input2 = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input2.readExcludedFile();
        input2.readSiteFile();
        Iterator itr = input2.siteList().iterator();
        Website site2 = (Website)itr.next();

        TreeSet<String> search = new TreeSet<String>();
        String[] empty = {};
        assertEquals("Test empty", false, site2.searchWords(search));
        assertArrayEquals("Test empty", empty, site2.getMatchList().toArray());

        search.add("liberal");
        search.add("lafayette");
        site2.clearMatchList();
        String[] expected = {"lafayette","liberal"};
        assertEquals("Test 1", true, site2.searchWords(search));
        assertArrayEquals("Test 1", expected, site2.getMatchList().toArray());

        search.clear();
        search.add("-liberal");
        search.add("lafayette");
        site2.clearMatchList();
        String[] expected2 = {"lafayette"};
        assertEquals("Test 1", false, site2.searchWords(search));
        assertArrayEquals("Test 1", expected2, site2.getMatchList().toArray());

        search.clear();
        search.add("environment");
        search.add("hiep");
        site2.clearMatchList();
        String[] expected3 = {"environment"};
        assertEquals("Test 3", false, site2.searchWords(search));
        assertArrayEquals("Test 3", expected3, site2.getMatchList().toArray());

        search.clear();
        search.add("-rocket");
        search.add("-environment");
        site2.clearMatchList();
        String[] expected4 = {"-rocket"};
        assertEquals("Test 4", false, site2.searchWords(search));
        assertArrayEquals("Test 4", expected4,site2.getMatchList().toArray());

        search.clear();
        search.add("hiep");
        search.add("le");
        site2.clearMatchList();
        String[] expected5 = {};
        assertEquals("Test 5", false, site2.searchWords(search));
        assertArrayEquals("Test 5", expected5,site2.getMatchList().toArray());
    }

    @Test
    public void searchThreeWords(){
        InputFileReader input2 = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input2.readExcludedFile();
        input2.readSiteFile();
        Iterator itr = input2.siteList().iterator();
        Website site2 = (Website)itr.next();

        TreeSet<String> search = new TreeSet<String>();
        search.add("liberal");
        search.add("lafayette");
        search.add("environment");
        site2.clearMatchList();
        String[] expected = {"environment","lafayette","liberal"};
        assertEquals("Test 1", true, site2.searchWords(search));
        assertArrayEquals("Test 1", expected, site2.getMatchList().toArray());

        search.clear();
        search.add("-liberal");
        search.add("lafayette");
        search.add("environment");
        site2.clearMatchList();
        String[] expected2 = {"environment","lafayette"};
        assertEquals("Test 2", true, site2.searchWords(search));
        assertArrayEquals("Test 2", expected2, site2.getMatchList().toArray());

        search.clear();
        search.add("environment");
        search.add("hiep");
        search.add("le");
        site2.clearMatchList();
        String[] expected3 = {"environment"};
        assertEquals("Test 3", false, site2.searchWords(search));
        assertArrayEquals("Test 3", expected3, site2.getMatchList().toArray());

        search.clear();
        search.add("-rocket");
        search.add("liberal");
        search.add("-environment");
        site2.clearMatchList();
        String[] expected4 = {"-rocket","liberal"};
        assertEquals("Test 4", true, site2.searchWords(search));
        assertArrayEquals("Test 4", expected4,site2.getMatchList().toArray());

        search.clear();
        search.add("-liberal");
        search.add("-lafayette");
        search.add("-environment");
        site2.clearMatchList();
        String[] expected5 = {};
        assertEquals("Test 5", false, site2.searchWords(search));
        assertArrayEquals("Test 5", expected5,site2.getMatchList().toArray());
    }
}
