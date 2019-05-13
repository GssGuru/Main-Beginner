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

/*
ENG: Dialog box to display internet request error
RU: Диалоговое окно для показа ошибки интернет запроса
*/
public class DialigError extends DialogFragment {

    /*
    ENG: Basic elements for working with a DialogFragment
    RU: Базовые элементы для работы со Диалоговым окном
    */
    public static DialigError newInstance() {
        return new DialigError();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawableResource(R.color.colorTrasnFull);
        View v = inflater.inflate(R.layout.d_error, container, false);

        /*
        ENG: Find the View to click and assign the click handler.
        RU: Находим View для клика и и присваиваем обработчик нажатия
        */
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

    /*
    ENG: Add an interface with response methods to DialogFragment.
    RU: Добавляем нашему Диалоговому окну интерфейс с методами ответа
    */
    public interface InterfaceCallback {
        void refresh();
        void exit();
    }
    private InterfaceCallback interfaceCallback;
    public void registerInterfaceCallback(InterfaceCallback interfaceCallback) {
        this.interfaceCallback = interfaceCallback;
    }
}