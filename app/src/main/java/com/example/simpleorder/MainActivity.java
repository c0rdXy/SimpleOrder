package com.example.simpleorder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleorder.entity.Menu;
import com.example.simpleorder.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private List<Menu> menuList = new ArrayList<>(); // 菜单列表
    private List<Order> orderList = new ArrayList<>(); // 订单列表

    private ListView lvMenu; // 菜单列表视图
    private ListView lvOrder; // 订单列表视图

    private Button btnOrder; // 提交订单按钮

    private MenuAdapter menuAdapter; // 菜单列表适配器
    private OrderAdapter orderAdapter; // 订单列表适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMenu(); // 初始化菜单数据

        lvMenu = findViewById(R.id.menu_list);
        lvOrder = findViewById(R.id.order_list);
        btnOrder = findViewById(R.id.btn_order);

        menuAdapter = new MenuAdapter(this, menuList);
        orderAdapter = new OrderAdapter(this, orderList);

        lvMenu.setAdapter(menuAdapter);
        lvOrder.setAdapter(orderAdapter);

        // 添加购物车操作
        menuAdapter.setOnAddMenuListener(new MenuAdapter.OnAddMenuListener() {
            @Override
            public void onAddMenu(int position) {
                Menu menu = menuList.get(position);
                for (Order order : orderList) {
                    if (order.getName().equals(menu.getName())) { // 已有该菜品则添加数量
                        order.setCount(order.getCount() + 1);
                        orderAdapter.notifyDataSetChanged();
                        return;
                    }
                }
                Order order = new Order(menu.getName(), menu.getPrice(), 1);
                orderList.add(order);
                orderAdapter.notifyDataSetChanged();
            }
        });

        // 删除订单操作
        orderAdapter.setOnDeleteOrderListener(new OrderAdapter.OnDeleteOrderListener() {
            @Override
            public void onDeleteOrder(int position) {
                orderList.remove(position);
                orderAdapter.notifyDataSetChanged();
            }
        });

        // 提交订单操作
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里实现订单提交和支付功能
                Toast.makeText(MainActivity.this, "提交订单成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initMenu() {
        menuList.add(new Menu("鱼香肉丝", 18));
        menuList.add(new Menu("宫保鸡丁", 22));
        menuList.add(new Menu("回锅肉", 20));
        menuList.add(new Menu("水煮鱼", 30));
        menuList.add(new Menu("毛血旺", 28));
    }


    public static class MenuAdapter extends BaseAdapter {

        private Context context;
        private List<Menu> menuList;
        private OnAddMenuListener onAddMenuListener;

        public MenuAdapter(Context context, List<Menu> menuList) {
            this.context = context;
            this.menuList = menuList;
        }

        public void setOnAddMenuListener(OnAddMenuListener onAddMenuListener) {
            this.onAddMenuListener = onAddMenuListener;
        }

        @Override
        public int getCount() {
            return menuList.size();
        }

        @Override
        public Object getItem(int position) {
            return menuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.menu_list_item, parent, false);
            TextView tvName = view.findViewById(R.id.tv_menu_name);
            TextView tvPrice = view.findViewById(R.id.tv_menu_price);
            Button btnAdd = view.findViewById(R.id.btn_add_menu);

            Menu menu = menuList.get(position);
            tvName.setText(menu.getName());
            tvPrice.setText(String.format(Locale.getDefault(), "%d元", menu.getPrice()));

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddMenuListener != null) {
                        onAddMenuListener.onAddMenu(position);
                    }
                }
            });

            return view;
        }

        public interface OnAddMenuListener {
            void onAddMenu(int position);
        }

    }

    public static class OrderAdapter extends BaseAdapter {

        private Context context;
        private List<Order> orderList;
        private OnDeleteOrderListener onDeleteOrderListener;

        public OrderAdapter(Context context, List<Order> orderList) {
            this.context = context;
            this.orderList = orderList;
        }

        public void setOnDeleteOrderListener(OnDeleteOrderListener onDeleteOrderListener) {
            this.onDeleteOrderListener = onDeleteOrderListener;
        }

        @Override
        public int getCount() {
            return orderList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
            TextView tvName = view.findViewById(R.id.tv_order_name);
            TextView tvPrice = view.findViewById(R.id.tv_order_price);
            Button btnDelete = view.findViewById(R.id.btn_delete_order);

            Order order = orderList.get(position);
            tvName.setText(order.getName());
            tvPrice.setText(String.format(Locale.getDefault(), "%d元 x %d", order.getPrice(), order.getCount()));

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteOrderListener != null) {
                        onDeleteOrderListener.onDeleteOrder(position);
                    }
                }
            });

            return view;
        }

        public interface OnDeleteOrderListener {
            void onDeleteOrder(int position);
        }

    }

}



