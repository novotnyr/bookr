package sk.upjs.ics.novotnyr.bookr.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public abstract class DialogUtils {
    public static final MouseEvent NO_MOUSE_EVENT = null;

    public static int yesNoDialog(Component owner, String message) {
        return JOptionPane.showConfirmDialog(owner, message, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Adds a listener that closes a JDialog
     * on Esc keypress.
     *
     * @param dialog
     * @see http://stackoverflow.com/a/661244 for source
     */
    public static void addEscapeListener(final JDialog dialog) {
        ActionListener escListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        };

        dialog.getRootPane().registerKeyboardAction(escListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

    }
}
