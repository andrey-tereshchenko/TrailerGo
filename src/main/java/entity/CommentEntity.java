package entity;

import javax.persistence.*;

import java.sql.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comment", schema = "public", catalog = "TrailerGo")
@Cacheable
public class CommentEntity implements Comparable<CommentEntity>{
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = IDENTITY)
    private int commentId;

    @Basic
    @Column(name = "comment_value")
    private String value;

    @Basic
    @Column(name = "user_id")
    private int userId;

    @Basic
    @Column(name = "film_id")
    private int filmId;

    @Basic
    @Column(name = "comment_date")
    private Date date;

    public CommentEntity(){

    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return getCommentId() == that.getCommentId() &&
                getUserId() == that.getUserId() &&
                getFilmId() == that.getFilmId() &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCommentId(), getValue(), getUserId(), getFilmId());
    }

    @Override
    public int compareTo(CommentEntity o) {
        return (o.commentId - this.commentId);
    }
}
