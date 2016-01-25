/*
 * Table.java
 *
 * Created on 12 de octubre de 2008, 10:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package abstractt;

import domain.Fecha;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Gilberto Adan Gonz�lez Silva
 */
public class Table extends javax.swing.JTable {

    /**
     * Creates a new instance of Table
     */
    public Table() {

        getTableHeader().setFont(new Font("Trebuchet", 0, 12));
        getTableHeader().setBackground(new Color(64, 37, 4));
        getTableHeader().setForeground(new Color(230, 225, 195));
        getTableHeader().setOpaque(false);
        setFont(new Font("Trebuchet", 0, 12));
//        addJTableHeaderListener();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setSelectionBackground(new Color(195, 25, 25));
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int column = columnAtPoint(p);
                String texto = String.valueOf(Table.this.getValueAt(row, column));
                if (texto == null) {
                    texto = " ";
                }
                setToolTipText(texto);
                TableColumn nColumn = Table.this.getColumnModel().getColumn(column);
            }
        });

        colors = new Color[]{
            new Color(120, 118, 93), // brown
            new Color(120, 118, 93) // gray
        };
    }

    public void colorPane() {

        if (getParent() != null) {

            Container parent = getParent().getParent();
            scrollpane = (JScrollPane) parent;
            scrollpane.getViewport().setBackground(new Color(120, 118, 93));
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
    
    private Color[] colors;

    public void buscar(String valor, int columna, JScrollPane jScrollPane) {

        for (int i = 0; i < getRowCount(); i++) {
            if (valor.equals(this.getValueAt(i, columna).toString())) {

                setRowSelectionInterval(i, i);
                Rectangle r = getCellRect(i, columna, true);
                jScrollPane.getViewport().scrollRectToVisible(r);
                return;
            }
        }
    }

    private void scrollToRow(JScrollPane jScrollPane) {

        Integer ultimaFila;

        ultimaFila = this.getRowCount() - 1;

        Rectangle r = getCellRect(ultimaFila, columna, true);
        setRowSelectionInterval(ultimaFila, ultimaFila);

        jScrollPane.getViewport().scrollRectToVisible(r);
    }

    public void eliminarFila(Integer fila) {

        DefaultTableModel modelo = (DefaultTableModel) getModel();
        //int filas = getRowCount();

        //for (int i = 0; i < filas; i++) {
        modelo.removeRow(fila);
        //}

        setModel(modelo);

    }

    public void setColor(Color color) {

        colors = new Color[]{
            color,
            new Color(120, 118, 93) // gray
        };
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);

        if (!isCellSelected(row, column)) {
            // If the cell is NOT selected, then calculates the index
            // in the color array and sets the background.
            int index = row % colors.length;

            rendererComponent.setBackground(colors[index]);
        }

        return rendererComponent;
    }

    public void centrar() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < this.getColumnCount(); i++) {

            getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        }
        //setDefaultRenderer(String.class, centerRenderer);
    }

    public void ocultarcolumna(int columna) {

        getColumnModel().getColumn(columna).setMinWidth(0);
        getColumnModel().getColumn(columna).setMaxWidth(0);
    }

    public void setTitulos(String as_titulos[]) {

        titulos = as_titulos;
        cambiarTitulos();
        /*se Añadio el color de la tabla para que sea automatico y no poner el evento en cada instancia*/
        colorPane();
    }

    public void cambiarTitulos() {

        TableColumnModel tcm = this.getColumnModel();
        for (int col = 0; col < titulos.length; col++) {
            tcm.getColumn(col).setHeaderValue(titulos[col]);
        }

    }

