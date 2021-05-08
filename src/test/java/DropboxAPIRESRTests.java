import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.config.EncoderConfig.encoderConfig;

public class DropboxAPIRESRTests {

    @Test
    public void Test1() throws IOException {
        byte[] file = Files.readAllBytes(Paths.get("src/fileToUpload/photo_2020-03-29_12-19-33.jpg"));
        Response response = RestAssured.given().
                config(RestAssured.config().
                        encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                header("Authorization", "Bearer ExvWJJGZqD0AAAAAAAAAATb-W1hz4Nsbm96i32CXt5wF_AEJNsSAF88aRkZbOPYV").
                header("Dropbox-API-Arg", "{\"mode\": \"add\",\"autorename\": true,\"mute\": false,\"path\": \"/my_new_pic.jpg\",\"strict_conflict\": false}").
                header("Content-Type", "application/octet-stream").
                body(file).
                when().
                post("https://content.dropboxapi.com/2/files/upload");
        System.out.println("loaded");
        Assert.assertEquals(200, response.getStatusCode());

    }

    @Test
    public void Test2() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("path", "/my_new_pic.jpg");
        requestBody.put("include_media_info", false);
        requestBody.put("include_deleted", false);
        requestBody.put("include_has_explicit_shared_members", false);
        Response response = RestAssured.given().
                config(RestAssured.config().
                        encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                header("Authorization", "Bearer ExvWJJGZqD0AAAAAAAAAATb-W1hz4Nsbm96i32CXt5wF_AEJNsSAF88aRkZbOPYV").
                header("Content-Type", "application/json").
                body(requestBody.toJSONString()).
                when().
                post("https://api.dropboxapi.com/2/files/get_metadata");
        System.out.println("metadata");
        Assert.assertEquals(200, response.getStatusCode());


    }

    @Test
    public void Test3() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("path", "/my_new_pic.jpg");
        Response response = RestAssured.given().
                config(RestAssured.config().
                        encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                header("Authorization", "Bearer ExvWJJGZqD0AAAAAAAAAATb-W1hz4Nsbm96i32CXt5wF_AEJNsSAF88aRkZbOPYV").
                header("Content-Type", "application/json").
                body(requestBody.toJSONString()).
                when().
                post("https://api.dropboxapi.com/2/files/delete_v2");
        System.out.println("deleted");
        Assert.assertEquals(200, response.getStatusCode());

    }

}
