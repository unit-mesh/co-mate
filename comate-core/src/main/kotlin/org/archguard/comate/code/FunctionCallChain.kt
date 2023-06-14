package org.archguard.comate.code

import chapi.domain.core.CodeDataStruct

data class Leaf(val left: String, val right: String) {
    override fun toString(): String {
        return "$left -> $right"
    }
}

data class NodeTree(val node: String, val children: List<Leaf>) {
    // remove duplicate leaf
    fun removeDuplicate(): NodeTree {
        val map = mutableMapOf<String, String>()
        val newChildren = mutableListOf<Leaf>()
        for (child in children) {
            if (!map.containsKey(child.right)) {
                map[child.right] = child.left
                newChildren.add(child)
            }
        }

        return NodeTree(node, newChildren)
    }

    override fun toString(): String {
        return "$node -> ${children.joinToString(", ")}"
    }
}

class FunctionCallChain {
    private var loopCount = 0
    private var maxLoopCount = 6

    fun analysis(funcName: String, structs: List<CodeDataStruct>): NodeTree {
        val methodMap = buildMethodMap(structs)
        val chain = buildCallChain(funcName, methodMap, emptyMap())
        return NodeTree(funcName, chain).removeDuplicate()
    }

    private fun buildMethodMap(structs: List<CodeDataStruct>): Map<String, List<String>> {
        val methodMap = mutableMapOf<String, List<String>>()
        for (clz in structs) {
            for (method in clz.Functions) {
                val methodName = method.buildFullMethodName(clz)
                methodMap[methodName] = method.getAllCallString()
            }
        }

        return methodMap
    }

    private fun buildCallChain(
        funcName: String,
        methodMap: Map<String, List<String>>,
        diMap: Map<String, String>,
    ): List<Leaf> {
        if (loopCount > maxLoopCount) {
            return listOf()
        }

        loopCount++

        if (methodMap[funcName]?.isNotEmpty() == true) {
            val arrayResult: MutableList<Leaf> = mutableListOf()
            methodMap[funcName]?.forEach { child ->
                val childName = if (diMap.containsKey(child.getClassName())) {
                    diMap[child.getClassName()] + "." + child.getMethodName()
                } else {
                    child
                }
                if (methodMap[child]?.isNotEmpty() == true) {
                    arrayResult += buildCallChain(childName, methodMap, diMap)
                }

                arrayResult += Leaf(funcName, childName)
            }

            return arrayResult
        }

        return listOf()
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