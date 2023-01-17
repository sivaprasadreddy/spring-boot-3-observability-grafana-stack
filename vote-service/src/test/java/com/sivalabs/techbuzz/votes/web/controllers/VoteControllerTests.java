package com.sivalabs.techbuzz.votes.web.controllers;

import com.sivalabs.techbuzz.votes.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.votes.domain.Vote;
import com.sivalabs.techbuzz.votes.domain.VoteRepository;
import io.restassured.http.ContentType;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class VoteControllerTests extends AbstractIntegrationTest {

    @Autowired
    private VoteRepository voteRepository;

    List<Vote> votes = List.of(
            new Vote(null, 1L, 2,1),
            new Vote(null, 2L, 5,2)
    );

    @BeforeEach
    void setUp() {
        voteRepository.deleteAllInBatch();
        votes = voteRepository.saveAll(votes);
    }

    @Test
    void shouldGetVotes() {
        List<Vote> votesResponse = given()
                .when()
                .get("/api/votes?postIds=1,2")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", Vote.class);
        assertThat(votesResponse).hasSize(2);

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration
                .builder()
                .withIgnoredFields("id")
                .build();
        assertThat(votesResponse).usingRecursiveFieldByFieldElementComparator(configuration)
                //.contains(votes.get(0), votes.get(1))
                .containsAll(votes);
    }

    @Test
    void shouldCreateVoteSuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "postId": 2,
                            "value": 1
                        }
                """)
                .when()
                .post("/api/votes")
                .then()
                .assertThat()
                .statusCode(200);
    }
}