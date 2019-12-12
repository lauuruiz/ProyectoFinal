package org.izv.pgc.posizv1920.model.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Command implements Parcelable {
    private int id, idfactura, idproducto, idempleado, unidades, entregada;
    private double precio;

    public Command() {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    public Command(int id, int idfactura, int idproducto, int idempleado, int unidades, int entregada, double precio) {
        this.id = id;
        this.idfactura = idfactura;
        this.idproducto = idproducto;
        this.idempleado = idempleado;
        this.unidades = unidades;
        this.precio = precio;
        this.entregada = entregada;
    }

    protected Command(Parcel in) {
        id = in.readInt();
        idfactura = in.readInt();
        idproducto = in.readInt();
        idempleado = in.readInt();
        unidades = in.readInt();
        entregada = in.readInt();
        precio = in.readLong();
    }

    public static final Creator<Command> CREATOR = new Creator<Command>() {
        @Override
        public Command createFromParcel(Parcel in) {
            return new Command(in);
        }

        @Override
        public Command[] newArray(int size) {
            return new Command[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(int idfactura) {
        this.idfactura = idfactura;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public int getEntregada() {
        return entregada;
    }

    public void setEntregada(int entregada) {
        this.entregada = entregada;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", idfactura=" + idfactura +
                ", idproducto=" + idproducto +
                ", idempleado=" + idempleado +
                ", unidades=" + unidades +
                ", entregada=" + entregada +
                ", precio=" + precio +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idfactura);
        dest.writeInt(idproducto);
        dest.writeInt(idempleado);
        dest.writeInt(unidades);
        dest.writeInt(entregada);
        dest.writeDouble(precio);
    }
}