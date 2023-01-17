package com.sivalabs.techbuzz.posts.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

	private final PostRepository postRepository;
	private final VoteRepository voteRepository;
	private final VoteServiceClient voteServiceClient;

	public List<PostModel> getPosts() {
		List<Post> posts = postRepository.findAll();
		List<Vote> votes = getVotes(posts.stream().map(Post::getId).toList());
		Map<Long, Vote> voteMap = votes.stream().collect(Collectors.toMap(Vote::getPostId, v -> v));
		return posts.stream().map(post -> {
			Vote vote = voteMap.get(post.getId());
			return new PostModel(post.getId(), post.getTitle(), post.getUrl(),
					vote != null? vote.getUpVotes(): 0,
					vote != null? vote.getDownVotes(): 0);
				}).toList();
	}

	public void createVote(AddVoteRequest voteRequest) {
		voteServiceClient.createVote(voteRequest);
	}

	private List<Vote> getVotes(List<Long> postIds) {
		if(postIds == null || postIds.isEmpty()) {
			return List.of();
		}
		try {
			String postIdsCsv = postIds.stream().map(String::valueOf).collect(Collectors.joining(","));
			log.info("Fetching votes for postIds: {}", postIdsCsv);
			return voteServiceClient.getVotes(postIdsCsv);
		} catch (Exception e) {
			log.error("Failed to get votes", e);
			return List.of();
		}
	}

}
