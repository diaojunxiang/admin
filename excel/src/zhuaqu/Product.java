package zhuaqu;

public class Product {
	private String proStyle;// 产品型号
	private String proAmount;// 产品数量
	private String proPrice;// 产品报价
	private String proSupplier;// 产品供应商

	public String getProStyle() {
		return proStyle;
	}

	public void setProStyle(String proStyle) {
		this.proStyle = proStyle;
	}

	public String getProSupplier() {
		return proSupplier;
	}

	public void setProSupplier(String proSupplier) {
		this.proSupplier = proSupplier;
	}

	public String getProAmount() {
		return proAmount;
	}

	public void setProAmount(String proAmount) {
		this.proAmount = proAmount;
	}

	public String getProPrice() {
		return proPrice;
	}

	public void setProPrice(String proPrice) {
		this.proPrice = proPrice;
	}

	public Product() {

	}

	@Override
	public String toString() {
		return "Product [proAmount=" + proAmount + ", proPrice=" + proPrice
				+ ", proStyle=" + proStyle + ", proSupplier=" + proSupplier
				+ "]";
	}
}
