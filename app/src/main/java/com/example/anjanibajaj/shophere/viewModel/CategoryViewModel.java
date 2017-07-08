package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjanibajaj.shophere.BR;
import com.example.anjanibajaj.shophere.IndexFragment;
import com.example.anjanibajaj.shophere.adapters.CategoryAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.model.Category;
import com.example.anjanibajaj.shophere.utils.VolleyNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anjani Bajaj on 7/8/2017.
 */

public class CategoryViewModel extends BaseObservable {
    private Category category;
    private IndexFragment indexFragment;
    private FragmentIndexBinding fragmentIndexBinding;

    public CategoryViewModel(Category category, IndexFragment indexFragment, FragmentIndexBinding fragmentIndexBinding) {
        this.category = category;
        this.indexFragment = indexFragment;
        this.fragmentIndexBinding = fragmentIndexBinding;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public IndexFragment getIndexFragment() {
        return indexFragment;
    }

    public void setIndexFragment(IndexFragment indexFragment) {
        this.indexFragment = indexFragment;
    }

    public FragmentIndexBinding getFragmentIndexBinding() {
        return fragmentIndexBinding;
    }

    public void setFragmentIndexBinding(FragmentIndexBinding fragmentIndexBinding) {
        this.fragmentIndexBinding = fragmentIndexBinding;
    }

    @Bindable
    public String getType() {
        return category.getType();
    }

    public void setType(String type){
        category.setType(type);
    }

    @Bindable
    public String getCid() {
        return category.getType();
    }

    public void setCid(Integer cid){
        category.setCid(cid);
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
                            setAdapterategory(s);
                            setType(s.get(0).getType()+s.get(1).getType()+s.get(2).getType()+s.get(3).getType()+s.get(4).getType());
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
    public void  setAdapterategory(List<Category> products) throws JSONException {
        CategoryAdapter productsAdapter = new CategoryAdapter(products, indexFragment , fragmentIndexBinding);
        fragmentIndexBinding.recyclerView.setAdapter(productsAdapter);
    }
}
