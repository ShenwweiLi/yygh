package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Doctor;
import edu.zjsr.yygh.service.DoctorService;
import edu.zjsr.yygh.vo.DoctorListVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author lishishan
 * @date 2024/3/13/17:04
 */

@RestController
@RequestMapping("yygh/doctor")
@CrossOrigin
public class DoctorController {

    @Resource
    DoctorService DoctorService;

    @GetMapping("/doctorList")
    @CrossOrigin
    public Message<DoctorListVo> getDoctorWithPagination(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "keshi", required = false) String keshi) {
        System.out.println("ArticlesController---------search"+search);
        System.out.println("ArticlesController---------category"+keshi);
        DoctorListVo doctorVo = DoctorService.findDoctorWithPagination(currentPage, pageSize, search, keshi);
        Message<DoctorListVo> response = new Message<>();

        if (doctorVo != null && doctorVo.getDoctorList() != null && !doctorVo.getDoctorList().isEmpty()) {
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

    @PostMapping("/getDoctorById")
    @CrossOrigin
    public Message<Doctor> getDoctorById(@RequestBody Doctor doctor) {

        Doctor doctorVo = DoctorService.getById(doctor.getId());
        Message<Doctor> response = new Message<>();

        if (doctorVo != null) {
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


    @PostMapping("/saveOrUpdateDoctor")
    @CrossOrigin
    public Message<Doctor> saveOrUpdateDoctor(@RequestBody Doctor doctor) {

        Boolean isSave = DoctorService.saveOrUpdate(doctor);
        Doctor doctorVo = DoctorService.getById(doctor.getId());
        Message<Doctor> response = new Message<>();

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


    @PostMapping("/delDoctor")
    @CrossOrigin
    public Message<Doctor> delDoctor(@RequestBody Doctor doctor) {
        Doctor doctorVo = DoctorService.getById(doctor.getId());
        Boolean isDel = DoctorService.removeById(doctor.getId());
        Message<Doctor> response = new Message<>();

        if (isDel) {
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


}
