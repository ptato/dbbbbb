package com.ptato.dbbbbb.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ptato.dbbbbb.R;

public class AppSettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_fragment_container, new AppSettingsFragment())
                .commit();
    }

    public static class AppSettingsFragment extends PreferenceFragmentCompat
    {
        private final static int PICK_FILE_REQUEST_CODE = 100;
        private final static String FILE_STORAGE_FOLDER_PREFERENCE_KEY = "pref_file_storage_folder";

        private Preference fileStorageFolder = null;

        @Override
        public void onCreatePreferences(Bundle bundle, String s)
        {
            setPreferencesFromResource(R.xml.preferences, s);

            fileStorageFolder = findPreference(FILE_STORAGE_FOLDER_PREFERENCE_KEY);
            updateFileStorageFolderSummary();
            fileStorageFolder.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                    {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
                        return true;
                    }

                    View view = getView();
                    if (view != null)
                    {
                        Snackbar.make(getView(), "Config option not supported on this Android version",
                                Snackbar.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
        }

        private void updateFileStorageFolderSummary()
        {
            Activity activity = getActivity();
            if (activity != null)
            {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                String uriValue = preferences.getString(FILE_STORAGE_FOLDER_PREFERENCE_KEY, null);
                if (uriValue != null)
                {
                    Uri uri = Uri.parse(uriValue);
                    fileStorageFolder.setSummary("Downloaded files will be stored on:\n"
                            + "\t Drive: " + uri.getHost() + "\n"
                            + "\t Folder: " + uri.getPath());
                } else
                {
                    fileStorageFolder.setSummary("Downloaded files will be stored on the default directory.");
                }
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            Activity activity = getActivity();
            if (requestCode == PICK_FILE_REQUEST_CODE && data != null && activity != null
                && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            {
                Uri folderUri = data.getData();

                if (folderUri != null)
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(FILE_STORAGE_FOLDER_PREFERENCE_KEY, folderUri.toString());
                    editor.apply();
                    updateFileStorageFolderSummary();
                }

                // https://stackoverflow.com/questions/44185477/intent-action-open-document-tree-doesnt-seem-to-return-a-real-path-to-drive
                // DocumentFile and DocumentContract

                /*
                ContentResolver contentResolver = getActivity().getContentResolver();
                Uri docUri = DocumentsContract.buildDocumentUriUsingTree(folderUri,
                        DocumentsContract.getTreeDocumentId(folderUri));
                Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(folderUri,
                        DocumentsContract.getTreeDocumentId(folderUri));
                String[] projection = new String[] {
                        DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                        DocumentsContract.Document.COLUMN_MIME_TYPE };


                try (Cursor docCursor = contentResolver.query(docUri, projection,
                        null, null, null))
                {
                    while (docCursor != null && docCursor.moveToNext())
                    {
                        Log.i("FELICIDAD",
                                "found doc =" + docCursor.getString(0)
                                        + ", mime=" + docCursor.getString(1));

                    }
                }
                Log.i("FELICIDAD", "-----------------------");
                try (Cursor docCursor = contentResolver.query(childrenUri, projection,
                        null, null, null))
                {
                    while (docCursor != null && docCursor.moveToNext())
                    {
                        Log.i("FELICIDAD",
                                "found doc =" + docCursor.getString(0)
                                        + ", mime=" + docCursor.getString(1));

                    }
                }
                */

            }
        }
    }
}
