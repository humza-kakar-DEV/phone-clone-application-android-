package com.example.wifip2p.Media;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.List;

public class DocumentMedia {

    private static final String TAG = "hmDoc";
    Context context;

    public DocumentMedia(Context context) {
        this.context = context;
    }

    public List<Document> generateDocuments() {

        List<Document> documentList = new ArrayList<Document>();

        ContentResolver cr = context.getContentResolver();

        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        }

        String[] projection = new String[] {
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.MediaColumns.SIZE,
        };

        String sortOrder = null; // unordered

        try (Cursor cursorAllDocumentFiles = cr.query(collection, projection, null, null, sortOrder)) {

            int idDocumentColumn = cursorAllDocumentFiles.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);
            int documentNameColumn = cursorAllDocumentFiles.getColumnIndexOrThrow(MediaStore.Downloads.DISPLAY_NAME);
            int documentTypeMimeColumn = cursorAllDocumentFiles.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE);
            int documentSizeColumn = cursorAllDocumentFiles.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);

            while (cursorAllDocumentFiles.moveToNext()) {

                long id = cursorAllDocumentFiles.getLong(idDocumentColumn);
                String name = cursorAllDocumentFiles.getString(documentNameColumn);
                String mimeType = cursorAllDocumentFiles.getString(documentTypeMimeColumn);
                int size = cursorAllDocumentFiles.getInt(documentSizeColumn);
                Uri contentUri = ContentUris.withAppendedId(collection, (id));

                documentList.add(new Document(id, contentUri, name, mimeType, size));

            }
        }
        return documentList;
    }
}
