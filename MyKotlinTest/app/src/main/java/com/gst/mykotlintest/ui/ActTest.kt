package com.gst.mykotlintest.ui

import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import com.gst.mykotlintest.ActMain
import com.gst.mykotlintest.MyApp
import com.gst.mykotlintest.R
import com.gst.mykotlintest.kotlin.CollectionTest
import com.gst.mykotlintest.kotlin.JsonTest
import com.gst.mykotlintest.util.SPUtil
import kotlinx.android.synthetic.main.act_test_first.*

/**
 * author: GuoSongtao on 2017/11/1 16:20
 * email: 157010607@qq.com
 */
class ActTest : AppCompatActivity() {
    //lateinit 在构造方法后赋值
    lateinit var spUtil: SPUtil // = SpUtilTest(this) 直接赋值 this:Context为空

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_test_first)

        tv_show1.text = "点我试试"
        tv_show1.setTextColor(resources.getColor(R.color.colorPrimary))
        tv_show1.setTextSize(35F)

        spUtil = SPUtil.instance!!
        val rectangle = PointF(5F, 2F) // 创建类 不需要“new”关键字

        tv_show1.setOnClickListener {
            //            actTestTest()

//            CollectionTest().test()

//            JsonTest().testJson()

            spUtil.test()

        }
    }


    fun actTestTest() {

        //测试带返回的方法
//            Log.i("getString", getString())
//            Log.i("testAdd(2, 5)", testAdd(2, 5).toString())
//            Log.i("testAdd1(9, 5)", testAdd1(9, 5).toString())

        //测试没有返回类型的方法
//            printf("别看我！")
//            printSum(4, 5)

        //测试常量变量的定义
//            testAssign()

        //使用字符串模板
//            useString()

        //if 的用法
//            Log.i("maxOfMy", " max(5.0 ,4.0)=${maxOfMy(5.0, 4.0)}")

        //
//            printProduct("a", "32")
//            printProduct("7", "7")
//            printProduct("7", "7.1")

        //
//            printf("ssssss length is ${useIsCheckClazz("ssssss")}")

        //测试for while when 等关键字
//            testFor()

        //测试集合
        testIn()
    }

    override fun onResume() {
        super.onResume()
    }

    fun getString(): String {
        return "aaaaaa"
    }

    //带有两个 Int 参数、返回 Int 的函数：
    fun testAdd(a: Int, b: Int): Int {
        return a + b
    }

    //将表达式作为函数体、返回值类型自动推断的函数：
    fun testAdd1(a: Int, b: Int) = a + b

    //函数返回无意义的值：
    fun printf(s: String): Unit {
        Log.i("printf", s)
    }

    //Unit 返回类型可以省略：
    fun printSum(a: Int, b: Int) {
        Log.i("printSum", " $a and $b is ${a + b}")
    }

    //定义变量
    /**
     * 定义变量
     * val 一次赋值（只读）的局部变量:
     * var 可变变量：
     */
    val PI = 3.1415926
    var s = 0.0
    fun testAssign() {
        val a: Int = 345//不能345F
        val b = 3456F//不能345F
        val c: Long
        //b=345 一次性变量 只能赋值一次
        c = 34567L

        var r = 20.0
        r += 1

        s = getArea(r)

        Log.i("printSum", " a = $a ; b = $b ; c = $c ;  r = $r ; s = $s ;")
    }

    fun getArea(r: Double) = r * r * PI / 2

    /**
     * 使用字符串模板
     */
    fun useString() {
        var a = 1
        val str = " a is $a" //此处 a is 1

        a = a + 5
        Log.i("useString", "${str.replace("is", "was")}, but now is $a")
    }

    fun maxOfMy(a: Double, b: Double) = if (a > b) a else b

    fun maxOfMy1(a: Double, b: Double): Double {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    //使用可空值及null检测 (Int后加上?)
    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    fun printProduct(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)

//        if (x != null && y != null) {
//            printf("$arg1 * $arg2 = ${x * y}")
//        } else {
//            printf("either $arg1 or  $arg2 is not number!")
//        }

        if (x == null) {
            printf("$arg1 is not a number!")
            return
        }

        if (y == null) {
            printf("$arg2 is not a number!")
            return
        }

        printf("$arg1 * $arg2 = ${x * y}")
    }


    //使用类型检测及自动类型转换
    fun useIsCheckClazz(obj: String): Int? {
        //1
//        if (obj is String) {
//            // `obj` 在该条件分支内自动转换成 `String`
//            return obj.length
//        }
//        // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
//        return null

        //2
//        if(obj !is String){
//            return null
//        }
//        return obj.length

        //3
        if (obj is String && obj.length > 0) {
            return obj.length
        }
        return null
    }

    //使用 for 循环
    fun testFor() {
//        val list1 = doubleArrayOf(1, 4.0, 6.3, 8, 9, 15, 32)
//        val list1 = arrayOf(1, 4.0, 6.3, 8, 9, 15, 32)
        val list1 = doubleArrayOf(1.0, 4.0, 6.3, 8.0, 9.4, 15.2, 32.2)
        var sum = 0.0
        for (item in list1) {
            sum = sum + item
        }
        printf("sum is $sum")

        val strs = arrayOf("Tom", "Marry", "Green")
        for (index in strs.indices) {
            printf("item at $index is ${strs[index]}")
        }

        //while
        var index2 = strs.size
        while (--index2 >= 0) {
            printf("item at $index2 is ${strs[index2]}")
        }

        //when
        var index3 = strs.size
        while (--index3 >= 0) {
            printf("${strs[index3]} is ${describe(strs[index3])}")
        }

        //使用区间
        var x = 8
        var y = 9
        var printStr1 = "$x is in 1 to ${y - 32} range"
        //in
        if (x !in 1..y - 32) {
            printStr1 = printStr1.replace("is", "is not")
        }
        printf(printStr1)


        for (x in 1..5) {
            printf("$x")
        }
        //注意 step mustbe positive(积极的，正数)  另外 for里面 10..0也无效
        for (x in 1..10 step 2) {
            printf("$x")
        }
        for (x in 10 downTo 0 step 3) {
            printf("$x")
        }
//        for (x in 10..0 step 3) {
//        for (x in 10..0 step -3) {
//            printf("$x")
//        }

    }

    fun describe(obj: Any): String =
            when (obj) {
                1 -> "One"
                "hello" -> "Greeting"
                is Long -> "Long"
                !is String -> "not String"
                else -> "Unknown"
            }

    //使用集合
    fun testIn() {

        //对集合进行迭代:
        val strs = arrayOf("Tom", "Marry", "Green", "Gray")
        for (index in strs.indices) {
            printf("item at $index is ${strs[index]}")
        }

        //使用 in 运算符来判断集合内是否包含某实例：
        //when相当于 switch case
        when {
            "Tom" in strs -> printf("Tom is in strs")
            "Green" !in strs -> printf("Green is not in strs")
            "Marry" in strs -> printf("Marry is in strs")
        }

        //使用 lambda 表达式来过滤（filter）和映射（map）集合：
        strs.filter { it.startsWith("G") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { println(it) }
    }
}