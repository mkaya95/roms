package ba.edu.ibu.roms;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableDetails extends AppCompatActivity {
    int table_number = 0;
    TextView table_number_tv;
    TextView totalprice_tv;
    TextView textViewTotalPriceLabel;
    TextView textViewOrderList;
    Button buttonPayCloseTable;

    ArrayList<OrderDataModel> dataModels;
    ListView listView;

    private List<OrderDataModel> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrdersAdapter oAdapter;
    private List<OrderDataModel> myorders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final OrderDataModel[] order = new OrderDataModel[1];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_details);

        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        if(mBundle != null){
            table_number = mBundle.getInt("table_number");
        }

        table_number_tv = findViewById(R.id.textViewTableNumber);

        table_number_tv.setText("Table  "+table_number);



        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);

        Call<List<Order>> mService = service.getOrderByTableNumber(table_number);

        mService.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                final List<Order> OrderObject = response.body();

                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

                totalprice_tv = findViewById(R.id.textViewTotalPrice);
                textViewTotalPriceLabel = findViewById(R.id.textViewTotalPriceLabel);
                textViewOrderList = findViewById(R.id.textViewOrderList);
                buttonPayCloseTable = findViewById(R.id.buttonPayCloseTable);


                oAdapter = new OrdersAdapter(orderList, new OrdersAdapter.OnItemClickListener() {
                    @Override public void onItemClick(OrderDataModel item) {

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);;
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(oAdapter);


                if(OrderObject.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    totalprice_tv.setVisibility(View.VISIBLE);
                    textViewTotalPriceLabel.setVisibility(View.VISIBLE);
                    textViewOrderList.setVisibility(View.VISIBLE);
                    buttonPayCloseTable.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    totalprice_tv.setVisibility(View.INVISIBLE);
                    textViewTotalPriceLabel.setVisibility(View.INVISIBLE);
                    textViewOrderList.setVisibility(View.INVISIBLE);
                    buttonPayCloseTable.setVisibility(View.INVISIBLE);
                }

                int sum = 0;
                for (int i = 0; i< OrderObject.size(); i++) {
                  /*  System.out.println(OrderObject.get(i).getName());
                    System.out.println(OrderObject.get(i).getDesc());
*/
                    OrderDataModel movie = new OrderDataModel(OrderObject.get(i).getId(), OrderObject.get(i).getName(), OrderObject.get(i).getDesc(), OrderObject.get(i).getPrice());
                    orderList.add(movie);

                    sum +=  OrderObject.get(i).getPrice();
                }

                totalprice_tv.setText(sum+" BAM");

            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

    public void clickOrderButton(View V) {
        Intent food_list_intent = new Intent(this, FoodList.class);
        food_list_intent.putExtra("table_number", table_number);
        startActivity(food_list_intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
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
            case R.id.action_settings:
                Intent intentt = new Intent(this, Settings.class);
                startActivity(intentt);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void  closeTable(View v) {

        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);

        Call<List> mService = service.closeTable(table_number);

        mService.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {

                new SweetAlertDialog(TableDetails.this)
                        .setTitleText("Success !")
                        .setContentText("The table has been paid and closed.")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        })
                        .show();


            }
            @Override
            public void onFailure(Call<List> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
