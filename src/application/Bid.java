package application;

import java.util.Date;


public class Bid {

    private User bidder;
    private Date bidTime;
    private double amount;

    private Bid(User bidder, double amount, Date bidTime) {
            this.bidder = bidder;
            this.amount = amount;
            this.bidTime = bidTime;
    }
    
    public static Bid getInstance(User bidder, double amount, Date bidTime) {
    	return new Bid(bidder, amount, bidTime);
    }

    /* below here are gets and sets */
    
	public User getBidder() {
		return bidder;
	}

	public void setBidder(User bidder) {
		this.bidder = bidder;
	}

	public Date getBidTime() {
		return bidTime;
	}

	public void setBidTime(Date bidTime) {
		this.bidTime = bidTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


}