//    public void addJTableHeaderListener() {
//
//        MouseAdapter mouseListener = new MouseAdapter() {
//            public void mouseReleased(MouseEvent e) {
//
//                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
//                columna = convertColumnIndexToModel(viewColumn);
//
//                // Ordenar elementos de la columna de la tabla.
//                if (e.getClickCount() == 1 && columna != -1) {
//                    if (ordenar) {
//                        ordenarPorColumna();
//                    }
//                }
//            }
//        };
//
//        JTableHeader header = getTableHeader();
//        header.addMouseListener(mouseListener);
//    }
    public void quitarSeleccion() {

        int filaSeleccionada = getSelectedRow();

        if (filaSeleccionada != -1) {

            Object arr[] = new Object[getColumnCount()];

            //Se copia la fila
            for (int i = 0; i < getColumnCount(); i++) {

                arr[i] = getValueAt(filaSeleccionada, i);
            }

            DefaultTableModel modelo = (DefaultTableModel) getModel();
            //se remueve la fila de la tabla
            modelo.removeRow(filaSeleccionada);
            //Se vuelve a insertar en la fila donde estaba
            modelo.insertRow(filaSeleccionada, arr);

            setModel(modelo);
        }
    }

    /**
     * Ordena la tabla por la columna dada intercalando Descendente <->
     * Ascendente una a la vez, por columna Emp�eza ordenando Ascendente
     *
     */
    private void ordenarPorColumna() {

        int filas = getRowCount();
        int columnas = getColumnCount();

        columnaTabla = new LinkedList();
        columnaTablaOrdenada = new LinkedList();

        for (int i = 0; i < filas; i++) {

            columnaTabla.add(getValueAt(i, columna));
            columnaTablaOrdenada.add(getValueAt(i, columna));
        }

        ordenar();

        indices = new int[filas];
        for (int i = 0; i < filas; i++) {
            indices[i] = -1;
        }

        for (int i = 0; i < filas; i++) {

            if (!orden[columna]) {
                //Ascendente
                indices[i] = obtenerIndiceUnico(columnaTablaOrdenada.get(i).toString());
            } else {
                //Descendente
                indices[i] = obtenerIndiceUnico(columnaTablaOrdenada.get((filas - 1) - i).toString());
            }
        }

        if (orden[columna]) {

            orden[columna] = false;
        } else {

            orden[columna] = true;
        }

        DefaultTableModel modelo = (DefaultTableModel) getModel();
        //TableModel modelo =  getModel();
        int removidas = 0;

        Object arr[];

        try {

            for (int i = 0; i < indices.length; i++) {

                arr = new Object[columnas];
                for (int j = 0; j < columnas; j++) {

                    arr[j] = modelo.getValueAt(indices[i], j);
                }

                modelo.addRow(arr);
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException err) {
        }

        for (int i = 0; i < indices.length; i++) {

            modelo.removeRow(0);
        }

        setModel(modelo);
    }

    public void agregarFila(JScrollPane jScrollPane1) {

        int columnas = getColumnCount();
        Object arr[];
        arr = new Object[columnas];
        DefaultTableModel modelo = (DefaultTableModel) getModel();

        modelo.addRow(arr);
        setModel(modelo);

        scrollToRow(jScrollPane1);
    }

    private void ordenar() {

        switch (formato[columna]) {
            //letra
            case 0:
                Collections.sort(columnaTablaOrdenada);
                break;
            //numero
            case 1:
                ordenarNumero();
                break;
            //moneda
            case 2:
                ordenarMoneda();
                break;
            //fecha
            case 3:
                ordenarFecha();
                break;
            //numero Entero
            case 4:
                ordenarNumeroEntero();
                break;
        }
    }

    private void ordenarFecha() {

        Fecha fecha = new Fecha();
        String Sfecha;

        for (int i = 0; i < columnaTablaOrdenada.size(); i++) {

            //columnaTablaOrdenada.set(i, fecha.cambiarFormato(columnaTablaOrdenada.get(i).toString()));
            columnaTablaOrdenada.set(i, columnaTablaOrdenada.get(i).toString());
        }

        Collections.sort(columnaTablaOrdenada);

        for (int i = 0; i < columnaTablaOrdenada.size(); i++) {

            //  columnaTablaOrdenada.set(i, fecha.obtenerFecha(columnaTablaOrdenada.get(i).toString()));
            columnaTablaOrdenada.set(i, columnaTablaOrdenada.get(i).toString());
        }
    }

    private void ordenarMoneda() {

        String campo;
        StringTokenizer stt;

        //Se le quita el caracter $
        for (int i = 0; i < columnaTablaOrdenada.size(); i++) {

            campo = columnaTablaOrdenada.get(i).toString();
            stt = new StringTokenizer(campo, " ");
            stt.nextToken();
            campo = stt.nextToken();
            columnaTablaOrdenada.set(i, campo);
        }

        //Se ordena por numero
        ordenarNumero();

        //Se le regresa el caracter $
        for (int i = 0; i < columnaTablaOrdenada.size(); i++) {
            campo = "$ ";
            campo += columnaTablaOrdenada.get(i).toString();
            columnaTablaOrdenada.set(i, campo);
        }
    }

    private void ordenarNumero() {

        String campo;
        double numero;

        for (int i = 0; i < columnaTablaOrdenada.size(); i++) {

            numero = Double.parseDouble(obtenerNumero(columnaTablaOrdenada.get(i).toString()));
            columnaTablaOrdenada.set(i, numero);
        }

        Collections.sort(columnaTablaOrdenada);

        //Regresar Formato de dos decimales
        for (int i = 0; i < columnaTablaOrdenada.size(); i++) {

            numero = Double.parseDouble(columnaTablaOrdenada.get(i).toString());
            columnaTablaOrdenada.set(i, redondear(numero));
        }
    }

    private void ordenarNumeroEntero() {

        String campo;
        int numero;

        for (int i = 0; i < columnaTablaOrdenada.size(); i++) {

            numero = Integer.parseInt(columnaTablaOrdenada.get(i).toString());
            columnaTablaOrdenada.set(i, numero);
        }

        Collections.sort(columnaTablaOrdenada);
    }

    /**
     * Si el numero tiene comas se las quita
     */
    private String obtenerNumero(String numero) {

        StringTokenizer num = new StringTokenizer(numero, ",");

        numero = "";

        while (num.hasMoreTokens()) {
            numero += num.nextToken();
        }
        return numero;
    }

    private int obtenerIndiceUnico(String ob) {

        for (int i = 0; i < columnaTabla.size(); i++) {

            if (columnaTabla.get(i).toString().equals(ob)) {

                if (!repetido(i)) {
                    return i;
                }
            }
        }

        return -1;
    }

    private boolean repetido(int i) {

        for (int j = 0; j < indices.length; j++) {
            if (indices[j] == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Quita todas las filas de un tabla
     *
     */
    public void limpiarTabla() {

        DefaultTableModel modelo = (DefaultTableModel) getModel();
        int filas = getRowCount();

        for (int i = 0; i < filas; i++) {

            modelo.removeRow(0);
        }

        setModel(modelo);
    }

    /**
     * Quita todas las filas de un tabla menos una
     *
     */
    protected void limpiarTabla1() {

        DefaultTableModel modelo = (DefaultTableModel) getModel();
        int filas = getRowCount();

        for (int i = 0; i < filas - 1; i++) {

            modelo.removeRow(0);
        }

        setModel(modelo);
    }

    /**
     * Da tama�o las columnas con con un arreglo de enteros
     *
     */
    private int[] tamaños;

    public void tamañoColumna(int[] Atamaños) {

        tamaños = Atamaños;

        TableColumn nColumn;

        for (int i = 0; i < tamaños.length; i++) {

            nColumn = getColumnModel().getColumn(i);
            nColumn.setPreferredWidth(tamaños[i]);
        }
    }

    public void reasignaTamaños() {

        TableColumn nColumn;

        if (tamaños != null) {

            for (int i = 0; i < tamaños.length; i++) {

                nColumn = getColumnModel().getColumn(i);
                nColumn.setPreferredWidth(tamaños[i]);
            }
        }
    }

    /**
     * Redondea un valor double en unos decimales especificados
     *
     */
    protected static String redondear(double num) {

        boolean negativo = false;
        if (num < 0) {

            num *= -1;
            negativo = true;
        }

        int decimales = 2;

        double expo = Math.pow(10, decimales);
        //siguiente valor despues de los decimales
        double expo2 = Math.pow(10, decimales + 1);

        //numero siguiente al redondeado
        int num1 = ((int) (num * expo2)) - (((int) (num * expo2)) / 10) * 10;

        //si el numero es menor que 5 no aumenta el redondeo
        if (num1 < 5) {
            //num = ( (double)( (int)(num * expo)  ) )/ expo;
        } else {
            //si el numero es 5 o mayor se le suma uno al menor decimal
            num = ((double) ((int) (num * expo) + 1)) / expo;
        }
        int segundoDecimal = ((int) (num * expo)) - (((int) (num * expo)) / 10) * 10;

        // ponerComas(num+"");
        String numero;

        if (segundoDecimal == 0) {
            numero = ponerComas(num + "0");
        } else {
            numero = ponerComas(num + "");
        }

        if (negativo) {
            return "-" + numero;
        } else {
            return numero;
        }
    }

    private static String ponerComas(String num) {

        StringTokenizer stt;

        stt = new StringTokenizer(num, ".");

        String numero = stt.nextToken();
        String decimales = stt.nextToken();

        String orenum = "";
        int cont = 0;
        for (int i = numero.length() - 1; i >= 0; i--) {

            if (cont == 3) {
                orenum += ",";
                cont = 0;
            }
            orenum += numero.charAt(i);

            cont++;
        }

        numero = "";
        for (int i = orenum.length() - 1; i >= 0; i--) {

            numero += orenum.charAt(i);
        }

        return numero + "." + decimales;
    }

    /**
     * Formatos de columna 0 = letras; 1 = numeros (dobles); 2 = monedas; 3 =
     * fecha 4 = numeros(enteros)
     */
    public void setFormato(int format[]) {

        formato = new int[format.length];
        for (int i = 0; i < formato.length; i++) {

            formato[i] = format[i];
        }

        orden = new boolean[formato.length];
    }

    public void setOrdenar(boolean orden) {

        ordenar = orden;
    }
    /*
     2015-01-02
     Formatos
     */
    public static final int letra = 0;
    public static final int numero_double = 1;
    public static final int moneda = 2;
    public static final int fecha = 3;
    public static final int numero_entero = 4;

    private int formato[];
    private int indices[];
    private int columna;

    private boolean orden[] = null;
    private boolean ordenar = true;

    private LinkedList columnaTabla;
    private LinkedList columnaTablaOrdenada;
    private String titulos[];

    JScrollPane scrollpane;
    public Integer colItemStatus;

    public void agregarItemStatus() {

        String titulos_nvos[];

        colItemStatus = getColumnCount();
        Integer filas;
        filas = getRowCount();

        DefaultTableModel modelo = (DefaultTableModel) getModel();

        Object arr[];
        arr = new Object[filas];

        for (int i = 0; i < filas; i++) {

            arr[i] = 0;
        }

        modelo.addColumn("ItemStatus", arr);
        setModel(modelo);

        titulos_nvos = new String[titulos.length];

        for (int i = 0; i < titulos.length; i++) {

            titulos_nvos[i] = titulos[i];
        }
        // titulos_nvos[colItemStatus] = "ItemStatus";

        this.setTitulos(titulos_nvos);
        tamañoColumna(tamaños);
        ocultarcolumna(colItemStatus);
    }

    public void acceptText() {

        if (isEditing()) {

            getCellEditor().stopCellEditing();
        }
    }

}
