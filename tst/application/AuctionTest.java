package application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AuctionTest {
		
		private Auction auction, auction_car, auction_sw;
		private User user, user2, user3;
		private Bid bid, bid2, bid3, bid4;
	
	    @Before
	    public void setup() {
	    	
	    	user = User.getInstance("dummy", "Dummy", "dummy@yahoo.com", "dummyid", "dummypasswd");
	    	user.setSeller(true);
	    	user.setLogged(true);
	    	
	        auction = Auction.getInstance(user,"Atari 2600",100.00,06132013,06142013,ItemType.OTHER);
	        auction_car = Auction.getInstance(user,"Corolla",15000.00,06132013,06142013,ItemType.CAR);
	        auction_sw = Auction.getInstance(user,"Game",50.00,06132013,06142013,ItemType.DOWNLOAD_SW);
	        
	        bid = Bid.getInstance(user, 95, 06132013);
	        bid2 = Bid.getInstance(user, 20000, 06132013);
	        bid3 = Bid.getInstance(user, 30000, 06132013);
	        bid4 = Bid.getInstance(user, 51000, 06132013);
	    	
	        user2 = User.getInstance("dummy2", "Dummy2", "dummy2@yahoo.com", "dummy2id", "dummy2passwd");
	    	user3 = User.getInstance("dummy3", "Dummy3", "dummy3@yahoo.com", "dummy3id", "dummy3passwd");
	    }
	    
	    @Test
		public void testCreateAuction()
		{
	    	assertEquals(auction.getSeller(), user);
	    	assertEquals(auction.getItemDescription(), "Atari 2600");
	    	assertTrue(auction.getHighBid() == 100.00);
	    	assertTrue(auction.getStartTime() == 06132013);
	    	assertTrue(auction.getEndTime() == 06142013);
		}
	    
	    @Test(expected=IllegalStateException.class) 
	    public void testCantCreateAuctionIfUserNotSeller() {
	    	// this test will throw an exception because the user is not a registered seller
	    	user.setSeller(false);
	    	auction = Auction.getInstance(user,"Atari 2600",100.00,06132013,06142013,ItemType.OTHER);
	    }
	    
	    @Test(expected=IllegalStateException.class)
	    public void testCantCreateAuctionIfSellerNotLoggedIn() {
	    	// this test will throw an exception because the user is not logged in
	    	user.setLogged(false);
	    	auction = Auction.getInstance(user,"Atari 2600",100.00,06132013,06142013,ItemType.OTHER);
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
	    	
	    	auction.setAuctionStatus(AuctionStatus.STARTED);
	    	auction.bids.clear();
	    	assertFalse(auction.onClose()); // zero bids
	    	
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
	    	auction_car.addBid(user2, bid2);
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
	    	auction_car.addBid(user3, bid4); // 50000
	    	auction_car.onClose();
	    	double fee = auction_car.calculateFee();
	    	assertTrue(fee == 55140.8);
	    }
	    
	    @Test
	    public void testDownloadSW() {
	    	auction_sw.onStart();
	    	user2.setLogged(true);
	    	auction_sw.addBid(user2, bid); // 95
	    	auction_sw.onClose();
	    	double fee = auction_sw.calculateFee();
	    	assertTrue(fee == 96.9);
	    	
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
