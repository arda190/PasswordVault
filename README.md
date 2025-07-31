# Password Vault

A simple JavaFX desktop application to securely save and manage passwords for different sites.  
Passwords are encrypted with AES and stored in an SQLite database.

---

## Features

- Set and verify a master password (hashed with SHA-256).  
- Save new site credentials (site name, username, password).  
- View saved sites and show/hide decrypted passwords.  
- Passwords encrypted with AES using the master password as the key.  
- Data persistence via SQLite databases.

---

## Technologies

- Java 17+  
- JavaFX for GUI  
- SQLite for local database  
- AES encryption for password security  
- SHA-256 for master password hashing

---

## Usage

1. Run the application.  
2. On first launch, set a master password (stored hashed).  
3. Login with the master password.  
4. Use the menu to save new passwords or view saved sites.  
5. Click a site to see username and toggle password visibility.  
6. Passwords are encrypted in the database and decrypted on demand.

---

## Project Structure

- `LoginView.java` - Login screen with master password verification.  
- `MenuView.java` - Main menu with navigation buttons.  
- `SavePasswordView.java` - Form to add new site credentials.  
- `ShowPasswordsView.java` - List saved sites and show details.  
- `Database.java` - SQLite database helper class.  
- `AES.java` - AES encryption and decryption utility.  
- `PasswordEntry.java` - Data model for password entries.

---

## Security Notes

- Master password is hashed with SHA-256 before storage.  
- Passwords are encrypted with AES-128 using the master password as key.  
- Ensure to keep your master password secure; losing it means you cannot decrypt stored passwords.  
- This is a demo app; for production, consider using stronger key derivation (e.g., PBKDF2) and secure storage.

---

## How to Build & Run

- Clone the repo.  
- Use your IDE to build the JavaFX application or use Maven/Gradle if configured.  
- Run the `LoginView` class as the main application.  
- Make sure JavaFX SDK and SQLite JDBC driver are in your classpath.

---

## License

[MIT License](LICENSE)


