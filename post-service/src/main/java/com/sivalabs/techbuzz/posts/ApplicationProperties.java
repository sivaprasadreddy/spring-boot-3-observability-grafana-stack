package com.sivalabs.techbuzz.posts;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "techbuzz")
public record ApplicationProperties(List<String> importFilePaths, String votesApiUrl) {
}
