package temp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;


@Controller
public class _GoController
{

    @RequestMapping(value = {"/temp/go"})
    public String index(Model model, HttpSession httpSession) throws Exception
    {
        System.out.println("======processed by index=======");
        System.out.println(httpSession.getId());

        model.addAttribute("msg", "Go Go Go!");

        return "/temp/go2";
        //return "/temp/mainPage.html";
    }

}
