package org.techtown.capstoneprojectcocktail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MJH_SimulatorUiActivity extends AppCompatActivity implements View.OnClickListener {
    public FloatingActionButton floatingActionButtonForAddList;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mjg_test);

        Intent intent = getIntent();

        floatingActionButtonForAddList = (FloatingActionButton) findViewById(R.id.floatingActionButtonForAddList);
        floatingActionButtonForAddList.setOnClickListener(this);



        ListView listview ;
        MJH_ListviewAdapter adapter;

        // Adapter 생성
        adapter = new MJH_ListviewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem( "1",
                "빌드", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem( "2",
                "푸어", "Account Box Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem( "1",
                "쉐이크", "Account Box Black 36dp") ;


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                MJH_ListviewItem item = (MJH_ListviewItem) parent.getItemAtPosition(position) ;

                String titleStep = item.getStep() ;
                String tech = item.getTech() ;
                String descStr = item.getDesc() ;
                Snackbar.make(v, titleStep, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                // TODO : use item data.
            }
        }) ;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.floatingActionButtonForAddList:
                //anim();
                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MJH_PopupActivity.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
