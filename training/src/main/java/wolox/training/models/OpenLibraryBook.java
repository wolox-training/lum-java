package wolox.training.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OpenLibraryBook implements Serializable {

    @SerializedName("publishers") private List<Publishers> publishers;
    @SerializedName("title") private String title;
    @SerializedName("subtitle") private String subtitle;
    @SerializedName("number_of_pages") private int numberOfPages;
    @SerializedName("publish_date") private String publishDate;
    @SerializedName("authors") private List<Authors> authors;

    public String getPublisher() {
        return publishers.get(0).getName();
    }

    public String getAuthors() {
        return authors.get(0).getName();
    }
}

@Getter
class Publishers implements Serializable {

    @SerializedName("name") private String name;
}

@Getter
class Authors implements Serializable {

    @SerializedName("name") private String name;
}
