package be.ugent.zeus.hydra.minerva.overview;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import be.ugent.zeus.hydra.minerva.account.AccountUtils;

/**
 * @author Niko Strijbol
 */
class AccountStateLiveData extends LiveData<AccountState> {

    private static final String TAG = "AccountStateLiveData";

    public void logout(Context context) {
        Log.d(TAG, "logout: logout is called");
        setValue(AccountState.LOGGING_OUT);
        AsyncTask.execute(() -> {
            AccountUtils.logout(context);
            postValue(AccountState.LOGGED_OUT);
        });
    }

    public void setState(AccountState state) {
        Log.d(TAG, "setState: state is set to " + state);
        setValue(state);
    }

    public boolean isInitialised() {
        return getValue() != null;
    }
}