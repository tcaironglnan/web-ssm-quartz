import com.ssm.config.quartz.QuartzManager;
import com.ssm.controller.RootController;

/**
 * @author Lenovo
 * @Created 2018-12-05 11:14
 **/
public class QuartzTest {

    private static String JOB_NAME = "动态任务调度";
    private static String TRIGGER_NAME = "动态任务触发器";
    private static String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";
    private static String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP";

    public static void main(String[] args) {
        try {
            System.out.println("【系统启动】开始(每1秒输出一次)...");
            QuartzManager.addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, RootController.class, "0/1 * * * * ?");

//            System.out.println("【修改时间】开始(每5秒输出一次)...");
              //如果没有设置对对应的参数，则不会起作用
//            QuartzManager.modifyJobTime(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/5 * * * * ?");
//            QuartzManager.modifyJobTime(TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/5 * * * * ?");

//            System.out.println("【移除定时】开始...");
//            QuartzManager.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
//            System.out.println("【移除定时】成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
