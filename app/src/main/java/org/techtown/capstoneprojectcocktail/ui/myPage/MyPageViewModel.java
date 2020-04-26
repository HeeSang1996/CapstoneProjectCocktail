package org.techtown.capstoneprojectcocktail.ui.myPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyPageViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}