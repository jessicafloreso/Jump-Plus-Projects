package com.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name="User")
public class User implements Serializable{


	private static final long serialVersionUID = 1L;
	
	public static enum Role {
		ROLE_USER, ROLE_ADMIN
	}
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Schema(description="username to be used with login")
	private String username;
    
    @NotBlank
    @Column(nullable = false)
    @Schema(description="password to be used with login")
	private String password;
    
    
    @NotBlank
    @Schema(description="user's first name")
    private String fname;
    
    @NotBlank
    @Schema(description="user's last name")
    private String lname;
    
    
    @Column( columnDefinition = "boolean default true" )
    @Schema(description="Is the user enabled?")
	private boolean enabled; // true or false if the user is enabled
    
    @Enumerated(EnumType.STRING)
	private Role role;
    
    @JsonIgnore
    @Schema(description="user's order as a list")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    public User() {
    	super();
    }
	

	public User(Integer id, @NotBlank String username, @NotBlank String password, @NotBlank String fname, @NotBlank String lname, boolean enabled, Role role,
			List<Order> orders) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.enabled = enabled;
		this.role = role;
		this.orders = orders;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public List<Order> getOrders() {
		return orders;
	}


	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
    
    
    
    
    
    
}
