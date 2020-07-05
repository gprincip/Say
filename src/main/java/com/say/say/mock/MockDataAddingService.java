package com.say.say.mock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.say.say.dao.repository.SayingRepository;
import com.say.say.dao.repository.TagRepository;
import com.say.say.dao.repository.UserRepository;
import com.say.say.model.Role;
import com.say.say.model.Saying;
import com.say.say.model.Tag;
import com.say.say.model.User;
import com.say.say.model.UserRolesEnum;
import com.say.say.service.UserRoleService;
import com.say.say.util.FileUtil;

/**
 * Class used for adding mock data into system to be used for testing
 * @author gavrilo
 *
 */
@Service
public class MockDataAddingService {

	public static final Logger log = LoggerFactory.getLogger(MockDataAddingService.class);
	public static final String MOCK_USERS_FILE = "static/files/mock_data/users.txt";
	public static final String MOCK_SAYINGS_FILE = "static/files/mock_data/lorem_ipsum_paragraphs.txt";
	public static final String MOCK_TAGS_FILE = "static/files/mock_data/corncob_lowercase.txt";
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	SayingRepository sayingRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRoleService userRoleService;
	
	Random rand = new Random();
	
	List<Tag> tags;
	List<User> users;
	
	/**
	 * Inserts tags, users and sayings to database. It's important to respect the order of this operations.
	 */
	public void insertDataToDatabase(){
		
		addTags();
		addUsersAndRoles();
		addSayings();
		log.info("Mock data insertion finished!");
		
	}
	
	public void addUsersAndRoles() {
		File file = null;
		try {
			file = new ClassPathResource(MOCK_USERS_FILE).getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> lines = FileUtil.loadTextFile(file);
		List<User> users = new ArrayList<User>();
		
		log.info("Inserting users to database!");
		
		for(String line : lines) {
			
			String[] splitted = line.split(" ");
			if(splitted.length < 2) {
				log.error("Line: " + line + " is invalid and cannot be used to form user data!");
				continue;
			}
			
			String firstName = splitted[0].trim();
			String lastName = splitted[1].trim();
			String username = firstName + lastName;
			String email = firstName + "@" + lastName + ".mock";
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUsername(username);
			user.setEmail(email);
			user.setReputation(getReputation());
			user.setPassword(passwordEncoder.encode("user"));
			user.setActive(true);
			
			users.add(user);
			
		}
		
		AtomicInteger ai = new AtomicInteger(0);
		Collection<List<User>> partitioned = users.stream().
				collect(Collectors.groupingBy(user -> ai.getAndIncrement() / 100)).values();
		
		int counter = 1;
		for(List<User> partition : partitioned) {
			saveUserPartition(partition);
			log.info("Saved " + counter++ + "/" + partitioned.size() + " user partitions!");
		}
		this.users = userRepo.findAll();
	}

	private void saveUserPartition(List<User> partition) {
		
		for(User user : partition) {
			
			//set admin role for all mock users
			//first save user and then role
			userRepo.save(user);
			Role adminRole = userRoleService.getUserRole(UserRolesEnum.ROLE_ADMIN);
			userRoleService.setRoleToUser(user, adminRole);		
		}
	}
	
	private int getReputation() {
		
		int rep = Math.abs(rand.nextInt() % 4);
		return rep;
		
	}

	
	public void addTags() {

		List<Tag> tags = new ArrayList<Tag>();

		try {

			File file = new ClassPathResource(MOCK_TAGS_FILE).getFile();

			Scanner scanner = new Scanner(file);

			
			while (scanner.hasNextLine()) {

				String line = scanner.nextLine();
				Tag tag = new Tag(line);
				tags.add(tag);
			}

			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		AtomicInteger ai = new AtomicInteger(0);
		Collection<List<Tag>> partitionedTags = tags.stream().
				collect(Collectors.groupingBy(tag -> ai.getAndIncrement() / 1000)).values();

		int counter = 1;
		for(List<Tag> partition : partitionedTags) {
			tagRepo.saveAll(partition);
			log.info("Saved " + counter++ + "/" + partitionedTags.size() + " tag partitions!");
		}
		
		tags = tagRepo.findAll();
	}
	
	public void addSayings() {
		
		if(tags == null) {
			tags = tagRepo.findAll();
		}
		
		if(users == null) {
			users = userRepo.findAll();
		}
		
		File file = null;
		try {
			file = new ClassPathResource(MOCK_SAYINGS_FILE).getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> lines = FileUtil.loadTextFile(file);
		List<Saying> sayings = new ArrayList<Saying>();
		
		for(String line : lines) {
			line = line.replace("<p>", "").replace("</p>", ""); //remove <p> tags
			if(line == null || line.trim().length() == 0) {
				continue;
			}
			Saying saying = new Saying();
			saying.setText(line);
			saying.setDate(new Date());
			addTagsToSaying(saying);
			setUserToSaying(saying);
			sayings.add(saying);
			saying.setClientIp("0:0:0:0:0:0:0:1");
			sayings.add(saying);
		}
		
		AtomicInteger ai = new AtomicInteger(0);
		Collection<List<Saying>> partitionedSayings = sayings.stream().
				collect(Collectors.groupingBy(tag -> ai.getAndIncrement() / 1000)).values();
		
		int counter = 1;
		for(List<Saying> partition : partitionedSayings) {
			sayingRepo.saveAll(partition);
			log.info("Saved " + counter++ + "/" + partitionedSayings.size() + " saying partitions!");
		}
		
	}

	private void setUserToSaying(Saying saying) {
		
		int randInt = Math.abs(rand.nextInt() % users.size());
		saying.setUser(users.get(randInt));
		
	}

	private void addTagsToSaying(Saying saying) {
	
		int randTen  = Math.abs(rand.nextInt() % 10);
		for(int i=0; i<randTen; i++) {
			int randInt = Math.abs(rand.nextInt() % tags.size());
			saying.addTag(tags.get(randInt));
		}
	}
	
}
