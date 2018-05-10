package husin;




import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class MainController {

    @RequestMapping(value = "/fileUploadPage", method = RequestMethod.GET)
    public ModelAndView fileUploadPage() {
        //设置文件上传界面(欢迎界面)
        ModelAndView modelAndView = new ModelAndView("fileUpload");
        return modelAndView;
    }

    //当在这个页面下有post请求
    //上传的文件会自动被绑定到FileModel(Bean)的MultipartFile中
    @RequestMapping(value="/fileUploadPage", method = RequestMethod.POST)
    public String fileUpload(@Validated FileModel file, BindingResult result, ModelMap model) throws IOException {
        if (result.hasErrors()) {
            System.out.println("validation errors");
            return "fileUploadPage";
        } else {
            System.out.println("Fetching file");
            MultipartFile multipartFile = file.getFile();
            String uploadPath = "/home/pzs/husin"+File.separator+"temp"+File.separator;

            //Now do something with file...
            FileCopyUtils.copy(file.getFile().getBytes(), new File(uploadPath+file.getFile().getOriginalFilename()));
            String fileName = multipartFile.getOriginalFilename();
            //把文件名返回给success.jsp
            model.addAttribute("finishfilename", fileName);
            return "success";
        }
    }

}

