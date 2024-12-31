package com.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Review {

	@Id
	private long id;

	private String text;
	private int likes;

	@ManyToOne
	@JsonIgnore
	private Usuario author;

	private LocalDateTime date;



	public Review(String text, Usuario author) {
		Random r = new Random();
		this.id = r.nextInt(500);
		this.likes = 0;
		this.text = text;
		this.author = author;
		this.date = LocalDateTime.now();
	}

	public Review() {
		
	}

	public long getId() {
		return this.id;
	}

	public String getFormattedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		return this.date.format(formatter);
	}

	public Usuario getAuthor() {
		return this.author;
	}

	public int getLikes() {
		return this.likes;
	}

	public String getText() {
		return this.text;
	}



	
}
