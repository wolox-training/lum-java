package wolox.training.services;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import wolox.training.exceptions.book.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.OpenLibraryBook;
import wolox.training.utils.ExternalRequestUtils;

@Slf4j
@Service
public class OpenLibraryService {

    public Book findBook(String isbn) throws IOException {
        log.info(String.format("%s was received to search book in OpenLibrary", isbn));

        URL url = new URL(String
            .format("https://openlibrary.org/api/books?bibkeys=ISBN:%s&format=json&jscmd=data",
                isbn));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            log.info("OpenLibrary Status Code 200");
            JSONObject response = ExternalRequestUtils.responseToJson(con);
            log.info("Open Library response" + response.toString());
            JSONObject isbnJsonObject = response.getJSONObject(String.format("ISBN:%s",isbn));
            OpenLibraryBook openLibraryBook = (new Gson()).fromJson(isbnJsonObject.toString(), OpenLibraryBook.class);
            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(openLibraryBook.getTitle());
            book.setSubtitle(openLibraryBook.getSubtitle());
            book.setPublisher(openLibraryBook.getPublisher());
            book.setYear(openLibraryBook.getPublishDate());
            book.setPages(openLibraryBook.getNumberOfPages());
            book.setAuthor(openLibraryBook.getAuthors());
            book.setImage("image");
            return book;
        } else {
            log.info("OpenLibrary Error while retrieving book");
            throw new BookNotFoundException();
        }
    }
}

