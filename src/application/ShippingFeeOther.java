package application;

public class ShippingFeeOther extends ShippingFee{
	public double calculateShippingFee(double fee){
		return fee+10;
	}
}
