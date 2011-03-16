/*
 * AbstractPlayerOptions.java
 *
 * Created on 25 Σεπτέμβριος 2005, 2:07 πμ
 *
 * This file is part of Jubler.
 *
 * Jubler is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2.
 *
 *
 * Jubler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Jubler; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package com.panayotis.jubler.options;

import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.media.player.AbstractPlayer;
import com.panayotis.jubler.os.SystemDependent;
import java.awt.BorderLayout;

/**
 *
 * @author  teras
 */
public class AbstractPlayerOptions extends JExtBasicOptions {

    private String args_default;

    /** Creates new form AbstractPlayerOptions */
    public AbstractPlayerOptions(String family, AbstractPlayer player) {
        super(family, player.getName(), player.getTestParameters(), player.getTestSignature());
        args_default = player.getDefaultArguments();

        initComponents();

        add(BrowserP, BorderLayout.NORTH);
    }

    @Override
    protected void loadPreferences() {
        super.loadPreferences();
        args.setText(Options.getOption("Player." + name + ".Arguments", args_default));
    }

    @Override
    public void savePreferences() {
        super.savePreferences();
        Options.setOption("Player." + name + ".Arguments", args.getText());
    }

    public String getArguments() {
        return args.getText();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ArgsP = new javax.swing.JPanel();
        HelpP = new javax.swing.JPanel();
        Help1L = new javax.swing.JLabel();
        Help2L = new javax.swing.JLabel();
        Help3L = new javax.swing.JLabel();
        Help4L = new javax.swing.JLabel();
        args = new javax.swing.JTextField();
        deflt = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        ArgsP.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        ArgsP.setLayout(new java.awt.BorderLayout());

        HelpP.setLayout(new java.awt.GridLayout(0, 1));

        Help1L.setText(_("Advanced argument list:"));
        HelpP.add(Help1L);

        Help2L.setText("    %p=player %v=video_file %s=subtiles_file %i=random_port");
        HelpP.add(Help2L);

        Help3L.setText("    %t=start_time %j=Jubler_path %x=x_offset %y=y_offset");
        HelpP.add(Help3L);

        Help4L.setText("    %a=optional_audio_file   %( %)=begin/end_of_audio_parameter");
        HelpP.add(Help4L);

        ArgsP.add(HelpP, java.awt.BorderLayout.NORTH);

        args.setColumns(40);
        ArgsP.add(args, java.awt.BorderLayout.CENTER);

        deflt.setText(_("Defaults"));
        deflt.setToolTipText(_("Use default player parameters"));
        SystemDependent.setCommandButtonStyle(deflt, "only");
        deflt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defltActionPerformed(evt);
            }
        });
        ArgsP.add(deflt, java.awt.BorderLayout.EAST);

        add(ArgsP, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void defltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defltActionPerformed
        args.setText(args_default);
    }//GEN-LAST:event_defltActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ArgsP;
    private javax.swing.JLabel Help1L;
    private javax.swing.JLabel Help2L;
    private javax.swing.JLabel Help3L;
    private javax.swing.JLabel Help4L;
    private javax.swing.JPanel HelpP;
    private javax.swing.JTextField args;
    private javax.swing.JButton deflt;
    // End of variables declaration//GEN-END:variables
}
