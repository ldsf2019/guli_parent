package com.atguigu.serviceedu.controller;




import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.atguigu.serviceedu.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2019-12-20
 */
@RestController
@RequestMapping("/serviceedu/edu-teacher")
@Api(description = "讲师管理")  //添加对类的描述
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "修改讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.updateById(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * @RequestParam 和 @PathVariable的区别
     *      @RequestParam是获取请求参数
     *      @PathVariable是获取 rest路径后的参数
     * @param id
     * @return
     */
    @ApiOperation(value = "按id查询讲师")
    @PostMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",byId);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+eduTeacher);
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "带条件的分页查询")
    @PostMapping("findTeacherPageCondition/{current}/{limit}")
    public R getTeacherPageCondition(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody(required = false) TeacherQuery queryWrapper
            ){
        //@RequestBody表示使用 json 传递数据，把 json 数据封装到对象里面
        //@RequestBody 使用时需要 post 提交

        //1.创建 page 对象，current为当前页，limit为每页数据
        Page<EduTeacher> page = new Page<>(current,limit);

        //调用方法实现条件查询带分页
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //判断条件值是否为空
        String name = queryWrapper.getName();
        Integer level = queryWrapper.getLevel();
        String begin = queryWrapper.getBegin();
        String end = queryWrapper.getEnd();

        if(!StringUtils.isEmpty(name)) {//name值不为空
            //拼接条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            //拼接条件
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            //拼接条件
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            //拼接条件
            wrapper.le("gmt_create",end);
        }

        //分页查询，并把查出的数据放入 Page对象中
        eduTeacherService.page(page, wrapper);

        long total = page.getTotal();   //得到总条数
        //得到查询处出的数据
        List<EduTeacher> records = page.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable Long limit){
        //mybatisplus的分页
        Page<EduTeacher> pageParam = new Page<>(page,limit);
        //根据页码分页查询并且将查出的数据放入 Page 对这项中
        eduTeacherService.page(pageParam,null);
        //获取查出的数据
        List<EduTeacher> records = pageParam.getRecords();
        //获取总页数
        long total = pageParam.getTotal();

        return R.ok().data("total",total).data("rows",records);
    }

    //查询所有教师
    @GetMapping
    @ApiOperation(value = "所有讲师列表")     //添加对方法的描述
    public R list(){
        List<EduTeacher> list = eduTeacherService.list(null);
        //如果不加入参数就是查询所有
        return R.ok().data("items",list);
    }

    //根据id实现逻辑删除教师
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据ID逻辑删除讲师")
    public R equals(
            @ApiParam(name = "id",value = "讲师ID",required = true)   //代表参数是必须的
            @PathVariable String id) {
        eduTeacherService.removeById(id);
        return R.ok();
    }

}

