
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * JUnit test class for Website class
 *
 * @author  Hiep Le
 * @version 04/14/18
 */
public class WebsiteTest
{   
    /**
     * Method tests if website2 comparison works correctly
     */
    @Test
    public void compareTest(){
        Website s1 = new Website("F", "G", "low");
        Website s2 = new Website("A", "C", "medium");
        Website s3 = new Website("D", "E", "high");

        assertEquals("Medium > Low", -1, s1.compareTo(s2));     // Compare using priorities
        assertEquals("Medium > Low", 1, s2.compareTo(s1));
        assertEquals("High > Low", -1, s1.compareTo(s3));
        assertEquals("High > Low", 1, s3.compareTo(s1));
        assertEquals("High > Medium", -1, s2.compareTo(s3));
        assertEquals("High > Medium", 1, s3.compareTo(s2));

        TreeSet<String> temp1 = new TreeSet<String>();
        temp1.add("Hiep");
        s1.setMatchList(temp1);                     // s1 has match list of size 1, s2 size 0 

        assertEquals("Test 1", 1, s1.compareTo(s2));
        assertEquals("Test 2", 1, s1.compareTo(s3));

        Website s4 = new Website("F", "E", "high");
        assertEquals("Test name", -2, s4.compareTo(s3)); // Test compare with name
    }

    /**
     * Method tests if combine match list works correctly
     */
    @Test
    public void combineMatchList(){
        Website s1 = new Website("F", "G", "low");
        Website s2 = new Website("F", "G", "low");

        String[] empty = {};
        assertEquals("Combine 2 empty list", true, s1.combineMatchList(s2));
        assertArrayEquals("Combine 2 empty list", empty, s1.getMatchList().toArray());

        TreeSet<String> temp1 = new TreeSet<String>();
        temp1.add("Hiep");
        s1.setMatchList(temp1);         // s1 match list = " Hiep", s1 empty
        String[] expected = {"Hiep"};

        assertEquals("Combine List", true, s1.combineMatchList(s2));
        assertArrayEquals("Combine list", expected, s1.getMatchList().toArray());

        TreeSet<String> temp2 = new TreeSet<String>();
        temp1.add("Le");
        s2.setMatchList(temp2);     // s1 match list = hiep, s2 match list = le
        String[] expected2 = {"Hiep", "Le"};
        assertEquals("Combine List", true, s1.combineMatchList(s2));
        assertArrayEquals("Combine List", expected2, s1.getMatchList().toArray());
    }

    /**
     * Method tests if search(String word) works correctly
     */
    @Test
    public void searchWord(){
        Website emptySite = new Website("a","b","c");
        assertEquals("Empty site2", false, emptySite.searchWord("Hello"));
        assertEquals("Empty site2", true, emptySite.searchWord("-hello"));

        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Iterator itr = input.siteList().iterator();
        Website site2 = (Website)itr.next(); 
        // Check site2.txt file for list of words

        assertEquals("1 word", true, site2.searchWord("liberal"));
        assertEquals("1 word", true, site2.searchWord("c'ollege"));
        assertEquals("1 word", false, site2.searchWord("knight"));

        assertEquals("1 word", true, site2.searchWord("-can't"));
        assertEquals("1 word", false, site2.searchWord("-liberal"));
        assertEquals("1 word", false, site2.searchWord("-coll-ege"));
    }

    /**
     * Method tests if search(TreeSet words) works correctly
     * with set of size 1
     */
    @Test
    public void searchWordList1(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Iterator itr = input.siteList().iterator();
        Website site2 = (Website)itr.next();

        // site.txt has word "liberal" 
        TreeSet<String> search = new TreeSet<String>();
        search.add("liberal");   
        String[] expected = {"liberal"};

        assertEquals("Test 1", true, site2.searchWords(search));
        assertArrayEquals("Test 1", expected, site2.getMatchList().toArray());

        search.clear();
        search.add("-liberal");
        site2.getMatchList().clear();
        String[] expected2 = {};
        assertEquals("Test 1", false, site2.searchWords(search));
        assertArrayEquals("Test 1", expected2, site2.getMatchList().toArray());
    }

