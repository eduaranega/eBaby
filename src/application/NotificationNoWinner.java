package application;

public class NotificationNoWinner extends Notification {
	
	public void notify(Auction auction) {
		postoffice.sendEMail(auction.getSeller().getUserEmail(), "Sorry, your auction for " + auction.getItemDescription() + " did not have any bidders.");
	}
	

}
