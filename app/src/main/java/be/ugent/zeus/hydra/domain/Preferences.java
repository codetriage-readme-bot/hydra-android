package be.ugent.zeus.hydra.domain;

/**
 * Contains the names of the preferences.
 *
 * Preference names used to be saved wherever convenient, which meant they were scattered throughout the code.
 *
 * @author Niko Strijbol
 */
public final class Preferences {

    private Preferences() {
        throw new AssertionError("You cannot instantiate this class.");
    }

    /**
     * Contains a {@link java.util.Set<String>} with the ID's of the disabled associations as values.
     */
    public static final String DISABLED_ASSOCIATIONS = "pref_associations_showing";
}