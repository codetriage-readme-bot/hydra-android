/**
 * Contains business rules and general logic of the application.
 *
 * You should not use Android-related things. However, a distinction needs to be made between two kind of
 * Android classes: the 'system' classes, such as {@link android.os.Bundle}, or the 'framework' classes. The first ones
 * are allowed if absolutely necessary. The second category is absolutely prohibited.
 *
 * Currently, allowed classes are:
 * - {@link android.os.Bundle}
 * - {@link android.content.SharedPreferences}, as it is an interface.
 *
 * Another area where shortcuts can be made to reduce code duplication are the entities. Sometimes it is faster to
 * add Parcelable, Serializable and Gson support to the domain models instead of making the same model three times.
 *
 * @author Niko Strijbol
 */
package be.ugent.zeus.hydra.domain;