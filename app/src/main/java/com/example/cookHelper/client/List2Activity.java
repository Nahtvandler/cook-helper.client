package com.example.cookHelper.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookHelper.client.rest.DTOs.PostRequest;
import com.example.cookHelper.client.rest.DTOs.PostResponse;
import com.example.cookHelper.client.rest.NetworkService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class List2Activity extends AppCompatActivity {

    private ArrayList<String> mlist = new ArrayList<>();
    private ListView mlistView;

    private Intent card2ActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        mlistView = (ListView) findViewById(R.id.listView);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.names, android.R.layout.simple_list_item_1);
//        mlistView.setAdapter(adapter);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = getCard2ActivityIntent();
                intent.putExtra("id", id );
                startActivity(intent);
            }
        });

        mlistView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String message = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            message = extras.getString("message");
        }

        try {
            for ( String p: Arrays.asList(message.split(" ")) ){
                mlist.add(p);
            };
        } catch (Exception e) {
            e.getMessage();
        }
        send_request();
    }

    private Intent getCard2ActivityIntent() {
        if (card2ActivityIntent == null) {
            card2ActivityIntent = new Intent(List2Activity.this, Card2Activity.class);
            return card2ActivityIntent;
        }
        return card2ActivityIntent;
    }

    public void send_request() {

        PostRequest postRequest = new PostRequest();
        postRequest.setList(mlist);

        NetworkService
                .getInstance()
                .getJSONApi()
                .getData(postRequest)
                .enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PostResponse> call, @NonNull Response<PostResponse> response) {
                        PostResponse post = response.body();
                        List<PostResponse.DataObject> list = post.getRecipes();

                        Intent intent = getCard2ActivityIntent();
                        intent.putExtra("hashCode", post.hashCode() );
                        HTTPRequestHelper.put(post.hashCode(), post);

                        if (list == null) {
                            return;
                        }

                        if ( post == null ){
                            return;
                        }

                        LinearLayout mContainer = (LinearLayout) findViewById(R.id.container_list2);
                        ListView listView = (ListView) findViewById(R.id.listView);

                        ArrayList<String> RecipeNames = new ArrayList<String>();
                        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

                        for(PostResponse.DataObject p: list){
                            Map<String, String> datum = new HashMap<String, String>(2);
                            datum.put("title", p.getRecipeName());
                            datum.put("date", p.getCookTime());
                            data.add(datum);
                        }

/*
                        SimpleDateFormat dateFormat = new SimpleDateFormat("kk час. HH мин.");
                        //Date date = dateFormat.parse(360000);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(36000*1000);
                        String formatedDate = dateFormat.format(calendar.getTime());

//                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContainer.getContext(), android.R.layout.simple_list_item_2, RecipeNames);
*/

                        SimpleAdapter adapter = new SimpleAdapter(mContainer.getContext(), data,
                                android.R.layout.simple_list_item_2,
                                new String[] {"title", "date"},
                                new int[] {android.R.id.text1,
                                        android.R.id.text2});

                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(@NonNull Call<PostResponse> call, @NonNull Throwable t) {

//                        mTextMessage.setText("Ошибка подключения к интернету");
//                        t.printStackTrace();
                    }
                });
    }
}
