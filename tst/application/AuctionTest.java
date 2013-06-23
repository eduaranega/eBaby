package application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class AuctionTest {
		
		private Auction auction, auction_car, auction_sw;
		private User user, user2, user3;
		private Bid bid, bid2, bid3, bid4, bid5;
		
	    private Date startTime = new Date();
	    private Date endTime = new Date();
	    private Date bidTime = new Date();
	
	    @Before
	    public void setup() {
	    	
	    	// Create a dummy user which is also a seller and is logged in the eBaby system
	    	user = User.getInstance("dummy", "Dummy", "dummy@yahoo.com", "dummyid", "dummypasswd");
	    	user.setSeller(true);
	    	user.setLogged(true);
	    	
	    	// Create 3 different Auctions to be used during the tests
	        auction = Auction.getInstance(user,"Atari 2600",100.00,startTime,endTime,ItemType.OTHER);
	        auction_car = Auction.getInstance(user,"Corolla",15000.00,startTime,endTime,ItemType.CAR);
	        auction_sw = Auction.getInstance(user,"Game",50.00,startTime,endTime,ItemType.DOWNLOAD_SW);
	        	    	
	        // Create 2 additional users
	        user2 = User.getInstance("dummy2", "Dummy2", "dummy2@yahoo.com", "dummy2id", "dummy2passwd");
	    	user3 = User.getInstance("dummy3", "Dummy3", "dummy3@yahoo.com", "dummy3id", "dummy3passwd");
	        
	    	// Create some Bids to be used during the tests
	        bid = Bid.getInstance(user2, 95, bidTime);
	        bid2 = Bid.getInstance(user2, 20000, bidTime);
	        bid3 = Bid.getInstance(user3, 30000, bidTime);
	        bid4 = Bid.getInstance(user3, 51000, bidTime);
	        bid5 = Bid.getInstance(user2, 105, bidTime);
	    }
	    
	    @Test
		public void testCreateAuction()
		{
	    	assertEquals(auction.getSeller(), user);
	    	assertEquals(auction.getItemDescription(), "Atari 2600");
	    	assertTrue(auction.getHighBid() == 100.00);
	    	assertTrue(auction.getStartTime() == startTime);
	    	assertTrue(auction.getEndTime() == endTime);
		}
	    
	    @Test (expected=IllegalStateException.class)
	    public void testCantCreateAuctionIfUserNotSeller() {
	    	// this test will throw an exception because the user is not a registered seller
	    	user.setSeller(false);
	    	auction = Auction.getInstance(user2,"Atari 2600",100.00,startTime,endTime,ItemType.OTHER);
	    }
	    	    
	    @Test(expected=IllegalStateException.class)
	    public void testCantCreateAuctionIfSellerNotLoggedIn() {
	    	// this test will throw an exception because the user is not logged in
	    	user.setLogged(false);
	    	auction = Auction.getInstance(user,"Atari 2600",100.00,startTime,endTime,ItemType.OTHER);
	    }
	  
	    @Test
	    public void testOnStart() {	   
	    	auction.onStart();
	    	assertEquals(auction.getAuctionStatus(), AuctionStatus.STARTED);
	    }
	    
	    @Test
	    public void testOnClose() {
	    	auction.onClose();
	    	assertEquals(auction.getAuctionStatus(), AuctionStatus.CLOSED);
	    }
	    
	    @Test
	    public void testOnCloseWithNoWinner() {    	
	    	auction.setAuctionStatus(AuctionStatus.STARTED);
	    	auction.bids.clear();
	    	assertFalse(auction.onClose()); // zero bids
	    }
	    
	    @Test
	    public void testOnCloseWithWinner() {
	    	auction.setAuctionStatus(AuctionStatus.STARTED);
	    	bid.setAmount(105);
	    	user2.setLogged(true);
	    	auction.addBid(user2,bid);
	    	assertTrue(auction.onClose()); // sold
	    }
	    	    
	    @Test
	    public void testAddBid() {
	    	assertFalse(auction.addBid(user, bid));  // auction not started
	    	auction.onStart();
	    	assertFalse(auction.addBid(user, bid));  // seller auto bid
	    	assertFalse(auction.addBid(user2, bid)); // user not logged in
	    	user2.setLogged(true);
	    	assertFalse(auction.addBid(user2, bid)); // bid < high bid
	    	bid.setAmount(105);
	    	assertTrue(auction.addBid(user2, bid)); // bid processed
	    }
	    
	    @Test
	    public void testCalculateFeeCarsUnder50000() {
	    	auction_car.onStart();
	    	user2.setLogged(true);
	    	auction_car.addBid(user2, bid2); // 20000
	    	auction_car.addBid(user2, bid3); // 30000
	    	auction_car.onClose();
	    	double fee = auction_car.calculateFee();
	    	assertTrue(fee == 31600);
	    }
	    
	    @Test 
	    public void testCalculateFeeCarsAbove50000() {
	    	auction_car.onStart();
	    	user2.setLogged(true);
	    	user3.setLogged(true);
	    	auction_car.addBid(user2, bid3); // 30000
	    	auction_car.addBid(user3, bid4); // 51000
	    	auction_car.onClose();
	    	double fee = auction_car.calculateFee();
	    	assertTrue(fee == 55140.8);
	    }
	    
	    @Test
	    public void testCalculateFeeDownloadSW() {
	    	auction_sw.onStart();
	    	user2.setLogged(true);
	    	auction_sw.addBid(user2, bid); // 95
	    	auction_sw.onClose();
	    	double fee = auction_sw.calculateFee();
	    	assertTrue(fee == 96.9);
	    }
	    
	    @Test
	    public void testCalculateFeeOther() {
	    	auction.onStart();
	    	user2.setLogged(true);
	    	auction.addBid(user2, bid5); // 105
	    	auction.onClose();
	    	double fee = auction.calculateFee();
	    	assertTrue(fee == 117.1);
	    }
	    
	    @Test
	    public void testLogCarSales(){
	    	auction_car.onStart();
	    	user2.setLogged(true);
	    	auction_car.addBid(user2, bid2);
	    	auction_car.addBid(user2, bid3); // 30000
	    	auction_car.onClose();
	    	auction_car.calculateFee();
	    	assertTrue(auction_car.logSales());
	    }
	    	    
	    @Test
	    public void testLogSalesOver10k(){
	    	auction_sw.onStart();
	    	user2.setLogged(true);
	    	auction_sw.addBid(user2, bid2);
	    	auction_sw.addBid(user2, bid3); // 30000
	    	auction_sw.onClose();
	    	auction_sw.calculateFee();
	    	assertTrue(auction_sw.logSales());
	    }
	    
	    @Test
	    public void testLogNoSales(){
	    	auction.onStart();
	    	auction.onClose();
	    	assertFalse(auction.logSales());
	    }
	    
}
