package com.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Order")
public class Order implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	@ManyToOne
	@JoinColumn( name = "user_id", referencedColumnName = "id")
	private User user;	

    @ManyToMany(mappedBy = "orders")
//    @JoinTable(
//        name = "order_furniture",
//        joinColumns = @JoinColumn(name = "order_id"),
//        inverseJoinColumns = @JoinColumn(name = "furniture_id")
//    )
	private List<Furniture> furniture;

	public Order(Integer id, User user, List<Furniture> furniture) {
		super();
		this.id = id;
		this.user = user;
		this.furniture = furniture;
	}

	public Order() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Furniture> getFurniture() {
		return furniture;
	}

	public void setFurniture(List<Furniture> furniture) {
		this.furniture = furniture;
	}
    
	
	
    
}
