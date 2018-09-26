package entity;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "films", schema = "public", catalog = "TrailerGo")
@Cacheable
public class FilmsEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "filmid")
    private int filmId;

    @Basic
    @Column(name = "filmname")
    private String filmName;

    @Basic
    @Column(name = "premiere")
    private Date premiere;

    @Basic
    @Column(name = "referencesvideo")
    private String referencesVideo;

    @Basic
    @Column(name = "fee")
    private int fee;

    @Basic
    @Column(name = "yearreliaze")
    private int yearReliaze;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "middle_rating")
    private float rating;

    @Basic
    @Column(name = "count_rating")
    private int counter;

    @ManyToMany(mappedBy = "films", fetch = FetchType.EAGER)
    private Set<ActorsEntity> actors;

    @ManyToMany(mappedBy = "films", fetch = FetchType.EAGER )
    private Set<CountryEntity> country;

    @ManyToMany(mappedBy = "films", fetch = FetchType.EAGER )
    private Set<DirectorsEntity> directors;

    @ManyToMany(mappedBy = "films", fetch = FetchType.EAGER)
    private Set<GenresEntity> genres;

    @ManyToMany(mappedBy = "films", fetch = FetchType.EAGER)
    private Set<UsersEntity> users;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id")
    private Set<CommentEntity> comments;


    public Set<UsersEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersEntity> users) {
        this.users = users;
    }

    public Set<ActorsEntity> getActors() {
        return actors;
    }

    public void setActors(Set<ActorsEntity> actors) {
        this.actors = actors;
    }

    public Set<CountryEntity> getCountry() {
        return country;
    }

    public void setCountry(Set<CountryEntity> country) {
        this.country = country;
    }

    public Set<DirectorsEntity> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<DirectorsEntity> directors) {
        this.directors = directors;
    }

    public Set<GenresEntity> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenresEntity> genres) {
        this.genres = genres;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmid) {
        this.filmId = filmid;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmname) {
        this.filmName = filmname;
    }

    public Date getPremiere() {
        return premiere;
    }

    public void setPremiere(Date premiere) {
        this.premiere = premiere;
    }

    public String getReferencesVideo() {
        return referencesVideo;
    }

    public void setReferencesVideo(String referencesvideo) {
        this.referencesVideo = referencesvideo;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getYearReliaze() {
        return yearReliaze;
    }

    public void setYearReliaze(int yearreliaze) {
        this.yearReliaze = yearreliaze;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmsEntity that = (FilmsEntity) o;

        if (filmId != that.filmId) return false;
        if (fee != that.fee) return false;
        if (yearReliaze != that.yearReliaze) return false;
        if (filmName != null ? !filmName.equals(that.filmName) : that.filmName != null) return false;
        if (premiere != null ? !premiere.equals(that.premiere) : that.premiere != null) return false;
        if (referencesVideo != null ? !referencesVideo.equals(that.referencesVideo) : that.referencesVideo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filmId;
        result = 31 * result + (filmName != null ? filmName.hashCode() : 0);
        result = 31 * result + (premiere != null ? premiere.hashCode() : 0);
        result = 31 * result + (referencesVideo != null ? referencesVideo.hashCode() : 0);
        result = 31 * result + fee;
        result = 31 * result + yearReliaze;
        return result;
    }

    public String getShortDescription(){
        if(description.length() > 599) {
            String s = this.description.substring(0, 600);
            s = s.substring(0, s.lastIndexOf(" ")) + " ...";
            return s;
        }else {
            return description;
        }

    }
}
