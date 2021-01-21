package com.xuebinduan.dynamicsortanim;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DynamicSortAnimRecyclerAdapter extends RecyclerView.Adapter<DynamicSortAnimRecyclerAdapter.ViewHolder> {
    private final Comparator<Datum> mComparator = new Comparator<Datum>() {
        @Override
        public int compare(Datum o1, Datum o2) {
            if (o1.getTime()!=o2.getTime()){
                // 按时间从小到大排序
                return Long.signum(o1.getTime() - o2.getTime());
            }
            return 0;
        }
    };

    private SortedList<Datum> sortedList = new SortedList<>(Datum.class, new SortedList.Callback<Datum>() {
        @Override
        public int compare(Datum o1, Datum o2) {
            Log.e("TAG","compare");
            return mComparator.compare(o1,o2);
        }

        @Override
        public void onChanged(int position, int count) {
            Log.e("TAG","onChanged");
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Datum oldItem, Datum newItem) {
            Log.e("TAG","areContentsTheSame");
            return oldItem.getName().equals(newItem.getName());
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
        sortedList.add(datum);
    }

    public void removeItem(Datum datum){
        sortedList.remove(datum);
    }

    /**
     * 演示SortList的批量操作，这里的逻辑是先动态删除不含list中的数据，然后再追加一套最加上来。
     * @param list
     */
    public void batchOperateReplaceAllItem(List<Datum> list){
        sortedList.beginBatchedUpdates();
        Datum[] sortedArray = list.toArray((Datum[])Array.newInstance(Datum.class,list.size()));
        Arrays.sort(sortedArray,mComparator);
        //todo 这里倒序遍历就可以避免删除后索引变化的错误
        for(int i = sortedList.size()-1;i>=0;i--){
            Datum datum = sortedList.get(i);
            int result = Arrays.binarySearch(sortedArray, datum, mComparator);
            if (result<0){
                sortedList.remove(datum);
            }
        }
        sortedList.addAll(list);
        sortedList.endBatchedUpdates();
    }

    @NonNull
    @Override
    public DynamicSortAnimRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicSortAnimRecyclerAdapter.ViewHolder holder, int position) {
        Datum datum = sortedList.get(position);
        String name = datum.getName();
        long time = datum.getTime();
        holder.textName.setText(name);
        holder.textTime.setText(time+"");
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
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
