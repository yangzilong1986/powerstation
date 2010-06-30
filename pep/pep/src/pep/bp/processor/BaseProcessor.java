/*
 * 业务处理器父类
 */

package pep.bp.processor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Thinkpad
 */
public class BaseProcessor implements Runnable{
    protected ApplicationContext cxt;
    public BaseProcessor(){
        cxt = new ClassPathXmlApplicationContext("beans.xml");
    }

    public void run(){

    }

}
