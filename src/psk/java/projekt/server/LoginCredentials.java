package psk.java.projekt.server;

import java.io.Serializable;

/**
 * Klasa odpowiada za logowanie, implementuje interfejs Serializable
 */
public class LoginCredentials implements Serializable {
    /**
     * Publiczny bezparametrowy konstruktor klasy LoginCredentials
     */
    public LoginCredentials() {
    }

    /**
     * Publiczny przeciazony konstruktor klasy LoginCredentials ktory przyjmuje 2 parametry
     * @param login Parametr typu String który przechowuje login
     * @param passwd Parametr typu String który przechowuje hasło
     */
    public LoginCredentials(String login, String passwd) {
        this.login = login;
        this.passwd = passwd;
    }

    /**
     * Publiczna metoda typu void to setter ktory ma za zadanie przypisac login do pola login klasy LoginCredentials
     * @param login Parametr typu String ktory przechowuje login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Publiczna metoda typu void to setter ktory ma za zadanie przypisac haslo do pola passwd klasy LoginCredentials
     * @param passwd Parametr typu String - dokladnie jest to hasło
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Publiczna metoda to getter ktory ma za zadanie pozyskac login
     * @return Zwraca typ String - dokladnie zwraca login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Publiczna metoda to getter ktory ma za zadanie pozyskac haslo
     * @return Zwraca typ String - dokladnie zwraca hasło
     */
    public String getPasswd() {
        return passwd;
    }
    private Integer id;

    /**
     * Publiczna metoda typu void to setter ktory ma za zadanie przypisac id do pola id klasy LoginCredentials
     * @param id Parametr typu Integer ktory przechowuje id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Publiczna metoda to getter ktory ma za zadanie pozyskac id
     * @return Zwraca typ Integer - dokladnie zwraca id
     */
    public Integer getId() {
        return id;
    }

    private String login;
    private String passwd;
}