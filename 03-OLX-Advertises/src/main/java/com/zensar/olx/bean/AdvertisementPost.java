package com.zensar.olx.bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="advertisepost")
public class AdvertisementPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String title;
	@Column
	private double price;
	@Column
	private String description;
	@Column
	private LocalDate createdDate;
	@Column
	private LocalDate modifiedDate;
	@Lob//for photo
	private byte[]photo;
	@Embedded
	@Column
	private Category category;
	@Embedded
	@Column
	private OlxUser olxUser;
	@Embedded
	@Column
	private AdvertisementStatus advertisementStatus;
	
	
	public AdvertisementPost(int id, String title, double price, String discription, LocalDate createdDate,
			LocalDate mofifiedDate, byte[] photo, Category category, OlxUser olxUser,
			AdvertisementStatus advertisementStatus) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.description = discription;
		this.createdDate = createdDate;
		this.modifiedDate = mofifiedDate;
		this.photo = photo;
		this.category = category;
		this.olxUser = olxUser;
		this.advertisementStatus = advertisementStatus;
	}
	
	public AdvertisementPost(String title, double price, String discription, LocalDate createdDate,
			LocalDate mofifiedDate, byte[] photo, Category category, OlxUser olxUser,
			AdvertisementStatus advertisementStatus) {
		super();
		this.title = title;
		this.price = price;
		this.description = discription;
		this.createdDate = createdDate;
		this.modifiedDate = mofifiedDate;
		this.photo = photo;
		this.category = category;
		this.olxUser = olxUser;
		this.advertisementStatus = advertisementStatus;
	}

	public AdvertisementPost(int id) {
		super();
		this.id = id;
	}
	public AdvertisementPost() {
		super();
		this.createdDate=LocalDate.now();
		this.modifiedDate=LocalDate.now();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public OlxUser getOlxUser() {
		return olxUser;
	}
	public void setOlxUser(OlxUser olxUser) {
		this.olxUser = olxUser;
	}
	public AdvertisementStatus getAdvertisementStatus() {
		return advertisementStatus;
	}
	public void setAdvertisementStatus(AdvertisementStatus advertisementStatus) {
		this.advertisementStatus = advertisementStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "AdvertisementPost [id=" + id + ", title=" + title + ", price=" + price + ", description=" + description
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", photo="
				+ Arrays.toString(photo) + ", category=" + category + ", olxUser=" + olxUser + ", advertisementStatus="
				+ advertisementStatus + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AdvertisementPost))
			return false;
		AdvertisementPost other = (AdvertisementPost) obj;
		return id == other.id;
	}
}
