package org.example;
/**
 * Egy költség adatmodellje.
 * <p>
 * Egyetlen kiadást reprezentál,
 * amely rendelkezik azonosítóval,
 * megnevezéssel és összeggel.
 * </p>
 */
public class Expense
    /** A költség egyedi azonosítója */{
    private int id;

    /** A költség egyedi azonosítója */

    private String name;

    /** A költség összege forintban */
    private double amount;
    /**
     * Létrehoz egy új {@code Expense} objektumot.
     *
     * @param id a költség azonosítója
     * @param name a költség megnevezése
     * @param amount a költség összege
     */
    public Expense(int id, String name, double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
    /**
     * Visszaadja a költség azonosítóját.
     *
     * @return azonosító
     */
    public int getId() { return id; }

    /**
     * Visszaadja a költség megnevezését.
     *
     * @return megnevezés
     */

    public String getName() { return name; }

    /**
     * Visszaadja a költség összegét.
     *
     * @return összeg forintban
     */
    public double getAmount() { return amount; }

    public void setName(String name) { this.name = name; }
    public void setAmount(double amount) { this.amount = amount; }
}
