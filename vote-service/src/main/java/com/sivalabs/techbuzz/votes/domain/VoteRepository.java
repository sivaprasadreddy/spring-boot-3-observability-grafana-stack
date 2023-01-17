package com.sivalabs.techbuzz.votes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	List<Vote> findByPostIdIn(List<Long> postIds);

	Optional<Vote> findByPostId(Long postId);
}
