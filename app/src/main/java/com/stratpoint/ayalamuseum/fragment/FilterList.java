package com.stratpoint.ayalamuseum.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stratpoint.ayalamuseum.R;
import com.stratpoint.ayalamuseum.databinding.FragmentFilterListBinding;
import com.stratpoint.ayalamuseum.manager.DatabaseManager;
import com.stratpoint.ayalamuseum.manager.ManagerFactory;
import com.stratpoint.ayalamuseum.database.object.Filter;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterList extends BaseFragment {
    private FragmentFilterListBinding fragmentFilterListBinding;
    private DatabaseManager databaseManager;

    public FilterList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentFilterListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_list, container, false);
        return fragmentFilterListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseManager = (DatabaseManager) ManagerFactory.createManager(
                    getActivity(), ManagerFactory.TYPE_DATABASE);

        fragmentFilterListBinding.filterList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        fragmentFilterListBinding.filterList.setAdapter(new ImageFilterAdapter());
    }

    private class ImageFilterAdapter extends RecyclerView.Adapter<ImageFilterAdapter.ViewHolder> {
        private Filter[] filters;

        public ImageFilterAdapter() {
            filters = databaseManager.getDefaultFilters();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_filter, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Filter filter = filters[position];

            holder.filterName.setText(filter.getName());
            Picasso.with(getActivity()).load(filter.getResourceId()).into(holder.filterImage);
        }

        @Override
        public int getItemCount() {
            return filters.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView filterImage;
            public TextView filterName;

            public ViewHolder(View itemView) {
                super(itemView);
                filterImage = (ImageView) itemView.findViewById(R.id.filterImage);
                filterName = (TextView) itemView.findViewById(R.id.filterName);
            }
        }
    }
}
