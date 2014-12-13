package org.doxu.iota.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 *
 * @author rafael
 */
public class MenuPanel extends JPanel {

    public MenuPanel(final UI ui) {
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ui.restart();
            }
        });
        add(start);
    }
}
