package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
@ApiModel(description = "Books")
public class Book {

    @ApiModelProperty(notes = "Book's genre, could be horror, science fiction, etc", required = false)
    @Column(nullable = true)
    private String genre;

    @ApiModelProperty(notes = "Book's author", required = true)
    @Column(nullable = false)
    private String author;

    @ApiModelProperty(notes = "Book's cover image url", required = true)
    @Column(nullable = false)
    private String image;

    @ApiModelProperty(notes = "Book's title", required = true)
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(notes = "Book's subtitle", required = true)
    @Column(nullable = false)
    private String subtitle;

    @ApiModelProperty(notes = "Book's publisher", required = true)
    @Column(nullable = false)
    private String publisher;

    @ApiModelProperty(notes = "Book's release year", required = true)
    @Column(nullable = false)
    private String year;

    @ApiModelProperty(notes = "Book's amount of pages", required = true)
    @Column(nullable = false)
    private int pages;

    @ApiModelProperty(notes = "Book's id", required = true)
    @Column(nullable = false)
    private String isbn;

    @Id
    @ApiModelProperty(notes = "Book's id", required = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public void setAuthor(String author) {
        this.author = Preconditions.checkNotNull(author, "Author field mustn't be null");
    }

    public void setImage(String image) {
        this.image = Preconditions.checkNotNull(image, "Image field mustn't be null");
    }

    public void setTitle(String title) {
        this.title = Preconditions.checkNotNull(title, "Title field mustn't be null");
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = Preconditions.checkNotNull(subtitle, "Subtitle field mustn't be null");
    }

    public void setPublisher(String publisher) {
        this.publisher = Preconditions.checkNotNull(publisher, "Publisher field mustn't be null");
    }

    public void setYear(String year) {
        this.year = Preconditions.checkNotNull(year, "Year field mustn't be null");
    }

    public void setPages(int pages) {
        Preconditions.checkArgument(pages > 0, "Pages field must be positive");
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        this.isbn = Preconditions.checkNotNull(isbn, "Isbn field mustn't be null");
    }

}
