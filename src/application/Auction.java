package application;

import java.util.ArrayList;
import com.tobeagile.training.ebaby.services.PostOffice;

enum AuctionStatus { CREATED, STARTED, CLOSED;
}

public class Auction {
	
	private User seller;
	private String itemDescription;
	private double highBid;
	private long startTime;
	private long endTime;
	private long now;
	private User highBidder;
	private AuctionStatus auctionStatus = AuctionStatus.CREATED;
	private AuctionException auctionException;
	public ArrayList<Bid> bids = new ArrayList<Bid>();
	PostOffice postOffice;
	
	private Auction(User seller, String itemDescription, double highBid, long startTime, long endTime) {
		this.seller = seller;
        if(!this.seller.isSeller()) {
        	AuctionException.getInstance("Only registered sellers can create new auctions");
        }
        if(!this.seller.isLogged()) {
        	AuctionException.getInstance("In order to create a new auction you need to log in");
        }
		this.itemDescription = itemDescription;
		this.highBid = highBid;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public static Auction getInstance(User seller, String itemDescription, double highBid, long startTime, long endTime) {
		return new Auction(seller, itemDescription, highBid, startTime, endTime);
	}
	
	public void onStart() {
		this.setAuctionStatus(AuctionStatus.STARTED);	
	}
	
	public boolean onClose() {
		
		postOffice = PostOffice.getInstance();
		if (this.bids.size() == 0) {
			postOffice.sendEMail(this.seller.getUserEmail(), "Sorry, your auction for " + this.getItemDescription() + " did not have any bidders.");
			this.setAuctionStatus(AuctionStatus.CLOSED);
			return false;
		}
		else {
			postOffice.sendEMail(this.seller.getUserEmail(), "Your item " + this.getItemDescription() + " auction sold to bidder " + this.getHighBidder().getUserName() + " for " + this.getHighBid() + ".");
			postOffice.sendEMail(this.getHighBidder().getUserEmail(), "Contratulations! You won auction for a " + this.getItemDescription() + " from " + this.seller.getUserEmail() + " for " + this.getHighBid() + ".");
			this.setAuctionStatus(AuctionStatus.CLOSED);
			return true;
		}
	}	

    public boolean addBid(User user, Bid bid) {
        if ((this.auctionStatus != AuctionStatus.STARTED) || 
        		(user.getUserName() == this.seller.getUserName()) ||
        		(!user.isLogged())) {
                return false; // action not started or seller auto-bid or user not logged
        }
                
        if (bid.getAmount() > this.getHighBid()) {
        	this.setHighBid(bid.getAmount());
        	this.setHighBidder(user);
        	this.bids.add(bid);
        	return true;
        }
        
        return false;
    }
    
    /* below here are gets and sets */
	
	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public double getHighBid() {
		return highBid;
	}

	public void setHighBid(double highBid) {
		this.highBid = highBid;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public User getHighBidder() {
		return highBidder;
	}

	public void setHighBidder(User highBidder) {
		this.highBidder = highBidder;
	}

	public AuctionStatus getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(AuctionStatus auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public AuctionException getAuctionException() {
		return auctionException;
	}

	public void setAuctionException(AuctionException auctionException) {
		this.auctionException = auctionException;
	}
    
	public ArrayList<Bid> getBids() {
		return bids;
	}

	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}
}