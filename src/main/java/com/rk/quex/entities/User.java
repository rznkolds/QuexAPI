package com.rk.quex.entities;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	@Column(name="uid")
	public String uid;
	
	@Column (name="name")
	public String name;
	
	@Column (name="description")
	public String description;
	
	@Column (name="profile")
	public String profile;
}
