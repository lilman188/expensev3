import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public Database() {
    try {
        Class.forName("org.sqlite.JDBC"); // 👈 EZ HIÁNYZOTT

        Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement();

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                amount REAL NOT NULL
            )
        """);

        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                null,
                "Adatbázis hiba: " + e.getMessage(),
                "DB hiba",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

public void main() {
}

private static final String URL = ;
