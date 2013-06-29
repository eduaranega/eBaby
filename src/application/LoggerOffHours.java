package application;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class LoggerOffHours extends Logger {

	@Override
	public boolean logSales(Auction auction) {
		AuctionLogger al=AuctionLogger.getInstance();
		al.log("OffHours.txt", auction.buildMessage());
	    return al.findMessage("OffHours.txt", auction.buildMessage());
	}

}
