package com.sds.study.sqliteapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 리스트 뷰에 보여질 화면이 단일 위젯이 아닌 2개 이상으로 구성된 복합 위젯의 경우
 * 개발자가 디자인을 정의하므로 어댑터를 재정의한다!!
 */

public class MyListAdapter extends BaseAdapter {
    String TAG;
    Context context;
    SQLiteDatabase db;

    ArrayList<Member> memberList=new ArrayList<Member>();

    public MyListAdapter(Context context) {
        this.context=context;
        TAG=this.getClass().getName();

        MainActivity mainActivity=(MainActivity)context;
        db=mainActivity.db; // mainActivity에서 얻어온 db를 우리 db에 넣자!!

        getList();
    }

    /*데이터베이스로부터 레코드 가져오기*/
    public void getList(){
        String sql="select * from member";
        Cursor rs=db.rawQuery(sql,null);

        /*기존 리스트 모두 삭제*/
        memberList.removeAll(memberList);

        while(rs.moveToNext()){
            int member_id=rs.getInt(rs.getColumnIndex("member_id"));
            String id=rs.getString(rs.getColumnIndex("id"));
            String password=rs.getString(rs.getColumnIndex("password"));

            Log.d(TAG,"id="+id+",password"+password);
            Member dto = new Member();

            dto.setId(id);
            dto.setMember_id(member_id);
            dto.setPassword(password);

            memberList.add(dto);
        }
    }

    public int getCount() {
        return memberList.size();
    }

    public Object getItem(int i) {
        return memberList.get(i);
    }

    public long getItemId(int i) {
        return memberList.get(i).getMember_id();
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view=null; // return값이 정해져있지 않기 때문에 변수로 선언한다. 누가 여기 들어올지는 모른다!, convertView는 LinearLayout
        Member member=memberList.get(i);

        if(convertView!=null){
        /*해당 인덱스에 이미 아이템이 채워져있다면*/
            view=convertView;
            MemberItem item=(MemberItem)view;
            item.setMember(member);

        }else{
        /*해당인덱스에 아무것도 없는 상태라면*/
          view = new MemberItem(context, member);
        }
        return view;
    }
}
