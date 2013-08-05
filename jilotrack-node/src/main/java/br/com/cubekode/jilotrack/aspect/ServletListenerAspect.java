package br.com.cubekode.jilotrack.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ServletListenerAspect extends TrackAspect {

	@Before("trackPointcut() && servletListenerPointcut()")
	public void beforeTrackAndPointcut(JoinPoint joinPoint) throws Throwable {

		beforeTrack(joinPoint);

		// Object object = joinPoint.getThis();
		// LogUtil.log("### ServletListenerAspect: ", object);
	}

	@AfterReturning("trackPointcut() && servletListenerPointcut()")
	public void afterTrackReturningAPI(JoinPoint joinPoint) throws Throwable {
		afterTrackReturning(joinPoint);
	}

	@AfterThrowing(pointcut = "trackPointcut() && servletListenerPointcut()", throwing = "e")
	public void afterTrackThrowingAPI(JoinPoint joinPoint, Throwable e) throws Throwable {
		afterTrackThrowing(joinPoint, e);
	}
}
