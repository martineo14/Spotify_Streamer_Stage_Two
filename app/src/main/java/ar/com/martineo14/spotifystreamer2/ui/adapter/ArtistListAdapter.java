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

package ar.com.martineo14.spotifystreamer2.ui.adapter;


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
import ar.com.martineo14.spotifystreamer2.data.model.ArtistModel;

/**
 * Created by sergiomartinpueyo on 6/27/15.
 */
public class ArtistListAdapter extends ArrayAdapter<ArtistModel> {

    private Context context;

    public ArtistListAdapter(Activity context, List<ArtistModel> artistModels) {
        super(context, 0, artistModels);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArtistModel artistModel = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.artist_line_item, parent, false);

        ImageView artist_image = (ImageView) rootView.findViewById(R.id.img_artist_line_item);
        if (artistModel.artistImageBig == null) {
            //http://www.the-music-shop.com/wp-content/uploads/2015/02/placeholder.png
            Picasso.with(context).load(R.drawable.placeholder_music).into(artist_image);
        } else {
            Picasso.with(context).load(artistModel.artistImageBig).into(artist_image);
        }
        TextView artist_name = (TextView) rootView.findViewById(R.id.name_artist_line_item);
        artist_name.setText(artistModel.artistName);

        return rootView;
    }
}



