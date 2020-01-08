package thang86.github.io.diffutilwithrecycleview.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import thang86.github.io.diffutilwithrecycleview.MainActivity;
import thang86.github.io.diffutilwithrecycleview.R;
import thang86.github.io.diffutilwithrecycleview.adapter.MyAdapters;
import thang86.github.io.diffutilwithrecycleview.model.Student;
import thang86.github.io.diffutilwithrecycleview.utils.PreLoadingLayout;


/**
 * @Author ThangTX2
 */
public class MainFragment extends Fragment implements MyAdapters.OnClickItem {

    private MyAdapters adapter;
    private List<Student> mStudentList;


    public MainFragment() {
    }

    public static MainFragment newInstance(List<Student> mStudentList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("students", (ArrayList<? extends Parcelable>) mStudentList);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundle = getArguments();
        if (bundle == null || !bundle.containsKey("students")) {
            return;
        } else {
            mStudentList = bundle.getParcelableArrayList("students");
        }

    }

    /**
     * Init view
     *
     * @param view
     */
    public void initView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.my_recycleview);
        //mDemoList = new ArrayList<>();
        adapter = new MyAdapters(getContext(), mStudentList, this);
        PreLoadingLayout preLoadingLayout = new PreLoadingLayout(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(preLoadingLayout);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflater view
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onClickItem(int position) {
        Student student = mStudentList.get(position);
        InforFragment inforFragment = InforFragment.newInstance(position, student);
        ((MainActivity) getActivity()).replaceFragment(inforFragment, InforFragment.class.getName());

    }

    @Override
    public void onClickDeleteItem(int position) {
        List<Student> students = new ArrayList<>();
        for (Student student : mStudentList) {
            students.add(student.clone());
        }
        students.remove(position);
        adapter.updatesDemo(students);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void updates(int pos, Student student) {
        adapter = new MyAdapters(getContext(), mStudentList, this);
        mStudentList.set(pos, student);
        adapter.notifyDataSetChanged();


        adapter = new MyAdapters(getContext(), mStudentList, this);
        List<Student> students = new ArrayList<>();
        for (Student student1 : mStudentList) {
            students.add(student1.clone());
        }
        students.set(pos, student);
        adapter.updatesDemo(students);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
