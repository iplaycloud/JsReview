package com.iplay.jsreview.review.view.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Description :
 * Created by iplay on 2016/12/19.
 * E-mail : iplaycloud@gmail.com
 */
public class SpinnerArrayAdapter extends ArrayAdapter {

    public SpinnerArrayAdapter(Context context, int resource, ArrayList list) {

        super(context, resource, list.toArray());
    }

}
