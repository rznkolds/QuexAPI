package com.rk.quex.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import com.rk.quex.entities.Notification;

public interface NotificationRepo extends JpaRepository<Notification , Long >  {
	
	@Query( value="SELECT * FROM notifications WHERE uid=:uid" , nativeQuery=true )
	public List<Notification> getNotifications(String uid);
	
	@Query( value="DELETE FROM notifications WHERE uid=:uid" , nativeQuery=true )
	public List<Notification> deleteNotifications(String uid);
}

