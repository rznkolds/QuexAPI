package com.rk.quex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rk.quex.repositories.*;
import com.rk.quex.entities.*;

@RestController
@RequestMapping(path="/quex/")
public class ApiController {

	@Autowired 
	UserRepo userRepo;
	
	@Autowired 
	FavoriteRepo favoriteRepo;
	
	@Autowired 
	NotificationRepo notificationRepo;
	
	@Autowired
	CommentRepo commentRepo;
	
	@Autowired 
	AnswerRepo answerRepo;
	
	// USER PROCESS
	
	@GetMapping(path="user/info")//**********************************************************
	public User getUser(@RequestParam String uid){
			
		return userRepo.getInformation(uid);
	}
	
	@PostMapping(path="user/save")
	public ResponseEntity<String> saveUser(@RequestBody User user){
		
		User info = userRepo.getInformation(user.uid);
		
		if(info == null){
			
			userRepo.save(user);
			
			return ResponseEntity.status(HttpStatus.OK).body ("Saved");
			
		}else{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current registered");
		}
	}
	
	// FAVORITE PROCESS
	
	@GetMapping(path="favorite/list")//***************************************************
	public List<Favorite> getFavorites(@RequestParam String uid){
			
		return favoriteRepo.getFavorites(uid);
	}
	
	// NOTIFICATION PROCESS
	
	@GetMapping(path="notification/list")//***********************************************
	public List<Notification> getNotifications(@RequestParam String uid){
			
		return notificationRepo.getNotifications(uid);
	}
	
	@DeleteMapping(path="notification/delete")
	public ResponseEntity<String> deleteNotifications(@RequestParam String uid){
			
		notificationRepo.deleteNotifications(uid);
			
		return ResponseEntity.status(HttpStatus.OK).body("Deleted");
	}
	
	// COMMENT PROCESS
	
	@GetMapping(path="comment/list")//****************************************************
	public List<Comment> getComments(@RequestParam String coin){
			
		return commentRepo.getComments(coin);
	}
	
	@PostMapping(path="comment/save")//*
	public ResponseEntity<String> saveComment(@RequestBody Comment comment){
		
		Comment info = commentRepo.getInformation(comment.uid, comment.coin, comment.comment, comment.date, comment.time);
		
		if (info == null){
			
			User user = userRepo.getInformation(comment.uid);
			
			Comment cmt = new Comment();
			cmt = comment;
			cmt.name = user.name;
			cmt.profile = user.profile;
			
			commentRepo.save(cmt);
			
			Favorite favorite = favoriteRepo.getInformation(comment.uid, comment.coin);
			
			if(favorite == null){
				
				Favorite crypto = new Favorite();
				crypto.coin = comment.coin;
				crypto.uid  = comment.uid;
				
				favoriteRepo.save(crypto);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body ("Saved");
			
		}else{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current registered");
		}
	}
	
	@DeleteMapping(path="comment/delete")
	public ResponseEntity<String> deleteCoinComment(@RequestBody Comment comment) {
		
		Comment info = commentRepo.getInformation(comment.uid, comment.coin, comment.comment, comment.date, comment.time);
		
		if (info != null){
			
			commentRepo.deleteComment(comment.comment);
			
			answerRepo.deleteChildAnswers(comment.uid,comment.date, comment.time); 
			
			List<Comment> list = commentRepo.getList(comment.uid, comment.coin);
			
			if(list.isEmpty()){		
				
				favoriteRepo.deleteFavorite(comment.uid, comment.coin);
			}
				
			return ResponseEntity.status(HttpStatus.OK).body("Deleted");
			
		}else{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not available already");
		}
	}
	
	@PutMapping(path="comment/update")
	public ResponseEntity<String> updateComment(@RequestBody Comment comment) {
		
		Comment info = commentRepo.getInformation(comment.uid, comment.coin, comment.comment, comment.date, comment.time);
		
		if (info != null){
			
			User user = userRepo.getInformation(comment.uid);
			
			Comment cmt = new Comment();
			cmt = comment;
			cmt.name = user.name;
			cmt.profile = user.profile;
			
			commentRepo.updateComment(cmt.comment, cmt.uid, cmt.name, cmt.profile, cmt.coin, cmt.date, cmt.time);
				
			return ResponseEntity.status(HttpStatus.OK).body("Updated");
			
		}else{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
		}
	}
	
	// ANSWER PROCESS
	
	@GetMapping(path="answer/list")//****************************************************
	public List<Answer> getAnswers(@RequestParam String coin, @RequestParam String top, @RequestParam int date, @RequestParam int time){
			
		return answerRepo.getAnswers(coin, top, date, time);
	}
	
	@PostMapping(path="answer/save")
	public ResponseEntity<String> saveAnswer(@RequestBody Answer answer) {
		
		Answer info = answerRepo.getInformation(answer.uid, answer.top, answer.coin, answer.comment, answer.date, answer.time);
		
		if(info == null){
			
			User user = userRepo.getInformation(answer.uid);
			
			Answer ans = new Answer();
			ans = answer;
			ans.name = user.name;
			ans.profile  = user.profile;
			
			answerRepo.save(ans);
			
			Favorite favorite = favoriteRepo.getInformation(answer.uid, answer.coin);
			
			if(favorite == null){
				
				Favorite crypto = new Favorite();
				crypto.uid = answer.uid;
				crypto.coin = answer.coin;
				
				favoriteRepo.save(crypto);
			}
			
			if(answer.uid != answer.top){
				
				Notification notification = new Notification();
				notification.uid  = answer.top;
				notification.coin = answer.coin;
				notification.name = user.name;
				notification.profile  = user.profile;
				notification.date = answer.date;
				notification.time = answer.time;
					
				notificationRepo.save(notification);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body ("Saved");
			
		}else{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current registered");
		}
	}

	@DeleteMapping(path="answer/delete")
	public ResponseEntity<String> deleteAnswer(@RequestBody Answer answer){
		
		Answer info = answerRepo.getInformation(answer.uid, answer.top, answer.coin, answer.comment, answer.date, answer.time);
		
		if (info != null ){
			
			answerRepo.deleteAnswer(answer.comment);
			
			List<Answer> list = answerRepo.getList(answer.uid, answer.top, answer.coin);
			
			if (list.isEmpty()){	
				
				favoriteRepo.deleteFavorite(answer.uid, answer.coin);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body("Deleted");
			
		}else{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not available already");
		}
	}
	
	@PutMapping(path="comment/update")
	public ResponseEntity<String> updateAnswer(@RequestBody Answer answer) {
		
		Answer info = answerRepo.getInformation(answer.uid, answer.top, answer.coin, answer.comment, answer.date, answer.time);
		
		if (info != null){
			
			User user = userRepo.getInformation(answer.uid);
			
			Answer ans = new Answer();
			ans = answer;
			ans.name = user.name;
			ans.profile = user.profile;
			
			answerRepo.updateAnswer(ans.comment, ans.uid, ans.name, ans.top, ans.profile, ans.coin, ans.date, ans.time);
				
			return ResponseEntity.status(HttpStatus.OK).body("Updated");
			
		}else{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
		}
	}
}
