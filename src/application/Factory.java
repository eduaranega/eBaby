package application;

public class Factory  {
	
	public static Notification getNotification(Auction auction) {
		if (auction.bids.size() == 0)
			return new NotificationNoWinner();
		else 
			return new NotificationWinner();
	}
	
	public static ShippingFee calculateShippingFee(Auction auction) {
		if (auction.itemtype==ItemType.CAR)
			return new ShippingFeeCar();
		else if(auction.itemtype==ItemType.DOWNLOAD_SW)
			return new ShippingFeeDownloadSW();
		else
			return new ShippingFeeOther();
		}
	
	public static LuxuryFee calculateLuxuryFee(Auction auction) {
		if (auction.itemtype==ItemType.CAR && auction.getHighBid()>50000)
			return new LuxuryFeeCar();
		else
			return new LuxuryFeeOther();
	}
	
	public static Logger logSales(Auction auction) {
		if(auction.itemtype==ItemType.CAR) {
			return new LoggerCarSales();
		}
		else if (auction.getHighBid() > 10000) {
			return new Logger10k();
		}
		else
			return new LoggerOtherSales();
		
	}
	
	
}