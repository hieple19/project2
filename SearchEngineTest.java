
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class SearchEngineTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SearchEngineTest
{
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
        s1.searchOneClause(test1);

        expected.add(site2);
        assertArrayEquals("1 clause", expected.toArray(),s1.getResults().toArray());

        String[] test2 = {"-liberal"};
        s1.searchOneClause(test2);
        expected.clear();
        expected.add(site3);
        assertArrayEquals("2 clause", expected.toArray(),s1.getResults().toArray());

        String[] test3 = {"dogs", "liberal"};
        s1.searchOneClause(test3);
        expected.clear();
        assertEquals("3 clause", expected.toArray(),s1.getResults().toArray());

        String[] test4 = {"-hello","dogs"};
        s1.searchOneClause(test4);
        expected.add(site3);
        assertEquals("4 clause", expected.toArray(),s1.getResults().toArray());

        String[] test5 = {"dogs","liberal","-hello"};
        s1.searchOneClause(test5);
        expected.add(site2);
        expected.add(site1);
        assertEquals("5 clause", expected.toArray(),s1.getResults().toArray());
    }

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
        s1.searchWithOr(test1,test2);

        String[] expected1 = {site3.getName(), site2.getName()};
        assertArrayEquals("1 clause", expected1,s1.nameSite());

        String[] test3 = {"rocket"};
        String[] expected2 = {site2.getName()};
        s1.searchWithOr(test1,test3);
        assertArrayEquals("2 clause", expected2,s1.nameSite());

        String[] test4 = {"liberal"};
        s1.searchWithOr(test1,test4);
        String[] expected3 = {site2.getName(), site1.getName()};
        assertArrayEquals("3 clause", expected3,s1.nameSite());

        String[] test5 = {"-liberal"};
        s1.searchWithOr(test4,test5);
        String[] expected4 = {site1.getName(), site3.getName(),site2.getName()};
        assertArrayEquals("4 clause", expected4,s1.nameSite());
    }

    @Test
    public void testTwoClauseComposite(){
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
        String[] test1 = {"lafayette","liberal"};
        String[] test2 = {"dogs"};
        s1.searchWithOr(test1,test2);

        String[] expected1 = {site2.getName(), site3.getName()};
        assertArrayEquals("1 clause", expected1,s1.nameSite());

        String[] test3 = {"-rocket"};
        s1.searchWithOr(test1,test3);
        String[] expected2 = {site2.getName(), site1.getName(), site3.getName()};
        assertArrayEquals("2 three", expected2, s1.nameSite());

        String[] test4 = {"lafayette", "dogs"};
        s1.searchWithOr(test1,test4);
        String[] expected3 = {site2.getName()};
        assertArrayEquals("3 three", expected3, s1.nameSite());

        String[] test5 = {"computer", "science", "-hello"};
        String[] test6 = {"dogs", "bone", "-liberal"};
        String[] expected4 = {site1.getName(), site3.getName()};
        s1.searchWithOr(test5,test6);       
        assertArrayEquals("4 three", expected4, s1.nameSite());
        
        String[] test7 = {"liberal", "lafayette", "-hello"};
        String[] test8 = {"dogs", "-rocket", "-constant"};
        s1.searchWithOr(test7, test8);
        String[] expected5 = {site2.getName(), site1.getName(), site3.getName()};
        assertArrayEquals("5", expected5, s1.nameSite());
        

    }
}
