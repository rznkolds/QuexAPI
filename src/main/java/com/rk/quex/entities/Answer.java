package com.rk.quex.entities;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="answers")
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	@Column(name="uid")
	public String uid;
	
	@Column (name="name")
	public String name;
	
	@Column (name="top")
	public String top;
	
	@Column (name="profile")
	public String profile;
	
	@Column(name="coin")
	public String coin;
	
	@Column (name="comment")
	public String comment;
	
	@Column (name="date")
	public int date;
	
	@Column (name="time")
	public int time;
}
