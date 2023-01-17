package com.sivalabs.techbuzz.posts.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {

	private Long id;

	private String title;

	private String url;

	private Long upVotes;

	private Long downVotes;
}
