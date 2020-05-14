package com.example.cookHelper.client;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookHelper.client.rest.DTOs.PostResponse;

public class Card2Activity extends AppCompatActivity {


    private TextView textViewName;
    private TextView textViewIngredients;
    private TextView textViewAlgorithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card2);

        textViewName = (TextView) findViewById(R.id.textView);
        textViewIngredients = (TextView) findViewById(R.id.textView3);
        textViewAlgorithm = (TextView) findViewById(R.id.textView4);

        Integer key = null;
        Integer id = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getInt("hashCode");
            id = extras.getInt("id");
        }

        try {
            //PostResponse list = HTTPRequestHelper.getValue(key);
            PostResponse list = HTTPRequestHelper.getValue(key);
            PostResponse.DataObject dataObject = list.getRecipes().get(id);

            textViewName.setText(dataObject.getRecipeName());

            StringBuilder builder = new StringBuilder();
            dataObject.getIngredients().forEach(ingredient -> {
                builder.append(ingredient).append("\n");
            });

            textViewIngredients.setText(builder.toString());
            textViewAlgorithm.setText(dataObject.getAlgorithm());

        } catch (Exception e) {
            e.getMessage();
        }

    }
}
