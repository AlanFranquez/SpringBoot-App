package com.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Random;

@Entity
public class Review {

	@Id
	private long id;

	private String text;
	private int likes;



	public Review(String text) {
		Random r = new Random();
		this.id = r.nextInt(500);
		this.likes = 0;
		this.text = text;
	}

	public Review() {
		
	}

	public long getId() {
		return this.id;
	}

	public int getLikes() {
		return this.likes;
	}

	public String getText() {
		return this.text;
	}



	
}
