package com.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;


/*
 * - [ ]  Name of the product
- [ ]  Amount in stock
- [ ]  Price
- [ ]  Image URL of the item
 */
@Entity
@Table(name="Furniture")
public class Furniture implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(nullable = false, unique = true)
    @Schema(description="product name")
	private String product;
    
	@NotNull
	@Min(value=0)
    @Schema(description="amount of product in stock")
	private int stock;
    
    
	@NotNull
	@Min(value=0)
    @Schema(description="product price")
    private double price;
    
	@NotBlank
    @Schema(description="image url of item")
    private String url;
    
	
	@JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "order_furniture",
        joinColumns = @JoinColumn(name = "furniture_id"),
        inverseJoinColumns = @JoinColumn(name = "order_id")
    )
	
    private List<Order> orders;
	
	
	public Furniture() {
		super();
	}
	
	public Furniture(Integer id, @NotBlank String product, @NotEmpty @Min(0) int stock, double price,@NotBlank String url, List<Order> orders) {
		super();
		this.id = id;
		this.product = product;
		this.stock = stock;
		this.price = price;
		this.url = url;
		this.orders = orders;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	

}
