package view;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * to get proper messages for users 
 * @author dotnet2
 */
public class Messages {
	private static final String BUNDLE_NAME = "view.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

    /**
     *
     * @param key to search for proper message
     * @return proper message for users based on key
     */
    public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
