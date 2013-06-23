package application;


public class AuctionException extends IllegalStateException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AuctionException(String message) {
		 new IllegalStateException(message);
	}

	public static AuctionException getInstance(String message) {
		return new AuctionException(message);
	}
	
}
