package application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AuctionTest {
		
		private Auction auction;
		private User user;
	
	    @Before
	    public void setup() {
	    	user = User.getInstance("dummy", "Dummy", "dummy@yahoo.com", "dummyid", "dummypasswd");
	    	user.setSeller(true);
	    	user.setLogged(true);
	        auction = Auction.getInstance(user,"Atari 2600",100.00,06132013,06142013);
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
	    	auction = Auction.getInstance(user,"Atari 2600",100.00,06132013,06142013);
	    }
	    
	    @Test(expected=IllegalStateException.class)
	    public void testCantCreateAuctionIfSellerNotLoggedIn() {
	    	// this test will throw an exception because the user is not logged in
	    	user.setLogged(false);
	    	auction = Auction.getInstance(user,"Atari 2600",100.00,06132013,06142013);
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
	    
	    
}
