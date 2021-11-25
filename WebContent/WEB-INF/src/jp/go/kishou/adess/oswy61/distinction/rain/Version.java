package jp.go.kishou.adess.oswy61.distinction.rain;

import java.util.ResourceBundle;
public final class Version {
	private static final String VERSION_PROPERTY ="jp.go.kishou.adess.oswy61.distinction.rain.conf.Version";
	private static final String PROPERTY_KEY = "Version.version";

	private static String ver = null;
	static{
		try{
			ResourceBundle bundle = ResourceBundle.getBundle(VERSION_PROPERTY);
			String value = bundle.getString(PROPERTY_KEY).trim();
			ver = "".equals(value) ? "UNKNOWN" : value;
		} catch (Throwable t) {
			ver = "UNKNOWN";
		}
	}

	private Version() {
		super();
	}

	public static final String getVersion() {
		return ver;
	}
}