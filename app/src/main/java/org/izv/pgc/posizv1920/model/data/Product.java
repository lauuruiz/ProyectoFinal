package org.izv.pgc.posizv1920.model.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private long id;
    private String nombre;
    private Double precio;
    private String destino;


    public Product() {
        this(0, "", null, "");
    }

    public Product(long id, String nombre, Double precio, String destino) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.destino = destino;
    }

    protected Product(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        precio = in.readDouble();
        destino = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", destino='" + destino + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombre);
        dest.writeDouble(precio);
        dest.writeString(destino);
    }
}
