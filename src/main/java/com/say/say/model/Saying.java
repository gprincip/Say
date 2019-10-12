package com.say.say.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="saying")
public class Saying{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column
	private String text;
	
	@Column
	private String author;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Tag> tags;
	
	@Column
	private Integer score;
	
	public Saying(String text, String author, Set<Tag> tags, int score) {
		this.text = text;
		this.author = author;
		this.tags = tags;
		this.score = score;
	}
	
	public Saying() {}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Set<Tag> getTags() {
		return tags;
	}
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}
	
}
