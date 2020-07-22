# SD-Project
1. This is my semestral project from Software Design. It is an app for Library Management. It is done using Java, Spring framework, MVC architectural pattern, client-server architecture, Factory Method design pattern, Retrofit, REST API, WebSockets, Java FX for interface and Junit and Mockito for testing.

2. The application should have two types of users (a regular user and an administrator user) which have to provide a username and a password in order to use the application. At a given time, there can be multiple users connected via multiple clients to the same server. Also some tests are implemented for the logical part of the app.

The regular user can perform the following operations:
* Search books by genre, title, author, time until return.
* Borrow books for a specific period and return them

The administrator can perform the following operations:
* CRUD on books (book information: title, author, genre, etc).
* CRUD on regular users' information.
* Generate two types of report files, one in pdf format and one in txt or html format, with the current books and their history (borrowed or not) The reports need to be saved in a user-selected location (not predefined by the application), similar on how one would save a file from Notepad (this was done using Factory Method design pattern).

Other features:
* An user can put himself in the queue for a book when it's returned. When that happens, the user gets a live notification.
* If the user returns the book later than the expected return date and/or in a worse state, a penalty will be accumulated on his account.
* Users can add/remove books from their favourites list.
* Users can rate (between 1-5 stars) and review (textual description) the books, those reviews will be public. They can also search by the rating. The admin user can edit the ratings.
* If a user receives 5 penalty points, he won't be able to borrow another book for another 1 month. The penalty resets after that time.
* Administrator can change user's subscription which limits the number of books he can borrow at a time. The subscriptions can be added/changed by the admin
