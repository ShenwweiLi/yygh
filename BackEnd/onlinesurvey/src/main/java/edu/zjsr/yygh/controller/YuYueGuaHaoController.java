package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Doctor;
import edu.zjsr.yygh.entity.YuYueGuaHao;
import edu.zjsr.yygh.service.YuYueGuaHaoService;
import edu.zjsr.yygh.vo.DoctorListVo;
import edu.zjsr.yygh.vo.YuYueGuaHaoListVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author lishishan
 * @date 2024/3/14/9:17
 */

@RestController
@RequestMapping("yygh/yuyueguahao")
@CrossOrigin
public class YuYueGuaHaoController {
    @Resource
    YuYueGuaHaoService yuYueGuaHaoService;

    @GetMapping("/guahaoList")
    @CrossOrigin
    public Message<YuYueGuaHaoListVo> getDoctorWithPagination(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "search", required = false) String search) {
        System.out.println("ArticlesController---------search"+search);
        YuYueGuaHaoListVo yueGuaHaoListVo = yuYueGuaHaoService.findListrWithPagination(currentPage, pageSize, search);
        Message<YuYueGuaHaoListVo> response = new Message<>();

        if (yueGuaHaoListVo != null) {
            response.setCode("200");
            response.setMsg("Success");
            response.setData(yueGuaHaoListVo);
        } else {
            response.setCode("204");
            response.setMsg("No Content");
            response.setData(null);
        }

        return response;
    }

    @PostMapping("/saveOrUpdateGuaHao")
    @CrossOrigin
    public Message<YuYueGuaHao> saveOrUpdateGuaHao(@RequestBody YuYueGuaHao yuYueGuaHao) {

        Boolean isSave = yuYueGuaHaoService.saveOrUpdate(yuYueGuaHao);
        YuYueGuaHao doctorVo = yuYueGuaHaoService.getById(yuYueGuaHao.getId());
        Message<YuYueGuaHao> response = new Message<>();

        if (isSave) {
            response.setCode("200");
            response.setMsg("Success");
            response.setData(doctorVo);
        } else {
            response.setCode("204");
            response.setMsg("No Content");
            response.setData(null);
        }
        return response;
    }


    @PostMapping("/getYuYueGuaHaoById")
    @CrossOrigin
    public Message<YuYueGuaHao> getDoctorById(@RequestBody YuYueGuaHao yuYueGuaHao) {

        YuYueGuaHao yueGuaHaoVo = yuYueGuaHaoService.getById(yuYueGuaHao.getId());
        Message<YuYueGuaHao> response = new Message<>();

        if (yueGuaHaoVo != null) {
            response.setCode("200");
            response.setMsg("Success");
            response.setData(yueGuaHaoVo);
        } else {
            response.setCode("204");
            response.setMsg("No Content");
            response.setData(null);
        }

        return response;
    }

    @PostMapping("/delYuYueGuaHao")
    @CrossOrigin
    public Message<YuYueGuaHao> delYuYueGuaHao(@RequestBody YuYueGuaHao yuYueGuaHao) {
        YuYueGuaHao yuYueGuaHaoVo = yuYueGuaHaoService.getById(yuYueGuaHao.getId());
        Boolean isDel = yuYueGuaHaoService.removeById(yuYueGuaHao.getId());
        Message<YuYueGuaHao> response = new Message<>();

        if (isDel) {
            response.setCode("200");
            response.setMsg("Success");
            response.setData(yuYueGuaHaoVo);
        } else {
            response.setCode("204");
            response.setMsg("No Content");
            response.setData(null);
        }

        return response;
    }
}
