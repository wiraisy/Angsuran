/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.listener.Confirm;
import org.apache.commons.io.FilenameUtils;
import angsuran.model.Ba;
import angsuran.view.BAFrame;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author User
 */
public class ImportController {

    private AngsuranDao dao = new AngsuranDaoImplements();

    public static Vector read(String fileName) {
        double value = 0.0;
        java.text.DecimalFormat formatter = null;
        java.text.FieldPosition fPosition = null;
        String formattingString = null;
        StringBuffer buffer = null;
        Vector cellVectorHolder = new Vector();
        try {
            formattingString = "#.###";
            formatter = new java.text.DecimalFormat(formattingString);
            fPosition = new java.text.FieldPosition(0);
            buffer = new StringBuffer();
            FileInputStream myInput = new FileInputStream(fileName);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myInput);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                List list = new ArrayList();
                while (cellIter.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIter.next();
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        buffer = new StringBuffer();
                        value = cell.getNumericCellValue();
                        formatter.format(value, buffer, fPosition);
                        cell.setCellValue(buffer.toString());
                    }
                    list.add(cell.getRichStringCellValue().getString());
                }
                cellVectorHolder.addElement(list);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return cellVectorHolder;
    }

    public void SimpanBaExcel(JFrame d) {
        JFileChooser jfc = new JFileChooser();
        int result = jfc.showSaveDialog(jfc);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String fileName = f.getAbsolutePath();
            Vector dataHolder = read(fileName);
            for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
                try {
                    List list = (List) iterator.next();
                    Ba ba = new Ba();
                    ba.setNama_bu(list.get(0).toString());
                    ba.setNo_entitas(list.get(1).toString());
                    ba.setNo_ba(list.get(2).toString());
                    ba.setTanggal_ba(new SimpleDateFormat("dd/MM/yyyy").parse(list.get(3).toString()));
                    ba.setTotal_tunggakan(Double.valueOf(list.get(4).toString()));
                    ba.setTahap_cicilan(Integer.valueOf(list.get(5).toString()));
                    ba.setAlamat_email(list.get(6).toString());
                    ba.setH_notifikasi(Integer.valueOf(list.get(7).toString()));
                    dao.Simpan(ba);
                } catch (ParseException ex) {
                    Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            JOptionPane.showMessageDialog(d, "Data Barang Berhasil Diimport !!");

        } else if (result == JFileChooser.CANCEL_OPTION) {
            jfc.cancelSelection();
            JOptionPane.showMessageDialog(d, "Data Ba Gagal Diimport.....!!!");
        }

    }

    //==========================================================================================================
    public void ReadExcel(String filePath, String fileName, Confirm c) throws InterruptedException, IOException {
        File file = new File(filePath + "\\" + fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook AddCatalog = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        if (fileExtensionName.equals(".xls")) {
            AddCatalog = new HSSFWorkbook(inputStream);
            HSSFCellStyle hcell = (HSSFCellStyle) AddCatalog.createCellStyle();
            CreationHelper ch = AddCatalog.getCreationHelper();
            hcell.setDataFormat(ch.createDataFormat().getFormat("#,##0;\\-#,##0"));
            hcell.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));
            //==============================================================================================
            Sheet AddCatalogSheet = AddCatalog.getSheet("Sheet1");
            int rowcount = AddCatalogSheet.getLastRowNum() - AddCatalogSheet.getFirstRowNum();
            List<Ba> list = new ArrayList<>();
            for (int i = 1; i < rowcount+1; i++) {
                Row row = AddCatalogSheet.getRow(i);
                   try {
                    Ba ba = new Ba();
                    ba.setNama_bu(row.getCell(0).getStringCellValue());
                    ba.setNo_entitas(row.getCell(1).getStringCellValue());
                    ba.setNo_ba(row.getCell(2).getStringCellValue());
                    ba.setTanggal_ba((Date) row.getCell(3).getDateCellValue());
                    ba.setTotal_tunggakan(row.getCell(4).getNumericCellValue());
                    ba.setTahap_cicilan((int) row.getCell(5).getNumericCellValue());
                    ba.setAlamat_email(row.getCell(6).getStringCellValue());
                    ba.setH_notifikasi((int) row.getCell(7).getNumericCellValue());
                    list.add(ba);
                    dao.Simpan(ba);
                } catch (Exception ex) {
                    Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }
            c.Berhasil("Data Ba Sebanyak "+list.size()+ " Berhasil DiImport");
        } else if (fileExtensionName.equals(".xlsx")) {
            AddCatalog = new XSSFWorkbook(inputStream);
            XSSFCellStyle xcell = (XSSFCellStyle) AddCatalog.createCellStyle();
            CreationHelper ch = AddCatalog.getCreationHelper();
            xcell.setDataFormat(ch.createDataFormat().getFormat("#,##0;\\-#,##0"));
            xcell.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));
            //==============================================================================
            Sheet AddCatalogSheet = AddCatalog.getSheet("Sheet1");
            int rowcount = AddCatalogSheet.getLastRowNum() - AddCatalogSheet.getFirstRowNum();
            List<Ba> list = new ArrayList<>();
            for (int i = 1; i < rowcount+1; i++) {
                Row row = AddCatalogSheet.getRow(i);
                   try {
                       Ba ba = new Ba();
                       ba.setNama_bu(row.getCell(0).getStringCellValue());
                       ba.setNo_entitas(row.getCell(1).getStringCellValue());
                       ba.setNo_ba(row.getCell(2).getStringCellValue());
                       ba.setTanggal_ba(row.getCell(3).getDateCellValue());
                       ba.setTotal_tunggakan(row.getCell(4).getNumericCellValue());
                       ba.setTahap_cicilan((int) row.getCell(5).getNumericCellValue());
                       ba.setAlamat_email(row.getCell(6).getStringCellValue());
                       ba.setH_notifikasi((int) row.getCell(7).getNumericCellValue());
                       ba.setStatus_tunggakan("BELUM LUNAS");
                       if(dao.getBabynoentitas(ba.getNo_entitas()) == null){
                            list.add(ba);
                            dao.Simpan(ba);
                       }                     
                } catch (Exception ex) {
                    Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }          
            c.Berhasil("Data Ba Sebanyak "+list.size()+ " Berhasil DiImport");
            }
           
        }
    
      public void LoadFilechooser(BAFrame d, Confirm c){
          JFileChooser jfc = new JFileChooser();
          int result = jfc.showSaveDialog(jfc);
          if (result == JFileChooser.APPROVE_OPTION) {
              File f = jfc.getSelectedFile();
              String url = f.getPath();
              String filePath = FilenameUtils.getFullPath(url);
              String fileName = FilenameUtils.getBaseName(url)+ "." + FilenameUtils.getExtension(url);
              try {
                  ReadExcel(filePath, fileName,c);
              } catch (InterruptedException | IOException ex) {
                  Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
              }
              System.out.println(filePath);
              System.out.println(fileName);
          }else if(result == JFileChooser.CANCEL_OPTION){
              c.Warning("Gagal memilih File");
          }
              
      }
          
    

}
