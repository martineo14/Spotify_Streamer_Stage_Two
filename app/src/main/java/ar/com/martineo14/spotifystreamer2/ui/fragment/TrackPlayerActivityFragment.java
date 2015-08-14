package ar.com.martineo14.spotifystreamer2.ui.fragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import ar.com.martineo14.spotifystreamer2.R;
import ar.com.martineo14.spotifystreamer2.data.model.TrackModel;
import kaaes.spotify.webapi.android.models.Track;

import static ar.com.martineo14.spotifystreamer2.util.Utils.convertMillisToMinutes;


/**
 * A placeholder fragment containing a simple view.
 */
public class TrackPlayerActivityFragment extends DialogFragment {

    TrackModel trackModel;
    TextView artistNameTextView;
    TextView artistAlbumNameTextView;
    ImageView trackAlbumImage;
    TextView trackNameTextView;
    ImageButton buttonPlayPause;
    ImageButton buttonNext;
    ImageButton buttonPrevious;
    SeekBar seekBar;
    TextView trackDuration;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Integer mActualPosition;
    private Handler mHandler = new Handler();
    private Runnable updateSeekbar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition / 1000);
                trackDuration.setText(convertMillisToMinutes(mCurrentPosition));
            }
            mHandler.postDelayed(this, 1000);
        }
    };

    public TrackPlayerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_player, container, false);

        artistNameTextView = (TextView) rootView.findViewById(R.id.player_artist_name);
        artistAlbumNameTextView = (TextView) rootView.findViewById(R.id.player_album_name);
        trackAlbumImage = (ImageView) rootView.findViewById(R.id.player_album_artwork);
        trackNameTextView = (TextView) rootView.findViewById(R.id.player_track_name);
        seekBar = (SeekBar) rootView.findViewById(R.id.player_seekbar);
        trackDuration = (TextView) rootView.findViewById(R.id.player_track_duration);
        addListenerOnButton(rootView);
        Bundle bundle = getArguments();
        if (savedInstanceState == null) {
            trackModel = bundle.getParcelable("trackModel");
        } else {
            trackModel = savedInstanceState.getParcelable("trackModel");
        }

        displayTrack(trackModel);
        return rootView;
    }

    public void displayTrack(TrackModel trackModel) {
        buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
        mActualPosition = trackModel.trackPosition;
        artistNameTextView.setText(trackModel.artistName);
        artistAlbumNameTextView.setText(trackModel.artistAlbum);
        trackNameTextView.setText(trackModel.trackName);
        Picasso.with(getActivity()).load(trackModel.albumImage)
                .placeholder(R.drawable.placeholder_music).into(trackAlbumImage);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String url = trackModel.trackPreview;
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    playerPlayPause();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addListenerOnButton(View rootView) {

        buttonPlayPause = (ImageButton) rootView.findViewById(R.id.player_media_play_pause);
        buttonPlayPause.setEnabled(false);

        buttonPlayPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                playerPlayPause();
            }
        });

        buttonNext = (ImageButton) rootView.findViewById(R.id.player_media_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mediaPlayer.reset();
                if (mActualPosition < ArtistDetailActivityFragment.tracksResult.size() - 1) {
                    displayTrack(getTrackModel(mActualPosition + 1));
                } else {
                    displayTrack(getTrackModel(0));
                }
            }

        });

        buttonPrevious = (ImageButton) rootView.findViewById(R.id.player_media_previous);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mediaPlayer.reset();
                if (mActualPosition == 0) {
                    displayTrack(getTrackModel(ArtistDetailActivityFragment.tracksResult.size() - 1));
                } else {
                    displayTrack(getTrackModel(mActualPosition - 1));
                }

            }

        });

    }

    private void playerPlayPause() {
        if (!mediaPlayer.isPlaying()) {
            //Play
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
            buttonPlayPause.setEnabled(true);
            mediaPlayer.start();
            //seekBar.setMax(mediaPlayer.getDuration());
            mHandler.postDelayed(updateSeekbar, 1000);
        } else {
            //Pause
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
            mediaPlayer.pause();
        }
    }

    public TrackModel getTrackModel(Integer position) {
        TrackModel trackModel = null;
        Track track = ArtistDetailActivityFragment.tracksResult.get(position);
        if (track != null) {
            trackModel = new TrackModel(ArtistDetailActivityFragment.mArtistIDStr,
                    ArtistDetailActivityFragment.mArtistNameSrt, track.album.name,
                    track.album.images.get(0).url, track.id, track.name, track.preview_url, position);
        }
        return trackModel;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("trackModel", trackModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
