package com.example.hw1.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hw1.DB.AppDB;
import com.example.hw1.DB.Score;
import com.example.hw1.Interface.Callback_List;
import com.example.hw1.R;
import com.example.hw1.Utilities.AppSP;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class ListFragment extends Fragment {
    private ListView userList;
    private Callback_List callBack_List;
    private AppDB scores;
    private final String SCORE = "scores";

    public void setCallBack_list(Callback_List callBack_list){
        this.callBack_List = callBack_list;
    }
    public ListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initListView();



        userList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                double latitude = scores.getScores().get(position).getLatitude();
                double longitude =  scores.getScores().get(position).getLongitude();
                String userName = scores.getScores().get(position).getUser();
                callBack_List.setMapLocation(latitude,longitude,userName);
            }
        });

        return view;
    }

    private void initListView() {
        scores = new Gson().fromJson(AppSP.getInstance().getStrSP(SCORE,""),AppDB.class);
        if(scores != null){
           // ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(getActivity(), android.R.layout.simple_expandable_list_item_1,scores.getScores());
           // userList.setAdapter(adapter);
            ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(getActivity(), android.R.layout.simple_expandable_list_item_1, scores.getScores()) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    //textView.setTypeface(typeface);
                    return view;
                }
            };

            userList.setAdapter(adapter);
        }
    }

    private void findViews(View view){
        this.userList = view.findViewById(R.id.score_list);
    }
}