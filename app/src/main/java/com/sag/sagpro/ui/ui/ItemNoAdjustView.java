package com.sag.sagpro.ui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.sag.sagpro.R;

public class ItemNoAdjustView extends LinearLayout {

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_item_number_ajust, this);

        View minusBT = view.findViewById(R.id.numberMinusBT);
        View addBT = view.findViewById(R.id.numberAddBT);
        EditText editEditText = (EditText) view.findViewById(R.id.numberEditText);

        minusBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editEditText.getText().toString();
                int result = subNumbder(number);
                editEditText.setText("" + result);
            }
        });
        addBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editEditText.getText().toString();
                int result = addNumbder(number);
                editEditText.setText("" + result);
            }
        });

    }

    public String getValue() {
        EditText editEditText = (EditText) this.findViewById(R.id.numberEditText);
        return editEditText.getText().toString();
    }


    public ItemNoAdjustView(Context context) {
        super(context);

        init();
    }

    public ItemNoAdjustView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ItemNoAdjustView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public ItemNoAdjustView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }



    private int addNumbder(String number) {
        return formatCaculatorNumber(number, 1);
    }

    private int subNumbder(String number) {
        return formatCaculatorNumber(number, -1);
    }

    private int formatCaculatorNumber(String number, int plusNumber) {
        try {
            int no = Integer.parseInt(number);
            return no + plusNumber;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
