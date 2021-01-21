package com.xuebinduan.dynamicsortanim;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

public class DynamicSortAnimRecyclerAdapter extends RecyclerView.Adapter<DynamicSortAnimRecyclerAdapter.ViewHolder> {
    private SortedList<Datum> list = new SortedList<>(Datum.class, new SortedList.Callback<Datum>() {
        @Override
        public int compare(Datum o1, Datum o2) {
            Log.e("TAG","compare");
            if (o1.getTime()!=o2.getTime()){
                // 按时间从小到大排序
                return Long.signum(o1.getTime() - o2.getTime());
            }
            return 0;
        }

        @Override
        public void onChanged(int position, int count) {
            Log.e("TAG","onChanged");
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Datum oldItem, Datum newItem) {
            Log.e("TAG","areContentsTheSame");
            return false;
        }

        @Override
        public boolean areItemsTheSame(Datum item1, Datum item2) {
            Log.e("TAG","areItemsTheSame");
            if (item1.getTime()==item2.getTime()&&item1.getName().equals(item2.getName())){
                return true;
            }
            return false;
        }

        @Override
        public void onInserted(int position, int count) {
            Log.e("TAG","onInserted");
            notifyItemRangeInserted(position,count);
        }

        @Override
        public void onRemoved(int position, int count) {
            Log.e("TAG","onRemoved");
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            Log.e("TAG","onMoved");
            notifyItemMoved(fromPosition,toPosition);
        }
    });

    public void addItem(Datum datum){
        list.add(datum);
    }

    public void removeItem(Datum datum){
        list.remove(datum);
    }

    @NonNull
    @Override
    public DynamicSortAnimRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicSortAnimRecyclerAdapter.ViewHolder holder, int position) {
        Datum datum = list.get(position);
        String name = datum.getName();
        long time = datum.getTime();
        holder.textName.setText(name);
        holder.textTime.setText(time+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textName;
        private final TextView textTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textTime = itemView.findViewById(R.id.text_time);
        }
    }

}
