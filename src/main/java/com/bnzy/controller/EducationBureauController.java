package com.bnzy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bnzy.common.R;
import com.bnzy.domian.Administrator;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import com.bnzy.service.AdministratorService;
import com.bnzy.service.SchoolStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 教育局
 */
@Slf4j
@RestController
@RequestMapping("/education")
public class EducationBureauController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private SchoolStudentService schoolStudentService;

    /**
     * 教育局登录
     *
     * @param administrator 管理员信息（密码，账户）
     * @return
     */
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, Administrator administrator) {

        LambdaQueryWrapper<Administrator> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(administrator.getAccount() != null, Administrator::getAccount, administrator.getAccount());
        wrapper.eq(administrator.getPassword() != null, Administrator::getPassword, administrator.getPassword());

        Administrator one = administratorService.getOne(wrapper);
        if (one != null) {
            request.getSession().setAttribute("id", one.getId());
            return R.success("登陆成功");
        }

        return R.error("账户或密码错误");
    }

    /**
     * 管理员退出
     *
     * @param request
     * @return
     */
    @DeleteMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("id");


        return R.success("退出成功");
    }


    /**
     * 教育局管理员分页查询（条件查询）
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @param camp     归属地（详细学校名称）
     * @param name     姓名
     */
    @GetMapping
    public R<Page<Administrator>> getAdministrator(Integer page, Integer pageSize, String camp, String name) {
        //分页构造器
        Page<Administrator> page1 = new Page<>(page, pageSize);
        //条件查询构造器
        LambdaQueryWrapper<Administrator> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(camp != null, Administrator::getCamp, camp);
        wrapper.eq(name != null, Administrator::getName, name);
        wrapper.orderByAsc(Administrator::getId);

        administratorService.page(page1, wrapper);
        return R.success(page1);
    }

    /**
     * 删除管理员 （批量删除）
     *
     * @param id 管理员id
     * @return
     */
    @DeleteMapping
    public R<String> removeAdministrator(Integer[] id) {

        List<Integer> list = Arrays.asList(id);

        boolean b = administratorService.removeByIds(list);

        if (b) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }


    }

    /**
     * 更新
     *
     * @param administrator
     * @return
     */
    @PostMapping
    public R<String> upDataAdministrator(@RequestBody Administrator administrator) {

        boolean updateById = administratorService.updateById(administrator);

        if (updateById) {
            return R.success("操作成功");
        } else {
            return R.error("操作失败");

        }


    }

    /**
     * 批量启用禁用
     *
     * @param ids   id
     * @param state 0表示启用，1表示禁用
     */
    @PostMapping("/state")
    public R<String> upDataState(Integer[] ids, Integer state) {
        List<Administrator> list = new ArrayList<>();

        for (Integer id : ids) {

            Administrator ad = new Administrator();
            ad.setId(id);
            ad.setState(state);

            list.add(ad);
        }


        boolean b = administratorService.updateBatchById(list);

        if (b) {
            return R.success("操作成功");
        } else {
            return R.error("删除失败");
        }

    }

    /**
     * 管理员新建
     *
     * @param administrator
     * @return
     */
    @PostMapping("/save")
    public R<String> saveAdministrator(Administrator administrator) {

        boolean save = administratorService.save(administrator);
        if (save) {
            return R.success("操作成功");
        } else {
            return R.error("删除失败");
        }
    }


    /**
     *
     * @param page      当前页码
     * @param pageSize  当前页码展示条数
     * @param name      条件查询-姓名
     * @param studentId 条件查询-学号
     * @param school 毕业学校
     * @param schoolName 录取学校
     * @return 分页查询
     */
    @GetMapping("/page")
    public R<IPage<StudentDto>> page(Integer page, Integer pageSize,
                                     String name, String studentId,
                                     String school,String schoolName) {



        Page<StudentDto> page1=new Page<>(page,pageSize);
        Student student=new Student();
        student.setId(studentId);
        student.setStudentName(name);

        IPage<StudentDto> iPage = schoolStudentService.page(page1, student, school, schoolName);

        if (0==iPage.getRecords().size()){
            return R.error("未查询到相关信息");
        }


        return  R.success(iPage);


    }

    /**
     * 教育-修改学生信息
     * @param studentDto
     * @return
     */
    @PostMapping("/upData")
    public R<String> upDataStudent(StudentDto studentDto){
        Integer integer = schoolStudentService.upData(studentDto);

        if (3!=integer){
            //若不为3则进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("修改失败请联系管理员");
        }
        return R.success("修改成功");
    }

    /**
     * 删除学生
     * @param ids 学生id
     * @return
     */
    @PostMapping("/remove")
    public R<String> remove(String[] ids){
        List<String> list=Arrays.asList(ids);

        schoolStudentService.removeGrades(list);

        return R.success("删除成功");
    }
}
