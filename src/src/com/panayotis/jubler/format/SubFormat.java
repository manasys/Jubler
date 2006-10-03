/*
 * SubFormat.java
 *
 * Created on 13 Ιούλιος 2005, 7:44 μμ
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

package com.panayotis.jubler.format;

import com.panayotis.jubler.subs.Subtitles;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author teras
 */
public abstract class SubFormat {
    
    public abstract String getExtension();
    public abstract String getName();
    
    /* convert a string into subtitles */
    public abstract Subtitles parse(String input, float FPS, File f);
    
    /* Export subtitles into a string stream */
    public abstract void produce(Subtitles subs, float FPS, BufferedWriter out) throws IOException;

    public String getExtendedName() {
        return getName();
    }
    
    public String getDescription() {
        return getExtendedName() + "  (*." + getExtension() + ")";
    }

}
