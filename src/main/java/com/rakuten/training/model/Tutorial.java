package com.rakuten.training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tutorial")
public class Tutorial {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private long id;
	@Column(name="title")
	private String title;
	@Column(name="description")
	private String Description;
	@Column(name="status")
	private String Status;
	
	public Tutorial() {
		
	}
	public Tutorial(long id, String title, String description, String status) {
		super();
		this.id = id;
		this.title = title;
		Description = description;
		Status = status;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", title=" + title + ", Description=" + Description + ", Status=" + Status + "]";
	}
	
	
	
	
}
