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
                    images.add("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxAQERIRDxAVEBIRFRARFhAWEBUQFxURFRUYFhUSExMYHiggGRolGxMTITEiJSkrOi46Fx8zODM4NyguLisBCgoKDg0OFw8QGyslICIuKzc3NzctNTUuLzcvLS0rMS03LS0rKysrLSstLSstLSsrLTA3LSswKy0rNzcrKysvK//AABEIAMUBAAMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAABQYBAwQCB//EAD0QAAIBAgMFBQQHCAIDAAAAAAABAgMRBCExBRJBUWEGEyJxgTJSkcEjQkNicpKhFFOCorHR4fAzNGOj0v/EABgBAQEBAQEAAAAAAAAAAAAAAAABBAMC/8QAHxEBAAIDAAEFAAAAAAAAAAAAAAECAxEhEgQTIjFB/9oADAMBAAIRAxEAPwD7KAAAAAAAAAAAAAAAACMw20ZVZtRsoKU4J2u3uOzfk2mSCqZpPjoB7AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADKMACvbCyuuVauv55EvWecPP5Mh9ke1UXLEV1/7JEnVqX3cmmppWfk8+TXkVHUqljZGSehyyZhStoFdgNVKsnk8mbSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKBg9sReKxmElJ05d9iN2SdpeKUs4vg7Sujd2aw+Pw85UcTLvqN9+Ndycm5by4vg03k3dNZXTube1nZiDrPGQi/Eo97ue3CUVaNeKWuSSa6J8zxg9sypbtPEtbst1wxCa3JK+TfuvnwKi0yZprV4ws5OyclG/BN5K74Xdl5tHpy/v6czDz1zWlunIDGHrwqRjOnKM4SV4zi1KMlzTWTR1Uq7WTzR8rx1TaGyKs3CTqYWdRzTcVKHildwl+7lnbKyeq5L6HsvHRxFGnWinFVIqST1V+AE1GSeaMnBCbWh10qyl0fL+xFbAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACG2nsGE7unGCbvvU5R8E76/hfVeq4kyAKBTWIwcnGjGVSnHOWCm/HBe9QnxXldE7sratHExcqUruPtU2t2cHynHh5kttWlRlC9eyUc1O9pRfODWd/68cj5dW2phq+LcaGIlTxFJNxrwgldX+1ayb4NXs8/So+ktJqzV08mnmmuTPOGoQpxUKcI04K9oRioRV227RWSu236ld2d2mcZKjj1GjN5Rrr/AIqnn+7l0eRZQMmGweWwOijjLZTzXP8AudkZJq6zXMhpsxSxMoPLTiuDIqbBowuLjU0yfGL1/wAo3gAAAAAAAAAAAAAAAAAAAAAAAAADzOajm3b+/JAeiN2ztqjhabnUmopXzb/Rc2Ru2u0W7LuMPB168tKUM7daktEji2Z2VlWmsRjpLE1VnGOtClbhCP2slz0XmgK5i4Y/bMlbfwuEldKTX0tZf+OHCP3nZaPMsmE7J4fA4fdhBKU3GNr3bespTnrKW5GfRcNEy30aEYaZt2vJ6u3Pp0WSI/ar3pxj7kXNrrJ7sWvSNT4lRX4bC73f3YxlFJJ0p+xK/CL+o8v0WmpGYeeJwLcaSlXox9rBzf01Fc6MvrR6Zp8OZetmUt2H4m38vkMfgKddWms1fdmnuyj+GXy0fFEVE7L2nRxMO8oT3lo1pKD92cdUzqkyr7Z2FUoT7+E+5qLTFwj4ZL3cTS0t1068Dq2b2g3pKji4rD13lF3+iq9aU+D+6yomJM0yZsmaZMDw5NZp2a46Elg9rfVq/n/+l8yJrSsm9bZ2NbldX55gW9O+az6gq+C2hOlp4o+6/k+BYsLioVFeL5Nxeq80RW4AAAAAAAAAAAAAAAAAAAYnNLX/AHyRW8d2ilKapYWm6s3ws0kuO8+fThxaAncVjI04ttpJcXov7lexNSrjFuw+joyydWUd6VRa2pQ4876LU7MNsudaW/XaqNaQ1owtwt9pJeiXmjd2g2pDA0J1F46tlGN825PKKdtI3zsrLU9VrNp1CTOmihh8FgIqNWpToKo81Oot+o+dWTzl5aLTPI8Ynttg4SlGLlUUNZQheKzSSTdr5tL1R8R2ntCtiazlKUqs3K11m5T0Vl6LJFveyKlKlSjP26koOpd33Ie005Xs9EvW+ribJ9PWkfKeuM5Z2vOze10q7jTjh3GrVn9FFvw9wvarTl0V3bjkuNyRr0+9dRu9nJxtdp+Fbtrrhk5fxHFsXZlWhOeJmkvoXTjTablk1KOd/Ddq1rNu60tnN0MMoWSzsopvnupK/wDKjJfW+OtdzDfCNklySRkA8PQ0VfbfZGlUvKlThK6cXQl7O63dqlL7N3SdtMloWgAfN8Pja+EbhJTxNCGUqcv+zQXr/wAkP9TJ3C4unWgqlGaqQf1lwfuyWsX0ZPbT2XSxCW+mpx9mrHKcfJ8V0d0UXa+xa+EqOtTkqM3Zd/GN6NXlHE0vqvr1yZUTdWKaaejNKha2emRwbO25GrJUa0f2fEcKbd4VOtGp9b8LzJGYGqOnkbadaUN2cHZxbXzs+mZqtqZ+rLo0/l80BbsFiVVhGayvw5NZNG4huzFW8Jx92V/SS/wTJFAAAAAAAAAAAAAA5cTj4QTd1aOUpv2Yvk3z6ED2825Uw9OFHDf9jEtxjL3KUbd5U8/FGK6yvwNPZvYM5xjUxk3UjH2MPpD8U4/W8mB3yxNTEf8AE3TpPLv2ryn0ow4+enHPMktn7KhTVt2yeqb3pT61Z8fwrLXVG2lVjCNStN2SclezbUIeFxSWdt6MnZczqo14TgqkZJwcVJSvlutbyd+VmmVHNtXHxw9Peau2404RXGcslFeib8kz5N252vKtUjThJZK+vFr6NrzTcs9Fuli7d9pYVVClhJb0qc1U79WcU9ycEqfvPxvPTLzca72X2W6leLjBTkm4q6unNqzqS6RS/lguKN+DHFKe5ZkyZ4m3jXrr7F9mZxkrQTlFK8/d3o+JX4N3fwS5l/qdmcNKcZ1IucouLtKzjeKaSStpm/iySwOEjRgoQ0WbfGUnrJ9WbzLkyze23amKIjvWjFZuEeclJ+UPFf8AMoL+I2GpPeqN67i3P4m7zT9FTNpxdgAAAAAMSimmmk00001dNPVNGQBT+0XY2FSD7iKlHX9nk7WfOhN+w+jy6rQquH2tXwjcMSp4ihDwupuv9oodKsNZxXPU+tEbtjYtHEq8041ErRrRykuj96PR/wBcwKzQrwqwVSlONSnLScXdPp0fRnunxXNNfP5Fc2psLE7PqOpRap77zkk3h6/JVIfZz/1NnfsfblOtNQlF0MQtaEnrzlSnpOP6lRY+zVW1WUfei/inf+lyzFLwFXu8RTvl4930eXzLoRQAAAAAAAAAAAA5JZvJLNvoBRsXD9p2pU4qhGlQXK9t+TX8VRr+Eus5KnBu3hhFuy5RV/kU/sPF1ZVMQ9a06lbP70m0vTeXwLbilfdj784x84rxyT84wkvUqIztLVq4bBqULb8HSTd0s3JKTz1bbfxZ8wWFjGMuFPJqLWStZJJN52svhd8z6X2zrLcjDcUpJ76m1fclZ23Y8ZNb2eiSbfBOj1MLKpJbsXOcmlG7bvN6Nvlk/RPllpwzER1h9VuZ1Dk2Xgp4irGEbp3bfFwjxk/vWf6qPGV/pfZ3YEMI5yWs92EVe+5TjouspPOT8lpFGOzew4YaCy3pPNyazb1v6Z5dSWq1lHJLek81FcubfBa59MrvI85s03nX47YMEUjc/b3Oairt2S4vI525T5wh8JS+cF+vkZjSbe9N70lovqx/CufV9dFkbTO0sQikkkkktElZGQAAAAAAAAAAAA81acZJxlFSjJNOLSaaeqaeqKdtnsXG/eYeO/u+KNCUknCXOlUlp5N+uiLmAPmmxsJtHvoUalOVWCkvpqtJwnSjfPela0rLSzzPphgAAAAAAAAAAAAIjtdiu6wVeSdnKHdLnvVWqaa8t6/oS5VO3lXe/ZaC+0quo192nG1n61Iv0A6uy8I0KCcslb+kXKX6Il8FGTlTU85Qp70uP0k8rr8tVeppw0VCjCDg5qo1BpK6UZXvKd9IqKz+GrSOzB5upPnNxXlBbrX5lU+JUQ+1ME6s7yzSvZevzyXp0OvZey4wSbXizd7c+XLRL0JCruRzlxyS1bfJLia3Bz9vKP7vX874+Wnnky7Txje2XWcsqeUf3lr/AJFx89PM9U6ajpxzb1bfNviz0Dy9AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABStqy77ae6s1h6dOFvvz+kf6Th8C6ldw+y3TxlWrL2as4zUum6lZ+TVgJ6UlCLb9mCbflFf4PFCbjCMElKaS339VTecm3xd23Zfpe5sqwUouMtJJp5tXT1V0eopJWVklwWRR5hTs7t70nrJ8uS5Loj2Li5AAuLgALi4AC4AAAAAAAAAAAAAAAAAAAAAAAAAAADEkeHB8zYANW4xuM2gDXusbrNgA8brG6z2APG6ZsegB5sLHoAYsLGQBixkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA//2Q==");
                    images.add("https://store.storeimages.cdn-apple.com/4974/as-images.apple.com/is/image/AppleInc/aos/published/images/r/ef/refurb/iphone6s/refurb-iphone6s-plus-silver?wid=1000&hei=1000&fmt=jpeg&qlt=95&op_sharpen=0&resMode=bicub&op_usm=0.5,0.5,0,0&iccEmbed=0&layer=comp&.v=1476121780367");
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
