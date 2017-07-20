package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.anjanibajaj.shophere.ProductDetailsFragment;
import com.example.anjanibajaj.shophere.ProductFragment;
import com.example.anjanibajaj.shophere.R;
import com.example.anjanibajaj.shophere.adapters.ProductsAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentProductBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.DatabaseAsync;
import com.example.anjanibajaj.shophere.utils.VolleyNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private List<Product> cproducts;
    private List<String> images;
    private Map<Integer, List<String>> imageList;

    public ProductViewModel(Product product, ProductFragment productFragment, FragmentProductBinding fragmentProductBinding) {
        this.product = product;
        this.productFragment = productFragment;
        this.fragmentProductBinding = fragmentProductBinding;
    }

    @Bindable
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

    @Bindable
    public String getPrice() {
        return "$"+String.valueOf(product.getPrice());
    }

    public void setPrice(String price){
        product.setPrice(Integer.valueOf(price));
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

    @Bindable
    public Integer getPid(){
        return product.getPid();
    }

    public void setPid(Integer pid){
        product.setPid(pid);
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
//                        Log.d("R:", response);
                        try {
                            cproducts = parseJSONProduct(response, cid);
                            setAdapterProduct(cproducts);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productFragment.getActivity().getApplicationContext(), "Connection Error: Cannot reach the server!", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
    This method pares the JSON response received from the Inventory table in MongoDb.
    It creates a database using Room Persistence storage library, DatabaseAsync class is a new Thread that adds all the products entries in ProductTable, which will
    be used to display the products throughout the application,
    Returns the List of products according to a particular category.
     */
    private List<Product> parseJSONProduct(String response, Integer cid) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray array = jsonObject.getJSONArray("result");
        List<Product> products = new ArrayList<>();
        List<Integer> productIdList = new ArrayList<>();
        for(int i=0; i<array.length(); i++) {
            productIdList.add((Integer) array.getJSONObject(i).get("id"));
        }
        loadProductImageMap(productIdList);
        loadProductDetailsImageMap(productIdList);
        for(int i=0; i<array.length(); i++) {
            Product p = new Product(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getString("category"), (Integer) array.getJSONObject(i).get("price"),
                    (Integer) array.getJSONObject(i).get("id"), (Integer) array.getJSONObject(i).get("cid"), imageMap.get(array.getJSONObject(i).get("id")), imageList.get(array.getJSONObject(i).get("id")));
            products.add(p);
            productIdList.add((Integer) array.getJSONObject(i).get("id"));
        }

        new DatabaseAsync(products, productFragment.getActivity().getApplicationContext()).execute();

        List<Product> cproducts = new ArrayList<>();
        Log.d("Size of products", String.valueOf(products.size()));
        for (int i=0; i<products.size(); i++) {
            if (Objects.equals(products.get(i).getCid(), cid)) {
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
                case 127: imageMap.put(i, "https://store.storeimages.cdn-apple.com/4974/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone7/plus/iphone7-plus-black-select-2016?wid=300&hei=300&fmt=png-alpha&qlt=95&.v=1472430090682");
                    break;
                case 125: imageMap.put(i, "http://www.samsung.com/uk/consumer-images/product/galaxy-gear/2014/SM-V7000ZKABTU/SM-V7000ZKABTU-71980-0.jpg");
                    break;
                case 123: imageMap.put(i, "http://i-loveshare.com/wp-content/uploads/2016/02/sony-4k-tv.jpg");
                    break;
                case 124: imageMap.put(i, "http://4k.com/wp-content/uploads/2015/08/lg-65eg9600-2-1500x1000.jpg");
                    break;
                case 121: imageMap.put(i, "http://www.img.lirent.net/2012/10/ipad2-mini-apple-new-imac-software-computer-design-ipad-iphone-free-ios07.jpg");
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
        Glide.with(view.getContext()).load(url).crossFade(2).into(view);
    }

    private Product getProductDetails(Integer pid){
        for (Product p: cproducts) {
            if (p.getPid() == pid){
                return p;
            }
        }
        return null;
    }

    public View.OnClickListener productCardClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = productFragment.getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                Product product = (Product) view.getTag();
                Bundle bundle = new Bundle();
                bundle.putInt("pid", product.getPid());
                bundle.putString("name", product.getName());
                bundle.putInt("price", product.getPrice());
                bundle.putString("category", product.getCategory());
                bundle.putString("image", product.getImageUrl());
                bundle.putStringArrayList("imageList", (ArrayList<String>) product.getImageList());
                productDetailsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, productDetailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
    }

    private void loadProductDetailsImageMap(List<Integer> productIdList) {
        imageList = new TreeMap<>();
        List<String> images;
        for (Integer i: productIdList) {
            switch (i){
                case 120:
                    images = new ArrayList<>();
                    images.add("https://store.storeimages.cdn-apple.com/4662/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone6s/gray/iphone6s-gray-select-2015?wid=1200&hei=630&fmt=jpeg&qlt=95&op_sharpen=0&resMode=bicub&op_usm=0.5,0.5,0,0&iccEmbed=0&layer=comp&.v=kqNjH3");
                    images.add("https://cnet2.cbsistatic.com/img/8YI9KWWu59TgqpOnOT7ev4Zww6M=/270x203/2017/01/24/a5a951ec-acfa-4ecb-9e2c-2ebaf61d0283/oneplus-3t-header.jpg");
                    imageList.put(i, images);
                    break;
          default:
                    images = new ArrayList<>();
                    images.add("http://www.mystore.no/wp-content/themes/Avada-child-2.0/images/2014/495.png");
                    imageList.put(i, images);
            }
        }
    }
}
