package org.example.jluzio.playground.ui.samples;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.example.jluzio.playground.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SampleBodyFragment.OnBodyFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SampleBodyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SampleBodyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TEXT = "text";

    // TODO: Rename and change types of parameters
    private String mText = "default-body";

    private OnBodyFragmentInteractionListener mListener;

    public SampleBodyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param text Parameter text.
     * @return A new instance of fragment SampleBodyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SampleBodyFragment newInstance(String text) {
        SampleBodyFragment fragment = new SampleBodyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mText = getArguments().getString(ARG_PARAM_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sample_body, container, false);
        TextView tvBodyText = view.findViewById(R.id.tvBodyText);
        tvBodyText.setText(mText);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onBodyFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBodyFragmentInteractionListener) {
            mListener = (OnBodyFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHeaderFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBodyFragmentInteractionListener {
        // TODO: Update argument type and name
        void onBodyFragmentInteraction(Uri uri);
    }
}
