package com.revature.model;

import java.awt.Image;
import java.time.LocalDateTime;

public class Reimbursement {
	private Integer id;
	private Double amount;
	private String submitted;
	private String resolved;
	private String description;
	private String author;
	private String resolver;
	private String status;
	private String type;
	private Image receipt;
	
	public Reimbursement(Integer id, Double ammount, String submitted, String resolved, String description,
			String author, String resolver, String status, String type) {
		super();
		this.id = id;
		this.amount = ammount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}
	
	public Reimbursement(Double amount, String submitted, String description, String author, String status,
			String type) {
		super();
		this.amount = amount;
		this.submitted = submitted;
		this.description = description;
		this.author = author;
		this.status = status;
		this.type = type;
	}

	public Reimbursement(Integer id, String resolved, String status) {
		super();
		this.id = id;
		this.resolved = resolved;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double ammount) {
		this.amount = ammount;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getResolved() {
		return resolved;
	}

	public void setResolved(String resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getResolver() {
		return resolver;
	}

	public void setResolver(String resolver) {
		this.resolver = resolver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Image getReceipt() {
		return receipt;
	}

	public void setReceipt(Image receipt) {
		this.receipt = receipt;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", description=" + description + ", author=" + author + ", resolver=" + resolver + ", status="
				+ status + ", type=" + type + "]";
	}
}

