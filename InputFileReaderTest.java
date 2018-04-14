
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
/**
 * JUnit Test for Input Reader
 *
 * @author  Hiep Le
 * @version 04/14/18
 */
public class InputFileReaderTest
{   
    /**
     * Method tests if list of excluded words are read correctly 
     */
    @Test
    public void testExcluded(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "testExcluded.txt");
        input.readExcludedFile();

        String[]expected = {"a", "anything","bottom", "could", "else", "everything"};

        // Compare expected with string array of excluded words read by reader
        assertArrayEquals("Test reading excluded files", expected, input.excludedToArray()); 
    }

    /**
     * Method tests if list of websites are read correctly 
     */
    @Test 
    public void testSiteFile(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "testExcluded.txt");
        input.readExcludedFile();
        input.readSiteFile();

        String[] expectedName = {"testSite2.txt", "testSite3.txt", "testSite1.txt"};
        String[] expectedPriority = {"low", "medium", "high"};
        String[] expectedUrl = {"test2.com", "test3.com", "test1.com"};

        // Compare array of site's names, urls and priorities
        assertArrayEquals("Test names", expectedName, input.siteNames());
        assertArrayEquals("Test priority", expectedPriority, input.sitePriority());
        assertArrayEquals("Test Url", expectedUrl, input.siteUrl());
    }

    /**
     * Method tests if words of websites are read correctly 
     */
    @Test
    public void testSiteWords(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();

        Iterator itr = input.siteList().iterator();
        Website site2 = (Website)itr.next();

        Website site3 = (Website)itr.next();
        Website site1 = (Website)itr.next();
        
        // Compare expected arrays with actual array returned from website objects
        String[] expected1 = {"calculator","computer","human","interaction","liberal","science","study"};
        String[] expected2 = {"arts", "calculator","college","environment","holistic","lafayette","liberal","provides","students"};
        String[] expected3 = {"bone","cats","chase","dogs","mice"};

        assertArrayEquals("site1", expected1, site1.wordArray());
        assertArrayEquals("site2", expected2, site2.wordArray());
        assertArrayEquals("site3", expected3, site3.wordArray());
    }

}
