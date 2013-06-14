package application;

public class ShippingFeeCar extends ShippingFee{
	public double calculateShippingFee(double fee){
		return fee+=1000;
		
	}
}
