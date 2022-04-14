package jp.ac.jec.cm0146.rirekisyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowAnimationFrameStats;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Company> {
    private int mResource;
    private List<Company> mItems;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, int resource, List<Company> items) {
        super(context, resource, items);

        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null ) {
            view = convertView;
        } else {
            view = mInflater.inflate(mResource, null);
        }

        Company item = mItems.get(position);

        TextView title = (TextView)view.findViewById(R.id.listTitle);
        title.setText(item.getName());

        return view;
    }
}
