package com.example.sophiah123.fbuinstagram;

/*public class HomeActivity extends AppCompatActivity {
    private static String imagePath = "/storage/emulated/0/DCIM/Camera/IMG_20180710_112710.jpg";
    private EditText descriptionInput;
    //private Button pictureButton;
    private Button createButton;
    //private Button refreshButton;
    private Button logoutButton;
    private Button cameraButton;
        public final String APP_TAG = "Parstagram";
        public final int SOME_WIDTH = 360;
        public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
        public String photoFileName = "photo.jpg";
        File photoFile;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            final FragmentManager fragmentManager = getSupportFragmentManager();
            // define your fragments here
            final Fragment feedFragment = new FeedFragment();
            final Fragment captureFragment = new CaptureFragment();
            final Fragment profileFragment = new ProfileFragment();
            descriptionInput = findViewById(R.id.etDescription);
            createButton = findViewById(R.id.btCreate);
//        feedButton = findViewById(R.id.btFeed);
            logoutButton = findViewById(R.id.btLogout);
            cameraButton = findViewById(R.id.btCamera);
            if (Build.VERSION.SDK_INT >= 23) {
                int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_feed:
                            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                            fragmentTransaction1.replace(R.id.flContainer, feedFragment).commit();
                            return true;
                        case R.id.action_camera:
                            FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                            fragmentTransaction2.replace(R.id.flContainer, captureFragment).commit();
                            return true;
                        case R.id.action_profile:
                            FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                            fragmentTransaction3.replace(R.id.flContainer, profileFragment).commit();
                            return true;
                    }
                    return false;
                }
            });
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createNewPost();
                }
            });
//        feedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, FeedActivity.class);
//                startActivity(intent);
//            }
//        });
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logoutUser();
                }
            });
            cameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLaunchCamera(view);
                }
            });
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
                    } else {
                        Log.e("HomeActivity", "Create post failure");
                        e.printStackTrace();
                    }
                }
            });
        }
        private void loadTopPosts() {
            final Post.Query postsQuery = new Post.Query();
            postsQuery.getTop().withUser();
            postsQuery.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> objects, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < objects.size(); i++) {
                            Log.d("HomeActivity", "Post[" + i + "] = "
                                    + objects.get(i).getDescription()
                                    + "\nusername = " + objects.get(i).getUser().getUsername());
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
        private void createNewPost() {
            final String description = descriptionInput.getText().toString();
            final ParseUser user = ParseUser.getCurrentUser();
            final File file = new File(imagePath);
            final ParseFile parseFile = new ParseFile(file);
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
        private void logoutUser() {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
            Log.d("HomeActivity", "Logout successful");
            final Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        public void onLaunchCamera(View view) {
            // create Intent to take a picture and return control to the calling application
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Create a File reference to access to future access
            photoFile = getPhotoFileUri(photoFileName);
            // wrap File object into a content provider
            // required for API >= 24
            // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
            Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "me.amyhgu.parstagram.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        }
        // Returns the File for a photo stored on disk given the fileName
        public File getPhotoFileUri(String fileName) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }
            // Return the file target for the photo based on filename
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
            return file;
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // by this point we have the camera photo on disk
                    Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    // RESIZE BITMAP, see section below
                    // See BitmapScaler.java: https://gist.github.com/nesquena/3885707fd3773c09f1bb
                    Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(takenImage, SOME_WIDTH);
                    // Configure byte output stream
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    // Compress the image further
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                    // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
                    File resizedUri = getPhotoFileUri(photoFileName + "_resized");
                    imagePath = resizedUri.getPath();
                    File resizedFile = new File(imagePath);
                    Log.d("CameraActivity", "resizing successful");
                    try {
                        resizedFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(resizedFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // Write the bytes of the bitmap to file
                    try {
                        fos.write(bytes.toByteArray());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("CameraActivity", "loading successful");
                    // Load the taken image into a preview
                    ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
                    ivPreview.setImageBitmap(resizedBitmap);
                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    */


//SO FAR FINAL VERSION
/*
public class HomeActivity extends AppCompatActivity {
    private Button logoutButton;
    FragmentTransaction fragmentTransaction;
    Fragment fragment1 = new FeedFragment();
    Fragment fragment2 = new CaptureFragment();
    Fragment fragment3 = new ProfileFragment();
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logoutButton = findViewById(R.id.btLogout);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        // handle navigation selection
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        fragmentTransaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.action_feed:
                                fragmentTransaction.replace(R.id.flContainer, fragment1).commit();
                                return true;
                            case R.id.action_camera:
                                fragmentTransaction.replace(R.id.flContainer, fragment2).commit();
                                onLaunchCamera();
                                return true;
                            case R.id.action_profile:
                                fragmentTransaction.replace(R.id.flContainer, fragment3).commit();
                                return true;
                        }
                        return false;
                    }
                });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
    }
    // Returns the File for a photo stored on disk given the fileName
    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);
        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }
        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                String imagePath = photoFile.getAbsolutePath();
                Bitmap rawTakenImage = BitmapFactory.decodeFile(imagePath);
                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, 400);
                ((CaptureFragment) fragment2).ivPhoto.setImageBitmap(resizedBitmap);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void logoutUser() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        Log.d("HomeActivity", "Logout successful");
        final Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }*/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.File;


public class HomeActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener,
        CaptureFragment.Callback {


    FragmentTransaction fragmentTransaction;

    Fragment fragment1 = new FeedFragment();
    Fragment fragment2 = new CaptureFragment();
    Fragment fragment3 = new ProfileFragment();

    private BottomNavigationView bottomNavigationView;

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment1).commit();

        // handle navigation selection
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        fragmentTransaction = fragmentManager.beginTransaction();

                        switch (item.getItemId()) {
                            case R.id.action_feed:
                                fragmentTransaction.replace(R.id.flContainer, fragment1).commit();
                                return true;
                            case R.id.action_camera:
                                fragmentTransaction.replace(R.id.flContainer, fragment2).commit();
                                onLaunchCamera();
                                return true;
                            case R.id.action_profile:
                                fragmentTransaction.replace(R.id.flContainer, fragment3).commit();
                                return true;
                        }

                        return false;
                    }
                });

    }

    @Override
    public void onPostCreated() {
        // this one line of code below...
        // navigates to the feed fragment.
        bottomNavigationView.setSelectedItemId(R.id.action_feed);
    }

    /* public void onClickToSettings(View view) {
       Intent i = new Intent(HomeActivity.this, SettingsFragment.class);
       startActivity(i);
   }
   */

    //----------CAMERA-----------

    // Returns the File for a photo stored on disk given the fileName
    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                String imagePath = photoFile.getAbsolutePath();
                final File imageFile = new File(imagePath);

                ((CaptureFragment) fragment2).setSelectedImage(imageFile);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void logoutInteraction() {
        ParseUser.logOut();
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
