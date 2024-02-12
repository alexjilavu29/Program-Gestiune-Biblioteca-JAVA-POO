# Sistem de Gestiune a unei Biblioteci

Acest document descrie metodele utilizate în sistemul nostru de gestiune a bibliotecii, incluzând interacțiunile cu cărțile, clienții, comenzile și autorii.

## Cărți

- `insertBook(Book book)`: Folosită pentru testarea inserării obiectelor.
- `insertBookQuery()`: Adaugă cărți în baza de date.
- `filteredView()`: Ordonează cărțile după numărul de copii disponibile.
- `deleteBook(Book book)`: Folosită pentru testarea ștergerii obiectelor.
- `getBooks()`: Interoghează baza de date pentru cărți.
- `inputBookDetails()`: Permite inserarea detaliilor despre carte.

## Clienți

- `insertClient(Client client)`: Folosită pentru testarea inserării obiectelor.
- `insertClientQuery()`: Adaugă clienți în baza de date.
- `deleteClientQuery()`: Șterge clienți din baza de date.
- `clientCheckIn()`: Realizează Check-In pentru clienți.
- `profilePageQuery()`: Afișează pagina de profil a clientului.
- `deleteClient(Client client)`: Folosită pentru testarea ștergerii obiectelor.
- `getClients()`: Interoghează baza de date pentru clienți.

## Comenzi

- `insertOrder(Order order)`: Folosită pentru testarea inserării obiectelor.
- `borrowBookQuery()`: Permite împrumutarea cărților.
- `retrieveBookQuery()`: Readaugă cărțile în stoc.
- `orderCheckUp()`: Verifică clienții restanți.
- `deleteOrder(Order order)`: Folosită pentru testarea ștergerii obiectelor.
- `getOrders()`: Interoghează baza de date pentru comenzi.

## Autori

- `insertAuthor(Author author)`: Folosită pentru testarea inserării obiectelor.
- `deleteAuthor(Author author)`: Folosită pentru testarea ștergerii obiectelor.
- `getAuthors()`: Interoghează baza de date pentru autori.
- `inputAuthorDetails()`: Permite inserarea detaliilor despre autor.

## AuditLogs

- `insertAudit(String description, Date date)`: Înregistrează modificările în baza de date.
- `selectAudit30()`: Afișează modificările din ultimele 30 de zile.
- `selectAuditAll()`: Afișează toate modificările.
- `selectAuditSession(Date dateTime)`: Afișează modificările din sesiunea curentă.

## Tipuri de Obiecte

- `Book`, `BookRepository`
- `Client`, `ClientRepository`
- `Order`, `OrderRepository`
- `Person`
- `Author`, `AuthorRepository`
- `DatabaseHandler`
- `AuditLogs`
