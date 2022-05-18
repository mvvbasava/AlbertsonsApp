package com.example.albertsonsapp.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.albertsonsapp.responseOneEntry
import com.example.albertsonsapp.service.Acromine.acromineService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val TAG = "Albertson"

    @Bindable
    val acronym = MutableLiveData<String>()

    @Bindable
    val initialism = MutableLiveData<String>()

    @Bindable
    var responseStr= MutableLiveData<String>()

    @Bindable
    val listData = MutableLiveData<MutableList<String>>()

    val list: MutableList<String> = mutableListOf()
    val errorMessage = MutableLiveData<String>()

    fun getAcromineResponse():MutableLiveData<String> {
        val call: Call<List<responseOneEntry>> = acromineService.getResponse(acronym.value, initialism.value)
        call.enqueue(object : Callback<List<responseOneEntry>> {
            override fun onResponse(call: Call<List<responseOneEntry>>, response: Response<List<responseOneEntry>>) {
                Log.d(TAG,"Albertson - onResponse")
                if (!response.isSuccessful) {
                    responseStr.value = "Code: " + response.code()
                    return
                }

                var varCount = 0
                var responseEntries = response.body()!!
                var content = ""
                for (responseOneEntry in responseEntries) {
                    content += "    sf: ${responseOneEntry.sf} \n"
                    content += "        lfsArray\n"
                    list.clear()
                    for (lfsEntry in responseOneEntry.lfs) {
                        content += "        lfsEntry.lf: ${lfsEntry.lf} \n"
                        content += "        lfsEntry.freq: ${lfsEntry.freq} \n"
                        content += "        lfsEntry.since: ${lfsEntry.since} \n"
                        content += "            varsArray \n"
                        for (vars in lfsEntry.vars) {
                            content += "            lf: ${vars.lf} \n"
                            if (list.size == 0) {
                                list.add(varCount,vars.lf)
                            } else {
                                list.add(varCount,vars.lf)
                            }
                            varCount++
                            content += "            freq: ${vars.freq} \n"
                            content += "            since: ${vars.since} \n"
                        }
                    }
                }

                Log.d(TAG,"list.size: ${list.size}")
                Log.d(TAG,"list: $list")
                listData.postValue(list)
                responseStr.value = content
            }

            override fun onFailure(call: Call<List<responseOneEntry>>, t: Throwable) {
                Log.d(TAG,"Albertson - onFailure")
                responseStr.value = t.message ?: ""
                errorMessage.postValue(t.message)
            }
        })
        return MutableLiveData(responseStr.value)
    }

}
