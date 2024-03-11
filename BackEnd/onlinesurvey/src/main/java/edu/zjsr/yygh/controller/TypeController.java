package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Type;
import edu.zjsr.yygh.service.ITypeService;
import edu.zjsr.yygh.vo.TypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lss
 * @since 2024-02-02
 */
@Api(tags = "分类列表")
@RestController
@RequestMapping("yygh/type")
public class TypeController {

    @Autowired
    ITypeService typeService;

    @GetMapping("typeList")
    @ApiOperation(value = "查询所有分类",notes = "查询所有分类信息")
    public Message<List<Type>> typeList(){
        Message<List<Type>> response = new Message<>();
        List<Type> list = typeService.list();
        response.setCode("200");
        response.setMsg("类型列表");
        response.setData(list);
        return response;
    }

}
