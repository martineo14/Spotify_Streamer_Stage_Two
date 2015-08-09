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
    ImageButton buttonPlay;
    ImageButton buttonPause;
    ImageButton buttonNext;
    ImageButton buttonPrevious;
    SeekBar seekBar;
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
        return rootView;
    }


    public void addListenerOnButton(View rootView) {

        buttonPlay = (ImageButton) rootView.findViewById(R.id.player_media_play);

        buttonPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String url = trackModel.trackPreview;
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
            }

        });

    }
}
