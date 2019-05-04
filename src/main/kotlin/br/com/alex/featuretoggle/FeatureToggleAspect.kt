package br.com.alex.featuretoggle

import nl.basjes.parse.useragent.UserAgentAnalyzer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.lang.IllegalArgumentException

class UnsupportedVersionException(message: String) : IllegalArgumentException(message)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class VersionToggle(val version: String)

@Aspect
@Component
class FeatureToggleAspect(
        val userAgentAnalyzer: UserAgentAnalyzer,
        val environment: Environment
) {
    @Around(value = "@annotation(br.com.alex.featuretoggle.VersionToggle)")
    fun toggle(joinPoint: ProceedingJoinPoint): Any = (joinPoint.signature as MethodSignature)
            .method
            .getAnnotation(VersionToggle::class.java)
            .version
            .let {
                when (it.startsWith("#")) {
                    true -> """#\{(.*)}""".toRegex().find(it)?.groups?.get(1)?.let { match -> environment.getProperty(match.value)!! }
                    false -> it
                }
            }
            ?.let { thresholdVersion ->
                val userAgent = (RequestContextHolder.getRequestAttributes()
                        as ServletRequestAttributes).request.getHeader(HttpHeaders.USER_AGENT)

                val version = userAgentAnalyzer.parse(userAgent)["OperatingSystemVersion"].value

                if (version <= thresholdVersion) throw UnsupportedVersionException("Unsupported agent version.")
                return joinPoint.proceed()
            }!!
}