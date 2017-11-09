package main;

import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;

/**
 * Created by mengfeifei on 2017/11/9.
 */
public class MyFileReader {
    //RTF file read
    public String getTextFromRtf(String filePath) {
        String result = null;
        File file = new File(filePath);
        try {
            DefaultStyledDocument styledDoc = new DefaultStyledDocument();
            InputStream is = new FileInputStream(file);
            new RTFEditorKit().read(is, styledDoc, 0);
            //result = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("ISO8859_1"));
            result = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("utf-8"));
            //提取文本，读取中文需要使用ISO8859_1编码，否则会出现乱码
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param filePath 文件路径
     * @return 获得html的全部内容
     */
    public String readHtml(String filePath) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * @param filePath 文件路径
     * @return 获得的html文本内容
     */
    public String getTextFromHtml(String filePath) {
        //得到body标签中的内容
        String str = readHtml(filePath);
        StringBuffer buff = new StringBuffer();
        int maxindex = str.length() - 1;
        int begin = 0;
        int end;
        //截取>和<之间的内容
        while ((begin = str.indexOf('>', begin)) < maxindex) {
            end = str.indexOf('<', begin);
            if (end - begin > 1) {
                buff.append(str.substring(++begin, end));
            }
            begin = end + 1;
        }
        ;
        return buff.toString();
    }

    /**
     * @param filePath 文件路径
     * @return 读出的txt的内容
     */
    public String getTextFromTxt(String filePath) throws Exception {

        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer buff = new StringBuffer();
        String temp = null;
        while ((temp = br.readLine()) != null) {
            buff.append(temp + "\r\n");
        }
        br.close();
        return buff.toString();
    }

    /**
     * @param filePath 文件路径
     * @return 读出的pdf的内容
     */
    public String getTextFromPdf(String filePath) {
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;
        try {
            is = new FileInputStream(filePath);
            PDFParser parser = new PDFParser((RandomAccessRead) is);
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * @param filePath 文件路径
     * @return 读出的Word的内容
     */
    public String getTextFromWord3(String filePath) {
        String result = null;
        File file = new File(filePath);
        try {
            FileInputStream fis = new FileInputStream(file);
            WordExtractor wordExtractor = new WordExtractor(fis);
            result = wordExtractor.getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        return result;
    }

    public String getTextFromWord7(String filePath) {
        String result = null;
        //File file = new File(filePath);
        try {
            OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            result = extractor.getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param filePath 文件路径
     * @return 读出的Excel的内容
     */
    public String getTextFromExcel(String filePath) {
        StringBuffer buff = new StringBuffer();
        try {
            //创建对Excel工作簿文件的引用
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
            //创建对工作表的引用。
            for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
                if (null != wb.getSheetAt(numSheets)) {
                    HSSFSheet aSheet = wb.getSheetAt(numSheets);//获得一个sheet
                    for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                        if (null != aSheet.getRow(rowNumOfSheet)) {
                            HSSFRow aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                            for (int cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                                if (null != aRow.getCell(cellNumOfRow)) {
                                    HSSFCell aCell = aRow.getCell(cellNumOfRow);//获得列值
                                    switch (aCell.getCellType()) {
                                        case HSSFCell.CELL_TYPE_FORMULA:
                                            break;
                                        case HSSFCell.CELL_TYPE_NUMERIC:
                                            buff.append(aCell.getNumericCellValue()).append('\t');
                                            break;
                                        case HSSFCell.CELL_TYPE_STRING:
                                            buff.append(aCell.getStringCellValue()).append('\t');
                                            break;
                                    }
                                }
                            }
                            buff.append('\n');
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buff.toString();
    }
}
