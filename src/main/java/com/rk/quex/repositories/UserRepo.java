package com.rk.quex.repositories;

import org.springframework.data.jpa.repository.*;
import com.rk.quex.entities.User;

public interface UserRepo extends JpaRepository<User , Long > {
	
	@Query( value="SELECT * FROM users WHERE uid=:uid" , nativeQuery=true )
	public User getInformation(String uid);
}
