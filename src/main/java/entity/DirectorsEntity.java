package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "directors", schema = "public", catalog = "TrailerGo")
@Cacheable
public class DirectorsEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "directorid")
    private int directorId;

    @Basic
    @Column(name = "directorname")
    private String directorName;

    @Basic
    @Column(name = "directorsurname")
    private String directorSurname;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "directorsinfilm",
            joinColumns = { @JoinColumn(name = "directorid") },
            inverseJoinColumns = { @JoinColumn(name = "filmid") }
    )
    Set<FilmsEntity> films;

    public Set<FilmsEntity> getFilms() {
        return films;
    }

    public void setFilms(Set<FilmsEntity> films) {
        this.films = films;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorid) {
        this.directorId = directorid;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorname) {
        this.directorName = directorname;
    }

    public String getDirectorSurname() {
        return directorSurname;
    }

    public void setDirectorSurname(String directorsurname) {
        this.directorSurname = directorsurname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectorsEntity that = (DirectorsEntity) o;

        if (directorId != that.directorId) return false;
        if (directorName != null ? !directorName.equals(that.directorName) : that.directorName != null) return false;
        if (directorSurname != null ? !directorSurname.equals(that.directorSurname) : that.directorSurname != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = directorId;
        result = 31 * result + (directorName != null ? directorName.hashCode() : 0);
        result = 31 * result + (directorSurname != null ? directorSurname.hashCode() : 0);
        return result;
    }
}
