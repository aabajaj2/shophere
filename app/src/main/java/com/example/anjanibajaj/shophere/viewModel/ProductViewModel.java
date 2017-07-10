package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
 */

public class ProductViewModel extends BaseObservable {
    private Product product;
    private ProductFragment productFragment;
    private FragmentProductBinding fragmentProductBinding;

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
        for(int i=0; i<array.length(); i++) {
            Product p = new Product(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getString("category"), (Integer) array.getJSONObject(i).get("price"),
                    (Integer) array.getJSONObject(i).get("id"), (Integer) array.getJSONObject(i).get("cid"));
            products.add(p);
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

    private void  setAdapterProduct(List<Product> products) throws JSONException {
        ProductsAdapter productsAdapter = new ProductsAdapter(products, productFragment, fragmentProductBinding);
        fragmentProductBinding.recyclerView2.setAdapter(productsAdapter);
    }
}
