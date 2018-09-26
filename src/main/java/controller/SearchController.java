package controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@SessionScoped
public class SearchController {
    private String[] genres_1;
    private String[] genres_2;
    private String[] countries;
    private String yearBottom;
    private String yearTop;

    public SearchController() {
    }

    public String[] getGenres_1() {
        return genres_1;
    }

    public void setGenres_1(String[] genres_1) {
        this.genres_1 = genres_1;
    }

    public String[] getGenres_2() {
        return genres_2;
    }

    public void setGenres_2(String[] genres_2) {
        this.genres_2 = genres_2;
    }

    public String getYearBottom() {
        return yearBottom;
    }

    public void setYearBottom(String yearBottom) {
        this.yearBottom = yearBottom;
    }

    public String getYearTop() {
        return yearTop;
    }

    public void setYearTop(String yearTop) {
        this.yearTop = yearTop;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }

    public String[] getGenresOneValue() {
        genres_1 = new String[8];
        genres_1[0] = "Биографический";
        genres_1[1] = "Боевик";
        genres_1[2] = "Вестерн";
        genres_1[3] = "Военный";
        genres_1[4] = "Детектив";
        genres_1[5] = "Фантастика";
        genres_1[6] = "Фэнтези";
        genres_1[7] = "Комедия";

        return genres_1;
    }

    public String[] getGenresTwoValue() {
        genres_2 = new String[8];
        genres_2[0] = "Драма";
        genres_2[1] = "Документальный";
        genres_2[2] = "Ужасы";
        genres_2[3] = "Спортивный";
        genres_2[4] = "Мелодрама";
        genres_2[5] = "Мультфильм";
        genres_2[6] = "Приключения";
        genres_2[7] = "Триллер";

        return genres_2;
    }

    public String[] getCountriesValue() {
        countries = new String[7];
        countries[0] = "США";
        countries[1] = "Россия";
        countries[2] = "Франция";
        countries[3] = "Великобритания";
        countries[4] = "Австралия";
        countries[5] = "Германия";
        countries[6] = "Китай";

        return countries;
    }

    public String[] getGenres() {
        String[] genres = new String[genres_1.length + genres_2.length];
        for (int i = 0; i < genres_1.length + genres_2.length; i++) {
            if (i < genres_1.length) {
                genres[i] = genres_1[i];
            } else {
                int j = 0;
                genres[i] = genres_2[j];
                j++;
            }
        }
        return genres;
    }
}
