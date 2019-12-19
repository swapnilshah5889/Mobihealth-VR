package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.GeneralFunctions.transitions;
import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.R;

import java.util.List;

import static com.example.mobihealthapis.GeneralFunctions.transitions.slideDown;
import static com.example.mobihealthapis.GeneralFunctions.transitions.slideUp;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {

    Context context;
    List<Issues> issuesList;

    public TestAdapter(Context context, List<Issues> issuesList, LinearLayout ll_main) {
        this.context = context;
        this.issuesList = issuesList;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_layout, parent, false);
        return new TestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final Boolean[] isVisible = {false};

       /* holder.ll_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //transitions.SlideUpDown(ll_main_activity2,ll_data,isVisible,1);

                if (isVisible[0])
                    slideUp(holder.ll_data,holder.ll_data.getHeight());
                else
                    slideDown(holder.ll_data,holder.ll_data.getHeight());

                isVisible[0] = !isVisible[0];

            }
        });*/

       holder.ll_expand.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               isVisible[0] = toggleLayout(isVisible[0], holder.ll_data,holder.img_expand);
               isVisible[0] = !isVisible[0];
           }
       });



    }

    @Override
    public int getItemCount() {
        return 2;
    }


    private boolean toggleLayout(boolean isExpanded, LinearLayout layoutExpand,ImageView img_expand) {
        if (isExpanded) {
            transitions.expand(layoutExpand);
            img_expand.setImageResource(R.drawable.ic_minus);
        } else {
            transitions.collapse(layoutExpand);
            img_expand.setImageResource(R.drawable.ic_add_white);
        }
        return isExpanded;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout ll_expand,ll_data;
        ImageView img_expand;

        public MyViewHolder(@NonNull View view) {
            super(view);
            ll_data = view.findViewById(R.id.ll_data_vital);
            ll_expand = view.findViewById(R.id.ll_expand_vital);
            img_expand = view.findViewById(R.id.img_expand_vital);
        }
    }
}
