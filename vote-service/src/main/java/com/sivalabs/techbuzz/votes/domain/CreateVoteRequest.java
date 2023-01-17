package com.sivalabs.techbuzz.votes.domain;

import jakarta.validation.constraints.NotNull;

public record CreateVoteRequest(@NotNull Long postId, @NotNull Integer value) {
}
