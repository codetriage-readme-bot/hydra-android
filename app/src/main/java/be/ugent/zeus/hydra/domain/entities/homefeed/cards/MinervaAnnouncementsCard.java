package be.ugent.zeus.hydra.domain.entities.homefeed.cards;

import be.ugent.zeus.hydra.domain.entities.homefeed.HomeCard;
import be.ugent.zeus.hydra.domain.entities.homefeed.PriorityUtils;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import java8.util.Objects;
import org.threeten.bp.Duration;
import org.threeten.bp.ZonedDateTime;

import java.util.List;

/**
 * Home card for {@link Announcement}.
 *
 * @author Niko Strijbol
 * @author feliciaan
 */
public class MinervaAnnouncementsCard extends HomeCard {

    private final List<Announcement> announcement;
    private final Course course;

    public MinervaAnnouncementsCard(List<Announcement> announcement, Course course) {
        this.announcement = announcement;
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public List<Announcement> getAnnouncements() {
        return announcement;
    }

    @Override
    public int getPriority() {
        ZonedDateTime date = getAnnouncements().get(0).getLastEditedAt();
        Duration duration = Duration.between(date, ZonedDateTime.now());
        return PriorityUtils.lerp((int) duration.toHours(), 0, 1488);
    }

    @Override
    @HomeCard.CardType
    public int getCardType() {
        return CardType.MINERVA_ANNOUNCEMENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinervaAnnouncementsCard that = (MinervaAnnouncementsCard) o;
        return Objects.equals(announcement, that.announcement) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return java8.util.Objects.hash(announcement, course);
    }
}