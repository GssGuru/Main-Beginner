package guru.gss.mainbeginner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import java.util.Objects;

public class DialigError extends DialogFragment {

    public static DialigError newInstance() {
        return new DialigError();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawableResource(R.color.colorTrasnFull);
        View v = inflater.inflate(R.layout.d_error, container, false);

        FrameLayout fl_ok = v.findViewById(R.id.fl_ok);
        fl_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                interfaceCallback.refresh();
                dismiss();
            }
        });

        FrameLayout fl_cancel = v.findViewById(R.id.fl_cancel);
        fl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                interfaceCallback.exit();
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public interface InterfaceCallback {
        void refresh();

        void exit();
    }

    private InterfaceCallback interfaceCallback;

    public void registerInterfaceCallback(InterfaceCallback interfaceCallback) {
        this.interfaceCallback = interfaceCallback;
    }
}