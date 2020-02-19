package wolox.training.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.json.JSONObject;

public class ExternalRequestUtils {

    public static final JSONObject responseToJson(HttpURLConnection con) throws IOException {
        String readLine = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();
        while ((readLine = in.readLine()) != null) {
            response.append(readLine);
        }
        in.close();
        return new JSONObject(response.toString());
    }
}
