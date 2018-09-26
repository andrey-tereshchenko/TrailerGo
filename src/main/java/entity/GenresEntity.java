package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "genres", schema = "public", catalog = "TrailerGo")
@Cacheable
public class GenresEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "genreid")
    private int genreId;

    @Basic
    @Column(name = "genrename")
    private String genreName;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "genrestofilm",
            joinColumns = { @JoinColumn(name = "genreid") },
            inverseJoinColumns = { @JoinColumn(name = "filmid") }
    )
    Set<FilmsEntity> films;

    public Set<FilmsEntity> getFilms() {
        return films;
    }

    public void setFilms(Set<FilmsEntity> films) {
        this.films = films;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreid) {
        this.genreId = genreid;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genrename) {
        this.genreName = genrename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenresEntity that = (GenresEntity) o;

        if (genreId != that.genreId) return false;
        if (genreName != null ? !genreName.equals(that.genreName) : that.genreName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = genreId;
        result = 31 * result + (genreName != null ? genreName.hashCode() : 0);
        return result;
    }
}
