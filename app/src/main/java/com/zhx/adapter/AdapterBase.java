package com.zhx.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

public abstract class AdapterBase<T> extends BaseAdapter {
	
	private final List<T> mList = new LinkedList<T>();
	
	public List<T> getList(){
		return mList;
	}
	
	public void appendToList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(list);
		notifyDataSetChanged();
	}

	public void appendToTopList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(0, list);
		notifyDataSetChanged();
	}

	public void clear() {
		mList.clear();
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(position > mList.size()-1){
			return null;
		}
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (position == getCount() - 1) {
			onReachBottom();
		}
//		return getExView(position, convertView, parent);
		return getViewHolder(convertView, parent, position).getConvertView();
	}
	

//	protected abstract View getExView(int position, View convertView, ViewGroup parent);
	protected abstract void onReachBottom();
	protected abstract ViewHolder getViewHolder(View convertView, ViewGroup parent, int position);
    
    public static class ViewHolder {
            private final SparseArray<View> views;
            private View convertView;

            private ViewHolder(Context mContext, View convertView) {
                    this.views = new SparseArray<View>();
                    this.convertView = convertView;
                    convertView.setTag(this);
            }

            public static ViewHolder get(Context mContext, View convertView, ViewGroup parent, int resId) {
                    if(convertView == null) {
                            LayoutInflater factory = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            convertView = factory.inflate(resId, parent, false);//
                            return new ViewHolder(mContext, convertView);
                    } 
                    return (ViewHolder) convertView.getTag();
            }

            @SuppressWarnings("unchecked")
            public <T extends View> T findViewById(int resourceId) {
                    View view = views.get(resourceId);
                    if (view == null) {
                            view = convertView.findViewById(resourceId);
                            views.put(resourceId, view);
                    }
                    return (T) view;
            }

            public View getConvertView() {
                    return convertView;
            }
            public void setConvertView(View convertView) {
                    this.convertView = convertView;
            }
    }
    
    public void updateView(AbsListView absView ,int itemIndex) {
            int firstVisiblePosition = absView.getFirstVisiblePosition();
            int lastVisiblePosition =absView.getLastVisiblePosition();
            if(itemIndex >= firstVisiblePosition && itemIndex <= lastVisiblePosition) {
                    View view = absView.getChildAt(itemIndex - firstVisiblePosition);
                    int type = getItemViewType(itemIndex); 
                    if(view.getTag() instanceof ViewHolder){
                            ViewHolder mViewHolder = (ViewHolder)view.getTag();
                            updateView(itemIndex, type, mViewHolder);
                    }
            }
    }
    public void updateView(int index ,int type, ViewHolder mViewHolder){};

}
