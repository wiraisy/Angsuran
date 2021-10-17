/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.Generatekode;
import angsuran.helper.HelperUmum;
import angsuran.listener.Confirm;
import org.apache.commons.io.FilenameUtils;
import angsuran.model.Ba;
import angsuran.model.Cicilan;
import angsuran.view.BAFrame;
import angsuran.view.CicilanFrame;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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

    //==========================================================================
    public void ReadExcelBA(String filePath, String fileName, Confirm c) throws InterruptedException, IOException {
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
            //==================================================================
            Sheet AddCatalogSheet = AddCatalog.getSheet("Sheet1");
            int rowcount = AddCatalogSheet.getLastRowNum() - AddCatalogSheet.getFirstRowNum();
            List<Ba> list = new ArrayList<>();
            for (int i = 1; i < rowcount + 1; i++) {
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
            c.Berhasil("Data Ba Sebanyak " + list.size() + " Berhasil DiImport");
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
            for (int i = 1; i < rowcount + 1; i++) {
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
                    if (dao.getBabynoentitas(ba.getNo_entitas()) == null) {
                        list.add(ba);
                        dao.Simpan(ba);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            c.Berhasil("Data Ba Sebanyak " + list.size() + " Berhasil DiImport");
        }

    }

    public void ReadExcelCicilan(String filePath, String fileName, Confirm c) throws InterruptedException, IOException {
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
            //==================================================================
            Sheet AddCatalogSheet = AddCatalog.getSheet("Sheet1");
            int rowcount = AddCatalogSheet.getLastRowNum() - AddCatalogSheet.getFirstRowNum();
            List<Cicilan> list = new ArrayList<>();
            for (int i = 1; i < rowcount + 1; i++) {
                Row row = AddCatalogSheet.getRow(i);
                try {
                    Ba ba = dao.getBabynoentitas(row.getCell(0).getStringCellValue());
                    if (ba != null) {
                        Cicilan cil = new Cicilan();
                        cil.setBa(ba);
                        cil.setKode_cicilan(new Generatekode().Generatekodecicilan(ba, c));
                        cil.setTahap_cicilan(row.getCell(1).getStringCellValue());
                        cil.setTanggal_cicilan(row.getCell(2).getDateCellValue());
                        cil.setNominal_cicilan(row.getCell(3).getNumericCellValue());
                        cil.setTagihan_berjalan(row.getCell(4).getNumericCellValue());
                        cil.setTotal(cil.getNominal_cicilan() + cil.getTagihan_berjalan());
                        cil.setTotal_kekurangan(cil.getTotal());
                        cil.setStatus("BELUM LUNAS");
                        cil.setSentnotification(Boolean.FALSE);
                        cil.setTanggal_notifikasi_terakhir(HelperUmum.getdatenotifikasi(cil.getTanggal_cicilan(), ba.getH_notifikasi()));
                        //======================================================
                        list.add(cil);
                        //======================================================
                        List<Cicilan> listcil = dao.getallcicilanbybaandstatus(ba, "BELUM LUNAS", cil.getTanggal_notifikasi_terakhir());
                        if (!listcil.isEmpty()) {
                            for (Cicilan cilo : listcil) {
                                Cicilan cila = dao.getcicilanbyid(cilo.getId_cicilan());
                                cila.setTanggal_notifikasi_terakhir(cil.getTanggal_notifikasi_terakhir());
                                dao.Update(cila);
                            }
                        }
                        dao.Simpan(ba);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            c.Berhasil("Data Ba Sebanyak " + list.size() + " Berhasil DiImport");
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
            for (int i = 1; i < rowcount + 1; i++) {
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
                    if (dao.getBabynoentitas(ba.getNo_entitas()) == null) {
                        list.add(ba);
                        dao.Simpan(ba);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            c.Berhasil("Data Ba Sebanyak " + list.size() + " Berhasil DiImport");
        }

    }

    public void LoadFilechooserBA(BAFrame d, Confirm c) {
        JFileChooser jfc = new JFileChooser();
        int result = jfc.showSaveDialog(jfc);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String url = f.getPath();
            String filePath = FilenameUtils.getFullPath(url);
            String fileName = FilenameUtils.getBaseName(url) + "." + FilenameUtils.getExtension(url);
            try {
                ReadExcelBA(filePath, fileName, c);
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(filePath);
            System.out.println(fileName);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            c.Warning("Gagal memilih File");
        }

    }
    
    public void LoadFilechooserCicilan(CicilanFrame d, Confirm c) {
        JFileChooser jfc = new JFileChooser();
        int result = jfc.showSaveDialog(jfc);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String url = f.getPath();
            String filePath = FilenameUtils.getFullPath(url);
            String fileName = FilenameUtils.getBaseName(url) + "." + FilenameUtils.getExtension(url);
            try {
                ReadExcelCicilan(filePath, fileName, c);
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(filePath);
            System.out.println(fileName);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            c.Warning("Gagal memilih File");
        }

    }

    public void ExportTemplateBA() throws IOException {
        String path = "D:\\TEMPLATEBA\\TemplateBA.xlsx";
        String pathfolder = "D:\\TEMPLATEBA";
        if (new File(path).exists()) {
            Desktop dt = Desktop.getDesktop();
            dt.open(new File(path));
        } else {
            new File(pathfolder).mkdir();
            //==============================================================
            String[] columns = {"NAMA BA", "NO ENTITAS", "NO BU", "TANGGAL", "TOTAL TAGIHAN", "JUMLAH ANGSURAN", "EMAIL", "H-NOTIFIKASI"};
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("DATA_BA");
            int rowNum = 0;
            //==================================================================
            Font headerFont = workbook.createFont();
            headerFont.setFontHeightInPoints((short) 13);
            headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            //==================================================================
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
            headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
            headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            headerCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            headerCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            headerCellStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
            //==================================================================
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            //==================================================================      
            //Create Header
            Row headerRow = sheet.createRow(rowNum++);
            int i = 0;
            for (String nama : columns) {
                Cell cell = headerRow.createCell(i++);
                cell.setCellValue(nama);
                cell.setCellStyle(headerCellStyle);
            }
            // Create a Row
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);
            //==================================================================
            for (int a = 0; a < columns.length; a++) {
                Row row = sheet.createRow(rowNum++);
                //=============================================================
                XSSFCellStyle datestyle = (XSSFCellStyle) workbook.createCellStyle();
                CreationHelper ch = workbook.getCreationHelper();
                datestyle.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));
                //==============================================================
                row.createCell(0);
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(0).setCellStyle(style);
                //==============================================================
                row.createCell(1);
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(1).setCellStyle(style);
                //==============================================================
                row.createCell(2);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellStyle(style);
                //==============================================================
                row.createCell(3);
                row.getCell(3).setCellStyle(datestyle);
                //==============================================================
                row.createCell(4);
                row.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
                row.getCell(4).setCellStyle(style);
                //==============================================================
                row.createCell(5);
                row.getCell(5).setCellType(Cell.CELL_TYPE_NUMERIC);
                row.getCell(5).setCellStyle(style);
                //==============================================================
                row.createCell(6);
                row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(6).setCellStyle(style);
                //==============================================================
                row.createCell(7);
                row.getCell(7).setCellType(Cell.CELL_TYPE_NUMERIC);
                row.getCell(7).setCellStyle(style);
                //==============================================================

            }
            // Resize all columns to fit the content size
            for (int x = 0; x < i; x++) {
                sheet.autoSizeColumn(x);
            }
            try ( // Write the output to a file
                    FileOutputStream fileOut = new FileOutputStream(path)) {
                    workbook.write(fileOut);
                    fileOut.close();
                    Desktop dtt = Desktop.getDesktop();
                    dtt.open(new File(path));
            }

        }

    }

}
