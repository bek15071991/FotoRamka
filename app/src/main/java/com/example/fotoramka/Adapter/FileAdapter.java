package com.example.fotoramka.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.fotoramka.Model.FileFolder;
import com.example.fotoramka.R;

import java.util.List;

public class FileAdapter extends BaseAdapter {

    private List<FileFolder> _list;
    private LayoutInflater _inflater;

    public FileAdapter(List<FileFolder> _list, Context context) {
        this._list = _list;
        _inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public Object getItem(int position) {
        return _list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    View tempView= convertView;
        if(tempView==null){
            tempView =_inflater.inflate(R.layout.item_layout,parent, false);
        }
        FileFolder folder = GetFolder(position);

       TextView textView = (TextView) tempView.findViewById(R.id.textDetail);


        textView.setText(folder.GetName());
        return tempView;
    }

    private FileFolder GetFolder(int pos)
    {
        return (FileFolder)getItem(pos);
    }
}
