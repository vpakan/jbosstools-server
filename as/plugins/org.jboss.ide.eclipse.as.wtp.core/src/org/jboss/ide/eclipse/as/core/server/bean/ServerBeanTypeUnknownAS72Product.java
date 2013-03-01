/******************************************************************************* 
 * Copyright (c) 2013 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.ide.eclipse.as.core.server.bean;

import java.io.File;
import java.util.Properties;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.jboss.ide.eclipse.as.core.util.IJBossToolingConstants;

public class ServerBeanTypeUnknownAS72Product extends JBossServerType {
	public ServerBeanTypeUnknownAS72Product() {
		super(
				"EAP-Product",
				"Application Server", //$NON-NLS-1$
				asPath("modules","system","layers","base",
						"org","jboss","as","server","main"),
				new String[]{}, 
				new UnknownAS72ProductServerTypeCondition());
	}
	public static class UnknownAS72ProductServerTypeCondition extends org.jboss.ide.eclipse.as.core.server.bean.ServerBeanTypeEAP6.EAP6ServerTypeCondition {
		public String getServerTypeId(String version) {
			return IJBossToolingConstants.SERVER_EAP_61;
		}
		public boolean isServerRoot(File location) {
			return getFullVersion(location, null) != null;
		}
		public String getFullVersion(File location, File systemJarFile) {
			IPath rootPath = new Path(location.getAbsolutePath());
			IPath productConf = rootPath.append("bin/product.conf"); //$NON-NLS-1$
			if( productConf.toFile().exists()) {
				Properties p = JBossServerType.loadProperties(productConf.toFile());
				String product = (String) p.get("slot"); //$NON-NLS-1$
				return getEAP6xVersionNoSlotCheck(location, 
						getMetaInfFolderForSlot(product),
						null, null);
			}
			return null;
		}
	}

	private static String getMetaInfFolderForSlot(String slot) {
		return "modules/system/layers/base/org/jboss/as/product/" + slot + "/dir/META-INF"; //$NON-NLS-1$

	}
}
