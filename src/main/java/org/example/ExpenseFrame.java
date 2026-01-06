/**
 * A költségelszámoló alkalmazás fő grafikus ablaka.
 * <p>
 * Megjeleníti a költségeket táblázatos formában,
 * és lehetőséget biztosít új költség hozzáadására
 * valamint meglévő költségek törlésére.
 * </p>
 */

package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExpenseFrame extends JFrame {
    private Database db;
    private JTable table;
    private DefaultTableModel model;

    /** Az adatbázis-kezelő objektum */
    public ExpenseFrame() {
        db = new Database();
        setTitle("Költségelszámoló");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /** Az adatbázis-kezelő objektum */
        model = new DefaultTableModel(new String[]{"ID", "Megnevezés", "Összeg (Ft)"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Gombok
        JButton addBtn = new JButton("Hozzáadás");
        JButton deleteBtn = new JButton("Törlés");
        JButton exportButton = new JButton("Exportálás Excelbe");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(exportButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();

        addBtn.addActionListener(e -> addExpense());
        deleteBtn.addActionListener(e -> deleteExpense());
        exportButton.addActionListener(e -> exportToExcel());
        setVisible(true);
    }
    /**
     * Frissíti a táblázat tartalmát az adatbázisból beolvasott adatokkal.
     */
    private void refreshTable() {
        model.setRowCount(0);
        List<Expense> expenses = db.getAllExpenses();
        for (Expense ex : expenses) {
            model.addRow(new Object[]{ex.getId(), ex.getName(), ex.getAmount()});
        }
    }
    /**
     * Új költséget ad hozzá az adatbázishoz
     * a felhasználótól bekért adatok alapján.
     */
    private void addExpense() {
        String name = JOptionPane.showInputDialog(this, "Megnevezés:");
        if (name == null || name.isEmpty()) return;

        String amountStr = JOptionPane.showInputDialog(this, "Összeg (Ft):");
        try {
            double amount = Double.parseDouble(amountStr);
            db.addExpense(name, amount);
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Érvénytelen összeg!", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Törli a felhasználó által kiválasztott költséget
     * az adatbázisból.
     */

    private void deleteExpense() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) model.getValueAt(row, 0);
        db.deleteExpense(id);
        refreshTable();
    }

    private void exportToExcel() {
        List<Expense> expenses = db.getAllExpenses();
        if (expenses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nincs exportálandó adat!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Mentés Excel fájlként");
        fileChooser.setSelectedFile(new java.io.File("koltsegek.xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) return;

        java.io.File fileToSave = fileChooser.getSelectedFile();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Költségek");

            // Fejléc
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Megnevezés", "Összeg (Ft)"};

            CellStyle boldStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font font = workbook.createFont(); // POI Font teljesen kvalifikált
            font.setBold(true);
            boldStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(boldStyle);
            }

            // Adatok
            int rowNum = 1;
            for (Expense exp : expenses) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(exp.getId());
                row.createCell(1).setCellValue(exp.getName());
                row.createCell(2).setCellValue(exp.getAmount());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(this, "✅ Export sikeres: " + fileToSave.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Hiba az exportálás során: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
