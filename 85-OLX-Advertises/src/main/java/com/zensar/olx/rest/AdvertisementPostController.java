package com.zensar.olx.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.zensar.olx.bean.FileterCriteriaRequest;
import com.zensar.olx.bean.NewAdvertisementPostRequest;
import com.zensar.olx.bean.NewAdvertisementPostResponse;
import com.zensar.olx.bean.OlxUser;
import com.zensar.olx.service.AdvertismentPostService;

@RestController
public class AdvertisementPostController {

	@Autowired
	AdvertismentPostService service;

	// 8 Posts new advertise
	@PostMapping("/advertise/{un}")
	public NewAdvertisementPostResponse add(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "un") String userName) {//Receiving path variable
		AdvertisementPost post = new AdvertisementPost();
		post.setTitle(request.getTitle());
		post.setPrice(request.getPrice());
		post.setDescription(request.getDescription());

		int categoryId = request.getCategoryId();
		RestTemplate restTemplate = new RestTemplate();//to interact between micro service->83 & 84
		Category category;
		String url = "http://localhost:9052/advertise/getcategory/" + categoryId;//point to category service
		category = restTemplate.getForObject(url, Category.class);//getForObject -->connect with micro service variable
		post.setCategory(category);//store in post obj

		url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		post.setOlxUser(olxUser);

		AdvertisementStatus advertisementStatus = new AdvertisementStatus(1, "OPEN");//new request so hard coded
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
//--------------------------------------------------------------------------------------------------------
	// 9 Updates existing advertise

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
//----------------------------------------------------------------------------------------------------------------
	// 10 Reads all advertisements posted by logged in user

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
	// 11 Reads specific advertisement posed by logged in user
	@GetMapping("/user/advertise/{aId}/{userName}")
	public NewAdvertisementPostResponse f4(@PathVariable(name = "aId") int id,
			@PathVariable(name = "userName") String userName) {
		AdvertisementPost advertisementPost = this.service.getAdvertisementById(id);

		RestTemplate restTemplate = new RestTemplate();

		String url = null;
		url = "http://localhost:9051/user/find/" + userName;
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		advertisementPost.setOlxUser(olxUser);

		Category category;
		url = "http://localhost:9052/advertise/getcategory/" + advertisementPost.getCategory().getId();
		category = restTemplate.getForObject(url, Category.class);
		advertisementPost.setCategory(category);

		url = "http://localhost:9052/advertise/status/" + advertisementPost.getAdvertisementStatus().getId();
		AdvertisementStatus advertisementStatus;
		advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
		advertisementPost.setAdvertisementStatus(advertisementStatus);
		System.out.println("AdvertisementStatus" + advertisementPost);

		NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();

		postRespone.setId(advertisementPost.getId());
		postRespone.setTitle(advertisementPost.getTitle());
		postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
		postRespone.setDescription(advertisementPost.getDescription());
		postRespone.setPrice(advertisementPost.getPrice());
		postRespone.setCategory(advertisementPost.getCategory().getName());
		postRespone.setCreatedDate(advertisementPost.getCreatedDate());
		postRespone.setModifiedDate(advertisementPost.getModifiedDate());
		postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());

		return postRespone;

	}

	// --------------------------------------------------------------------------------------------------
	// 12 Deletes specific advertisement posted by logged in user
	@DeleteMapping("/user/advertise/{aId}")
	public boolean delteAdvertisementById(@PathVariable(name = "aId") int id) {
		AdvertisementPost advertisementPost = this.service.getAdvertisementById(id);
		System.out.println(advertisementPost);
		return this.service.deleteAdvertisement(advertisementPost);
	}

//----------------------------------------------------------------------------------------------------------------------
	// 13 Search advertisements based upon given filter criteria
	@GetMapping("/advertise/search/filtercriteria")
	public List<NewAdvertisementPostResponse> searchBasedOnAll(@RequestBody FileterCriteriaRequest criteriaRequest,String searchText) {
		LocalDate dateFrom=criteriaRequest.getFromDate();
		LocalDate toDate=criteriaRequest.getToDate();
		List<AdvertisementPost> allPost=this.service.getAllAdvertisement();
		RestTemplate restTemplate=new RestTemplate();
		//System.out.println(searchText);
		System.out.println(criteriaRequest);
		for(AdvertisementPost advertisementPost:allPost)
		{
			String url = null;
			url = "http://localhost:9051/user/" + advertisementPost.getOlxUser().getOlxUserId();
			OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
			advertisementPost.setOlxUser(olxUser);

			Category category;
			url = "http://localhost:9052/advertise/getcategory/" + advertisementPost.getCategory().getId();
			category = restTemplate.getForObject(url, Category.class);
			advertisementPost.setCategory(category);

			url = "http://localhost:9052/advertise/status/" + advertisementPost.getAdvertisementStatus().getId();
			AdvertisementStatus advertisementStatus;
			advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
			advertisementPost.setAdvertisementStatus(advertisementStatus);
		}
		List<AdvertisementPost>filterPosts=new ArrayList<>();
		for(AdvertisementPost advertisementPost:allPost)
		{
			if((advertisementPost.getCategory().getName().contains(searchText))||
					(advertisementPost.getTitle().contains(searchText))
					||(advertisementPost.getDescription().contains(searchText))||
					(advertisementPost.getAdvertisementStatus().getStatus().contains(searchText))||
					(advertisementPost.getCreatedDate().toString().contains(searchText))||
					(advertisementPost.getModifiedDate().toString().contains(searchText))
					)
			{
				filterPosts.add(advertisementPost);
			}
		}

		List<NewAdvertisementPostResponse> responce=new ArrayList<>();
		for(AdvertisementPost advertisementPost:filterPosts)
		{
			NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();
			

			postRespone.setId(advertisementPost.getId());
			postRespone.setTitle(advertisementPost.getTitle());
			postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
			postRespone.setDescription(advertisementPost.getDescription());
			postRespone.setPrice(advertisementPost.getPrice());
			postRespone.setCategory(advertisementPost.getCategory().getName());
			postRespone.setCreatedDate(advertisementPost.getCreatedDate());
			postRespone.setModifiedDate(advertisementPost.getModifiedDate());
			postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
			responce.add(postRespone);
		}
		System.out.println(responce);
		return responce;

	}

