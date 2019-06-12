package ba.edu.ibu.roms;

import android.content.Intent;
import android.support.design.widget.TabLayout;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodList extends AppCompatActivity {

    int table_number = 0;

    ArrayList<OrderDataModel> dataModels;
    ListView listView;

    private List<OrderDataModel> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrdersAdapter oAdapter;
    private List<OrderDataModel> myorders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        if(mBundle != null){
            table_number = mBundle.getInt("table_number");
        }



        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);

        Call<List<Foods>> mService = service.getFoods();

        mService.enqueue(new Callback<List<Foods>>() {
            @Override
            public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                final List<Foods> OrderObject = response.body();

                recyclerView = (RecyclerView) findViewById(R.id.food_list);


                oAdapter = new OrdersAdapter(orderList,  new OrdersAdapter.OnItemClickListener() {
                    @Override public void onItemClick(OrderDataModel item) {
                        int food_id = item.getId();

                        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);

                        Call<List> mService = service.order(table_number,food_id);

                        mService.enqueue(new Callback<List>() {
                            @Override
                            public void onResponse(Call<List> call, Response<List> response) {
                                final List OrderObject = response.body();

                                Intent food_list_intent = new Intent(FoodList.this, TableDetails.class);
                                food_list_intent.putExtra("table_number", table_number);
                                startActivity(food_list_intent);

                            }
                            @Override
                            public void onFailure(Call<List> call, Throwable t) {
                                System.out.println(t.getMessage());

                                Toast.makeText(FoodList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        System.out.println(food_id);

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);;
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(oAdapter);


                for (int i = 0; i< OrderObject.size(); i++) {
                    OrderDataModel food = new OrderDataModel(OrderObject.get(i).getId(), OrderObject.get(i).getName(), OrderObject.get(i).getDesc(), OrderObject.get(i).getPrice());
                    orderList.add(food);
                }



            }
            @Override
            public void onFailure(Call<List<Foods>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
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
