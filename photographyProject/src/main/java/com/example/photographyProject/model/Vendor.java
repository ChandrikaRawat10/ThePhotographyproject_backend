package com.example.photographyProject.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String location;
    private String contact;
    @ElementCollection
    private List<String> portfolio;
    private String description;
    @ElementCollection
    private List<String> servicesOffered;
    private LocalDate joinedDate;
    private String paymentTerms;
    private String travelCost;

    private static final LocalDate SITE_LAUNCH_DATE = LocalDate.of(2025, 3, 1);

    public Vendor() {}

    public Vendor(String fullName, String location, String contact, List<String> portfolio, String description,
                  List<String> servicesOffered, LocalDate joinedDate, String paymentTerms, String travelCost) {
        this.fullName = fullName;
        this.location = location;
        this.contact = contact;
        this.portfolio = portfolio;
        this.description = description;
        this.servicesOffered = servicesOffered;
        this.joinedDate = joinedDate;
        this.paymentTerms = paymentTerms;
        this.travelCost = travelCost;
    }

    public int getYearsOnSite() {
        return Period.between(SITE_LAUNCH_DATE, LocalDate.now()).getYears();
    }

    public int getMonthsOnSite() {
        return Period.between(SITE_LAUNCH_DATE, LocalDate.now()).getMonths();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<String> getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(List<String> portfolio) {
		this.portfolio = portfolio;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getServicesOffered() {
		return servicesOffered;
	}

	public void setServicesOffered(List<String> servicesOffered) {
		this.servicesOffered = servicesOffered;
	}

	public LocalDate getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(LocalDate joinedDate) {
		this.joinedDate = joinedDate;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getTravelCost() {
		return travelCost;
	}

	public void setTravelCost(String travelCost) {
		this.travelCost = travelCost;
	}

	public static LocalDate getSiteLaunchDate() {
		return SITE_LAUNCH_DATE;
	}
	
}
