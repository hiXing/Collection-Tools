package markmao.pulltorefresh.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.zhx.R;


public class MarkmaoActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.markmao_main);

        Button listBrn = (Button) findViewById(R.id.list_view_btn);
        listBrn.setOnClickListener(this);

        Button scrollBrn = (Button) findViewById(R.id.scroll_view_btn);
        scrollBrn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_view_btn:
                MListViewActivity.launch(this);
                break;

            case R.id.scroll_view_btn:
                MScrollViewActivity.launch(this);
                break;
        }
    }
}
