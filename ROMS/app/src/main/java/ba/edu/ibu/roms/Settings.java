package ba.edu.ibu.roms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends AppCompatActivity {
    TextView restaurant_name;
    TextView table_rows;
    TextView table_cols;


    Response<List<SettingsProvider>> last_settings =null;
    APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        restaurant_name = (TextView)findViewById(R.id.editText_restaurant_name);
        table_rows = (TextView)findViewById(R.id.editText_table_rows);
        table_cols = (TextView)findViewById(R.id.editText_table_cols);



        Call<List<SettingsProvider>> call = service.getAllSettings();
        call.enqueue(new Callback<List<SettingsProvider>>() {
            @Override
            public void onResponse(Call<List<SettingsProvider>> call, Response<List<SettingsProvider>> response) {
                last_settings = response;

                for (int i=0; i < response.body().size(); i++) {

                    if(response.body().get(i).getLabel().equals("restaurant_name"))
                        restaurant_name.setText(response.body().get(i).getValue().toString());
                    else if(response.body().get(i).getLabel().equals("table_rows"))
                        table_rows.setText(response.body().get(i).getValue().toString());
                    else if(response.body().get(i).getLabel().equals("table_cols"))
                        table_cols.setText(response.body().get(i).getValue().toString());

                }
            }

            @Override
            public void onFailure(Call<List<SettingsProvider>> call, Throwable t) {
                System.out.println("kayaaaaaaaaaa");
                System.out.println(t.getMessage());
                System.out.println("sssaaaa");
                Toast.makeText(Settings.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void saveSettings(View v) {

        final boolean[] is_success = {true};

        final SweetAlertDialog pDialog = new SweetAlertDialog(Settings.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Saving");
        pDialog.setCancelable(false);
        pDialog.show();


        for (int i=0; i < last_settings.body().size(); i++) {
            if(last_settings.body().get(i).getLabel().equals("restaurant_name"))
                last_settings.body().get(i).setValue(restaurant_name.getText().toString());
            else if(last_settings.body().get(i).getLabel().equals("table_rows"))
                last_settings.body().get(i).setValue(table_rows.getText().toString());
            else if(last_settings.body().get(i).getLabel().equals("table_cols"))
                last_settings.body().get(i).setValue(table_cols.getText().toString());




            Call<List<SettingsProvider>> call = service.updateSettings(last_settings.body().get(i).getTitle(), last_settings.body().get(i).getLabel(), last_settings.body().get(i).getValue());

            call.enqueue(new Callback<List<SettingsProvider>>() {
                @Override
                public void onResponse(Call<List<SettingsProvider>> call, Response<List<SettingsProvider>> response) {
                    if(response.isSuccessful()) {

                    }
                }

                @Override
                public void onFailure(Call<List<SettingsProvider>> call, Throwable t) {
                    is_success[0] = false;
                    System.out.println(t.getMessage());
                    pDialog.cancel();
                    Toast.makeText(Settings.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });


        }

        if(is_success[0] == true) {
            pDialog.cancel();
            new SweetAlertDialog(Settings.this)
                    .setTitleText("Success !")
                    .setContentText("The settings has been saved.")
                    .show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(this, Overview.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
