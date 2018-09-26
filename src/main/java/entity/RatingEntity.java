package entity;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "rating", schema = "public", catalog = "TrailerGo")
@Cacheable
public class RatingEntity {
    @Id
    @Column(name = "rate_id")
    @GeneratedValue(strategy = IDENTITY)
    private int ratingId;

    @Basic
    @Column(name = "mark")
    private int mark;

    @Basic
    @Column(name = "user_id")
    private int userId;

    @Basic
    @Column(name = "film_id")
    private int filmId;

    public RatingEntity() {
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingEntity that = (RatingEntity) o;
        return getRatingId() == that.getRatingId() &&
                getMark() == that.getMark() &&
                getUserId() == that.getUserId() &&
                getFilmId() == that.getFilmId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRatingId(), getMark(), getUserId(), getFilmId());
    }
}
