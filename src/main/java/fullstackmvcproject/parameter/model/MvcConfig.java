package fullstackmvcproject.parameter.model;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        exposeDirectory(registry);
    }

    private void exposeDirectory(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("employee-photo");
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + "employee-photo" + "/**").addResourceLocations("file:/" + uploadPath + "/");
    }
    //		String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//		employee.setPhoto(fileName);
//		Employee empl = employeeService.saveEmployee(employee);
//		String uploadDir = "employee-photo/" + empl.getId();
//		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

}
