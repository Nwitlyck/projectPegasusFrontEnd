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
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

/*
 * @author Nwitlyck
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private String email;

    private String password;

    private ColaboratorTO logColaboratorTO;

    private UploadedFile originalPdfFile;

    private DocTO docTO;

    private ServiceDocsTO serviceDocsTO;

    private List<DocTO> listDocsTO;

    public LoginController() {
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ColaboratorTO getLogColaboratorTO() {
        return logColaboratorTO;
    }

    public void setLogColaboratorTO(ColaboratorTO logColaboratorTO) {
        this.logColaboratorTO = logColaboratorTO;
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
            listDocsTO = serviceDocsTO.select();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to get data"));
            listDocsTO = new ArrayList<DocTO>();
        }
    }

    public void logIn() {
        if (verifyNulls()) {
            return;
        }

        if (!verifyUser()) {
            return;
        }

        try {
            this.logColaboratorTO = new ServiceColaboratorTO().selectByEmail(this.email);
            this.email = "";
            this.password = "";

        } catch (Exception e) {
        }

        this.redirect("/faces/Main.xhtml");
    }

    public boolean verifyNulls() {
        if (this.email.isEmpty() || this.email == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email is empity"));
            return true;
        }
        
        if (this.password.isEmpty() || this.password == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The password is empity"));
            return true;
        }
        return false;
    }

    public boolean verifyUser() {
        ColaboratorTO colaboratorTO;
        try {
            colaboratorTO = new ServiceColaboratorTO().selectByEmail(this.email);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Error at the time to connect with data base"));
            return false;
        }

        if (colaboratorTO == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email or password are incorrect"));
            return false;
        }

        if (!colaboratorTO.getPassword().equals(this.password)) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email or password are incorrect"));
            return false;
        }

        return true;
    }

    public void redirect(String ruta) {
        HttpServletRequest request = null;
        try {

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public boolean isLog() {
        if (logColaboratorTO == null) {
            redirect("/faces/index.xhtml");
            return false;
        }
        return true;
    }

    public ColaboratorTO logColaborator() {

        if (!isLog()) {
            return this.logColaboratorTO;
        }

        return this.logColaboratorTO;
    }

    public boolean isManager() {

        if (!isLog()) {
            return false;
        }

        if (logColaboratorTO.getAcceslevel() == 0) {
            return false;
        }

        return true;
    }

    public boolean isAdmin() {
        if (!isLog()) {
            return false;
        }

        if (logColaboratorTO.getAcceslevel() != 2) {
            return false;
        }

        return true;
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

        docTO.setColaboratorId(logColaborator().getId());
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
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Deleted the file: "+ doc.getName()));
            } else {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Failed to delete the file"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to delete document data"));
        }
        fillListDocsTO();
    }

}
