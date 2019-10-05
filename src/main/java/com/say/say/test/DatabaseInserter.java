package com.say.say.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.say.say.dao.repository.TagRepository;
import com.say.say.model.Tag;

/**
 * Class used for adding entries to database
 * 
 * @author gavrilo
 *
 */
@Component
public class DatabaseInserter {

	private static final Logger log = LoggerFactory.getLogger(DatabaseInserter.class);

	@Autowired
	TagRepository tagRepo;

	@Autowired
	public DatabaseInserter() {
		log.info("Database inserter constructor");
	}

	/**
	 * Adds tags from txt file to database. One line - one tag name
	 * 
	 * @param filename
	 */
	public void addTagsToDatabase(String filename) {

		try {

			File file = new ClassPathResource(filename).getFile();

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {

				String line = scanner.nextLine();
				Tag tag = new Tag(line);
				tagRepo.save(tag);
				log.info("Tag [" + line + "] saved.");
			}

			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
