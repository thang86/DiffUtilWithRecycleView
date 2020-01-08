package thang86.github.io.diffutilwithrecycleview.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import thang86.github.io.diffutilwithrecycleview.R;
import thang86.github.io.diffutilwithrecycleview.model.Student;
import thang86.github.io.diffutilwithrecycleview.utils.DiffUtilCallBack;

public class MyAdapters extends RecyclerView.Adapter<MyAdapters.ViewHoder> {
    private Context mContext;
    private List<Student> mDemoList;
    private OnClickItem mOnClickItem;

    public MyAdapters(Context context, List<Student> demoList, OnClickItem onClickItem) {
        mContext = context;
        mDemoList = demoList;
        mOnClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        Student student = mDemoList.get(position);

        holder.ivAvatar.setImageResource(R.drawable.ic_launcher_background);
        holder.nameStudent.setText(student.getNameStudent());
        holder.className.setText(student.getClassName());
        holder.birthday.setText(student.getBirthday());
    }


    public void updatesDemo(List<Student> students) {
        final DiffUtilCallBack diffCallback = new DiffUtilCallBack(students, this.mDemoList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mDemoList.clear();
        mDemoList.addAll(students);
        diffResult.dispatchUpdatesTo(this);

    }


    @Override
    public int getItemCount() {
        return mDemoList.size() > 0 ? mDemoList.size() : 0;
    }

    public class ViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View linearLayout;
        private ImageView ivAvatar;
        private TextView nameStudent;
        private TextView className;
        private TextView birthday;


        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.my_linearlayout);
            linearLayout.setOnClickListener(this);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            ivAvatar.setOnClickListener(this);
            nameStudent = itemView.findViewById(R.id.tv_name_student);
            className = itemView.findViewById(R.id.tv_class_name);
            birthday = itemView.findViewById(R.id.tv_birthday);
        }

        @Override
        public void onClick(View v) {
            int postion = getAdapterPosition();
            switch (v.getId()) {
                case R.id.iv_avatar:
                    if (mOnClickItem == null) {
                        return;
                    }
                    mOnClickItem.onClickDeleteItem(postion);
                    break;
                case R.id.my_linearlayout:
                    if (mOnClickItem == null) {
                        return;
                    }
                    mOnClickItem.onClickItem(postion);
                    break;

            }

        }
    }

    public interface OnClickItem {
        void onClickItem(int position);

        void onClickDeleteItem(int position);
    }
}
