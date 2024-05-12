package application.controller;

public class TableAsset {

	public String name;
	public String category;
	public String location;
	public String purchase_date;
	public String description;
	public String value;
	public String exp_date;

	public TableAsset() {

	}

	// name, category, locationName, purchaseDate, description, purchasedValue,
	// expDate
	// POJO
	public TableAsset(String n, String c, String l, String p, String d, String v, String e) {
		name = n;
		category = c;
		location = l;
		purchase_date = p;
		description = d;
		value = v;
		exp_date = e;

	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getExp_date() {
		return exp_date;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String getPurchase_date() {
		return purchase_date;
	}

	public String getValue() {
		return value;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
