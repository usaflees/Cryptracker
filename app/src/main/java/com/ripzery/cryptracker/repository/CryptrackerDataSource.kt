package com.ripzery.cryptracker.repository

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import io.reactivex.Observable

/**
 * Created by ripzery on 10/29/17.
 */
interface CryptrackerDataSource {
    fun getBxPrice(): Observable<BxPrice>
    fun getCmcPrice(currency: String): Observable<List<CoinMarketCapResult>>
    fun updatePriceWithInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<Pair<String, String>>

    fun loadCurrency(): Pair<String, String>
    fun getCryptoList(): List<String>
}