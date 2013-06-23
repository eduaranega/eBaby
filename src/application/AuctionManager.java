package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionManager {
	
    private List<Auction> auctionList;
    private Timer timer;

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
    
    public void startAuction(Auction auction) {
        auction.onStart();
    }
    
    public void endAuction(Auction auction) {
        auction.onClose();
        auction.logSales();
    }
        
	public void timerStart() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() { public void run() { timerTick(); } }, 100, 100 );
	}

	public void timerStop() {
		timer.cancel();
	}

   public void timerTick() {
        Date currentDate = new Date();
        for (Auction auction : auctionList) {
        	
                if ((auction.getAuctionStatus() == AuctionStatus.CREATED) 
                		&& currentDate.after(auction.getStartTime())) {
                        	startAuction(auction);
                }

                if ((auction.getAuctionStatus() == AuctionStatus.STARTED) 
                		&& currentDate.after(auction.getEndTime())) {
                        	endAuction(auction);
                }
        }
    }
}
