package e.dell.project1.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import e.dell.project1.ModelData.Model;
import e.dell.project1.R;

public class DisplayDetail extends RecyclerView.Adapter<DisplayDetail.ViewHolder>{
    private List<Model> detail;
    private Context context;
    public DisplayDetail(List<Model> detail,Context context)
    {
        this.detail= detail;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model userData=detail.get(position);
        holder.tvDate.setText(userData.getDatepik());
        holder.tvTime.setText(userData.getTimepik());
        holder.tvAmount.setText(userData.getAmount());
        holder.tvKm.setText(userData.getKm());

    }

    @Override
    public int getItemCount() {
        return detail.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvTime;
        TextView tvAmount;
        TextView tvKm;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvAmount=itemView.findViewById(R.id.tvAmount);
            tvKm=itemView.findViewById(R.id.tvKm);
        }
    }
}
