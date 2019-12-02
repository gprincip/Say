package com.say.say.model;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@Column
	private String clientIp;
	
	/**Date when saying was submitted*/
	@Column
	private Date date;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonProperty("tags")
	private Set<Tag> tags;
	
	@Column
	private Integer score;
	
	public Saying(String text, String author, Set<Tag> tags, int score, String clientIp, Date date) {
		this.text = text;
		this.author = author;
		this.tags = tags;
		this.score = score;
		this.clientIp = clientIp;
		this.date = date;
	}
	
	public Saying() {}
	
	public Long getId() {
		return id;
	}

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
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public void addTag(Tag tag) {
		tags.add(tag);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Saying [id=" + id + ", text=" + text + ", author=" + author + ", clientIp=" + clientIp + ", date="
				+ date + ", tags=" + tags + ", score=" + score + "]";
	}

	public Set<String> getTagNames() {
		
		return (tags.stream().map(elem -> elem.getName()).collect(Collectors.toSet()));		
		
	}
	
}
