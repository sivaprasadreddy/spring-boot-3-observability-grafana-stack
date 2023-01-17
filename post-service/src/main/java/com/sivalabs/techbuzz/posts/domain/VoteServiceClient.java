package com.sivalabs.techbuzz.posts.domain;

import com.sivalabs.techbuzz.posts.ApplicationProperties;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteServiceClient {
    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final Tracer tracer;
    private final ObservationRegistry observationRegistry;
    private final ApplicationProperties properties;

    public List<Vote> getVotes(String postIds) {
        String url = properties.votesApiUrl() + "/api/votes?postIds="+postIds;
        ResponseEntity<Vote[]> response = restTemplate.getForEntity(url, Vote[].class);
        return Arrays.asList(response.getBody());
    }

    public void createVote(AddVoteRequest voteRequest) {
        String url = properties.votesApiUrl() + "/api/votes";
        restTemplate.postForEntity(url, voteRequest, Void.class);
    }

    public List<Vote> getVotesReactive(String postIds) {
        log.info("Fetching votes using WebClient");
        /*
        Observation observation = Observation.start("post-service-getvotes", observationRegistry);
        Mono<List<Vote>> voteFlux = Mono.just(observation).flatMap(span -> {
                    observation.scoped(() -> log.info("POST_SERVICE <TRACE:{}> Hello from consumer",
                            this.tracer.currentSpan().context().traceId()));
                    String url = properties.votesApiUrl() + "/api/votes?postIds="+postIds;
                    return webClient.get().uri(url).retrieve().bodyToMono(new ParameterizedTypeReference<List<Vote>>(){});
                }).doFinally(signalType -> observation.stop())
                .contextWrite(context -> context.put(ObservationThreadLocalAccessor.KEY, observation));
        return voteFlux.block();
        */
        String url = properties.votesApiUrl() + "/api/votes?postIds="+postIds;
        Flux<Vote> voteFlux = webClient.get().uri(url).retrieve().bodyToFlux(Vote.class);
        return voteFlux.toStream().toList();
    }
}
