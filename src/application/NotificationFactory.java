package application;

public class NotificationFactory  {
	
	public static Notification getNotification(Auction auction) {
		if (auction.bids.size() == 0)
			return new NotifyNoWinner();
		else 
			return new NotifyWinner();		
		}
}