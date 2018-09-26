package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "country", schema = "public", catalog = "TrailerGo")
@Cacheable
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "countryid")
    private int countryId;

    @Basic
    @Column(name = "countryname")
    private String countryName;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "countrytofilm",
            joinColumns = { @JoinColumn(name = "countryid") },
            inverseJoinColumns = { @JoinColumn(name = "filmid") }
    )
    Set<FilmsEntity> films;

    public Set<FilmsEntity> getFilms() {
        return films;
    }

    public void setFilms(Set<FilmsEntity> films) {
        this.films = films;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryid) {
        this.countryId = countryid;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryname) {
        this.countryName = countryname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (countryId != that.countryId) return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryId;
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        return result;
    }
}
