package tvz.zavrsni.eimenik;

/**
 * Created by Pero on 12.7.2015..
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommentsFragment extends Fragment {

    public CommentsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);

        return rootView;
    }
}