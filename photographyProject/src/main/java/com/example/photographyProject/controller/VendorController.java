package com.example.photographyProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.photographyProject.model.Vendor;
import com.example.photographyProject.service.VendorDetailsService;

@RestController
@RequestMapping("/Vendors")
public class VendorController {

	@Autowired
	private VendorDetailsService vendorService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addVendor(@RequestBody Vendor vendor){
		vendorService.addVendor(vendor);
		return new ResponseEntity<>("Vendor added successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Vendor>> getAllVendors(){
		return new ResponseEntity<>(vendorService.getAllVendors(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable long id){
		Vendor vendor = vendorService.getVendorById(id);
        if (vendor != null) {
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteVendorById(@PathVariable long id){
		vendorService.deleteVendor(id);
		return new ResponseEntity<>("Vendor deleted successfully",HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteAll")
	public ResponseEntity<String> deleteVendors(){
		vendorService.deleteAllVendors();
		return new ResponseEntity("All Vendors Deleted Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor updatedVendor) {
        Vendor vendor = vendorService.updateVendor(id, updatedVendor);
        if (vendor != null) {
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
