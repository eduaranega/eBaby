package application;


public class Bid {

    private User bidder;
    private long bidTime;
    private double amount;

    private Bid(User bidder, double amount, long bidTime) {
            this.bidder = bidder;
            this.amount = amount;
            this.bidTime = bidTime;
    }
    
    public static Bid getInstance(User bidder, double amount, long bidTime) {
    	return new Bid(bidder, amount, bidTime);
    }

    /* below here are gets and sets */
    
	public User getBidder() {
		return bidder;
	}

	public void setBidder(User bidder) {
		this.bidder = bidder;
	}

	public long getBidTime() {
		return bidTime;
	}

	public void setBidTime(long bidTime) {
		this.bidTime = bidTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


}
