package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange: Előkészítjük a tesztadatokat
        int id = 1;
        String name = "Vacsora";
        double amount = 5500.0;

        // Act: Példányosítjuk az objektumot (ez a művelet, amit tesztelünk)
        Expense expense = new Expense(id, name, amount);

        // Assert: Ellenőrizzük, hogy a getterek a várt értékeket adják-e vissza
        assertAll("Getterek ellenőrzése",
                () -> assertEquals(id, expense.getId(), "Az ID nem egyezik."),
                () -> assertEquals(name, expense.getName(), "A név nem egyezik."),
                () -> assertEquals(amount, expense.getAmount(), "Az összeg nem egyezik.")
        );
    }

    @Test
    void testSetName() {
        // Arrange: Létrehozunk egy alap objektumot
        Expense expense = new Expense(1, "Reggeli", 1200.0);
        String newName = "Ebéd";

        // Act: Megváltoztatjuk a nevet a setterrel
        expense.setName(newName);

        // Assert: Ellenőrizzük a változást
        assertEquals(newName, expense.getName(), "A név frissítése sikertelen volt.");
    }

    @Test
    void testSetAmount() {
        // Arrange: Létrehozunk egy alap objektumot
        Expense expense = new Expense(1, "Taxi", 3000.0);
        double newAmount = 4500.0;

        // Act: Frissítjük az összeget
        expense.setAmount(newAmount);

        // Assert: Ellenőrizzük az új értéket
        assertEquals(newAmount, expense.getAmount(), "Az összeg frissítése sikertelen volt.");
    }
}