import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HistoricalConversionTest {

    private static Response response;

    @ParameterizedTest
    @ValueSource(strings = {"2018-01-01","test",""})
    public void dateCheckTest(String expectedDate) {

        String responseAsString;
        response = given().contentType("application/json").get(Consts.URL_HISTORICAL + expectedDate + "&apikey=" + Consts.TOKEN + Consts.FOCUS_CURRENCIES);
        responseAsString = response.asString();

        System.out.println(responseAsString);

        if(responseAsString.contains("You have not specified a date")) {
            response.then().body("error.code", equalTo(301));
        }
        else if (responseAsString.contains("You have entered an invalid date")) {
            response.then().body("error.code", equalTo(302));
        }
        else {

            response.then().body("date", containsString(expectedDate));


            JsonPath jsonPathEvaluator = response.jsonPath();


            int timeStamp;
            timeStamp = jsonPathEvaluator.get("timestamp");
            System.out.println("Please go to https://www.epochconverter.com/ to convert this timestamp to a human-readable date: " + timeStamp);
        }
    }
}

