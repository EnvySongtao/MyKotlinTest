package com.gst.mykotlintest.kotlin

import org.json.JSONObject

/**
 * author: GuoSongtao on 2017/11/15 17:23
 * email: 157010607@qq.com
 */
class JsonTest {
    val jsonStr = "{\"VER\":\"66\",\"RESP\":\"000\",\"RESPDESC\":\"\",\"CUSTID\":\"6000060886\",\"COLLTRANSLIST\":[{\"TRANSDATE\":\"20171114\",\"TOTALFEE\":\"0.00\",\"TOTALCNT\":\"0\",\"TOTALAMT\":\"0.00\"},{\"TRANSDATE\":\"20171115\",\"TOTALFEE\":\"0.00\",\"TOTALCNT\":\"0\",\"TOTALAMT\":\"0.00\"}]}"

    fun testJson() {
        var json = JSONObject(jsonStr)
        println("CUSTID = " + json.optString("CUSTID"))
        json.put("LOGINNAME", "gst1502161")
        println("LOGINNAME = " + json.getString("LOGINNAME"))

        var jsonArr = json.getJSONArray("COLLTRANSLIST")
        for (index in 0..jsonArr.length() - 1) {
            println("item at $index is ${jsonArr[index].toString()}")
        }

        //jsonArr.get(0) is Object
        var jsonInner1 = JSONObject(jsonArr.get(0).toString())
        println(jsonInner1.optString("TRANSDATE"))
    }

}