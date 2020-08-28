package com.alatheer.missing.Comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.missing.Categories.CategoryActivity;
import com.alatheer.missing.Data.Local.MySharedPreference;
import com.alatheer.missing.Data.Remote.Model.Comment.Comment;
import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.Model.Authentication.User;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.Data.Remote.Model.Success.Success;
import com.alatheer.missing.R;
import com.alatheer.missing.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddComment extends AppCompatActivity {
    @BindView(R.id.category_name)
    TextView category_name;
    @BindView(R.id.category_img)
    ImageView category_img;
    @BindView(R.id.location_name)
    TextView location_name;
    @BindView(R.id.missing_date)
    TextView missing_date;
    @BindView(R.id.et_add_comment)
    TextView et_add_comment;
    @BindView(R.id.img_add_comment)
    ImageView img_add_comment;
    @BindView(R.id.recycler_all_comments)
    RecyclerView recycler_all_comments;
    RecyclerView.LayoutManager layoutManager;
    CommentAdapter commentAdapter;
    int item_id,user_id,type;
    String img,name,city,govern,date;
    MySharedPreference mprefs;
    User user;
    int comment_user_id_fk;
    String comment;
    List<Comment> commentsList;
    boolean active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        ButterKnife.bind(this);
        getDataFromIntent();
        get_all_comments();
        mprefs = MySharedPreference.getInstance();
        user = mprefs.Get_UserData(this);
        comment_user_id_fk = user.getId();
        setData();
        LocalBroadcastManager.getInstance(this).registerReceiver(mhandler,new IntentFilter("com.alatheer.missing_FCM-MESSAGE"));
    }
    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
    }
    private void getDataFromIntent() {
        item_id = getIntent().getIntExtra("item_id",0);
        type = getIntent().getIntExtra("type",0);
        user_id = getIntent().getIntExtra("userId",0);
        name = getIntent().getStringExtra("name");
        img = getIntent().getStringExtra("img");
        city = getIntent().getStringExtra("city");
        govern = getIntent().getStringExtra("govern");
        date = getIntent().getStringExtra("date");
    }

    private void get_all_comments() {
        if(Utilities.isNetworkAvailable(this)){
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Comment>> call = service.get_all_comments(item_id+"");
            call.enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    if(response.isSuccessful()){
                      commentsList = response.body();
                      initrecyclerview();
                    }
                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {

                }
            });
        }
    }

    private void initrecyclerview() {
        layoutManager = new LinearLayoutManager(this);
        commentAdapter = new CommentAdapter(commentsList,this);
        recycler_all_comments.setLayoutManager(layoutManager);
        recycler_all_comments.setAdapter(commentAdapter);
        recycler_all_comments.setHasFixedSize(true);
    }

    private void setData() {
        category_name.setText(name);
        Picasso.get().load("https://mymissing.online/uploads/images/"+img).into(category_img);
        location_name.setText(city+"-"+govern);
        missing_date.setText(date);
    }
    @OnClick(R.id.img_add_comment)
    public void add_this_comment(View view) {
        comment = et_add_comment.getText().toString();
       if(Utilities.isNetworkAvailable(this)){
           GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
           Call<Success> call = getDataService.addcomment(item_id+"",type+"",user_id+"",
                   comment_user_id_fk+"",comment);
           call.enqueue(new Callback<Success>() {
               @Override
               public void onResponse(Call<Success> call, Response<Success> response) {
                   if(response.isSuccessful()){
                       if(response.body().getSuccess()==1){
                           Toast.makeText(AddComment.this, "تم اضافة تعليقك بنجاح", Toast.LENGTH_SHORT).show();
                           et_add_comment.setText("");
                           get_all_comments();
                       }
                   }
               }

               @Override
               public void onFailure(Call<Success> call, Throwable t) {

               }
           });
       }
    }

    public void Back(View view) {
        onBackPressed();
    }
    private BroadcastReceiver mhandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("message");
            //message.setText(msg);
            if(active == true){
                Utilities.showNotificationInADialog(AddComment.this,msg);
            }
        }
    };
}
