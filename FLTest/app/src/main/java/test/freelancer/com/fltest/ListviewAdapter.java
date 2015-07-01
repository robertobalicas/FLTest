package test.freelancer.com.fltest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android 18 on 7/1/2015.
 */
public class ListviewAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    //	LinearLayout thumb1,thumb;
    private static LayoutInflater inflater=null;
    //	String s;

    public ListviewAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vi=view;
        if(view==null)
            vi = inflater.inflate(R.layout.program_list_row, null);

        TextView name = (TextView)vi.findViewById(R.id.name);
        TextView time = (TextView)vi.findViewById(R.id.time);
        TextView rating = (TextView)vi.findViewById(R.id.rating);
        TextView channel = (TextView)vi.findViewById(R.id.channel);

        HashMap<String, String> progitem = new HashMap<String, String>();
        progitem = data.get(i);

        name.setText(progitem.get(ListFragment.KEY_NAME));
        time.setText(progitem.get(ListFragment.KEY_STARTTIME)+" - "+progitem.get(ListFragment.KEY_ENDTIME));
        rating.setText("Rating: "+progitem.get(ListFragment.KEY_RATING));
        channel.setText("Channel: "+progitem.get(ListFragment.KEY_CHANNEL));

        return vi;
    }
}
