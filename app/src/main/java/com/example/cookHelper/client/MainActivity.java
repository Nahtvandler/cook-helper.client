package com.example.cookHelper.client;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.cookHelper.client.rest.DTOs.PostRequest;
import com.example.cookHelper.client.rest.DTOs.PostResponse;
import com.example.cookHelper.client.rest.NetworkService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mTextMessage;
    private Button buttonCenter;
    private ConstraintLayout mContainer;
    private EditText mEditText;
    private ImageButton microphone;
    private TTSManager ttsManager = new TTSManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("");
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("");
                    Intent intent = new Intent(MainActivity.this, List2Activity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        buttonCenter = (Button) findViewById(R.id.buttonCenter);
        mContainer = (ConstraintLayout) findViewById(R.id.container);
        mEditText = (EditText) findViewById(R.id.edit_text);
        microphone = (ImageButton) findViewById(R.id.microphone);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        buttonCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, List2Activity.class);
                intent.putExtra("message", mEditText.getText().toString() );
                startActivity(intent);
            }
        });

        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startVoiceInput();
            }
        });

        ttsManager.init(this);

        final EditText editText = (EditText) findViewById(R.id.edit_text);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    editText.setHint("Введите ингредиент");
                } else {
                    editText.setHint("");
                }
            }
        });


//        Intent intent = new Intent(MainActivity.this, Card2Activity.class);
//        startActivity(intent);

//        call_service();
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//mVoiceInputTv.setText(result.get(0));
//System.out.println(result.toString());

// final StringBuilder builder = new StringBuilder();
//
// for(String pString: result) {
// builder.append(pString).append(" ");
// }

                    String recognizedString = result.get(0);
                    String[] array = recognizedString.split(" ");
                    List<String> list = Arrays.asList(array);
                    System.out.println(list);

                    for(String p: list){
                        mEditText.append(p);
                        mEditText.append(" ");
                    }

//editText.setText(builder.toString());
                    String message = "Вы назвали " + result.get(0);
                    ttsManager.initQueue(message);
                }
                break;
            }

        }
    }

    public void call_service() {
        ArrayList<String> list = new ArrayList<>();
        list.add("марковь");

        PostRequest postRequest = new PostRequest();
        postRequest.setList(list);

        NetworkService
                .getInstance()
                .getJSONApi()
                .getData(postRequest)
                .enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PostResponse> call, @NonNull Response<PostResponse> response) {
                        PostResponse post = response.body();
/*

                            buttonCenter.setText(post.getTest12());
                            List<PostResponse.DataObject> list = post.getRecipes();

                            int bottom = 5;
                            for(PostResponse.DataObject p: list ){
                                TextView tv = new TextView(mContainer.getContext());
                                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                tv.setGravity(Gravity.CENTER);
                                tv.setTextSize(18);
                                tv.setPadding(0, 5, 0, bottom + 50);
                                tv.setText(p.getRecipeName());
                                mContainer.addView(tv);
                            }

                            for(PostResponse.DataObject p: list ) {
                                System.out.println(p.getCookTime());
                            }
*/
                    }

                    @Override
                    public void onFailure(@NonNull Call<PostResponse> call, @NonNull Throwable t) {

                        mTextMessage.setText("Ошибка подключения к интернету");
                        t.printStackTrace();
                    }
                });
    }
}

