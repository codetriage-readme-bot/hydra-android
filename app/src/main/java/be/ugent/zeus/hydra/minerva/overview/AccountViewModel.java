package be.ugent.zeus.hydra.minerva.overview;

import android.arch.lifecycle.ViewModel;

/**
 * @author Niko Strijbol
 */
public class AccountViewModel extends ViewModel {

    private final AccountStateLiveData accountStateLiveData = new AccountStateLiveData();

    public AccountStateLiveData getAccountStateLiveData() {
        return accountStateLiveData;
    }
}