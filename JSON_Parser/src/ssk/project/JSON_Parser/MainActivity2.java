public class MainActivity2 extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String x = readTwitterFeed();
		String y = writeJSON();
		TextView tv = (TextView) findViewById(R.id.textview1);

		try {
			JSONArray jsonArray = new JSONArray(x);
			Log.i(MainActivity2.class.getName(),
				"Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				y += jsonObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tv.setText(y);
	}

	public String readTwitterFeed() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http:'//twitter.com/users/show/vogella.json");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readline()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(MainActivity2.class.toString(), "Failed to download file");
			}
		} catch (ClientProtocolExpcetion e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public String WriteJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("name", "Jack Hack");
			object.put("score", new Integer(200));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}
}