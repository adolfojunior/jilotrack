package br.com.cubekode.jilotrack.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class StatementAspect extends TrackAspect {

	@Before("trackPointcut() && statementPointcut()")
	public void beforeTrackAndPointcut(JoinPoint joinPoint) throws Throwable {

		beforeTrack(joinPoint);

		// Object object = joinPoint.getThis();
		// LogUtil.log("### StatementAspect: ", object);
	}

	@AfterReturning("trackPointcut() && statementPointcut()")
	public void afterTrackReturningAPI(JoinPoint joinPoint) throws Throwable {
		afterTrackReturning(joinPoint);
	}

	@AfterThrowing(pointcut = "trackPointcut() && statementPointcut()", throwing = "e")
	public void afterTrackThrowingAPI(JoinPoint joinPoint, Throwable e) throws Throwable {
		afterTrackThrowing(joinPoint, e);
	}
}
