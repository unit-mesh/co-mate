package org.archguard.comate.code

import chapi.domain.core.CodeDataStruct

class FunctionCall {
    var loopCount = 0
    var maxLoopCount = 6

    fun buildMethodMap(structs: List<CodeDataStruct>): Map<String, List<String>> {
        val methodMap = mutableMapOf<String, List<String>>()
        for (clz in structs) {
            for (method in clz.Functions) {
                val methodName = method.buildFullMethodName(clz)
                methodMap[methodName] = method.getAllCallString()
            }
        }

        return methodMap
    }

    fun buildCallChain(funcName: String, methodMap: Map<String, List<String>>, diMap: Map<String, String>): String {
        if (loopCount > maxLoopCount) {
            return "\n"
        }
        loopCount++

        if (methodMap[funcName]?.isNotEmpty() == true) {
            var arrayResult = ""
            methodMap[funcName]?.forEach { child ->
                val childName = if (diMap.containsKey(child.getClassName())) {
                    diMap[child.getClassName()] + "." + child.getMethodName()
                } else {
                    child.toString()
                }
                if (methodMap[child]?.isNotEmpty() == true) {
                    arrayResult += buildCallChain(childName, methodMap, diMap)
                }
                arrayResult += "\"${escapeStr(funcName)}\" -> \"${escapeStr(childName)}\";\n"
            }

            return arrayResult
        }
        return "\n"
    }
}

fun String.getClassName(): String {
    val split = this.split(".")
    return split.subList(0, split.size - 1).joinToString(".")
}

fun String.getMethodName(): String {
    val split = this.split(".")
    return split.subList(split.size - 1, split.size).joinToString(".")
}

fun escapeStr(caller: String): String {
    return caller.replace("\"", "\\\"")
}