package wait.play.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import wait.play.model.MyFile;

@Service
public class FileService {

  public String path;

  public List<MyFile> getFiles() {
    List<MyFile> myFiles = new ArrayList<MyFile>();
    List<File> files = new ArrayList<File>();
    listFiles(new File(path), files);
    for (File f : files) {
      if (f.isFile()) {
        MyFile myFile = new MyFile();
        String parentFoler = f.getParentFile().getName();
        myFile.setFileName(parentFoler + "/" + f.getName());
        myFile.setPath(f.getAbsolutePath());
        myFile.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(f.lastModified())));
        myFiles.add(myFile);
      }
    }
    if (!myFiles.isEmpty()) {
      myFiles = myFiles.stream().sorted((o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate())).collect(Collectors.toList());
    }
    return myFiles;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  private void listFiles(File f, List<File> files) {
    if (f.isFile()) {
      files.add(f);
    } else {
      File[] subfiles = f.listFiles();
      if (subfiles != null) {
        for (int i = 0; i < subfiles.length; i++) {
          listFiles(subfiles[i], files);
        }
      }

    }
  }

}
