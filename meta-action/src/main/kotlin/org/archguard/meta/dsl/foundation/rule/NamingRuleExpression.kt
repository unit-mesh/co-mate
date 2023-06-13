package org.archguard.meta.dsl.foundation.rule

import org.archguard.meta.dsl.foundation.rule.NamingRule
import java.io.Serializable


typealias NamingExpression = NamingRule.() -> Serializable?

