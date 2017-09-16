package be.ugent.zeus.hydra.domain.entities.homefeed;

/**
 * Util class, with e.g. help calculate the priority a card should have in the home feed.
 *
 * @see HomeCard
 *
 * @author Niko Strijbol
 */
public class PriorityUtils {

    /**
     * The special shift.
     */
    public static int FEED_SPECIAL_SHIFT = 10;

    /**
     * The upper limit on the feed.
     */
    public static int FEED_MAX_VALUE = 1000 + FEED_SPECIAL_SHIFT;

    /**
     * Lineair interpolation of the value x ∈ [a,b] to [10,FEED_MAX_VALUE]. The formula used is:
     *
     * x' = (c - a) * FEED_MAX_VALUE / (b - a)
     *
     * If x is bigger than b, FEED_MAX_VALUE is returned. This is to help calculate a correct priority.
     *
     * @param x The value in the original range.
     * @param a Original range start.
     * @param b Original range end.
     *
     * @return The interpolated value in [y,z].
     */
    public static int lerp(int x, int a, int b) {
        return Math.min((int) ((x - a) * (double) FEED_MAX_VALUE / (b - a)), FEED_MAX_VALUE) + FEED_SPECIAL_SHIFT;
    }
}