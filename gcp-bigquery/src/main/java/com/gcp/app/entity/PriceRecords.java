package com.gcp.app.entity;

//@Entity
//@Data
//@Builder
//@Table(name = "Price_Records")
//@AllArgsConstructor
//@NoArgsConstructor
public class PriceRecords {
    //@Id
    private Integer record_id;

    private Integer prod_discount;

    private Integer prod_price_latest;
    
    private Integer prod_price_old;

	public Integer getRecord_id() {
		return record_id;
	}

	public void setRecord_id(Integer record_id) {
		this.record_id = record_id;
	}

	public Integer getProd_discount() {
		return prod_discount;
	}

	public void setProd_discount(Integer prod_discount) {
		this.prod_discount = prod_discount;
	}

	public Integer getProd_price_latest() {
		return prod_price_latest;
	}

	public void setProd_price_latest(Integer prod_price_latest) {
		this.prod_price_latest = prod_price_latest;
	}

	public Integer getProd_price_old() {
		return prod_price_old;
	}

	public void setProd_price_old(Integer prod_price_old) {
		this.prod_price_old = prod_price_old;
	}
    
    
}
