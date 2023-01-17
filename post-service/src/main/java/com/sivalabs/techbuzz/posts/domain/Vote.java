package com.sivalabs.techbuzz.posts.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private Long postId;
    private Long upVotes;
    private Long downVotes;
}
