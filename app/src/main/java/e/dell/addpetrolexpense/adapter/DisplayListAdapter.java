package e.dell.addpetrolexpense.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import e.dell.addpetrolexpense.listener.OnItemClickListenr;
import e.dell.addpetrolexpense.model.Model;
import e.dell.addpetrolexpense.R;

public class DisplayListAdapter extends RecyclerView.Adapter<DisplayListAdapter.ViewHolder> {
    private List<Model> detail;
    private Context context;
    private OnItemClickListenr onItemClickListenr;

    public DisplayListAdapter(List<Model> detail, Context context, OnItemClickListenr onItemClickListenr) {
        this.detail = detail;
        this.context = context;
        this.onItemClickListenr = onItemClickListenr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Model userData = detail.get(position);
        holder.tvDate.setText(userData.getDatepik());
        holder.tvTime.setText(userData.getTimepik());
        holder.tvAmount.setText(userData.getAmount() + " RS ");
        holder.tvKm.setText(userData.getKm() + " KM ");


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListenr.onItemClickLister(v, position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListenr.onItemClickLister(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return detail.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvTime;
        TextView tvAmount;
        TextView tvKm;
        ImageView btnDelete, btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvKm = itemView.findViewById(R.id.tvKm);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
