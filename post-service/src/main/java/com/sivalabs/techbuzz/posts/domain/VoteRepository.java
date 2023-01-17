package com.sivalabs.techbuzz.posts.domain;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface VoteRepository {
    @GetExchange("/api/votes")
    List<Vote> getVotes(@RequestParam String postIds);

    @PostExchange("/api/votes")
    void createVote(@RequestBody AddVoteRequest voteRequest);
}
