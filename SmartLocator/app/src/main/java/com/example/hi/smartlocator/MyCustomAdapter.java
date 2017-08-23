package com.example.hi.smartlocator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HI on 08-Aug-16.
 */
public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.myviewholder> {

    int prepos = 0;
    Context context;
    ArrayList<Friends> list;
    LayoutInflater inflater;

    public MyCustomAdapter(Context context, ArrayList<Friends> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyCustomAdapter.myviewholder onCreateViewHolder(final ViewGroup parent, final int position) {
        View v = inflater.inflate(R.layout.rowitem, parent, false);
        final myviewholder holder = new myviewholder(v, context, list);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyCustomAdapter.myviewholder holder, int position) {


       Friends re=list.get(position);
        String name=re.getName();
        holder.name.setText(name);
        String phone=re.getPhone();
        holder.phone.setText(phone);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView name,phone;
        Context context;
        ArrayList<Friends> list = new ArrayList<>();

        public myviewholder(View itemView, Context context, ArrayList<Friends> list) {
            super(itemView);
            this.list = list;
            this.context = context;
            img = (ImageView) itemView.findViewById(R.id.row_image);
            name= (TextView) itemView.findViewById(R.id.row_name);
            phone = (TextView) itemView.findViewById(R.id.row_phone);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent i=new Intent(context,ShowDetail.class);
            Bundle b=new Bundle();
            b.putInt("pos",pos);
            b.putParcelableArrayList("list",list);
            i.putExtras(b);
            context.startActivity(i);

        }
    }
}