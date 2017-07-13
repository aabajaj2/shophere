package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
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
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.adapters.CategoryAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentIndexBinding;
import com.example.anjanibajaj.shophere.model.Category;
import com.example.anjanibajaj.shophere.utils.VolleyNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Anjani Bajaj on 7/8/2017.
 * Binds data for category View (which is also the index page) i.e fragment_index and IndexFragment where all the categories are displayed.
 */

public class CategoryViewModel extends BaseObservable {
    private Category category;
    private IndexFragment indexFragment;
    private FragmentIndexBinding fragmentIndexBinding;
    private Map<Integer, String> imageMap;

    public CategoryViewModel(Category category, IndexFragment indexFragment, FragmentIndexBinding fragmentIndexBinding) {
        this.category = category;
        this.indexFragment = indexFragment;
        this.fragmentIndexBinding = fragmentIndexBinding;
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).crossFade().into(view);
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
        String type = category.getType();
        // Capitalize first letter of the category type
        StringBuilder sb = new StringBuilder(type);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public void setType(String type) {
        category.setType(type);
    }

    @Bindable
    public Integer getCid() {
        return category.getCid();
    }

    public void setCid(Integer cid) {
        category.setCid(cid);
    }

    @Bindable
    public String getImageUrl() {
        return category.getImageUrl();
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
                            setAdapterCategory(s);
                            setType(s.get(0).getType() + s.get(1).getType() + s.get(2).getType() + s.get(3).getType() + s.get(4).getType());
                            notifyPropertyChanged(BR.category);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(indexFragment.getActivity().getApplicationContext(), "Connection Error: Cannot reach the server! "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Category> parseJSONCategory(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray array = jsonObject.getJSONArray("result");
        List<Category> categories = new ArrayList<>();
        loadImageMap();
        for (int i = 0; i < array.length(); i++) {
            Category c = new Category((Integer) array.getJSONObject(i).get("cid"), array.getJSONObject(i).getString("type"), imageMap.get(i));
            categories.add(c);
        }
        return categories;
    }

    private void setAdapterCategory(List<Category> categories) throws JSONException {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, indexFragment, fragmentIndexBinding);
        fragmentIndexBinding.recyclerView.setAdapter(categoryAdapter);
    }

    private void loadImageMap() {
        imageMap = new TreeMap<>();
        imageMap.put(1, "http://images.samsung.com/is/image/samsung/p5/nz/tablets/brilliant-screen-big-entertainment.png?$ORIGIN_PNG$");
        imageMap.put(2, "https://cnet2.cbsistatic.com/img/C3RPtt8a_n1be4azT8jokd9vhsM=/1600x900/2016/07/21/d90577a0-8dc3-426a-889f-b3c29bbc9b17/4-laptops-dan-02.jpg");
        imageMap.put(3, "http://www.argos.co.uk/wcsstore/argos/en_GB/images/promo/4k-tv/4k-tv-intro.png");
        imageMap.put(4, "http://crunchwear.com/wp-content/uploads/2016/09/Smartwatch.jpg");
        imageMap.put(0, "https://support.apple.com/library/content/dam/edam/applecare/images/en_US/iphone/iphone-6splus-colors.jpg");
    }

    public View.OnClickListener categoryCardClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = indexFragment.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ProductFragment productFragment = new ProductFragment();
                Category category = (Category) view.getTag();
                Integer cid = category.getCid();
                String type = category.getType();
                Bundle bundle = new Bundle();
                bundle.putInt("cid", cid);
                bundle.putString("ctype", type);
                productFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, productFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
    }
}