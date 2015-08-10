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
    MediaPlayer mediaPlayer;
    Integer PlayerState = 0;
    public TrackPlayerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_player, container, false);
        Bundle bundle = getArguments();
        trackModel = bundle.getParcelable("trackModel");
        artistNameTextView = (TextView) rootView.findViewById(R.id.player_artist_name);
        artistAlbumNameTextView = (TextView) rootView.findViewById(R.id.player_album_name);
        trackAlbumImage = (ImageView) rootView.findViewById(R.id.player_album_artwork);
        trackNameTextView = (TextView) rootView.findViewById(R.id.player_track_name);
        seekBar = (SeekBar) rootView.findViewById(R.id.player_seekbar);
        artistNameTextView.setText(trackModel.artistName);
        artistAlbumNameTextView.setText(trackModel.artistAlbum);
        trackNameTextView.setText(trackModel.trackName);
        Picasso.with(getActivity()).load(trackModel.albumImage)
                .placeholder(R.drawable.placeholder_music).into(trackAlbumImage);
        addListenerOnButton(rootView);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String url = trackModel.trackPreview;
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }


    public void addListenerOnButton(View rootView) {

        buttonPlayPause = (ImageButton) rootView.findViewById(R.id.player_media_play_pause);

        buttonPlayPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (PlayerState == 0 || PlayerState == 2) {
                    //Play
                    PlayerState = 1;
                    buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                    mediaPlayer.start();
                } else if (PlayerState == 1) {
                    //Pause
                    PlayerState = 2;
                    buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
                    mediaPlayer.pause();
                }
                seekBar.setMax(mediaPlayer.getDuration());
            }

        });

        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }

        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }

        });

    }
}
