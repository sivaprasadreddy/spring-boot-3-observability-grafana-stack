package com.sivalabs.techbuzz.votes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "votes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "post_id", nullable = false)
	private Long postId;

	@Column(name = "up_votes", nullable = false)
	private Integer upVotes;

	@Column(name = "down_votes", nullable = false)
	private Integer downVotes;

}
