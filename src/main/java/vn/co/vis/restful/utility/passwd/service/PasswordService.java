package vn.co.vis.restful.utility.passwd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import vn.co.vis.restful.constant.DateTime;
import vn.co.vis.restful.utility.DateTimeUtils;
import vn.co.vis.restful.utility.XmlUtils;
import vn.co.vis.restful.utility.passwd.model.Password;
import vn.co.vis.restful.utility.passwd.model.PasswordSet;
import vn.co.vis.restful.utility.passwd.model.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Using to get password from folder passwd config
 *
 */
@Component
public class PasswordService {

    private String passWord;

    private static final String PASSWORD = ".pwd";

    @Value("${password.directory}")
    private String directory;

    public PasswordService(String directory) {
        this.directory = directory;
    }

    public PasswordService() {
    }

    /**
     * Get password from .pwd file
     *
     * @param pwName password name
     * @return password of pwName
     */
    public String getPassword(String pwName) {
        passWord = "";
        List<Password> listPassword = getPasswords();
        if (!listPassword.isEmpty()) {
            passWord = getPassword(listPassword, pwName);
        }
        return passWord;
    }

    /**
     * Get list password from .pwd file
     *
     * @return List of all password
     */
    public List<Password> getPasswords() {
        List<Password> listPassword = new ArrayList<>();
        if (!Files.exists(Paths.get(this.directory))) {
            return Collections.emptyList();
        }
        try {
            XmlUtils xmlIO = new XmlUtils();

            List<File> listFile = xmlIO.getFileList(directory, PASSWORD);
            if (!listFile.isEmpty()) {
                for (File fileP : listFile) {
                    FileReader fileReader = new FileReader(fileP);
                    PasswordSet passwordSet = xmlIO.unmarshal(fileReader, PasswordSet.class);
                    if (!ObjectUtils.isEmpty(passwordSet) && !passwordSet.getPasswordList().isEmpty()) {
                        listPassword.addAll(passwordSet.getPasswordList());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listPassword;
    }

    /**
     * Check and get pass from file .pwd
     *
     * @param passwords PasswdSet
     * @param passWord  String
     * @return password in PasswdSet
     */
    public String getPassword(List<Password> passwords, String passWord) {

        Optional<Password> optPassWord = passwords.stream().filter(obj -> passWord.equals(obj.getName()) &&
                DateTimeUtils.compareDate(DateTimeUtils.getCurrentTime(), DateTimeUtils.toDateFromStr(obj.getExpires(), DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN)) <= 0 &&
                checkValueOfWord(obj.getWord())).findFirst();
        if (optPassWord.isEmpty()) {
            return "";
        }
        return optPassWord.get().getWord().getValue();

    }

    /**
     * Check data word element
     *
     * @param word Word
     * @return true if existed
     */
    public boolean checkValueOfWord(Word word) {
        if (!StringUtils.isEmpty(word.getStart()) && !StringUtils.isEmpty(word.getEnd())) {
            if (DateTimeUtils.compareDate(DateTimeUtils.getCurrentTime(),
                    DateTimeUtils.toDateFromStr(word.getStart(), DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN)) <= 0 ||
                    DateTimeUtils.compareDate(DateTimeUtils.getCurrentTime(), DateTimeUtils.toDateFromStr(word.getEnd(),
                            DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN)) >= 0) {
                return false;
            }
        }
        return true;
    }
}