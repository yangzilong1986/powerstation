/*
 * 业务处理器父类
 */

package pep.bp.processor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class BaseProcessor implements Runnable{
    protected ApplicationContext cxt;
    public BaseProcessor(){
        cxt = new ClassPathXmlApplicationContext(SystemConst.SPRING_BEANS);
    }

    @Override
    public void run(){

    }

}
