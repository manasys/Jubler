/*
 * StaticJubler.java
 *
 * Created on 9 Φεβρουάριος 2006, 9:56 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.panayotis.jubler;

import static com.panayotis.jubler.i18n.I18N._;

import com.panayotis.jubler.information.JAbout;
import com.panayotis.jubler.information.JVersion;
import com.panayotis.jubler.options.*;
import com.panayotis.jubler.options.gui.JUnsaved;
import com.panayotis.jubler.subs.Subtitles;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 *
 * @author teras
 */
public class StaticJubler {
    private static JVersion version;
    
    private static int screen_x, screen_y, screen_width, screen_height, screen_state;
    private static final int SCREEN_DELTAX = 24;
    private static final int SCREEN_DELTAY = 24;
    
    static {
        loadWindowPosition();
    }
    
    
    
    public static void setWindowPosition(Jubler current_window, boolean save) {
        screen_x = current_window.getX();
        screen_y = current_window.getY();
        screen_width = current_window.getWidth();
        screen_height = current_window.getHeight();
        screen_state = current_window.getExtendedState();
        if (save && screen_width > 0) {
            String vals = "(("+screen_x + "," + screen_y +"),(" + screen_width + "," + screen_height + "),"+ screen_state +")";
            Options.setOption("System.WindowState", vals);
            Options.saveOptions();
        }
        jumpWindowPosition(true);
    }
    
    public static void putWindowPosition(Jubler new_window) {
        if (screen_width <= 0) return;
        
        new_window.setLocationByPlatform(false);
        new_window.setBounds( screen_x, screen_y, screen_width, screen_height);
        new_window.setExtendedState(screen_state);
        jumpWindowPosition(true);
    }
    
    
    public static void jumpWindowPosition(boolean forth) {
        if (forth) {
            screen_x += SCREEN_DELTAX;
            screen_y += SCREEN_DELTAY;
        } else {
            screen_x -= SCREEN_DELTAX;
            screen_y -= SCREEN_DELTAY;
        }
    }
    
    public static void loadWindowPosition() {
        int [] values = new int[5];
        int pos = 0;
        
        for (int i = 0 ; i < 5 ; i++) {
            values[i] = -1;
        }
        
        String props = Options.getOption("System.WindowState", "");
        if (props!=null && (!props.equals("")) ) {
            StringTokenizer st = new StringTokenizer(props ,"(),");
            while (st.hasMoreTokens() && pos < 5 ) {
                values[pos++] = Integer.parseInt(st.nextToken());
            }
        }
        screen_x      = values[0];
        screen_y      = values[1];
        screen_width  = values[2];
        screen_height = values[3];
        screen_state  = values[4];
        
    }
    
    
    public static void showAbout() {
        JIDialog.message(null, new JAbout(), _("About Jubler"), JIDialog.INFORMATION_MESSAGE);
    }
    
    
    public static void quitAll() {
        Vector <String>unsaved = new Vector<String>();
        for (Jubler j : Jubler.windows) {
            if (j.isUnsaved()) {
                unsaved.add(j.getSubtitles().getCurrentFileName());
            }
        }
        if (unsaved.size()>0) {
            int ret = JIDialog.question(null, new JUnsaved(unsaved), _("Quit Jubler"), true);
            if (ret!=JIDialog.YES_OPTION) return;
        }
        System.exit(0);
    }
    
    
    public static void updateMenus(Jubler j) {
        j.prefs.setMenuShortcuts(j.JublerMenuBar);
    }
    
    public static void updateAllMenus() {
        for (Jubler j : Jubler.windows) updateMenus(j);
    }
    
    /* Find already opened files */
    public static ArrayList<String>  findOpenedFiles() {
        ArrayList<String> files = new ArrayList<String>();
        
        Subtitles subs;
        String jfile;
        boolean found;
        
        for (Jubler j : Jubler.windows) {
            subs = j.getSubtitles();
            if (subs!=null) {
                jfile = subs.getLastOpendFilePath();
                found = false;
                
                if (jfile!=null) {
                    for (String prevfile : files) {
                        if (prevfile.equals(jfile)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        files.add(jfile);
                }
            }
        }
        return files;
    }
    
    
    
    /* Add to Recents menu not opened files */
    public static void populateRecentsMenu(ArrayList<String> files) {
        JMenu recent_menu;
        for (Jubler j : Jubler.windows) {
            recent_menu = j.RecentsFM;
            
            /* Add clone entry */
            recent_menu.removeAll();
            if (j.getSubtitles()!=null) {
                recent_menu.add(addNewMenu( _("Clone current"), true, true, j, -1));
                recent_menu.add(new JSeparator());
            }
            
            if (files.size() == 0) {
                recent_menu.add( addNewMenu(_("-Not any recent items-"), false, false, j,  -1));
            } else {
                int counter = 1;
                for (String entry : files) {
                    recent_menu.add(addNewMenu(entry, false, true, j, counter++));
                }
            }
        }
    }
    
    private static JMenuItem addNewMenu(String text, boolean isclone, boolean enabled, Jubler jub, int counter) {
        JMenuItem item = new JMenuItem(text);
        item.setEnabled(enabled);
        if(counter>=0)
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0+counter, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        final boolean isclone_f = isclone;
        final String text_f = text;
        final Jubler jub_f = jub;
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isclone_f) jub_f.recentMenuCallback(null);
                else jub_f.recentMenuCallback(text_f);
            }
        });
        return item;
    }
    
    
    public static void initVersion() {
        version = new JVersion();
    }
    public static String getCurrentVersion() {
        return version.getCurrentVersion();
    }
}
