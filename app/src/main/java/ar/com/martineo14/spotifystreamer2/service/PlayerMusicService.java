package ar.com.martineo14.spotifystreamer2.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by sergiomartinpueyo on 8/10/15.
 */
public class PlayerMusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {


    private static final int NOTIFY_ID = 1;
    //private List<MusicPlayerObserver> mObservers;
    private final IBinder playerBind = new MusicBinder();
    private MediaPlayer mMediaPlayer;
    private List<Track> mPlaylist;
    private Integer mPosition;

    private Boolean isRepeating;
    private Boolean isShuffling;
    private Boolean isPrepared;
    private Boolean isPaused;

    // Callback Methods______________________________________________
    @Override
    public void onCreate() {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return playerBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return false;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }


    // UTIL METHODS__________________________________________________
//    private Long getCurrentTrackId() {
//        return mPlaylist.get(mPosition).getTrackId();
//    }
//
//    private Long getCurrentAlbumId() {
//        return mPlaylist.get(mPosition).getAlbumId();
//    }


    // MEDIA PLAYER INTERFACE________________________________________

    public void play() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void next() {

    }

    public void previous() {

    }

    public void seekTo(int pos) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    // SERVICE INTERFACE PROVIDER_____________________________________

    /**
     * Interface through the component bound to this service can interact with it
     */
    public class MusicBinder extends Binder {
        public PlayerMusicService getService() {
            return PlayerMusicService.this;
        }
    }
}
