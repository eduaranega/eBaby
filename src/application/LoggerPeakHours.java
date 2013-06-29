package application;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class LoggerPeakHours extends Logger {

	@Override
	public boolean logSales(Auction auction) {
		// TODO Auto-generated method stub
		AuctionLogger al=AuctionLogger.getInstance();
		return al.findMessage("OffHours.txt", auction.buildMessage());
	}

}
