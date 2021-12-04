package com.finance.domain;

import java.math.BigDecimal;

public class Car {
	 private String brand;
	    private BigDecimal price;

	    public Car(String brand, BigDecimal price) {
	        this.brand = brand;
	        this.price = price;
	    }

	    public String getBrand() {
	        return brand;
	    }

	    public void setBrand(String brand) {
	        this.brand = brand;
	    }

	    public BigDecimal getPrice() {
	        return price;
	    }

	    public void setPrice(BigDecimal price) {
	        this.price = price;
	    }
}
