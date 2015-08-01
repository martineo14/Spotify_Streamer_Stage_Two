package ar.com.martineo14.spotifystreamer2.ui.adapter;

/**
 * Created by sergiomartinpueyo on 7/13/15.
 */
//public class ArtistListAdapter extends RecyclerView.Adapter {
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.martineo14.spotifystreamer2.R;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by sergiomartinpueyo on 6/27/15.
 */
public class ArtistListAdapter extends ArrayAdapter<Artist> {

    private Context context;

    public ArtistListAdapter(Activity context, List<Artist> artistModels) {
        super(context, 0, artistModels);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Artist artistModel = getItem(position);
        Image image = new Image();
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.artist_line_item, parent, false);

        ImageView artist_image = (ImageView) rootView.findViewById(R.id.img_artist_line_item);
        //artist_image.setImageURI(artistModel.artistImage);
        List<Image> images = artistModel.images;
        if (images != null && images.size() > 0) {
            if (images.size() >= 2) {
                Picasso.with(context).load(artistModel.images.get(2).url).into(artist_image);
            } else {
                Picasso.with(context).load(artistModel.images.get(0).url).into(artist_image);
            }
        } else {
            //http://www.the-music-shop.com/wp-content/uploads/2015/02/placeholder.png
            Picasso.with(context).load(R.drawable.placeholder_music).into(artist_image);
        }
        //Picasso.with(context).load(image.url).into(artist_image);

        TextView artist_name = (TextView) rootView.findViewById(R.id.name_artist_line_item);
        artist_name.setText(artistModel.name);

        return rootView;
    }
}



