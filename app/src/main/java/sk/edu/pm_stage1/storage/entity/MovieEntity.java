package sk.edu.pm_stage1.storage.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class MovieEntity implements Parcelable {
  private static final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

  private String title;
  private Date releaseDate;
  private String imagePath;
  private String description;
  private double averageVote;

  public MovieEntity() {
    super();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(imagePath);
    dest.writeString(description);
    dest.writeValue(averageVote);
    dest.writeValue(releaseDate);
  }

  private MovieEntity(Parcel in) {
    title = in.readString();
    imagePath = in.readString();
    description = in.readString();
    averageVote = (Double)in.readValue(Double.class.getClassLoader());
    releaseDate = (Date)in.readValue(Date.class.getClassLoader());
  }

  public static final Parcelable.Creator<MovieEntity> CREATOR =
      new Parcelable.Creator<MovieEntity>() {

    public MovieEntity createFromParcel(Parcel source) {
      return new MovieEntity(source);
    }

    public MovieEntity[] newArray(int size) {
      return new MovieEntity[size];
    }
  };

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }
  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getImageFullPath() {
    return TMDB_POSTER_BASE_URL + imagePath;
  }
  public String getImagePath() {
    return imagePath;
  }
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public double getAverageVote() {
    return averageVote;
  }
  public void setAverageVote(double averageVote) {
    this.averageVote = averageVote;
  }
}
