package sk.upjs.ics.novotnyr.bookr;

import sk.upjs.ics.novotnyr.bookr.gui.BookDashboardForm;


public enum GuiFactory {
    INSTANCE;

    private BookDashboardForm bookDashboardForm;

    public BookDashboardForm bookDashboardForm() {
        if (this.bookDashboardForm == null) {
            this.bookDashboardForm = new BookDashboardForm();
        }
        return this.bookDashboardForm;
    }


}
