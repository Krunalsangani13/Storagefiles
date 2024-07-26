package com.example.storagefiles;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import java.io.File;

public class FileOpenActivity extends AppCompatActivity {
    String filepath="";
    Integer pagenumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_open);
        PDFView view=findViewById(R.id.pdfviewer);
        filepath=getIntent().getStringExtra("path");

        Log.d(TAG, "onCreate: filepath: "+filepath);
        File file=new File(filepath);
        Log.d(TAG, "onCreate: filepath: //////////"+file);
        Uri path=Uri.fromFile(file);
        Log.d(TAG, "onCreate: filepath: -----------------"+path);
        view.fromUri(path).defaultPage(pagenumber).enableSwipe(true).scrollHandle(new DefaultScrollHandle(this)).load();
        Log.d(TAG, "onCreate: filepath: -----------view------"+view);
    }

}

