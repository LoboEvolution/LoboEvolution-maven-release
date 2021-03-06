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
package com.jtattoo.plaf.graphite;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseIcons;

/**
 * <p>GraphiteIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class GraphiteIcons extends BaseIcons {

	/**
	 * <p>getCloseIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getCloseIcon() {
		if (closeIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				closeIcon = new MacCloseIcon();
			} else {
				Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				closeIcon = new BaseIcons.CloseSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(1, 1, 1, 1));
			}
		}
		return closeIcon;
	}

	/**
	 * <p>getIconIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getIconIcon() {
		if (iconIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				iconIcon = new MacIconIcon();
			} else {
				Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				iconIcon = new BaseIcons.IconSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(1, 1, 1, 1));
			}
		}
		return iconIcon;
	}

	/**
	 * <p>getMaxIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMaxIcon() {
		if (maxIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				maxIcon = new MacMaxIcon();
			} else {
				Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				maxIcon = new BaseIcons.MaxSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(1, 1, 1, 1));
			}
		}
		return maxIcon;
	}

	/**
	 * <p>getMinIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMinIcon() {
		if (minIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				minIcon = new MacMinIcon();
			} else {
				Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				minIcon = new BaseIcons.MinSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(1, 1, 1, 1));
			}
		}
		return minIcon;
	}

} // end of class GraphiteIcons
