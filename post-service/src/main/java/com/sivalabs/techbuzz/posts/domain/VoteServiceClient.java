package com.sivalabs.techbuzz.posts.domain;

import com.sivalabs.techbuzz.posts.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteServiceClient {
    private final RestTemplate restTemplate;
    private final ApplicationProperties properties;

    public List<Vote> getVotes(String postIds) {
        String url = properties.votesApiUrl() + "/api/votes?postIds="+postIds;
        ResponseEntity<Vote[]> response = restTemplate.getForEntity(url, Vote[].class);
        return Arrays.asList(response.getBody());
    }

    public Vote createVote(AddVoteRequest voteRequest) {
        String url = properties.votesApiUrl() + "/api/votes";
        return restTemplate.postForEntity(url, voteRequest, Vote.class).getBody();
    }

}
