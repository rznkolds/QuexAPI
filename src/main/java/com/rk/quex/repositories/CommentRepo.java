package com.rk.quex.repositories;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.rk.quex.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment , Long >{

	// All CRUD database methods
	
	// It only retrieves all the information of a comment .
	@Query( value="SELECT * FROM comments WHERE uid=:uid AND coin=:coin AND comment=:comment AND date=:date AND time=:time" , nativeQuery=true )
	public Comment getInformation(String uid, String coin, String comment, int date, int time);
	
	// Retrieves the current user's answer list of certain coins.
	@Query( value="SELECT * FROM comments WHERE uid=:uid AND coin=:coin" , nativeQuery=true )
	public List<Comment> getList(String uid, String coin);
	
	//Lists comments for a specific coin
	@Query( value="SELECT * FROM comments WHERE coin=:coin ORDER BY id DESC" , nativeQuery=true )
	public List<Comment> getComments(String coin);
	
	// Deletes the specified comment
	@Modifying
	@Transactional
	@Query( value="DELETE FROM comments WHERE comment=:comment" , nativeQuery=true )
	public void deleteComment(String comment);
	
	@Modifying
	@Transactional
	@Query( value="UPDATE comments SET comment=comment WHERE uid =:uid AND name=:name AND profile=:profile AND coin=:coin AND date=:date AND time=:time" , nativeQuery=true )
	public void updateComment(String comment, String uid, String name, String profile, String coin, int date, int time);
}