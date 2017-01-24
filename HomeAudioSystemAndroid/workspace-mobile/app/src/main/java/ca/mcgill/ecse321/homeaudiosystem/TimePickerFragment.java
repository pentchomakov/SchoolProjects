package ca.mcgill.ecse321.homeaudiosystem;

/**
 * Created by Pouvanen on 2016-04-11.
 */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.support.v4.app.DialogFragment;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    String label;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int minute = 0;
        int second = 00;
// Parse the existing time from the arguments
        Bundle args = getArguments();
        if (args != null) {
            minute = args.getInt("minute");
            second = args.getInt("second");
        }

// Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, minute, second,
                DateFormat.is24HourFormat(getActivity()));
    }
    public void onTimeSet(TimePicker view, int minute, int second) {
        MainActivity myActivity = (MainActivity)getActivity();
        myActivity.setTime(getArguments().getInt("id"), minute, second);
    }


}
