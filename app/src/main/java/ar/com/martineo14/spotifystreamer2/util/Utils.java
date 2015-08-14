package ar.com.martineo14.spotifystreamer2.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by sergiomartinpueyo on 8/13/15.
 */
public class Utils {


    // I used from here http://stackoverflow.com/questions/9027317/how-to-convert-milliseconds-to-hhmmss-format.
    public static String convertMillisToMinutes(int millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
