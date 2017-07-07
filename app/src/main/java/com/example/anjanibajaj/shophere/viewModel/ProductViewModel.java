package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjanibajaj.shophere.BR;
import com.example.anjanibajaj.shophere.IndexFragment;
import com.example.anjanibajaj.shophere.MainActivity;
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

import static android.R.id.list;

/**
 * Created by Anjani Bajaj on 7/6/2017.
 */

public class ProductViewModel extends BaseObservable {
    private Product product;
   private IndexFragment indexFragment;
    private StaggeredGridLayoutManager sglm;

    public ProductViewModel(Product product, IndexFragment indexFragment) {
        this.product = product;
        this.indexFragment = indexFragment;
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

    @NonNull
    public String buildUrl(String type) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.APP_URL).append(type);
        Log.d("URL", url.toString());
        return url.toString();
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

    public void getCategories(String url) {
        StringRequest stringRequest = getStringRequestForCategories(url);
        VolleyNetwork.getInstance(indexFragment.getActivity()).addToRequestQueue(stringRequest);
    }

    private StringRequest getStringRequestForCategories(String url) {
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("R f c:", response);
                        try {
                            List<Category> s = parseJSONCategory(response);
                            System.out.println("--------------------------------"+s);
                            setCategory(s.get(0).getType()+s.get(1).getType()+s.get(2).getType()+s.get(3).getType()+s.get(4).getType());
                            notifyPropertyChanged(BR.category);
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

    private List<Category> parseJSONCategory(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray array = jsonObject.getJSONArray("result");
        List<Category> categories = new ArrayList<>();
        for(int i=0; i<array.length(); i++) {
            Category c = new Category((Integer) array.getJSONObject(i).get("cid"), array.getJSONObject(i).getString("type"));
            categories.add(c);
        }
        return categories;
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
}
