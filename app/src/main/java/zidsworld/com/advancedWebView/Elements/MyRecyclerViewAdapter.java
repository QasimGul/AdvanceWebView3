package zidsworld.com.advancedWebView.Elements;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import zidsworld.com.advancedWebView.R;
import zidsworld.com.advancedWebView.Activities.WebActivity;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private final MyListData[] listdata;
    // RecyclerView recyclerView;

    public MyRecyclerViewAdapter(MyListData[] listdata) {
        this.listdata = listdata;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rv_items, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MyListData myListData = listdata[position];
        holder.textView.setText(listdata[position].getTitle());
        holder.textViewdesc.setText(listdata[position].getDescription());
        holder.imageView.setImageResource(listdata[position].getImgId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (position == 0) {

                    Intent myactivity = new Intent(view.getContext(), WebActivity.class);
                    myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    myactivity.putExtra("url", "https://google.com");
                    Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_SHORT).show();
                    view.getContext().getApplicationContext().startActivity(myactivity);
                } else if (position == 1) {


                } else if (position == 2) {
                    Intent myactivity = new Intent(view.getContext(), WebActivity.class);
                    myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    myactivity.putExtra("url", "");
                    view.getContext().getApplicationContext().startActivity(myactivity);
                } else if (position == 3) {
                    Intent myactivity = new Intent(view.getContext(), WebActivity.class);
                    myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    myactivity.putExtra("url", "");
                    view.getContext().getApplicationContext().startActivity(myactivity);

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;

        public final TextView textView;
        public final TextView textViewdesc;
        public final CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.info_img);
            this.textView = (TextView) itemView.findViewById(R.id.Main_text);
            this.textViewdesc = (TextView) itemView.findViewById(R.id.desctext);
            cardView = (CardView) itemView.findViewById(R.id.card_layout);

        }
    }

}
