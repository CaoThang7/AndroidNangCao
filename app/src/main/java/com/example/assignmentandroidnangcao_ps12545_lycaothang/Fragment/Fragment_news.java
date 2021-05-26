package com.example.assignmentandroidnangcao_ps12545_lycaothang.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignmentandroidnangcao_ps12545_lycaothang.Customadapter;
import com.example.assignmentandroidnangcao_ps12545_lycaothang.DocBao;
import com.example.assignmentandroidnangcao_ps12545_lycaothang.Main2Activity;
import com.example.assignmentandroidnangcao_ps12545_lycaothang.R;
import com.example.assignmentandroidnangcao_ps12545_lycaothang.XMLDOMParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Fragment_news extends Fragment {
    ListView lv;
    Customadapter customadapter;
    ArrayList<DocBao> mangdocbao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,container,false);

        lv=view.findViewById(R.id.lv);
        mangdocbao=new ArrayList<DocBao>();

        new Readdata().execute("https://vnexpress.net/rss/the-gioi.rss");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),Main2Activity.class);
                intent.putExtra("link",mangdocbao.get(position).link);
                startActivity(intent);
            }
        });



        return view;
    }


    class Readdata extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser=new XMLDOMParser();
            Document document=parser.getDocument(s);
            NodeList nodeList=document.getElementsByTagName("item");
            NodeList nodeListdescription=document.getElementsByTagName("description");
            String hinhanh="";
            String title="";
            String link="";
            for( int i=0;i<nodeList.getLength();i++){
                String cdata=nodeListdescription.item(i + 1).getTextContent();
                Pattern p=Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher=p.matcher(cdata);
                if(matcher.find()){
                    hinhanh=matcher.group(1);
//                    Log.d("hinhanh",hinhanh +"........"+ i);
                }
                Element element=(Element) nodeList.item(i);
                title+=parser.getValue(element,"title");
                link=parser.getValue(element,"link");
                mangdocbao.add(new DocBao(title,link,hinhanh));

            }

            customadapter=new Customadapter(getActivity(),android.R.layout.simple_list_item_1,mangdocbao);
            lv.setAdapter(customadapter);
            super.onPostExecute(s);

        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
