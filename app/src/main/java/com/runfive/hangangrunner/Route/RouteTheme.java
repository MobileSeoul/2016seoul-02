package com.runfive.hangangrunner.Route;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.runfive.hangangrunner.R;
import com.runfive.hangangrunner.RouteCourse.RouteCourseActivity;

/**
 * Created by Jinwoo on 2016-08-02.
 */


public class RouteTheme extends Fragment {
    RouteActivity routeActivity = new RouteActivity();
    public static final String ARG_SECTION_NUMBER = "section_one";


    ListView listView1;
    RouteThemeAdapter adapter;


    public RouteTheme(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.route_fragment_page1, container, false);


        listView1 = (ListView) rootView.findViewById (R.id.listView1);

        // 어댑터 객체 생성
        //adapter = new RouteAdapter(this);
        adapter = new RouteThemeAdapter(getActivity().getBaseContext());
        // 액티비티는 컨텍스트참조되는데 fragment여서 액티비티를 가져온후 컨택스트참조를한다

        // 아이템 데이터 만들기
        Resources res = getResources();


        // 정렬해야됨
        //List<RouteObject> routeArrayData = new ArrayList<RouteObject>(); // 랭킹정보배열
        //rankingArrayData.add(new RankingObject(res.getDrawable(R.drawable.sponge), "공원1", "300m"));

        adapter.addItem(new RouteObject(res.getDrawable(R.drawable.route_banpo_image),"반포 공원로", "반포 공원", "2.98"));
        adapter.addItem(new RouteObject(res.getDrawable(R.drawable.route_gangnaru_image),"광나루 공원로", "광나루공원", "1.82"));
        adapter.addItem(new RouteObject(res.getDrawable(R.drawable.route_echon_image),"이촌 공원로", "이촌 공원", "2.78"));
        adapter.addItem(new RouteObject(res.getDrawable(R.drawable.route_gamsil_image),"잠실 공원로", "잠실 공원", "2.97"));
        adapter.addItem(new RouteObject(res.getDrawable(R.drawable.route_nanzi_image),"난지 공원로", "난지 공원", "2.58"));
        adapter.addItem(new RouteObject(res.getDrawable(R.drawable.route_ddoksum_image),"뚝섬 공원로", "뚝섬 공원", "2.74"));
        adapter.addItem(new RouteObject(res.getDrawable(R.drawable.route_yangha_image),"양화 공원로", "양화 공원", "2.27"));
        adapter.addItem(new RouteObject(res.getDrawable(R.drawable.route_yueeido_image),"여의도 공원로", "여의도공원", "4.77"));

        // adapter.sort(new Comparator<String>() {
        //    @Override
        //    public int compare(String arg1, String arg0) {
        //        return -arg1.compareTo(arg0);
        //   }
        // });
        // 리스트뷰에 어댑터 설정
        listView1.setAdapter(adapter);

        // 새로 정의한 리스너로 객체를 만들어 설정 (그냥 확인차 보여주는것 토스트메세지로)
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RouteObject curItem = (RouteObject) adapter.getItem(position);

                String[] course = curItem.getData();

                Toast.makeText(getActivity().getApplicationContext(), ""  +course[1] , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RouteCourseActivity.class);
                intent.putExtra("routename", course[0]);
                intent.putExtra("parkname", course[1]);
                intent.putExtra("distance", course[2]);
                createBackStack(intent);
            }

        });

        return rootView;
    }
    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(getActivity());
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            routeActivity.finish();
        }
    }
}
