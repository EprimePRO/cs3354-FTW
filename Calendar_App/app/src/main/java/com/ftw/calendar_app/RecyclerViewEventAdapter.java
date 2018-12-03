package com.ftw.calendar_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewEventAdapter extends RecyclerView.Adapter<RecyclerViewEventAdapter.ViewHolder>{

    public static final String TAG = "RecyclerViewEventAdapte";

    private ArrayList<Event> mEvents = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, time;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventListTitle);
            time = itemView.findViewById(R.id.eventListTime);
            parentLayout = itemView.findViewById(R.id.parent);
        }
    }

    public RecyclerViewEventAdapter(ArrayList<Event> mEvents, Context context) {
        this.mEvents = mEvents;
        this.context = context;
    }

    public void swap(ArrayList<Event> newEvents)
    {
        if(newEvents == null || newEvents.size()==0) {
            mEvents.clear();
            notifyDataSetChanged();
            return;
        }
        if (mEvents != null && mEvents.size()>0)
            mEvents.clear();
        mEvents.addAll(newEvents);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_event_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        Log.d(TAG, "onbindViewHolder: called.");
        holder.title.setText(mEvents.get(i).getTitle());
        holder.time.setText(mEvents.get(i).getStartTime());

        /*holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d(TAG, "onClick: clicked on: " + mEvents.get(i).getTitle());
                Toast.makeText(context, mEvents.get(i).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }



}
