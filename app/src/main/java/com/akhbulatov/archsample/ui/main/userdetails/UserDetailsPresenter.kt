package com.akhbulatov.archsample.ui.main.userdetails

import com.akhbulatov.archsample.data.global.DataManager
import com.akhbulatov.archsample.models.UserDetails
import com.akhbulatov.archsample.ui.global.BasePresenter
import com.akhbulatov.archsample.ui.global.ErrorHandler
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import me.aartikov.alligator.Navigator

@InjectViewState
class UserDetailsPresenter(
    navigator: Navigator,
    private val dataManager: DataManager,
    val login: String,
    private val errorHandler: ErrorHandler
) : BasePresenter<UserDetailsView>(navigator) {

    override fun onFirstViewAttach() {
        loadUserDetails()
    }

    private fun loadUserDetails() {
        dataManager.getUserDetails(login)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showLoadingProgress(true)
                viewState.showContentLayout(false)
            }
            .doAfterTerminate { viewState.showLoadingProgress(false) }
            .subscribe(
                {
                    viewState.showContentLayout(true)
                    viewState.showUserDetails(it)
                },
                { errorHandler.proceed(it) { viewState.showError(it) } }
            )
            .connect()
    }

    fun onAddToFavoritesClicked(userDetails: UserDetails) {
        dataManager.addUserToFavorites(userDetails)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { viewState.showToFavoritesAdded() },
                { errorHandler.proceed(it) { viewState.showError(it) } }
            )
            .connect()
    }
}