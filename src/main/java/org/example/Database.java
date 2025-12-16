package org.example;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Az adatbázis-kezelésért felelős osztály.
 * <p>
 * SQLite adatbázist használ a költségek
 * tárolására, lekérdezésére és törlésére.
 * </p>
 */
public class Database {

    private static final String URL = "jdbc:sqlite:expenses.db";
    /**
     * Inicializálja az adatbázist és létrehozza
     * az expenses táblát, ha még nem létezik.
     */
    public Database() {
        try {
            // SQLite driver betöltése, ideiglenes tár
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
// táblák létrehozása
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
    /**
     * Lekérdezi az összes költséget az adatbázisból.
     *
     * @return a költségek listája
     */
    public List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM expenses")) {

            while (rs.next()) {
                list.add(new Expense(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("amount")
                ));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return list;
    }
    /**
     * Új költséget szúr be az adatbázisba.
     *
     * @param name a költség megnevezése
     * @param amount a költség összege
     */
    public void addExpense(String name, double amount) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO expenses (name, amount) VALUES (?, ?)")) {

            ps.setString(1, name);
            ps.setDouble(2, amount);
            ps.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    /**
     * Töröl egy költséget az adatbázisból azonosító alapján.
     *
     * @param id a törlendő költség azonosítója
     */
    public void deleteExpense(int id) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM expenses WHERE id = ?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
