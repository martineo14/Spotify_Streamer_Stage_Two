/*
 * Copyright 2015 Sergio Martin Pueyo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    public String albumImageBig;
    public String albumImageSmall;
    public String trackId;
    public String trackName;
    public String trackPreview;
    public Integer trackPosition;

    public TrackModel(String artistId, String artistName, String artistAlbum, String albumImageBig,
                      String albumImageSmall, String trackId, String trackName, String trackPreview,
                      Integer trackPosition) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistAlbum = artistAlbum;
        this.albumImageBig = albumImageBig;
        this.albumImageSmall = albumImageSmall;
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackPreview = trackPreview;
        this.trackPosition = trackPosition;

    }

    private TrackModel(Parcel in) {
        artistId = in.readString();
        artistName = in.readString();
        artistAlbum = in.readString();
        albumImageBig = in.readString();
        albumImageSmall = in.readString();
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
        parcel.writeString(albumImageBig);
        parcel.writeString(albumImageSmall);
        parcel.writeString(trackId);
        parcel.writeString(trackName);
        parcel.writeString(trackPreview);
        parcel.writeInt(trackPosition);
    }
}
