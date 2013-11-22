package com.balancetask.testspring;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = -8024119518086432102L;

	private String description;
    private Double price;
    
    public Product() {
	}

    public Product(String description, Double price) {
    	setDescription(description);
    	setPrice(price);
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Description: " + description + ";");
        buffer.append("Price: " + price);
        return buffer.toString();
    }
    
}
