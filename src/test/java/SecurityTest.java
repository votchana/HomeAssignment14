import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SecurityTest {
    private static Response response;

    @Test
    public void responseStatusTest(){
        response = given().contentType("application/json").get(Consts.URL_LIVE+Consts.TOKEN); // using token for authorization
        System.out.println(response.asString());
        response.then().statusCode(200);

    }

    @Test
    public void negativeResponseStatusTest(){
        response = given().contentType("application/json").get(Consts.URL_LIVE+"testest");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", containsString("Invalid authentication credenti"));

    }


}
