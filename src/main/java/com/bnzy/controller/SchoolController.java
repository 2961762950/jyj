package com.bnzy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bnzy.common.R;
import com.bnzy.domian.Administrator;
import com.bnzy.domian.Student;
import com.bnzy.dto.StudentDto;
import com.bnzy.service.AdministratorService;
import com.bnzy.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 学校
 */
@Slf4j
@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private StudentService studentService;


    //登录的管理员
    private Administrator one = null;

    /**
     * 学校登录
     *
     * @param administrator 管理员对象
     * @return
     */
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody Administrator administrator) {
        //进行md5加密
        administrator.setPassword(DigestUtils.md5DigestAsHex(administrator.getPassword().getBytes()));

        LambdaQueryWrapper<Administrator> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(administrator.getAccount() != null, Administrator::getAccount, administrator.getAccount());
        wrapper.eq(administrator.getPassword() != null, Administrator::getPassword, administrator.getPassword());

        one = administratorService.getOne(wrapper);

        if (one != null) {
            request.getSession().setAttribute("id", one.getId());

            return R.success("登录成功");
        }
        return R.error("登录失败");
    }

    /**
     * 学校退出
     *
     * @return
     */
    @DeleteMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("id");

        System.out.println("退出成功");
        one = null;
        return R.success("退出成功");
    }
    /**
     * 分页查询
     *
     * @param page      当前页码
     * @param pageSize  当前页码展示条数
     * @param name      条件查询-姓名
     * @param studentId 条件查询-学号
     * @return
     */
    @GetMapping
    public R<IPage<StudentDto>> page(Integer page, Integer pageSize, String name, String studentId) {

        if (one == null) {
            return  R.error(",未查询到学校信息，请重新登录");
        }

        Page<StudentDto> page1=new Page<>(page,pageSize);
        Student student=new Student();
        student.setId(studentId);
        student.setStudentName(name);

        return  R.success(studentService.page1(page1, student,one.getCamp(),null));


    }



    /**
     * 修改密码
     *
     * @param administrator
     * @return
     */
    @PutMapping("/revise")
    public R<String> upDataSchool(Administrator administrator) {
        //将获得的密码进行md5加密
        administrator.setPassword(DigestUtils.md5DigestAsHex(administrator.getPassword().getBytes()));

        //查询用户是否存在
        LambdaQueryWrapper<Administrator> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(administrator.getAccount() != null, Administrator::getAccount, administrator.getAccount());
        wrapper.eq(administrator.getPassword() != null, Administrator::getPassword, administrator.getPassword());

        Administrator one = administratorService.getOne(wrapper);
        //用户为空则返回错误的结果
        if (one == null) {
            return R.error("账号，或者密码错误");
        }
        //将新密码进行替换
        one.setPassword(DigestUtils.md5DigestAsHex(administrator.getName().getBytes()));
        //查看结果
        boolean b = administratorService.updateById(one);

        if (b) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }

    }

    /**
     * 分页查询-录取学校
     *
     * @param page      当前页码
     * @param pageSize  当前页码展示条数
     * @param name      条件查询-姓名
     * @param studentId 条件查询-学号
     */
    @GetMapping("/enrollment")
    public R<IPage<StudentDto>> enrollment(Integer page, Integer pageSize, String name, String studentId) {

        if (one == null) {
            return  R.error(",未查询到学校信息，请重新登录");
        }

        Page<StudentDto> page1=new Page<>(page,pageSize);
        Student student=new Student();
        student.setId(studentId);
        student.setStudentName(name);

        return  R.success(studentService.page1(page1, student,null,one.getCamp()));


    }



}
