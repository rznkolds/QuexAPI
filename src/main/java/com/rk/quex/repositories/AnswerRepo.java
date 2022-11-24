package com.rk.quex.repositories;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.rk.quex.entities.Answer;

public interface AnswerRepo extends JpaRepository<Answer , Long>{
	
	// All CRUD database methods
	
	// It only retrieves all the information of a comment .
	@Query( value="SELECT * FROM answers WHERE uid=:uid AND top=:top AND coin=:coin AND comment=:comment AND date=:date AND time=:time" , nativeQuery=true )
	public Answer getInformation(String uid, String top, String coin, String comment,int date, int time);
	
	// Retrieves the current user's answer list of certain coins.
	@Query( value="SELECT * FROM answers WHERE uid=:uid AND top=:top AND coin=:coin" , nativeQuery=true )
	public List<Answer> getList(String uid, String top, String coin);
	
	// Retrieves all answers from the parent user as a list
	@Query( value="SELECT * FROM answers WHERE coin=:coin AND top=:top AND date=:date AND time=:time" , nativeQuery=true )
	public List<Answer> getAnswers(String coin, String top, int date, int time);
	
	// Deletes the specified comment
	@Modifying
	@Transactional
	@Query( value="DELETE FROM answers WHERE comment=:comment" , nativeQuery=true )
	public void deleteAnswer(String comment);
	
	// Deletes all child comments of the parent user
	@Modifying
	@Transactional
	@Query( value="DELETE FROM answers WHERE top=:top AND date=:date AND time=:time" , nativeQuery=true )
	public void deleteChildAnswers(String top, int date, int time);

	@Modifying
	@Transactional
	@Query( value="UPDATE answers SET comment=:comment WHERE uid=:uid AND name=:name AND top=:top AND profile=:profile AND coin=:coin AND date=:date AND time=:time" , nativeQuery=true)
	public void updateAnswer(String comment, String uid, String name, String top, String profile, String coin, int date, int time);
}
