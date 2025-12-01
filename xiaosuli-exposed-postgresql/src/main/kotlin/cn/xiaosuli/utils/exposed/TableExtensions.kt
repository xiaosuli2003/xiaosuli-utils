@file:Suppress("unused")

package cn.xiaosuli.utils.exposed

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.StringColumnType
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.statements.api.PreparedStatementApi
import org.postgresql.util.PGobject

/**
 * 注册INET类型的列
 */
fun Table.inet(name: String): Column<String> = registerColumn(name, InetColumnType())

/**
 * INET类型的列
 */
class InetColumnType : StringColumnType() {
    override fun sqlType(): String = "INET"

    override fun setParameter(stmt: PreparedStatementApi, index: Int, value: Any?) {
        val parameterValue: PGobject? = value?.let {
            PGobject().apply {
                type = sqlType()
                this.value = value as? String
            }
        }
        super.setParameter(stmt, index, parameterValue)
    }
}