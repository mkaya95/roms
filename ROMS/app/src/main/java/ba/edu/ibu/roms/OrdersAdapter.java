package ba.edu.ibu.roms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OrdersAdapter  extends  RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(OrderDataModel item);
    }
    
    private List<OrderDataModel> ordersList;
    private OnItemClickListener listener = null;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView food_name, food_desc, food_price;

        public MyViewHolder(View view) {
            super(view);
            food_name = view.findViewById(R.id.food_name);
            food_desc = view.findViewById(R.id.food_desc);
            food_price = view.findViewById(R.id.food_price);
        }

        public void bind(final OrderDataModel item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    public OrdersAdapter(List<OrderDataModel> ordersList, OnItemClickListener listener) {

        this.ordersList = ordersList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderDataModel order = ordersList.get(position);
        holder.food_name.setText(order.getName());
        holder.food_desc.setText(order.getDesc());
        holder.food_price.setText(order.getPrice().toString()+"  BAM");
        holder.bind(ordersList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }
}
