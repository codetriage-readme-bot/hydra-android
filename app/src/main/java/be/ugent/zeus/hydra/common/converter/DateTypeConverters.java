package be.ugent.zeus.hydra.common.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Converts for various date-related classes.
 *
 * @author Niko Strijbol
 */
public class DateTypeConverters {

    /**
     * The format of the result of {@link #fromOffsetDateTime(OffsetDateTime)} and the format expected by
     * {@link #toOffsetDateTime(String)}.
     */
    public static DateTimeFormatter OFFSET_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Converts a string representing a date in the format specified by {@link #OFFSET_FORMATTER}.
     *
     * @param sqlValue The string representing the value or {@code null}. The string must be in the format
     *                 specified by {@link #OFFSET_FORMATTER}. If not, the behaviour is undefined.
     *
     * @return The instance or {@code null} if the input was {@code null}.
     */
    @TypeConverter
    public static OffsetDateTime toOffsetDateTime(String sqlValue) {
        if (sqlValue == null) {
            return null;
        } else {
            return OffsetDateTime.parse(sqlValue, OFFSET_FORMATTER);
        }
    }

    /**
     * Converts a offset date time to a string in the format specified by {@link #OFFSET_FORMATTER}.
     *
     * @param dateTime The date time or {@code null}.
     *
     * @return The string or {@code null} if the input was {@code null}.
     */
    @TypeConverter
    public static String fromOffsetDateTime(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        } else {
            return dateTime.format(OFFSET_FORMATTER);
        }
    }

    @TypeConverter
    public static String fromInstant(Instant instant) {
        if (instant == null) {
            return null;
        } else {
            return instant.toString();
        }
    }

    @TypeConverter
    public static Instant toInstant(String value) {
        if (value == null) {
            return null;
        } else {
            return Instant.parse(value);
        }
    }

    /**
     * Adapter for Gson.
     */
    public static class GsonOffset extends TypeAdapter<OffsetDateTime> {

        @Override
        public void write(JsonWriter out, OffsetDateTime value) throws IOException {
            out.value(fromOffsetDateTime(value));
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return toOffsetDateTime(in.nextString());
        }
    }

    public static class GsonInstant extends TypeAdapter<Instant> {

        @Override
        public void write(JsonWriter out, Instant instant) throws IOException {
            out.value(fromInstant(instant));
        }

        @Override
        public Instant read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return toInstant(in.nextString());
        }
    }
}