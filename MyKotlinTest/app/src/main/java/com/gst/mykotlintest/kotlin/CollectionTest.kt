package com.gst.mykotlintest.kotlin

import android.os.Build
import android.support.annotation.RequiresApi

/**
 * author: GuoSongtao on 2017/11/14 15:46
 * email: 157010607@qq.com
 */
class CollectionTest {

    fun mapTest() {
        val map1 = mapOf<Int, String>(1 to "name", 2 to "gender", 3 to "old", 4 to "class")
        var map2 = hashMapOf<String, String>("name" to "小明", "gender" to "男", "old" to "13", "class" to "6年3班")

        println("key is name then value is ${map2.get("name")}")
        println("key is name1 then value is ${map2.get("name1")}")
        //plus不能做put minus不能做 map的remove
//        map2.plus("name1" to "郭嘉")//plus 不能做map put
        map2.put("name1", "郭嘉")
        println("key is name1 then value is ${map2.get("name1")}")
//        map2.plus(mapOf("name1" to "郭嘉"))
        if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) {
            map2.replace("name1", "我去")
            println("key is name1 then value is ${map2.getOrDefault("name1", "2B铅笔")}")
        } else {
//            map2.minus("name1")
//            map2.remove("name1")
            val mapCopy = map2 - "name1"
            println("key is name1 then value is ${mapCopy.get("name1")}")
        }
        //？？map2.getOrPut()  map2.getOrDefault()

        for ((k, v) in map2) {
            println("key is $k then value is $v")
        }

        if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) {
            map2.replace("name1", "郭嘉2")
        } else {
            map2.put("name1", "郭嘉2")
        }
        for ((k, v) in map2) {
            println("key is $k then value is $v")
        }

        for (key in map1.keys) {
            print(map1.getValue(key) + "  ;")
        }
        println("")

        if (map2 != null && !map2.isEmpty()) println("map2 is not null or empty,and size is ${map2.size}")


        for (en in map2.entries) {
            println("key is ${en.key} then value is ${en.value}")
        }

        var bookMap = HashMap<Book, Int>()
        bookMap.put(Book("三国演义", 33.98, "罗贯中"), 10)
        bookMap.put(Book("水浒传", 43.98, "施耐奄"), 300)
        bookMap.put(Book("西游记", 83.98, "吴承恩"), 12)
        bookMap.put(Book("西游记", 83.98, "吴承恩"), 4)
        bookMap.put(Book("红楼梦", 143.99, "曹雪芹"), 1200)


        //filter(predicate: (Map.Entry<K, V>) -> Boolean): Map<K, V>
        //toSortedMap(comparator: Comparator<in K>): SortedMap<K, V>
        //map(transform: (Map.Entry<K, V>) -> R): List<R>
        //mapTo(destination: C, transform: (Map.Entry<K, V>) -> R): C
        //forEach(action: (T) -> Unit): Unit
        bookMap.filter { it.key.price > 40 && it.value > 10 }
                .toSortedMap(Comparator { o1, o2 -> if (o1 is Book && o2 is Book) (o1.price - o2.price).toInt() else -1 })
                .map { it.key }
//                .map { it.price = it.price.toLong().toDouble() }//错误
                .onEach { it.price = it.price.toLong().toDouble() }
                .forEach {
                    println(it.toString())
                }
//                .map{it.key.price = it.key.price.toLong().toDouble()} }
    }

    fun test() {
        //map的测试
        mapTest()
    }


    class Book(name1: String, price1: Double, author1: String) {
        var name = name1
        var price = price1
        var author = author1

        init {
            System.out.println("create a new Book")
        }

        override fun equals(otherO: Any?): Boolean {
            if (otherO !is Book) return false
            return (otherO as Book).name.equals(name) && otherO.author.equals(author)

            //val other = otherO as Book
            //return other.name.equals(name) && other.author.equals(author)
        }

        override fun toString(): String = "Book={name:\"$name\",price:\"$price\",author:\"$author\"}"
//        override fun toString(): String {
//            return "Book={name:\"$name\",price:\"$price\",author:\"$author\"}"
//        }
    }

    class BookA constructor(name1: String, price1: String) {
        var name: String
        var price: String

        init {
            price = price1
            name = name1
        }
    }


}