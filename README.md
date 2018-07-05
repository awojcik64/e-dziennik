Elektroniczny system oceniania
Instukcja uruchomienia 
Aby uruchomić aplikację wystarczy skompilować clienta i server oraz je uruchomić, natomiast aby ona działała musi mieć dostęp do bazy danych. 
Można stworzyć bazę danych lokalnie np. na serwerze Oracle XE, gdy to zrobimy będziemy musieli zmienić kod odpowiadający za łączenie z bazą danych
który znajduje się w klasie ServerConnectionHandler i ma następującą postać:
db = DriverManager.getConnection("jdbc:oracle:thin:@alekstablefield.asuscomm.com:27020:XE", "edziennik", "B@zka123!");
należy zmienić parametry metody getConnection na ("jdbc:oracle:thin:@localhost:port:XE", "login", "password");
gdzie zamiast port należy podać odpowiedni port, zamiast login podać login i password podać odpowiednio login i hasło do bazy danych
Aby JDBC z którego korzystamy w projekcie działało poprawnie potrzebujemy sterownika który można pobrać ze strony:
http://www.oracle.com/technetwork/database/application-development/jdbc/downloads/index.html
Po wykonaniu powyższych czynności aplikacja powinna działać.