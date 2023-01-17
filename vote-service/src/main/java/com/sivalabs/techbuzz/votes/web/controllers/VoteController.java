package com.sivalabs.techbuzz.votes.web.controllers;

import com.sivalabs.techbuzz.votes.domain.CreateVoteRequest;
import com.sivalabs.techbuzz.votes.domain.Vote;
import com.sivalabs.techbuzz.votes.domain.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
@Slf4j
public class VoteController {

	private final VoteService voteService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public Vote createVote(@Valid @RequestBody CreateVoteRequest request) {
		log.info("Saving vote for postId:{}, value: {}", request.postId(), request.value());
		return voteService.addVote(request);
	}

	@GetMapping
	public List<Vote> getVotes(@RequestParam(name = "postIds") String postIdsCsv) {
		log.info("Fetching votes for posts: {}", postIdsCsv);
		if(!StringUtils.hasText(postIdsCsv)) {
			return List.of();
		}
		List<Long> postIds = Arrays.stream(postIdsCsv.split(",")).map(Long::parseLong).toList();
		return voteService.getVotes(postIds);
	}
}
