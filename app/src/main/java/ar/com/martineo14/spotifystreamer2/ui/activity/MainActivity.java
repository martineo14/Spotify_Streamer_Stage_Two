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

package ar.com.martineo14.spotifystreamer2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import ar.com.martineo14.spotifystreamer2.R;
import ar.com.martineo14.spotifystreamer2.data.model.ArtistModel;
import ar.com.martineo14.spotifystreamer2.ui.fragment.ArtistDetailActivityFragment;
import ar.com.martineo14.spotifystreamer2.ui.fragment.SearchArtistFragment;
import ar.com.martineo14.spotifystreamer2.util.Constants;


public class MainActivity extends ActionBarActivity implements SearchArtistFragment.SearchCallback {

    private static final String ARTISTDETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTwoPane = findViewById(R.id.fragment_detail) != null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(ArtistModel artist) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putString(Constants.ARTIST_ID, artist.artistId);
            args.putString(Constants.ARTIST_NAME, artist.artistName);
            ArtistDetailActivityFragment fragment = new ArtistDetailActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail, fragment, ARTISTDETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, ArtistDetailActivity.class);
            intent.putExtra(Constants.ARTIST_ID, artist.artistId);
            intent.putExtra(Constants.ARTIST_NAME, artist.artistName);
            startActivity(intent);
        }
    }
}
