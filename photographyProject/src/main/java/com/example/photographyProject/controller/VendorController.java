package com.example.photographyProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.photographyProject.model.Vendor;
import com.example.photographyProject.service.VendorDetailsService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Vendors")
public class VendorController {

	@Autowired
	private VendorDetailsService vendorService;
	
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> addVendor(@RequestBody Vendor vendor){
	    vendorService.addVendor(vendor);

	    // Create a response map with the success message
	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Vendor added successfully");

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
	@GetMapping("/list")
	public ResponseEntity<List<Vendor>> getAllVendors(){
		return new ResponseEntity<>(vendorService.getAllVendors(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable("id") Long id) { 
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
