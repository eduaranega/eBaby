package application;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class LoggerCarSales extends Logger {
	public boolean logSales(Auction auction){
		AuctionLogger al=AuctionLogger.getInstance();
		al.log("Sales.txt", auction.buildMessage());
	    return al.findMessage("Sales.txt", auction.buildMessage());
	}
}
