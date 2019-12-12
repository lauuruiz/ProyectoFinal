package org.izv.pgc.posizv1920;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.izv.pgc.posizv1920.model.data.Command;
import org.izv.pgc.posizv1920.model.data.Product;
import org.izv.pgc.posizv1920.view.CommandViewModel;
import org.izv.pgc.posizv1920.view.ProductViewModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomPrintActivity extends AppCompatActivity {

    private ProductViewModel productViewModel;
    List<Command> comandas;
    List<Command> comandasAgrupadas;
    List<Product> productos;
    List<Command> comandasImprimir;
    Command comandaAux = new Command();
    ArrayList<Integer> idProductosComandas = new ArrayList<>();

    String nombreAux = "si";
    double precioAux = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_print);

        initComponents();
        // Recibo la id de la factura
        // final int idFactura = Integer.parseInt(getIntent().getStringExtra("idfactura"));

        comandas = getIntent().getParcelableArrayListExtra("arraylistCommands");
        for (int i = 0; i < comandas.size(); i++) {
            Log.v("arraylistCommands", "" + comandas.get(i).toString());
        }


        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getLiveProductList().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> products) {
                // Log.v("listproductos", "Lista productos: " + products.size());
                for (int i = 0; i < products.size(); i++) {
                    // Log.v("listproductos", "Comandas agrupadas: " + comandas.size());
                    for (int j = 0; j < comandas.size(); j++) {
                        if (products.get(i).getId() == comandas.get(j).getIdproducto()) {
                            productos.add(products.get(i));
                            Log.v("adaptador", "listProduct: " + productos.size());
                        }
                    }
                    // Log.v("arraylistProducts", "" + productos.get(i).toString());
                }
                }
            });

        // fillProducts();
        agruparComandas();
        obtenerIdProductosComandas();

        printDocument();
    }

    private void initComponents() {

        comandas = new ArrayList<>();
        productos = new ArrayList<>();
        comandasAgrupadas = new ArrayList<>();

    }

    // método para rellenar productos en vez de directamente cogerlos del servidor (CAMBIAR)
    private void fillProducts() {
        productos.add(new Product(1, "Big mac", 3.5d, "Mesa"));
        productos.add(new Product(2, "King Fusion", 6.6d, "Mesa"));
        productos.add(new Product(3, "Fanta", 1.8d, "Mesa"));
        productos.add(new Product(4, "Flamenquín con patatas", 8.0d, "Mesa"));
        productos.add(new Product(5, "Arroz del chino", 4.7d, "Mesa"));
        productos.add(new Product(6, "Angulas", 5.0d, "Mesa"));
        productos.add(new Product(7, "Solo hay uno de este", 10.0d, "Mesa"));
        productos.add(new Product(8, "Coca cola", 2.35d, "Mesa"));
        productos.add(new Product(9, "Patatas bravas con queso", 2.9d, "Mesa"));
        productos.add(new Product(10, "Sprite", 1.6d, "Mesa"));
        productos.add(new Product(11, "Macarrones", 8.5d, "Mesa"));
        productos.add(new Product(12, "Agua mineral", 1.0d, "Mesa"));
        productos.add(new Product(13, "Pollo a la plancha", 3.5d, "Mesa"));
        productos.add(new Product(14, "Producto 14", 14d, "Mesa"));
        productos.add(new Product(15, "Producto 15", 15d, "Mesa"));
        productos.add(new Product(16, "Producto 16", 16d, "Mesa"));
        productos.add(new Product(17, "Producto 17", 17d, "Mesa"));
        productos.add(new Product(18, "Producto 18", 18d, "Mesa"));
        productos.add(new Product(19, "Producto 19", 19d, "Mesa"));
        productos.add(new Product(20, "Producto 20", 20d, "Mesa"));
        productos.add(new Product(21, "Producto 21", 21d, "Mesa"));
        productos.add(new Product(22, "Producto 22", 22d, "Mesa"));
        productos.add(new Product(23, "Producto 23", 23d, "Mesa"));
        productos.add(new Product(24, "Producto 24", 24d, "Mesa"));
        productos.add(new Product(25, "Producto 25", 25d, "Mesa"));


    }

    // método para rellenar comandas en vez de directamente cogerlas del servidor (CAMBIAR)
    private void fillCommands() {
        comandas.add(new Command(1, 1, 1, 1, 1, 1, 3.5d));
        comandas.add(new Command(2, 1, 1, 1, 3, 1, 3.5d));
        comandas.add(new Command(3, 1, 1, 1, 2, 1, 3.5d));
        comandas.add(new Command(4, 1, 2, 2, 1, 1, 6.6d));
        comandas.add(new Command(5, 1, 2, 2, 1, 1, 6.6d));
        comandas.add(new Command(6, 1, 6, 1, 4, 1, 5.0d));
        comandas.add(new Command(7, 1, 7, 3, 7, 1, 10.0d));
        comandas.add(new Command(8, 1, 8, 3, 12, 1, 2.3d));

        comandas.add(new Command(1, 1, 9, 1, 1, 1, 3.5d));
        comandas.add(new Command(2, 1, 10, 1, 3, 1, 3.5d));
        comandas.add(new Command(3, 1, 11, 1, 2, 1, 3.5d));
        comandas.add(new Command(4, 1, 12, 2, 1, 1, 6.6d));
        comandas.add(new Command(5, 1, 13, 2, 1, 1, 6.6d));
        comandas.add(new Command(6, 1, 14, 1, 4, 1, 5.0d));
        comandas.add(new Command(7, 1, 15, 3, 7, 1, 10.0d));
        comandas.add(new Command(8, 1, 16, 3, 12, 1, 2.3d));

        comandas.add(new Command(1, 1, 17, 1, 1, 1, 3.5d));
        comandas.add(new Command(2, 1, 18, 1, 3, 1, 3.5d));
        comandas.add(new Command(3, 1, 19, 1, 2, 1, 3.5d));
        comandas.add(new Command(4, 1, 20, 2, 1, 1, 6.6d));
        comandas.add(new Command(5, 1, 21, 2, 1, 1, 6.6d));
        comandas.add(new Command(6, 1, 22, 1, 4, 1, 5.0d));
        comandas.add(new Command(7, 1, 23, 3, 7, 1, 10.0d));
        comandas.add(new Command(8, 1, 24, 3, 12, 1, 2.3d));
    }

    //Metodo para crear el array de comandas final
    private void agruparComandas() {
        // fillCommands();
        comandasAgrupadas = new ArrayList<>(); // instancio el arraylist nuevo

        boolean añadida;
        Command comandaAux, comandaAux2; // creo 2 objetos auxiliares de la clase comanda
        for (int i = 0; i < comandas.size(); i++) { // recorro el array de comandas actual (sin juntar)

            comandaAux2 = comandas.get(i); // en el objeto comandaAux2 tengo la comanda recorrida [0, 1, 2...]

            añadida = false; // asigno al booleano el valor false
            if (comandasAgrupadas.size() == 0) { // compruebo si el futuro arraylist está vacio
                comandasAgrupadas.add(comandaAux2); // si está vacio añado la primera comanda
            } else { // cuando ya haya minimo 1 comanda añadida...
                for (int j = 0; j < comandasAgrupadas.size(); j++) { // recorro el arraylist bueno
                    if (comandasAgrupadas.get(j).getIdproducto() == comandaAux2.getIdproducto()) { //
                        comandaAux = comandasAgrupadas.get(j);
                        comandaAux.setUnidades(comandaAux.getUnidades() + comandaAux2.getUnidades());
                        comandaAux.setPrecio(comandaAux.getPrecio() + comandaAux2.getPrecio());
                        comandasAgrupadas.set(j, comandaAux);
                        añadida = true;
                    }
                }
                if (!añadida) {
                    comandasAgrupadas.add(comandaAux2);
                }
            }
            //Log.v(TAG, "comandas.size: " + comandasAgrupadas.size());
        }
    }

    private void obtenerIdProductosComandas() {
        for (int i = 0; i < comandasAgrupadas.size(); i++) {
            idProductosComandas.add(comandasAgrupadas.get(i).getIdproducto());
        }
    }

    private void comprobarComandas() {
        // Log.v("xyz", "productos size: " + productos.size() + " / idproductos size: " + idProductosComandas.size());
        for (int i = 0; i < idProductosComandas.size(); i++) {
            if (idProductosComandas.get(i) == productos.get(i).getId()) {

                nombreAux = productos.get(i).getNombre();
                precioAux = productos.get(i).getPrecio();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printDocument() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) + " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(this, productos, comandas, comandasAgrupadas, nombreAux, precioAux, idProductosComandas), null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public class MyPrintDocumentAdapter extends PrintDocumentAdapter {

        public PdfDocument myPdfDocument;
        public int totalpages = 2;
        List<Command> comandas;
        List<Command> comandasAgrupadas;
        List<Product> productos;
        ArrayList<Integer> idProductosComandas;
        String nombreAux;
        double precioAux;
        Context context;
        private int pageHeight;
        private int pageWidth;
        private int rest = 0;
        private int startFrom = 0;
        private int endAt = 15;

        public MyPrintDocumentAdapter(Context context, List<Product> productos, List<Command> comandas, List<Command> comandasAgrupadas, String nombreAux, double precioAux, ArrayList<Integer> idProductosComandas) {
            this.context = context;
            this.productos = productos;
            this.comandas = comandas;
            this.comandasAgrupadas = comandasAgrupadas;
            this.nombreAux = nombreAux;
            this.precioAux = precioAux;
            this.idProductosComandas = idProductosComandas;
            // Log.v("xyz", "numero de comandas:  " + comandas.size());
            // Log.v("xyz", "paginas extra; " + Math.ceil(comandas.size() / 15));
            this.totalpages = 1 + (comandasAgrupadas.size() + 15 - 1) / 15; // Ecuacion to guapa para calcular el numero de paginas extra en funcion de el numero de tuplas
            this.rest = comandasAgrupadas.size() % 15;

            if (rest == 0) { // Una pagina extra si las paginas quedan justas a la cantidad de comandas
                totalpages++;
            }
            // Log.v("xyz", "numpaginas: " + totalpages + " resto: " + rest);
        }

        // nombre  /  unidades  /  precioTotal
        private boolean pageInRange(PageRange[] pageRanges, int page) {
            for (int i = 0; i < pageRanges.length; i++) {
                if ((page >= pageRanges[i].getStart()) &&
                        (page <= pageRanges[i].getEnd()))
                    return true;
            }
            return false;
        }

        private void drawPage(PdfDocument.Page page, int pagenumber) {
            Date currentTime = Calendar.getInstance().getTime();
            Canvas canvas = page.getCanvas();

            pagenumber++; // Make sure page numbers start at 1

            int titleBaseLine = 72;
            int leftMargin = 54;
            int totalDePaginaExacto = 575;
            int totalDePagina = 400;
            int widthStart = 25;
            int heightStart = 250;
            int heightCommands = 75;

            Paint factura = new Paint();

            // Si es la primera página dibujo el logo de la empresa como si fuera la cabecera del ticket
            if (pagenumber == 1) {

                //TÍTULO DE LA FACTURA
                factura.setTextSize(40);
                canvas.drawText("POSIZV", (totalDePaginaExacto - 135) / 2, 100, factura);

                //SEPARACIÓN TITULO FACTURA --------------------------------------------------------
/*
                // top lane
                canvas.drawRect(20, 110, totalDePaginaExacto, 112, factura);

                canvas.drawRect(26, 116, totalDePaginaExacto - 26, 122, factura);

                // bottom lane
                canvas.drawRect(20, 126, totalDePaginaExacto, 128, factura);

                // left lane
                canvas.drawRect(20, 110, 22, 128, factura);

                // right lane
                canvas.drawRect(totalDePaginaExacto, 110, totalDePaginaExacto + 2, 128, factura);
*/
                //----------------------------------------------------------------------------------

                // SI NO ES LA PRIMERA HOJA, QUE QUEPAN MÁS COMANDAS
            } else if (pagenumber > 1) {
                //factura.setTextSize(40);
                //canvas.drawText("pagina " + pagenumber, (totalDePaginaExacto - 175) / 2, 100, factura);

                /* TITULOS */
                factura.setTextSize(26);
                canvas.drawText("NOMBRE", 25, 75, factura);

                factura.setTextSize(26);
                canvas.drawText("UNIDADES", 270, 75, factura);

                factura.setTextSize(26);
                canvas.drawText("TOTAL", 460, 75, factura);

                for (int i = startFrom; i < endAt && i < comandasAgrupadas.size(); i++) {
                    //Log.v("xyz", "comandasAgrupadas.size: " + comandasAgrupadas.size());
                    //Log.v("xyz", "idProductosComandas.size: " + idProductosComandas.size());

                   for (Product p : productos) {
                        if (comandasAgrupadas.get(i).getIdproducto() == p.getId()) {
                            nombreAux = p.getNombre();
                            precioAux = p.getPrecio();
                        }
                    }

                    //Log.v("xyzyx", "Nombre del producto: " + nombreAux + " En la posicion: " + i);
                    // Nombre de la comanda

                    factura.setTextSize(19);
                    canvas.drawText(nombreAux, 25, heightCommands + 100, factura);
                    //Log.v("xyzyxaaa", "nombre del producto: " + nombreAux + "Precio del producto: " + precioAux);

                    // Unidades de la comanda
                    factura.setTextSize(19);
                    canvas.drawText("" + comandasAgrupadas.get(i).getUnidades(), 325, heightCommands + 100, factura);

                    // Precio total de la comanda

                    factura.setTextSize(19);
                    double precioTotal = precioAux * comandasAgrupadas.get(i).getUnidades();
                    // Lo parseo a int para recoger simplemente los 2 decimales que necesito (céntimos)
                    precioTotal = precioTotal * 100;
                    precioTotal = (int) precioTotal;
                    precioTotal = precioTotal / 100;
                    canvas.drawText(precioTotal + "€", 480, heightCommands + 100, factura);

                    heightCommands = heightCommands + 35;

                }
                startFrom += 15;
                endAt += 15;

            }

        }

        @Override
        public void onLayout(PrintAttributes oldAttributes,
                             PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback, Bundle metadata) {

            myPdfDocument = new PrintedPdfDocument(context, newAttributes);

            pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
            pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }

            if (totalpages > 0) {
                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                        .Builder("print_output.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalpages);

                PrintDocumentInfo info = builder.build();
                callback.onLayoutFinished(info, true);
            } else {
                callback.onLayoutFailed("Page count is zero.");
            }
        }

        @Override
        public void onWrite(final PageRange[] pageRanges, final ParcelFileDescriptor destination, final CancellationSignal cancellationSignal, final WriteResultCallback callback) {
            for (int i = 0; i < totalpages; i++) {
                if (pageInRange(pageRanges, i)) {
                    PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create();

                    PdfDocument.Page page = myPdfDocument.startPage(newPage);

                    if (cancellationSignal.isCanceled()) {
                        callback.onWriteCancelled();
                        myPdfDocument.close();
                        myPdfDocument = null;
                        return;
                    }
                    drawPage(page, i);
                    myPdfDocument.finishPage(page);
                }
            }

            try {
                myPdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                myPdfDocument.close();
                myPdfDocument = null;
            }

            callback.onWriteFinished(pageRanges);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            Intent intent = new Intent(CustomPrintActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }


}