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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.com.martineo14.spotifystreamer2.R;
import ar.com.martineo14.spotifystreamer2.data.model.ArtistModel;
import ar.com.martineo14.spotifystreamer2.ui.adapter.ArtistListAdapter;
import ar.com.martineo14.spotifystreamer2.util.Constants;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static ar.com.martineo14.spotifystreamer2.util.Utils.getBigImageFromArtist;
import static ar.com.martineo14.spotifystreamer2.util.Utils.getSmallImageFromArtist;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchArtistFragment extends Fragment {

    List<Artist> artistResult;
    ArrayList<ArtistModel> artistResultModel;
    private String artistNameSearch;
    private ArtistListAdapter artistAdapter;
    private ListView listView;
    private SearchView searchView;
    private int mPosition = ListView.INVALID_POSITION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list_artist);
        searchView = (SearchView) view.findViewById(R.id.search_artist);

        if (savedInstanceState != null) {
            artistNameSearch = savedInstanceState.getString(Constants.ARTIST_NAME);
            artistResultModel = savedInstanceState.getParcelableArrayList(Constants.ARTIST_MODEL_RESULT);
            if (artistResultModel != null) {
                artistAdapter = new ArtistListAdapter(getActivity(), artistResultModel);
                listView.setAdapter(artistAdapter);
            } else {
                searchView.setQuery(artistNameSearch, false);
                updateArtistList();
            }
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                artistNameSearch = query;
                updateArtistList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ArtistModel artist = artistAdapter.getItem(position);
                ((SearchCallback) getActivity())
                        .onItemSelected(artist);
                mPosition = position;
            }
        });
    }

    private void updateArtistList() {
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        // I used this post of the forum to the call of the API.
        // https://discussions.udacity.com/t/spotify-api-examples/21933
        spotify.searchArtists(artistNameSearch, new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager artistsPager, Response response) {
                if (artistsPager.artists.items.size() == 0) {
                    if (artistAdapter != null) {
                        artistAdapter.clear();
                    }
                    Toast.makeText(getActivity(), getString(R.string.msg_no_artist_found),
                            Toast.LENGTH_LONG).show();
                } else {
                    artistResult = artistsPager.artists.items;
                    artistResultModel = new ArrayList<ArtistModel>();
                    for (int i = 0; i < artistsPager.artists.items.size(); i++) {
                        Artist artist = artistsPager.artists.items.get(i);
                        ArtistModel artistModel = new ArtistModel(artist.id, artist.name,
                                getBigImageFromArtist(artist), getSmallImageFromArtist(artist));
                        artistResultModel.add(artistModel);
                    }
                    artistAdapter = new ArtistListAdapter(getActivity(), artistResultModel);
                    listView.setAdapter(artistAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (artistAdapter != null) {
                    artistAdapter.clear();
                }
                Toast.makeText(getActivity(), getString(R.string.msg_error_default),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.ARTIST_MODEL_RESULT, artistResultModel);
        outState.putString(Constants.ARTIST_NAME, artistNameSearch);
        super.onSaveInstanceState(outState);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface SearchCallback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        void onItemSelected(ArtistModel artist);
    }
}
