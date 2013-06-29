package application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class AuctionManagerTest {

	private Auction auction, auction2;
	private AuctionManager auctionManager, mockAuctionManager;
	private User user;
    private Date startTime = new Date();
    private Date endTime = new Date();
    
    @Before
    public void setup() {
    	user = User.getInstance("dummy", "Dummy", "dummy@yahoo.com", "dummyid", "dummypasswd");
    	user.setSeller(true);
    	user.setLogged(true);
    	auction = Auction.getInstance(user,"Atari 2600",100.00,startTime,endTime,ItemType.OTHER);
    	auctionManager = AuctionManager.getInstance();
       	mockAuctionManager = mock(AuctionManager.class);
    }
    
    
    @Test
    public void testAddAuctionToAuctionManager() {
     auctionManager.addAuction(auction);
     assertEquals(auctionManager.containsAuction(auction), true);
    }

	@Test
    public void testStartAuction() {
    	auctionManager.auctionStart(auction);
    	assertEquals(auction.getAuctionStatus(), AuctionStatus.STARTED);
    }
    
    @Test
    public void testStopAuction() {
    	auctionManager.auctionStart(auction);
    	auctionManager.auctionClose(auction);
    	assertEquals(auction.getAuctionStatus(), AuctionStatus.CLOSED);
    }
    
    @Test
    public void testHandleAuctionEvents() {
    	
        long currentTime = System.currentTimeMillis();
        long start = currentTime - 1000;
        long end = currentTime;
        Date AuctionStartTime = new Date(start);
        Date AuctionEndTime = new Date(end);

        auction2 = Auction.getInstance(user,"Atari 2600",100.00,AuctionStartTime,AuctionEndTime,ItemType.OTHER);
        		
        mockAuctionManager.auctionStart(auction2);
    	try {
    	    Thread.sleep(1000);
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
    	mockAuctionManager.auctionClose(auction);
    	
    	

    }
    
}


