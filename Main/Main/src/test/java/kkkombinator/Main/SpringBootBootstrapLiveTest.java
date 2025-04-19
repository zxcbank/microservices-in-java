package kkkombinator.Main;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import kkkombinator.DAO.Entities.Cat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootBootstrapLiveTest {

    @LocalServerPort
    private int port;
    private String API_ROOT;

    @BeforeEach
    public void setUp() {
        API_ROOT = "http://localhost:" + port + "/api/cats";
        RestAssured.port = port;
    }

    private Cat createRandomCat() {
        final Cat cat = new Cat();
        cat.setName(randomAlphabetic(10));
        return cat;
    }

    private String createCatAsUrl(Cat cat) {
        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cat)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void whenGetAllCats_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetCreatedCatById_thenOK() {
        Cat cat = createRandomCat();
        String location = createCatAsUrl(cat);

        Response response = RestAssured.get(location);

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assertions.assertEquals(cat.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenGetNotExistCatById_thenNotFound() {

        Response response = RestAssured.get(API_ROOT + "/" + 9000);
        System.out.println(response.jsonPath().toString());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewCat_thenCreated() {
        Cat cat = createRandomCat();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cat)
                .post(API_ROOT);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidCat_thenError() {
        Cat cat = createRandomCat();
        cat.setOwner(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cat)
                .post(API_ROOT);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }


    @Test
    public void whenDeleteCreatedCat_thenOk() {
        Cat cat = createRandomCat();
        String location = createCatAsUrl(cat);
        System.out.println(location);
        Response response = RestAssured.delete(location);

//        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
