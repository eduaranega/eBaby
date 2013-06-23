package application;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class AuctionManagerTest {

	private Auction auction, auction2, auction3;
	private AuctionManager auctionManager;
	private User user;
    private Date startTime = new Date();
    private Date endTime = new Date();

    @Before
    public void setup() {
    	user = User.getInstance("dummy", "Dummy", "dummy@yahoo.com", "dummyid", "dummypasswd");
    	user.setSeller(true);
    	user.setLogged(true);
    	auction = Auction.getInstance(user,"Atari 2600",100.00,startTime,endTime,ItemType.OTHER);
    	auction2 = Auction.getInstance(user,"Atari 3200",200.00,startTime,endTime,ItemType.OTHER);
    	auction3 = Auction.getInstance(user,"Playstation",299.00,startTime,endTime,ItemType.OTHER);
    	auctionManager = AuctionManager.getInstance();
    }
    
    @Test
	public void testAddAuctionToAuctionManager() {
    	auctionManager.addAuction(auction);
    	assertEquals(auctionManager.containsAuction(auction), true);
    }
    
    @Test
    public void testStartAuction() {
    	auctionManager.startAuction(auction);
    	assertEquals(auction.getAuctionStatus(), AuctionStatus.STARTED);
    }
    
    @Test
    public void testStopAuction() {
    	auctionManager.endAuction(auction);
    	assertEquals(auction.getAuctionStatus(), AuctionStatus.CLOSED);
    }
	    
    @Test
    public void testTimerTick() {
    	auctionManager.addAuction(auction);
    	auctionManager.addAuction(auction2);
    	auctionManager.addAuction(auction3);
    	auctionManager.startAuction(auction);
    	auctionManager.timerTick();
    	// incomplete, need to finish
    }
    
}


