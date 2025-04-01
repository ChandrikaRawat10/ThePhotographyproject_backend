package com.example.photographyProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.photographyProject.model.Vendor;
import com.example.photographyProject.repository.VendorRepository;
@Service
public class VendorDetailsService {
	 @Autowired
	    private VendorRepository vendorRepository;

	    public void addVendor(Vendor vendor) {
	        vendorRepository.save(vendor);
	    }

	    public List<Vendor> getAllVendors() {
	        return vendorRepository.findAll();
	    }

	    public Vendor getVendorById(Long id) {
	        return vendorRepository.findById(id).orElse(null);
	    }
	    
	    public void deleteVendor(Long id) {
	        vendorRepository.deleteById(id);
	    }

	    public void deleteAllVendors() {
	        vendorRepository.deleteAll();
	    }

	    public Vendor updateVendor(Long id, Vendor updatedVendor) {
	        return vendorRepository.findById(id).map(vendor -> {
	            vendor.setFullName(updatedVendor.getFullName());
	            vendor.setLocation(updatedVendor.getLocation());
	            vendor.setContact(updatedVendor.getContact());
	            vendor.setPortfolio(updatedVendor.getPortfolio());
	            vendor.setDescription(updatedVendor.getDescription());
	            vendor.setServicesOffered(updatedVendor.getServicesOffered());
	            vendor.setJoinedDate(updatedVendor.getJoinedDate());
	            vendor.setPaymentTerms(updatedVendor.getPaymentTerms());
	            vendor.setTravelCost(updatedVendor.getTravelCost());
	            return vendorRepository.save(vendor);
	        }).orElse(null);
	    }
}
