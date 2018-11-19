package com.akhbulatov.archsample.ui.main.favoritesroot.favorites

import com.akhbulatov.archsample.data.global.DataManager
import com.akhbulatov.archsample.ui.global.BasePresenter
import com.akhbulatov.archsample.ui.global.ErrorHandler
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class FavoritesPresenter(
    private val dataManager: DataManager,
    private val errorHandler: ErrorHandler
) : BasePresenter<FavoritesView>() {

    override fun onFirstViewAttach() {
        loadFavorites()
    }

    private fun loadFavorites() {
        dataManager.getFavorites()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoadingProgress(true) }
            .doAfterTerminate { viewState.showLoadingProgress(false) }
            .subscribe(
                {
                    if (!it.isEmpty()) {
                        viewState.showFavorites(it)
                    } else {
                        viewState.showEmptyFavorites()
                    }
                },
                { errorHandler.proceed(it) { viewState.showError(it) } }
            )
            .connect()
    }

    fun onBackClicked() = viewState.backToUsers()
}