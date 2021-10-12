/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

/**
 *
 * @author BADASS
 */
public class HelperUmum {
    
    public static void setlogoframe(final JFrame d) {
        d.setIconImage(Toolkit.getDefaultToolkit().getImage(d.getClass().getResource("/angsuran/image/logo.png")));
    }
    
    public static void setlogodialog(final JDialog d) {
        d.setIconImage(Toolkit.getDefaultToolkit().getImage(d.getClass().getResource("/angsuran/image/logo.png")));
    }
    
    public static String angkaToTerbilang(Long angka){
        
        String[] huruf={"","Satu","Dua","Tiga","Empat","Lima","Enam","Tujuh","Delapan","Sembilan","Sepuluh","Sebelas"};
        
        if(angka < 12)
            return huruf[angka.intValue()];
        if(angka >=12 && angka <= 19)
           return huruf[angka.intValue() % 10] + " Belas";
        if(angka >= 20 && angka <= 99)
           return angkaToTerbilang(angka / 10) + " Puluh " + huruf[angka.intValue() % 10];
        if(angka >= 100 && angka <= 199)
           return "Seratus " + angkaToTerbilang(angka % 100);
        if(angka >= 200 && angka <= 999)
           return angkaToTerbilang(angka / 100) + " Ratus " + angkaToTerbilang(angka % 100);
        if(angka >= 1000 && angka <= 1999)
           return "Seribu " + angkaToTerbilang(angka % 1000);
        if(angka >= 2000 && angka <= 999999)
           return angkaToTerbilang(angka / 1000) + " Ribu " + angkaToTerbilang(angka % 1000);
        if(angka >= 1000000 && angka <= 999999999)
           return angkaToTerbilang(angka / 1000000) + " Juta " + angkaToTerbilang(angka % 1000000);
        if(angka >= 1000000000 && angka <= 999999999999L)
           return angkaToTerbilang(angka / 1000000000) + " Milyar " + angkaToTerbilang(angka % 1000000000);
        if(angka >= 1000000000000L && angka <= 999999999999999L)
           return angkaToTerbilang(angka / 1000000000000L) + " Triliun " + angkaToTerbilang(angka % 1000000000000L);
        if(angka >= 1000000000000000L && angka <= 999999999999999999L)
          return angkaToTerbilang(angka / 1000000000000000L) + " Quadrilyun " + angkaToTerbilang(angka % 1000000000000000L);
        return "";
        }
    
    //==========================================================================
    
    public static String setDoubletoIDR(Double nominal){
        return String.format("%,.0f", nominal);
    }
    
    public static Date getdatenotifikasi(Date tanggalawal, Integer DayNotifikasi) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(tanggalawal);
        cal.add(Calendar.DATE, -DayNotifikasi);
        return cal.getTime();
    }
    
     public static Date getnextdatenotifikasi(Date tanggalawal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(tanggalawal);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }
    
    
    //==========================================================================

    private static final String BAD_CHARS = "`~!@#$%^&*()_+=\\|\"':;?/>.<, ";
    
    public static Long RoundUp(double number, long multiple) {

        long result = multiple;

        if (number % multiple == 0) {
            return (long) number;
        }

        // If not already multiple of given number

        if (number % multiple != 0) {

            long division = (long) ((number / multiple) + 1l);

            result = division * multiple;

        }
        return result;

    }
    //========================= COPY PASTE -------------------------------------
    public static void kopas(final JTextField textField) {

        JPopupMenu menu = new JPopupMenu();
        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Cut");
        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        menu.add(cut);

        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copy");
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add(copy);

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Paste");
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        menu.add(paste);

        Action selectAll = new SelectAll();
        menu.add(selectAll);

        textField.setComponentPopupMenu(menu);
    }
    
    public static void kopasarea(final JTextArea textField) {

        JPopupMenu menu = new JPopupMenu();
        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Cut");
        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        menu.add(cut);

        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copy");
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add(copy);

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Paste");
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        menu.add(paste);

        Action selectAll = new SelectAll();
        menu.add(selectAll);

        textField.setComponentPopupMenu(menu);
    }

    public static class SelectAll extends TextAction {

        public SelectAll() {
            super("Select All");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        }

        public void actionPerformed(ActionEvent e) {
            JTextComponent component = getFocusedComponent();
            component.selectAll();
            component.requestFocusInWindow();
        }
    }
    
    //==========================================================================
    
    public static void setUpResolution(final JFrame d) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        /*
         * Disini kita mengatur jika taskbar windows terlihat,
         * taskbar tidak akan tertimpa JFrame pada resolusi max.
         */
        d.setMaximizedBounds(env.getMaximumWindowBounds());
        d.setExtendedState(d.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    public static void autoResizeColumn(JTable jTable1) {
        JTableHeader header = jTable1.getTableHeader();
        int rowCount = jTable1.getRowCount();

        final Enumeration columns = jTable1.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) jTable1.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(jTable1, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();

            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) jTable1.getCellRenderer(row, col).getTableCellRendererComponent(jTable1,
                        jTable1.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column); // this line is very important
            column.setWidth(width + jTable1.getIntercellSpacing().width);
        }
    }

