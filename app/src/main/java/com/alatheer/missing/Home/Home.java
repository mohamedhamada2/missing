package com.alatheer.missing.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.alatheer.missing.Authentication.Splash;
import com.alatheer.missing.Categories.CategoryActivity;
import com.alatheer.missing.Data.Local.MySharedPreference;
import com.alatheer.missing.R;
import com.alatheer.missing.Search.SearchActivity;

public class Home extends AppCompatActivity {
    @BindView(R.id.menu_imgbtn)
    View menu_imgbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void Add(View view) {
        startActivity(new Intent(this, CategoryActivity.class));
    }

    public void search(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    public void menu_imgbtn(){

}
    @OnClick(R.id.menu_imgbtn)
    public void menu_imgbtn(View view) {
        PopupMenu popup = new PopupMenu(this, menu_imgbtn);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onOptionsItemSelected);
        popup.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Logout();
                return true;
            default:
                return true;
        }
    }

    private void Logout() {
        MySharedPreference sharedPreferences = new MySharedPreference(this);
        sharedPreferences.ClearData(this);
        startActivity(new Intent(this, Splash.class));
        finish();
    }
}
