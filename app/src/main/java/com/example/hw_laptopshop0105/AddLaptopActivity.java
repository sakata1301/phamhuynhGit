package com.example.hw_laptopshop0105;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddLaptopActivity extends AppCompatActivity {

    private ImageView imgButton;
    private ImageView imgAnh;
    int REQUEST_CODE_FOLFER = 123;

    private ListView lvLap;
    ArrayList<Laptop> arrayLap;
    LaptopListViewAdapter adapter;

    private EditText etID, etName, etPrice;
    private Button btnAdd, btnUpdate, btnDelete,btnReset,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laptop);

        final LaptopDatabase dbHelper = new LaptopDatabase(this);

        btnAdd = findViewById(R.id.btn_add_ANL);
        btnUpdate = findViewById(R.id.btn_Update_ANL);
        btnDelete = findViewById(R.id.btn_delete_ANL);
        btnReset=findViewById(R.id.btn_RESET_ANL);
        btnBack=findViewById(R.id.btn_back_ANL);

        etID = findViewById(R.id.et_lapID);
        etName = findViewById(R.id.et_lapName);
        etPrice = findViewById(R.id.et_lapPrice);

        lvLap = findViewById(R.id.lv_list_ANL);

        arrayLap = new ArrayList<>();
        adapter = new LaptopListViewAdapter(this, R.layout.dong_list_item, dbHelper.getLaps());
        lvLap.setAdapter(adapter);

        imgButton = findViewById(R.id.img_btn_ANL);
        imgAnh = findViewById(R.id.img_anh_ANL);


        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);

        setImageBase();


        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_FOLFER);

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] hinhanh = byteArray.toByteArray();

                dbHelper.addLap(etID.getText().toString().trim(), etName.getText().toString().trim(), Integer.parseInt(etPrice.getText().toString().trim()), hinhanh);
                LaptopListViewAdapter adapter = new LaptopListViewAdapter(AddLaptopActivity.this, R.layout.dong_list_item, dbHelper.getLaps());
                lvLap.setAdapter(adapter);

                Toast.makeText(AddLaptopActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                setNullEdiText();


            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] hinhanh = byteArray.toByteArray();

                dbHelper.update(etID.getText().toString().trim(), etName.getText().toString().trim(), Integer.parseInt(etPrice.getText().toString().trim()), hinhanh);
                LaptopListViewAdapter adapter = new LaptopListViewAdapter(AddLaptopActivity.this, R.layout.dong_list_item, dbHelper.getLaps());
                lvLap.setAdapter(adapter);

                Toast.makeText(AddLaptopActivity.this, "Cap nhat thnah cong", Toast.LENGTH_SHORT).show();
                setNullEdiText();

                Enabled_2();



            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteLap(String.valueOf(etID.getText().toString()));
                LaptopListViewAdapter adapter = new LaptopListViewAdapter(AddLaptopActivity.this, R.layout.dong_list_item, dbHelper.getLaps());
                lvLap.setAdapter(adapter);

                Toast.makeText(AddLaptopActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                setNullEdiText();

                Enabled_2();


            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enabled_2();
                setNullEdiText();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        lvLap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<Laptop> list = dbHelper.getLaps();
                Laptop laptop=dbHelper.getLapByID(list.get(position).getId());
                etID.setText(String.valueOf(laptop.getId()));
                etName.setText(String.valueOf(laptop.getName()));
                etPrice.setText(String.valueOf(laptop.getPrice()));

                byte[] hinhanh = laptop.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
                imgAnh.setImageBitmap(bitmap);

                Enabled_1();

            }
        });


    }
    public void Enabled_1(){
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);

        btnAdd.setEnabled(false);

    }

    public void Enabled_2(){
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);

        btnAdd.setEnabled(true);

    }



    private void setImageBase() {

        String name="ic_launcher_background";
        String pkgName = this.getPackageName();
        int resID = this.getResources().getIdentifier(name, "drawable", pkgName);
        imgAnh.setImageResource(resID);
    }

    public void setNullEdiText(){
        etID.setText("");
        etName.setText("");
        etPrice.setText("");
        setImageBase();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOLFER && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                imgAnh.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
