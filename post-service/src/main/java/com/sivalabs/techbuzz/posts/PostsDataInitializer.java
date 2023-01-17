package com.sivalabs.techbuzz.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.techbuzz.posts.domain.Post;
import com.sivalabs.techbuzz.posts.domain.PostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostsDataInitializer implements CommandLineRunner {
	private final PostRepository postRepository;
	private final ApplicationProperties properties;

	@Override
	public void run(String... args) throws Exception {
		List<String> filePaths = properties.importFilePaths();
		if(filePaths == null || filePaths.isEmpty()) {
			return;
		}
		this.importPostsAsync(filePaths);
	}

	public void importPostsAsync(List<String> fileNames) throws IOException {
		if(postRepository.count() > 0) {
			log.info("There are existing posts, importing default data is skipped");
			return;
		}

		for (String fileName : fileNames) {
			log.info("Importing posts from file: {}", fileName);
			ClassPathResource file = new ClassPathResource(fileName, this.getClass());
			long count = this.importPosts(file.getInputStream());
			log.info("Imported {} posts from file {}", count, fileName);
		}
	}

	public long importPosts(InputStream is) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		PostsData postsData = objectMapper.readValue(is, PostsData.class);
		long count = 0L;
		for (PostEntry postEntry : postsData.posts) {
			Post post = convertToPost(postEntry);
			postRepository.save(post);
			count++;
		}
		return count;
	}

	private Post convertToPost(PostEntry postEntry) {
		return new Post(null, postEntry.title, postEntry.url, Instant.now());
	}

	@Setter
	@Getter
	static class PostsData {
		private List<PostEntry> posts;
	}

	@Setter
	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PostEntry {
		private String title;
		private String url;
	}
}
