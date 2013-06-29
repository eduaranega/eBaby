package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tobeagile.training.ebaby.services.AuctionTimer;
import com.tobeagile.training.ebaby.services.Auctionable;

public class AuctionManager implements Auctionable {
	
    private List<Auction> auctionList;
    private AuctionTimer auctionTimer;

    private AuctionManager() {
    	this.auctionList = new ArrayList<Auction>();
	}
    
    public static AuctionManager getInstance() {
    	return new AuctionManager();
    }
    
    public void addAuction(Auction auction) {
        auctionList.add(auction);
    }
    
    public boolean containsAuction(Auction auction) {
        return auctionList.contains(auction);
    }
    
    public void auctionStart(Auction auction) {
        
        auctionTimer = new AuctionTimer();
        auctionTimer.start();
        auction.onStart();
    }
    
    public void auctionClose(Auction auction) {
        auction.logSales();
        auction.onClose();
        auctionTimer.stop();
    }

	@Override
	public void handleAuctionEvents(long now) {
		
        Date currentDate = new Date();

        for (Auction auction : auctionList) {
                if ((auction.getAuctionStatus() == AuctionStatus.CREATED) && currentDate.after(auction.getStartTime())) {
                        auctionStart(auction);
                }

                if ((auction.getAuctionStatus() == AuctionStatus.STARTED) && currentDate.after(auction.getEndTime())) {
                        auctionClose(auction);
                }
        }
	}
	
	
}
