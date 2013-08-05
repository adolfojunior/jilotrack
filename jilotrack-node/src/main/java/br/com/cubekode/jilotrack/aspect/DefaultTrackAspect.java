package br.com.cubekode.jilotrack.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class DefaultTrackAspect extends TrackAspect {

	// Before
	@Before("trackPointcut() && !apiPointcut()")
	public void beforeGenericTrack(JoinPoint joinPoint) throws Throwable {
		beforeTrack(joinPoint);
	}

	@AfterReturning("trackPointcut() && !apiPointcut()")
	public void afterReturningGenericTrack(JoinPoint joinPoint) throws Throwable {
		afterTrackReturning(joinPoint);
	}

	@AfterThrowing(pointcut = "trackPointcut() && !apiPointcut()", throwing = "e")
	public void afterThrowingGenericTrack(JoinPoint joinPoint, Throwable e) throws Throwable {
		afterTrackThrowing(joinPoint, e);
	}
}
