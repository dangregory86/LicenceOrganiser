package gregory.dan.licenceorganiser.Unit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
public class Ammunition implements Parcelable {

    private String adac, description, HCC;
    private int quantity;

    public Ammunition(String adac, String description, String HCC, int quantity) {
        this.adac = adac;
        this.description = description;
        this.HCC = HCC;
        this.quantity = quantity;
    }

    protected Ammunition(Parcel in) {
        adac = in.readString();
        description = in.readString();
        HCC = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<Ammunition> CREATOR = new Creator<Ammunition>() {
        @Override
        public Ammunition createFromParcel(Parcel in) {
            return new Ammunition(in);
        }

        @Override
        public Ammunition[] newArray(int size) {
            return new Ammunition[size];
        }
    };

    public String getAdac() {
        return adac;
    }

    public void setAdac(String adac) {
        this.adac = adac;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHCC() {
        return HCC;
    }

    public void setHCC(String HCC) {
        this.HCC = HCC;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adac);
        dest.writeString(description);
        dest.writeString(HCC);
        dest.writeInt(quantity);
    }
}
