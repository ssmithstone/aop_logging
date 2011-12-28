import blog.aop.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;

public class AOPLoggerTest {

    private MyLogger logger;
    private Simple proxy;

    @Before
   public void setUp(){
        logger = Mockito.mock(MyLogger.class);
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(new SimpleBean());
        proxyFactory.addAspect(new MyLoggingAspect(logger));
        proxy = proxyFactory.getProxy();
    }

    @Test
    public void testBeforeMethod(){
        proxy.doSomeWorkNoParams();
        Mockito.verify(logger, atLeastOnce()).debug("Working Before blog.aop.Simple.doSomeWorkNoParams");
    }
    
    @Test
    public void testAfterMethod(){
        proxy.doSomeWorkNoParams();
        Mockito.verify(logger, atLeastOnce()).debug("Working After blog.aop.Simple.doSomeWorkNoParams");
    }
    
    @Test
    public void testAfterMethodReturns(){
        int v = proxy.doSomeWorkThatReturnsAValue();
        Mockito.verify(logger, atLeastOnce()).debug("Catching Return Value of 1 After blog.aop.Simple.doSomeWorkThatReturnsAValue");
        assertThat(v, equalTo(1));
    }


    @Test
    public void testAroundMethodWithNoParams(){
        proxy.doSomeWorkNoParams();
        Mockito.verify(logger, atLeastOnce()).debug("Working Around Before blog.aop.Simple.doSomeWorkNoParams");
        Mockito.verify(logger, atLeastOnce()).debug("Working Around After blog.aop.Simple.doSomeWorkNoParams");
    }
    
    @Test
    public void testAroundMethodWithParams(){
        int v = proxy.doSomeWorkThatReturnsAValue();
        Mockito.verify(logger).debug("Working Around Before blog.aop.Simple.doSomeWorkThatReturnsAValue");
        assertThat(v, equalTo(1));
        Mockito.verify(logger).debug("Catching Return Value of 1 Around After blog.aop.Simple.doSomeWorkThatReturnsAValue");
    }

    @Test
    public void testBeforeMethodWithParams(){
        proxy.doSomeWorkWithParams("Cows Make Milk"); 
        Mockito.verify(logger).debug("Working Before blog.aop.Simple.doSomeWorkWithParams(String message) passed COWS MAKE MILK");
    }
    
}



