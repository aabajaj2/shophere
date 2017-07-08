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
import com.example.anjanibajaj.shophere.adapters.ProductsAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.model.Category;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.Constants;
import com.example.anjanibajaj.shophere.utils.VolleyNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Anjani Bajaj on 7/6/2017.
 */

public class ProductViewModel extends BaseObservable {
    private Product product;
    private IndexFragment indexFragment;
    private FragmentIndexBinding fragmentIndexBinding;

    public ProductViewModel(Product product, IndexFragment indexFragment, FragmentIndexBinding fragmentIndexBinding) {
        this.product = product;
        this.indexFragment = indexFragment;
        this.fragmentIndexBinding = fragmentIndexBinding;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public IndexFragment getIndexFragment() {
        return indexFragment;
    }

    public void setIndexFragment(IndexFragment indexFragment) {
        this.indexFragment = indexFragment;
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

    public void getAllProducts(String url) {
        StringRequest stringRequest = getStringRequest(url);
        VolleyNetwork.getInstance(indexFragment.getActivity()).addToRequestQueue(stringRequest);
    }

    @NonNull
    private StringRequest getStringRequest(String url) {
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("R:", response);
                        try {
                            List<Product> products = parseJSONProduct(response);
                            setAdapterProduct(products);
                            setName(products.get(0).getName()+products.get(1).getName()+products.get(2).getName()+products.get(3).getName()+products.get(4).getName());
                            notifyPropertyChanged(BR.name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(indexFragment.getActivity().getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public List<Product> parseJSONProduct(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray array = jsonObject.getJSONArray("result");
        List<Product> products = new ArrayList<>();
        for(int i=0; i<array.length(); i++) {
            Product p = new Product(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getString("category"), (Integer) array.getJSONObject(i).get("price"),
                    (Integer) array.getJSONObject(i).get("id"), (Integer) array.getJSONObject(i).get("cid"));
            products.add(p);
        }
        return products;
    }

    public void  setAdapterProduct(List<Product> products) throws JSONException {
        ProductsAdapter productsAdapter = new ProductsAdapter(products, indexFragment , fragmentIndexBinding);
        fragmentIndexBinding.recyclerView.setAdapter(productsAdapter);
    }
}
