package org.archguard.meta.dsl.foundation.expression

import org.archguard.meta.dsl.foundation.rule.NamingRule
import java.io.Serializable

typealias NamingExpression = NamingRule.() -> Serializable?
