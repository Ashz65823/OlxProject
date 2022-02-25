package com.zensar.olx.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zensar.olx.bean.AdvertisementPost;
import com.zensar.olx.bean.AdvertisementStatus;
import com.zensar.olx.bean.Category;
import com.zensar.olx.bean.NewAdvertisementPostRequest;
import com.zensar.olx.bean.NewAdvertisementPostResponse;
import com.zensar.olx.bean.OlxUser;
import com.zensar.olx.service.AdvertismentPostService;

@RestController
public class AdvertisementPostController {

	@Autowired
	AdvertismentPostService service;

	@PostMapping("/advertise/{un}")
	public NewAdvertisementPostResponse add(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "un") String userName) {
		AdvertisementPost post = new AdvertisementPost();
		post.setTitle(request.getTitle());
		post.setPrice(request.getPrice());
		post.setDescription(request.getDescription());

		int categoryId = request.getCategoryId();
		RestTemplate restTemplate = new RestTemplate();
		Category category;
		String url = "http://localhost:9052/advertise/getcategory/" + categoryId;
		category = restTemplate.getForObject(url, Category.class);
		post.setCategory(category);

		url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		post.setOlxUser(olxUser);

		AdvertisementStatus advertisementStatus = new AdvertisementStatus(1, "OPEN");
		post.setAdvertisementStatus(advertisementStatus);

		AdvertisementPost advertisementPost = this.service.addAdvertisement(post);

		NewAdvertisementPostResponse responce = new NewAdvertisementPostResponse();// saved in db
		responce.setId(advertisementPost.getId());
		responce.setTitle(advertisementPost.getTitle());
		responce.setPrice(advertisementPost.getPrice());
		responce.setCategory(advertisementPost.getCategory().getName());
		responce.setDescription(advertisementPost.getDescription());
		responce.setUserName(advertisementPost.getOlxUser().getUserName());
		responce.setCreatedDate(advertisementPost.getCreatedDate());
		responce.setModifiedDate(advertisementPost.getModifiedDate());
		responce.setStatus(advertisementPost.getAdvertisementStatus().getStatus());

		return responce;
	}

	@PutMapping("/advertise/{aid}/{userName}")
	public NewAdvertisementPostResponse f2(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "aid") int id, @PathVariable(name = "userName") String userName) {
		AdvertisementPost post = this.service.getAdvertisementById(id);
		post.setTitle(request.getTitle());
		post.setDescription(request.getDescription());
		post.setPrice(request.getPrice());

		RestTemplate restTemplate = new RestTemplate();
		Category category;
		String url = "http://localhost:9052/advertise/getcategory/" + request.getCategoryId();
		category = restTemplate.getForObject(url, Category.class);
		post.setCategory(category);

		url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		post.setOlxUser(olxUser);

		url = "http://localhost:9052/advertise/status/" + request.getStatusId();
		AdvertisementStatus advertisementStatus;
		advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
		post.setAdvertisementStatus(advertisementStatus);

		AdvertisementPost advertisementPost = this.service.updateAdvertisement(post); // writing into db

		NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();
		postRespone.setId(advertisementPost.getId());
		postRespone.setTitle(advertisementPost.getTitle());
		postRespone.setDescription(advertisementPost.getDescription());
		postRespone.setPrice(advertisementPost.getPrice());
		postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
		postRespone.setCategory(advertisementPost.getCategory().getName());
		postRespone.setCreatedDate(advertisementPost.getCreatedDate());
		postRespone.setModifiedDate(advertisementPost.getModifiedDate());
		postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());

		return postRespone;
	}

	@GetMapping("/user/advertise/{userName}")
	public List<NewAdvertisementPostResponse> f3(@PathVariable(name = "userName") String userName) {
		List<AdvertisementPost> advPost = this.service.getAllAdvertisement();
		RestTemplate restTemplate = new RestTemplate();
		List<AdvertisementPost> filterList = new ArrayList<>();
		String url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		// ;
		for (AdvertisementPost post : advPost) {

			Category category;
			url = "http://localhost:9052/advertise/getcategory/" + post.getCategory().getId();
			category = restTemplate.getForObject(url, Category.class);
			post.setCategory(category);

			url = "http://localhost:9052/advertise/status/" + post.getAdvertisementStatus().getId();
			AdvertisementStatus advertisementStatus;
			advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
			post.setAdvertisementStatus(advertisementStatus);
			System.out.println("AdvertisementStatus" + post);

			if (olxUser.getOlxUserId() == post.getOlxUser().getOlxUserId()) {
				post.setOlxUser(olxUser);
				filterList.add(post);
			}
		}
		List<NewAdvertisementPostResponse> responseList = new ArrayList<>();
		for (AdvertisementPost advertisementPost : filterList) {
			NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();
			postRespone.setId(advertisementPost.getId());
			postRespone.setTitle(advertisementPost.getTitle());
			postRespone.setDescription(advertisementPost.getDescription());
			postRespone.setPrice(advertisementPost.getPrice());
			postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
			postRespone.setCategory(advertisementPost.getCategory().getName());
			postRespone.setCreatedDate(advertisementPost.getCreatedDate());
			postRespone.setModifiedDate(advertisementPost.getModifiedDate());
			postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());

			responseList.add(postRespone);
		}
		return responseList;
	}

//--------------------------------------------------------------------------------------------------------	
	@GetMapping("/user/advertise/{advertiseId}/{userName}")
	public List<NewAdvertisementPostResponse> f4(@PathVariable(name = "{advertiseId}") int id,@PathVariable(name="userName") String userName) {
		
		List<NewAdvertisementPostResponse> allResponses = new ArrayList<>();
		List<AdvertisementPost> posts = this.service.getAllAdvertisement();
		RestTemplate restTemplate = new RestTemplate();
		NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();
		
		for(AdvertisementPost advertisementPost:posts)
		{
			String url = "http://localhost:9051/user/find/" + userName;
			OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
			System.out.println(url);
			if(olxUser.getUserName().equals(userName))
			{
				if(olxUser.getOlxUserId()==id)
				{
					Category category;
					url = "http://localhost:9052/advertise/getcategory/" + advertisementPost.getCategory().getId();
					category = restTemplate.getForObject(url, Category.class);
					advertisementPost.setCategory(category);

					url = "http://localhost:9052/advertise/status/" + advertisementPost.getAdvertisementStatus().getId();
					AdvertisementStatus advertisementStatus;
					advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
					advertisementPost.setAdvertisementStatus(advertisementStatus);
					System.out.println("AdvertisementStatus" + advertisementPost);

					postRespone.setId(advertisementPost.getId());
					postRespone.setId(advertisementPost.getId());
					postRespone.setTitle(advertisementPost.getTitle());
					postRespone.setDescription(advertisementPost.getDescription());
					postRespone.setPrice(advertisementPost.getPrice());
					postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
					postRespone.setCategory(advertisementPost.getCategory().getName());
					postRespone.setCreatedDate(advertisementPost.getCreatedDate());
					postRespone.setModifiedDate(advertisementPost.getModifiedDate());
					postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
					allResponses.add(postRespone);
				}
			}
			else
			{
				return null;
			}
			
		}	
			
		System.out.println(postRespone + "----------------------------");
		return allResponses;

	}

}
