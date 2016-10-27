package sk.upjs.ics.novotnyr.bookr.gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import sk.upjs.ics.novotnyr.bookr.BeanFactory;
import sk.upjs.ics.novotnyr.bookr.Publisher;
import sk.upjs.ics.novotnyr.bookr.PublisherDao;


public class PublisherEditForm extends JDialog {
    private PublisherDao publisherDao = BeanFactory.INSTANCE.publisherDao();
    
    private JLabel lblName = new JLabel("Name:");

    private JTextField txtName = new JTextField();;
    
    private JLabel lblWeb = new JLabel("Website:");;

    private JTextField txtWeb = new JTextField();;
    
    private JButton btnOk = new JButton("OK");
    
    private JButton btnCancel = new JButton("Cancel");
    
   
    public PublisherEditForm(Frame owner) {
        super(owner, "Publisher", /* modal*/ true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setLayout(new MigLayout("wrap 2", "[][grow, fill, 50:200:]", "[][][nogrid]"));
        
        add(lblName);
        add(txtName);

        add(lblWeb);
        add(txtWeb);

        add(btnOk, "tag ok");
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnOkActionPerformed(e);
            }
        });
        
        add(btnCancel, "tag cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCancelActionPerformed(e);
            }
        });
        
        setLocationByPlatform(true);
        setLocationRelativeTo(null);
        pack();
    }
    
    private void btnOkActionPerformed(ActionEvent event) {
        try {
            Publisher publisher = new Publisher();
            publisher.setName(txtName.getText());
            publisher.setWeb(new URL(txtWeb.getText()));

            publisherDao.saveOrUpdate(publisher);

            setVisible(false);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(this, "The publisher website URL is not correct");            
        }
    }


    private void btnCancelActionPerformed(ActionEvent e) {
        setVisible(false);
    }

}
