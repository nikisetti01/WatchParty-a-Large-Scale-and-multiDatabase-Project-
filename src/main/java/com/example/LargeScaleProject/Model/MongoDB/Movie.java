package com.example.LargeScaleProject.Model.MongoDB;
import java.util.*;

import com.mongodb.lang.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.*;


@Setter
@Getter
@Data
@Document(collection = "movie") // we just say by Spring this is a Document
public class Movie {

    @Id
    private String _id;
    @Setter
    @Getter
    private String[] genres;
    @Setter
    @Getter
    private String movie_id;
    @Setter
    @Getter
    private String movie_title;
    @Setter
    @Getter
    private String original_language;
    @Setter
    @Getter
    private String overview;
    @Setter
    @Getter
    private String[] production_countries;
    @Setter
    @Getter
    private String release_date;
    @Setter
    @Getter
    private Integer runtime;
    @Setter
    @Getter
    private String type;
    private List <String> director;
    private List <String> cast;

    // Costruttore
    public Movie(String[] genres, String movie_id, String movie_title, String original_language,
                String overview, String[] production_countries, String release_date, Integer runtime, String type,
                List<String> cast, List<String>director) {
        this._id = UUID.randomUUID().toString();
        this.genres = genres;
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.original_language = original_language;
        this.overview = overview;
        this.production_countries = production_countries;
        this.release_date = release_date;
        this.runtime = runtime;
        this.type = type;

    }
    public Movie(){

    }
    public Movie(String movie_id) {
        this.movie_id = movie_id;

    }
    public  Movie(String movie_id, String movie_title){
        this.movie_id = movie_id;
        this.movie_title=movie_title;
    }
   public  Movie(String movie_id, String movie_title, Integer runtime){
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.runtime=runtime;

    }

    public Movie(String[] genres, String movie_id, String movie_title, String original_language,
                String overview, String[] production_countries, String release_date, Integer runtime, String type
                ) {
        this._id = UUID.randomUUID().toString();
        this.genres = genres;
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.original_language = original_language;
        this.overview = overview;
        this.production_countries = production_countries;
        this.release_date = release_date;
        this.runtime = runtime;
        this.type = type;
          this.director=(director !=null)? director: new ArrayList<>();
         this.cast=(cast != null)? cast: new ArrayList<>();
    }

    // Override del metodo toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("movie{");
        sb.append("_id=").append(_id).append('\n');
        sb.append(", movie_id='").append(movie_id).append('\'').append('\n');
        sb.append(", movie_title='").append(movie_title).append('\'').append('\n');
        sb.append(", original_language='").append(original_language).append('\'').append('\n');
        sb.append(", overview='").append(overview).append('\'').append('\n');
        sb.append(", release_date=").append(release_date).append('\n');
        sb.append(", runtime=").append(runtime).append('\n');
        sb.append(", type='").append(type).append('\'').append('\n');

        // Verifica se i campi sono nulli prima di stamparli
        if (genres != null) {
            sb.append(", genres=").append(Arrays.toString(genres)).append('\n');
        }

        if (production_countries != null) {
            sb.append(", production_countries=").append(Arrays.toString(production_countries)).append('\n');
        }

        if (cast != null) {
            sb.append(", cast=").append(cast.toString()).append('\n');
        }

        if (director != null) {
            sb.append(", director=").append(director.toString()).append('\n');
        }

        sb.append('}');
        return sb.toString();
    }


    //prova commento
}
