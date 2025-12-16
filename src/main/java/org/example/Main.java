/**
 * A program belépési pontja.
 * <p>
 * Elindítja a grafikus felhasználói felületet (Swing),
 * és létrehozza az {@link ExpenseFrame} ablakot.
 * </p>
 */

package org.example;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseFrame::new);
    }
}
