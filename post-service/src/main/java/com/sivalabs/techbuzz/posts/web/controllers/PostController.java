package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.domain.AddVoteRequest;
import com.sivalabs.techbuzz.posts.domain.PostModel;
import com.sivalabs.techbuzz.posts.domain.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
	private final PostService postService;

	@GetMapping
	public List<PostModel> getPosts() {
		log.info("Fetching posts");
		return postService.getPosts();
	}

	@PostMapping("/{postId}/votes")
	public void addVote(@PathVariable Long postId, @RequestBody AddVoteRequest request) {
		log.info("Adding vote for postId:{}, value: {}", postId, request.value());
		var voteRequest = new AddVoteRequest(postId, request.value() > 0 ? 1 : -1);
		postService.createVote(voteRequest);
	}
}
