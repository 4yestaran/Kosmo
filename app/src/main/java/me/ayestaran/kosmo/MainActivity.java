package me.ayestaran.kosmo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<List<Project>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        String token = settings.getString("token", null);
/*
        if(token == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            OkHttpClient client = new OkHttpClient();
            client.networkInterceptors().add(new StethoInterceptor());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.cosmo.paradigmate.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            // prepare call in Retrofit 2.0
            CosmoAPI cosmoAPI = retrofit.create(CosmoAPI.class);

            Call<List<Project>> call = cosmoAPI.getProjects();
            //asynchronous call
            call.enqueue(this);

            // synchronous call would be with execute, in this case you
            // would have to perform this outside the main thread
            // call.execute()

            // to cancel a running request
            // call.cancel();
            // calls can only be used once but you can easily clone them
            //Call<StackOverflowQuestions> c = call.clone();
            //c.enqueue(this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Response<List<Project>> response, Retrofit retrofit) {
        setProgressBarIndeterminateVisibility(false);
        for(Project project : response.body()) {
            Log.i("Cosmo", project.toString());
        }
        ArrayAdapter mProjecsAdapter= new ArrayAdapter<Project>(this, R.layout.project_list_item, R.id.project_list_item_text, response.body());
        ListView listView = (ListView) findViewById(R.id.listview_projects);
        listView.setAdapter(mProjecsAdapter);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("Cosmo", t.getLocalizedMessage());
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
