package e.dell.addpetrolexpense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import e.dell.addpetrolexpense.adapter.DisplayListAdapter;
import e.dell.addpetrolexpense.database.DatabaseHelper;
import e.dell.addpetrolexpense.listener.OnItemClickListenr;
import e.dell.addpetrolexpense.model.Model;

public class ListPetrolExpenseActivity extends AppCompatActivity implements OnItemClickListenr {

    private RecyclerView rvDetail;
    private DatabaseHelper databaseHelper;
    private List<Model> mUserData = new ArrayList<>();
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petrol_expense);
        init();
    }

    private void init() {
        databaseHelper = new DatabaseHelper(this);
        mUserData.clear();
        tvTotal = findViewById(R.id.tvTotal);
        mUserData.addAll(databaseHelper.getUserData());
        rvDetail = findViewById(R.id.rvDetail);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));
        rvDetail.setAdapter(new DisplayListAdapter(mUserData, this, this));

        getTotal();

    }

    private void getTotal() {
        int total = 0;
        for (int i = 0; i < mUserData.size(); i++) {

            total = total + Integer.parseInt(mUserData.get(i).getAmount());
            if (i == (mUserData.size() - 1)) {
                tvTotal.setText("Total Amount : " + total);
            }
        }
    }

    @Override
    public void onItemClickLister(View view, int pos) {
        if (view.getId() == R.id.btnDelete) {
            databaseHelper.deleteExpenses(mUserData.get(pos).getId());
            mUserData.remove(pos);
            rvDetail.getAdapter().notifyDataSetChanged();

        } else if (view.getId() == R.id.btnEdit) {

            Intent intent = new Intent(this, AddPetrolExpenseActivity.class);
            intent.putExtra(Model.DATA, mUserData.get(pos));
            startActivity(intent);
            finish();

        }
        getTotal();
    }
}
