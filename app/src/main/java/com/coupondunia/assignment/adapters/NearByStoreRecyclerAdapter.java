package com.coupondunia.assignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coupondunia.assignment.R;
import com.coupondunia.assignment.model.NearByStoreModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Aabid Mulani
 * {05-10-2015}
 */
public class NearByStoreRecyclerAdapter extends RecyclerView.Adapter<NearByStoreRecyclerAdapter.StoreViewHolder> {

    private final DisplayImageOptions displayImageOptions;
    private ArrayList<NearByStoreModel> storeList = new ArrayList<>();
    private Context context;

    public NearByStoreRecyclerAdapter(Context context, ArrayList<NearByStoreModel> storeList) {
        this.storeList = storeList;
        this.context = context;
        displayImageOptions = getDisplayOptions();
    }


    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_store, viewGroup, false);
        StoreViewHolder viewHolder = new StoreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StoreViewHolder storeViewHolder, int i) {
        NearByStoreModel storeModel = storeList.get(i);

        storeViewHolder.titleTextView.setText(storeModel.getBrandName());
        storeViewHolder.couponsTextView.setText(storeModel.getCouponCountMsg());
        storeViewHolder.categoryTextView.setText(Html.fromHtml(storeModel.getCategoryString()));
        storeViewHolder.locationTextView.setText(storeModel.getComputeDistanceFromCurrentLocationString() + " " + storeModel.getCityName());

        ImageLoader.getInstance().displayImage(storeModel.getLogoURL(), storeViewHolder.imageView, displayImageOptions);

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.storeImageView)
        ImageView imageView;

        @Bind(R.id.storeNameTextView)
        TextView titleTextView;

        @Bind(R.id.offerCountTextView)
        TextView couponsTextView;

        @Bind(R.id.categoryTextView)
        TextView categoryTextView;

        @Bind(R.id.locationTextView)
        TextView locationTextView;

        public StoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    // get display settings for Universal Image Loader
    public DisplayImageOptions getDisplayOptions() {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.empty_image_drawable)
                .showImageForEmptyUri(R.drawable.empty_image_drawable)
                .showImageOnFail(R.drawable.empty_image_drawable)
                .resetViewBeforeLoading(false)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

}
