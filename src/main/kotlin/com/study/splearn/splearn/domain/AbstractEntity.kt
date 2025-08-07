package com.study.splearn.splearn.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.proxy.HibernateProxy
import java.util.Objects

@MappedSuperclass
abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as Member

        return id != null && Objects.equals(id, other.id)
    }

    /**
     * HibernateProxy를 사용하지 않는 경우, id가 0인 경우는 영속화되지 않은 상태이므로
     * 이 경우에는 클래스의 해시코드를 사용합니다.
     */
    override fun hashCode(): Int {
        return if (this is HibernateProxy) (this as HibernateProxy).hibernateLazyInitializer.persistentClass
            .hashCode() else javaClass.hashCode()
    }
}
