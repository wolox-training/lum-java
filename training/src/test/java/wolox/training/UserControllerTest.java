package wolox.training;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.controllers.UserController;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;
import wolox.training.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = {UserRepository.class, UserController.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;
    private Book book;
    private Users user;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setGenre("Fantasy");
        book.setAuthor("J. K. Rowling");
        book.setImage("image.png");
        book.setTitle("Harry Potter and the Philosopher's Stone");
        book.setSubtitle("-");
        book.setPublisher("Bloomsbury Publishing");
        book.setYear("1997");
        book.setPages(223);
        book.setIsbn("97808747532743");
        user = new Users();
        user.setName("Lucas");
        user.setUsername("lmiotti");
        user.setBirthdate(LocalDate.parse("1900-01-01"));
        user.addBook(book);
    }
    
    @Test
    public void whenUserIdExists_thenUserIsReturned() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        mvc.perform(get("api/users/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(
                "\"id\":1,\"username\":\"lmiotti\",\"name\":\"Lucas\",\"birthdate\":\"1900-01-01\", + "
                    + "\"books\":[{\"id\":1,\"genre\":\"\"Fantasy\",\"author\":\"\"J. K. Rowling\", + "
                    + "\"image\":\"image.png\", \"title\":\"\"Harry Potter and the Philosopher's Stone\", + "
                    + "\"subtitle\":\"-\",\"publisher\":\"Bloomsbury Publishing\",\"year\":\"1997\",\"pages\":223, + "
                    + "\"isbn\":\"97808747532743\"}]}"
            ));
    }

    @Test
    public void whenUserIdNoExists_thenUserNotFoundExceptionIsThrown() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(null);
        mvc.perform(get("api/users/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
            //.andExpect(UserNotFoundException(ResponseStatusException::new))
    }
}
