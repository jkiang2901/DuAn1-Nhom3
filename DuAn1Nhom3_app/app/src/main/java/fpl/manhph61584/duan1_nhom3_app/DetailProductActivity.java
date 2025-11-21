package fpl.manhph61584.duan1_nhom3_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import fpl.manhph61584.duan1_nhom3_app.R;
import fpl.manhph61584.duan1_nhom3_app.ApiClient;
import fpl.manhph61584.duan1_nhom3_app.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView imgProduct, btnPlus, btnMinus;
    private TextView txtName, txtPrice, txtDesc, txtQuantity, btnBuy;
    private LinearLayout layoutColors, layoutSizes;

    private int quantity = 1;
    private String selectedColor = "";
    private String selectedSize = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        imgProduct = findViewById(R.id.imgProduct);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDesc = findViewById(R.id.txtDesc);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnBuy = findViewById(R.id.btnBuy);
        layoutColors = findViewById(R.id.layoutColors);
        layoutSizes = findViewById(R.id.layoutSizes);

        String id = getIntent().getStringExtra("id");

        loadProduct(id);
        handleQuantity();
        handleBuy(id);
    }

    private void loadProduct(String id) {
        ApiClient.getService().getProductDetail(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> res) {
                if (!res.isSuccessful() || res.body() == null) return;

                Product p = res.body();

                txtName.setText(p.getName());
                txtPrice.setText(p.getPrice() + "₫");
                txtDesc.setText(p.getDescription());

                Glide.with(DetailProductActivity.this)
                        .load(p.getImage())
                        .into(imgProduct);

                // Fake màu + size tạm thời
                addColor("Đỏ");
                addColor("Đen");
                addColor("Xanh");

                addSize("S");
                addSize("M");
                addSize("L");
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addColor(String color) {
        TextView tv = new TextView(this);
        tv.setText(color);
        tv.setPadding(30, 10, 30, 10);
        tv.setBackgroundResource(R.drawable.bg_unselect);
        tv.setTextSize(14);
        tv.setOnClickListener(v -> {
            selectedColor = color;
            resetSelection(layoutColors);
            tv.setBackgroundResource(R.drawable.bg_selected);
        });
        layoutColors.addView(tv);
    }

    private void addSize(String size) {
        TextView tv = new TextView(this);
        tv.setText(size);
        tv.setPadding(30, 10, 30, 10);
        tv.setBackgroundResource(R.drawable.bg_unselect);
        tv.setTextSize(14);
        tv.setOnClickListener(v -> {
            selectedSize = size;
            resetSelection(layoutSizes);
            tv.setBackgroundResource(R.drawable.bg_selected);
        });
        layoutSizes.addView(tv);
    }

    private void resetSelection(LinearLayout layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            layout.getChildAt(i).setBackgroundResource(R.drawable.bg_unselect);
        }
    }

    private void handleQuantity() {
        btnPlus.setOnClickListener(v -> {
            quantity++;
            txtQuantity.setText(String.valueOf(quantity));
        });

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) quantity--;
            txtQuantity.setText(String.valueOf(quantity));
        });
    }

    private void handleBuy(String productId) {
        btnBuy.setOnClickListener(v -> {

            Intent i = new Intent(DetailProductActivity.this, DetailProductActivity.class);
            i.putExtra("productId", productId);
            i.putExtra("quantity", quantity);
            i.putExtra("color", selectedColor);
            i.putExtra("size", selectedSize);
            startActivity(i);
        });
    }
}
