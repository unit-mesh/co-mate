package org.archguard.spec.lang.foundation.expression

import org.archguard.spec.lang.foundation.rule.NamingRule
import java.io.Serializable

typealias NamingExpression = NamingRule.() -> Serializable?
