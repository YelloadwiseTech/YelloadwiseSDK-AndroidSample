package ir.yelloadwise.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.yelloadwise.sample.BuildConfig;
import ir.yelloadwise.sample.R;
import ir.yelloadwise.sample.enums.ListItemType;
import ir.yelloadwise.sample.model.ItemList;
import ir.yelloadwise.app.nativeads.YelloadwiseNativeBannerManager;
import ir.yelloadwise.app.nativeads.YelloadwiseNativeBannerViewManager;

public class NativeBannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_AD = 1;
    private final Context context;
    private final LayoutInflater inflater;
    private final List<ItemList> items;

    public NativeBannerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    public void updateItem(List<ItemList> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_AD) {
            return new YelloadwiseListItemAdHolder(
                    inflater.inflate(R.layout.list_ad_item, parent, false), context);
        } else {
            return new ItemHolder(inflater.inflate(R.layout.list_item, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).listItemType == ListItemType.ITEM) {
            return VIEW_TYPE_ITEM;
        }

        return VIEW_TYPE_AD;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            ((ItemHolder) holder).bindView(position);
            return;
        }

        ((YelloadwiseListItemAdHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvTitle;

        ItemHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        void bindView(int position) {
            tvTitle.setText(items.get(position).title);
        }
    }

    public class YelloadwiseListItemAdHolder extends RecyclerView.ViewHolder {
        private final YelloadwiseNativeBannerViewManager nativeBannerViewManager;

        YelloadwiseListItemAdHolder(View itemView, Context context) {
            super(itemView);
            FrameLayout adContainer = itemView.findViewById(R.id.adContainer);
            nativeBannerViewManager = new YelloadwiseNativeBannerManager.Builder()
                    .setParentView(adContainer)
                    .setContentViewTemplate(R.layout.yelloadwise_content_banner_ad_template)
                    .inflateTemplate(context);
        }

        void bindView(int position) {
            YelloadwiseNativeBannerManager.bindAd(
                    context,
                    nativeBannerViewManager,
                    BuildConfig.YELLOADWISE_NATIVE_BANNER,
                    items.get(position).id);
        }
    }
}
