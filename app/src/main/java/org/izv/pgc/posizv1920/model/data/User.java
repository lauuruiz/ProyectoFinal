package org.izv.pgc.posizv1920.model.data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private long id;
    private String login;
    private String clave;

    public User(){
        this(0,"","");
    }

    public User(long id, String login, String clave) {
        this.id = id;
        this.login = login;
        this.clave = clave;
    }

    protected User(Parcel in) {
        id = in.readLong();
        login = in.readString();
        clave = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getClave() {
        return clave;
    }

    public User setClave(String clave) {
        this.clave = clave;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(login);
        dest.writeString(clave);
    }
}
