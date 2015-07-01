package test.freelancer.com.fltest;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * List that displays the TV Programmes
 */
public class ListFragment extends Fragment {

    ListView plist;
    ListAdapter adapter;
    static ArrayList<HashMap<String, String>> progList;

    getPrograms task;
    ProgressDialog progress;

    public static final String KEY_RESULTS = "results";
    public static final String KEY_NAME = "name";
    public static final String KEY_STARTTIME = "start_time";
    public static final String KEY_ENDTIME = "end_time";
    public static final String KEY_CHANNEL = "channel";
    public static final String KEY_RATING = "rating";

    DatabaseHandler db;
    ProgramsDTO host = new ProgramsDTO();

    boolean flag;
    int currentPosition;

    TextView taptoref;

    public static boolean loadSuccess = true;

    //    public static boolean read = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ListView view = (ListView) inflater.inflate(R.layout.fragment_list, container, false);

        db = new DatabaseHandler(getActivity());
        View view = (View) inflater.inflate(R.layout.fragment_list, container, false);
        plist = (ListView) view.findViewById(R.id.listView);
        taptoref = (TextView) view.findViewById(R.id.refresh);
        taptoref.setVisibility(View.GONE);
        // eurgh, damn android.os.NeworkOnMainThreadException - so pesky!
        // stackoverflow told me to do this:
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        // download the program guide
//        String JsonResponse = connect("http://whatsbeef.net/wabz/guide.php?start=0");
//        try {
//            JSONObject json = new JSONObject(JsonResponse);
//            view.setAdapter(new ListAdapter(json.getJSONArray("results")));
//        } catch (JSONException e) {
//            e.printStackTrace
//                    ();
//        }

        taptoref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.getCount() <= 0){
                    taptoref.setVisibility(View.GONE);
                    plist.setVisibility(View.VISIBLE);
                    getProgramsDta(0);

                }
            }
        });


        if(db.hasData()){
            progList =new ArrayList<HashMap<String, String>>();
            List<ProgramsDTO> programlist = db.getAllUrl();

            for (ProgramsDTO h : programlist) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_NAME, h.getName());
                map.put(KEY_STARTTIME, h.getStart_time());
                map.put(KEY_ENDTIME, h.getEnd_time());
                map.put(KEY_CHANNEL, h.getChannel());
                map.put(KEY_RATING, h.getRating());
//                    urls.add(h.getName());
                progList.add(map);
                adapter=new ListviewAdapter(getActivity(), progList);
                plist.setAdapter(adapter);
                plist.setSelection(currentPosition);
            }
        }else{
            getProgramsDta(0);
        }



        plist.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == 2)
                    flag = true;
//                Log.i("Scroll State", "" + i);
                currentPosition = absListView.getFirstVisiblePosition();
                Log.i("currentPosition State", "" + currentPosition);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                // Check if the last view is visible
                if ((i2 == (i3 - i))
                        && flag) {
                    flag = false;

                    Log.i("Scroll", "Ended " + db.getCount());
                    getProgramsDta(db.getCount());


                }
            }
        });

        return view;
    }

    private void getProgramsDta(int d) {
        task = new getPrograms();
        task.execute(d,null,null);
    }

    class getPrograms extends AsyncTask<Integer, String, String> {

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), "Please wait..",
                    "Loading Data", true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... param) {
            progList =new ArrayList<HashMap<String, String>>();
            Log.i("HAS DATA", ""+db.hasData());
                List<ProgramsDTO> proglist = db.getAllUrl();

                JSONParser jParser = new JSONParser();
                JSONObject json = jParser.getJSONFromUrl("http://whatsbeef.net/wabz/guide.php?start=" + param[0]);

                try {
                    org.json.JSONArray results = json.getJSONArray(KEY_RESULTS);

                    for (int i = 0; i < results.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject c = results.getJSONObject(i);
                        map.put(KEY_NAME, c.getString(KEY_NAME));
                        map.put(KEY_STARTTIME, c.getString(KEY_STARTTIME));
                        map.put(KEY_ENDTIME, c.getString(KEY_ENDTIME));
                        map.put(KEY_CHANNEL, c.getString(KEY_CHANNEL));
                        map.put(KEY_RATING, c.getString(KEY_RATING));
                        db.addprogram(new ProgramsDTO(c.getString(KEY_NAME), c.getString(KEY_STARTTIME), c.getString(KEY_ENDTIME), c.getString(KEY_CHANNEL), c.getString(KEY_RATING)));
//                    progList.add(map);
                    }


                } catch (JSONException e) {
                loadSuccess = false;
                    e.printStackTrace();
                } catch (Exception e) {
                loadSuccess = false;
                    e.printStackTrace();
                }

            List<ProgramsDTO> programlist = db.getAllUrl();


            for (ProgramsDTO h : programlist) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_NAME, h.getName());
                map.put(KEY_STARTTIME, h.getStart_time());
                map.put(KEY_ENDTIME, h.getEnd_time());
                map.put(KEY_CHANNEL, h.getChannel());
                map.put(KEY_RATING, h.getRating());
//                    urls.add(h.getName());
                progList.add(map);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(loadSuccess){
                adapter=new ListviewAdapter(getActivity(), progList);
                plist.setAdapter(adapter);
                plist.setVisibility(View.VISIBLE);
                taptoref.setVisibility(View.GONE);
                plist.setSelection(currentPosition);

            }else{
              if(db.getCount() <= 0){
                  plist.setVisibility(View.GONE);
                  taptoref.setVisibility(View.VISIBLE);
              }
                loadSuccess = true;
            }

            progress.dismiss();

            super.onPostExecute(s);
        }
    }



//    public static String connect(String url) {
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet httpget = new HttpGet(url);
//        HttpResponse response;
//        try {
//            response = httpclient.execute(httpget);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                InputStream instream = entity.getContent();
//                String result = convertStreamToString(instream);
//                instream.close();
//                return result;
//            }
//        } catch (IOException e) {
//        }
//        return null;
//    }

//    private static String convertStreamToString(InputStream is) {
//        /*
//         * To convert the InputStream to String we use the BufferedReader.readLine()
//         * method. We iterate until the BufferedReader return null which means
//         * there's no more data to read. Each line will appended to a StringBuilder
//         * and returned as String.
//         */
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb = new StringBuilder();
//
//        String line = null;
//        try {
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return sb.toString();
//    }

//    public class ListAdapter extends BaseAdapter {
//
//        JSONArray array;
//
//        public ListAdapter(JSONArray response) {
//            array = response;
//        }
//
//        @Override
//        public int getCount() {
//            return 1;
//        }
//
//        @Override
//        public JSONObject getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LinearLayout layout = new LinearLayout(getActivity());
//            layout.setOrientation(LinearLayout.VERTICAL);
//
//            try {
//                TextView name = new TextView(getActivity());
//                name.setText(array.getJSONObject(position).getString("name"));
//
//                layout.addView(name);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return layout;
//        }
//    }
}
