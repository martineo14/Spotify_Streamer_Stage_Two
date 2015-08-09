package ar.com.martineo14.spotifystreamer2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import ar.com.martineo14.spotifystreamer2.R;
import ar.com.martineo14.spotifystreamer2.ui.fragment.ArtistDetailActivityFragment;
import ar.com.martineo14.spotifystreamer2.ui.fragment.SearchArtistFragment;
import kaaes.spotify.webapi.android.models.Artist;


public class MainActivity extends ActionBarActivity implements SearchArtistFragment.SearchCallback {

    public static final String ARTIST_NAME = "artist_name";
    public static final String ARTIST_ID = "artist_id";
    private static final String ARTISTDETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The detail container view will be present only in the large-screen layouts
// (res/layout-sw600dp). If this view is present, then the activity should be
// in two-pane mode.
// In two-pane mode, show the detail view in this activity by
// adding or replacing the detail fragment using a
// fragment transaction.
//            if (savedInstanceState == null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.artist_detail_container, new ArtistDetailActivityFragment(), ARTISTDETAILFRAGMENT_TAG)
//                        .commit();
//}
//            getSupportActionBar().setElevation(0f);
        mTwoPane = findViewById(R.id.fragment_detail) != null;

//        ForecastFragment forecastFragment =  ((ForecastFragment)getSupportFragmentManager()
//                .findFragmentById(R.id.fragment_forecast));
//        forecastFragment.setUseTodayLayout(!mTwoPane);
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
    public void onItemSelected(Artist artist) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putString(ARTIST_ID, artist.id);
            args.putString(ARTIST_NAME, artist.name);
            ArtistDetailActivityFragment fragment = new ArtistDetailActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail, fragment, ARTISTDETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, ArtistDetailActivity.class);
            intent.putExtra(ARTIST_ID, artist.id);
            intent.putExtra(ARTIST_NAME, artist.name);
            startActivity(intent);
        }
    }
}
