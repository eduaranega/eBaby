package application;

import com.tobeagile.training.ebaby.services.PostOffice;

public abstract class Notification {
	
	PostOffice postoffice = PostOffice.getInstance();
	
	public abstract void notify(Auction auction);
	
}
