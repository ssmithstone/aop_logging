package blog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class MyLoggingAspect {

    private MyLogger logger;

    public MyLoggingAspect(MyLogger logger) {
        this.logger = logger;
    }

    @Before("execution(public void Simple.doSomeWorkNoParams())")
    public void doBefore(){
        logger.debug("Working Before blog.aop.Simple.doSomeWorkNoParams");
    }

    @After("execution(public void Simple.doSomeWorkNoParams())")
    public void doAfter(){
        logger.debug("Working After blog.aop.Simple.doSomeWorkNoParams");
    }



    @Around("execution(public void Simple.doSomeWorkNoParams())")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        logger.debug("Working Around Before blog.aop.Simple.doSomeWorkNoParams");
        Object proceed = pjp.proceed();
        logger.debug("Working Around After blog.aop.Simple.doSomeWorkNoParams");
        return proceed;
    }

    @Around("execution(public int Simple.doSomeWorkThatReturnsAValue())" )
    public Object doAroundWithParams(ProceedingJoinPoint pjp) throws Throwable {
        logger.debug("Working Around Before blog.aop.Simple.doSomeWorkThatReturnsAValue");
        Object a = pjp.proceed();
        logger.debug("Catching Return Value of "+a+" Around After blog.aop.Simple.doSomeWorkThatReturnsAValue");
        return a;
    }


    @AfterReturning(value = "execution(public int Simple.doSomeWorkThatReturnsAValue())" , returning = "a")
    public void doAfterReturning(int a){
        logger.debug("Catching Return Value of " + a + " After blog.aop.Simple.doSomeWorkThatReturnsAValue");
    }

    @Before(value = "execution(public void Simple.doSomeWorkWithParams(String)) && args(message)")
    public void doBeforeWithParams(String message){
        logger.debug("Working Before blog.aop.Simple.doSomeWorkWithParams(String message) passed " + message.toUpperCase());
    }
}
