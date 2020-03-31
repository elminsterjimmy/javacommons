package com.elminster.spring.web.helper.test;

import com.elminster.spring.web.helper.HttpCallTemplate;
import com.elminster.spring.web.helper.UnexpectedHttpStatusException;
import com.elminster.web.commons.response.JsonResponse;
import com.elminster.web.commons.response.JsonResponseBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class HTTPCallTemplateTest {

  private static final String URL = "/server/products/1";
  private MockRestServiceServer mockRestServiceServer;
  private RestTemplate restTemplate;
  private ObjectMapper objectMapper;

  @Before
  public void setUp() {
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
    mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  public void response4xxBadRequest() {
    mockRestServiceServer
        .expect(requestTo(URL))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withBadRequest());

    try {
      HttpCallTemplate.httpGet(restTemplate, URL, null, String.class);
    } catch (UnexpectedHttpStatusException e) {
      assertThat(e.getResponse().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
  }

  @Test
  public void response2xxCreated() {
    mockRestServiceServer
        .expect(requestTo(URL))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.CREATED).body(""));

    ResponseEntity<String> response = HttpCallTemplate.httpGet(restTemplate, URL, null, String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isEqualTo(null);
  }

  @Test
  public void response3xxMoved() {
    mockRestServiceServer
        .expect(requestTo(URL))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.MOVED_PERMANENTLY).body(""));

    ResponseEntity<String> response = HttpCallTemplate.httpGet(restTemplate, URL, null, String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.MOVED_PERMANENTLY);
    assertThat(response.getBody()).isEqualTo(null);
  }

  @Test
  public void responseNotFoundAndGetResponse() throws JsonProcessingException {
    mockRestServiceServer
        .expect(requestTo(URL))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.NOT_FOUND).body(
            objectMapper.writeValueAsString(
                JsonResponseBuilder.INSTANCE.buildErrorJsonResponse("resource not found."))));

    try {
      HttpCallTemplate.httpGet(restTemplate, URL, null, JsonResponse.class);
    } catch (UnexpectedHttpStatusException e) {
      assertThat(e.getResponse().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      Class<?> clazz = e.getResponseType();
      String body = (String) e.getResponse().getBody();
      JsonResponse jsonResponse = (JsonResponse) objectMapper.readValue(body, clazz);
      assertThat(jsonResponse.getErrors().get(0).getErrorMessage()).isEqualTo("resource not found.");
    }
  }
}
