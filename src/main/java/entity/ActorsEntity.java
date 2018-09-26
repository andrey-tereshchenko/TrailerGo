package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "actors", schema = "public", catalog = "TrailerGo")
@Cacheable
public class ActorsEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "actorid")
    private int actorId;

    @Basic
    @Column(name = "actorname")
    private String actorName;

    @Basic
    @Column(name = "actorsurname")
    private String actorSurname;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "actorsinfilm",
            joinColumns = { @JoinColumn(name = "actorid") },
            inverseJoinColumns = { @JoinColumn(name = "filmid") }
    )
    Set<FilmsEntity> films;

    public Set<FilmsEntity> getFilms() {
        return films;
    }

    public void setFilms(Set<FilmsEntity> films) {
        this.films = films;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorid) {
        this.actorId = actorid;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorSurname() {
        return actorSurname;
    }

    public void setActorSurname(String actorsurname) {
        this.actorSurname = actorsurname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActorsEntity that = (ActorsEntity) o;

        if (actorId != that.actorId) return false;
        if (actorName != null ? !actorName.equals(that.actorName) : that.actorName != null) return false;
        if (actorSurname != null ? !actorSurname.equals(that.actorSurname) : that.actorSurname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = actorId;
        result = 31 * result + (actorName != null ? actorName.hashCode() : 0);
        result = 31 * result + (actorSurname != null ? actorSurname.hashCode() : 0);
        return result;
    }

}
