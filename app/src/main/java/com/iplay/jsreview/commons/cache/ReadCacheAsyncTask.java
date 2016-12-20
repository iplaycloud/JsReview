package com.iplay.jsreview.commons.cache;

import android.content.Context;
import android.os.AsyncTask;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Author : iplay
 * Mail：iplaycloud@gmail.com
 * Description：
 */
public class ReadCacheAsyncTask<T> extends AsyncTask<String, Void, T> {

    private WeakReference<Context> mContext;
    private OnReadCacheToDo<T> onReadCacheToDo;

    public ReadCacheAsyncTask(Context context) {
        mContext = new WeakReference<Context>(context);
    }

    public void setOnReadCacheToDo(OnReadCacheToDo<T> onReadCacheToDo) {
        this.onReadCacheToDo = onReadCacheToDo;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (onReadCacheToDo != null) {
            onReadCacheToDo.preExecute();
        }
    }

    @Override
    protected T doInBackground(String... params) {
        if (mContext.get() != null) {
            Serializable seri = CacheHelper.readObject(mContext.get(), params[0]);
            if (seri == null) {
                return null;
            } else {
                return (T) seri;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(T data) {
        super.onPostExecute(data);
        if (onReadCacheToDo != null) {
            onReadCacheToDo.postExectue(data);
        }
    }

    public interface OnReadCacheToDo<T> {
        void preExecute();

        void postExectue(T data);
    }
}
