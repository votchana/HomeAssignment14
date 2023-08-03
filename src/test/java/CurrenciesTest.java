import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

    public class CurrenciesTest {
        private static Response response;

        @ParameterizedTest
        @ValueSource(strings = {Consts.FOCUS_CURRENCIES,"&source=LLL&currencies=CAD%2CEUR%2CNIS%2CRUB","&source=USD&currencies=LLL%2CLLL%2CLLL%2CLLL"})
        public void converterCurrenciesTest(String currencies) {

            String responseAsString;

            response = given().contentType("application/json").get(Consts.URL_LIVE + Consts.TOKEN+currencies);
            responseAsString = response.asString();
            System.out.println(responseAsString);

//            JsonPath jsonPathEvaluator = response.jsonPath();
//
//            float cad;
//            cad = jsonPathEvaluator.get("quotes.USDCAD");
//            System.out.println(cad);


            if(responseAsString.contains("You have supplied an invalid Source Currency")) {
                response.then().body("error.code", equalTo(201));
            }
            else if (responseAsString.contains("You have provided one or more invalid Currency Codes")) {
                response.then().body("error.code", equalTo(202));
            }
            else {
                    response.then().body("quotes.USDCAD", notNullValue());
                    response.then().body("quotes.USDEUR", notNullValue());
                    response.then().body("quotes.USDRUB", notNullValue());

            }

            }

        }

//Consts.FOCUS_CURRENCIES,"&source=LLL&currencies=CAD%2CEUR%2CNIS%2CRUB","&source=USD&currencies=LLL%2CLLL%2CLLL%2CLLL"