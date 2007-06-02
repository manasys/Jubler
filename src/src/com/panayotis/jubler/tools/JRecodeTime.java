/*
 * JRecodeTime.java
 *
 * Created on 25 Ιούνιος 2005, 1:53 πμ
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

package com.panayotis.jubler.tools;
import com.panayotis.jubler.Jubler;
import com.panayotis.jubler.subs.SubEntry;
import java.awt.BorderLayout;

import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.media.console.TimeSync;
import com.panayotis.jubler.options.gui.JRateChooser;

/**
 *
 * @author  teras
 */
public class JRecodeTime extends JToolRealTime {
    private double factor;
    private double center;
    
    private double given_factor, given_center;
    private TimeSync t1, t2;
    
    private JRateChooser FromR, ToR;
    
    
    
    /** Creates new form JRecodeTime */
    public JRecodeTime() {
        super(true);
    }
    
    public void initialize() {
        initComponents();
        
        FromR = new JRateChooser();
        FromP.add(FromR, BorderLayout.CENTER);
        ToR = new JRateChooser();
        ToP.add(ToR, BorderLayout.CENTER);
        
        t1 = t2 = null;
    }
    
    protected String getToolTitle() {
        return _("Recode time");
    }
    
    
    public boolean setValues(TimeSync first, TimeSync second) {
        if (first.smallerThan(second)) {
            t1 = first;
            t2 = second;
        } else {
            t1 = second;
            t2 = first;
        }
        
        given_center = (t2.timediff*t1.timepos - t1.timediff*t2.timepos) / (t2.timediff-t1.timediff);
        if (Double.isInfinite(given_center)||Double.isNaN(given_center)) {
            t1 = t2 = null;
            given_center = given_factor = 0;
            return false;
        }
        
        given_factor = (t1.timepos-t2.timepos+t1.timediff-t2.timediff) / (t1.timepos-t2.timepos);
        if (Double.isInfinite(given_factor)||Double.isNaN(given_factor)) {
            t1 = t2 = null;
            given_center = given_factor = 0;
            return false;
        }
        
        pos.forceRangeSelection();
        return true;
    }
    
    
    public void updateData(Jubler j) {
        if (t1==null) {
            /* normal execution */
            super.updateData(j);
        } else {
            /* Directly execute from VideoConsole - do not follow normal procedure*/
            subs = j.getSubtitles();
            selected = new int[2];
            selected[0] = subs.findSubEntry(t1.timepos, true);
            selected[1] = subs.findSubEntry(t2.timepos, true);
            
            pos.updateData(subs, selected);
            jparent = j;
            
            /* Set recode parameters */
            CustomC.setText(Double.toString(given_center));
            CustomF.setText(Double.toString(given_factor));
            
            /* Set default selections */
            CustomB.setSelected(true);
            
            
            t1 = t2 = null;
            given_center = given_factor = 0;
        }
        /* Set other values */
        FromR.setDataFiles(j.getMediaFile(), j.getSubtitles());
        ToR.setDataFiles(j.getMediaFile(), j.getSubtitles());
    }
    
    public void storeSelections() {
        center = 0;
        factor = 1;
        try {
            if (AutoB.isSelected()) {
                factor  = FromR.getFPSValue() / ToR.getFPSValue();
            } else {
                factor = Double.parseDouble(CustomF.getText());
            }
            center = Double.parseDouble(CustomC.getText());
        } catch (NumberFormatException e) {
        }
    }
    
    protected void affect(int index) {
        SubEntry sub = affected_list.elementAt(index);
        sub.getStartTime().recodeTime(center, factor);
        sub.getFinishTime().recodeTime(center, factor);
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        Factor = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        AutoB = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        FromP = new javax.swing.JPanel();
        ToP = new javax.swing.JPanel();
        CustomB = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        CustomF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        CustomC = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(15, 0, 0, 0), javax.swing.BorderFactory.createTitledBorder(_("Use the following factor"))));
        Factor.add(AutoB);
        AutoB.setSelected(true);
        AutoB.setText(_("Automatically compute based on FPS"));
        AutoB.setToolTipText(_("Use the following FPS in order to automatically compute the desired recoding"));
        AutoB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AutoBActionPerformed(evt);
            }
        });

        jPanel1.add(AutoB);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel2.setText(" -> ");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel2, java.awt.BorderLayout.EAST);

        FromP.setLayout(new java.awt.BorderLayout());

        jPanel2.add(FromP, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel2);

        ToP.setLayout(new java.awt.BorderLayout());

        jPanel3.add(ToP);

        jPanel1.add(jPanel3);

        Factor.add(CustomB);
        CustomB.setText(_("Custom"));
        CustomB.setToolTipText(_("Use a custom factor in order to perform the recoding"));
        CustomB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustomBActionPerformed(evt);
            }
        });

        jPanel1.add(CustomB);

        jLabel1.setText(_("Recoding factor"));
        jPanel1.add(jLabel1);

        CustomF.setText("1.0");
        CustomF.setToolTipText(_("The value of the custom factor which will do the recoding"));
        CustomF.setEnabled(false);
        jPanel1.add(CustomF);

        jLabel3.setText(_("Central time"));
        jPanel1.add(jLabel3);

        CustomC.setText("0.0");
        CustomC.setToolTipText(_("The central time point which the recoding occurs. Usually left to 0 to apply evenly to the whole file."));
        CustomC.setEnabled(false);
        jPanel1.add(CustomC);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents
    
    private void AutoBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AutoBActionPerformed
        FromR.setEnabled(true);
        ToR.setEnabled(true);
        CustomF.setEnabled(false);
    }//GEN-LAST:event_AutoBActionPerformed
    
    private void CustomBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustomBActionPerformed
        FromR.setEnabled(false);
        ToR.setEnabled(false);
        CustomF.setEnabled(true);
    }//GEN-LAST:event_CustomBActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton AutoB;
    private javax.swing.JRadioButton CustomB;
    private javax.swing.JTextField CustomC;
    private javax.swing.JTextField CustomF;
    private javax.swing.ButtonGroup Factor;
    private javax.swing.JPanel FromP;
    private javax.swing.JPanel ToP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
    
}
