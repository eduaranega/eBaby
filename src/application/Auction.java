package application;

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
	
	public void onClose() {
		this.setAuctionStatus(AuctionStatus.CLOSED);
	}	

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

	
}