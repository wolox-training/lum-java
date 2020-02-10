package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "Book's cover image url", required = true )
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

    @ApiModelProperty(notes = "Book's isbn", required = true)
    @Column(nullable = false)
    private String isbn;

    @ApiModelProperty(notes = "Book's id", required = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @Override
    public boolean equals(Object o) {
        Book book = (Book) o;
        return this.title.equals(book.getTitle()) &&
            this.author.equals(book.getAuthor()) &&
            this.isbn.equals(book.getIsbn());
    }
}
