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

package ar.com.martineo14.spotifystreamer2.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by sergiomartinpueyo on 8/13/15.
 */
public class Utils {


    // I used from here http://stackoverflow.com/questions/9027317/how-to-convert-milliseconds-to-hhmmss-format.
    public static String convertMillisToMinutes(int millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }


    public static String getSmallImageFromTrack(Track track) {
        List<Image> images = track.album.images;
        if (images == null || images.size() <= 0) {
            return null;
        }
        if (images.size() == 3) {
            return track.album.images.get(2).url;
        } else {
            return track.album.images.get(1).url;
        }
    }

    public static String getBigImageFromTrack(Track track) {
        List<Image> images = track.album.images;
        if (images == null || images.size() <= 0) {
            return null;
        }
        return track.album.images.get(0).url;

    }

    public static String getSmallImageFromArtist(Artist artist) {
        List<Image> images = artist.images;
        if (images == null || images.size() <= 0) {
            return null;
        }
        if (images.size() == 3) {
            return artist.images.get(2).url;
        } else {
            return artist.images.get(1).url;
        }
    }

    public static String getBigImageFromArtist(Artist artist) {
        List<Image> images = artist.images;
        if (images == null || images.size() <= 0) {
            return null;
        }
        return artist.images.get(0).url;

    }
}