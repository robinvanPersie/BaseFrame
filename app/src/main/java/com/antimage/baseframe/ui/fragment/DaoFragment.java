package com.antimage.baseframe.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antimage.baseframe.App;
import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.FragmentDaoBinding;
import com.antimage.baseframe.model.Address;
import com.antimage.baseframe.model.AddressDao;
import com.antimage.baseframe.model.DaoSession;
import com.antimage.baseframe.model.User;
import com.antimage.baseframe.model.UserDao;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.Random;

import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/25.
 */

public class DaoFragment extends Fragment {

    FragmentDaoBinding binding;
    UserDao userDao;
    Query<User> userQuery;

    UserAdapter adapter;

    AddressDao addressDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dao, container, false);
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter();
        binding.rv.setAdapter(adapter);
        binding.insertBtn.setOnClickListener(v -> {
            insert();
        });
        binding.delBtn.setOnClickListener(v -> {
            del();
        });
        binding.whereBtn.setOnClickListener(v -> {
            queryByWhere();
        });
        binding.joinBtn.setOnClickListener(v -> {
            queryByJoin();
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DaoSession daoSession = App.getInstance().getDaoSession();
        userDao = daoSession.getUserDao();
        userQuery = userDao.queryBuilder().build();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        addressDao = daoSession.getAddressDao();
    }

    String[] s = new String[] {"shanghai", "nanjing", "hangzhou", "suzhou"};
    Random random = new Random();

    public void insert() {
        User user = new User();
        String r = String.format("%f.2", Math.random());

        user.setMobile("mobile-" + r);
        user.setUserId("userId-" + r);




        Address address = new Address();
        address.setUserId(user.getUserId());
        address.setCountry("cn");
        address.setProvince("shanghai");
        address.setCity(s[random.nextInt(4)]);
        address.setCounty("changning");

        user.setName("name-" + address.getCity());
        userDao.insert(user);

        addressDao.insert(address);

        queryToView();
    }

    public void del() {
        userDao.deleteByKey(adapter.getItemId(0));
        queryToView();
    }

    private void queryToView() {
        List<User> users = userQuery.list();
        adapter.setList(users);
    }

    private void queryByWhere() {
        Query<User> userQuery = userDao.queryBuilder().where(UserDao.Properties.Name.like("%sh%")).build();
        adapter.setList(userQuery.list());
    }

    void queryByJoin() {
        QueryBuilder<User> builder = userDao.queryBuilder();
        builder.join(Address.class, AddressDao.Properties.Id);
//                .where(AddressDao.Properties.City.eq("suzhou"));
//        builder.join(AddressDao.Properties.UserId, Address.class);
//                .where(AddressDao.Properties.City.eq("suzhou"));
        adapter.setList(builder.list());
    }

    private class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {

        List<User> list;

        public void setList(List<User> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.textView.setText(list.get(position).toString());
        }

        @Override
        public long getItemId(int position) {
            return list.get(position).getId();
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            public TextView textView;

            public Holder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }

    void example() {

    }
}
