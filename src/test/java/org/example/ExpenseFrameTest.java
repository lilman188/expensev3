package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseFrameTest {

    private ExpenseFrame frame;

    @BeforeEach
    void setUp() {
        frame = new ExpenseFrame();
    }

    @Test
    void testFrameInitialization() {
        assertEquals("Költségelszámoló", frame.getTitle());
        assertEquals(WindowConstants.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
        assertTrue(frame.isVisible());
    }

    @Test
    void testTableModelStructure() {
        JTable table = findComponent(frame, JTable.class);

        assertNotNull(table);
        assertEquals(3, table.getColumnCount());
        assertEquals("ID", table.getColumnName(0));
        assertEquals("Megnevezés", table.getColumnName(1));
        assertEquals("Összeg (Ft)", table.getColumnName(2));
    }

    @Test
    void testButtonsPresence() {
        JButton addBtn = findButtonByText(frame, "Hozzáadás");
        JButton deleteBtn = findButtonByText(frame, "Törlés");
        JButton exportBtn = findButtonByText(frame, "Exportálás Excelbe");

        assertNotNull(addBtn);
        assertNotNull(deleteBtn);
        assertNotNull(exportBtn);
    }

    private <T extends Component> T findComponent(Container container, Class<T> clazz) {
        for (Component comp : container.getComponents()) {
            if (clazz.isInstance(comp)) {
                return clazz.cast(comp);
            } else if (comp instanceof Container) {
                T found = findComponent((Container) comp, clazz);
                if (found != null) return found;
            }
        }
        return null;
    }

    private JButton findButtonByText(Container container, String text) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton && text.equals(((JButton) comp).getText())) {
                return (JButton) comp;
            } else if (comp instanceof Container) {
                JButton found = findButtonByText((Container) comp, text);
                if (found != null) return found;
            }
        }
        return null;
    }
}