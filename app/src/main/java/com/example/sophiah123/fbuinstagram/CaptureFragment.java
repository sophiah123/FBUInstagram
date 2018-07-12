package com.example.sophiah123.fbuinstagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sophiah123.fbuinstagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class CaptureFragment extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.

    ImageView ivPhoto;
    Button btCreate;
    EditText etCaption;

    /**
     * A file that points to the image the user selected to upload.
     */
    private File selectedImageFile;

    interface Callback {

        /**
         * To be called when a post has been successfully created.
         */
        void onPostCreated();
    }

    /**
     * This is what we use to communicate upwards to the activity.
     */
    private Callback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Callback) {
            callback = (Callback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v = inflater.inflate(R.layout.fragment_capture, parent, false);
        ivPhoto = v.findViewById(R.id.ivPhoto);
        btCreate = v.findViewById(R.id.btCreate);
        etCaption = v.findViewById(R.id.etCaption);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = etCaption.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                // Upload our image to parse
                // then when the image is successfully uploaded
                // we create our post!
                final ParseFile parseFile = new ParseFile(selectedImageFile);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            createPost(description, parseFile, user);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        return v;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    public void setSelectedImage(File imageFile) {
        Bitmap rawTakenImage = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, 400);
        ivPhoto.setImageBitmap(resizedBitmap);

        selectedImageFile = imageFile;
    }

    private void createPost(String description, ParseFile imageFile, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create post success");
                    callback.onPostCreated();
                } else {
                    Log.e("HomeActivity", "Create post failure");
                    e.printStackTrace();
                }
            }
        });
    }
}

