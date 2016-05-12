package com.example.fotoramka.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fotoramka.Adapter.FileAdapter;
import com.example.fotoramka.HttpMethod.HttpPropFind;
import com.example.fotoramka.Model.FileFolder;
import com.example.fotoramka.Model.FileObject;
import com.example.fotoramka.Model.WebDav;
import com.example.fotoramka.R;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;


public class FolderActivity extends Activity {

    ListView listView;
    List<FileFolder> _list;
    TextView textVal;
    String _path;
    String _catolog ="/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_layout);
        listView = (ListView)  findViewById(R.id.listViewFolder);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),GalleryActivity.class);

                FileFolder  selectedFolder = _list.get(position);

                if(!selectedFolder.getClass().equals(FileObject.class))
                {
                    _catolog = selectedFolder.GetPath();
                      ShowDirectory();
                    return;
                }

                _path=selectedFolder.GetPath();
                intent.putExtra("file",_path);

                 if(!_path.contains(".jpg") & !_path.contains(".png") & !_path.contains(".bmp"))
                 {
                     Toast tos =   Toast.makeText(getApplicationContext(),"Невозможно открыть данный тип файла",Toast.LENGTH_LONG);
                     tos.setGravity(Gravity.CENTER, 0,0);
                     tos.show();
                     return;
                 }
                intent.putStringArrayListExtra("files",GetFiles());
                startActivity(intent);
            }
        });
        ShowDirectory();

    }

    private ArrayList<String> GetFiles() {
        ArrayList<String> list = new ArrayList<String>();

        for(int i=0; i<_list.size(); i++)
        {
            list.add(_list.get(i).GetPath());
        }
        return list;
    }

    private void ShowDirectory() {
        DirectoryTask task  = new DirectoryTask();
        task.execute();
        _list = new ArrayList<FileFolder>();

        try {
            _list = task.get();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if(_list.size()>0)
        {
            FileAdapter adap  = new FileAdapter(_list,this);
            listView.setAdapter(adap);
        }
    }


    class DirectoryTask extends AsyncTask<Void, Void, List<FileFolder>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected  List<FileFolder> doInBackground(Void... params) {

            List<FileFolder> result = new ArrayList<FileFolder>();
            try {
                HttpClient _client = new DefaultHttpClient();
                HttpPropFind request = new HttpPropFind("https://webdav.yandex.ru"+_catolog);
                request.setHeader("Accept","*/*");
                request.setHeader("Depth","1");
                request.setHeader("Authorization","OAuth "+ WebDav.CLIENT_SECRET);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(_client.execute(request).getEntity().getContent());
                Element elem = doc.getDocumentElement();
                NodeList nl = elem.getElementsByTagName("d:response");

                if(nl != null && nl.getLength() > 0) {
                    for(int i = 0 ; i < nl.getLength();i++) {
                        Element el = (Element)nl.item(i);
                        result.add( getFile(el));
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<FileFolder> result) {
            super.onPostExecute(result);

        }

        private FileFolder getFile(Element el) {
            String name = getTextValue(el,"d:displayname");
            String path = getTextValue(el,"d:href");
            String  creationDate = getTextValue(el,"d:creationdate");
            String  lastModifiedDate = getTextValue(el,"d:getlastmodified");
            String folder = getTextValue(el,"d:getcontenttype");

            if(folder!=null) // если не папка
            {
                FileObject file = new FileObject(name,path,creationDate,lastModifiedDate);
                return  file;
            }
            return new FileFolder(name,path,creationDate,lastModifiedDate);
        }

        private String getTextValue(Element ele, String tagName) {
            String textVal = null;
            NodeList nl = ele.getElementsByTagName(tagName);
            if(nl != null && nl.getLength() > 0) {
                Element el = (Element)nl.item(0);
                textVal = el.getFirstChild().getNodeValue();
            }

            return textVal;
        }
    }




}
