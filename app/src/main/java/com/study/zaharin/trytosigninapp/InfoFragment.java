package com.study.zaharin.trytosigninapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * This is Content type fragment. Here information about user is displayed
 */
public class InfoFragment extends Fragment {

    public static final String EXTRAS = "extrs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        GoogleSignInAccount account = getArguments().getParcelable(EXTRAS);

        //Was downloaded from open source resource
        CircleImageView iv = (CircleImageView) v.findViewById(R.id.user_avatar);
        assert account != null;
        Uri uri = account.getPhotoUrl();
        assert uri != null;
        new LoadImageTask(iv).execute(uri.toString());

        ((TextView) v.findViewById(R.id.name)).setText(account.getDisplayName());
        ((TextView) v.findViewById(R.id.email)).setText(account.getEmail());

        return v;
    }

    class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        CircleImageView image;
        public LoadImageTask(CircleImageView imageView) {
            image = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                InputStream is = new URL(urls[0]).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }
    }

}
