--Numer grupy po nazwie
SELECT nr_grupy FROM grupy WHERE nazwa='pierwsza';
--Imie, nazwisko po przedmiocie, wykladowcy i grupie
SELECT osoby.imie, osoby.nazwisko FROM osoby
JOIN studenci ON studenci.id_osoby=osoby.id_osoby
JOIN grupy ON grupy.nr_grupy=studenci.nr_grupy
JOIN grupa_zajeciowa ON grupa_zajeciowa.nr_grupy=grupy.nr_grupy
WHERE grupa_zajeciowa.id_przedmiotu=2 AND grupa_zajeciowa.id_wykladowcy=2 AND grupy.nr_grupy=11;
--Nazwy grup po numerze przedmiotu i wykladowcy
SELECT grupy.nazwa FROM grupy
JOIN grupa_zajeciowa ON grupa_zajeciowa.nr_grupy=grupy.nr_grupy
WHERE grupa_zajeciowa.id_przedmiotu=2 AND grupa_zajeciowa.id_wykladowcy=2;
--Ustal id przedmiotu po nazwie
SELECT przedmioty.id_przedmiotu FROM przedmioty WHERE przedmioty.nazwa='Systemy inteligentne';

--Ustal nr albumu studenta na podstawie imienia i nazwiska
SELECT studenci.nr_albumu FROM studenci
JOIN osoby ON osoby.id_osoby=studenci.id_osoby
WHERE osoby.imie='Aleksander' AND osoby.nazwisko='Wojcik';


SELECT * FROM przedmioty;
SELECT * FROM grupa_zajeciowa;
SELECT * FROM wykladowcy;
SELECT * FROM grupy;
SELECT COUNT(*) FROM oceny;
SELECT * FROM oceny;