package com.privasia.scss.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;



public class AppVersion {
	

	private static final Logger log = Logger.getLogger(AppVersion.class);

	private static String version;
	
	public static String getManifestInfo() {
	    Enumeration resEnum;
	    try {
	        resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
	        while (resEnum.hasMoreElements()) {
	            try {
	                URL url = (URL)resEnum.nextElement();
	                InputStream is = url.openStream();
	                if (is != null) {
	                    Manifest manifest = new Manifest(is);
	                    Attributes mainAttribs = manifest.getMainAttributes();
	                    String version = mainAttribs.getValue("Implementation-Version");
	                    if(version != null) {
	                        return version;
	                    }
	                }
	            }
	            catch (Exception e) {
	                // Silently ignore wrong manifests on classpath?
	            }
	        }
	    } catch (IOException e1) {
	        // Silently ignore wrong manifests on classpath?
	    }
	    return null; 
	}

	public static String get() {
		if (StringUtils.isBlank(version)) {
			Class<?> clazz = AppVersion.class;
			String className = clazz.getSimpleName() + ".class";
			String classPath = clazz.getResource(className).toString();
			if (!classPath.startsWith("jar")) {
				// Class not from JAR
				String relativePath = clazz.getName().replace('.', File.separatorChar) + ".class";
				String classFolder = classPath.substring(0, classPath.length() - relativePath.length() - 1);
				String manifestPath = classFolder + "/META-INF/MANIFEST.MF";
				log.debug("manifestPath={}" + manifestPath);
				version = readVersionFrom(manifestPath);
			} else {
				String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
				log.debug("manifestPath={}" + manifestPath);
				version = readVersionFrom(manifestPath);
			}
		}
		return version;
	}

	private static String readVersionFrom(String manifestPath) {
		Manifest manifest = null;
		try {
			manifest = new Manifest(new URL(manifestPath).openStream());
			Attributes attrs = manifest.getMainAttributes();

			String implementationVersion = attrs.getValue("Implementation-Version");
			implementationVersion = StringUtils.replace(implementationVersion, "-SNAPSHOT", "");
			log.debug("Read Implementation-Version: {}" + implementationVersion);

			String implementationBuild = attrs.getValue("Implementation-Build");
			log.debug("Read Implementation-Build: {}" + implementationBuild);

			String version = implementationVersion;
			if (StringUtils.isNotBlank(implementationBuild)) {
				version = StringUtils.join(new String[] { implementationVersion, implementationBuild }, '.');
			}
			return version;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return StringUtils.EMPTY;
	}

}
