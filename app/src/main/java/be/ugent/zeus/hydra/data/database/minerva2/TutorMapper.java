package be.ugent.zeus.hydra.data.database.minerva2;

import be.ugent.zeus.hydra.domain.minerva.Tutor;

/**
 * @author Niko Strijbol
 */
public class TutorMapper {

    public String asString(Tutor tutor) {
        return tutor.getName();
    }

    public Tutor asTutor(String name) {
        return new Tutor(name);
    }
}