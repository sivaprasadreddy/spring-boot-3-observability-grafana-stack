package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.posts.domain.PostModel;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class PostControllerTests extends AbstractIntegrationTest {

    @Test
    void shouldGetPosts() {
        mockGetVotes();

        List<PostModel> postsResponse = given()
                .when()
                .get("/api/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", PostModel.class);
        assertThat(postsResponse).isNotEmpty();
    }

    @Test
    void shouldAddVote() {
        mockAddVote(1L, 4L, 1L);
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "value": 1
                        }
                        """
                )
                .when()
                .post("/api/posts/1/votes")
                .then()
                .assertThat()
                .statusCode(200)
                .body("postId", Matchers.equalTo(1))
                .body("upVotes", Matchers.equalTo(4))
                .body("downVotes", Matchers.equalTo(1))
        ;
    }
}