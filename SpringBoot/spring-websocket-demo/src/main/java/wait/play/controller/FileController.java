package wait.play.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import wait.play.model.MyFile;
import wait.play.service.FileService;

@Controller
public class FileController {

  @Autowired
  private FileService fileService;

  private final String PATH = "D:\\迅雷下载";

  @RequestMapping(value = {"/file"})
  public String listFiles(Model model) {
    fileService.setPath(PATH);
    List<MyFile> files = fileService.getFiles();
    model.addAttribute("files", files);
    return "file";

  }

  @RequestMapping(value = "/file/download/{folderName}/{fileName:.+}", method = RequestMethod.GET)
  public ResponseEntity<InputStreamResource> download(@PathVariable("fileName") String fileName,
                                                      @PathVariable("folderName") String folderName) throws IOException {
    File f = new File(PATH);
    FileSystemResource file = new FileSystemResource(
        f.getAbsolutePath() + File.separator + fileName);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .body(new InputStreamResource(file.getInputStream()));
  }

  @RequestMapping(value = "/file/del/{folderName}/{fileName:.+}", method = RequestMethod.GET)
  public String del(@PathVariable("fileName") String fileName, @PathVariable("folderName") String folderName,
                    Model model) throws IOException {
    File f = new File(PATH);

    File file = new File(f.getAbsolutePath() + File.separator + fileName);
    if (file.exists()) {
      file.delete();
    }

    File parent = new File(f.getAbsolutePath() + File.separator + folderName);
    if (parent.exists()) {
      parent.delete();
    }

    fileService.setPath(PATH);
    List<MyFile> files = fileService.getFiles();
    model.addAttribute("files", files);
    return "file";
  }

}
