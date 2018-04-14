
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * JUnit Test Class for Search Engine
 *
 * @author  Hiep Le
 * @date    04/14/18
 */
public class SearchEngineTest
{   
    /**
     * Method tests searching with one clause
     */
    @Test
    public void oneClause(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);
        ds.print();
        Iterator itr = input.siteList().iterator();
        Website site2 = (Website)itr.next();
        Website site3 = (Website)itr.next();
        Website site1 = (Website)itr.next();
        PriorityQueue<Website> expected = new PriorityQueue<Website>(Collections.reverseOrder());
        SearchEngine s1 = new SearchEngine(ds);

        String[] empty = {""};
        s1.searchOneClause(empty);
        assertArrayEquals("Empty case", expected.toArray(),s1.getResults().toArray());

        String[] test1 = {"lafayette"};
        s1.searchOneClause(test1); //site2 has the phrase

        expected.add(site2);
        assertArrayEquals("1", expected.toArray(),s1.getResults().toArray());

        String[] test2 = {"-liberal"};  //site3 has no liberal
        s1.searchOneClause(test2);
        expected.clear();
        expected.add(site3);
        assertArrayEquals("2", expected.toArray(),s1.getResults().toArray());

        String[] test3 = {"dogs", "liberal"};   // No site has both words
        s1.searchOneClause(test3);
        expected.clear();
        assertEquals("3", expected.toArray(),s1.getResults().toArray());

        String[] test4 = {"-hello","dogs"};     // Site 3 has no hello and has dogs
        s1.searchOneClause(test4);
        expected.add(site3);
        assertEquals("4", expected.toArray(),s1.getResults().toArray());

        String[] test5 = {"dogs","liberal","-hello"}; // Each site has 2 out of 3
        s1.searchOneClause(test5);
        expected.add(site2);
        expected.add(site1);
        assertEquals("5", expected.toArray(),s1.getResults().toArray());
    }

    /**
     * Method tests searching with two clauses
     */
    @Test
    public void testTwoClause(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);
        ds.print();
        Iterator itr = input.siteList().iterator();
        Website site2 = (Website)itr.next();
        Website site3 = (Website)itr.next();
        Website site1 = (Website)itr.next();

        SearchEngine s1 = new SearchEngine(ds);
        String[] test1 = {"lafayette"};
        String[] test2 = {"dogs"};
        s1.searchWithOr(test1,test2); // First clase gives site2, second site3

        String[] expected1 = {site3.getName(), site2.getName()}; // site3 has higher priority so goes first
        assertArrayEquals("1 clause", expected1,s1.nameSite());

        String[] test3 = {"rocket"};    //First clause gives site2, second gives none
        s1.searchWithOr(test1,test3);

        String[] expected2 = {site2.getName()}; // Results only has one site2

        assertArrayEquals("2 clause", expected2,s1.nameSite());

        String[] test4 = {"liberal"};   // First clase site2, second site 2 and 1
        s1.searchWithOr(test1,test4);
        String[] expected3 = {site2.getName(), site1.getName()}; // Site2 has two matches so it's first element
        assertArrayEquals("3 clause", expected3,s1.nameSite());

        String[] test5 = {"-liberal"};  // First clause site1 and 2, second site3
        s1.searchWithOr(test4,test5);
        String[] expected4 = {site1.getName(), site3.getName(),site2.getName()}; // All have same number of mathces. Site1 has highest priority, then 3 and 2
        assertArrayEquals("4 clause", expected4,s1.nameSite());
    }

    /**
     * Method tests searching with two clauses of more complex forms
     */
    @Test
    public void testTwoClauseComposite(){
        InputFileReader input = new InputFileReader("testFileNames.txt", "excludedList.txt");
        input.readExcludedFile();
        input.readSiteFile();
        Database ds = new Database();
        ds.readData(input);
        
        Iterator itr = input.siteList().iterator();
        Website site2 = (Website)itr.next();
        Website site3 = (Website)itr.next();
        Website site1 = (Website)itr.next();

        SearchEngine s1 = new SearchEngine(ds);
        String[] test1 = {"lafayette","liberal"}; // Gives site2
        String[] test2 = {"dogs"};                // Give site3
        s1.searchWithOr(test1,test2);

        String[] expected1 = {site2.getName(), site3.getName()}; // site2 has 2 matches, site3 has 1
        assertArrayEquals("1 clause", expected1,s1.nameSite());

        String[] test3 = {"-rocket"}; // Gives all three
        s1.searchWithOr(test1,test3); // Site2: 3 matches, site1 and 3: 1 match so ranked by priority
        
        String[] expected2 = {site2.getName(), site1.getName(), site3.getName()};
        assertArrayEquals("2 three", expected2, s1.nameSite());

        String[] test4 = {"lafayette", "dogs"}; // Gives none
        s1.searchWithOr(test1,test4);   // First gives site2, second none
        
        String[] expected3 = {site2.getName()};
        assertArrayEquals("3 three", expected3, s1.nameSite());

        String[] test5 = {"computer", "science", "-hello"};        // Gives site1 with 3 matches
        String[] test6 = {"dogs", "bone", "-liberal"};             // Give site3 with 3 matches
        String[] expected4 = {site1.getName(), site3.getName()};   // Same number of matches site1 has higher priority
        s1.searchWithOr(test5,test6);       
        assertArrayEquals("4 three", expected4, s1.nameSite());

        String[] test7 = {"liberal", "lafayette", "-hello"};    // Gives site1 (2 matches) and site2 (3 matches)
        String[] test8 = {"dogs", "-rocket", "-constant"};      // Gives site2 (3 matches), site1 (3 matches) and site3(2 matches)
        s1.searchWithOr(test7, test8);
        String[] expected5 = {site2.getName(), site1.getName(), site3.getName()}; // site2(6 matches - high), site1(5 matches), site3(2 matches)
        assertArrayEquals("5", expected5, s1.nameSite());

    }
}
