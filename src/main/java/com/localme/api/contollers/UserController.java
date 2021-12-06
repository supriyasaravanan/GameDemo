package com.localme.api.contollers;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.localme.api.dao.CategoryRepo;
import com.localme.api.dao.GameRepo;
import com.localme.api.dao.UserRepo;
import com.localme.api.exception.BusinessException;
import com.localme.api.exception.ControllerException;
import com.localme.api.exception.ErrorDetails;
import com.localme.api.service.CategoryService;
import com.localme.api.service.GameService;
import com.localme.api.utils.EncryptDecryptUtil;
import com.localme.api.vo.Category;
import com.localme.api.vo.GameDetail;
import com.localme.api.vo.GameList;
import com.localme.api.vo.UserDetailsVO;
import com.localme.api.vo.UserLoginVO;


@RestController
public class UserController {


	@Autowired
	UserRepo userRepo;
	
	@Autowired
	GameRepo gameRepo;
	
	@Autowired
	CategoryRepo catRepo;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	GameService gameService;
	
	EncryptDecryptUtil encryptUtil  = new EncryptDecryptUtil();
	
	 Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/*@PostMapping("/getUser")
	public Map<String, Object> getUser(@RequestBody UserLoginVO uservo) {
		logger.trace("Entering method get user");
		logger.debug("Logged in user:"+uservo.getUsername());
		Map<String, Object> result = new HashMap<String,Object>();
		UserDetailsVO userDetails = userRepo.findByuid(uservo.getUsername());
		if(userDetails == null) {
			logger.error("User not found");
			result.put("STATUS", "FAILURE");
			result.put("ERROR", "USER NOT FOUND");
		}else {
			String decodedPwd =  encryptUtil.decrypt(userDetails);
			System.out.println("Decoded PWd : "+decodedPwd);
			if(decodedPwd.equals(uservo.getPassword())) {
			result.put("STATUS", "SUCCESS");
			userDetails.setPwd("");
			result.put("DATA", userDetails);
			}else {
				result.put("STATUS", "FAILURE");
				result.put("DATA", "Invalid Credentials");
			}
		}
		return result;
	}
	
	@PostMapping("/addUser")
	public Map<String, Object> addUser(@RequestBody UserDetailsVO uservo) {
		
		Map<String, Object> result = new HashMap<String,Object>();
		UserDetailsVO userDetails = userRepo.findByuid(uservo.getUid());
	if(userDetails == null )
		{
		String encodedPwd =  encryptUtil.encrypt(uservo);
		System.out.println("Encoded PWd : "+encodedPwd);
		 uservo.setPwd(encodedPwd);
		UserDetailsVO restultvo = userRepo.save(uservo);
		result.put("STATUS", "SUCCESS");
		restultvo.setPwd("");
		result.put("DATA", restultvo);
		}
	else {
		result.put("STATUS","FAILURE");
		
	}
		return result;
	}
	
	
	@PostMapping("/addGame")
	public ResponseEntity<?> addGame(@RequestBody GameDetail gamedetail){
		GameDetail gameDetail1 = gameRepo.findByname(gamedetail.getName());
		
		try {
			GameDetail employeeSaved = gameService.addGame(gamedetail);
			return new ResponseEntity<GameDetail>(employeeSaved, HttpStatus.CREATED);
		}catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			ControllerException ce = new ControllerException("611","Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/getGame")
	public Map<String, Object> getGame(@RequestBody GameList gamevo) {
		logger.trace("Entering method get Game");
		Map<String, Object> result = new HashMap<String,Object>();
		GameDetail gameDetail = gameRepo.findByname(gamevo.getName());
		if(gameDetail == null) {
			logger.error("game not found");
			result.put("STATUS", "FAILURE");
			result.put("ERROR", "GAME NOT FOUND");
		
		}else {
			result.put("STATUS", "SUCCESS");
			result.put("DATA", gameDetail);
			}
		return result;
	}*/
	 
	 @PostMapping("/addGame")
		public ResponseEntity<?> addGame(@RequestBody GameDetail gamedetail){
			if(gamedetail.getName().isEmpty() || gamedetail.getName().length() == 0 )
			{
				ErrorDetails errorDetails = new ErrorDetails(400,"Enter the valid username");
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);			
			}
			
			try 
			{
				GameDetail employeeSaved = gameService.addGame(gamedetail);
				return new ResponseEntity<GameDetail>(employeeSaved, HttpStatus.CREATED);
		    }
			catch (Exception e) 
			{
				ErrorDetails errorDetails = new ErrorDetails(400,"Already Present");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
			}
			
		}
		
		
		@PostMapping("/addUser")
		public ResponseEntity<?> addUser(@RequestBody UserDetailsVO userdetail){
			if(userdetail.getUid().isEmpty() || userdetail.getUid().length() == 0 )
			{
				ErrorDetails errorDetails = new ErrorDetails(400,"Enter the valid name");
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);			
			}
			try {
				UserDetailsVO employeeSaved = gameService.addUser(userdetail);
				return new ResponseEntity<UserDetailsVO>(employeeSaved, HttpStatus.CREATED);
				}
			catch (Exception e) {
				ErrorDetails errorDetails = new ErrorDetails(400,"Already Present");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
			}
			
		}
		
		@PostMapping("/getGame")
		public ResponseEntity<?>  getGame(@RequestBody GameList gamedetails) {
			logger.trace("Entering method get Game");
			try 
			{
				GameDetail gameFind=gameService.getGame(gamedetails);
				return new ResponseEntity<GameDetail>(gameFind, HttpStatus.OK);
			
			}catch (Exception e) {
				ErrorDetails errorDetails = new ErrorDetails(400,"Not present");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
			}
			
			
		}	
		
		@PostMapping("/getUser")
		public ResponseEntity<?>  getUser(@RequestBody UserLoginVO userdetails) {
			logger.trace("Entering method get User");
			try 
			{
				UserDetailsVO gameFind=gameService.getUser(userdetails);
				return new ResponseEntity<UserDetailsVO>(gameFind, HttpStatus.OK);
			}catch (Exception e) {
				ErrorDetails errorDetails = new ErrorDetails(400,"Not present");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
			}
				
		}	
	
	@PostMapping("/addCat")
	public Map<String, Object> addCat(@RequestBody Category gamevo) {
		
		Map<String, Object> result = new HashMap<String,Object>();
		Category gameDetail = catRepo.findByname(gamevo.getName());
	if(gameDetail == null )
		{
		Category restultvo = catRepo.save(gamevo);
		result.put("STATUS", "SUCCESS");
		result.put("DATA", restultvo);
		}
	else {
		result.put("STATUS","FAILURE");
		
	}
		return result;
	}
	
	@GetMapping("/findGameForCategory/{name}")
	 public List<GameDetail> findGamesForCategory(@PathVariable String name) {
		logger.trace("Entering method");
			   return gameService.findGamesForCategory(name);
	 } 
	@GetMapping("/findAllCategories")
	 Iterable<Category> findAllCategories() {
	  return categoryService.findAllCategories();
	 }
}
