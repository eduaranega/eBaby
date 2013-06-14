package application;

public class FeeFactory  {
	
	public static ShippingFee calculateShippingFee(Auction auction) {
		if (auction.itemtype==ItemType.CAR)
			return new ShippingFeeCar();
		else if(auction.itemtype==ItemType.DOWNLOAD_SW)
			return new ShippingFeeDownloadSW();
		else
			return new ShippingFeeOther();
		}
	public static LuxuryFee calculateLuxFee(Auction auction){
		if (auction.itemtype==ItemType.CAR && auction.getHighBid()>50000)
			return new LuxuryFeeCar();
		else
			return new LuxuryFeeOther();
		
	}
	
}