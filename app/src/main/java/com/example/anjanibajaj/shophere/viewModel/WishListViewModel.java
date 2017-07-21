package com.example.anjanibajaj.shophere.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.anjanibajaj.shophere.WishListFragment;
import com.example.anjanibajaj.shophere.adapters.WishListAdapter;
import com.example.anjanibajaj.shophere.databinding.FragmentWishListBinding;
import com.example.anjanibajaj.shophere.model.Product;
import com.example.anjanibajaj.shophere.utils.FetchProducts;
import com.example.anjanibajaj.shophere.utils.SessionManager;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anjani Bajaj on 7/19/2017.
 *
 */

public class WishListViewModel extends BaseObservable {
    private Product product;
    private WishListFragment wlf;
    private FragmentWishListBinding fwlb;

    public WishListViewModel(Product product, WishListFragment wlf, FragmentWishListBinding fwlb) {
        this.product = product;
        this.wlf = wlf;
        this.fwlb = fwlb;
    }

    @Bindable
    public String getImageUrl() {
        return product.getImageUrl();
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).centerCrop().into(view);
    }

    @Bindable
    public Product getProduct(){
        return this.product;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public void getListDetails() throws ExecutionException, InterruptedException {
        SessionManager sessionManager = new SessionManager(wlf.getActivity());
        Set<String> sessionPidList = sessionManager.getProductDetails(SessionManager.WPIDLIST).get(SessionManager.WPIDLIST);
        if (sessionPidList != null) {
            List<Product> wishProducts = new FetchProducts(sessionPidList, wlf.getContext()).execute().get();
            WishListAdapter wishListAdapter = new WishListAdapter(wishProducts, wlf, fwlb);
            fwlb.recyclerView4.setAdapter(wishListAdapter);
            //noinspection ConstantConditions
        }
    }

    public View.OnClickListener onAddtocartClicked(){
        return new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                SessionManager sessionManager= new SessionManager(wlf.getActivity());
                sessionManager.addTocart(product.getPid());
                sessionManager.removeFromWishList(product.getPid());
                try {
                    getListDetails();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                Snackbar.make(wlf.getView(), product.getName()+ " moved to cart",Snackbar.LENGTH_LONG).show();
            }
        };
    }
}