package ŁC;

import java.io.Serializable;
import java.time.LocalDate;

class Person  implements Serializable {

    public Person() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdressLine() {
        return adressLine;
    }

    public void setAdressLine(String adressLine) {
        this.adressLine = adressLine;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Person(long id, String name, String adressLine, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.adressLine = adressLine;
        this.birthDate = birthDate;
    }

    private long id;
    private String name;
    private String adressLine;
    private LocalDate birthDate;


}