package com.say.say.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.say.say.comparator.TagComparator;

@Entity
@Table(name="saying")
public class Saying{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column
	@Type(type = "text")
	private String text;
	
	@Column
	private String author;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private User user;
	
	@Column
	private String clientIp;
	
	/**Date when saying was submitted*/
	@Column
	private Date date;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Tag> tags;
	
	@Column
	private Integer score;
	
	/**
	 * Added to have consistent order of tags for serialization
	 * For example adding json to redis set so we don't have duplicates
	 */
	@JsonProperty("tags")
	@Transient
	private List<Tag> orderedTags;
	
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
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public void addTag(Tag tag) {
		if(tags == null) {
			tags = new HashSet<Tag>();
		}
		tags.add(tag);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Saying [id=" + id + ", text=" + text + ", author=" + author + ", clientIp=" + clientIp + ", date="
				+ date + ", tags=" + tags + ", score=" + score + "]";
	}

	@JsonIgnore
	public Set<String> getTagNames() {
		
		return (tags.stream().map(elem -> elem.getName()).collect(Collectors.toSet()));		
		
	}

	public Map<String, String> toMap() {
		
		Map<String, String> map = new HashMap<>();
		
		map.put("text", text);
		map.put("author", author);
		map.put("tags", tags.toString()); //should be handled differently, this is only for demo
		map.put("score", score.toString());
		map.put("clientIp", clientIp);
		map.put("date", date.toString());
		
		return map;
	}
	
	public void addTagsToList() {
		orderedTags = new ArrayList<Tag>(tags);
		orderedTags.sort(new TagComparator());
		
	}
	
}
