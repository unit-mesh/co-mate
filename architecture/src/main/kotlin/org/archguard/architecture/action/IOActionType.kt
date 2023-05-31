package org.archguard.architecture.action

enum class IOActionType(val actionName: String) {
    CREATE_DIRECTORY("create_directory") {
        override fun createAction(args: List<String>): IOAction {
            TODO()
        }
    },
    CREATE_FILE("create_file") {
        override fun createAction(args: List<String>): IOAction {
            TODO()
        }
    },
    // for
    CREATE_PACKAGE("create_package") {
        override fun createAction(args: List<String>): IOAction {
            TODO()
        }
    },
    CREATE_CLASS("create_class") {
        override fun createAction(args: List<String>): IOAction {
            TODO()
        }
    },
    CREATE_INTERFACE("create_interface") {
        override fun createAction(args: List<String>): IOAction {
            TODO()
        }
    },
    CREATE_ENUM("create_enum") {
        override fun createAction(args: List<String>): IOAction {
            TODO()
        }
    },
    ;

    abstract fun createAction(args: List<String>): IOAction
}