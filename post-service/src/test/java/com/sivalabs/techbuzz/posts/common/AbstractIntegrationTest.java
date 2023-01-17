package com.sivalabs.techbuzz.posts.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles({ "test", "integration-test" })
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.1-alpine");
	static final MockServerContainer mockServer = new MockServerContainer(DockerImageName.parse("mockserver/mockserver:5.13.2"));

	protected static MockServerClient mockServerClient;

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUpBase() {
		RestAssured.baseURI = "http://localhost:" + port;
		mockServerClient.reset();
	}

	@DynamicPropertySource
	static void postgresProperties(DynamicPropertyRegistry registry) {
		Startables.deepStart(postgres, mockServer).join();

		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);

		registry.add("techbuzz.vote-api-url", mockServer::getEndpoint);
		mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort());
	}

	protected static void mockGetVotes() {
		mockServerClient.when(
						request().withMethod("GET").withPath("/api/votes?postIds=.*"))
				.respond(
						response()
								.withStatusCode(200)
								.withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
								.withBody(json(
										"""
                                                [
                                                    {
                                                        "postId": 1,
                                                        "upVotes": 2,
                                                        "downVotes": 2
                                                    },
                                                    {
                                                        "postId": 2,
                                                        "upVotes": 4,
                                                        "downVotes": 1
                                                    }
                                                ]
                                                """
								))
				);
	}
}
