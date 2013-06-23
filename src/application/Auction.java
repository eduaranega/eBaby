package application;

import java.util.ArrayList;
import java.util.Date;

import com.tobeagile.training.ebaby.services.PostOffice;

enum AuctionStatus { CREATED, STARTED, CLOSED;
}

enum ItemType { CAR, DOWNLOAD_SW, OTHER;
}

public class Auction {
	
	private User seller;
	private String itemDescription;
	private double highBid;
	private Date startTime;
	private Date endTime;
	private User highBidder;
	private AuctionStatus auctionStatus = AuctionStatus.CREATED;
	private AuctionException auctionException = null;
	public ArrayList<Bid> bids = new ArrayList<Bid>();
	PostOffice postOffice;
	Notification notification;
	public ItemType itemtype;
	
	private Auction(User seller, String itemDescription, double highBid, Date startTime, Date endTime, ItemType itemtype) {
		this.seller = seller;
        if(!this.seller.isSeller()) {
        	throw this.auctionException = AuctionException.getInstance("Only registered sellers can create new auctions");
        }
        if(!this.seller.isLogged()) {
        	throw this.auctionException = AuctionException.getInstance("In order to create a new auction you need to log in");
        }
		this.itemDescription = itemDescription;
		this.highBid = highBid;
		this.startTime = startTime;
		this.endTime = endTime;
		this.itemtype = itemtype;
	}
	
	public static Auction getInstance(User seller, String itemDescription, double highBid, Date startTime, Date endTime, ItemType itemtype) {
		return new Auction(seller, itemDescription, highBid, startTime, endTime, itemtype);
	}
	
	public void onStart() {
		this.setAuctionStatus(AuctionStatus.STARTED);	
	}
	
	public boolean onClose() {
		
		// call Factory to get a new Notification instance
		notification = Factory.getNotification(this);
		notification.notify(this);
		
		if (notification instanceof NotificationNoWinner) {
			this.setAuctionStatus(AuctionStatus.CLOSED);
			return false;
		}
		else if (notification instanceof NotificationWinner) {
			this.setAuctionStatus(AuctionStatus.CLOSED);
			return true;
		}
		
		return false;
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
    

	public double calculateFee() {
		
		double fee = this.getHighBid() * 0.02 + this.getHighBid();
		
		// call Factory to get a new ShipptingFee instance
		ShippingFee sf=Factory.calculateShippingFee(this);
		fee=sf.calculateShippingFee(fee);
		// call Factory to get a LuxuryFee instance
		LuxuryFee lf=Factory.calculateLuxuryFee(this);
		fee=lf.calculateLuxuryFee(fee);
		return fee;
		
		/* code before re-factory
		double fee = this.getHighBid() * 0.02 + this.getHighBid();
		
		if (this.itemtype == ItemType.DOWNLOAD_SW) {
			return fee;
		}
		
		if (this.itemtype == ItemType.CAR) {
			fee += 1000;
			if (this.getHighBid() > 50000) {
				fee += fee * 0.04;
			}
			return fee;
		}
		else return fee+= 10;*/
	}
	
	public boolean logSales() {
		Logger l=Factory.logSales(this);
		return l.logSales(this);
		
		/* code before re-factory
		if(itemtype==ItemType.CAR){
			AuctionLogger al=AuctionLogger.getInstance();
			al.log("CarSales.txt", buildMessage());
		    return al.findMessage("CarSales.txt", buildMessage());
		}
		
		if (highBid > 10000) {
			AuctionLogger al=AuctionLogger.getInstance();
			al.log("10kSales.txt", buildMessage());
			return al.findMessage("10kSales.txt", buildMessage());
		}
		return false;*/
	}
	
	public String buildMessage() {
		String temp="";
		temp+="Seller:"+seller.getUserName()+" Item Name:"+itemDescription;
		temp+=" Amount:"+highBid+" High Bidder:"+highBidder.getUserName();
		return temp;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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