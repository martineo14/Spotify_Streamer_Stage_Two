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
import ar.com.martineo14.spotifystreamer2.util.Constants;
import kaaes.spotify.webapi.android.models.Track;

import static ar.com.martineo14.spotifystreamer2.util.Utils.convertMillisToMinutes;
import static ar.com.martineo14.spotifystreamer2.util.Utils.getBigImageFromTrack;
import static ar.com.martineo14.spotifystreamer2.util.Utils.getSmallImageFromTrack;


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
    MediaPlayer mediaPlayer;
    Integer mActualPosition;
    Integer mActualTrackPosition = 0;

    //http://stackoverflow.com/questions/21620348/how-to-create-media-player-in-android-with-seek-bar-and-button
    private Handler mHandler = new Handler();
    private Runnable updateSeekbar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                mActualTrackPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mActualTrackPosition / 1000);
                trackDuration.setText(convertMillisToMinutes(mActualTrackPosition));
            }
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        createMediaPlayer();
        if (savedInstanceState == null) {
            trackModel = bundle.getParcelable(Constants.TRACK_MODEL);
        } else {
            trackModel = savedInstanceState.getParcelable(Constants.TRACK_MODEL);
            mActualTrackPosition = savedInstanceState.getInt(Constants.ACTUAL_POSITION);
        }

        displayTrack(trackModel);
        return rootView;
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
                    int nextPosition = mActualPosition + 1;
                    displayTrack(getTrackModel(nextPosition));
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
                    int prevPosition = mActualPosition - 1;
                    displayTrack(getTrackModel(prevPosition));
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

    }

    private void playerPlayPause() {

        if (mediaPlayer == null) {
            createMediaPlayer();
            displayTrack(trackModel);
        }
        if (!mediaPlayer.isPlaying()) {
            //Play
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
            buttonPlayPause.setEnabled(true);
            mediaPlayer.start();
            if (mActualTrackPosition > 0) {
                mediaPlayer.seekTo(mActualTrackPosition);
            }
            mHandler.postDelayed(updateSeekbar, 1000);
        } else {
            //Pause
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
            mediaPlayer.pause();
        }
    }

    public TrackModel getTrackModel(Integer position) {
        TrackModel track_Model = null;
        Track track = ArtistDetailActivityFragment.tracksResult.get(position);
        if (track != null) {
            track_Model = new TrackModel(ArtistDetailActivityFragment.mArtistIDStr,
                    ArtistDetailActivityFragment.mArtistNameSrt, track.album.name,
                    getBigImageFromTrack(track), getSmallImageFromTrack(track), track.id,
                    track.name, track.preview_url, position);
        }
        return track_Model;
    }


    public void createMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void displayTrack(TrackModel trackModel) {
        this.trackModel = trackModel;
        buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
        mActualPosition = trackModel.trackPosition;
        artistNameTextView.setText(trackModel.artistName);
        artistAlbumNameTextView.setText(trackModel.artistAlbum);
        trackNameTextView.setText(trackModel.trackName);
        Picasso.with(getActivity()).load(trackModel.albumImageBig)
                .placeholder(R.drawable.placeholder_music).into(trackAlbumImage);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.TRACK_MODEL, trackModel);
        outState.putInt(Constants.ACTUAL_POSITION, mActualTrackPosition);
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
