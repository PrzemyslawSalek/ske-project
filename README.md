# Ske-project

Projekt zaliczeniowy z przedmiotu "Systemy klasy Enterprise - persystencje".

## Zrealizowane funkcjonalności

- [x] 3 CRUD + Spring Security z podziałem na role : admin -dodaje i usuwa  ksiazki z ksiegarni, user możne przeglądać.
- [x] 3.5 CRUD + koszyk - dodawanie usuwanie książek przez Usera.
- [x] 4 Zamówienia -  Dodanie funkcji które może wykonywać User - składanie zamówień na książki, zmiana statusu zamówienia przez admina.
- [x] 4.5 Wygląd aplikacji Można użyć boostrapa / Własny bardziej rozbudowany projekt lub bardziej rozszerzony projekt z przykładu
- [x] 5 Płatności - podpięcie np. PayU
- [ ] 0.5 za zadania - jeżeli  zostanie zrealizowany inny projekt na zaliczenie.

## Wygląd aplikacji

![skeproject](https://github.com/PrzemyslawSalek/ske-project/blob/dev/src/main/resources/static/skeproject.gif)

## Uzupełnienie bazy danych

```
INSERT INTO "users" VALUES ('user', 'true', 'user', 'ROLE_USER');
INSERT INTO "users" VALUES ('admin', 'true', 'admin', 'ROLE_ADMIN');

INSERT INTO "categories" VALUES (1, 'Powieść');
INSERT INTO "categories" VALUES (2, 'Popularnonaukowa');
INSERT INTO "categories" VALUES (3, 'Biografia');

INSERT INTO "books" VALUES (1, 'Hamlet', 16, 'AAA', 3);
INSERT INTO "books" VALUES (2, 'Makbet', 23, 'AAA', 2);
INSERT INTO "books" VALUES (3, 'Potop', 64, 'BBB', 2);
INSERT INTO "books" VALUES (4, 'Quo vadis', 70, 'BBB', 2);
INSERT INTO "books" VALUES (5, 'Pan Tadeusz', 90, 'CCC', 2);
INSERT INTO "books" VALUES (6, 'Nad Niemnem', 20, 'CCC', 2);
```
