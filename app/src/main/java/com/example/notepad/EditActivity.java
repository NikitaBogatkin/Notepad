package com.example.notepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notepad.adapter.ListItem;
import com.example.notepad.db.AppExecutor;
import com.example.notepad.db.MyConstants;
import com.example.notepad.db.MyDbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {
    private final int PICK_IMAGE_CODE = 123;
    private ImageView imNewImage;
    private ConstraintLayout ImageContainer;
    private FloatingActionButton fbAddImage;
    private ImageButton imEditImage, imDeleteImage;
    private EditText edTitle, edDeck;
    private MyDbManager myDbManager;
    private String tempUrl = "empty";
    private boolean isEditState = true;
    private ListItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getMyIntents();
    }

    protected void onResume() {
        super.onResume();
        myDbManager.openDb();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE && data != null) {

            tempUrl = data.getData().toString();
            imNewImage.setImageURI(data.getData());
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

    }

    private void init() {
        edTitle = findViewById(R.id.edTitle);
        edDeck = findViewById(R.id.edDesc);
        imNewImage = findViewById(R.id.imNewImage);
        fbAddImage = findViewById(R.id.fbAddImage);
        ImageContainer = findViewById(R.id.ImageContainer);
        imEditImage = findViewById(R.id.imEditImage);
        imDeleteImage = findViewById(R.id.imDeleteImage);
        myDbManager = new MyDbManager(this);

    }

    private void getMyIntents() {
        Intent i = getIntent();
        if (i != null) {
            item = (ListItem) i.getSerializableExtra(MyConstants.LIST_ITEM_INTENT);

            isEditState = i.getBooleanExtra(MyConstants.EDIT_STATE, true);

            if (!isEditState) {
                edTitle.setText(item.getTitle());
                edDeck.setText(item.getDesc());
                if (!item.getUrl().equals("empty")) {
                    tempUrl = item.getUrl();
                    ImageContainer.setVisibility(View.VISIBLE);
                    imNewImage.setImageURI(Uri.parse(item.getUrl()));
                    imDeleteImage.setVisibility(View.GONE);
                    imEditImage.setVisibility(View.GONE);
                }
            }
        }
    }

    public void onClickSave(View view) {
        final String title = edTitle.getText().toString();
        final String desc = edDeck.getText().toString();
        if (title.equals("")
                || desc.equals("")) {
            Toast.makeText(this, R.string.text_empty, Toast.LENGTH_SHORT).show();

        } else {
            if (isEditState) {
                AppExecutor.getInstance().getSubID().execute(new Runnable() {
                    @Override
                    public void run() {
                        myDbManager.insertToDb(title, desc, tempUrl);
                    }
                });
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();

            } else {

                myDbManager.updateItem(title, desc, tempUrl,item.getId());
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            }

            myDbManager.closeDb();
            finish();
        }

    }

    public void onClickDeleteImage(View view) {

        imNewImage.setImageResource(R.drawable.ic_image_def);
        tempUrl = "empty";
        ImageContainer.setVisibility(View.GONE);
        fbAddImage.setVisibility(View.VISIBLE);
    }

    public void onClickAddimage(View view) {
        ImageContainer.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }

    public void onClickChooseImage(View view) {
        Intent chooser = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        chooser.setType("image/*");
        startActivityForResult(chooser, PICK_IMAGE_CODE);
    }
}