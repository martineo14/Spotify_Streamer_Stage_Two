package ar.com.martineo14.spotifystreamer2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergiomartinpueyo on 8/6/15.
 */
public class ArtistModel implements Parcelable {

    public final Parcelable.Creator<ArtistModel> CREATOR = new Parcelable.Creator<ArtistModel>() {
        @Override
        public ArtistModel createFromParcel(Parcel parcel) {
            return new ArtistModel(parcel);
        }

        @Override
        public ArtistModel[] newArray(int i) {
            return new ArtistModel[i];
        }

    };
    String artistId;
    String artistName;
    String artistAlbum;

    public ArtistModel(String artistId, String artistName, String artistAlbum) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistAlbum = artistAlbum;
    }

    private ArtistModel(Parcel in) {
        artistId = in.readString();
        artistName = in.readString();
        artistAlbum = in.readString();
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
    }
}
