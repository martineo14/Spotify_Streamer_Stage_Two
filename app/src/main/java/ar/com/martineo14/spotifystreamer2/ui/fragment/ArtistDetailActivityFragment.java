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

package ar.com.martineo14.spotifystreamer2.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import ar.com.martineo14.spotifystreamer2.R;
import ar.com.martineo14.spotifystreamer2.data.model.TrackModel;
import ar.com.martineo14.spotifystreamer2.ui.adapter.ArtistTracksListAdapter;
import ar.com.martineo14.spotifystreamer2.util.Constants;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistDetailActivityFragment extends Fragment {

    private static final String LOG_TAG = ArtistDetailActivityFragment.class.getSimpleName();
    public static List<Track> tracksResult;
    public static String mArtistIDStr;
    public static String mArtistNameSrt;
    private ArtistTracksListAdapter tracksListAdapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Constants.ARTIST_NAME)) {
            mArtistIDStr = intent.getStringExtra(Constants.ARTIST_ID);
            mArtistNameSrt = intent.getStringExtra(Constants.ARTIST_NAME);
            listView = (ListView) rootView.findViewById(R.id.list_artist_top_ten);
            ArtistTopTenTask artistTopTenTask = new ArtistTopTenTask();
            artistTopTenTask.execute(mArtistIDStr);
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            mArtistIDStr = bundle.getString(Constants.ARTIST_ID);
            mArtistNameSrt = bundle.getString(Constants.ARTIST_NAME);
            listView = (ListView) rootView.findViewById(R.id.list_artist_top_ten);
            ArtistTopTenTask artistTopTenTask = new ArtistTopTenTask();
            artistTopTenTask.execute(mArtistIDStr);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (listView != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Track track = tracksListAdapter.getItem(position);
                    if (track != null) {
                        TrackModel trackModel = new TrackModel(mArtistIDStr, mArtistNameSrt, track.album.name,
                                track.album.images.get(0).url, track.id, track.name, track.preview_url, position);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.TRACK_MODEL, trackModel);
                        TrackPlayerActivityFragment playerActivityFragment = new TrackPlayerActivityFragment();
                        playerActivityFragment.setArguments(bundle);
                        playerActivityFragment.show(getFragmentManager(), "dialog");
                    }
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public class ArtistTopTenTask extends AsyncTask<String, Void, Tracks> {

        public static final String COUNTRY_CODE = "AR";


        @Override
        protected Tracks doInBackground(String... params) {
            try {
                Tracks results;
                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                //https://github.com/kaaes/spotify-web-api-android/issues/82
                Map<String, Object> options = new Hashtable<>();
                options.put("country", COUNTRY_CODE);
                results = spotify.getArtistTopTrack(params[0], options);
                return results;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Tracks result) {
            if (result.tracks.size() == 0) {
                if (tracksListAdapter != null) {
                    tracksListAdapter.clear();
                }
                Toast.makeText(getActivity(), getString(R.string.msg_artist_no_top_tracks),
                        Toast.LENGTH_LONG).show();
            } else {
                if (result.tracks != null) {
                    tracksResult = result.tracks;
                    tracksListAdapter = new ArtistTracksListAdapter(getActivity(), tracksResult);
                    listView.setAdapter(tracksListAdapter);
                }
            }
        }
    }
}
