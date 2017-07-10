package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.anjanibajaj.shophere.BR;
import com.example.anjanibajaj.shophere.IndexFragment;
import com.example.anjanibajaj.shophere.ProductFragment;
import com.example.anjanibajaj.shophere.adapters.ProductsAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentProductBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.VolleyNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by Anjani Bajaj on 7/6/2017.
 * This class is the viewModel for ProductFragment
 * Sends request /product using Singleton VolleyNetwork Class
 * Parses response and returns the data according to the selected category from Category ViewModel
 */

public class ProductViewModel extends BaseObservable {
    private Product product;
    private ProductFragment productFragment;
    private FragmentProductBinding fragmentProductBinding;
    private Map<Integer, String> imageMap;
    private List<Integer> productIdList;

    public ProductViewModel(Product product, ProductFragment productFragment, FragmentProductBinding fragmentProductBinding) {
        this.product = product;
        this.productFragment = productFragment;
        this.fragmentProductBinding = fragmentProductBinding;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductFragment getProductFragment() {
        return productFragment;
    }

    public void setProductFragment(ProductFragment productFragment) {
        this.productFragment = productFragment;
    }
    @Bindable
    public String getName() {
        return product.getName();
    }

    public void setName(String name){
        product.setName(name);
    }

    @Bindable
    public String getCategory() {
        return product.getCategory();
    }

    public void setCategory(String category){
        product.setCategory(category);
    }

    @Bindable
    public String getImageUrl() {
        return product.getImageUrl();
    }

    public void getAllProducts(String url, Integer cid) {
        StringRequest stringRequest = getStringRequest(url, cid);
        VolleyNetwork.getInstance(productFragment.getActivity()).addToRequestQueue(stringRequest);
    }

    @NonNull
    private StringRequest getStringRequest(String url, final Integer cid) {
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("R:", response);
                        try {
                            List<Product> cproducts = parseJSONProduct(response, cid);
                            setAdapterProduct(cproducts);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productFragment.getActivity().getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Product> parseJSONProduct(String response, Integer cid) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray array = jsonObject.getJSONArray("result");
        List<Product> products = new ArrayList<>();
        productIdList = new ArrayList<>();
        for(int i=0; i<array.length(); i++) {
            productIdList.add((Integer) array.getJSONObject(i).get("id"));
        }
        loadProductImageMap(productIdList);
        for(int i=0; i<array.length(); i++) {
            Product p = new Product(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getString("category"), (Integer) array.getJSONObject(i).get("price"),
                    (Integer) array.getJSONObject(i).get("id"), (Integer) array.getJSONObject(i).get("cid"), imageMap.get(array.getJSONObject(i).get("id")));
            products.add(p);
            productIdList.add((Integer) array.getJSONObject(i).get("id"));
        }
        List<Product> cproducts = new ArrayList<>();
        Log.d("Size of products", String.valueOf(products.size()));
        for (int i=0; i<products.size(); i++) {
            if (products.get(i).getCid() == cid) {
                cproducts.add(products.get(i));
            }
        }
        return cproducts;
    }

    private void loadProductImageMap(List<Integer> productIdList) {
        imageMap = new TreeMap<>();
        for (Integer i: productIdList) {
            switch (i){
                case 120: imageMap.put(i, "https://store.storeimages.cdn-apple.com/4662/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone6s/gray/iphone6s-gray-select-2015?wid=1200&hei=630&fmt=jpeg&qlt=95&op_sharpen=0&resMode=bicub&op_usm=0.5,0.5,0,0&iccEmbed=0&layer=comp&.v=kqNjH3");
                    break;
                case 122: imageMap.put(i, "https://cnet2.cbsistatic.com/img/8YI9KWWu59TgqpOnOT7ev4Zww6M=/270x203/2017/01/24/a5a951ec-acfa-4ecb-9e2c-2ebaf61d0283/oneplus-3t-header.jpg");
                    break;
                default:
                    imageMap.put(i, "http://www.mystore.no/wp-content/themes/Avada-child-2.0/images/2014/495.png");
            }
        }
    }

    private void  setAdapterProduct(List<Product> products) throws JSONException {
        ProductsAdapter productsAdapter = new ProductsAdapter(products, productFragment, fragmentProductBinding);
        fragmentProductBinding.recyclerView2.setAdapter(productsAdapter);
    }
    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).centerCrop().into(view);
    }
}
