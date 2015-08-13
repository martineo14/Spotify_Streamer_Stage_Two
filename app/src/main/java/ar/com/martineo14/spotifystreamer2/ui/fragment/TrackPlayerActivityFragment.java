package ar.com.martineo14.spotifystreamer2.ui.fragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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
    Boolean IsPlaying = false;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Integer mActualPosition;


    public TrackPlayerActivityFragment() {
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
        addListenerOnButton(rootView);
        Bundle bundle = getArguments();
        trackModel = bundle.getParcelable("trackModel");
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
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addListenerOnButton(View rootView) {

        buttonPlayPause = (ImageButton) rootView.findViewById(R.id.player_media_play_pause);

        buttonPlayPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!mediaPlayer.isPlaying()) {
                    //Play
                    buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                    mediaPlayer.start();
                } else {
                    //Pause
                    buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
                    mediaPlayer.pause();
                }
                seekBar.setMax(mediaPlayer.getDuration());
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
    public void onStop() {
        super.onStop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
