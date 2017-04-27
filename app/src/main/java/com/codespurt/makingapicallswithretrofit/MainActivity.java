package com.codespurt.makingapicallswithretrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.codespurt.makingapicallswithretrofit.engine.ApiEndpointInterface;
import com.codespurt.makingapicallswithretrofit.models.Result;
import com.codespurt.makingapicallswithretrofit.utils.RequestUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MakingAPICalls";

    private String[] paramsForGetUrl = {"1"};
    private String[] paramsForPostUrl = {"english"};

    private ProgressDialog progressDialog;
    private TextView tvGet, tvPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGet = (TextView) findViewById(R.id.tv_data_get);
        tvPost = (TextView) findViewById(R.id.tv_data_post);

        // call API - GET
        makeGetCall(RequestUtils.BASE_URL_GET, paramsForGetUrl);
    }

    // get
    private void makeGetCall(String url, String[] params) {
        startProgressDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface mInterface = retrofit.create(ApiEndpointInterface.class);

        Call call = mInterface.getDataFromGet(params[0]);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                progressDialog.dismiss();
                Log.d(TAG, response.toString());
                returnGetData((Result) response.body());
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                progressDialog.dismiss();
                Log.d(TAG, throwable.getMessage());
            }
        });
    }

    private void returnGetData(Result result) {
        if (result != null) {
            tvGet.setText(R.string.get_data_get_successful);
        } else {
            Toast.makeText(this, "Unable to get data from GET API", Toast.LENGTH_SHORT).show();
        }
        // call API - POST
        makePostCall(RequestUtils.BASE_URL_POST, paramsForPostUrl);
    }

    // post
    private void makePostCall(String url, String[] params) {
        startProgressDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface mInterface = retrofit.create(ApiEndpointInterface.class);

        // add params - body
        // {"language":"english"}
        Result result = new Result();
        result.setLanguage(params[0]);

        Call<Result> call = mInterface.getDataFromPost(result);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                Log.d(TAG, response.toString());
                returnPostData(response.body());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void returnPostData(Result result) {
        if (result != null) {
            tvPost.setText(R.string.post_data_get_successful);
        } else {
            Toast.makeText(this, "Unable to get data from POST API", Toast.LENGTH_SHORT).show();
        }
    }

    private void startProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}