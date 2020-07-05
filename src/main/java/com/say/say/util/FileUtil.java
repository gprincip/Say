package com.say.say.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	public static List<String> loadTextFile(File file) {

		List<String> content = new ArrayList<String>();

		try {
			log.info("Starting to read file: " + file.getAbsolutePath());
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				content.add(scanner.nextLine());
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			log.error("Error reading file!", e);
		}

		return content;
	}

}
