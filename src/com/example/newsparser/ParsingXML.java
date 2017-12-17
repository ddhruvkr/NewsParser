package com.example.newsparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;



import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ParsingXML extends Activity {

	String urls, hl, pd, pd1;
	ProgressDialog pDialog;
	ListView adpt;
	ArrayList<String> headlines = new ArrayList<String>();
	ArrayList<String> links = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parsing_xml);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		urls = b.getString("src");
		adpt = (ListView) findViewById(R.id.listView1);
		InternetConnection obj = new InternetConnection(getApplicationContext());
		Boolean internet = obj.isConnectingToInternet();
		if(internet)
		new getFeed().execute();
		else {
			Toast.makeText(getApplicationContext(), "No Internet Connection!",
					   Toast.LENGTH_LONG).show();
		}
		
	}
	
	private class getFeed extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(ParsingXML.this);
	        pDialog.setMessage("Fetching News..");
	        pDialog.setCancelable(false);
	        pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			try {
				URL url = new URL(urls);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			    conn.setReadTimeout(10000);
			    conn.setConnectTimeout(15000);
			    conn.setRequestMethod("GET");
			    conn.setDoInput(true);
			    conn.connect();
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(false);
				XmlPullParser xpp = factory.newPullParser();
				InputStream stream = conn.getInputStream();
				xpp.setInput(stream, null);
				//xpp.setInput(getInputStream(url), "UTF_8");
				boolean insideItem = false;
				int event = xpp.getEventType();
				while(event != XmlPullParser.END_DOCUMENT){
					if(event == XmlPullParser.START_TAG){
						if(xpp.getName().equalsIgnoreCase("item")){
							insideItem = true;
						}
						else if(xpp.getName().equalsIgnoreCase("title")){
							if(insideItem)
								hl = xpp.nextText();
							//headlines.add(xpp.nextText());
						}
						else if(xpp.getName().equalsIgnoreCase("pubDate")){
							if(insideItem){
								pd = xpp.nextText();
								int count = 0;
								for(int i = 0; i < pd.length(); i++){
									if(pd.charAt(i) == ','){
										count++;
										if(count == 3){
											pd1 = pd.substring(i+2, i+7);
											break;
										}
									}
								}
								headlines.add(hl + "\n" + pd1);
							}
						}
						else if(xpp.getName().equalsIgnoreCase("link")){
							if(insideItem)
								links.add(xpp.nextText());
						}
					}else if(event == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
			            insideItem=false;
					}
					event = xpp.next();
				}
				
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ParsingXML.this, R.layout.custom_textview, headlines);
			adpt.setAdapter(adapter);
			adpt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Uri uri = Uri.parse((String)links.get(arg2));
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				}
			});
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parsing_xml, menu);
		return true;
	}

}
