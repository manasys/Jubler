/*
 * JExtBasicOptions.java
 *
 * Created on 6 Ιούλιος 2005, 4:18 πμ
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
import com.panayotis.jubler.os.DEBUG;
import com.panayotis.jubler.os.SystemDependent;
import com.panayotis.jubler.os.TreeWalker;
import java.io.File;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.JPanel;


/**
 *
 * @author  teras
 */
public class JExtBasicOptions extends JPanel {
    private JFileChooser fdialog;
    
    protected String name;
    protected String programname;
    protected String type;
    
    /** Creates new form MPlay */
    public JExtBasicOptions(String type, String name, String programname) {
        super();
        
        this.type = type;
        this.name = name;
        this.programname = programname;
        
        initComponents();
        
        FileL.setText(_("{0} path", name));
        fdialog = new JFileChooser();
        fdialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        BrowserP = new javax.swing.JPanel();
        FilenameT = new javax.swing.JTextField();
        Browse = new javax.swing.JButton();
        FileL = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        BrowserP.setLayout(new java.awt.BorderLayout());

        BrowserP.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 8, 0));
        FilenameT.setColumns(20);
        FilenameT.setEditable(false);
        FilenameT.setToolTipText(_("The absolute path of the player. Use the Browse button to change it"));
        BrowserP.add(FilenameT, java.awt.BorderLayout.CENTER);

        Browse.setText(_("Browse"));
        Browse.setToolTipText(_("Open a file dialog to select the filename of the player"));
        Browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrowseActionPerformed(evt);
            }
        });

        BrowserP.add(Browse, java.awt.BorderLayout.EAST);

        FileL.setText("[path]");
        BrowserP.add(FileL, java.awt.BorderLayout.NORTH);

        add(BrowserP, java.awt.BorderLayout.NORTH);

    }// </editor-fold>//GEN-END:initComponents
    
    /* Use an external method, so that this actins can be monitored (e.g. in spell check */
    protected boolean activatedBrowseButton() {
        if ( fdialog.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return false;
        File newexe = TreeWalker.searchExecutable(fdialog.getSelectedFile(), programname.toLowerCase());
        if (newexe!=null) {
            FilenameT.setText(newexe.getAbsolutePath());
            return true;
        }
        else {
            DEBUG.error(_("Unable to find valid executable for {0}.",name));
            return false;
        }
    }
    
    private void BrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrowseActionPerformed
        activatedBrowseButton();
    }//GEN-LAST:event_BrowseActionPerformed
    
    /* Use this method tyo grab feedback when the options card of this program gets activated */
    public void activateProgramPanel() {}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Browse;
    protected javax.swing.JPanel BrowserP;
    private javax.swing.JLabel FileL;
    private javax.swing.JTextField FilenameT;
    // End of variables declaration//GEN-END:variables
    
    protected void loadPreferences(Properties props) {
        FilenameT.setText( props.getProperty(type + "." + name + ".Path", name.toLowerCase()) );
    }
    
    protected void savePreferences(Properties props) {
        props.setProperty(type + "." + name + ".Path", FilenameT.getText());
    }
    
    public String getExecFileName() {
        return SystemDependent.getRealExecFilename(FilenameT.getText());
    }
    
    public JPanel getOptionsPanel() {
        return this;
    }
}