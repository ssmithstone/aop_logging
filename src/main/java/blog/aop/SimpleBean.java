package blog.aop;

public class SimpleBean  implements Simple {

    @Override
    public void doSomeWorkNoParams() {

    }

    @Override
    public int doSomeWorkThatReturnsAValue() {
        return 1;
    }

    @Override
    public void doSomeWorkWithParams(String message) {

    }
}
