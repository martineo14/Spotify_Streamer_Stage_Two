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
