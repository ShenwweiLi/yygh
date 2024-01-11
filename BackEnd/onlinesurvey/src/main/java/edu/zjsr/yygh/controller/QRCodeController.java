package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.annotation.UserLoginToken;
import edu.zjsr.yygh.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("yygh")
public class QRCodeController {
    @Autowired
    private QRCodeService qrCodeService;

    /**
     * 生成问卷地址跳转的二维码
     * @param surveyUrl
     * @return
     */
    @UserLoginToken
    @PostMapping("publish/getQRCode")
    @CrossOrigin
    public Message createSurveyQRCode(@RequestParam String surveyUrl) throws Exception {
        String imageBase64 = "data:image/png;base64," + qrCodeService.createQRCode(surveyUrl);
        Message message = new Message();
        message.setCode("200");
        message.setMsg("ok");
        message.setData(imageBase64);
        return message;
    }
}
