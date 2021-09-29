package com.njtech.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.njtech.server.pojo.*;
import com.njtech.server.service.*;
import com.njtech.server.vo.Result;
import com.sun.deploy.net.URLEncoder;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeEcService employeeEcService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有员工（分页）")
    @GetMapping("/")
    public Result getAllEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                 @RequestParam(defaultValue = "10")Integer pageSize,
                                 Employee employee,
                                 LocalDate[] beginDateScopes){
        return employeeEcService.getAllEmployee(currentPage,pageSize,employee,beginDateScopes);
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nations")
    public Result getAllNations(){
        List<Nation> nationList = nationService.list();
        return Result.success(nationList);
    }

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/politicsStatus")
    public Result getAllPoliticsStatus(){
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        return Result.success(politicsStatusList);
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/joblevel")
    public Result getAllJoblevel(){
        List<Joblevel> joblevelList = joblevelService.list();
        return Result.success(joblevelList);
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/positions")
    public Result getAllPositions(){
        List<Position> list = positionService.list();
        return Result.success(list);
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/departments")
    public Result getAllDepartments(){
        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "最大工号")
    @GetMapping("/maxWorkId")
    public Result maxWorkId(){
        String maxWorkId = employeeService.getMaxWorkId();
        return Result.success(null,maxWorkId);
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public Result addEmp(@RequestBody Employee employee){
        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "更新员工信息")
    @PutMapping("/")
    public Result updateEmp(@RequestBody Employee employee){
        if(employeeService.updateById(employee)){
            return Result.success("更新成功！");
        }
        return Result.fail("更新失败!");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public Result deleteEmp(@PathVariable("id") Integer id){
        if(employeeService.removeById(id)){
            return Result.success("删除成功！");
        }
        return Result.fail("删除失败！");
    }

    @ApiOperation(value = "导出员工数据", produces = "application/octet-stream")
    @GetMapping("/export")
    public void exportEmployee(HttpServletResponse response){
        List<Employee> employeeList = employeeService.getEmployeeInfo(null);
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, employeeList);
        ServletOutputStream outputStream = null;
        try {
            // 设置返回形式
            response.setHeader("content-type","application/octet-stream");
            // 防止中文乱码
            response.setHeader("Content-disposition","attachment; filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
