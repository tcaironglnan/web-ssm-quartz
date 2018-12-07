package com.ssm.controller.user;

import com.ssm.controller.RootController;
import com.ssm.model.TaskModel;
import com.ssm.model.UserModel;
import com.ssm.service.QuartzService;
import com.ssm.service.UserService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author FaceFeel
 * @Created 2018-01-28 19:12
 */
@Controller
@RequestMapping("/user")
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class UserController implements Job {

    //region 变量定义部分

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private QuartzService quartzService;
    //endregion


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        UserModel userById = userService.findUserById(1L);
        System.err.println("UserController:" + userById + ":" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ****************** doing something...");
    }

    //region 定时器部分

    @RequestMapping("/startTask")
    @ResponseBody
    public String task(TaskModel taskModel) {
        try {
            quartzService.addJob(taskModel.getJobName(), taskModel.getJobGroupName(), taskModel.getTriggerName(), taskModel.getTriggerGroupName(), UserController.class, taskModel.getCron());
        } catch (Exception e) {
            logger.error("startTask error,info is: " + e);
            return "0";
        }
        return "1";
    }

    @RequestMapping("/modifyTask")
    @ResponseBody
    public String modifyTask(TaskModel taskModel) {
        try {
            quartzService.modifyJobTime(taskModel.getJobName(), taskModel.getJobGroupName(), taskModel.getTriggerName(), taskModel.getTriggerGroupName(), taskModel.getCron());
        } catch (Exception e) {
            logger.error("modifyTask error,info is: " + e);
            return "0";
        }
        return "1";
    }

    @RequestMapping("/shutTask")
    @ResponseBody
    public String shutTask(TaskModel taskModel) {
        try {
            quartzService.removeJob(taskModel.getJobName(), taskModel.getJobGroupName(), taskModel.getTriggerName(), taskModel.getTriggerGroupName());
        } catch (Exception e) {
            logger.error("shutTask error,info is: " + e);
            return "0";
        }
        return "1";
    }

    /**
     * 暂停指定的任务
     *
     * @param taskModel 定时器实体
     */
    @RequestMapping("/pauseTask")
    @ResponseBody
    public String pauseTask(TaskModel taskModel) {
        try {
            quartzService.pauseJob(taskModel.getJobName(), taskModel.getJobGroupName());
        } catch (Exception e) {
            logger.error("pauseTask error,info is: " + e);
            return "0";
        }
        return "1";
    }

    /**
     * 恢复指定的任务
     *
     * @param taskModel 定时器实体
     */
    @RequestMapping("/resumeTask")
    @ResponseBody
    public String resumeJob(TaskModel taskModel) {
        try {
            quartzService.resumeJob(taskModel.getJobName(), taskModel.getJobGroupName());
        } catch (Exception e) {
            logger.error("resumeTask error,info is: " + e);
            return "0";
        }
        return "1";
    }

    @RequestMapping("/shutAllTask")
    @ResponseBody
    public String shutAllTask() {
        try {
            quartzService.shutdownSchedule();
        } catch (Exception e) {
            logger.error("shutAllTask error,info is: " + e);
            return "0";
        }
        return "1";
    }

    @RequestMapping("/startAllTask")
    @ResponseBody
    public String startAllTask() {
        try {
            quartzService.startSchedule();
        } catch (Exception e) {
            logger.error("shutAllTask error,info is: " + e);
            return "0";
        }
        return "1";
    }
    //endregion

    /**
     * 修改密码
     *
     * @param request http
     * @return json
     */
    @RequestMapping("/updatePassWord")
    @ResponseBody
    public String updatePassWord(HttpServletRequest request) {

        String passWord = request.getParameter("passWord");
        UserModel currentUser = (UserModel) request.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            return "2";
        }

        boolean result = userService.updatePassWord(currentUser.getId(), passWord);
        if (result) {
            //修改成功
            return "1";
        } else {
            return "0";
        }
    }
}
