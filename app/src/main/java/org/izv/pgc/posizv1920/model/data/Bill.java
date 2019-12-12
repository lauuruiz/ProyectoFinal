package org.izv.pgc.posizv1920.model.data;


import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class Bill implements Parcelable {
    private int id, mesa, idempleadoinicio;
    private Timestamp horadeinicio, horadecierre;
    private String idempleadocierre;
    double total;

    public Bill() {
        this(0, 0, null, 0, null,null,0.00);
    }

    public Bill(int id, int mesa, Timestamp horadeinicio, int idempleadoinicio, Timestamp horadecierre, String idempleadocierre, double total) {
        this.id = id;
        this.idempleadoinicio = idempleadoinicio;
        this.idempleadocierre = idempleadocierre;
        this.mesa = mesa;
        this.horadeinicio = horadeinicio;
        this.horadecierre = horadecierre;
        this.total = total;
    }

    protected Bill(Parcel in) {
        id = in.readInt();
        idempleadoinicio = in.readInt();
        idempleadocierre = in.readString();
        mesa = in.readInt();
        total = in.readDouble();
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdempleadoinicio() {
        return idempleadoinicio;
    }

    public void setIdempleadoinicio(int idempleadoinicio) {
        this.idempleadoinicio = idempleadoinicio;
    }

    public String getIdempleadocierre() {
        return idempleadocierre;
    }

    public void setIdempleadocierre(String idempleadocierre) {
        this.idempleadocierre = idempleadocierre;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public Timestamp getHoradeinicio() {
        return horadeinicio;
    }

    public void setHoradeinicio(Timestamp horadeinicio) {
        this.horadeinicio = horadeinicio;
    }

    public Timestamp getHoradecierre() {
        return horadecierre;
    }

    public void setHoradecierre(Timestamp horadecierre) {
        this.horadecierre = horadecierre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", idempleadoinicio=" + idempleadoinicio +
                ", idempleadocierre=" + idempleadocierre +
                ", mesa=" + mesa +
                ", horadeinicio=" + horadeinicio +
                ", horadecierre=" + horadecierre +
                ", total=" + total +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idempleadoinicio);
        dest.writeString(idempleadocierre);
        dest.writeInt(mesa);
        dest.writeDouble(total);
    }
}
