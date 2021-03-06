package com.ripzery.cryptracker.repository

import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.data.PairedCurrency
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by ripzery on 10/29/17.
 */
class CryptrackerRepository(private val cryptrackerLocalDataSource: CryptrackerDataSource,
                            private val cryptrackerRemoteDataSource: CryptrackerDataSource) : CryptrackerDataSource {


    override fun fetchPriceAndSave(intervalInSecond: Long): Observable<List<Pair<PairedCurrency, CoinMarketCapResult>>>? {
        return Observable.interval(0, intervalInSecond, TimeUnit.SECONDS)
                .flatMap { cryptrackerRemoteDataSource.fetchPriceAndSave(intervalInSecond) }
    }

    override fun getCryptoList(): List<String> = cryptrackerLocalDataSource.getCryptoList()

    override fun loadCurrency(): Pair<String, String> {
        cryptrackerRemoteDataSource.loadCurrency()
        return cryptrackerLocalDataSource.loadCurrency()
    }

    companion object {
        private var INSTANCE: CryptrackerRepository? = null

        @JvmStatic
        fun getInstance(cryptrackerLocalDataSource: CryptrackerDataSource,
                        cryptrackerRemoteDataSource: CryptrackerDataSource) =
                INSTANCE ?: synchronized(CryptrackerRepository::class.java) {
                    INSTANCE ?: CryptrackerRepository(cryptrackerLocalDataSource, cryptrackerRemoteDataSource).also { INSTANCE = it }
                }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}