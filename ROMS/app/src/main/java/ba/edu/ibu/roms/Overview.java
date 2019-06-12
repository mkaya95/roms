package ba.edu.ibu.roms;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Overview extends AppCompatActivity {

    FrameLayout overview_frame_layout;
    private static final String TAG = "MydActivit";
    public int[] table_colors=new int[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);


        overview_frame_layout = findViewById(R.id.overview_frame_layout);


        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);



        Call<List> ocall = service.getOverview();
        ocall.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> ocall, Response<List> response) {

                if(response.body().get(0).toString().length() != 0) {
                    String[] tablesList = response.body().get(0).toString().split(",");
                    int a =0;
                    for(String name : tablesList){
                        table_colors[a] = Integer.parseInt(name);
                        System.out.println(name);
                        a++;
                    }
                }


            }

            @Override
            public void onFailure(Call<List> ocall, Throwable t) {

                System.out.println(t.getMessage());

                Toast.makeText(Overview.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


        Call<List<SettingsProvider>> call = service.getAllSettings();
        call.enqueue(new Callback<List<SettingsProvider>>() {
            @Override
            public void onResponse(Call<List<SettingsProvider>> call, Response<List<SettingsProvider>> response) {

                int table_rows = 0;
                int table_cols = 0;

                for (int i=0; i < response.body().size(); i++) {

                     if(response.body().get(i).getLabel().equals("table_rows"))
                        table_rows = Integer.parseInt(response.body().get(i).getValue());
                    else if(response.body().get(i).getLabel().equals("table_cols"))
                        table_cols = Integer.parseInt(response.body().get(i).getValue());

                }


                Button[][] buttonArray = new Button[table_rows][table_cols];
                TableLayout table = new TableLayout(Overview.this);

                int table_number = 1;
                for (int row = 0; row < table_rows; row++) {
                    TableRow currentRow = new TableRow(Overview.this);
                    currentRow.setGravity(Gravity.CENTER);
                    for (int button = 0; button < table_cols; button++) {
                        Button currentButton = new Button(Overview.this);
                        currentButton.setText("Table "+table_number);
                        currentButton.setTag("table_"+table_number);
                        buttonArray[row][button] = currentButton;
                        currentButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deskOnClick(v);
                            }
                        });

                        if(table_colors.length > 0) {
                            for (int element : table_colors) {
                                if (element == table_number) {
                                    currentButton.setBackgroundColor(Color.RED);
                                }
                            }
                        }


                        currentRow.addView(currentButton);
                        table_number++;
                    }
                    table.addView(currentRow);
                }
/*
                for(String name : tablesList){
                    table_colors[a] = Integer.parseInt(name);
                    System.out.println(name);
                    a++;
                }
*/


                //  table.setBackgroundColor(Color.parseColor("#000000"));

                overview_frame_layout.addView(table);

            }

            @Override
            public void onFailure(Call<List<SettingsProvider>> call, Throwable t) {
                System.out.println("kayaaaaaaaaaa");
                System.out.println(t.getMessage());
                System.out.println("sssaaaa");
                Toast.makeText(Overview.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deskOnClick(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        int table_number = Integer.parseInt(buttonText.split("Table ")[1]);

        Intent table_details_intent = new Intent(this, TableDetails.class);
        table_details_intent.putExtra("table_number", table_number);
        startActivity(table_details_intent);




    }
}
