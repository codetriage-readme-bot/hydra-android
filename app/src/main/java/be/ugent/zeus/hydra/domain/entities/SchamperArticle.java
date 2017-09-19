package be.ugent.zeus.hydra.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

import be.ugent.zeus.hydra.data.gson.ZonedThreeTenAdapter;
import be.ugent.zeus.hydra.utils.DateUtils;
import be.ugent.zeus.hydra.utils.TtbUtils;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

import java.io.Serializable;

/**
 * TODO: this still depends on Serializable and more importantly Parcelable. Must we stop this?
 *
 * Created by feliciaan on 16/06/16.
 */
public final class SchamperArticle implements Serializable, Parcelable {

    private String title;
    private String link;
    @JsonAdapter(ZonedThreeTenAdapter.class)
    @SerializedName("pub_date")
    private ZonedDateTime pubDate;
    private String author;
    private String body;
    private String image;
    private String category;
    private String intro;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public ZonedDateTime getPubDate() {
        return pubDate;
    }

    public LocalDateTime getLocalPubDate() {
        return DateUtils.toLocalDateTime(getPubDate());
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public String getCategory() {
        return category;
    }

    public String getIntro() {
        return intro;
    }

    public String getImage() {
        return image;
    }

    public String getLargeImage() {
        if(getImage() != null) {
            return getLargeImage(getImage());
        } else {
            return null;
        }
    }

    public static String getLargeImage(String url) {
        return url.replace("/regulier/", "/preview/");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeLong(TtbUtils.serialize(this.pubDate));
        dest.writeString(this.author);
        dest.writeString(this.body);
        dest.writeString(this.image);
        dest.writeString(this.category);
        dest.writeString(this.intro);
    }

    public SchamperArticle() {
    }

    private SchamperArticle(Parcel in) {
        this.title = in.readString();
        this.link = in.readString();
        this.pubDate = TtbUtils.unserialize(in.readLong());
        this.author = in.readString();
        this.body = in.readString();
        this.image = in.readString();
        this.category = in.readString();
        this.intro = in.readString();
    }

    public static final Creator<SchamperArticle> CREATOR = new Creator<SchamperArticle>() {
        @Override
        public SchamperArticle createFromParcel(Parcel source) {
            return new SchamperArticle(source);
        }

        @Override
        public SchamperArticle[] newArray(int size) {
            return new SchamperArticle[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchamperArticle article = (SchamperArticle) o;
        return java8.util.Objects.equals(link, article.link) &&
                java8.util.Objects.equals(pubDate, article.pubDate);
    }

    @Override
    public int hashCode() {
        return java8.util.Objects.hash(link, pubDate);
    }
}