package e.dell.addpetrolexpense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import e.dell.addpetrolexpense.adapter.DisplayDetail;
import e.dell.addpetrolexpense.database.DatabaseHelper;
import e.dell.addpetrolexpense.model.Model;

public class RecycleViewExample extends AppCompatActivity {

    private RecyclerView rvDetail;
    private DatabaseHelper databaseHelper;
    private List<Model> mUserData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_example);
        init();
    }

  private void init() {
        databaseHelper=new DatabaseHelper(this);
        mUserData.clear();
        mUserData.addAll(databaseHelper.getUserData());
        rvDetail=findViewById(R.id.rvDetail);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));
        rvDetail.setAdapter(new DisplayDetail(mUserData, this));


    }
}
