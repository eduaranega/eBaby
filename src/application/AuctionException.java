package application;


public class AuctionException  {
	
	private AuctionException(String message) {
		throw new IllegalStateException(message);
	}

	public static AuctionException getInstance(String message) {
		return new AuctionException(message);
	}
	
}
