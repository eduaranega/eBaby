package application;

public class LuxuryFeeCar extends LuxuryFee{
	public double calculateLuxuryFee(double fee){
		return fee+fee*0.04;
	}
	
}
