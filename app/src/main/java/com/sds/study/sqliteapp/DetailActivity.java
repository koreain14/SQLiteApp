package com.sds.study.sqliteapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    ImageView img;
    LinearLayout layout;
    Intent intent;

    TextView txt_member_id;
    EditText txt_id;
    EditText txt_password;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        img = (ImageView) findViewById(R.id.img);
        layout = (LinearLayout) findViewById(R.id.layout);

        txt_member_id = (TextView) findViewById(R.id.txt_member_id);
        txt_id = (EditText) findViewById(R.id.txt_id);
        txt_password = (EditText) findViewById(R.id.txt_password);

        AnimationDrawable drawable = (AnimationDrawable) img.getDrawable();
        drawable.start();

        /*AnimationDrawable back = (AnimationDrawable) layout.getBackground();
        back.start();*/

        /*member 넘기기*/
        intent = getIntent();
        Member member = intent.getParcelableExtra("member");

        Toast.makeText(this, "넘어온 멤버 아이디는?" + member.getMember_id(), Toast.LENGTH_SHORT).show();

        txt_member_id.setText(Integer.toString(member.getMember_id()));
        txt_id.setText(member.getId().toString());
        txt_password.setText(member.getPassword().toString());
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_edit:
                edit();
                Toast.makeText(this, "수정 눌렀어?", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_delete:
                delete();
                Toast.makeText(this, "삭제 눌렀어?", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_list:
                list();
                Toast.makeText(this, "리스트 눌렀어?", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void edit() {
        String sql = "update member set id=?, password=? where member_id=?";

        String member_id = txt_member_id.getText().toString();
        String id = txt_id.getText().toString();
        String password = txt_password.getText().toString();

        MainActivity.db.execSQL(sql, new String[]{
                id, password, member_id
        });
    }

    public void list() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void delete() {
        String sql = "delete from member where member_id=?";

        String member_id = txt_member_id.getText().toString();
        MainActivity.db.execSQL(sql, new String[]{
                member_id
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
