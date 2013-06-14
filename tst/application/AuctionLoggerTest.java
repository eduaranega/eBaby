package application;

import org.junit.Before;
import org.junit.Test;

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
    }
    
    @Test
    public void testFindMessage() {
    	auctionLogger.findMessage("somefile.txt", "test log message");
    }
    
    @Test
    public void testReturnMessage() {
    	auctionLogger.returnMessage("somefile.txt", "test log message");
    }
	
}
