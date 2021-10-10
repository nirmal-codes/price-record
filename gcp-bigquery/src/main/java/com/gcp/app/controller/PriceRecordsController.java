package com.gcp.app.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcp.app.entity.PriceRecords;
import com.gcp.app.service.PriceRecordsService;

@RestController
public class PriceRecordsController {

	protected Logger logger = Logger.getLogger(PriceRecordsController.class.getName());

	@Autowired
	private PriceRecordsService priceRecordsService;

	@RequestMapping(value = "/price/{id}", method = RequestMethod.GET)
	public ResponseEntity<PriceRecords> getPriceRecord(@PathVariable("id") Integer id) {
		try {
			String status = priceRecordsService.getPriceRecord(id);
			if (!status.isEmpty()) {
				return new ResponseEntity(status, new HttpHeaders(), HttpStatus.OK);
			} else {
				return new ResponseEntity("No Data Found", new HttpHeaders(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
