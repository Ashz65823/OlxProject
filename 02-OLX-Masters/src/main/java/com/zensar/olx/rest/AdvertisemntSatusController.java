package com.zensar.olx.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.olx.bean.AdvertisementStatus;
import com.zensar.olx.service.AdvertisementStatusService;


@RestController
public class AdvertisemntSatusController {

	@Autowired
	AdvertisementStatusService service;

	@PostMapping("/advertise/addStatus")
	public AdvertisementStatus addAdvStatus(@RequestBody AdvertisementStatus aStatus)
	{
		return this.service.addAdvertisment(aStatus);
	}

	@GetMapping("/advertise/getAllStatus")
	public List<AdvertisementStatus> getAllAdvertismentStatus()
	{
		return this.service.getAllAdvertismentStatus();
	}

	@GetMapping("/advertise/status/{id}")
	public AdvertisementStatus findAdvertismentStatus(@PathVariable(name="id") int id)
	{
		return this.service.findAdvertisementStatus(id);
	}

}
