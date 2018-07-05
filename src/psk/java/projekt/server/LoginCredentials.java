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
     * Publiczny przeciążony konstruktor klasy LoginCredentials który przyjmuje 2 parametry
     * @param login Parametr typu String który przechowuje login
     * @param passwd Parametr typu String który przechowuje hasło
     */
    public LoginCredentials(String login, String passwd) {
        this.login = login;
        this.passwd = passwd;
    }

    /**
     * Publiczna metoda typu void to setter który ma za zadanie przypisać login do pola login klasy LoginCredentials
     * @param login Parametr typu String który przechowuje login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Publiczna metoda typu void to setter który ma za zadanie przypisać hasł do pola passwd klasy LoginCredentials
     * @param passwd Parametr typu String - dokładnie jest to hasło
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Publiczna metoda to getter który ma za zadanie pozyskać login
     * @return Zwraca typ String - dokładnie zwraca login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Publiczna metoda to getter który ma za zadanie pozyskać hasło
     * @return Zwraca typ String - dokładnie zwraca hasło
     */
    public String getPasswd() {
        return passwd;
    }
    private Integer id;

    /**
     * Publiczna metoda typu void to setter który ma za zadanie przypisać id do pola id klasy LoginCredentials
     * @param id Parametr typu Integer który przechowuje id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Publiczna metoda to getter który ma za zadanie pozyskać id
     * @return Zwraca typ Integer - dokładnie zwraca id
     */
    public Integer getId() {
        return id;
    }

    private String login;
    private String passwd;
}