//    public static void fullScreen(final Component c, final boolean absolute) {
//        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        c.setPreferredSize(new Dimension(1024, 768));
//        final int width = c.getWidth();
//        final int height = c.getHeight();
//        int x = (screenSize.width / 2) - (width / 2);
//        int y = (screenSize.height / 2) - (height / 2);
//        if (!absolute) {
//            x /= 2;
//            y /= 2;
//        }
//        try {
//
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            // If Nimbus is not available, fall back to cross-platform
//            try {
//                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//                // not worth my time
//            }
//        }
//        c.setLocation(x, y);
//    }
    
    
    public static void fullScreen(final Component c, final boolean absolute) {
        // Test if each monitor will support my app's window
       // Iterate through each monitor and see what size each is
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
//        Dimension mySize = new Dimension(c.getWidth(), c.getHeight());
        Dimension maxSize = Toolkit.getDefaultToolkit().getScreenSize();
        for (int i = 0; i < gs.length; i++) {
            DisplayMode dm = gs[i].getDisplayMode();
            if (dm.getWidth() > maxSize.getWidth() && dm.getHeight() > maxSize.getHeight()) {   // Update the max size found on this monitor
                maxSize.setSize(dm.getWidth(), dm.getHeight());
            }

    // Do test if it will work here
            c.setSize(maxSize);
        }
        try {
//            Plastic3DLookAndFeel.setPlasticTheme(new SkyGreen());
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                // not worth my time
            }
        }
    }

    public static void jdialogcenterOnScreen(Component c, final boolean absolute) {
        final int width = c.getWidth();
        final int height = c.getHeight();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width / 2) - (width / 2);
        int y = (screenSize.height / 2) - (height / 2);
        if (!absolute) {
            x /= 2;
            y /= 2;
        }
        try {
//            Plastic3DLookAndFeel.setPlasticTheme(new SkyGreen());
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                // not worth my time
            }
        }
        c.setLocation(x, y);
    }
    

    public static String ubahdoublekerupiah(double masukan) {
        return String.format("%,.0f", masukan);
    }

    public static int ubahStringBulanMenjadiIntegerBulan(String bulan) {
        if (bulan.isEmpty()) {
            return 0;
        }

        int bulanAngka = 1;
        switch (bulan) {
            case "JANUARI":
                bulanAngka = 1;
                break;
            case "FEBRUARI":
                bulanAngka = 2;
                break;
            case "MARET":
                bulanAngka = 3;
                break;
            case "APRIL":
                bulanAngka = 4;
                break;
            case "MEI":
                bulanAngka = 5;
                break;
            case "JUNI":
                bulanAngka = 6;
                break;
            case "JULI":
                bulanAngka = 7;
                break;
            case "AGUSTUS":
                bulanAngka = 8;
                break;
            case "SEPTEMBER":
                bulanAngka = 9;
                break;
            case "OKTOBER":
                bulanAngka = 10;
                break;
            case "NOVEMBER":
                bulanAngka = 11;
                break;
            case "DESEMBER":
                bulanAngka = 12;
                break;
            default:
                bulanAngka = 0;
                break;
        }
        return bulanAngka;
    }

    /**
     * Method agar ukuran column pada JTable sesuai dengan value
     *
     * @param t
     */
    public static void sesuaikanLebarColumnTable(JTable t) {
        //========================CENTER===========================================
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
//        TableModel tableModel = t.getModel();
//        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
//            t.getTableHeader().getColumnModel().getColumn(columnIndex).setHeaderRenderer(rightRenderer);
//            t.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
//        }

        //======================================================================
        // cara untuk menyesuaikan kolom dari tabel adalah mengambil lebar kolom yang ada kemudian sesuaikan
        TableColumnModel tableColumnModel = t.getColumnModel();
        for (int column = 0; column < tableColumnModel.getColumnCount(); column++) {
            int columnWidthMax = 0;
            for (int row = 0; row < t.getRowCount(); row++) {
                TableCellRenderer tableCellRenderer = t.getCellRenderer(row, column);
                Object tableValue = t.getValueAt(row, column);
                Component component = tableCellRenderer.getTableCellRendererComponent(t, tableValue, false, false, row, column);
                columnWidthMax = Math.max(component.getPreferredSize().width, columnWidthMax);
            }
            TableColumn tableColumn = tableColumnModel.getColumn(column);
            tableColumn.setPreferredWidth(columnWidthMax);
            t.getTableHeader().getColumnModel().getColumn(column).setHeaderRenderer(rightRenderer);
            t.getColumnModel().getColumn(column).setCellRenderer(rightRenderer);
        }
        t.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        t.setFillsViewportHeight(true);

    }

    /**
     * Format mata uang dari BigDecimal menggunakan simbol Rupiah
     *
     * @param value
     * @return formatted value
     */
    public static String formatMataUangDenganSimbolRupiah(BigDecimal value) {
        if (value == null || value.equals(BigDecimal.ZERO)) {
            return "0.00";
        } else {
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);

            Long longValue = ((value.longValue() + 50) / 100) * 100;

            StringBuilder sb = new StringBuilder();
            sb.append("Rp. ");
            sb.append(formatter.format(longValue));
            return sb.toString();
        }
    }

    /**
     * Format mata uang dari BigDecimal tanpa simbol rupiah
     *
     * @param value
     * @return formatted value
     */
    public static String formatMataUangTanpaSimbolRupiah(BigDecimal value) {
        if (value == null || value.equals(BigDecimal.ZERO)) {
            return "0.00";
        } else {
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            Long longValue = ((value.longValue() + 50) / 100) * 100;
            return formatter.format(longValue);
        }
    }

    /**
     * method untuk membuat input textfield hanya diperbolehkan angka
     *
     * @param textField
     */
    public static void setTextfieldHanyaAngka(final javax.swing.JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar()) || BAD_CHARS.indexOf(evt.getKeyChar()) > -1) {
                    evt.consume();
                }
            }
        });
    }

    /**
     * method untuk mengubah inputkan berubah menjadi format mata uang
     *
     * @param textField
     * @return textField
     */
    public static JTextField formatInputanTextfield(JTextField textField) {
        textField.addKeyListener(new IntegerMasking());
        return textField;
    }

    /**
     * method untuk mengambil value dari textfield apabila sudah terformat
     *
     * @param jtextfield
     * @return string value
     */
    public static String getNilaiDariTextField(final JTextField jtextfield) {
        final char txt[] = jtextfield.getText().toCharArray();
        StringBuilder sb = new StringBuilder();
        String tmp = "";
        for (int i = 0; i < txt.length; i++) {
            tmp = String.valueOf(txt[i]);

            if (tmp.equals(".") || tmp.equals(",")) {
            } else {
                sb.append(tmp);
            }
        }

        return sb.toString();
    }

    /**
     * mengubah format rupiah kembali ke Double
     *
     * @param nilaiRupiah
     * @return value
     */
    public static Double ubahFormatRupiahkeAngka(String nilaiRupiah) {

        Double value = 0d;
        if (nilaiRupiah == null || nilaiRupiah.isEmpty()) {
            return value;
        }

        try {
            NumberFormat formatter = NumberFormat.getInstance();
            Number number = formatter.parse(nilaiRupiah);
            value = number.doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(HelperUmum.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    public static Double pembulatanDibelakangKoma(Double value) {

        try {
            return (new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_EVEN)).doubleValue();
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(value)) {
                return value;
            } else {
                return Double.NaN;
            }
        }

        //return value;
    }

    private static String formatCurrencyForString(String text) {
        char thousandsSeparator = '.';
        char decimalSeparator = ',';
        if (Locale.getDefault().equals(Locale.US)) {
            thousandsSeparator = ',';
            decimalSeparator = '.';
        } else {
            thousandsSeparator = '.';
            decimalSeparator = ',';
        }
        StringBuilder builder = new StringBuilder();
        boolean isDecimalSeparatorFound = false;
        for (Character c : text.toCharArray()) {
            if (c != thousandsSeparator) {
                builder.append(c);
                if (c == decimalSeparator) {
                    isDecimalSeparatorFound = true;
                }
            }
        }
        char[] arr = builder.toString().toCharArray();
        StringBuilder builder1 = new StringBuilder();
        int maxIndex = arr.length - 1;
        //mengambil pecahan
        int i = 0;
        int decimalSeparatorIndex = 0;
        if (isDecimalSeparatorFound) {
            for (; i <= maxIndex; i++) {
                char c = arr[maxIndex - i];
                if (c != decimalSeparator) {
                    builder1.append(c);
                } else {
                    isDecimalSeparatorFound = false;
                    break;
                }
            }
            builder1.append(arr[maxIndex - i++]);
            decimalSeparatorIndex = i;
        }
        for (i = 0; i <= maxIndex - decimalSeparatorIndex; i++) {
            char c = arr[maxIndex - i - decimalSeparatorIndex];
            if (i != 0 && i % 3 == 0) {
                builder1.append(thousandsSeparator);
                builder1.append(c);
            } else {
                builder1.append(c);
            }
        }
        StringBuilder builder2 = new StringBuilder();
        char[] arr2 = builder1.toString().toCharArray();
        maxIndex = arr2.length - 1;
        for (i = 0; i <= maxIndex; i++) {
            char c = arr2[maxIndex - i];
            builder2.append(c);
        }
        return builder2.toString();
    }

    // =============================================================================================================================================//
    private static class IntegerMasking implements KeyListener {

        public void keyTyped(KeyEvent evt) {
            JTextField source = (JTextField) evt.getSource();
            if (Locale.getDefault() == Locale.US) {
                if (Character.isLetter(evt.getKeyChar())
                        || BAD_CHARS.indexOf(evt.getKeyChar()) >= 0) {
                    if (evt.getKeyChar() != '.' || source.getText().indexOf('.') >= 0) {
                        evt.consume();
                    }
                }
            } else if (Character.isLetter(evt.getKeyChar())
                    || BAD_CHARS.indexOf(evt.getKeyChar()) >= 0) {
                if (evt.getKeyChar() != ',' || source.getText().indexOf(',') >= 0) {
                    evt.consume();
                }
            }
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent evt) {
            if (evt.getKeyCode() == KeyEvent.VK_LEFT
                    || evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                return;
            }
            if (evt.getSource() instanceof JTextField) {
                JTextField textField = (JTextField) evt.getSource();
                int caretPosition = textField.getCaretPosition();
                int initialLentgh = textField.getText() != null ? textField.getText().length() : 0;
                String formatedNumber = formatCurrencyForString(textField.getText());
                textField.setText(formatedNumber);
                if (formatedNumber.length() > initialLentgh) {
                    textField.setCaretPosition(caretPosition + 1);
                }
            }
        }
    }
}
