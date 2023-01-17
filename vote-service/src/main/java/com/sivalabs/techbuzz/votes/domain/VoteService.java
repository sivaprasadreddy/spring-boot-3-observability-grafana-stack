package com.sivalabs.techbuzz.votes.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoteService {

	private final VoteRepository voteRepository;

	public void addVote(CreateVoteRequest request) {
		Optional<Vote> voteOptional = voteRepository.findByPostId(request.postId());
		if (voteOptional.isEmpty()) {
			int upvote = 0, downvote = 0;
			if(request.value() > 0) {
				upvote++;
			} else {
				downvote++;
			}
			Vote vote = new Vote(null, request.postId(), upvote, downvote);
			voteRepository.save(vote);
			log.info("Vote saved successfully");
			return;
		}
		Vote existingVote = voteOptional.get();
		if(request.value() > 0) {
			existingVote.setUpVotes(existingVote.getUpVotes() + 1);
		} else {
			existingVote.setDownVotes(existingVote.getDownVotes() + 1);
		}
		voteRepository.save(existingVote);
		log.info("Vote update successfully");
	}

	public List<Vote> getVotes(List<Long> postIds) {
		return voteRepository.findByPostIdIn(postIds);
	}
}
