package be.ugent.zeus.hydra.domain.minerva;

import java8.util.Objects;

/**
 * A tutor is someone who can manage a {@link Course}.
 *
 * @author Niko Strijbol
 */
public final class Tutor {

    private final String name;

    public Tutor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(name, tutor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns a string containing the name of the tutor. This method is equivalent with calling {@link #getName()}.
     * Constructing a new Tutor instance with {@link #Tutor(String)} with the value returned by this method will result
     * in two instances that are equal (in terms of {@link #equals(Object)}).
     *
     * @return The name of the tutor.
     */
    @Override
    public String toString() {
        return name;
    }
}