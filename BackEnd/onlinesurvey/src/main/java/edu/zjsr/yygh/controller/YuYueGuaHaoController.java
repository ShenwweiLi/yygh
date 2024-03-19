package edu.zjsr.yygh.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Comments;
import edu.zjsr.yygh.entity.Doctor;
import edu.zjsr.yygh.entity.YuYueGuaHao;
import edu.zjsr.yygh.service.YuYueGuaHaoService;
import edu.zjsr.yygh.vo.DoctorListVo;
import edu.zjsr.yygh.vo.YuYueGuaHaoListVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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

    String secret = "survey.yonatan.cn.secret";
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

    @GetMapping("/guahaoListWithUser")
    @CrossOrigin
    public Message<YuYueGuaHaoListVo> getDoctorWithPaginationByUser(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "search", required = false) String search,@RequestHeader(value="Authorization") String token) {

        // 使用相同的密钥和算法构建JWT验证器
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // 验证token并解析token
        DecodedJWT jwt = verifier.verify(token);

        // 获取token中包含的所有claims
        Map<String, Claim> claims = jwt.getClaims();

        // 遍历claims，获取键值对
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue().asString());
        }

        // 直接通过claim名称获取特定的claim
        String userId = jwt.getClaim("id").asString();

        String role = jwt.getClaim("role").asString();
        System.out.println("role:"+role);

        YuYueGuaHaoListVo yueGuaHaoListVo = yuYueGuaHaoService.findListrWithPaginationWithUser(currentPage, pageSize, search,userId);

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



    @GetMapping("/guahaoListWithDoctor")
    @CrossOrigin
    public Message<YuYueGuaHaoListVo> guahaoListWithDoctor(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "search", required = false) String search,@RequestHeader(value="Authorization") String token) {

        // 使用相同的密钥和算法构建JWT验证器
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // 验证token并解析token
        DecodedJWT jwt = verifier.verify(token);

        // 获取token中包含的所有claims
        Map<String, Claim> claims = jwt.getClaims();

        // 遍历claims，获取键值对
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue().asString());
        }

        // 直接通过claim名称获取特定的claim
        String userId = jwt.getClaim("id").asString();


        YuYueGuaHaoListVo yueGuaHaoListVo = yuYueGuaHaoService.findListrWithPaginationWithDoctor(currentPage, pageSize, search,userId);

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
    public Message<YuYueGuaHao> saveOrUpdateGuaHao(@RequestBody YuYueGuaHao yuYueGuaHao,@RequestHeader(value="Authorization") String token) {

        // 使用相同的密钥和算法构建JWT验证器
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // 验证token并解析token
        DecodedJWT jwt = verifier.verify(token);

        // 获取token中包含的所有claims
        Map<String, Claim> claims = jwt.getClaims();

        // 遍历claims，获取键值对
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue().asString());
        }

        // 直接通过claim名称获取特定的claim
        String userId = jwt.getClaim("id").asString();
        yuYueGuaHao.setZhanghao(userId);
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
