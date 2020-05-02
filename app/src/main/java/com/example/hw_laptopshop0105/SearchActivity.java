package com.example.hw_laptopshop0105;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Spinner spinner_cate;
    private ImageView imageView;
    private TextView etID, etName, etPrice;
    protected Button btnBack;
    private List<Laptop> list = new ArrayList<Laptop>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spinner_cate = findViewById(R.id.spinner_Cate_SL);
        etID = findViewById(R.id.tv_id_SL);
        etName = findViewById(R.id.tv_name_SL);
        etPrice = findViewById(R.id.tv_price_SL);
        imageView=findViewById(R.id.img_show_SL);
        btnBack=findViewById(R.id.btn_back_SL);


        final LaptopDatabase dbHelper = new LaptopDatabase(this);
        final LaptopSpinnerAdapter myCustomSpinner = new LaptopSpinnerAdapter(SearchActivity.this, R.layout.dong_spinner_item, dbHelper.getLaps());
        spinner_cate.setAdapter(myCustomSpinner);

        spinner_cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Laptop laptop = (Laptop) myCustomSpinner.getItem(position);
                etID.setText("ID:"+String.valueOf(laptop.getId()));
                etName.setText("Name:"+String.valueOf(laptop.getName()));
                etPrice.setText("Price:"+String.valueOf(laptop.getPrice()));

                byte[] hinhanh = laptop.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
                imageView.setImageBitmap(bitmap);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
