/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*
* see: gpl-2.0.txt
*
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
*
* see: lgpl-2.0.txt
* see: classpath-exception.txt
*
* Registered users could also use JTattoo under the terms and conditions of the
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*
* see: APACHE-LICENSE-2.0.txt
 */
package com.jtattoo.plaf.texture;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BasePanelUI;

/**
 * <p>TexturePanelUI class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class TexturePanelUI extends BasePanelUI {

	private static TexturePanelUI panelUI = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(JComponent c) {
		if (panelUI == null) {
			panelUI = new TexturePanelUI();
		}
		return panelUI;
	}

	/** {@inheritDoc} */
	@Override
	public void update(Graphics g, JComponent c) {
		if (c.isOpaque() && c.getBackground() instanceof ColorUIResource
				&& c.getClientProperty("backgroundTexture") == null) {
			TextureUtils.fillComponent(g, c, TextureUtils.getTextureType(c));
		} else {
			super.update(g, c);
		}
	}

} // end of class TexturePanelUI