//----------------------------------------------------------------------------------------------------------
	// 14 Matches advertisements using the provided 'searchText' within all fields
	// of an advertise.
	@GetMapping("/advertise/{search}")
	public List<NewAdvertisementPostResponse> f7(@PathVariable(name="search")String searchText) {
		List<AdvertisementPost>allPost=this.service.getAllAdvertisement();
		System.out.println(allPost);
		RestTemplate restTemplate=new RestTemplate();
		for(AdvertisementPost advertisementPost:allPost)
		{
			String url = null;
			url = "http://localhost:9051/user/" + advertisementPost.getOlxUser().getOlxUserId();
			OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
			advertisementPost.setOlxUser(olxUser);

			Category category;
			url = "http://localhost:9052/advertise/getcategory/" + advertisementPost.getCategory().getId();
			category = restTemplate.getForObject(url, Category.class);
			advertisementPost.setCategory(category);

			url = "http://localhost:9052/advertise/status/" + advertisementPost.getAdvertisementStatus().getId();
			AdvertisementStatus advertisementStatus;
			advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
			advertisementPost.setAdvertisementStatus(advertisementStatus);
			
		}
		List<AdvertisementPost>filterPosts=new ArrayList<>();
		for(AdvertisementPost advertisementPost:allPost)
		{
			if((advertisementPost.getCategory().getName().toLowerCase().contains(searchText.toLowerCase()))||
					(advertisementPost.getTitle().toLowerCase().contains(searchText.toLowerCase()))
					||(advertisementPost.getDescription().toLowerCase().contains(searchText.toLowerCase()))||
					(advertisementPost.getAdvertisementStatus().getStatus().toLowerCase().contains(searchText.toLowerCase()))
					)
			{
				filterPosts.add(advertisementPost);
			}
		}
		List<NewAdvertisementPostResponse> responce=new ArrayList<>();
		for(AdvertisementPost advertisementPost:filterPosts)
		{
			NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();
			

			postRespone.setId(advertisementPost.getId());
			postRespone.setTitle(advertisementPost.getTitle());
			postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
			postRespone.setDescription(advertisementPost.getDescription());
			postRespone.setPrice(advertisementPost.getPrice());
			postRespone.setCategory(advertisementPost.getCategory().getName());
			postRespone.setCreatedDate(advertisementPost.getCreatedDate());
			postRespone.setModifiedDate(advertisementPost.getModifiedDate());
			postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
			responce.add(postRespone);
		}
		return responce;
	}

//----------------------------------------------------------------------------------------------------------
	// 15 Return advertise details by id
	@GetMapping("/user/advertiseById/{aid}")
	public NewAdvertisementPostResponse getAdvertismentByUserId(@PathVariable(name = "aid") int id) {
		AdvertisementPost advertisementPost = this.service.getAdvertisementById(id);
		RestTemplate restTemplate = new RestTemplate();

		String url = null;
		url = "http://localhost:9051/user/" + advertisementPost.getOlxUser().getOlxUserId();
		OlxUser olxUser = restTemplate.getForObject(url, OlxUser.class);
		advertisementPost.setOlxUser(olxUser);

		Category category;
		url = "http://localhost:9052/advertise/getcategory/" + advertisementPost.getCategory().getId();
		category = restTemplate.getForObject(url, Category.class);
		advertisementPost.setCategory(category);

		url = "http://localhost:9052/advertise/status/" + advertisementPost.getAdvertisementStatus().getId();
		AdvertisementStatus advertisementStatus;
		advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
		advertisementPost.setAdvertisementStatus(advertisementStatus);

		System.out.println(advertisementPost);

		NewAdvertisementPostResponse postRespone = new NewAdvertisementPostResponse();

		postRespone.setId(advertisementPost.getId());
		postRespone.setTitle(advertisementPost.getTitle());
		postRespone.setUserName(advertisementPost.getOlxUser().getUserName());
		postRespone.setDescription(advertisementPost.getDescription());
		postRespone.setPrice(advertisementPost.getPrice());
		postRespone.setCategory(advertisementPost.getCategory().getName());
		postRespone.setCreatedDate(advertisementPost.getCreatedDate());
		postRespone.setModifiedDate(advertisementPost.getModifiedDate());
		postRespone.setStatus(advertisementPost.getAdvertisementStatus().getStatus());

		System.out.println(postRespone);
		return postRespone;
	}
}
