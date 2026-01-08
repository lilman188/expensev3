package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database db;

    @BeforeEach
    void setUp() {
        db = new Database();
        clearDatabase();
    }

    private void clearDatabase() {
        List<Expense> all = db.getAllExpenses();
        for (Expense e : all) {
            db.deleteExpense(e.getId());
        }
    }

    @Test
    void testAddExpense() {
        String name = "Kávé";
        double amount = 500.0;

        db.addExpense(name, amount);
        List<Expense> expenses = db.getAllExpenses();

        assertEquals(1, expenses.size());
        assertEquals(name, expenses.get(0).getName());
        assertEquals(amount, expenses.get(0).getAmount());
    }

    @Test
    void testDeleteExpense() {
        db.addExpense("Ebéd", 2500.0);
        List<Expense> current = db.getAllExpenses();
        int id = current.get(0).getId();

        db.deleteExpense(id);
        List<Expense> expensesAfterDelete = db.getAllExpenses();

        assertTrue(expensesAfterDelete.isEmpty());
    }
}