    /**
     * Method tests if search(TreeSet words) works correctly with 
     * set of size 2 - search for 2 words
     */
    @Test
    public void searchTwoWords(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Iterator itr = input.siteList().iterator();
        Website site2 = (Website)itr.next();

        TreeSet<String> search = new TreeSet<String>();
        String[] empty = {};
        assertEquals("Test empty", false, site2.searchWords(search));
        assertArrayEquals("Test empty", empty, site2.getMatchList().toArray());

        // Site 2 has both "liberal" and "lafayette" 

        search.add("liberal");           // true
        search.add("lafayette");         // true
        site2.getMatchList().clear();
        String[] expected = {"lafayette","liberal"};
        assertEquals("Test 1", true, site2.searchWords(search));    // 2 out of 2 -> true
        assertArrayEquals("Test 1", expected, site2.getMatchList().toArray());

        search.clear();
        search.add("-liberal");         // false
        search.add("lafayette");        // true
        site2.getMatchList().clear();
        String[] expected2 = {"lafayette"};
        assertEquals("Test 1", false, site2.searchWords(search)); // 1 out of 2 -> false
        assertArrayEquals("Test 1", expected2, site2.getMatchList().toArray());

        search.clear();

        // site 2 has "environment" but no "hiep"

        search.add("environment");      // true
        search.add("hiep");             // false
        site2.getMatchList().clear();
        String[] expected3 = {"environment"};
        assertEquals("Test 3", false, site2.searchWords(search));  // 1 out of 2 -> false
        assertArrayEquals("Test 3", expected3, site2.getMatchList().toArray());

        // site 2 has "-rocket" but not "-environment"
        search.clear();
        search.add("-rocket");              // true
        search.add("-environment");         // false
        site2.getMatchList().clear();
        String[] expected4 = {"-rocket"};
        assertEquals("Test 4", false, site2.searchWords(search)); // 1 out of 2 -> false
        assertArrayEquals("Test 4", expected4,site2.getMatchList().toArray());

        search.clear();

        // site 2 has neither "hiep" nor "le"
        search.add("hiep");             // false
        search.add("le");               // false
        site2.getMatchList().clear();
        String[] expected5 = {};
        assertEquals("Test 5", false, site2.searchWords(search));  // 0 out of 2 -> false
        assertArrayEquals("Test 5", expected5,site2.getMatchList().toArray());
    }

    /**
     * Method tests if search(TreeSet words) works correctly with 
     * set of size 3 - search for 3 words
     */
    @Test
    public void searchThreeWords(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Iterator itr = input.siteList().iterator();
        Website site2 = (Website)itr.next();

        //site 2 has all "liberal", "lafayette", and "environment"
        TreeSet<String> search = new TreeSet<String>();
        search.add("liberal");
        search.add("lafayette");
        search.add("environment");
        site2.getMatchList().clear();
        String[] expected = {"environment","lafayette","liberal"};
        assertEquals("Test 1", true, site2.searchWords(search));
        assertArrayEquals("Test 1", expected, site2.getMatchList().toArray());

        search.clear();
        search.add("-liberal");     //  false
        search.add("lafayette");    //  true
        search.add("environment");  // true
        site2.getMatchList().clear();
        String[] expected2 = {"environment","lafayette"};
        assertEquals("Test 2", true, site2.searchWords(search)); // has 2 out of 3 so returns true
        assertArrayEquals("Test 2", expected2, site2.getMatchList().toArray());

        search.clear();
        search.add("environment");  // false
        search.add("hiep");         // false
        search.add("le");           // false
        site2.getMatchList().clear();
        String[] expected3 = {"environment"};
        assertEquals("Test 3", false, site2.searchWords(search)); // has 1 out of 3 -> false
        assertArrayEquals("Test 3", expected3, site2.getMatchList().toArray());

        search.clear();
        search.add("-rocket");     // true
        search.add("liberal");      // true
        search.add("-environment"); // false
        site2.getMatchList().clear();
        String[] expected4 = {"-rocket","liberal"};
        assertEquals("Test 4", true, site2.searchWords(search)); // has 2 out of 3 -> true
        assertArrayEquals("Test 4", expected4,site2.getMatchList().toArray());

        search.clear();
        search.add("-liberal");     //false
        search.add("-lafayette");       //false
        search.add("-environment");     //false
        site2.getMatchList().clear();
        String[] expected5 = {};
        assertEquals("Test 5", false, site2.searchWords(search)); // has 0 out of 3 -> false
        assertArrayEquals("Test 5", expected5,site2.getMatchList().toArray());
    }
}
