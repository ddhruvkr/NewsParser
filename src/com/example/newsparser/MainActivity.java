package com.example.newsparser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	String url;
	Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
	EditText et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button4);
		b5 = (Button) findViewById(R.id.button5);
		b6 = (Button) findViewById(R.id.button6);
		b7 = (Button) findViewById(R.id.button7);
		b8 = (Button) findViewById(R.id.button8);
		b9 = (Button) findViewById(R.id.button9);
		et = (EditText) findViewById(R.id.editText1);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		b9.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button1:
			url = "http://zeenews.india.com/rss/india-national-news.xml";
			Intent it1 = new Intent("com.example.newsparser.NP");
			it1.putExtra("src", url);
			startActivity(it1);
			break;
		case R.id.button2:
			url = "http://zeenews.india.com/rss/world-news.xml";
			Intent it2 = new Intent("com.example.newsparser.NP");
			it2.putExtra("src", url);
			startActivity(it2);
			break;
		case R.id.button3:
			url = "http://zeenews.india.com/rss/india-news.xml";
			Intent it3 = new Intent("com.example.newsparser.NP");
			it3.putExtra("src", url);
			startActivity(it3);
			break;
		case R.id.button4:
			url = "http://zeenews.india.com/rss/south-asia-news.xml";
			Intent it4 = new Intent("com.example.newsparser.NP");
			it4.putExtra("src", url);
			startActivity(it4);
			break;
		case R.id.button5:
			url = "http://zeenews.india.com/rss/business.xml";
			Intent it5 = new Intent("com.example.newsparser.NP");
			it5.putExtra("src", url);
			startActivity(it5);
			break;
		case R.id.button6:
			url = "http://zeenews.india.com/rss/sports-news.xml";
			Intent it6 = new Intent("com.example.newsparser.NP");
			it6.putExtra("src", url);
			startActivity(it6);
			break;
		case R.id.button7:
			url = "http://zeenews.india.com/rss/science-technology-news.xml";
			Intent it7 = new Intent("com.example.newsparser.NP");
			it7.putExtra("src", url);
			startActivity(it7);
			break;
		case R.id.button8:
			url = "http://zeenews.india.com/rss/general-elections-2014.xml";
			Intent it8 = new Intent("com.example.newsparser.NP");
			it8.putExtra("src", url);
			startActivity(it8);
			break;
		case R.id.button9:
			String e = et.getText().toString();
			if(e.compareTo("") != 0){
				Intent it9 = new Intent("com.example.newsparser.SE");
				it9.putExtra("src", e);
				startActivity(it9);
				break;
			}
		}
	}

}
