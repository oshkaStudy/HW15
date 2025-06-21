import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ApiTests extends ApiTestBase{

    @Test
    @DisplayName("POST. Создание нового пользователя")
    @Tag("Позитивный")
    void successfulUserCreationTest() {

        given()
                .contentType(JSON)
                .header("x-api-key", xApiKey)
                .log().uri()
                .body(JSONData.userCreationPositive)

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("neo"))
                .body("job", is("the chosen one"))
                .body("id", matchesRegex("\\d+"))
                .body("createdAt", matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("POST. Создание нового пользователя с пустым body")
    @Tag("Негативный")
    void negativeUserCreationNoBodyTest() {

        given()
                .header("x-api-key", xApiKey)
                .log().uri()
                .body("")

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("$", not(hasKey("name")))
                .body("$", not(hasKey("job")))
                .body("id", matchesRegex("\\d+"))
                .body("createdAt", matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("POST. Создание нового пользователя с пустым job")
    @Tag("Негативный")
    void negativeUserCreationNoJobTest() {

        given()
                .header("x-api-key", xApiKey)
                .log().uri()
                .body(JSONData.userCreationNegativeNoJob)

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("$", not(hasKey("name")))
                .body("$", not(hasKey("job")))
                .body("id", matchesRegex("\\d+"))
                .body("createdAt", matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("PUT. Обновление данных пользователя")
    @Tag("Позитивный")
    void successfulUserFullUpdateTest() {

        given()
                .contentType(JSON)
                .header("x-api-key", xApiKey)
                .log().uri()
                .body(JSONData.userUpdatingPositive)

                .when()
                .put("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("neo"))
                .body("job", is("not the chosen one"))
                .body("updatedAt", matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("PUT. Обновление данных пользователя - только имя")
    @Tag("Позитивный")
    void successfulUserPutUpdateNameOnlyTest() {

        given()
                .contentType(JSON)
                .header("x-api-key", xApiKey)
                .log().uri()
                .body(JSONData.userUpdatingPositiveNoJob)

                .when()
                .put("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("neo"))
                .body("$", not(hasKey("job")))
                .body("updatedAt", matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("PATCH. Обновление данных пользователя")
    @Tag("Позитивный")
    void successfulUserPatchUpdateTest() {

        given()
                .contentType(JSON)
                .header("x-api-key", xApiKey)
                .log().uri()
                .body(JSONData.userUpdatingPositive)

                .when()
                .patch("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("neo"))
                .body("job", is("not the chosen one"))
                .body("updatedAt", matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("DELETE. Удаление пользователя")
    @Tag("Позитивный")
    void successfulUserDeleteTest() {

        given()
                .contentType(JSON)
                .header("x-api-key", xApiKey)
                .log().uri()
                .body(JSONData.userUpdatingPositive)

                .when()
                .delete("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(204)
                .body(equalTo(""));
    }


}