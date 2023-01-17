package com.sivalabs.techbuzz.posts.config;

import com.sivalabs.techbuzz.posts.ApplicationProperties;
import com.sivalabs.techbuzz.posts.domain.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final ApplicationProperties properties;

    @Bean
    public VoteRepository voteRepository() {
        WebClient client = WebClient.builder().baseUrl(properties.votesApiUrl()).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(VoteRepository.class);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
