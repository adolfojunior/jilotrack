package br.com.cubekode.jilotrack.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class FacesPhaseListenerAspect extends TrackAspect {

	@Before("trackPointcut() && facesPhaseListenerPointcut()")
	public void beforeTrackAndPointcut(JoinPoint joinPoint) throws Throwable {

		beforeTrack(joinPoint);

		// Object object = joinPoint.getThis();
		// LogUtil.log("### FacesPhaseListenerAspect: ", object);
	}

	@AfterReturning("trackPointcut() && facesPhaseListenerPointcut()")
	public void afterTrackReturningAPI(JoinPoint joinPoint) throws Throwable {
		afterTrackReturning(joinPoint);
	}

	@AfterThrowing(pointcut = "trackPointcut() && facesPhaseListenerPointcut()", throwing = "e")
	public void afterTrackThrowingAPI(JoinPoint joinPoint, Throwable e) throws Throwable {
		afterTrackThrowing(joinPoint, e);
	}
}
