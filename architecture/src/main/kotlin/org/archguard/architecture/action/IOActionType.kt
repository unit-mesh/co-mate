package org.archguard.architecture.action

enum class IOActionType(val actionName: String) {
    CREATE_DIRECTORY("create_directory") {
        override fun createAction(args: List<String>): IOAction {
            return EmptyIOAction()
        }
    },
    CREATE_FILE("create_file") {
        override fun createAction(args: List<String>): IOAction {
            return EmptyIOAction()
        }
    },

    CREATE_PACKAGE("create_package") {
        override fun createAction(args: List<String>): IOAction {
            return EmptyIOAction()
        }
    },
    CREATE_CLASS("create_class") {
        override fun createAction(args: List<String>): IOAction {
            return EmptyIOAction()
        }
    },
    CREATE_INTERFACE("create_interface") {
        override fun createAction(args: List<String>): IOAction {
            return EmptyIOAction()
        }
    },
    CREATE_ENUM("create_enum") {
        override fun createAction(args: List<String>): IOAction {
            return EmptyIOAction()
        }
    },
    UNKNOWN("unknown") {
        override fun createAction(args: List<String>): IOAction {
            return EmptyIOAction()
        }
    }
    ;

    abstract fun createAction(args: List<String>): IOAction

    companion object {
        fun from(source: String): IOActionType {
            return values().find { it.actionName == source.lowercase() } ?: UNKNOWN
        }
    }
}