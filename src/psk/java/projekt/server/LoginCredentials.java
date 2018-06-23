package psk.java.projekt.server;

import java.io.Serializable;

public class LoginCredentials implements Serializable {
    public LoginCredentials() {
    }

    public LoginCredentials(String login, String passwd) {

        this.login = login;
        this.passwd = passwd;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getLogin() {

        return login;
    }

    public String getPasswd() {
        return passwd;
    }

    private String login;
    private String passwd;
}