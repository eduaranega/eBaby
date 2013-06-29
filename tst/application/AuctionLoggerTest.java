package application;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;	

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class AuctionLoggerTest {

  AuctionLogger auctionLogger;


    @Before
    public void setup() {
    	auctionLogger = AuctionLogger.getInstance();
    }
    
    @Test
    public void testLog() {
    	auctionLogger.log("somefile.txt","test log message");
    	assertTrue(auctionLogger.findMessage("somefile.txt","test log message"));
		assertFalse(auctionLogger.findMessage("somefile.txt","test log message test"));	
    }
    
    @Test
    public void testFindMessage() {
    	auctionLogger.log("somefile.txt","test log message");
    	assertTrue(auctionLogger.findMessage("somefile.txt","test log message"));
		assertFalse(auctionLogger.findMessage("somefile.txt","test log message test"));	
    }
    
    @Test
    public void testReturnMessage() {
    	auctionLogger.log("test.txt", "Hello World");
		String s1=auctionLogger.returnMessage("test.txt", "Hello World");
		String s2=auctionLogger.returnMessage("test.txt", "Hello World test");
		assertTrue(!s1.isEmpty());
		assertFalse(!s2.isEmpty());	
    }
	
}
