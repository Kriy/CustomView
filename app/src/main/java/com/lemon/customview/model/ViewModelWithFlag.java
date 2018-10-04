package com.lemon.customview.model;

import com.lemon.customview.widget.ExpandableTextView.ExpandableTextView;
import com.lemon.customview.widget.ExpandableTextView.StatusType;

public class ViewModelWithFlag implements ExpandableTextView.ExpandableStatusFix {

    private StatusType status;

    public ViewModelWithFlag(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setStatus(StatusType status) {
        this.status = status;
    }

    @Override
    public StatusType getStatus() {
        return status;
    }
}
