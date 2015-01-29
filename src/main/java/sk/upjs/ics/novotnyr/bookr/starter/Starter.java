package sk.upjs.ics.novotnyr.bookr.starter;

import com.jgoodies.looks.windows.WindowsLookAndFeel;
import sk.upjs.ics.novotnyr.bookr.GuiFactory;
import sk.upjs.ics.novotnyr.bookr.SystemUtilities;
import sk.upjs.ics.novotnyr.bookr.gui.BookDashboardForm;

import javax.swing.*;

public class Starter {
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        if (SystemUtilities.isRunningOnWindows()) {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BookDashboardForm bookDashboardForm = GuiFactory.INSTANCE.bookDashboardForm();
                bookDashboardForm.setLocationRelativeTo(null);
                bookDashboardForm.setVisible(true);
            }
        });
    }
}
