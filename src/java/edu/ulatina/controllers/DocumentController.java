package edu.ulatina.controllers;

import edu.ulatina.serviceTO.*;
import edu.ulatina.transfereObjects.*;
import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.application.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Nwitlyck
 */
@ManagedBean(name = "documentsController")
@SessionScoped
public class DocumentController {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    private UploadedFile originalPdfFile;

    private DocTO docTO;

    private ServiceDocsTO serviceDocsTO;

    private List<DocTO> listDocsTO;

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public UploadedFile getOriginalPdfFile() {
        return originalPdfFile;
    }

    public void setOriginalPdfFile(UploadedFile originalPdfFile) {
        this.originalPdfFile = originalPdfFile;
    }

    public DocTO getDocTO() {
        return docTO;
    }

    public void setDocTO(DocTO docTO) {
        this.docTO = docTO;
    }

    public ServiceDocsTO getServiceDocsTO() {
        return serviceDocsTO;
    }

    public void setServiceDocsTO(ServiceDocsTO serviceDocsTO) {
        this.serviceDocsTO = serviceDocsTO;
    }

    public List<DocTO> getListDocsTO() {
        return listDocsTO;
    }

    public void setListDocsTO(List<DocTO> listDocsTO) {
        this.listDocsTO = listDocsTO;
    }

    //metods
    @PostConstruct
    public void initianizate() {
        docTO = new DocTO();
        serviceDocsTO = new ServiceDocsTO();
        fillListDocsTO();
    }

    public void fillListDocsTO() {
        try {
            listDocsTO = serviceDocsTO.selectByColaboratorId(loginController.getLogColaboratorTO().getId());

        } catch (Exception e) {
            e.printStackTrace();
            listDocsTO = new ArrayList<DocTO>();
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.originalPdfFile = null;
            UploadedFile file = event.getFile();
            if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
                this.originalPdfFile = file;
                this.copyFileInFileSystem(file.getInputStream(), "C:\\Users\\david\\OneDrive\\Escritorio\\DocsSavedProjectPegasus", this.originalPdfFile.getFileName());
                FacesMessage msg = new FacesMessage("Successful", this.originalPdfFile.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFileInFileSystem(InputStream input, String pathCopy, String fileName) throws FileNotFoundException, IOException {
        Path path = Paths.get(pathCopy, fileName);
        if (Files.exists(path.getParent())) {
            try {
                System.out.println("PROCESS FILE PATH===> " + path);
                Files.copy(input, path, REPLACE_EXISTING);
                saveDoc(path.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //IOUtils.closeQuietly(input);
                //IOUtils.closeQuietly(output);
            }
        } else {
            try {
                System.out.println("PROCESS FILE PATH===> " + path);
                Files.createDirectories(path.getParent());
                try {
                    Files.copy(input, path, REPLACE_EXISTING);
                    saveDoc(path.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //IOUtils.closeQuietly(input);
                    //IOUtils.closeQuietly(output);
                }
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }

    public void saveDoc(String docLocation) {

        docTO.setColaboratorId(loginController.getLogColaboratorTO().getId());
        docTO.setType(1);
        docTO.setDocLocation(docLocation);

        try {
            serviceDocsTO.insert(docTO);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to save document data"));
        }
        fillListDocsTO();
    }

    public void deleteDoc() {

        try {
            serviceDocsTO.delete(docTO);
            File doc = new File(docTO.getDocLocation());
            if (doc.delete()) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Deleted the file: " + doc.getName()));
            } else {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Failed to delete the file"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to delete document data"));
        }
        fillListDocsTO();
    }

    public StreamedContent getDownloadDoc() {
        File doc = new File(docTO.getDocLocation());

        return DefaultStreamedContent.builder()
                .name(doc.getName())
                .contentType("application/pdf")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(docTO.getDocLocation()))
                .build();
    }

}
