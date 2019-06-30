package e.dell.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import e.dell.project1.Adapter.DisplayDetail;
import e.dell.project1.Database.DatabaseHelper;
import e.dell.project1.ModelData.Model;

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
