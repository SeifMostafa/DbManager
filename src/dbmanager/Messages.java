package dbmanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "dbmanager.messages"; //$NON-NLS-1$
	//private static final String AR_BUNDLE_NAME = "dbmanager.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	public static String getString(String key,String bundle) {
		try {
			ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(bundle);
			return RESOURCE_BUNDLE.getString(key);
		}catch(MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
