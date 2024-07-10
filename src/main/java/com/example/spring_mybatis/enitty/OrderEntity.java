package com.example.spring_mybatis.enitty;

import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.spring_mybatis.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table ( name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "price", nullable = false)
	private Long price;
	
	@Column( name = "expiry_date")
	@Temporal(TemporalType.DATE)
	private Date expDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public OrderEntity(Long id, Long price, Date expDate) {
		super();
		this.id = id;
		this.price = price;
		this.expDate = expDate;
	}

	public OrderEntity() {
	}
}
