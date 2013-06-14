package application;

public class NotifyWinner extends Notification {
	
	public void notify(Auction auction) {
		postoffice.sendEMail(auction.getSeller().getUserEmail(), "Your item " + auction.getItemDescription() + " auction sold to bidder " + auction.getHighBidder().getUserName() + " for " + auction.getHighBid() + ".");
		postoffice.sendEMail(auction.getHighBidder().getUserEmail(), "Contratulations! You won auction for a " + auction.getItemDescription() + " from " + auction.getSeller().getUserEmail() + " for " + auction.getHighBid() + ".");
	}

}
