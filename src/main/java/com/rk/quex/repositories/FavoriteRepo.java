package com.rk.quex.repositories;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.rk.quex.entities.Favorite;

public interface FavoriteRepo extends JpaRepository<Favorite , Long > {
	
	// All CRUD database methods
	
	//Gets the coin type of the specified user
	@Query( value="SELECT * FROM favorites WHERE uid=:uid AND coin=:coin" , nativeQuery=true )
	public Favorite getInformation (String uid , String coin );
	
	//Gets the coin list of the specified user
	@Query( value="SELECT * FROM favorites WHERE uid=:uid" , nativeQuery=true )
	public List<Favorite> getFavorites (String uid);
		
	//Deletes the specified user's coin type
	@Modifying
	@Transactional
	@Query( value="DELETE FROM favorites WHERE uid=:uid AND coin=:coin" , nativeQuery=true )
	public void deleteFavorite(String uid , String coin );
}
