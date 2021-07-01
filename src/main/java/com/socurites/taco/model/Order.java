package com.socurites.taco.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.Data;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable {
	@Id
	private Long id;
	private String deliveryName;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryState;
	private String deliveryZip;
	private String ccNumber;
	private String ccExpiration;
	private String ccCVV;
	private Date placedAt;
	
	@ManyToMany(targetEntity = Taco.class)
	private List<Taco> tacos = new ArrayList<>();
	
	public void addDesign(Taco design) {
		this.tacos.add(design);
	}
	
	@PrePersist
	void placedAt() {
		this.placedAt = new Date();
	}
}
