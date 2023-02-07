package com.sag.sagpro.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.sag.sagpro.R;
import com.sag.sagpro.interfaces.NumberAdjustHandler;
import com.sag.sagpro.utils.ToastUtils;

public class ItemNoAdjustView extends LinearLayout {

    EditText editEditText = null;
    NumberAdjustHandler numberAdjustHandler = null; //for user add or minus actions


    public void setNumberAdjustHandler(NumberAdjustHandler numberAdjustHandler) {
        this.numberAdjustHandler = numberAdjustHandler;
    }


    public String getValue() {
        EditText editEditText = (EditText) this.findViewById(R.id.numberEditText);
        return editEditText.getText().toString();
    }

    public void setText(String text) {
        if (!text.matches("\\d+")) {
            ToastUtils.showToast(getContext(), getResources().getString(R.string.prompt_input_only_digital));
            return;
        }
        editEditText.setText(text);
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

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_item_number_ajust, this);

        View minusBT = view.findViewById(R.id.numberMinusBT);
        View addBT = view.findViewById(R.id.numberAddBT);
        editEditText = (EditText) view.findViewById(R.id.numberEditText);

        minusBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editEditText.getText().toString();
                int result = subNumbder(number);
                ItemNoAdjustView.this.setText("" + result);
                if (numberAdjustHandler != null)
                    numberAdjustHandler.handleMinus(ItemNoAdjustView.this, result);
            }
        });
        addBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editEditText.getText().toString();
                int result = addNumbder(number);
                ItemNoAdjustView.this.setText("" + result);

                if (numberAdjustHandler != null)
                    numberAdjustHandler.handleAdd(ItemNoAdjustView.this, result);
            }
        });

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


}
