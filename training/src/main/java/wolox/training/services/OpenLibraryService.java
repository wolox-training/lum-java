package wolox.training.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenLibraryService {

    public void findBook(String isbn) throws IOException {
        log.info(String.format("%s was received to search book in OpenLibrary",isbn));
        URL url = new URL(String.format("https://openlibrary.org/api/books?bibkeys=ISBN:%s&format=json&jscmd=data",isbn));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        String readLine = "";
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            //JSONObject jsnobject = new JSONObject(response);

        }
    }

}
