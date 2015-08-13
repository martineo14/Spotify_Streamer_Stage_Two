package ar.com.martineo14.spotifystreamer2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergiomartinpueyo on 8/6/15.
 */
public class TrackModel implements Parcelable {
    public final Parcelable.Creator<TrackModel> CREATOR = new Parcelable.Creator<TrackModel>() {
        @Override
        public TrackModel createFromParcel(Parcel parcel) {
            return new TrackModel(parcel);
        }

        @Override
        public TrackModel[] newArray(int i) {
            return new TrackModel[i];
        }

    };
    public String artistId;
    public String artistName;
    public String artistAlbum;
    public String albumImage;
    public String trackId;
    public String trackName;
    public String trackPreview;
    public Integer trackPosition;

    public TrackModel(String artistId, String artistName, String artistAlbum, String albumImage,
                      String trackId, String trackName, String trackPreview, Integer trackPosition) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistAlbum = artistAlbum;
        this.albumImage = albumImage;
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackPreview = trackPreview;
        this.trackPosition = trackPosition;

    }

    private TrackModel(Parcel in) {
        artistId = in.readString();
        artistName = in.readString();
        artistAlbum = in.readString();
        albumImage = in.readString();
        trackId = in.readString();
        trackName = in.readString();
        trackPreview = in.readString();
        trackPosition = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return artistName + "--" + artistAlbum;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(artistId);
        parcel.writeString(artistName);
        parcel.writeString(artistAlbum);
        parcel.writeString(albumImage);
        parcel.writeString(trackId);
        parcel.writeString(trackName);
        parcel.writeString(trackPreview);
        parcel.writeInt(trackPosition);
    }
}
